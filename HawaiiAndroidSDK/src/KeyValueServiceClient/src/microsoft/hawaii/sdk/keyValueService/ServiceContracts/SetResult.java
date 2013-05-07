/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Service result class for Set API
 * 
 */
public class SetResult extends ServiceResult {

	/**
	 * current available byte count
	 */
	private long availableByteCount;

	/**
	 * current totla KeyValueItem count
	 */
	private int totalKVPairCount;

	/**
	 * 
	 */
	public SetResult() {
	}

	/**
	 * Gets the current available byte count
	 * 
	 * @return long
	 */
	@JsonProperty("AvailableByteCount")
	public long getAvailableByteCount() {
		return this.availableByteCount;
	}

	/**
	 * Sets the current available byte count
	 * 
	 * @param availableByteCount
	 *            the availableByteCount to set
	 */
	public void setAvailableByteCount(long availableByteCount) {
		this.availableByteCount = availableByteCount;
	}

	/**
	 * Gets the current totla KeyValueItem count
	 * 
	 * @return int
	 */
	@JsonProperty("TotalKeyValuePairCount")
	public int getTotalKVPairCount() {
		return this.totalKVPairCount;
	}

	/**
	 * Sets the current totla KeyValueItem count
	 * 
	 * @param totalKVPairCount
	 *            the totalKVPairCount to set
	 */
	public void setTotalKVPairCount(int totalKVPairCount) {
		this.totalKVPairCount = totalKVPairCount;
	}

	@Override
	public String toString() {
		return String.format("%s\r\n%d: %d", super.toString(),
				this.availableByteCount, this.totalKVPairCount);
	}
}
