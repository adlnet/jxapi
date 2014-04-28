package gov.adlnet.xapi.model;

import java.net.URI;

public class Activity {
	public String getObjectType() {
		return "activity";
	}
	public URI getId() {
		return id;
	}
	public void setId(URI id) {
		this.id = id;
	}
	public ActivityDefinition getDefinition() {
		return definition;
	}
	public void setDefinition(ActivityDefinition definition) {
		this.definition = definition;
	}
	private URI id;
	private ActivityDefinition definition;
}
