package gov.adlnet.xapi.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Score {
    private float scaled;
    private float raw;
    private float min;
    private float max;

    public float getScaled() {
        return scaled;
    }
    public void setScaled(float scaled) {
        this.scaled = scaled;
    }
    public float getRaw() {
        return raw;
    }
    public void setRaw(float raw) {
        this.raw = raw;
    }
    public float getMin() {
        return min;
    }
    public void setMin(float min) {
        this.min = min;
    }
    public float getMax() {
        return max;
    }
    public void setMax(float max) {
        this.max = max;
    }

	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		obj.addProperty("scaled", this.scaled);
		obj.addProperty("raw", this.raw);
		obj.addProperty("min", this.min);
		obj.addProperty("max", this.max);
		return obj;
	}	

}
