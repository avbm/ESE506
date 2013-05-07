/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sampleapp.rendezvousservice;

import microsoft.hawaii.sampleappbase.HawaiiBaseApplication;
import microsoft.hawaii.sdk.RendezvousService.ServiceContracts.NameRegistrationResult;

/**
 *
 */
public class RendezvousApplication extends HawaiiBaseApplication {

	private String name;
	private String registrationId;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the registrationId
	 */
	public String getRegistrationId() {
		return registrationId;
	}

	/**
	 * @param registrationId
	 *            the registrationId to set
	 */
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	public void setAll(NameRegistrationResult result) {
		this.name = result.getName();
		this.registrationId = result.getId();
	}

}
