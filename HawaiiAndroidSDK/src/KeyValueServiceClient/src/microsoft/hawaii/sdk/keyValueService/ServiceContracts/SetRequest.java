/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService.ServiceContracts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request class for Set API
 * 
 */
public class SetRequest {

	/**
	 * KeyValueItem array
	 */
	private KeyValueItem[] items;

	/**
	 * 
	 */
	public SetRequest() {
	}

	/**
	 * Gets the KeyValueItem array
	 * 
	 * @return KeyValueItem[]
	 */
	@JsonProperty("KeyValueCollection")
	public KeyValueItem[] getKvItems() {
		return this.items;
	}

	/**
	 * Sets the KeyValueItem array
	 * 
	 * @param items
	 *            the KeyValueCollection to set
	 */
	public void setKvItems(KeyValueItem[] items) {
		this.items = items;
	}

}
