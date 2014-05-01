package gov.adlnet.xapi.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.nio.charset.Charset;
import com.google.gson.*;

import java.io.*;

import org.apache.http.*;
import org.apache.http.client.protocol.*;
import org.apache.http.impl.auth.*;
import org.apache.http.client.*;
import org.apache.http.impl.client.*;

import gov.adlnet.xapi.model.*;
import gov.adlnet.xapi.model.adapters.ActorAdapter;
import gov.adlnet.xapi.model.adapters.StatenentObjectAdapter;

import org.apache.http.client.methods.*;
import org.apache.http.auth.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

public class StatementClient {
	private HttpClient _client = HttpClients.createDefault();
	private HttpClientContext _context;
	private HttpHost _host;
	private ArrayList<BasicNameValuePair> filters;
	private Gson gson;

	public StatementClient(String uri, String user, String password)
			throws URISyntaxException {
		init(new URI(uri), user, password);
	}

	public StatementClient(URI uri, String user, String password) {
		init(uri, user, password);
	}

	private void init(URI uri, String user, String password) {
		this._host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(this._host.getHostName(),
				this._host.getPort()), new UsernamePasswordCredentials(user,
				password));

		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(_host, basicAuth);

		// Add AuthCache to the execution context
		this._context = HttpClientContext.create();
		this._context.setCredentialsProvider(credsProvider);
		this._context.setAuthCache(authCache);
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

		HttpPost post = new HttpPost("/xapi/statements");
		Gson gson = getDecoder();
		String json = gson.toJson(statement);
		post.setEntity(new StringEntity(json));
		post.addHeader("X-Experience-API-Version", "1.0");
		HttpResponse resp = this._client.execute(this._host, post,
				this._context);
		HttpEntity entity = resp.getEntity();
		InputStreamReader in = new InputStreamReader(entity.getContent());
		BufferedReader reader = new BufferedReader(in);
		try {
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			if (resp.getStatusLine().getStatusCode() < 200
					|| resp.getStatusLine().getStatusCode() > 205) {
				throw new IllegalArgumentException(sb.toString());
			} else {
				JsonArray result = gson
						.fromJson(sb.toString(), JsonArray.class);
				return result.get(0).getAsString();
			}
		} finally {
			in.close();
			reader.close();
		}
	}

	private String issueRequest(String path) throws java.io.IOException {
		HttpGet method = new HttpGet(path);
		method.addHeader("X-Experience-API-Version", "1.0");
		HttpResponse resp = this._client.execute(this._host, method,
				this._context);
		HttpEntity entity = resp.getEntity();
		InputStreamReader in = new InputStreamReader(entity.getContent());
		BufferedReader reader = new BufferedReader(in);
		try {
			StringBuilder sb = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} finally {
			in.close();
			reader.close();
		}
	}

	public StatementResult getStatements(String more)
			throws java.io.IOException {
		String result = this.issueRequest(more);
		return this.getDecoder().fromJson(result, StatementResult.class);
	}

	public StatementResult getStatements() throws java.io.IOException {
		String baseQuery = "/xapi/statements";
		if (this.filters != null && !this.filters.isEmpty()) {
			baseQuery = baseQuery + "?"
					+ URLEncodedUtils.format(filters, Charset.forName("utf-8"));
		}
		String result = this.issueRequest(baseQuery);
		StatementResult r2 = this.getDecoder().fromJson(result, StatementResult.class);
		return r2; 
	}

	public Statement get(String statementId) throws java.io.IOException {
		String result = this.issueRequest("/xapi/statements?statementId="
				+ statementId);
		return this.getDecoder().fromJson(result, Statement.class);
	}

	private StatementClient addFilter(String key, String value) {
		if (filters == null) {
			filters = new ArrayList<BasicNameValuePair>();
		}
		filters.add(new BasicNameValuePair(key, value));
		return this;
	}

	public StatementClient filterByVerb(Verb v) {
		return addFilter("verb", v.getId());
	}

	public StatementClient filterByActor(Actor a) {
		return addFilter("agent", getDecoder().toJson(a));
	}

}
