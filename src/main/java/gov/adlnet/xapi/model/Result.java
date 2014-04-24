package gov.adlnet.xapi.model;

import org.joda.time.*;

public class Result<T> {
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
	public Duration getDuration() {
		return duration;
	}
	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	public T getExtensions() {
		return extensions;
	}
	public void setExtensions(T extensions) {
		this.extensions = extensions;
	}
	private Score score;
	private boolean success;
	private boolean completion;
	private String response;
	private Duration duration;
	private T extensions;
}
