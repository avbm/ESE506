/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.translatorService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response for the GetLanguagesForSpeak method.
 */
public class GetLanguagesForSpeakResponse extends ServiceResult {

	/**
	 * The supported languages for speak
	 */
	private Language[] supportedLanguages;

	/**
	 * Gets the supported languages for speak
	 * 
	 * @return Language[] Returns the languages.
	 */
	@JsonProperty("SupportedLanguages")
	public Language[] getSupportedLanguages() {
		return this.supportedLanguages;
	}

	/**
	 * Sets the supported languages for speak
	 * 
	 * @param languages
	 *            The supported languages for speak
	 */
	public void setSupportedLanguages(Language[] languages) {
		this.supportedLanguages = languages;
	}
}
