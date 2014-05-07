package gov.adlnet.xapi.client;

import gov.adlnet.xapi.model.Agent;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonElement;

public class AgentClient extends BaseClient {

	public AgentClient(String uri, String username, String password)
			throws MalformedURLException {
		super(uri, username, password);
	}

	public AgentClient(URL uri, String username, String password)
			throws MalformedURLException {
		super(uri, username, password);
	}

	private String formatProfilePath(Agent a, String profileId) {
		String agentJson = getDecoder().toJson(a.serialize());
		String path = "/xAPI/agents/profile?agent=" + agentJson + "&profileId="
				+ profileId;
		return path;
	}

	public boolean publishAgentProfile(Agent a, String profileId,
			JsonElement profile) throws IOException {
		String requestBody = getDecoder().toJson(profile);
		String path = formatProfilePath(a, profileId);
		String result = issuePost(path, requestBody);
		return result != null;
	}

	public JsonElement getAgentProfile(Agent a, String profileId)
			throws MalformedURLException, IOException {
		String path = formatProfilePath(a, profileId);
		String result = issueGet(path);
		return getDecoder().fromJson(result, JsonElement.class);
	}
}
