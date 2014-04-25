package gov.adlnet.xapi.model;

import java.util.UUID;

public class GroupingObject {
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Definition getDefinition() {
		return definition;
	}
	public void setDefinition(Definition definition) {
		this.definition = definition;
	}
	private UUID id;
	private Definition definition;
}
