package gov.adlnet.xapi.model;

public class Parent {
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Definition getDefinition() {
		return definition;
	}
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}
	private String id;
	private Definition definition;
}
