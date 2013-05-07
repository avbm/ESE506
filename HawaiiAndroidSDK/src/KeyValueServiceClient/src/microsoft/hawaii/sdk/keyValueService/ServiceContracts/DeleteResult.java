/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request class for Delete API
 * 
 */
public class DeleteResult extends ServiceResult {

	/**
	 * number of deleted items
	 */
	private int deletedItemNumber;

	/**
	 * flag which indicates there are still more items to delete
	 */
	private boolean moreItemsToDelete;

	/**
	 * available byte count left
	 */
	private long availableByteCount;

	/**
	 * current total key-value pair count
	 */
	private int totalKeyValuePairCount;

	/**
	 * 
	 */
	public DeleteResult() {
	}

	/**
	 * Gets the number of deleted items
	 * 
	 * @return int
	 */
	@JsonProperty("DeletedItemNumber")
	public int getDeletedItemNumber() {
		return this.deletedItemNumber;
	}

	/**
	 * Sets the number of deleted items
	 * 
	 * @param deletedItemNumber
	 *            the deletedItemNumber to set
	 */
	public void setDeletedItemNumber(int deletedItemNumber) {
		this.deletedItemNumber = deletedItemNumber;
	}

	/**
	 * Gets the flag which indicates there are still more items to delete
	 * 
	 * @return Boolean
	 */
	@JsonProperty("MoreItemsToDelete")
	public boolean getMoreItemsToDelete() {
		return this.moreItemsToDelete;
	}

	/**
	 * Sets the flag which indicates there are still more items to delete
	 * 
	 * @param moreItemsToDelete
	 *            the moreItemsToDelete to set
	 */
	public void setMoreItemsToDelete(Boolean moreItemsToDelete) {
		this.moreItemsToDelete = moreItemsToDelete;
	}

	/**
	 * Gets the available byte count left
	 * 
	 * @return long
	 */
	@JsonProperty("AvailableByteCount")
	public long getAvailableByteCount() {
		return this.availableByteCount;
	}

	/**
	 * Sets the available byte count left
	 * 
	 * @param availableByteCount
	 *            the availableByteCount to set
	 */
	public void setAvailableByteCount(long availableByteCount) {
		this.availableByteCount = availableByteCount;
	}

	/**
	 * Gets the current total key-value pair count
	 * 
	 * @return int
	 */
	@JsonProperty("TotalKeyValuePairCount")
	public int getTotalKeyValuePairCount() {
		return this.totalKeyValuePairCount;
	}

	/**
	 * Sets the current total key-value pair count
	 * 
	 * @param totalKeyValuePairCount
	 *            the totalKeyValuePairCount to set
	 */
	public void setTotalKeyValuePairCount(int totalKeyValuePairCount) {
		this.totalKeyValuePairCount = totalKeyValuePairCount;
	}
}
