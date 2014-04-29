package gov.adlnet.xapi.model;
import com.google.gson.*;
public interface IStatementObject {
	public String getObjectType();
	public JsonElement serialize();
}
