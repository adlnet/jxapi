package gov.adlnet.xapi.model;

import java.util.ArrayList;

public class Group extends Actor {

	@Override
	public String getObjectType() {
		return "Group";
	}
	private ArrayList<Agent> member;
	public ArrayList<Agent> getMember() {
		return member;
	}
	public void setMember(ArrayList<Agent> member) {
		this.member = member;
	}
	
}
