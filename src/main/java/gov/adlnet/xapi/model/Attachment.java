package gov.adlnet.xapi.model;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bouncycastle.util.encoders.Hex;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import gov.adlnet.xapi.util.AttachmentAndType;

public class Attachment {
	private URI usageType;							//required
	private HashMap<String, String> display;		//required
	private HashMap<String, String> description;	//optional
	private String contentType;						//required
	private int length;								//required
	private String sha2;							//required
	private URI fileUrl;							//optional
	
	private final static char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	
	private static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static String generateSha2(byte[] bytes) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(bytes);
		return new String(Hex.encode(md.digest()));
	}

	public byte[] addAttachment(String attachmentInput, String contentTypeInput) throws NoSuchAlgorithmException, IOException {
		byte[] byteArray = null;
		contentType = contentTypeInput;
		length = attachmentInput.length();
		if (contentType.equals("text/plain")) {
			byteArray = attachmentInput.getBytes("UTF-8");

		} else {
			File testFile = new File(attachmentInput);
			byteArray = Files.readAllBytes(testFile.toPath());
			byteArray = bytesToHex(byteArray).getBytes();
		}
		
		sha2 = generateSha2(byteArray);
		return byteArray;
	}

	public URI getUsageType() {
		return usageType;
	}

	public void setUsageType(URI usageType) {
		this.usageType = usageType;
	}

	public HashMap<String, String> getDisplay() {
		return display;
	}

	public void setDisplay(HashMap<String, String> display) {
		this.display = display;
	}

	public HashMap<String, String> getDescription() {
		return description;
	}

	public void setDescription(HashMap<String, String> description) {
		this.description = description;
	}

	public String getContentType() {
		return contentType;
	}

//	public void setContentType(String contentType) {
//		this.contentType = contentType;
//	}

	public int getLength() {
		return length;
	}

//	public void setLength(int length) {
//		this.length = length;
//	}

	public String getSha2() {
		return sha2;
	}

//	public void setSha2(String sha2) {
//		this.sha2 = sha2;
//	}

	public URI getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(URI fileUrl) {
		this.fileUrl = fileUrl;
	}

    private JsonElement serializeHash(HashMap<String, String> map) {
        JsonObject obj = new JsonObject();
        for (Entry<String, String> item : map.entrySet()) {
            obj.addProperty(item.getKey(), item.getValue());
        }
        return obj;
    }
    
    public JsonElement serialize(){
        JsonObject obj = new JsonObject();
        if(usageType != null){
        	obj.addProperty("usageType", this.usageType.toString());
        } else {
        	throw new NullPointerException("Attachment usageType can't be null");
        }        
        
        obj.addProperty("length", this.length);
        obj.addProperty("sha2", this.sha2);
        
        if(description != null){
        	obj.add("description", this.serializeHash(this.description));
        }
        if(fileUrl != null){
        	obj.addProperty("fileUrl", this.fileUrl.toString());        
        }
        return obj;
    }
}
