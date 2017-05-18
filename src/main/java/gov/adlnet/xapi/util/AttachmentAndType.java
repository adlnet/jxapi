package gov.adlnet.xapi.util;

public class AttachmentAndType {
	private byte[] attachment;
	private String contentType;
	
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
	
	public byte[] getAttachment() {
		return attachment;
	}
	
	public void setAttachments(byte[] attachmentInput){
		if(attachmentInput == null){
			throw new IllegalArgumentException("Attachment cannot be null.");
		} else {
			attachment = attachmentInput;
		}
	}
	
	public String getType() {
		return contentType;
	}
	
	public void setType(String typeInput){
		if(typeInput == null){
			throw new IllegalArgumentException("Content type cannot be null.");
		} else {
			contentType = typeInput;
		}
	}
}
