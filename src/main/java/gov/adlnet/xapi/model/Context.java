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
	private ContextActivities contextActivities;	
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
	public String getRevision() {
		return revision;
	}
	public void setRevision(String revision) {
		this.revision = revision;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public StatementReference getStatement() {
		return statement;
	}
	public void setStatement(StatementReference statement) {
		this.statement = statement;
	}
	public HashMap<String, String> getExtensions() {
		return extensions;
	}
	public void setExtensions(HashMap<String, String> extensions) {
		this.extensions = extensions;
	}
//	public HashMap<String, Activity> getContextActivities() {
//		return contextActivities;
//	}
//	public void setContextActivities(HashMap<String, Activity> contextActivities) {
//		this.contextActivities = contextActivities;
//	}
}
