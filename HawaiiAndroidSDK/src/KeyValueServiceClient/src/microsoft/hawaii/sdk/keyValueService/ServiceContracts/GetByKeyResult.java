/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Service result class for GetByKey API
 * 
 */
public class GetByKeyResult extends ServiceResult {

	/**
	 * KeyValueItem object
	 */
	private KeyValueItem keyValueItem;

	public GetByKeyResult() {
		this.keyValueItem = null;
	}

	public GetByKeyResult(KeyValueItem item) {
		this.setKeyValueItem(item);
	}

	/**
	 * Gets the KeyValueItem object
	 * 
	 * @return KeyValueItem
	 */
	@JsonProperty("KeyValueItem")
	public KeyValueItem getKeyValueItem() {
		return this.keyValueItem;
	}

	/**
	 * Sets the KeyValueItem object
	 * 
	 * @param keyValueItem
	 *            the keyValueItem to set
	 */
	public void setKeyValueItem(KeyValueItem value) {
		this.keyValueItem = value;
	}
}
