package gov.adlnet.xapi.model;

import java.util.ArrayList;
import com.google.gson.*;
public class Group extends Actor {

	public static final String GROUP = "Group";
	@Override
	public String getObjectType() {
		return GROUP;
	}
	private ArrayList<Agent> member;
	public ArrayList<Agent> getMember() {
		return member;
	}
	public void setMember(ArrayList<Agent> member) {
		this.member = member;
	}
	public JsonElement serialize(){
		JsonObject obj = (JsonObject)super.serialize();
		JsonArray members = new JsonArray();
		for (Agent agent: this.member){
			members.add(agent.serialize());
		}
		obj.add("member", members);
		return obj;
	}
}
