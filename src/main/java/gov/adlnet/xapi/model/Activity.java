package gov.adlnet.xapi.model;

import java.net.URI;
import com.google.gson.*;

public class Activity implements IStatementObject {
	public static final String ACTIVITY = "Activity";

	private String id;

	private ActivityDefinition definition;

	public String getObjectType() {
		return ACTIVITY;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ActivityDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(ActivityDefinition definition) {
		this.definition = definition;
	}

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		if (this.id != null) {
			obj.addProperty("id", this.id);
		}
		if (this.definition != null) {
			obj.add("definition", this.definition.serialize());
		}
		return obj;
	}
}
