/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService.ServiceAgents;

import java.net.URI;

import microsoft.hawaii.hawaiiClientLibraryBase.HttpMethod;
import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.UriUtility;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.GetByKeyResult;

/**
 * Service Agent class to get KeyValueItem by specified key
 */
public class GetByKeyAgent extends ServiceAgent<GetByKeyResult> {

	/**
	 * Initializes an instance of the {@link GetByKeyAgent} class
	 * 
	 * @param baseUri
	 *            base URI object
	 * @param identity
	 *            client identity
	 * @param key
	 *            the specified key
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public GetByKeyAgent(URI baseUri, ClientIdentity identity, String key,
			Object state) throws Exception {
		super(GetByKeyResult.class, HttpMethod.GET, state);

		// parameter validation
		Utility.assertNotNull("baseUri", baseUri);
		Utility.assertNotNull("identity", identity);
		Utility.assertStringNotNullOrEmpty("key", key);

		this.clientIdentity = identity;
		String encodedKey = UriUtility.safeEncode(key);
		URI newUri = new URI(String.format("%s/%s", baseUri.toString(),
				encodedKey));
		this.serviceUri = newUri;
	}
}
