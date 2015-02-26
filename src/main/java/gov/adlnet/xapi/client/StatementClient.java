package gov.adlnet.xapi.client;

import gov.adlnet.xapi.model.Actor;
import gov.adlnet.xapi.model.Statement;
import gov.adlnet.xapi.model.StatementResult;
import gov.adlnet.xapi.model.Verb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.bouncycastle.util.encoders.Hex;

public class StatementClient extends BaseClient {
	private TreeMap<String, String> filters;
    private static final String LINE_FEED = "\r\n";

	public StatementClient(String uri, String user, String password)
			throws java.net.MalformedURLException {
		super(new URL(uri), user, password);
	}

	public StatementClient(URL uri, String user, String password)
			throws MalformedURLException {
		super(uri, user, password);
	}

    protected HttpURLConnection initializeConnectionForAttachments(URL url, String boundary)
            throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.addRequestProperty("X-Experience-API-Version", "1.0.0");
        conn.setRequestProperty("Content-Type", "multipart/mixed; boundary=" + boundary);
        conn.setRequestProperty("Authorization", this.authString);
        conn.setUseCaches(false);
        return conn;
    }

    protected HttpURLConnection initializePOSTConnectionForAttachments(URL url, String boundary)
            throws IOException {
        HttpURLConnection conn = initializeConnectionForAttachments(url, boundary);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        return conn;
    }

    protected String issuePostWithFileAttachment(String path, String data, String contentType, ArrayList<byte[]> attachmentData)
            throws java.io.IOException, NoSuchAlgorithmException {
        String boundary = "===" + System.currentTimeMillis() + "===";
        URL url = new URL(this._host.getProtocol(), this._host.getHost(),this._host.getPort() ,path);
        HttpURLConnection conn = initializePOSTConnectionForAttachments(url, boundary);
        OutputStreamWriter writer = new OutputStreamWriter(
                conn.getOutputStream());
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
                writer.append(ba.toString()).append(LINE_FEED);
                writer.append("--" + boundary + "--");
            }
            writer.flush();
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

	public String postStatement(Statement statement)
			throws java.io.UnsupportedEncodingException, java.io.IOException {
		Gson gson = getDecoder();
		String json = gson.toJson(statement);
		String result = issuePost("/xapi/statements", json);
		JsonArray jsonResult = gson.fromJson(result, JsonArray.class);
		return jsonResult.get(0).getAsString();
	}

    public Boolean putStatement(Statement statement, String stmtId)
            throws java.io.UnsupportedEncodingException, java.io.IOException {
        Gson gson = getDecoder();
        String json = gson.toJson(statement);
        String result = issuePut("/xapi/statements?statementId=" + stmtId, json);
        return result.isEmpty();
    }

    public String postStatementWithAttachment(Statement statement, String contentType, ArrayList<byte[]> attachmentData)
            throws UnsupportedEncodingException, IOException, NoSuchAlgorithmException{
        Gson gson = getDecoder();
        String json = gson.toJson(statement);
        String result = issuePostWithFileAttachment("/xapi/statements", json, contentType, attachmentData);
        JsonArray jsonResult = gson.fromJson(result, JsonArray.class);
        return jsonResult.get(0).getAsString();
    }


	public StatementResult getStatements(String more)
			throws java.io.IOException {
		String result = this.issueGet(more);
		return this.getDecoder().fromJson(result, StatementResult.class);
	}

	public StatementResult getStatements() throws java.io.IOException {
		StringBuilder query = new StringBuilder();
		query.append("/xapi/statements");
		if (this.filters != null && !this.filters.isEmpty()) {
			query.append("?");
			for (Entry<String, String> item : this.filters.entrySet()) {
				query.append(item.getKey());
				query.append("=");
				query.append(item.getValue());
				query.append("&");
			}
			query.deleteCharAt(query.length() - 1);
			this.filters.clear();
		}
		String result = this.issueGet(query.toString());
		return this.getDecoder().fromJson(result, StatementResult.class);
	}

    public String getStatementsWithAttachments() throws java.io.IOException {
        StringBuilder query = new StringBuilder();
        query.append("/xapi/statements");
        if (this.filters != null && !this.filters.isEmpty()) {
            query.append("?");
            for (Entry<String, String> item : this.filters.entrySet()) {
                query.append(item.getKey());
                query.append("=");
                query.append(item.getValue());
                query.append("&");
            }
            query.deleteCharAt(query.length() - 1);
            this.filters.clear();
        }
        if (query.toString().contains("?")){
            query.append("&attachments=true");
        }
        else{
            query.append("?attachments=true");
        }
        String result = this.issueGet(query.toString());
        return result;
    }

    public Statement get(String statementId) throws java.io.IOException {
		String result = this.issueGet("/xapi/statements?statementId="
				+ statementId);
		return this.getDecoder().fromJson(result, Statement.class);
	}

	public Statement getVoided(String statementId) throws java.io.IOException {
		String result = this.issueGet("/xapi/statements?voidedStatementId="
				+ statementId);
		return this.getDecoder().fromJson(result, Statement.class);
	}

	private StatementClient addFilter(String key, String value) {
		try {
			StatementClient client = new StatementClient(this._host,
					this.username, this.password);
			if (client.filters == null) {
				client.filters = new TreeMap<String, String>();
			}
			if (this.filters != null) {
				for (Entry<String, String> filter : filters.entrySet()) {
					client.filters.put(filter.getKey(), filter.getValue());
				}
			}
			client.filters.put(key, value);
			return client;
		} catch (MalformedURLException ex) {
			return null;
		}
	}

	public StatementClient filterByVerb(Verb v) throws UnsupportedEncodingException {
		return addFilter("verb", URLEncoder.encode(v.getId(), "UTF-8"));
	}

	public StatementClient filterByVerb(String verbId) {
		return addFilter("verb", verbId);
	}

	public StatementClient filterByActor(Actor a) throws UnsupportedEncodingException{
		return addFilter("agent", URLEncoder.encode(getDecoder().toJson(a.serialize()), "UTF-8"));
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

	public StatementClient filterBySince(String timestamp) {
		return addFilter("since", timestamp);
	}

    public StatementClient limitResults(int limit){
        return addFilter("limit", Integer.toString(limit));
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
