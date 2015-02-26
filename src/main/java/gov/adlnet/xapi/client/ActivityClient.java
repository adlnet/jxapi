package gov.adlnet.xapi.client;

import gov.adlnet.xapi.model.Activity;
import gov.adlnet.xapi.model.Agent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ActivityClient extends BaseClient {

	public ActivityClient(String uri, String username, String password)
			throws MalformedURLException {
		super(uri, username, password);
	}

	public ActivityClient(URL uri, String username, String password)
			throws MalformedURLException {
		super(uri, username, password);
	}

	public Activity getActivity(String activityId)
			throws MalformedURLException, IOException {
		String path = "/xapi/activities?activityId=" + activityId;
		String result = issueGet(path);
		return getDecoder().fromJson(result, Activity.class);
	}

    private String createProfilePath(String activityId, String profileId){
        StringBuilder sb = new StringBuilder();
        sb.append("/xAPI/activities/profile?activityId=");
        sb.append(activityId);
        sb.append("&profileId=");
        sb.append(profileId);
        return sb.toString();
    }

    public JsonObject getActivityProfile(String activityId, String profileId)
            throws MalformedURLException, IOException{
        String result = issueGet(createProfilePath(activityId, profileId));
        return getDecoder().fromJson(result, JsonObject.class);
    }

	public JsonArray getActivityProfiles(String activityId, String since) throws MalformedURLException, IOException {
		String path = "/xapi/activities/profile?activityId=" + activityId;
		if (since != null && since.length() > 0){
			path += ("&since=" + since);
		}
		String result = issueGet(path);
		return getDecoder().fromJson(result, JsonArray.class);		
	}

	public boolean postActivityProfile(String activityId, String profileId,
			JsonElement profile) throws IOException {
		String json = getDecoder().toJson(profile);
		String response = issuePost(createProfilePath(activityId, profileId),
				json);
		return response.isEmpty();
	}

    public boolean putActivityProfile(String activityId, String profileId,
                                       JsonElement profile) throws IOException {
        String json = getDecoder().toJson(profile);
        String response = issuePut(createProfilePath(activityId, profileId),
                json);
        return response.isEmpty();
    }

    public boolean deleteActivityProfile(String activityId, String profileId) throws IOException {
        String response = issueDelete(createProfilePath(activityId, profileId));
        return response.isEmpty();
    }

    private String createStatePath(String activityId, Agent agent, String registration, String stateId){
        String path = String.format("/xapi/activities/state?activityId=%s&agent=%s&stateId=%s", activityId,
                getDecoder().toJson(agent.serialize()), stateId);
        if (registration != null && registration.length() > 0){
            path += ("&registration=" + registration);
        }
        return path;
    }

    public JsonObject getActivityState(String activityId, Agent agent, String registration, String stateId)
            throws MalformedURLException, IOException{
        String result = issueGet(createStatePath(activityId, agent, registration, stateId));
        return getDecoder().fromJson(result, JsonObject.class);
    }

    public boolean postActivityState(String activityId, Agent agent, String registration, String stateId,
                                        JsonElement state)
            throws MalformedURLException, IOException{
        String json = getDecoder().toJson((state));
        String result = issuePost(createStatePath(activityId, agent, registration, stateId), json);
        return result.isEmpty();
    }

    public boolean putActivityState(String activityId, Agent agent, String registration, String stateId,
                                     JsonElement state)
            throws MalformedURLException, IOException{
        String json = getDecoder().toJson((state));
        String result = issuePut(createStatePath(activityId, agent, registration, stateId), json);
        return result.isEmpty();
    }

    public boolean deleteActivityState(String activityId, Agent agent, String registration, String stateId)
            throws MalformedURLException, IOException{
        String result = issueDelete(createStatePath(activityId, agent, registration, stateId));
        return result.isEmpty();
    }

    public JsonArray getActivityStates(String activityId, Agent agent, String registration, String since)
            throws MalformedURLException, IOException{
        String path = String.format("/xapi/activities/state?activityId=%s&agent=%s", activityId,
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
            throws MalformedURLException, IOException{
        String path = String.format("/xapi/activities/state?activityId=%s&agent=%s", activityId,
                getDecoder().toJson(agent.serialize()));
        if (registration != null && registration.length() > 0){
            path += ("&registration=" + registration);
        }
        String result = issueDelete(path);
        return result.isEmpty();
    }
}
