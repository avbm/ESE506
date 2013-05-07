/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.relayService.ServiceAgents;

import java.net.URI;

import microsoft.hawaii.hawaiiClientLibraryBase.HttpMethod;
import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.UriQueryBuilder;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.relayService.RelayServiceConst;
import microsoft.hawaii.sdk.relayService.ServiceContracts.GroupResult;

/**
 * Class agent to create a new group.
 */
public class CreateGroupAgent extends ServiceAgent<GroupResult> {
	/**
	 * Initializes an instance of the {@link CreateGroupAgent} class
	 * 
	 * @param baseUri
	 *            base URI object
	 * @param timeoutInSeconds
	 *            timeout in seconds
	 * @param identity
	 *            the client identity
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public CreateGroupAgent(URI baseUri, int timeoutInSeconds,
			ClientIdentity identity, Object state) throws Exception {
		super(GroupResult.class, HttpMethod.POST, state);

		Utility.assertNotNull("baseUri", baseUri);
		Utility.assertNotNull("identity", identity);

		this.clientIdentity = identity;

		URI uri = new URI(String.format("%s/%s", baseUri,
				RelayServiceConst.GroupSignature));
		UriQueryBuilder builder = new UriQueryBuilder();
		builder.add(RelayServiceConst.TtlKey,
				RelayServiceConst.ConvertSecondsToTimespan(timeoutInSeconds));

		this.serviceUri = builder.addToURI(uri);
	}
}
