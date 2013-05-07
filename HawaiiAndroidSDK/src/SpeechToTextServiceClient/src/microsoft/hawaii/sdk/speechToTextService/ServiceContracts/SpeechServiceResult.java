/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.speechToTextService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the result of the Speech-to-Text processing.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpeechServiceResult extends ServiceResult {
	/**
	 * The error message if an error happens during the Speech-to-Text
	 * translation.
	 */
	private String internalErrorMessage;
	/**
	 * The list of items obtained after the speech-to-text translation
	 */
	private String[] items;

	/**
	 * Gets the internalErrorMessage
	 * 
	 * @return String Returns the internal error message.
	 */
	@JsonProperty("InternalErrorMessage")
	public String getInternalErrorMessage() {
		return this.internalErrorMessage;
	}

	/**
	 * Sets the internalErrorMessage
	 * 
	 * @param internalErrorMessage
	 *            the internal error message
	 */
	public void setInternalErrorMessage(String internalErrorMessage) {
		this.internalErrorMessage = internalErrorMessage;
	}

	/**
	 * Gets the items
	 * 
	 * @return List<String> Returns the items.
	 */
	@JsonProperty("Items")
	public String[] getItems() {
		return this.items;
	}

	/**
	 * Sets the items
	 * 
	 * @param items
	 *            the items
	 */
	public void setItems(String[] items) {
		this.items = items;
	}
}
