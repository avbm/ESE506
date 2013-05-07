/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.translatorService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response for the Translate method.
 */
public class TranslateResponse extends ServiceResult {

	/**
	 * The text to be translated in target language
	 */
	private String TextTranslated;

	/**
	 * Gets the text translated
	 * 
	 * @return String Returns the text translated.
	 */
	@JsonProperty("TextTranslated")
	public String getTextTranslated() {
		return this.TextTranslated;
	}

	/**
	 * Sets the text translated
	 * 
	 * @param textTranslated
	 *            the text translated to set
	 */
	public void setTextTranslated(String textTranslated) {
		this.TextTranslated = textTranslated;
	}
}
