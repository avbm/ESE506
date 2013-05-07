/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.RendezvousService.ServiceAgent;

import java.net.URI;

import microsoft.hawaii.hawaiiClientLibraryBase.HttpMethod;
import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.UriUtility;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.RendezvousService.ServiceContracts.NameRegistrationResult;

/**
 * Service Agent class to unregister (delete) a Name with the service.
 */
public class UnregisterNameAgent extends ServiceAgent<NameRegistrationResult> {

	/**
	 * 
	 * Initializes an instance of the {@link UnregisterNameAgent} class
	 * 
	 * @param baseUri
	 *            uri to access service
	 * @param clientIdentity
	 *            the client identity
	 * @param registration
	 *            specifies a name registration object
	 * @throws Exception
	 */
	public UnregisterNameAgent(URI baseUri, ClientIdentity clientIdentity,
			NameRegistrationResult registration) throws Exception {
		this(baseUri, clientIdentity, registration, null);
	}

	/**
	 * 
	 * Initializes an instance of the {@link UnregisterNameAgent} class
	 * 
	 * @param baseUri
	 *            uri to access service
	 * @param clientIdentity
	 *            the client identity
	 * @param registration
	 *            specifies a name registration object
	 * @param state
	 *            specifies a user defined object
	 * @throws Exception
	 */
	public UnregisterNameAgent(URI baseUri, ClientIdentity clientIdentity,
			NameRegistrationResult registration, Object state) throws Exception {
		super(NameRegistrationResult.class, HttpMethod.DELETE, state);
		// parameter validation
		Utility.assertNotNull("baseUri", baseUri);
		Utility.assertNotNull("registration", registration);
		Utility.assertNotNull("identity", clientIdentity);

		clientIdentity.setRegistrationId(registration.getName());
		clientIdentity.setSecretKey(registration.getSecretkey());

		// set the client identity
		this.clientIdentity = clientIdentity;

		String encodeName = UriUtility.safeEncode(registration.getName());
		URI newUri = new URI(String.format("%s/%s", baseUri.toString(),
				encodeName));
		this.serviceUri = newUri;
	}

	/**
	 * Override {@link parseServiceResultRequired} method of
	 * {@link ServiceAgent} class
	 */
	@Override
	protected boolean parseServiceResultRequired() {
		return false;
	}
}
