package gov.adlnet.xapi.model;

import java.util.UUID;

public class State {
	private String activityId;
	private Agent agent;
	private UUID registration;
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
}
