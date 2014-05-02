package gov.adlnet.xapi.client;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.nio.charset.Charset;

import com.google.gson.*;

import java.io.*;

import gov.adlnet.xapi.model.*;
import gov.adlnet.xapi.model.adapters.ActorAdapter;
import gov.adlnet.xapi.model.adapters.StatenentObjectAdapter;

public class StatementClient {
	private TreeMap<String, String> filters;
	private Gson gson;
	private URL _host;

	public StatementClient(String uri, String user, String password)
			throws java.net.MalformedURLException {
		init(new URL(uri), user, password);
	}

	public StatementClient(URL uri, String user, String password) {
		init(uri, user, password);
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

	public String publishStatement(Statement statement)
			throws java.io.UnsupportedEncodingException, java.io.IOException {
		Gson gson = getDecoder();
		String json = gson.toJson(statement);
		URL url = new URL(this._host.getProtocol(), this._host.getHost(),
				"/xapi/statements");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.addRequestProperty("X-Experience-API-Version", "1.0");
		conn.setRequestProperty("Content-Type",
				"application/json; charset=utf8");
		conn.setRequestMethod("POST");
		OutputStreamWriter writer = new OutputStreamWriter(
				conn.getOutputStream());
		try {
			writer.write(json);
		} finally {
			writer.close();
		}

		String result = readFromConnection(conn);
		JsonArray jsonResult = gson.fromJson(result, JsonArray.class);
		return jsonResult.get(0).getAsString();
	}

	private String readFromConnection(HttpURLConnection conn)
			throws java.io.IOException {
		InputStream in = new BufferedInputStream(conn.getInputStream());
		StringBuilder sb = new StringBuilder();
		InputStreamReader reader = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}

	private String issueRequest(String path) throws java.io.IOException,
			java.net.MalformedURLException {
		URL url = new URL(this._host.getProtocol(), this._host.getHost(), path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.addRequestProperty("X-Experience-API-Version", "1.0");
		try {
			return readFromConnection(conn);
		} finally {
			conn.disconnect();
		}

	}

	public StatementResult getStatements(String more)
			throws java.io.IOException {
		String result = this.issueRequest(more);
		return this.getDecoder().fromJson(result, StatementResult.class);
	}

	public StatementResult getStatements() throws java.io.IOException {
		StringBuilder query = new StringBuilder();
		query.append("/xapi/statements");
		if (this.filters != null && !this.filters.isEmpty()) {
			query.append("?");
			for(Entry<String, String> item: this.filters.entrySet()){
				query.append(item.getKey());
				query.append("=");
				query.append(item.getValue());
				query.append("&");
			}
			this.filters.clear();
		}
		String result = this.issueRequest(query.toString());
		return this.getDecoder().fromJson(result, StatementResult.class);
	}

	public Statement get(String statementId) throws java.io.IOException {
		String result = this.issueRequest("/xapi/statements?statementId="
				+ statementId);
		return this.getDecoder().fromJson(result, Statement.class);
	}

	public Statement getVoided(String statementId) throws java.io.IOException {
		String result = this.issueRequest("/xapi/statements?voidedStatementId="
				+ statementId);
		return this.getDecoder().fromJson(result, Statement.class);
	}

	private StatementClient addFilter(String key, String value) {
		if (filters == null) {
			filters = new TreeMap<String, String>();
		}
		filters.put(key, value);
		return this;
	}

	public StatementClient filterByVerb(Verb v) {
		return addFilter("verb", v.getId());
	}

	public StatementClient filterByVerb(String verbId) {
		return addFilter("verb", verbId);
	}

	public StatementClient filterByActor(Actor a) {
		return addFilter("agent", getDecoder().toJson(a));
	}

	public StatementClient filterByActivity(String activityId) {
		return addFilter("activity", activityId);
	}

	public StatementClient filterByRegistration(String registrationId) {
		return addFilter("registration", registrationId);
	}

	public StatementClient includeRelatedActivities(boolean include) {
		if (include)
			return addFilter("related_activities", "true");
		else
			return addFilter("related_activities", "false");
	}

	public StatementClient includeRelatedAgents(boolean include) {
		if (include)
			return addFilter("related_agents", "true");
		else
			return addFilter("related_agents", "false");
	}

	public StatementClient includeAttachments(boolean include) {
		if (include)
			return addFilter("attachments", "true");
		else
			return addFilter("attachments", "false");
	}

	public StatementClient filterBySince(String timestamp) {
		return addFilter("since", timestamp);
	}

	public StatementClient filterByUntil(String timestamp) {
		return addFilter("until", timestamp);
	}

	public StatementClient exact() {
		return addFilter("format", "exact");
	}

	public StatementClient ids() {
		return addFilter("format", "ids");
	}

	public StatementClient canonical() {
		return addFilter("format", "canonical");
	}

	public StatementClient ascending(boolean include) {
		if (include)
			return addFilter("ascending", "true");
		else
			return addFilter("ascending", "false");
	}
}
