/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.relayService;

import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.sdk.relayService.ServiceAgents.CreateEndPointAgent;
import microsoft.hawaii.sdk.relayService.ServiceAgents.CreateGroupAgent;
import microsoft.hawaii.sdk.relayService.ServiceAgents.DeleteEndPointAgent;
import microsoft.hawaii.sdk.relayService.ServiceAgents.DeleteGroupAgent;
import microsoft.hawaii.sdk.relayService.ServiceAgents.JoinGroupAgent;
import microsoft.hawaii.sdk.relayService.ServiceAgents.LeaveGroupAgent;
import microsoft.hawaii.sdk.relayService.ServiceAgents.ReceiveMessagesAgent;
import microsoft.hawaii.sdk.relayService.ServiceAgents.SendMessageAgent;
import microsoft.hawaii.sdk.relayService.ServiceContracts.EndpointResult;
import microsoft.hawaii.sdk.relayService.ServiceContracts.GroupResult;
import microsoft.hawaii.sdk.relayService.ServiceContracts.MessageResult;

/**
 * Helper class that provides access to the Relay service.
 */
public class RelayService {

	/**
	 * Helper method to initiate the call that creates an endpoint.
	 * 
	 * @param identity
	 *            the client identity
	 * @param name
	 *            Specifies a name of the endpoint
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public static void createEndPoint(ClientIdentity identity, String name,
			OnCompleteListener<EndpointResult> completedCallback, Object state)
			throws Exception {

		CreateEndPointAgent agent = new CreateEndPointAgent(
				HawaiiClient.getServiceBaseUri(), name, identity, state);
		executeAgent(agent, completedCallback);
	}

	/**
	 * Helper method to initiate the call that creates a new group.
	 * 
	 * @param identity
	 *            client identity
	 * @param timeoutInSeconds
	 *            Specifies the time to live in the service
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public static void createGroup(ClientIdentity identity,
			int timeoutInSeconds,
			OnCompleteListener<GroupResult> completedCallback, Object state)
			throws Exception {

		CreateGroupAgent agent = new CreateGroupAgent(
				HawaiiClient.getServiceBaseUri(), timeoutInSeconds, identity,
				state);
		executeAgent(agent, completedCallback);
	}

	/**
	 * Helper method to initiate the call that deletes an endpoint.
	 * 
	 * @param identity
	 *            client identity
	 * @param registrationId
	 *            the endpoint registrationId
	 * @param secretKey
	 *            the endpoint secretKey
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public static void deleteEndPoint(ClientIdentity identity,
			String registrationId, String secretKey,
			OnCompleteListener<EndpointResult> completedCallback, Object state)
			throws Exception {

		identity.setRegistrationId(registrationId);
		identity.setSecretKey(secretKey);

		DeleteEndPointAgent agent = new DeleteEndPointAgent(
				HawaiiClient.getServiceBaseUri(), registrationId, identity,
				state);
		executeAgent(agent, completedCallback);
	}

	/**
	 * Helper method to initiate the call that deletes a group.
	 * 
	 * @param identity
	 *            client identity
	 * @param registrationId
	 *            the group registrationId
	 * @param secretKey
	 *            the group secretKey
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public static void deleteGroup(ClientIdentity identity,
			String registrationId, String secretKey,
			OnCompleteListener<GroupResult> completedCallback, Object state)
			throws Exception {

		identity.setRegistrationId(registrationId);
		identity.setSecretKey(secretKey);

		DeleteGroupAgent agent = new DeleteGroupAgent(
				HawaiiClient.getServiceBaseUri(), registrationId, identity,
				state);
		executeAgent(agent, completedCallback);
	}

	/**
	 * Helper method to initiate the call that will join an endpoint to a group.
	 * 
	 * @param identity
	 *            the client identity
	 * @param groupRegistrationId
	 *            the group registrationId
	 * @param endpointRegistrationId
	 *            the endpoint registrationId
	 * @param secretKey
	 *            the endpoint secretKey
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public static void joinGroup(ClientIdentity identity,
			String groupRegistrationId, String endpointRegistrationId,
			String secretKey,
			OnCompleteListener<GroupResult> completedCallback, Object state)
			throws Exception {

		identity.setRegistrationId(endpointRegistrationId);
		identity.setSecretKey(secretKey);

		JoinGroupAgent agent = new JoinGroupAgent(
				HawaiiClient.getServiceBaseUri(), groupRegistrationId,
				endpointRegistrationId, identity, state);
		executeAgent(agent, completedCallback);
	}

	/**
	 * Helper method to initiate the call that will have an endpoint leave from
	 * a group.
	 * 
	 * @param identity
	 *            the client identity
	 * @param groupRegistrationId
	 *            the group registrationId
	 * @param endpointRegistrationId
	 *            the endpoint registrationId
	 * @param secretKey
	 *            the endpoint secretKey
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public static void leaveGroup(ClientIdentity identity,
			String groupRegistrationId, String endpointRegistrationId,
			String secretKey,
			OnCompleteListener<GroupResult> completedCallback, Object state)
			throws Exception {

		identity.setRegistrationId(endpointRegistrationId);
		identity.setSecretKey(secretKey);

		LeaveGroupAgent agent = new LeaveGroupAgent(
				HawaiiClient.getServiceBaseUri(), groupRegistrationId,
				endpointRegistrationId, identity, state);
		executeAgent(agent, completedCallback);
	}

	/**
	 * Helper method to initiate the call that will receive a message.
	 * 
	 * @param identity
	 *            client identity
	 * @param registrationId
	 *            the endpoint registrationId
	 * @param secretKey
	 *            the endpoint secretKey
	 * @param filter
	 *            Specifies a list of registration ids for Endpoints and/or
	 *            Groups that identify senders and/or group recipients of
	 *            desired messages.
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public static void receiveMessage(ClientIdentity identity,
			String registrationId, String secretKey, String filter,
			OnCompleteListener<MessageResult> completedCallback, Object state)
			throws Exception {

		identity.setRegistrationId(registrationId);
		identity.setSecretKey(secretKey);

		ReceiveMessagesAgent agent = new ReceiveMessagesAgent(
				HawaiiClient.getServiceBaseUri(), identity, registrationId,
				filter, state);
		executeAgent(agent, completedCallback);
	}

	/**
	 * Helper method to initiate the call that will send a message.
	 * 
	 * @param identity
	 *            client identity
	 * @param message
	 *            the message content
	 * @param fromRegistrationId
	 *            the endpoint registrationId
	 * @param recipientIds
	 *            the recipients ids
	 * @param secretKey
	 *            the endpoint secretKey
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public static void sendMessage(ClientIdentity identity, byte[] message,
			String fromRegistrationId, String recipientIds, String secretKey,
			OnCompleteListener<MessageResult> completedCallback, Object state)
			throws Exception {

		identity.setRegistrationId(fromRegistrationId);
		identity.setSecretKey(secretKey);

		SendMessageAgent agent = new SendMessageAgent(message,
				fromRegistrationId, recipientIds,
				HawaiiClient.getServiceBaseUri(), identity, state);
		executeAgent(agent, completedCallback);
	}

	/**
	 * Execute specified ServiceAgent object
	 * 
	 * @param agent
	 *            the specified ServiceAgent object
	 * @param completedCallback
	 *            completion callback
	 * @throws Exception
	 */
	private static <T extends ServiceResult> void executeAgent(
			ServiceAgent<T> agent, OnCompleteListener<T> completedCallback)
			throws Exception {
		agent.processRequest(completedCallback);
	}
}
