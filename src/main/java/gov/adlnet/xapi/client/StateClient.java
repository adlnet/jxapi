package gov.adlnet.xapi.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.model.Actor;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.IStatementObject;
import gov.adlnet.xapi.model.State;
import gov.adlnet.xapi.model.adapters.ActorAdapter;
import gov.adlnet.xapi.model.adapters.StatenentObjectAdapter;

public class StateClient extends BaseClient {

	public StateClient(String uri, String username, String password)
			throws MalformedURLException {
		super(new URL(uri), username, password);
	}

	public StateClient(URL uri, String username, String password)
			throws MalformedURLException {
		super(uri, username, password);
	}

	private String createStatePath(String activityId, Agent agent,
			String stateId) {

		StringBuilder path = new StringBuilder();
		path.append("/xapi/activities/state");
		path.append("?");
		path.append("activityId=");
		path.append(activityId);
		path.append("&");
		path.append("agent=");
		path.append(getDecoder().toJson(agent.serialize()));
		path.append("&");
		path.append("stateId=");
		path.append(stateId);
		return path.toString();
	}

	public boolean publishState(String activityId, Agent agent, String stateId,
			JsonObject state) throws IOException {
		Gson gson = getDecoder();
		String json = gson.toJson(state);
		String path = createStatePath(activityId, agent, stateId);
		String result = issuePost(path, json);
		return result != null;
	}

	public JsonObject getState(String activityId, Agent agent, String stateId)
			throws IOException {
		String path = createStatePath(activityId, agent, stateId);
		URL url = new URL(this._host.getProtocol(), this._host.getHost(), path);
		HttpURLConnection conn = initializeConnection(url);
		String response = readFromConnection(conn);
		return getDecoder().fromJson(response, JsonObject.class);
	}

	private String createStatesPath(String activityId, Agent agent,
			String registration, String since) {
		StringBuilder path = new StringBuilder();
		path.append("/xapi/activities/state");
		path.append("?");
		path.append("activityId=");
		path.append(activityId);
		path.append("&");
		path.append("agent=");
		path.append(getDecoder().toJson(agent.serialize()));
		if (registration != null) {
			path.append("&");
			path.append("registration=");
			path.append(registration);
		}
		if (since != null) {
			path.append("&");
			path.append("since=");
			path.append(since);
		}
		return path.toString();
	}

	public JsonArray getStates(String activityId, Agent agent,
			String registration, String since) throws IOException {
		String path = createStatesPath(activityId, agent, registration, since);
		URL url = new URL(this._host.getProtocol(), this._host.getHost(), path);
		HttpURLConnection conn = initializeConnection(url);
		String response = readFromConnection(conn);
		return getDecoder().fromJson(response, JsonArray.class);
	}
}
