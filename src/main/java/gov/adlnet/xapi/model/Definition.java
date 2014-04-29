package gov.adlnet.xapi.model;

import java.util.HashMap;
import java.util.Map.Entry;
import com.google.gson.*;
public class Definition {
	private HashMap<String, String> extensions;
	public JsonElement serialize(){
		JsonObject extensionsObj = new JsonObject();
		for (Entry<String, String> item : extensions.entrySet()){
			extensionsObj.addProperty(item.getKey(), item.getValue());
		}
		return extensionsObj;
	}
	public HashMap<String, String> getExtensions() {
		return extensions;
	}

	public void setExtensions(HashMap<String, String> extensions) {
		this.extensions = extensions;
	}
}
