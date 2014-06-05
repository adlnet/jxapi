package gov.adlnet.xapi;

import gov.adlnet.xapi.model.Actor;
import gov.adlnet.xapi.model.Agent;
import gov.adlnet.xapi.model.Group;
import gov.adlnet.xapi.model.IStatementObject;
import gov.adlnet.xapi.model.adapters.ActorAdapter;
import gov.adlnet.xapi.model.adapters.StatementObjectAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import junit.framework.TestCase;

public class ActorAdapterTest extends TestCase {
	private Gson gson;
	public ActorAdapterTest(){
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Actor.class, new ActorAdapter());
		gson = builder.create();
	}
	public void testDeserializeActor() {
		String data = "{\"mbox\": \"mailto:user@example.com\",\"name\": \"test user\",\"objectType\": \"Agent\"}";		
		Actor a = gson.fromJson(data, Actor.class);
		assert a.getClass() == Agent.class;
	}
	public void testDeserializeActorNoType() {
		String data = "{\"mbox\": \"mailto:user@example.com\",\"name\": \"test user\"}";		
		Actor a = gson.fromJson(data, Actor.class);
		assert a.getClass() == Agent.class;
	}
	public void testDeserializeActorGroup() {
		String data = "{\"mbox\": \"mailto:user@example.com\",\"name\": \"test user\",\"objectType\": \"Group\"}";		
		Actor a = gson.fromJson(data, Actor.class);
		assert a.getClass() == Group.class;
	}		
}
