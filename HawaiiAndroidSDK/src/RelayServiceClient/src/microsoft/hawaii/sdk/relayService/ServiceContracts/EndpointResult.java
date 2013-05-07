/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.relayService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a communications endpoint of the relay service.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EndpointResult extends ServiceResult {
	/**
	 * The registration id assigned to this endpoint by the relay service.
	 */
	private String registrationId;

	/**
	 * The secret key used to authenticate requests operating on this endpoint
	 * to the relay service.
	 */
	private String secretKey;

	/**
	 * The group container to hold groups of this end point.
	 */
	private Groups groups;

	/**
	 * Initializes an instance of the {@link Endpoint} class
	 */
	public EndpointResult() {
		this.groups = new Groups();
	}

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
	 *            the registration Id
	 */
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
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

	/**
	 * Gets the groups
	 * 
	 * @return Groups Returns the Groups
	 */
	@JsonProperty("Groups")
	public Groups getGroups() {
		return this.groups;
	}

	/**
	 * Sets the groups
	 * 
	 * @param groups
	 *            the Groups object
	 */
	public void setGroups(Groups groups) {
		this.groups = groups;
	}
}
