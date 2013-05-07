/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.relayService.ServiceContracts;

/**
 * Represents a message received from the relay service.
 */
public class Message {
	/**
	 * A value indicating whether or not the message was constructed properly.
	 */
	private Boolean valid;

	/**
	 * A registration id of the sender of this message.
	 */
	private String from;

	/**
	 * A registration id(s) of the recipient(s) of this message.
	 */
	private String to;

	/**
	 * The message body.
	 */
	private byte[] body;

	/**
	 * Initializes an instance of the {@link Message} class
	 */
	public Message() {

	}

	/**
	 * Gets the valid
	 * 
	 * @return Boolean Returns Boolean value indicating whether this is valid.
	 */
	public Boolean getValid() {
		return this.valid;
	}

	/**
	 * Sets the valid
	 * 
	 * @param valid
	 *            A Boolean value indicating whether this is valid.
	 */
	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	/**
	 * Gets the from
	 * 
	 * @return String Returns the message is from whom.
	 */
	public String getFrom() {
		return this.from;
	}

	/**
	 * Sets the from
	 * 
	 * @param from
	 *            A String value indicating where is this message from.
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Gets the to
	 * 
	 * @return String Returns the message is to whom.
	 */
	public String getTo() {
		return this.to;
	}

	/**
	 * Sets the to
	 * 
	 * @param to
	 *            A String value indicating this message is to whom.
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * Gets the body
	 * 
	 * @return byte[] Returns the message body
	 */
	public byte[] getBody() {
		return this.body;
	}

	/**
	 * Sets the body
	 * 
	 * @param body
	 *            the message body
	 */
	public void setBody(byte[] body) {
		this.body = body;
	}
}
