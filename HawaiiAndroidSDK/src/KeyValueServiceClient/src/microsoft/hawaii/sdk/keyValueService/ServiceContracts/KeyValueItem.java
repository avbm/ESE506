/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService.ServiceContracts;

import java.util.Date;

import microsoft.hawaii.hawaiiClientLibraryBase.Util.CustomDateDeserializer;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.CustomDateSerializer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * KeyValueItem entity class
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyValueItem {

	/**
	 * key
	 */
	private String key;

	/**
	 * value
	 */
	private String value;

	/**
	 * last modified date
	 */
	private Date lastModifiedDate;

	/**
	 * Initializes an instance of the {@link KeyValueItem} class
	 */
	public KeyValueItem() {
		this(null, null, null);
	}

	/**
	 * Initializes an instance of the {@link KeyValueItem} class
	 * 
	 * @param key
	 */
	public KeyValueItem(String key) {
		this(key, null, null);
	}

	/**
	 * Initializes an instance of the {@link KeyValueItem} class
	 * 
	 * @param key
	 * @param value
	 * @param datetime
	 */
	public KeyValueItem(String key, String value, Date datetime) {
		this.key = key;
		this.value = value;
		this.lastModifiedDate = datetime;
	}

	/**
	 * Gets the key
	 * 
	 * @return String
	 */
	@JsonProperty("Key")
	public String getKey() {
		return this.key;
	}

	/**
	 * Gets the value
	 * 
	 * @return String
	 */
	@JsonProperty("Value")
	public String getValue() {
		return this.value;
	}

	/**
	 * Gets the last modified date
	 * 
	 * @return Date
	 */
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	@JsonProperty("LastModifiedDate")
	public Date getDatetime() {
		return this.lastModifiedDate;
	}

	/**
	 * Sets the key
	 * 
	 * @param key
	 *            void
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Sets the value
	 * 
	 * @param value
	 *            void
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Sets the last modified date
	 * 
	 * @param value
	 *            void
	 */
	public void setDatetime(Date value) {
		this.lastModifiedDate = value;
	}

	@Override
	public String toString() {
		return this.key + " " + this.value + " "
				+ this.lastModifiedDate.toString();
	}
}
