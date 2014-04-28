package gov.adlnet.xapi.model.adapters;

import com.google.gson.*;

import gov.adlnet.xapi.model.*;

import java.lang.reflect.Type;

public class StatenentObjectAdapter implements
		JsonDeserializer<IStatementObject> {
	@Override
	public IStatementObject deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		Class<?> klass = null;
		try {
			if (obj.has("objectType")) {
				String objectType = obj.get("objectType").toString()
						.toLowerCase();
				if (objectType.equals("agent")) {
					klass = Class.forName("gov.adlnet.xapi.model.Agent");
				} else if (objectType.equals("group")) {
					klass = Class.forName("gov.adlnet.xapi.model.Group");
				} else if (objectType.equals("activity")) {
					klass = Class.forName("gov.adlnet.xapi.model.Activity");
				} else if (objectType.equals("statementRef")) {
					klass = Class.forName("gov.adlnet.xapi.model.StatementRef");
				} else if (objectType.equals("statementRef")) {
					klass = Class.forName("gov.adlnet.xapi.model.SubStatement");
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
}
