package gov.adlnet.xapi.client;

import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.AgentProfile;
import gov.adlnet.xapi.model.Person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class AgentClient extends BaseClient {

    protected String issueProfilePost(String path, String data, HashMap<String, String> etag)
            throws java.io.IOException {
        URL url = new URL(this._host.getProtocol(), this._host.getHost(), this._host.getPort(), this._host.getPath()+path);
        HttpURLConnection conn = initializePOSTConnection(url);

        // Agent Profile requires either of these headers being sent
        // If neither are sent it will set If-None-Match to null and exception
        // will be caught during request
        if (etag.containsKey("If-Match")){
            conn.addRequestProperty("If-Match", etag.get("If-Match"));
        }
        else{
            conn.addRequestProperty("If-None-Match", etag.get("If-None-Match"));
        }
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

    protected String issueProfilePut(String path, String data, HashMap<String, String> etag)
            throws java.io.IOException {
    	URL url = new URL(this._host.getProtocol(), this._host.getHost(), this._host.getPort(), this._host.getPath()+path);

        HttpURLConnection conn = initializePOSTConnection(url);

        // Agent Profile requires either of these headers being sent
        // If neither are sent it will set If-None-Match to null and exception
        // will be caught during request
        if (etag.containsKey("If-Match")){
            conn.addRequestProperty("If-Match", etag.get("If-Match"));
        }
        else{
            conn.addRequestProperty("If-None-Match", etag.get("If-None-Match"));
        }

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

    protected String issueProfileDelete(String path, String ifMatchEtagValue)
            throws java.io.IOException {
        URL url = new URL(this._host.getProtocol(), this._host.getHost(), this._host.getPort(), this._host.getPath()+path);
        HttpURLConnection conn = initializeConnection(url);
        //Agent profile requires If-Match header - exception will get caught when making
        conn.addRequestProperty("If-Match", ifMatchEtagValue);
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

	public AgentClient(String uri, String username, String password)
			throws MalformedURLException {
		super(uri, username, password);
	}

	public AgentClient(URL uri, String username, String password)
			throws MalformedURLException {
		super(uri, username, password);
	}
	
	public AgentClient(String uri, String encodedUsernamePassword)
			throws MalformedURLException {
		super(uri, encodedUsernamePassword);
	}
	
	public AgentClient(URL uri, String encodedUsernamePassword)
			throws MalformedURLException {
		super(uri, encodedUsernamePassword);
	}

    public Person getPerson(Agent a)
            throws IOException {
        String path = "/agents?agent=" + getDecoder().toJson(a.serialize()); 
        String result = issueGet(path);
        return getDecoder().fromJson(result, Person.class);
    }

    private String formatProfilePath(AgentProfile agentProfile) {
		String agentJson = getDecoder().toJson(agentProfile.getAgent().serialize());
        StringBuilder sb = new StringBuilder();
        sb.append("/agents/profile?agent=");
        sb.append(agentJson);
        sb.append("&profileId=");
        sb.append(agentProfile.getProfileId());
		return sb.toString();
	}

	public JsonElement getAgentProfile(AgentProfile agentProfile)
			throws IOException {
		String path = formatProfilePath(agentProfile);
		String result = issueGet(path);
		return getDecoder().fromJson(result, JsonElement.class);
	}

    public boolean putAgentProfile(AgentProfile agentProfile, HashMap<String, String> etag)
            throws IOException {
        String path = formatProfilePath(agentProfile);
        String json = getDecoder().toJson(agentProfile.getProfile());
        String result = issueProfilePut(path, json, etag);
        return result.isEmpty();
    }

    public boolean postAgentProfile(AgentProfile agentProfile, HashMap<String, String> etag)
            throws IOException {
        String path = formatProfilePath(agentProfile);
        String json = getDecoder().toJson(agentProfile.getProfile());
        String result = issueProfilePost(path, json, etag);
        return result.isEmpty();
    }

    public boolean deleteAgentProfile(AgentProfile agentProfile, String ifMatchEtagValue)
            throws IOException {
        String path = formatProfilePath(agentProfile);
        String result = issueProfileDelete(path, ifMatchEtagValue);
        return result.isEmpty();
    }

    public JsonArray getAgentProfiles(Agent a, String since)
            throws IOException {
        String agentJson = getDecoder().toJson(a.serialize());
        String path = "/agents/profile?agent=" + agentJson;
        if (since != null && since.length() > 0){
            path += ("&since=" + since);
        }
        String result = issueGet(path);
        return getDecoder().fromJson(result, JsonArray.class);
    }
}
