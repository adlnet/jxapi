package gov.adlnet.xapi.model.adapters;

import com.google.gson.*;

import gov.adlnet.xapi.model.*;

import java.lang.reflect.Type;

public class StatenentObjectAdapter implements JsonSerializer<IStatementObject>,
		JsonDeserializer<IStatementObject> {
	private static final String OBJECT_TYPE = "objectType";

	@Override
	public IStatementObject deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		Class<?> klass = null;
		try {
			if (obj.has(OBJECT_TYPE)) {
				String objectType = obj.get(OBJECT_TYPE).getAsJsonPrimitive()
						.getAsString().toLowerCase();
				if (objectType.equals(Agent.AGENT.toLowerCase())) {
					klass = Class.forName(Agent.class.getCanonicalName());
				} else if (objectType.equals(Group.GROUP.toLowerCase())) {
					klass = Class.forName(Group.class.getCanonicalName());
				} else if (objectType.equals(Activity.ACTIVITY.toLowerCase())) {
					klass = Class.forName(Activity.class.getCanonicalName());
				} else if (objectType.equals(StatementReference.STATEMENT_REFERENCE.toLowerCase())) {
					klass = Class.forName(StatementReference.class.getCanonicalName());
				} else if (objectType.equals(SubStatement.SUB_STATEMENT.toLowerCase())) {
					klass = Class.forName(SubStatement.class.getCanonicalName());
				}
			} else {
				throw new JsonParseException(
						"Invalid JSON for statement object");
			}
		} catch (ClassNotFoundException e) {
			throw new JsonParseException(e.getMessage());
		}
		return context.deserialize(json, klass);
	}
	@Override
	public JsonElement serialize(IStatementObject a, Type typeofa,
			JsonSerializationContext context) {
		return a.serialize();
	}	
}
