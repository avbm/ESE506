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
import microsoft.hawaii.sdk.relayService.ServiceContracts.EndpointResult;

/**
 * Class agent to create a new end point.
 */
public class CreateEndPointAgent extends ServiceAgent<EndpointResult> {

	/**
	 * Initializes an instance of the {@link CreateEndPointAgent} class
	 * 
	 * @param baseUri
	 *            base URI object
	 * @param name
	 *            the endpoint name
	 * @param identity
	 *            the client identity
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public CreateEndPointAgent(URI baseUri, String name,
			ClientIdentity identity, Object state) throws Exception {
		super(EndpointResult.class, HttpMethod.POST, state);
		Utility.assertNotNull("baseUri", baseUri);
		Utility.assertNotNull("identity", identity);

		this.clientIdentity = identity;

		int timeoutInSeconds = Integer.MAX_VALUE;
		URI uri = new URI(String.format("%s/%s", baseUri,
				RelayServiceConst.EndPointSignature));
		UriQueryBuilder builder = new UriQueryBuilder();
		builder.add(RelayServiceConst.NameKey, name);
		builder.add(RelayServiceConst.TtlKey,
				RelayServiceConst.ConvertSecondsToTimespan(timeoutInSeconds));

		this.serviceUri = builder.addToURI(uri);
	}
}
