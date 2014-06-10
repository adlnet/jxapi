package gov.adlnet.xapi.model;

import java.util.ArrayList;
import com.google.gson.*;

public class ContextActivities {
	private ArrayList<Activity> parent;
	private ArrayList<Activity> grouping;
	private ArrayList<Activity> category;
	private ArrayList<Activity> other;
	public ArrayList<Activity> getParent() {
		return parent;
	}

	public void setParent(ArrayList<Activity> parent) {
		this.parent = parent;
	}

	public ArrayList<Activity> getGrouping() {
		return grouping;
	}

	public void setGrouping(ArrayList<Activity> grouping) {
		this.grouping = grouping;
	}

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		if (this.parent != null) {
			JsonArray parents = new JsonArray();
			obj.add("parent", parents);
			for (Activity item : this.parent) {
				parents.add(item.serialize());
			}
		}
		if (this.grouping != null) {
			JsonArray groupings = new JsonArray();
			obj.add("grouping", groupings);
			for (Activity g : this.grouping) {
				groupings.add(g.serialize());
			}
		}
		if (this.category != null) {
			JsonArray groupings = new JsonArray();
			obj.add("category", groupings);
			for (Activity g : this.category) {
				groupings.add(g.serialize());
			}
		}
		if (this.other != null) {
			JsonArray groupings = new JsonArray();
			obj.add("other", groupings);
			for (Activity g : this.other) {
				groupings.add(g.serialize());
			}
		}			
		return obj;
	}

	public ArrayList<Activity> getCategory() {
		return category;
	}

	public void setCategory(ArrayList<Activity> category) {
		this.category = category;
	}

	public ArrayList<Activity> getOther() {
		return other;
	}

	public void setOther(ArrayList<Activity> other) {
		this.other = other;
	}
}
