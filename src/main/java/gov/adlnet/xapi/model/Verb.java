package gov.adlnet.xapi.model;

import java.util.HashMap;
import java.util.Map.Entry;
import com.google.gson.*;

public class Verb {
   private String id;
   private HashMap<String, String> display;

   public Verb() {}
   
   public Verb(String id) {
      this.id = id;
   }
   
   public Verb(String id, HashMap<String, String> display) {
      this.id = id;
      this.display = display;
   }
   
	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		if (this.id != null) {
			obj.addProperty("id", this.id);
		}
		if (this.display != null) {
			JsonObject displayObj = new JsonObject();
			obj.add("display", displayObj);
			for (Entry<String, String> item : display.entrySet()) {
				displayObj.addProperty(item.getKey(), item.getValue());
			}
		}
		return obj;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HashMap<String, String> getDisplay() {
		return display;
	}

	public void setDisplay(HashMap<String, String> display) {
		this.display = display;
	}
	
	public String toString() {
	   String ret = id;
	   if (display != null && 
	       display.get("en-US") != null && 
	       ! display.get("en-US").isEmpty()) ret = display.get("en-US");
	   return ret;
	}

    public String toString(String langKey) {
        String ret = id;
        if (display != null &&
                display.get(langKey) != null &&
                ! display.get(langKey).isEmpty()) ret = display.get(langKey);
        return ret;
    }
}
