package gov.adlnet.xapi.model;

import com.google.gson.*;
import java.util.HashMap;
import java.util.Map.Entry;

public class InteractionComponent {
    private String id;
    private HashMap<String, String> description;
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, String> getDescription() {
		return description;
	}

	public void setDescription(HashMap<String, String> description) {
		this.description = description;
	}

	private JsonElement serializeMap(HashMap<String, String> map) {
		JsonObject obj = new JsonObject();
		for (Entry<String, String> item : map.entrySet()) {
			obj.addProperty(item.getKey(), item.getValue());
		}
		return obj;
	}

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		if (this.id != null) {
			obj.addProperty("id", this.id);
		}
		if (this.description != null) {
			obj.add("description", this.serializeMap(this.description));
		}
		return obj;
	}
}