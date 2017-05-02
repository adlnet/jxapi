package gov.adlnet.xapi.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by lou on 12/15/14.
 */
public class Person {
	private static final String OBJECTTYPE = "Person";
	private String[] name = null;
	private String[] mbox = null;
	private String[] mbox_sha1sum = null;
	private String[] openid = null;
	private Account[] account = null;
	private transient boolean inverseFunctionalPropertySet = false;

	public String getObjectType() {
		return OBJECTTYPE;
	}

	public String[] getName() {
		return this.name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public String[] getMbox() {
		return this.mbox;
	}

	public void setMbox(String[] mbox) {
		if (mbox != null) {
			if (this.inverseFunctionalPropertySet) {
				throw new IllegalArgumentException("Only one Inverse Functional Property can be set");
			}
			inverseFunctionalPropertySet = true;
		} else {
			inverseFunctionalPropertySet = false;
		}
		this.mbox = mbox;
	}

	public String[] getMbox_sha1sum() {
		return this.mbox_sha1sum;
	}

	public void setMbox_sha1sum(String[] mbox_sha1sum) {
		if (mbox_sha1sum != null) {
			if (this.inverseFunctionalPropertySet) {
				throw new IllegalArgumentException("Only one Inverse Functional Property can be set");
			}
			inverseFunctionalPropertySet = true;
		} else {
			inverseFunctionalPropertySet = false;
		}
		this.mbox_sha1sum = mbox_sha1sum;
	}

	public String[] getOpenid() {
		return this.openid;
	}

	public void setOpenid(String[] openid) {
		if (openid != null) {
			if (this.inverseFunctionalPropertySet) {
				throw new IllegalArgumentException("Only one Inverse Functional Property can be set");
			}
			inverseFunctionalPropertySet = true;
		} else {
			inverseFunctionalPropertySet = false;
		}
		this.openid = openid;
	}

	public Account[] getAccount() {
		return this.account;
	}

	public void setAccount(Account[] account) {
		if (account != null) {
			if (this.inverseFunctionalPropertySet) {
				throw new IllegalArgumentException("Only one Inverse Functional Property can be set");
			}
			inverseFunctionalPropertySet = true;
			for (int i = 0; i < account.length; i++) {
				this.account = account;
			}
		} else {
			inverseFunctionalPropertySet = false;

		}
	}

	public String toString() {
		String ret = getObjectType() + " ; ";

		if (name != null && name.length != 0) {
			for (int i = 0; i < name.length; i++) {
				ret += "name: " + name[i].toString() + " ; ";
			}
		}
		if (mbox != null && mbox.length != 0) {
			for (int i = 0; i < mbox.length; i++) {
				ret += "mbox: " + mbox[i].toString() + " ; ";
			}

		}
		if (mbox_sha1sum != null && mbox_sha1sum.length != 0) {
			for (int i = 0; i < mbox_sha1sum.length; i++) {
				ret += "mbox_sha1sum: " + mbox_sha1sum[i].toString() + " ; ";
			}
		}
		if (openid != null && openid.length != 0) {
			for (int i = 0; i < openid.length; i++) {
				ret += "openid: " + openid[i].toString() + " ; ";
			}
		}
		if (account != null && account.length != 0) {
			ret += "account: ";
			String accts = "";
			for (Account acc : account) {
				if (accts.length() != 0) {
					accts += " , ";
				}
				accts += acc.toString() + " ";
			}
			ret += accts + " ; ";
		}
		return ret;
	}

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();

		if (name != null && name.length != 0) {
			obj.addProperty("name", toString(name));
		}
		if (mbox != null && mbox.length != 0) {
			obj.addProperty("mbox", toString(mbox));
		}
		if (mbox_sha1sum != null && mbox_sha1sum.length != 0) {
			obj.addProperty("mbox_sha1sum", toString(mbox_sha1sum));
		}
		if (openid != null && openid.length != 0) {
			obj.addProperty("openid", toString(openid));
		}
		if (account != null && account.length != 0) {
			for (int i = 0; i < account.length; i++) {
				obj.add("account", account[i].serialize());
			}

		}

		obj.addProperty("objectType", this.getObjectType());
		return obj;
	}

	private String toString(String[] input) {
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < input.length; i++) {
			strBuilder.append(input[i]);
		}
		return strBuilder.toString();
	}
}
