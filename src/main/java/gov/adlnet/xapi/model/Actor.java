package gov.adlnet.xapi.model;

import java.net.URI;

import com.google.gson.*;

public abstract class Actor {
	private String name;
	private String mbox;
	private String mbox_sha1sum;
	private URI openid;
	private Account account;
	private boolean inverseFunctionalPropertySet = false;

	public String getMbox() {
		return mbox;
	}

	public void setMbox(String mbox) {
		if (mbox != null) {
			if (this.inverseFunctionalPropertySet) {
				throw new IllegalArgumentException(
						"Only one Inverse Functional Property can be set");
			}
			inverseFunctionalPropertySet = true;
		} else {
			inverseFunctionalPropertySet = false;
		}
		this.mbox = mbox;
	}

	public String getMbox_sha1sum() {
		return mbox_sha1sum;
	}

	public void setMbox_sha1sum(String mbox_sha1sum) {
		if (mbox_sha1sum != null) {
			if (this.inverseFunctionalPropertySet) {
				throw new IllegalArgumentException(
						"Only one Inverse Functional Property can be set");
			}
			inverseFunctionalPropertySet = true;
		} else {
			inverseFunctionalPropertySet = false;
		}
		this.mbox_sha1sum = mbox_sha1sum;
	}

	public URI getOpenid() {
		return openid;
	}

	public void setOpenid(URI openid) {
		if (openid != null) {
			if (this.inverseFunctionalPropertySet) {
				throw new IllegalArgumentException(
						"Only one Inverse Functional Property can be set");
			}
			inverseFunctionalPropertySet = true;
		} else {
			inverseFunctionalPropertySet = false;
		}		
		this.openid = openid;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		if (account != null) {
			if (this.inverseFunctionalPropertySet) {
				throw new IllegalArgumentException(
						"Only one Inverse Functional Property can be set");
			}
			inverseFunctionalPropertySet = true;
		} else {
			inverseFunctionalPropertySet = false;
		}		
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract String getObjectType();

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		if (this.name != null) {
			obj.addProperty("name", this.name);
		}
		if (this.mbox != null) {
			obj.addProperty("mbox", this.mbox);
		}
		if (this.mbox_sha1sum != null) {
			obj.addProperty("mbox_sha1sum", this.mbox_sha1sum);
		}
		if (this.openid != null) {
			obj.addProperty("openid", this.openid.toString());
		}
		if (this.account != null) {
			obj.add("account", this.account.serialize());
		}
		obj.addProperty("objectType", this.getObjectType());
		return obj;
	}
	
	public String toString() {
	   if (name != null && ! name.isEmpty()) return name;
	   
	   String ret = "Anonymous";
	   if (mbox != null && ! mbox.isEmpty()) ret = mbox;
	   else if (mbox_sha1sum != null && ! mbox_sha1sum.isEmpty()) ret = mbox_sha1sum;
	   else if (openid != null && ! openid.toString().isEmpty()) ret = openid.toString();
	   else if (account != null) ret = account.toString();
	   return ret;
	}
}
