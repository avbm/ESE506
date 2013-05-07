/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.relayService.ServiceAgents;

import java.net.URI;

import microsoft.hawaii.hawaiiClientLibraryBase.HttpMethod;
import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.Exceptions.HawaiiException;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.UriQueryBuilder;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.UriUtility;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.relayService.HawaiiClient;
import microsoft.hawaii.sdk.relayService.RelayServiceConst;
import microsoft.hawaii.sdk.relayService.ServiceContracts.MessageResult;

/**
 * Class agent to send messages.
 */
public class SendMessageAgent extends ServiceAgent<MessageResult> {
	/**
	 * The message content
	 */
	private byte[] message;

	/**
	 * Initializes an instance of the {@link SendMessageAgent} class
	 * 
	 * @param message
	 *            the message content
	 * @param fromRegistrationId
	 *            the message sent from
	 * @param recipientIds
	 *            the message recipients
	 * @param baseUri
	 *            base URI object
	 * @param identity
	 *            the client identity
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public SendMessageAgent(byte[] message, String fromRegistrationId,
			String recipientIds, URI baseUri, ClientIdentity identity,
			Object state) throws Exception {
		super(MessageResult.class, HttpMethod.POST, state);
		Utility.assertNotNull("baseUri", baseUri);
		Utility.assertNotNull("identity", identity);

		this.clientIdentity = identity;
		this.message = message;

		int timeoutInSeconds = Integer.MAX_VALUE;
		URI uri = new URI(String.format("%s/%s/%s",
				HawaiiClient.getServiceBaseUri(),
				RelayServiceConst.EndPointSignature,
				UriUtility.safeEncode(fromRegistrationId)));

		UriQueryBuilder builder = new UriQueryBuilder();
		builder.add(RelayServiceConst.TtlKey,
				RelayServiceConst.ConvertSecondsToTimespan(timeoutInSeconds));
		builder.add(RelayServiceConst.ToKey, recipientIds);

		this.serviceUri = builder.addToURI(uri);
	}

	/*
	 * Directly returns the message byte[].
	 * 
	 * @see
	 * microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent#getRequestBodyData
	 * ()
	 */
	@Override
	protected byte[] getRequestBodyData() throws HawaiiException {
		return this.message;
	}

	/*
	 * Override the base method returns false.
	 * 
	 * @see microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent#
	 * parseServiceResultRequired()
	 */
	@Override
	protected boolean parseServiceResultRequired() {
		return false;
	}
}
