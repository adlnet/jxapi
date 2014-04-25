package gov.adlnet.xapi.model;

import java.util.ArrayList;

public class ContextActivities {
	private ArrayList<Parent> parent;
	private ArrayList<GroupingObject> grouping;
	public ArrayList<Parent> getParent() {
		return parent;
	}
	public void setParent(ArrayList<Parent> parent) {
		this.parent = parent;
	}
	public ArrayList<GroupingObject> getGrouping() {
		return grouping;
	}
	public void setGrouping(ArrayList<GroupingObject> grouping) {
		this.grouping = grouping;
	}
}	
