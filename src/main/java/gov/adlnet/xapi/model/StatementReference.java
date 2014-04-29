package gov.adlnet.xapi.model;

import java.util.UUID;

import com.google.gson.*;

public class StatementReference {
	public static final String STATEMENT_REFERENCE = "statementRef";
	private String id;

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		obj.addProperty("objectType", this.getObjectType());
		if (this.id != null) {
			obj.addProperty("id", this.id);
		}
		return obj;
	}

	public String getObjectType() {
		return STATEMENT_REFERENCE;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
