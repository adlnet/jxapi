package gov.adlnet.xapi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.mail.BodyPart;
import javax.mail.Header;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;

import gov.adlnet.xapi.model.Actor;
import gov.adlnet.xapi.model.Statement;
import gov.adlnet.xapi.model.StatementResult;
import gov.adlnet.xapi.model.Verb;
import gov.adlnet.xapi.util.AttachmentAndType;
import gov.adlnet.xapi.util.AttachmentResult;

public class StatementClient extends BaseClient {
	private TreeMap<String, String> filters;

	public StatementClient(String uri, String user, String password) throws java.net.MalformedURLException {
		super(uri, user, password);
	}

	public StatementClient(URL uri, String user, String password) throws MalformedURLException {
		super(uri, user, password);
	}

	public StatementClient(String uri, String encodedUsernamePassword) throws MalformedURLException {
		super(uri, encodedUsernamePassword);
	}

	public StatementClient(URL uri, String encodedUsernamePassword) throws MalformedURLException {
		super(uri, encodedUsernamePassword);
	}

	public String postStatement(Statement statement) throws java.io.IOException {
		Gson gson = this.getDecoder();
		String json = gson.toJson(statement);
		String result = this.issuePost("/statements", json);
		JsonArray jsonResult = gson.fromJson(result, JsonArray.class);
		return jsonResult.get(0).getAsString();
	}

	public Boolean putStatement(Statement statement, String stmtId) throws java.io.IOException {
		Gson gson = this.getDecoder();
		String json = gson.toJson(statement);
		String result = this.issuePut("/statements?statementId=" + stmtId, json);
		return result.isEmpty();
	}

	public String postStatementWithAttachment(Statement statement, String contentType, ArrayList<byte[]> attachmentData)
			throws IOException, NoSuchAlgorithmException {
		Gson gson = this.getDecoder();
		String json = gson.toJson(statement);
		String result = this.issuePostWithFileAttachment("/statements", json, contentType, attachmentData);
		JsonArray jsonResult = gson.fromJson(result, JsonArray.class);
		return jsonResult.get(0).getAsString();
	}
	
	public Boolean putStatementWithAttachment(Statement statement, String stmtId, String contentType, ArrayList<byte[]> attachmentData)
			throws IOException, NoSuchAlgorithmException {
		Gson gson = this.getDecoder();
		String json = gson.toJson(statement);
		String result = this.issuePutWithFileAttachment("/statements?statementId=" + stmtId, json, contentType, attachmentData);
		return result.isEmpty();
	}

	public StatementResult getStatements(String more) throws java.io.IOException {
		String result = this.issueGet(more);
		return this.getDecoder().fromJson(result, StatementResult.class);
	}

	public StatementResult getStatements() throws java.io.IOException {
		StringBuilder query = new StringBuilder();
		query.append("/statements");
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

	public AttachmentResult getStatementsWithAttachments()
			throws IOException, JsonSyntaxException, NumberFormatException, MessagingException {

		StringBuilder query = new StringBuilder();
		query.append("/statements");
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
		if (query.toString().contains("?")) {
			query.append("&attachments=true");
		} else {
			query.append("?attachments=true");
		}
		String result = this.issueGetWithAttachments(query.toString());
		return parseMultipartString(result);
	}

	public AttachmentResult getStatementWithAttachments(String statementId)
			throws IOException, JsonSyntaxException, NumberFormatException, MessagingException {

		StringBuilder query = new StringBuilder();
		query.append("/statements?statementId=" + statementId);
		query.append("&attachments=true");

		String result = this.issueGetWithAttachments(query.toString());
		return parseMultipartString(result);
	}

	private AttachmentResult parseMultipartString(String responseMessage)
			throws IOException, JsonSyntaxException, NumberFormatException, MessagingException {

		final String STMNTS = "{\"statements\": [";
		final String[] NAME = { "X-Experience-API-Hash" };

		StatementResult statements = null;
		Statement statement = null;
		ArrayList<byte[]> attachment = new ArrayList<byte[]>();

		// Storage for hash, byte[], and content type.
		Map<String, AttachmentAndType> attachments = new HashMap<String, AttachmentAndType>();
		AttachmentResult results = null;

		ByteArrayDataSource ds;
		ds = new ByteArrayDataSource(responseMessage, "multipart/mixed;");

		MimeMultipart multipart = new MimeMultipart(ds);
		BodyPart bodypart = null;

		for (int i = 0; i < multipart.getCount(); i++) {
			bodypart = multipart.getBodyPart(i);

			if (bodypart.isMimeType("application/json")) {
				// Get xAPI JSON statement
				if (bodypart.getContent() instanceof InputStream) {
					InputStream r = (InputStream) bodypart.getContent();
					BufferedReader read = new BufferedReader(new InputStreamReader(r));
					String xapiStmnt = read.readLine();

					if (xapiStmnt.contains(STMNTS)) {
						// multiple statements
						statements = this.getDecoder().fromJson(xapiStmnt, StatementResult.class);
					} else {
						// single statement
						statement = this.getDecoder().fromJson(xapiStmnt, Statement.class);
					}
				} else {
					throw new IOException(String.format("Failed to store JSON."));
				}
			} else if (bodypart.isMimeType("text/plain")) {
				// Handle plain text

				// Get content type
				String type = bodypart.getContentType();

				// get hash of attachment
				Enumeration<Header> e = bodypart.getMatchingHeaders(NAME);
				String hash = null;
				if (e != null && e.hasMoreElements()) {
					hash = e.nextElement().getValue();
				}

				// Get attachment
				byte[] bytes = bodypart.getContent().toString().getBytes("UTF-8");

				attachment.add(bytes);
				attachments.put(hash, new AttachmentAndType(attachment, type));
			} else {
				// Get binary attachment

				// Get content type
				String type = bodypart.getContentType();
				byte[] bytes = null;

				if (bodypart.getContent() instanceof InputStream) {

					InputStream in = (InputStream) bodypart.getInputStream();
					bytes = IOUtils.toByteArray(in);

					// get hash of attachment
					Enumeration<Header> e = bodypart.getMatchingHeaders(NAME);
					String hash = null;
					if (e != null && e.hasMoreElements()) {
						hash = e.nextElement().getValue();
					}
					attachment.add(bytes);
					attachments.put(hash, new AttachmentAndType(attachment, type));
				}
			}
		} // end loop

		if (statements == null) {
			results = new AttachmentResult(responseMessage, statement, attachments);
		} else {
			results = new AttachmentResult(responseMessage, statements, attachments);
		}

		return results;
	}

	public Statement getStatement(String statementId) throws java.io.IOException {
		String result = this.issueGet("/statements?statementId=" + statementId);
		return this.getDecoder().fromJson(result, Statement.class);
	}

	public Statement getVoided(String statementId) throws java.io.IOException {
		String result = this.issueGet("/statements?voidedStatementId=" + statementId);
		return this.getDecoder().fromJson(result, Statement.class);
	}

	public StatementClient addFilter(String key, String value) {
		try {
			StatementClient client = new StatementClient(this._host, this.username, this.password);
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

	public StatementClient filterByActor(Actor a) throws UnsupportedEncodingException {
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

	public StatementClient limitResults(int limit) {
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
