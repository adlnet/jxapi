package gov.adlnet.xapi.model;

import com.google.gson.JsonObject;

import java.util.UUID;

public class ActivityState {
	private String activityId;
	private String stateId;
    private Agent agent;
	private UUID registration;
    private JsonObject state;

    public ActivityState(){}

    public ActivityState(String activityId, String stateId, Agent agent){
        this.activityId = activityId;
        this.stateId = stateId;
        this.agent = agent;
    }

    public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
    public String getStateId() {
        return stateId;
    }
    public void setStateId(String stateId) {
        this.stateId = stateId;
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
}
