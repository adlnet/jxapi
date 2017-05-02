package gov.adlnet.xapi.client;

import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.ActivityProfile;
import gov.adlnet.xapi.model.ActivityState;
import gov.adlnet.xapi.model.Agent;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ActivityClient extends BaseClient {

    private String issueProfilePost(String path, String data, HashMap<String, String> etag)
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

    private String issueProfilePut(String path, String data, HashMap<String, String> etag)
            throws java.io.IOException {
    	URL url = new URL(this._host.getProtocol(), this._host.getHost(),this._host.getPort(), this._host.getPath()+path);
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

    private String issueProfileDelete(String path, String ifMatchEtagValue)
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

	public ActivityClient(String uri, String username, String password)
			throws MalformedURLException {
		super(uri, username, password);
	}

	public ActivityClient(URL uri, String username, String password)
			throws MalformedURLException {
		super(uri, username, password);
	}
	
	public ActivityClient(String uri, String encodedUsernamePassword)
			throws MalformedURLException {
		super(uri, encodedUsernamePassword);
	}
	
	public ActivityClient(URL uri, String encodedUsernamePassword)
			throws MalformedURLException {
		super(uri, encodedUsernamePassword);
	}

	public Activity getActivity(String activityId)
			throws IOException {
		String path = "/activities?activityId=" + activityId;
		String result = issueGet(path);
		return getDecoder().fromJson(result, Activity.class);
	}

    private String createProfilePath(ActivityProfile activityProfile){
        StringBuilder sb = new StringBuilder();
        sb.append("/activities/profile?activityId=");
        sb.append(activityProfile.getActivityId());
        sb.append("&profileId=");
        sb.append(activityProfile.getProfileId());
        return sb.toString();
    }

    public JsonObject getActivityProfile(ActivityProfile activityProfile)
            throws IOException{
        String result = issueGet(createProfilePath(activityProfile));
        return getDecoder().fromJson(result, JsonObject.class);
    }

	public boolean postActivityProfile(ActivityProfile activityProfile, HashMap<String, String> etag)
            throws IOException {
		String json = getDecoder().toJson(activityProfile.getProfile());
		String response = issueProfilePost(createProfilePath(activityProfile), json, etag);
		return response.isEmpty();
	}

    public boolean putActivityProfile(ActivityProfile activityProfile, HashMap<String, String> etag)
            throws IOException {
        String json = getDecoder().toJson(activityProfile.getProfile());
        String response = issueProfilePut(createProfilePath(activityProfile), json, etag);
        return response.isEmpty();
    }

    public boolean deleteActivityProfile(ActivityProfile activityProfile, String ifMatchEtagValue)
            throws IOException {
        String response = issueProfileDelete(createProfilePath(activityProfile), ifMatchEtagValue);
        return response.isEmpty();
    }

    public JsonArray getActivityProfiles(String activityId, String since) throws IOException {
        String path = "/activities/profile?activityId=" + activityId;
        if (since != null && since.length() > 0){
            path += ("&since=" + since);
        }
        String result = issueGet(path);
        return getDecoder().fromJson(result, JsonArray.class);
    }

    private String createStatePath(ActivityState activityState){
        StringBuilder sb = new StringBuilder();
        sb.append("/activities/state?activityId=");
        sb.append(activityState.getActivityId());
        sb.append("&stateId=");
        sb.append(activityState.getStateId());
        sb.append("&agent=");
        sb.append(getDecoder().toJson(activityState.getAgent()));
        UUID reg = activityState.getRegistration();
        if (reg != null){
            sb.append("&registration=" + reg.toString());
        }
        return sb.toString();
    }

    public JsonObject getActivityState(ActivityState activityState)
            throws IOException{
        String result = issueGet(createStatePath(activityState));
        return getDecoder().fromJson(result, JsonObject.class);
    }

    public boolean postActivityState(ActivityState activityState)
            throws IOException{
        String json = getDecoder().toJson(activityState.getState());
        String result = issuePost(createStatePath(activityState), json);
        return result.isEmpty();
    }

    public boolean putActivityState(ActivityState activityState)
            throws IOException{
        String json = getDecoder().toJson(activityState.getState());
        String result = issuePut(createStatePath(activityState), json);
        return result.isEmpty();
    }

    public boolean deleteActivityState(ActivityState activityState)
            throws IOException{
        String result = issueDelete(createStatePath(activityState));
        return result.isEmpty();
    }

    public JsonArray getActivityStates(String activityId, Agent agent, String registration, String since)
            throws IOException{
        String path = String.format("/activities/state?activityId=%s&agent=%s", activityId,
                getDecoder().toJson(agent.serialize()));
        if (registration != null && registration.length() > 0){
            path += ("&registration=" + registration);
        }
        if (since != null && since.length() > 0){
            path += ("&since=" + since);
        }
        String result = issueGet(path);
        return getDecoder().fromJson(result, JsonArray.class);
    }

    public boolean deleteActivityStates(String activityId, Agent agent, String registration)
            throws IOException{
        String path = String.format("/activities/state?activityId=%s&agent=%s", activityId,
                getDecoder().toJson(agent.serialize()));
        if (registration != null && registration.length() > 0){
            path += ("&registration=" + registration);
        }
        String result = issueDelete(path);
        return result.isEmpty();
    }
}
