package gov.adlnet.xapi.model.adapters;

import java.lang.reflect.Type;

import com.google.gson.*;

import gov.adlnet.xapi.model.*;

public class ActorAdapter implements JsonDeserializer<Actor>,
		JsonSerializer<Actor> {

	private static final String OBJECT_TYPE = "objectType";

	@Override
	public Actor deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		Class<?> klass = null;
		try {
			String objectType = obj.get(OBJECT_TYPE).getAsJsonPrimitive()
					.getAsString().toLowerCase();			
			if (objectType.equals(Agent.AGENT.toLowerCase())) {
				klass = Class.forName(Agent.class.getCanonicalName());
			} else if (objectType.equals(Group.GROUP.toLowerCase())) {
				klass = Class.forName(Group.class.getCanonicalName());
			}
		} catch (ClassNotFoundException e) {
			throw new JsonParseException(e.getMessage());
		}
		return context.deserialize(json, klass);
	}

	@Override
	public JsonElement serialize(Actor a, Type typeofa,
			JsonSerializationContext context) {
		return a.serialize();
	}
}
