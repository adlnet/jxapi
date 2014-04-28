package gov.adlnet.xapi.model;

import java.util.HashMap;

public class InteractionComponent {
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
	private String id;
	private HashMap<String, String> description;
}