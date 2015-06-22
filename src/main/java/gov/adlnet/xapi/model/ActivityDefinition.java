package gov.adlnet.xapi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ActivityDefinition {
	private String type;
	private String moreInfo;
	private String interactionType;

	private ArrayList<String> correctResponsesPattern;

	private HashMap<String, JsonElement> extensions;
	private HashMap<String, String> name;
	private HashMap<String, String> description;

	private ArrayList<InteractionComponent> choices;
	private ArrayList<InteractionComponent> scale;
	private ArrayList<InteractionComponent> source;
	private ArrayList<InteractionComponent> target;
	private ArrayList<InteractionComponent> steps;
	
	public ActivityDefinition() {}
	
	public ActivityDefinition(HashMap<String, String>name, HashMap<String, String>description) {
	   this.name = name;
	   this.description = description;
	}

	private JsonElement serializeMap(HashMap<String, String> map) {
		JsonObject obj = new JsonObject();
		for (Entry<String, String> item : map.entrySet()) {
			obj.addProperty(item.getKey(), item.getValue());
		}
		return obj;
	}

	private JsonElement serializeInteractionComponents(
			ArrayList<InteractionComponent> components) {
		JsonArray array = new JsonArray();
		for (InteractionComponent comp : components) {
			array.add(comp.serialize());
		}
		return array;
	}

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		if (this.type != null) {
			obj.addProperty("type", this.type);
		}
		if (this.moreInfo != null) {
			obj.addProperty("moreInfo", this.moreInfo);
		}
		if (this.interactionType != null) {
			obj.addProperty("interactionType", this.interactionType);
		}
		if (this.correctResponsesPattern != null) {
			JsonArray correctResponsesPatterns = new JsonArray();
			for (String s : this.correctResponsesPattern) {
				correctResponsesPatterns.add(new JsonPrimitive(s));
			}
			obj.add("correctResponsesPattern", correctResponsesPatterns);
		}
		if (this.extensions != null) {
			JsonObject extensions = new JsonObject();
			obj.add("extensions", extensions);
			for (Entry<String, JsonElement> item : this.extensions.entrySet()){
				extensions.add(item.getKey(), item.getValue());
			}
		}
		if (this.name != null) {
			obj.add("name", this.serializeMap(this.name));
		}
		if (this.description != null) {
			obj.add("description", this.serializeMap(this.description));
		}
		if (this.choices != null) {
			obj.add("choices",
					this.serializeInteractionComponents(this.choices));
		}
		if (this.scale != null) {
			obj.add("scale", this.serializeInteractionComponents(this.scale));
		}
		if (this.source != null) {
			obj.add("source", this.serializeInteractionComponents(this.source));
		}
		if (this.target != null) {
			obj.add("target", this.serializeInteractionComponents(this.target));
		}
		if (this.steps != null) {
			obj.add("steps", this.serializeInteractionComponents(this.steps));
		}
		return obj;
	}

	public HashMap<String, String> getName() {
		return name;
	}

	public void setName(HashMap<String, String> name) {
		this.name = name;
	}

	public HashMap<String, String> getDescription() {
		return description;
	}

	public void setDescription(HashMap<String, String> description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreinfo) {
		this.moreInfo = moreinfo;
	}

	public HashMap<String, JsonElement> getExtensions() {
		return extensions;
	}

	public void setExtensions(HashMap<String, JsonElement> extensions) {
		this.extensions = extensions;
	}

	public String getInteractionType() {
		return interactionType;
	}

	public void setInteractionType(String interactionType) {
		this.interactionType = interactionType;
	}

	public ArrayList<String> getCorrectResponsesPattern() {
		return correctResponsesPattern;
	}

	public void setCorrectResponsesPattern(
			ArrayList<String> correctResponsesPattern) {
		this.correctResponsesPattern = correctResponsesPattern;
	}

	public ArrayList<InteractionComponent> getChoices() {
		return choices;
	}

	public void setChoices(ArrayList<InteractionComponent> choices) {
		this.choices = choices;
	}

	public ArrayList<InteractionComponent> getScale() {
		return scale;
	}

	public void setScale(ArrayList<InteractionComponent> scale) {
		this.scale = scale;
	}

	public ArrayList<InteractionComponent> getSource() {
		return source;
	}

	public void setSource(ArrayList<InteractionComponent> source) {
		this.source = source;
	}

	public ArrayList<InteractionComponent> getTarget() {
		return target;
	}

	public void setTarget(ArrayList<InteractionComponent> target) {
		this.target = target;
	}

	public ArrayList<InteractionComponent> getSteps() {
		return steps;
	}

	public void setSteps(ArrayList<InteractionComponent> steps) {
		this.steps = steps;
	}
	
	public String toString() {
		if (name != null)
			return name.get("en-US");
		return "";
	}
    public String toString(String langMap) {
        if (name != null){
            return name.get(langMap);
        }
        return "";
    }
}
