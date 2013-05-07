/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.RendezvousService.ServiceContracts;

import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class to carry the results of Rendezvous service invocation.
 */
public class NameRegistrationResult extends ServiceResult {

	/**
	 * 
	 * Initializes an instance of the {@link NameRegistrationResult} class
	 */
	public NameRegistrationResult() {
		this.name = "";
		this.id = "";
		this.secretkey = "";

	}

	/**
	 * Register name
	 */
	private String name;

	/**
	 * Associated id
	 */
	private String id;

	/**
	 * Secret key of name
	 */
	private String secretkey;

	/**
	 * 
	 * Gets the name of an end point or a group
	 * 
	 * @return String name
	 */
	@JsonProperty("Name")
	public String getName() {
		return name;
	}

	/**
	 * 
	 * Sets a name with a string value
	 * 
	 * @param name
	 *            a name of an end point or a group
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the id of the name
	 * 
	 * @return String the id of the name
	 */
	@JsonProperty("Id")
	public String getId() {
		return id;
	}

	/**
	 * 
	 * Sets the id of the name
	 * 
	 * @param id
	 *            the id of the name
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * Gets the secret key of the name
	 * 
	 * @return String the secret key
	 */
	@JsonProperty("SecretKey")
	public String getSecretkey() {
		return secretkey;
	}

	/**
	 * 
	 * Sets a secret key to the name
	 * 
	 * @param secretkey
	 *            a secret key
	 */
	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}

}
