package gov.adlnet.xapi.model;

import java.net.URI;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.*;

public class Attachment {
	private URI usageType;
	private HashMap<String, String> display;
	private HashMap<String, String> description;
	private String contentType;
	private int length;
	private String sha2;
	private URI fileUrl;

	private JsonElement serializeHash(HashMap<String, String> map) {
		JsonObject obj = new JsonObject();
		for (Entry<String, String> item : map.entrySet()) {
			obj.addProperty(item.getKey(), item.getValue());
		}
		return obj;
	}
	public JsonElement serialize(){
		JsonObject obj = new JsonObject();
		obj.addProperty("usageType", this.usageType.toString());
		obj.add("display", this.serializeHash(this.display));
		obj.add("description", this.serializeHash(this.description));
		obj.addProperty("contentType", this.contentType);
		obj.addProperty("length", this.length);
		obj.addProperty("sha2", this.sha2);
		obj.addProperty("fileUrl", this.fileUrl.toString());
		return obj;
	}
	public URI getUsageType() {
		return usageType;
	}

	public void setUsageType(URI usageType) {
		this.usageType = usageType;
	}

	public HashMap<String, String> getDisplay() {
		return display;
	}

	public void setDisplay(HashMap<String, String> display) {
		this.display = display;
	}

	public HashMap<String, String> getDescription() {
		return description;
	}

	public void setDescription(HashMap<String, String> description) {
		this.description = description;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getSha2() {
		return sha2;
	}

	public void setSha2(String sha2) {
		this.sha2 = sha2;
	}

	public URI getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(URI fileUrl) {
		this.fileUrl = fileUrl;
	}
}
