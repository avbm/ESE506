/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Service result class for Get API
 */
public class GetResult extends ServiceResult {

	/**
	 * KeyValueItem array
	 */
	private KeyValueItem[] keyValueCollection;

	/**
	 * continuation token
	 */
	private String continuationToken;

	/**
	 * Initializes an instance of the {@link GetResult} class
	 */
	public GetResult() {
	}

	/**
	 * Gets the KeyValueItem array
	 * 
	 * @return KeyValueItem[]
	 */
	@JsonProperty("KeyValueCollection")
	public KeyValueItem[] getKeyValueCollection() {
		return this.keyValueCollection;
	}

	/**
	 * Sets the KeyValueItem array
	 * 
	 * @param keyValueCollection
	 *            the keyValueCollection to set
	 */
	public void setKeyValueCollection(KeyValueItem[] keyValueCollection) {
		this.keyValueCollection = keyValueCollection;
	}

	/**
	 * Gets the continuation token
	 * 
	 * @return String
	 */
	@JsonProperty("ContinuationToken")
	public String getContinuationToken() {
		return this.continuationToken;
	}

	/**
	 * Sets the continuation token
	 * 
	 * @param continuationToken
	 *            the continuationToken to set
	 */
	public void setContinuationToken(String continuationToken) {
		this.continuationToken = continuationToken;
	}
}
