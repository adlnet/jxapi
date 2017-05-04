package gov.adlnet.xapi.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.util.encoders.Hex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gov.adlnet.xapi.model.Actor;
import gov.adlnet.xapi.model.IStatementObject;
import gov.adlnet.xapi.model.adapters.ActorAdapter;
import gov.adlnet.xapi.model.adapters.StatementObjectAdapter;
import gov.adlnet.xapi.util.Base64;

public class BaseClient {
	private static final String XAPI_VERSION = "1.0.0";
	private static final String LINE_FEED = "\r\n";
	private static final int ERROR_RESPONSE = 400;
	protected URL _host;
	protected Gson gson;
	protected String username;
	protected String password;
	protected String authString;
	public BaseClient(String uri, String username, String password)
			throws MalformedURLException {
		init(new URL(uri), username, password);
	}

	public BaseClient(URL uri, String username, String password)
			throws MalformedURLException {
		init(uri, username, password);
	}	
	
	public BaseClient(String uri, String encodedUsernamePassword)
			throws MalformedURLException {
		
		init(new URL(uri), encodedUsernamePassword);
	}
	
	public BaseClient(URL uri, String encodedUsernamePassword)
			throws MalformedURLException {
		
		init(uri, encodedUsernamePassword);
	}
	
	protected Gson getDecoder() {	
		if (gson == null) {
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(Actor.class, new ActorAdapter());
			builder.registerTypeAdapter(IStatementObject.class,
					new StatementObjectAdapter());
			gson = builder.create();
		}
		return gson;
	}

	protected void init(URL uri, String user, String password)
            throws MalformedURLException{
		String holder = uri.toString();
        if(holder.endsWith("/")){
            URL newUri = new URL(holder.substring(0, holder.length()-1));
            this._host = newUri;
        }
        else{
            this._host = uri;
        }
		this.username = user;
		this.password = password;
        this.authString = "Basic " + Base64.encodeToString((this.username + ":" + this.password).getBytes(), Base64.NO_WRAP);
    }
	
	
	
	protected void init(URL uri, String encodedUsernamePassword)
            throws MalformedURLException{
		String holder = uri.toString();
        if(holder.endsWith("/")){
            URL newUri = new URL(holder.substring(0, holder.length()-1));
            this._host = newUri;
        }
        else{
            this._host = uri;
        }

        this.authString = "Basic " + encodedUsernamePassword;
    }

	protected String readFromConnection(HttpURLConnection conn)
			throws java.io.IOException {
		InputStream in;
		if (conn.getResponseCode() >= ERROR_RESPONSE) {
			String server = conn.getURL().toString();
			int statusCode = conn.getResponseCode();
			in = new BufferedInputStream(conn.getErrorStream());
			StringBuilder sb = new StringBuilder();
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(reader);

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			throw new IOException(
					String.format("Server (%s) Responded with %d: %s", server, statusCode, sb.toString()));
		}
		else {
			in = new BufferedInputStream(conn.getInputStream());
			StringBuilder sb = new StringBuilder();
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(reader);

			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			br.close();
			reader.close();
			conn.disconnect();
			return sb.toString();
		}
	}

