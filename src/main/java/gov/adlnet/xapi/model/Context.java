package gov.adlnet.xapi.model;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Context {
	private String registration;
	private String revision;
	private String platform;
	private String language;

	private Actor instructor;
	private Group team;
	private StatementReference statement;
	private ContextActivities contextActivities;

	private HashMap<String, JsonElement> extensions;

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		if (this.registration != null) {
			obj.addProperty("registration", this.registration);
		}
		if (this.revision != null) {
			obj.addProperty("revision", this.revision);
		}
		if (this.platform != null) {
			obj.addProperty("platform", this.platform);
		}
		if (this.language != null) {
			obj.addProperty("language", this.language);
		}
		if (this.instructor != null) {
			obj.add("instructor", instructor.serialize());
		}
		if (this.team != null) {
			obj.add("team", this.team.serialize());
		}
		if (this.extensions != null) {
			JsonObject extensionsObj = new JsonObject();
			obj.add("extensions", extensionsObj);
			for (Entry<String, JsonElement> item : extensions.entrySet()) {
				extensionsObj.add(item.getKey(), item.getValue());
			}
		}
		if (this.statement != null) {
			obj.add("statement", statement.serialize());
		}
		if (this.contextActivities != null) {
			obj.add("contextActivities", contextActivities.serialize());
		}
		return obj;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public Actor getInstructor() {
		return instructor;
	}

	public void setInstructor(Actor instructor) {
		this.instructor = instructor;
	}

	public Group getTeam() {
		return team;
	}

	public void setTeam(Group team) {
		this.team = team;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public StatementReference getStatement() {
		return statement;
	}

	public void setStatement(StatementReference statement) {
		this.statement = statement;
	}

	public HashMap<String, JsonElement> getExtensions() {
		return extensions;
	}

	public void setExtensions(HashMap<String, JsonElement> extensions) {
		this.extensions = extensions;
	}
}
