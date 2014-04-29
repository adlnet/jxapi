package gov.adlnet.xapi.model;

import java.util.ArrayList;
import com.google.gson.*;

public class SubStatement implements IStatementObject {
	public static final String SUB_STATEMENT = "SubStatement";
	private String timestamp;
	private Verb verb;
	private Actor actor;
	private IStatementObject object;
	private Result result;
	private Context context;
	private ArrayList<Attachment> attachments;

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		if (this.timestamp != null) {
			obj.addProperty("timestamp", this.timestamp);
		}
		if (this.actor != null) {
			obj.add("actor", this.actor.serialize());
		}
		if (this.verb != null) {
			obj.add("verb", verb.serialize());
		}
		if (this.object != null) {
			obj.add("object", object.serialize());
		}
		if (this.result != null) {
			obj.add("result", result.serialize());
		}
		if (this.context != null) {
			obj.add("context", this.context.serialize());
		}
		obj.addProperty("objectType", this.getObjectType());
		JsonArray jsonAttachments = new JsonArray();
		obj.add("attachments", jsonAttachments);
		for (Attachment a : this.attachments) {
			jsonAttachments.add(a.serialize());
		}
		return obj;
	}

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
		if (object.getObjectType().toLowerCase().equals("substatement")) {
			throw new IllegalArgumentException(
					"Sub-Statements cannot be nested");
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
		return SUB_STATEMENT;
	}
}
