/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService.ServiceAgents;

import java.net.URI;

import microsoft.hawaii.hawaiiClientLibraryBase.HttpMethod;
import microsoft.hawaii.hawaiiClientLibraryBase.PostableServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.SetRequest;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.SetResult;

/**
 * Service Agent class to update existing KeyValueItem collection or create
 * KeyValueItem collection
 */
public class SetAgent extends PostableServiceAgent<SetRequest, SetResult> {
	/**
	 * Initializes an instance of the {@link SetAgent} class
	 * 
	 * @param request
	 *            {@link SetRequest} object
	 * @param baseUri
	 *            base URI object
	 * @param identity
	 *            client identity
	 * @param state
	 *            user defined state
	 * @throws Exception
	 */
	public SetAgent(SetRequest request, URI baseUri, ClientIdentity identity,
			Object state) throws Exception {
		super(request, SetResult.class, baseUri, identity, HttpMethod.PUT,
				state);
	}
}
