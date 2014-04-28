package gov.adlnet.xapi.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class ActivityDefinition{
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
	public URI getType() {
		return type;
	}
	public void setType(URI type) {
		this.type = type;
	}
	public URI getMoreinfo() {
		return moreinfo;
	}
	public void setMoreinfo(URI moreinfo) {
		this.moreinfo = moreinfo;
	}
	public HashMap<String, String> getExtensions() {
		return extensions;
	}
	public void setExtensions(HashMap<String, String> extensions) {
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
	public void setCorrectResponsesPattern(ArrayList<String> correctResponsesPattern) {
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
	private HashMap<String, String> name;
	private HashMap<String, String> description;
	private URI type;
	private URI moreinfo;
	private HashMap<String, String> extensions;
	private String interactionType;
	private ArrayList<String> correctResponsesPattern;
	private ArrayList<InteractionComponent> choices;
	private ArrayList<InteractionComponent> scale;
	private ArrayList<InteractionComponent> source;
	private ArrayList<InteractionComponent> target;
	private ArrayList<InteractionComponent> steps;
}
