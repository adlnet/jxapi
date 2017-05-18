package gov.adlnet.xapi.util;

import java.util.Map;

import gov.adlnet.xapi.model.Statement;
import gov.adlnet.xapi.model.StatementResult;

public class AttachmentResult {

	private String responseMessage;
	private Statement statement;
	private StatementResult statements;
	private Map<String, AttachmentAndType> attachments;

	public AttachmentResult(String inputMessage, Statement inputStatement,
			Map<String, AttachmentAndType> inputAttachments) {
		responseMessage = inputMessage;
		statement = inputStatement;
		attachments = inputAttachments;
	}

	public AttachmentResult(String inputMessage, StatementResult inputStatements,
			Map<String, AttachmentAndType> inputAttachments) {

		responseMessage = inputMessage;
		statements = inputStatements;
		attachments = inputAttachments;
	}

	public void setResponseMessage(String inputMessage) {
		responseMessage = inputMessage;
	}

	public void setXapiStatement(Statement inputStatement) {
		statement = inputStatement;
	}

	public void setXapiStatements(StatementResult inputStatements) {
		statements = inputStatements;
	}

	public void setAttachments(Map<String, AttachmentAndType> inputAttachments) {
		attachments = inputAttachments;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public Statement getXapiStatement() {
		return statement;
	}

	public StatementResult getXapiStatements() {
		return statements;
	}

	public Map<String, AttachmentAndType> getAttachment() {
		return attachments;
	}
}
