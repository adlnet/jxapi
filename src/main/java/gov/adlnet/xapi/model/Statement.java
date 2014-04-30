package gov.adlnet.xapi.model;

import java.util.ArrayList;
import java.util.UUID;

public class Statement {
	private String id;
	private String timestamp;
	private String stored;
	private String version;
	
	private Verb verb;
	private Actor actor;
	private IStatementObject object;
	private Result result;
	private Context context;	
	private Actor authority;
	private ArrayList<Attachment> attachments;
	
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
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public IStatementObject getObject() {
		return object;
	}
	public void setObject(IStatementObject object) {
		this.object = object;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}	
}
