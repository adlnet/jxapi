package gov.adlnet.xapi.model;

import java.util.HashMap;

public class Verbs {
	private static String BASE_VERB_URI ="http://adlnet.gov/expapi/verbs/"; 
	private static Verb createVerb(String description){
		Verb v = new Verb();
		HashMap<String, String> descriptions = new HashMap<String, String>();
		descriptions.put("en-US", description);
		v.setId(BASE_VERB_URI + description);
		v.setDisplay(descriptions);
		return v;
	}
	public static Verb answered(){
		return createVerb("answered");
	}	
	public static Verb asked(){
		return createVerb("asked");
	}
	public static Verb attempted(){
		return createVerb("attempted");
	}
	public static Verb attended(){
		return createVerb("attended");
	}
	public static Verb commented(){
		return createVerb("commented");
	}
	public static Verb completed(){
		return createVerb("completed");
	}
	public static Verb exited(){
		return createVerb("exited");
	}
	public static Verb experienced(){
		return createVerb("experienced");
	}
	public static Verb failed(){
		return createVerb("failed");
	}
	public static Verb imported(){
		return createVerb("imported");
	}
	public static Verb initialized(){
		return createVerb("initialized");
	}
	public static Verb interacted(){
		return createVerb("interacted");
	}
	public static Verb launched(){
		return createVerb("launched");
	}
	public static Verb mastered(){
		return createVerb("mastered");
	}
	public static Verb passed(){
		return createVerb("passed");
	}
	public static Verb preferred(){
		return createVerb("preferred");
	}
	public static Verb progressed(){
		return createVerb("progressed");
	}
	public static Verb registered(){
		return createVerb("registered");		
	}
	public static Verb responded(){
		return createVerb("responded");
	}
	public static Verb resumed(){
		return createVerb("resumed");
	}
	public static Verb scored(){
		return createVerb("scored");
	}
	public static Verb shared(){
		return createVerb("shared");
	}
	public static Verb suspended(){
		return createVerb("suspended");
	}
	public static Verb terminated(){
		return createVerb("terminated");
	}
	public static Verb voided(){
		return createVerb("voided");
	}
}
