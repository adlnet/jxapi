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

public class StateClient {
	private URL _host;
	private Gson gson;

	private Gson getDecoder() {
		if (gson == null) {
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(Actor.class, new ActorAdapter());
			builder.registerTypeAdapter(IStatementObject.class,
					new StatenentObjectAdapter());
			gson = builder.create();
		}
		return gson;
	}

	private void init(URL uri, String user, String password) {
		this._host = uri;
		final String username = user;
		final char[] pwd = password.toCharArray();
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, pwd);

			}
		});
	}

	public StateClient(String uri, String username, String password)
			throws MalformedURLException {
		init(new URL(uri), username, password);
	}

	public StateClient(URL uri, String username, String password)
			throws MalformedURLException {
		init(uri, username, password);
	}

	private String readFromConnection(HttpURLConnection conn)
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

	private HttpURLConnection initializeConnection(URL url) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.addRequestProperty("X-Experience-API-Version", "1.0");
		conn.setRequestProperty("Content-Type", "application/json");
		return conn;
	}

	public boolean publishState(String activityId, Agent agent, String stateId,
			JsonObject state) throws IOException {
		Gson gson = getDecoder();
		String json = gson.toJson(state);
		URL url = new URL(this._host.getProtocol(), this._host.getHost(),
				createStatePath(activityId, agent, stateId));
		HttpURLConnection conn = initializeConnection(url);
		conn.setRequestMethod("POST");
		OutputStreamWriter writer = new OutputStreamWriter(
				conn.getOutputStream());
		try {
			writer.write(json);
			writer.flush();
		} finally {
			writer.close();
		}
		String response = readFromConnection(conn);
		return conn.getResponseCode() == 204;
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
