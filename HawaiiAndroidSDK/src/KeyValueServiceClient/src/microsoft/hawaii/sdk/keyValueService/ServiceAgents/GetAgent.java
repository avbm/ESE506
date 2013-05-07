/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService.ServiceAgents;

import java.net.URI;

import microsoft.hawaii.hawaiiClientLibraryBase.HttpMethod;
import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.UriQueryBuilder;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.keyValueService.KeyValueServiceConst;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.GetResult;

/**
 * Service Agent class to query KeyValueItem collection by specified filter
 * conditions including prefix, return size and continuation token
 */
public class GetAgent extends ServiceAgent<GetResult> {
	/**
	 * Initializes an instance of the {@link DeleteAgent} class
	 * 
	 * @param prefix
	 *            prefix string
	 * @param returnSize
	 *            size of returned result
	 * @param baseUri
	 *            base URI object
	 * @param continuationToken
	 *            continuation token
	 * @param identity
	 *            client identity
	 * @param state
	 *            user define state object
	 * @throws Exception
	 */
	public GetAgent(String prefix, int returnSize, String continuationToken,
			URI baseUri, ClientIdentity identity, Object state)
			throws Exception {
		super(GetResult.class, HttpMethod.GET, state);

		// parameter validation
		Utility.assertNotNull("baseUri", baseUri);
		Utility.assertNotNull("identity", identity);

		this.clientIdentity = identity;
		UriQueryBuilder builder = new UriQueryBuilder();
		builder.add(KeyValueServiceConst.PrefixKey, prefix);
		builder.add(KeyValueServiceConst.SizeKey, Integer.toString(returnSize));
		if (!Utility.isStringNullOrEmpty(continuationToken)) {
			builder.add(KeyValueServiceConst.ContinuationTokenKey,
					continuationToken);
		}

		URI newUri = builder.addToURI(baseUri);
		this.serviceUri = newUri;
	}
}