	protected HttpURLConnection initializeConnection(URL url)
			throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.addRequestProperty("X-Experience-API-Version", XAPI_VERSION);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", this.authString);
		return conn;
	}
	
	protected HttpURLConnection initializeConnectionForAttachments(URL url, String boundary)
            throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.addRequestProperty("X-Experience-API-Version", XAPI_VERSION);
        conn.setRequestProperty("Content-Type", "multipart/mixed; boundary=" + boundary);
        conn.setRequestProperty("Authorization", this.authString);
        conn.setUseCaches(false);
        return conn;
    }

	protected String issuePost(String path, String data)
			throws java.io.IOException {
        URL url = new URL(this._host.getProtocol(), this._host.getHost(), this._host.getPort(), this._host.getPath()+path);
		HttpURLConnection conn = initializeConnection(url);
		conn.setRequestMethod("POST");
		OutputStreamWriter writer = new OutputStreamWriter(
				conn.getOutputStream());
		try {
			writer.write(data);
		} catch (IOException ex) {
			InputStream s = conn.getErrorStream();
			InputStreamReader isr = new InputStreamReader(s);
			BufferedReader br = new BufferedReader(isr);
			try {
				String line;
				while((line = br.readLine()) != null){
					System.out.print(line);
				}
				System.out.println();
			} finally {
				s.close();
			}
			throw ex;
		} finally {
			writer.close();
		}
		try {
			return readFromConnection(conn);
		} finally {
			conn.disconnect();
		}
	}

    protected String issuePostWithFileAttachment(String path, String data, String contentType, ArrayList<byte[]> attachmentData)
            throws java.io.IOException, NoSuchAlgorithmException {
        String boundary = "===" + System.currentTimeMillis() + "===";
        URL url = new URL(this._host.getProtocol(), this._host.getHost(),this._host.getPort(), this._host.getPath()+path);
 
        HttpURLConnection conn = initializeConnectionForAttachments(url, boundary);
        conn.setRequestMethod("POST");
        OutputStreamWriter writer = new OutputStreamWriter(
				conn.getOutputStream(), "UTF-8");

        try {
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Type:application/json").append(LINE_FEED).append(LINE_FEED);
            writer.append(data).append(LINE_FEED);
            writer.append("--" + boundary).append(LINE_FEED);
            for(byte[] ba: attachmentData){
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(ba);
                String sha256String = new String(Hex.encode(md.digest()));
                writer.append("Content-Type:" + contentType).append(LINE_FEED);
                writer.append("Content-Transfer-Encoding:binary").append(LINE_FEED);
                writer.append("X-Experience-API-Hash:" + sha256String).append(LINE_FEED).append(LINE_FEED);
                writer.append(new String(ba)).append(LINE_FEED);
                writer.append("--" + boundary + "--");
            }
            writer.flush();
        } catch (IOException ex) {
            InputStream s = conn.getErrorStream();
            InputStreamReader isr = new InputStreamReader(s);
            BufferedReader br = new BufferedReader(isr);
            try {
                String line;
                while((line = br.readLine()) != null){
                    System.out.print(line);
                }
                System.out.println();
            } finally {
                s.close();
            }
            throw ex;
        } finally {
            writer.close();
        }
        try {
            return readFromConnection(conn);
        } finally {
            conn.disconnect();
        }
    }

    protected String issuePut(String path, String data)
            throws java.io.IOException {
        URL url = new URL(this._host.getProtocol(), this._host.getHost(), this._host.getPort(), this._host.getPath()+path);
        HttpURLConnection conn = initializeConnection(url);
        conn.setRequestMethod("PUT");
        OutputStreamWriter writer = new OutputStreamWriter(
                conn.getOutputStream());
        try {
            writer.write(data);
        } catch (IOException ex) {
            InputStream s = conn.getErrorStream();
            InputStreamReader isr = new InputStreamReader(s);
            BufferedReader br = new BufferedReader(isr);
            try {
                String line;
                while((line = br.readLine()) != null){
                    System.out.print(line);
                }
                System.out.println();
            } finally {
                s.close();
            }
            throw ex;
        } finally {
            writer.close();
        }
        try {
            return readFromConnection(conn);
        } finally {
            conn.disconnect();
        }
    }
    
    protected String issuePutWithFileAttachment(String path, String data, String contentType, ArrayList<byte[]> attachmentData)
            throws java.io.IOException, NoSuchAlgorithmException {
        String boundary = "===" + System.currentTimeMillis() + "===";
        URL url = new URL(this._host.getProtocol(), this._host.getHost(),this._host.getPort(), this._host.getPath()+path);
 
        HttpURLConnection conn = initializeConnectionForAttachments(url, boundary);
        conn.setRequestMethod("PUT");
        OutputStreamWriter writer = new OutputStreamWriter(
				conn.getOutputStream(), "UTF-8");

        try {
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Type:application/json").append(LINE_FEED).append(LINE_FEED);
            writer.append(data).append(LINE_FEED);
            writer.append("--" + boundary).append(LINE_FEED);
            for(byte[] ba: attachmentData){
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(ba);
                String sha256String = new String(Hex.encode(md.digest()));
                writer.append("Content-Type:" + contentType).append(LINE_FEED);
                writer.append("Content-Transfer-Encoding:binary").append(LINE_FEED);
                writer.append("X-Experience-API-Hash:" + sha256String).append(LINE_FEED).append(LINE_FEED);
                writer.append(new String(ba)).append(LINE_FEED);
                writer.append("--" + boundary + "--");
            }
            writer.flush();
        } catch (IOException ex) {
            InputStream s = conn.getErrorStream();
            InputStreamReader isr = new InputStreamReader(s);
            BufferedReader br = new BufferedReader(isr);
            try {
                String line;
                while((line = br.readLine()) != null){
                    System.out.print(line);
                }
                System.out.println();
            } finally {
                s.close();
            }
            throw ex;
        } finally {
            writer.close();
        }
        try {
//        	System.out.println(readFromConnection(conn));
            return readFromConnection(conn);
        } finally {
            conn.disconnect();
        }
    }

    protected String issueDelete(String path)
            throws java.io.IOException {
        URL url = new URL(this._host.getProtocol(), this._host.getHost(), this._host.getPort(), this._host.getPath()+path);
        HttpURLConnection conn = initializeConnection(url);
        conn.setRequestMethod("DELETE");
        try{
            return readFromConnection(conn);
        }
        catch (IOException ex){
            InputStream s = conn.getErrorStream();
            InputStreamReader isr = new InputStreamReader(s);
            BufferedReader br = new BufferedReader(isr);
            try {
                String line;
                while((line = br.readLine()) != null){
                    System.out.print(line);
                }
                System.out.println();
            } finally {
                s.close();
            }
            throw ex;
        }
        finally{
            conn.disconnect();
        }
    }

	protected String issueGet(String path) throws java.io.IOException {
        String fixedPath = checkPath(path);
		URL url = new URL(this._host.getProtocol(), this._host.getHost(), this._host.getPort(), this._host.getPath()+fixedPath);
        HttpURLConnection conn = initializeConnection(url);
        conn.setRequestMethod("GET");
        
        try {
			return readFromConnection(conn);
		} catch (IOException ex) {
			InputStream s = conn.getErrorStream();
			InputStreamReader isr = new InputStreamReader(s);
			BufferedReader br = new BufferedReader(isr);
			try {
				String line;
				while((line = br.readLine()) != null){
					System.out.print(line);
				}
			} finally {
				s.close();
			}
			throw ex;
		}finally {
			conn.disconnect();
		}
	}

	protected String issueGetWithAttachments(String path) throws java.io.IOException {
		String fixedPath = checkPath(path);
		URL url = new URL(this._host.getProtocol(), this._host.getHost(), this._host.getPort(),
				this._host.getPath() + fixedPath);

		HttpURLConnection conn = initializeConnection(url);
		conn.setRequestMethod("GET");
		InputStream in = new BufferedInputStream(conn.getInputStream());

		String line;
		String response;

		if (conn.getResponseCode() >= ERROR_RESPONSE) {
			String server = conn.getURL().toString();
			int statusCode = conn.getResponseCode();
			in = new BufferedInputStream(conn.getErrorStream());
			StringBuilder sb = new StringBuilder();
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(reader);
			
			while ((line = br.readLine()) != null) {
				sb.append(line);

			}
			br.close();
			reader.close();
			throw new IOException(
					String.format("Server (%s) Responded with %d: %s", server, statusCode, sb.toString()));

		} else {
			// Create parsable multipart/mixed string.
			 response = IOUtils.toString(in, "UTF-8"); 
		}
		
		conn.disconnect();
		return response;
	}
    
    // When retrieving 'more' statements, LRS will return full path..the client will have part in the URI already so cut that off
    protected String checkPath(String path){
        if (path.toLowerCase().contains("statements") && path.toLowerCase().contains("more")){
            int pathLength = this._host.getPath().length();
            return path.substring(pathLength, path.length());
        }
        return path;
    }
}
