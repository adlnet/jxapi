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

	public Boolean isSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Boolean isCompletion() {
		return completion;
	}

	public void setCompletion(Boolean completion) {
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

	public JsonObject getExtensions() {
		return extensions;
	}

	public void setExtensions(JsonObject extensions) {
		this.extensions = extensions;
	}

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		if (this.success != null) {
			obj.addProperty("success", this.success);
		}
		if (this.completion != null) {
			obj.addProperty("completion", this.completion);
		}
		if (this.response != null) {
			obj.addProperty("response", this.response);
		}
		if (this.duration != null) {
			obj.addProperty("duration", this.duration);
		}
		if (this.extensions != null) {
			obj.add("extensions", extensions);
		}
		if (this.score != null) {
			obj.add("score", this.score.serialize());
		}
		return obj;
	}

	private Score score;
	private Boolean success;
	private Boolean completion;
	private String response;
	private String duration;
	private JsonObject extensions;
}
