package gov.adlnet.xapi.model;

import java.util.ArrayList;
import com.google.gson.*;

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

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		if (this.parent != null) {
			JsonArray parents = new JsonArray();
			obj.add("parent", parents);
			for (Parent item : this.parent) {
				parents.add(item.seriailze());
			}
		}
		if (this.grouping != null) {
			JsonArray groupings = new JsonArray();
			obj.add("grouping", groupings);
			for (GroupingObject g : this.grouping) {
				groupings.add(g.serialize());
			}
		}
		return obj;
	}
}
