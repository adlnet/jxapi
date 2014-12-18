package gov.adlnet.xapi.client;

import gov.adlnet.xapi.model.Actor;
import gov.adlnet.xapi.model.IStatementObject;
import gov.adlnet.xapi.model.adapters.ActorAdapter;
import gov.adlnet.xapi.model.adapters.StatementObjectAdapter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//import android.util.Base64;
import javax.xml.bind.DatatypeConverter;
//import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseClient {
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

	protected void init(URL uri, String user, String password) {
		this._host = uri;
		this.username = user;
		this.password = password;
//        this.authString = "Basic " + Base64.encodeToString((this.username + ":" + this.password).getBytes(), Base64.DEFAULT);
        this.authString = "Basic " + DatatypeConverter.printBase64Binary((this.username + ":" + this.password).getBytes());
//        this.authString = "Basic " + Base64.encodeBase64((this.username + ":" + this.password).getBytes()).toString();
    }

	protected String readFromConnection(HttpURLConnection conn)
			throws java.io.IOException {
		InputStream in = new BufferedInputStream(conn.getInputStream());
		StringBuilder sb = new StringBuilder();
		InputStreamReader reader = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(reader);
		try {
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} finally {
			br.close();
			reader.close();
		}
	}

	protected HttpURLConnection initializeConnection(URL url)
			throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.addRequestProperty("X-Experience-API-Version", "1.0");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", this.authString);
		return conn;
	}
	
	protected HttpURLConnection initializePOSTConnection(URL url)
			throws IOException {
		HttpURLConnection conn = initializeConnection(url);
		conn.setDoOutput(true);
		return conn;
	}

	protected String issuePost(String path, String data)
			throws java.io.IOException {
		URL url = new URL(this._host.getProtocol(), this._host.getHost(), path);
		HttpURLConnection conn = initializePOSTConnection(url);
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
				String line = "";
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
        URL url = new URL(this._host.getProtocol(), this._host.getHost(), path);
        HttpURLConnection conn = initializePOSTConnection(url);
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
                String line = "";
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

    protected String issueDelete(String path)
            throws java.io.IOException {
        URL url = new URL(this._host.getProtocol(), this._host.getHost(), path);
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
                String line = "";
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

	protected String issueGet(String path) throws java.io.IOException,
			java.net.MalformedURLException {
		URL url = new URL(this._host.getProtocol(), this._host.getHost(), path);
		HttpURLConnection conn = initializeConnection(url);
		try {
			return readFromConnection(conn);
		} catch (IOException ex) {
			InputStream s = conn.getErrorStream();
			InputStreamReader isr = new InputStreamReader(s);
			BufferedReader br = new BufferedReader(isr);
			try {
				String line = "";
				while((line = br.readLine()) != null){
					System.out.print(line);
				}
				System.out.println();
			} finally {
				s.close();
			}
			throw ex;
		}finally {
			conn.disconnect();
		}
	}
}
