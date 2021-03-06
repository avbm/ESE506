/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Service result class for GetByKeys API
 * 
 */
public class GetByKeysResult extends ServiceResult {

	/**
	 * KeyValueItem array
	 */
	private KeyValueItem[] keyValueCollection;

	/**
	 * Initializes an instance of the {@link GetByKeysResult} class
	 */
	public GetByKeysResult() {
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
}
