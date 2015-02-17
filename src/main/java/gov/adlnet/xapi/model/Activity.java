package gov.adlnet.xapi.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Activity implements IStatementObject {
	public static final String ACTIVITY = "Activity";

	private String id;
	private ActivityDefinition definition;
	
	public Activity() {}
	
	public Activity(String id) {
	   this.id = id;
	}
	
	public Activity(String id, ActivityDefinition definition) {
	   this.id = id;
	   this.definition = definition;
	}

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
	
	public String toString() {
	   return String.format(
	         "%s", (definition == null || 
	                definition.getName() == null) ? id : definition.toString());
	}
    public String toString(String langMap) {
        return String.format(
                "%s", (definition == null ||
                        definition.getName().get(langMap) == null) ? id : definition.toString(langMap));
    }
}
