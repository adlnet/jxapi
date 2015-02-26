package gov.adlnet.xapi.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
	
	public Statement() {}
	
	public Statement(Actor actor, Verb verb, IStatementObject object) {
	   this.id = UUID.randomUUID().toString();
	   this.actor = actor;
	   this.verb = verb;
	   this.object = object;
	}
	
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

    public String toString(){
        return String.format("%s %s %s", actor.toString(), verb.toString(), object.toString());
    }

    public JsonElement serialize() {
        JsonObject obj = new JsonObject();
        if (this.id!= null) {
            obj.addProperty("id", this.id);
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
        if (this.timestamp != null) {
            obj.addProperty("timestamp", this.timestamp);
        }
        if (this.stored != null) {
            obj.addProperty("stored", this.stored);
        }
        if (this.version != null) {
            obj.addProperty("version", this.version);
        }
        if (this.authority != null) {
            obj.add("authority", this.authority.serialize());
        }
        JsonArray jsonAttachments = new JsonArray();
        obj.add("attachments", jsonAttachments);
        for (Attachment a : this.attachments) {
            jsonAttachments.add(a.serialize());
        }
        return obj;
    }
}
