package gov.adlnet.xapi.model;

import java.net.URI;
import java.util.HashMap;

public class Attachment {
	private URI usageType;
	private HashMap<String, String> display;
	private HashMap<String, String> description;
	private String contentType;
	private int length;
	private String sha2;
	private URI fileUr;
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
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getSha2() {
		return sha2;
	}
	public void setSha2(String sha2) {
		this.sha2 = sha2;
	}
	public URI getFileUr() {
		return fileUr;
	}
	public void setFileUr(URI fileUr) {
		this.fileUr = fileUr;
	}
}
