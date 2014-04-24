package gov.adlnet.xapi.model;

import java.util.HashMap;

public class Verb {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public HashMap<String, String> getDisplay() {
		return display;
	}
	public void setDisplay(HashMap<String, String> display) {
		this.display = display;
	}
	private String id;
	private HashMap<String, String> display;
}
