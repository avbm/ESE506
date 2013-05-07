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
 * Service Agent class to register a name to DateBase
 */
public class RegisterNameAgent extends ServiceAgent<NameRegistrationResult> {

	/**
	 * 
	 * Initializes an instance of the {@link RegisterNameAgent} class
	 * 
	 * @param baseUri
	 *            uri to access service
	 * @param clientIdentity
	 *            the client identity
	 * @param name
	 *            specifies a name of end point or group
	 * @throws Exception
	 */
	public RegisterNameAgent(URI baseUri, ClientIdentity clientIdentity,
			String name) throws Exception {
		this(baseUri, clientIdentity, name, null);
	}

	/**
	 * 
	 * Initializes an instance of the {@link RegisterNameAgent} class
	 * 
	 * @param baseUri
	 *            uri to access service
	 * @param clientIdentity
	 *            authentication identity
	 * @param name
	 *            specifies a name of end point or group
	 * @param state
	 *            specifies a user defined object
	 * @throws Exception
	 */
	public RegisterNameAgent(URI baseUri, ClientIdentity clientIdentity,
			String name, Object state) throws Exception {
		super(NameRegistrationResult.class, HttpMethod.GET, state);

		// parameter validation
		Utility.assertNotNull("baseUri", baseUri);
		Utility.assertNotNull("name", name);
		Utility.assertNotNull("identity", clientIdentity);

		// Set the client identity
		this.clientIdentity = clientIdentity;

		String encodeName = UriUtility.safeEncode(name);
		URI newUri = new URI(String.format("%s?%s=%s", baseUri.toString(),
				"Name", encodeName));
		this.serviceUri = newUri;
	}

}
