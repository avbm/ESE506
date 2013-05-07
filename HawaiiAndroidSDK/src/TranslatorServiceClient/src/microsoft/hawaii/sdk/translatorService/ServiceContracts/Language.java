/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.translatorService.ServiceContracts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class to represent the language
 */
public class Language {

	/**
	 * The language code
	 */
	private String code;

	/**
	 * The language display name
	 */
	private String name;

	/**
	 * Initializes an instance of the {@link Language} class
	 */
	public Language() {
	}

	/**
	 * Gets the language code
	 * 
	 * @return String Returns the language code.
	 */
	@JsonProperty("Code")
	public String getCode() {
		return this.code;
	}

	/**
	 * Sets the language code
	 * 
	 * @param code
	 *            the language code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the language display name
	 * 
	 * @return String Returns the language name.
	 */
	@JsonProperty("Name")
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the language display name
	 * 
	 * @param name
	 *            the language display name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
