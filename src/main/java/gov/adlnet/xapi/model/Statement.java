package gov.adlnet.xapi.model;

import java.util.ArrayList;
import java.util.UUID;

public class Statement {
	private UUID id;
	private Verb verb;
	private Actor actor;
	private Object object;
	private Result result;
	private Context context;	
	private String timestamp;
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getStored() {
		return stored;
	}
	public void setStored(String stored) {
		this.stored = stored;
	}
	public Actor getAuthority() {
		return authority;
	}
	public void setAuthority(Actor authority) {
		this.authority = authority;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public ArrayList<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(ArrayList<Attachment> attachments) {
		this.attachments = attachments;
	}
	private String stored;
	private Actor authority;
	private String version;
	private ArrayList<Attachment> attachments;
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Verb getVerb() {
		return verb;
	}
	public void setVerb(Verb verb) {
		this.verb = verb;
	}
	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor) {
		this.actor = actor;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	
}
