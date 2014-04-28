package gov.adlnet.xapi.model;

import java.util.ArrayList;

public class SubStatement implements IStatementObject {	
	private Verb verb;
	private Actor actor;
	private IStatementObject object;
	private Result result;
	private Context context;	
	private String timestamp;
	private ArrayList<Attachment> attachments;	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
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
		if (object.getObjectType().toLowerCase().equals("substatement")){
			throw new IllegalArgumentException("Sub-Statements cannot be nested");
		}
		this.object = object;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public String getObjectType() {
		return "SubStatement";
	}
}
