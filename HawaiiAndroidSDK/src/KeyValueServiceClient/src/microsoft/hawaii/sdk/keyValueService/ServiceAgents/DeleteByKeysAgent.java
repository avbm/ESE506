/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService.ServiceAgents;

import java.net.URI;

import microsoft.hawaii.hawaiiClientLibraryBase.HttpMethod;
import microsoft.hawaii.hawaiiClientLibraryBase.PostableServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.sdk.keyValueService.KeyValueServiceConst;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.DeleteByKeysRequest;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.DeleteResult;

/**
 * Service Agent class to delete KeyValueItem collection by specified key
 * collection
 */
public class DeleteByKeysAgent extends
		PostableServiceAgent<DeleteByKeysRequest, DeleteResult> {

	/**
	 * Initializes an instance of the {@link DeleteByKeysAgent} class
	 * 
	 * @param request
	 *            {@link DeleteByKeysRequest} object
	 * @param baseUri
	 *            base URI object
	 * @param identity
	 *            client identity
	 * @param state
	 *            user defined state
	 * @throws Exception
	 */
	public DeleteByKeysAgent(DeleteByKeysRequest request, URI baseUri,
			ClientIdentity identity, Object state) throws Exception {
		super(request, DeleteResult.class, new URI(String.format("%s/%s",
				baseUri.toString(), KeyValueServiceConst.BatchOperationKey)),
				identity, HttpMethod.DELETE, state);
	}
}
