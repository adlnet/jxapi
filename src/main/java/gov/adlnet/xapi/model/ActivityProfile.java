package gov.adlnet.xapi.model;

import com.google.gson.JsonObject;

/**
 * Created by lou on 2/6/15.
 */
public class ActivityProfile {
    private String activityId;
    private String profileId;
    private String since;
    private JsonObject profile;

    public String getActivityId() {
        return activityId;
    }
    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
    public String getProfileId(){
        return this.profileId;
    }
    public void setProfileId(String profileId){
        this.profileId = profileId;
    }
    public String getSince() {
        return this.since;
    }
    public void setSince(String s) {
        this.since = since;
    }
    public JsonObject getProfile(){return this.profile;}
    public void setProfile(JsonObject p){ this.profile = p; }
}
