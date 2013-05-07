/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.relayService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to carry the results of relay service invocation.
 */
public class MessageResult extends ServiceResult {
	/**
	 * The message instance
	 */
	private Message[] messages;

	/**
	 * Gets the messages
	 * 
	 * @return Message[] Returns the messages.
	 */
	@JsonProperty("Messages")
	public Message[] getMessages() {
		return this.messages;
	}

	/**
	 * Sets the messages
	 * 
	 * @param messages
	 *            the messages
	 */
	public void setMessages(Message[] messages) {
		this.messages = messages;
	}
}
