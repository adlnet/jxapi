package gov.adlnet.xapi.model;

import com.google.gson.*;
public class Definition {
	private JsonObject extensions;
	public JsonElement serialize(){
		
		JsonObject self = new JsonObject();
		self.add("extensions", extensions);
		return self;
	}
	public JsonObject getExtensions() {
		return extensions;
	}

	public void setExtensions(JsonObject extensions) {
		this.extensions = extensions;
	}
}
