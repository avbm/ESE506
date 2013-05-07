/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService.ServiceAgents;

import java.net.URI;
import java.util.Date;

import microsoft.hawaii.hawaiiClientLibraryBase.HttpMethod;
import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.UriQueryBuilder;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.keyValueService.KeyValueServiceConst;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.DeleteResult;

/**
 * Service Agent class to delete KeyValueItem collection
 */
public class DeleteAgent extends ServiceAgent<DeleteResult> {

	/**
	 * Initializes an instance of the {@link DeleteAgent} class
	 * 
	 * @param prefix
	 *            prefix string
	 * @param before
	 *            before date time
	 * @param baseUri
	 *            base URI object
	 * @param identity
	 *            client identity
	 * @param state
	 *            user define state object
	 * @throws Exception
	 */
	public DeleteAgent(String prefix, Date before, URI baseUri,
			ClientIdentity identity, Object state) throws Exception {
		super(DeleteResult.class, HttpMethod.DELETE, state);

		// parameter validation
		Utility.assertNotNull("baseUri", baseUri);
		Utility.assertNotNull("identity", identity);

		this.clientIdentity = identity;
		UriQueryBuilder builder = new UriQueryBuilder();
		builder.add(KeyValueServiceConst.PrefixKey, prefix);
		builder.add(KeyValueServiceConst.BeforeKey,
				Utility.ConvertDateToISO8601String(before));
		URI newUri = builder.addToURI(baseUri);
		this.serviceUri = newUri;
	}
}
