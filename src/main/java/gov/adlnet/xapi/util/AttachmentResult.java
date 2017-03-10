package gov.adlnet.xapi.util;

import java.util.Map;

import gov.adlnet.xapi.model.Statement;
import gov.adlnet.xapi.model.StatementResult;

/**
 * 
 * @author
 *
 */
public class AttachmentResult {

	private String responseMessage;
	private Statement statement;
	private StatementResult statements;
	private Map<String, AttachmentAndType> attachments;

	/**
	 * The <code>AttachmentResult</code> constructor used for creating
	 * attachment results.
	 * 
	 * @param inputMessage
	 *            The response message from the LRS.
	 * @param inputStatement
	 *            The xAPI statement associated with the attachments.
	 * @param inputAttachments
	 *            The attachment(s) returned from the LRS.
	 */
	public AttachmentResult(String inputMessage, Statement inputStatement,
			Map<String, AttachmentAndType> inputAttachments) {
		responseMessage = inputMessage;
		statement = inputStatement;
		attachments = inputAttachments;
	}

	/**
	 * The <code>AttachmentResult</code> constructor used for creating
	 * attachment results.
	 * 
	 * @param inputMessage
	 *            The response message from the LRS.
	 * @param inputStatements
	 *            The xAPI statement(s) associated with the attachments.
	 * @param inputAttachments
	 *            The attachment(s) returned from the LRS.
	 */
	public AttachmentResult(String inputMessage, StatementResult inputStatements,
			Map<String, AttachmentAndType> inputAttachments) {

		responseMessage = inputMessage;
		statements = inputStatements;
		attachments = inputAttachments;
	}

	/**
	 * The <code>setResponseMessage</code> sets the response message.
	 * 
	 * @param inputMessage
	 *            The response message from the LRS.
	 */
	public void setResponseMessage(String inputMessage) {
		responseMessage = inputMessage;
	}

	/**
	 * The <code>setStatements</code> sets the xAPI statement.
	 * 
	 * @param inputStatements
	 *            The xAPI statement.
	 */
	public void setXapiStatement(Statement inputStatement) {
		statement = inputStatement;
	}

	/**
	 * The <code>setStatements</code> sets the xAPI statement.
	 * 
	 * @param inputStatements
	 *            The xAPI statement.
	 */
	public void setXapiStatements(StatementResult inputStatements) {
		statements = inputStatements;
	}

	/**
	 * The <code>setAttachment</code> sets the attachment associated with the
	 * statement.
	 * 
	 * @param inputAttachments
	 *            The attachments.
	 */
	public void setAttachments(Map<String, AttachmentAndType> inputAttachments) {
		attachments = inputAttachments;
	}

	/**
	 * The <code>getResponseMessage</code> returns the response message.
	 * 
	 * @return The response message from the LRS.
	 */
	public String getResponseMessage() {
		return responseMessage;
	}

	/**
	 * The <code>getStatement</code> returns the xAPI statement.
	 * 
	 * @return The xAPI statement.
	 */
	public Statement getXapiStatement() {
		return statement;
	}

	/**
	 * The <code>getStatements</code> returns the xAPI statements.
	 * 
	 * @return The xAPI statements.
	 */
	public StatementResult getXapiStatements() {
		return statements;
	}

	/**
	 * The <code>getAttachments</code> returns the attachments associated with
	 * the statement(s).
	 * 
	 * @return The attachments.
	 */
	public Map<String, AttachmentAndType> getAttachment() {
		return attachments;
	}

}
