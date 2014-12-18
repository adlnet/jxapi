package gov.adlnet.xapi.model;

/**
 * Created by lou on 12/15/14.
 */
public class Person {
    private static final String OBJECTTYPE = "Person";
    private String[] name;
    private String[] mbox;
    private String[] mbox_sha1sum;
    private String[] openid;
    private Account[] account;

    public String getObjectType() {
        return OBJECTTYPE;
    }

    public String toString() {
        String ret = getObjectType() + " ; ";
        if (name != null && name.length != 0){
            ret += "name: " + name.toString() + " ; ";
        }
        if (mbox != null && mbox.length != 0){
            ret += "mbox: " + mbox.toString() + " ; ";
        }
        if (mbox_sha1sum != null && mbox_sha1sum.length != 0){
            ret += "mbox_sha1sum: " + mbox_sha1sum.toString() + " ; ";
        }
        if (openid != null && openid.length != 0){
            ret += "openid: " + openid.toString() + " ; ";
        }
        if (account != null && account.length != 0){
            ret += "account: ";
            String accts = "";
            for(Account acc: account){
                if (accts.length() != 0){
                    accts += " , ";
                }
                accts += acc.toString() + " ";
            }
            ret += accts + " ; ";
        }
        return ret;
    }
}
