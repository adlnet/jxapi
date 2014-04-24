package gov.adlnet.xapi.model;

import java.util.HashMap;
import java.util.UUID;

public class Context {
	private UUID registration;
	private Actor instructor;
	private Group team;
	private String revision;
	private String platform;
	private String language;
	private StatementReference statement;
	private HashMap<String, String> extensions;
	private HashMap<String, Activity> contextActivities;
	public UUID getRegistration() {
		return registration;
	}
	public void setRegistration(UUID registration) {
		this.registration = registration;
	}
	public Actor getInstructor() {
		return instructor;
	}
	public void setInstructor(Actor instructor) {
		this.instructor = instructor;
	}
	public Group getTeam() {
		return team;
	}
	public void setTeam(Group team) {
		this.team = team;
	}
}
