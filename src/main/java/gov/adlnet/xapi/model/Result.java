package gov.adlnet.xapi.model;

import java.util.HashMap;

import org.joda.time.*;

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
	private Score score;
	private boolean success;
	private boolean completion;
	private String response;
	private String duration;
	private HashMap<String, String> extensions;
}
