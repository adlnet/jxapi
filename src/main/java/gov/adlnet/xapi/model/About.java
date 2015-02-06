package gov.adlnet.xapi.model;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by lou on 2/6/15.
 */
public class About {
    private ArrayList<String> version;
    private JsonObject extensions;

    public ArrayList<String> getVersion() {
        return version;
    }

    public void setVersion(ArrayList<String> v) {
        this.version= v;
    }

    public JsonObject getExtensions() {
        return extensions;
    }

    public void setExtensions(JsonObject extensions) {
        this.extensions = extensions;
    }
}
