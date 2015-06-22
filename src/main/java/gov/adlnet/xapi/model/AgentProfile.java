package gov.adlnet.xapi.model;

import com.google.gson.JsonObject;

/**
 * Created by lou on 2/6/15.
 */
public class AgentProfile {
    private Agent agent;
    private String profileId;
    private JsonObject profile;

    public AgentProfile(){}

    public AgentProfile(Agent agent, String profileId){
        this.agent = agent;
        this.profileId = profileId;
    }

    public Agent getAgent() {
        return agent;
    }
    public void setAgent(Agent a) { this.agent = a; }
    public String getProfileId(){
        return this.profileId;
    }
    public void setProfileId(String profileId){
        this.profileId = profileId;
    }
    public JsonObject getProfile(){return this.profile;}
    public void setProfile(JsonObject p){ this.profile = p; }
}
