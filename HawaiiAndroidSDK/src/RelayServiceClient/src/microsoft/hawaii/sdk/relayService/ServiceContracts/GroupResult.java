/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.relayService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a multicast group of communications endpoints.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupResult extends ServiceResult {
	/**
	 * The registration id assigned to this group by the relay service.
	 */
	private String registrationId;

	/**
	 * The name of the group.
	 */
	private String name;

	/**
	 * The secret key used to authenticate requests operating on this group to
	 * the relay service.
	 */
	private String secretKey;

	/**
	 * Gets the registrationId
	 * 
	 * @return String Returns the registration Id.
	 */
	@JsonProperty("RegistrationId")
	public String getRegistrationId() {
		return this.registrationId;
	}

	/**
	 * Sets the registrationId
	 * 
	 * @param registrationId
	 *            the registration Id.
	 */
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	/**
	 * Gets the name
	 * 
	 * @return String Returns the name.
	 */
	@JsonProperty("Name")
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name
	 * 
	 * @param name
	 *            the group name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the secretKey
	 * 
	 * @return String Returns the secret key.
	 */
	@JsonProperty("SecretKey")
	public String getSecretKey() {
		return this.secretKey;
	}

	/**
	 * Sets the secretKey
	 * 
	 * @param secretKey
	 *            the secret key
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
}
