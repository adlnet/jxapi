package gov.adlnet.xapi.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class StatementReference implements IStatementObject {
	public static final String STATEMENT_REFERENCE = "StatementRef";
	private String id;

    public StatementReference(){}

    public StatementReference(String id){
        this.id = id;
    }

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
