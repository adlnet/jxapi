package gov.adlnet.xapi.model;
import com.google.gson.*;
public class Parent {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Definition getDefinition() {
		return definition;
	}
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}
	private String id;
	private Definition definition;
	public JsonElement seriailze(){
		JsonObject obj = new JsonObject();
		obj.addProperty("id", this.id);
		obj.add("definition", this.definition.serialize());
		return obj;
	}
}
