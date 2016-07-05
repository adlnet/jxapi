package gov.adlnet.xapi.model;

import java.net.URI;

public class Agent extends Actor {

	public static final String AGENT = "Agent";
	
	public Agent() {}
	
	public Agent(String name, String mbox) {
	   super();
	   setName(name);
	   setMbox(mbox);
	}
	
	public Agent(String name, URI openid) {
	   super();
	   setName(name);
	   setOpenid(openid);
	}
	
	public Agent(String name, Account account) {
	   super();
	   setName(name);
	   setAccount(account);
	}

	@Override
	public String getObjectType() {
		return AGENT;
	}

}
