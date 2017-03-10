package gov.adlnet.xapi.util;

import java.util.ArrayList;

/**
 * 
 * @author 
 *
 */
public class AttachmentAndType {
	private ArrayList<byte[]> attachment;
	private String contentType;
	
	/**
	 * 
	 * @param attachmentInput
	 * @param typeInput
	 */
	public AttachmentAndType(ArrayList<byte[]> attachmentInput, String typeInput) {
		if(attachmentInput == null){
			throw new IllegalArgumentException("Attachment cannot be null.");
		} else {
			attachment = attachmentInput;
		}
		
		if(typeInput == null){
			throw new IllegalArgumentException("Content type cannot be null.");
		} else {
			contentType = typeInput;
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<byte[]> getAttachment() {
		return attachment;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getType() {
		return contentType;
	}
}
