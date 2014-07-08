package gov.adlnet.xapi.model;

import com.google.gson.*;

public class Account {
	private String homePage;
	private String name;
	
	public Account() {}
	
	public Account(String name, String homepage) {
	   this.name = name;
	   this.homePage = homepage;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		if (this.homePage != null) {
			obj.addProperty("homePage", this.homePage);
		}
		if (this.name != null) {
			obj.addProperty("name", this.name);
		}
		return obj;
	}
	
	public String toString() {
	   return String.format("%s (%s)", name, homePage);
	}
}
