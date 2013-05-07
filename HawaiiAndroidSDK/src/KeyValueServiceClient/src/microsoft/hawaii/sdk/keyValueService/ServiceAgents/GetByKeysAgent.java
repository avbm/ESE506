/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService.ServiceAgents;

import java.net.URI;

import microsoft.hawaii.hawaiiClientLibraryBase.HttpMethod;
import microsoft.hawaii.hawaiiClientLibraryBase.PostableServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.sdk.keyValueService.KeyValueServiceConst;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.GetByKeysRequest;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.GetByKeysResult;

/**
 * Service Agent class to get KeyValueItem collection by key collection
 */
public class GetByKeysAgent extends
		PostableServiceAgent<GetByKeysRequest, GetByKeysResult> {
	/**
	 * Initializes an instance of the {@link GetByKeysAgent} class
	 * 
	 * @param request
	 *            {@link GetByKeysRequest} object
	 * @param baseUri
	 *            base URI object
	 * @param identity
	 *            client identity
	 * @param state
	 *            user defined state
	 * @throws Exception
	 */
	public GetByKeysAgent(GetByKeysRequest request, URI baseUri,
			ClientIdentity identity, Object state) throws Exception {
		super(request, GetByKeysResult.class, new URI(String.format("%s/%s",
				baseUri.toString(), KeyValueServiceConst.BatchOperationKey)),
				identity, HttpMethod.POST, state);
	}
}
