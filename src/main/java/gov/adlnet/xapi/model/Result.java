package gov.adlnet.xapi.model;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Result {
	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isCompletion() {
		return completion;
	}

	public void setCompletion(boolean completion) {
		this.completion = completion;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public HashMap<String, String> getExtensions() {
		return extensions;
	}

	public void setExtensions(HashMap<String, String> extensions) {
		this.extensions = extensions;
	}

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		obj.addProperty("success", this.success);
		obj.addProperty("completion", this.completion);
		if (this.response != null) {
			obj.addProperty("response", this.response);
		}
		if (this.duration != null) {
			obj.addProperty("duration", this.duration);
		}
		if (this.extensions != null) {
			JsonObject extensionsObj = new JsonObject();
			obj.add("extensions", extensionsObj);
			for (Entry<String, String> item : extensions.entrySet()) {
				extensionsObj.addProperty(item.getKey(), item.getValue());
			}
		}
		if (this.score != null) {
			obj.add("score", this.score.serialize());
		}
		return obj;
	}

	private Score score;
	private boolean success;
	private boolean completion;
	private String response;
	private String duration;
	private HashMap<String, String> extensions;
}
