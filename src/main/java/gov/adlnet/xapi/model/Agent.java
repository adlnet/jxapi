package gov.adlnet.xapi.model;

public class Agent extends Actor implements IStatementObject {

	public static final String AGENT = "Agent";

	@Override
	public String getObjectType() {
		return AGENT;
	}

}
