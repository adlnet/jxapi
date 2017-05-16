package gov.adlnet.xapi.util;

/**
 * 
 * @author 
 *
 */
public class AttachmentAndType {
	private byte[] attachment;
	private String contentType;
	
	/**
	 * 
	 * @param attachmentInput
	 * @param typeInput
	 */
	public AttachmentAndType(byte[] attachmentInput, String typeInput) {
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
	public byte[] getAttachment() {
		return attachment;
	}
	
	/**
	 * 
	 * @param attachmentInput
	 */
	public void setAttachments(byte[] attachmentInput){
		if(attachmentInput == null){
			throw new IllegalArgumentException("Attachment cannot be null.");
		} else {
			attachment = attachmentInput;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getType() {
		return contentType;
	}
	
	/**
	 * 
	 * @param typeInput
	 */
	public void setType(String typeInput){
		if(typeInput == null){
			throw new IllegalArgumentException("Content type cannot be null.");
		} else {
			contentType = typeInput;
		}
	}
}
