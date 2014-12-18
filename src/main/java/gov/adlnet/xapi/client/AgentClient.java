package gov.adlnet.xapi.client;

import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.Person;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonArray;
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

    public Person getPerson(Agent a)
            throws MalformedURLException, IOException {
        String path = "/xapi/agents?agent=" + getDecoder().toJson(a.serialize());
        String result = issueGet(path);
        return getDecoder().fromJson(result, Person.class);
    }

    private String formatProfilePath(Agent a, String profileId) {
		String agentJson = getDecoder().toJson(a.serialize());
		String path = "/xAPI/agents/profile?agent=" + agentJson + "&profileId="
				+ profileId;
		return path;
	}

	public JsonElement getAgentProfile(Agent a, String profileId)
			throws MalformedURLException, IOException {
		String path = formatProfilePath(a, profileId);
		String result = issueGet(path);
		return getDecoder().fromJson(result, JsonElement.class);
	}

    public boolean putAgentProfile(Agent a, String profileId, JsonElement profile)
            throws MalformedURLException, IOException {
        String path = formatProfilePath(a, profileId);
        String json = getDecoder().toJson((profile));
        String result = issuePut(path, json);
        return result.isEmpty();
    }

    public boolean postAgentProfile(Agent a, String profileId, JsonElement profile)
            throws MalformedURLException, IOException {
        String path = formatProfilePath(a, profileId);
        String json = getDecoder().toJson((profile));
        String result = issuePost(path, json);
        return result.isEmpty();
    }

    public boolean deleteAgentProfile(Agent a, String profileId)
            throws MalformedURLException, IOException {
        String path = formatProfilePath(a, profileId);
        String result = issueDelete(path);
        return result.isEmpty();
    }

    public JsonArray getAgentProfiles(Agent a, String since)
            throws MalformedURLException, IOException {
        String agentJson = getDecoder().toJson(a.serialize());
        String path = "/xAPI/agents/profile?agent=" + agentJson;
        if (since != null && since.length() > 0){
            path += ("&since=" + since);
        }
        String result = issueGet(path);
        return getDecoder().fromJson(result, JsonArray.class);
    }

}
