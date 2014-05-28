package gov.adlnet.xapi.client;

import gov.adlnet.xapi.model.Activity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

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
	public JsonArray getActivities(String activityId, String since) throws MalformedURLException, IOException {
		String path = "/xapi/activities/profile?activityId=" + activityId;
		if (since != null && since.length() > 0){
			path += ("&since=" + since);
		}
		String result = issueGet(path);
		return getDecoder().fromJson(result, JsonArray.class);		
	}
	private String createProfilePath(String activityId, String profileId){
		StringBuilder sb = new StringBuilder();
		sb.append("/xAPI/activities/profile?activityId=");
		sb.append(activityId);
		sb.append("&profileId=");
		sb.append(profileId);
		return sb.toString();
	}
	public boolean publishActivityProfile(String activityId, String profileId,
			JsonElement obj) throws IOException {
		String json = getDecoder().toJson(obj);
		String response = issuePost(createProfilePath(activityId, profileId),
				json);
		return response != null;
	}
}
