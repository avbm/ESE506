/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService.ServiceContracts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request class for GetByKeys API
 * 
 */
public class GetByKeysRequest {

	/**
	 * keys array
	 */
	private String[] keys;

	/**
	 * Initializes an instance of the {@link GetByKeysRequest} class
	 */
	public GetByKeysRequest() {
	}

	/**
	 * Gets the key array
	 * 
	 * @return the keys
	 */
	@JsonProperty("Keys")
	public String[] getKeys() {
		return this.keys;
	}

	/**
	 * Sets the key array
	 * 
	 * @param keys
	 *            the keys to set
	 */
	public void setKeys(String[] keys) {
		this.keys = keys;
	}

}
