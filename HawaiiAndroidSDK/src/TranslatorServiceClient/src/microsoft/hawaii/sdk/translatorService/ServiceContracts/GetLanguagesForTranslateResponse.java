/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.translatorService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response for the GetLanguagesForSpeak method.
 */
public class GetLanguagesForTranslateResponse extends ServiceResult {

	/**
	 * The supported languages for translate
	 */
	private Language[] SupportedLanguages;

	/**
	 * Gets the supported languages for translate
	 * 
	 * @return Language[] Returns the languages.
	 */
	@JsonProperty("SupportedLanguages")
	public Language[] getSupportedLanguages() {
		return this.SupportedLanguages;
	}

	/**
	 * Sets the supported languages for translate
	 * 
	 * @param languages
	 *            The supported languages for translate
	 */
	public void setSupportedLanguages(Language[] languages) {
		this.SupportedLanguages = languages;
	}
}
