package gov.adlnet.xapi.model;

import com.google.gson.JsonObject;

import java.util.UUID;

public class ActivityState {
	private String activityId;
	private Agent agent;
	private UUID registration;
    private JsonObject state;
    private String since;

    public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	} 
	public void setAgent(Agent a){
		this.agent = a;
	}
	public Agent getAgent(){
		return this.agent;
	}
	public UUID getRegistration() {
		return registration;
	}
	public void setRegistration(UUID registration) {
		this.registration = registration;
	}
    public JsonObject getState(){return this.state;}
    public void setState(JsonObject s){ this.state = s; }
    public String getSince() { return since; }
    public void setSince(String s) {this.since = s;}
}
