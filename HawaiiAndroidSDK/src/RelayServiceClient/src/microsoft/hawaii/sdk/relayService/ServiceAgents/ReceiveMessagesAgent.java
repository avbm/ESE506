/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.relayService.ServiceAgents;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

import microsoft.hawaii.hawaiiClientLibraryBase.HttpMethod;
import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.Status;
import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceFault;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Json;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.UriQueryBuilder;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.UriUtility;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.relayService.HawaiiClient;
import microsoft.hawaii.sdk.relayService.RelayServiceConst;
import microsoft.hawaii.sdk.relayService.ServiceContracts.Message;
import microsoft.hawaii.sdk.relayService.ServiceContracts.MessageReader;
import microsoft.hawaii.sdk.relayService.ServiceContracts.MessageResult;

/**
 * Class agent to receive messages.
 */
public class ReceiveMessagesAgent extends ServiceAgent<MessageResult> {
	/**
	 * Initializes an instance of the {@link ReceiveMessagesAgent} class
	 * 
	 * @param baseUri
	 *            the service base URI
	 * @param identity
	 *            the client identity
	 * @param registrationId
	 *            the endpoint registrationId
	 * @param filter
	 *            Specifies a list of registration ids for Endpoints and/or
	 *            Groups that identify senders and/or group recipients of
	 *            desired messages.
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public ReceiveMessagesAgent(URI baseUri, ClientIdentity identity,
			String registrationId, String filter, Object state)
			throws Exception {
		super(MessageResult.class, HttpMethod.GET, state);

		Utility.assertNotNull("baseUri", baseUri);
		Utility.assertNotNull("identity", identity);

		this.clientIdentity = identity;

		UriQueryBuilder builder = new UriQueryBuilder();
		builder.add(RelayServiceConst.FilterKey, filter);
		builder.add(RelayServiceConst.WaitKey,
				RelayServiceConst.ConvertSecondsToTimespan(0));

		URI uri = new URI(String.format("%s/%s/%s",
				HawaiiClient.getServiceBaseUri(),
				RelayServiceConst.EndPointSignature,
				UriUtility.safeEncode(registrationId)));

		this.serviceUri = builder.addToURI(uri);
	}

	/*
	 * Override base method with application/xml content type.
	 * 
	 * @see
	 * microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent#getContentType()
	 */
	@Override
	public String getContentType() {
		return "application/xml";
	}

	/*
	 * Override the base method with specific message parsing.
	 * 
	 * @see
	 * microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent#parseOutput(java
	 * .io.InputStream)
	 */
	@Override
	protected void parseOutput(InputStream inputStream) {
		try {
			if (this.serviceResult.getStatus() == Status.Success) {
				MessageReader messageReader = new MessageReader();
				List<Message> messages = messageReader.getMessages(inputStream);

				Message[] messagsArray = new Message[messages.size()];
				messages.toArray(messagsArray);
				this.serviceResult.setMessages(messagsArray);
			} else {
				ServiceFault fault = Json.deserializeFromStream(
						ServiceFault.class, inputStream);
				if (fault != null) {
					this.handleException(fault.getMessage(), null);
				}
			}
		} catch (Exception ex) {
			this.handleException(
					"Failed to parse service result object from response", ex);
		}
	}
}
