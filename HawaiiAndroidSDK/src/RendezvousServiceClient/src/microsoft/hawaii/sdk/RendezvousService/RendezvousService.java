/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.RendezvousService;

import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.sdk.RendezvousService.ServiceAgent.AssociateIdAgent;
import microsoft.hawaii.sdk.RendezvousService.ServiceAgent.DisassociateIdAgent;
import microsoft.hawaii.sdk.RendezvousService.ServiceAgent.LookupNameAgent;
import microsoft.hawaii.sdk.RendezvousService.ServiceAgent.RegisterNameAgent;
import microsoft.hawaii.sdk.RendezvousService.ServiceAgent.UnregisterNameAgent;
import microsoft.hawaii.sdk.RendezvousService.ServiceContracts.NameRegistrationResult;

/**
 * Helper class that provides access to the Rendezvous service.
 */
public class RendezvousService {

	/**
	 * 
	 * Helper method to initiate the call to look up name from DB.
	 * 
	 * @param identity
	 *            the client identity
	 * @param name
	 *            specifies a name of end point or group
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            specifies a user-defined object
	 * @throws Exception
	 *             void
	 */
	public static void lookupName(ClientIdentity identity, String name,
			OnCompleteListener<NameRegistrationResult> completedCallback,
			Object state) throws Exception {
		LookupNameAgent agent = new LookupNameAgent(
				HawaiiClient.getServiceBaseUri(), identity, name, state);
		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * 
	 * Helper method to initiate the call to register name to DB.
	 * 
	 * @param identity
	 *            the client identity
	 * @param name
	 *            specifies a name of end point or group
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            specifies a user-defined object
	 * @throws Exception
	 *             void
	 */
	public static void registerName(ClientIdentity identity, String name,
			OnCompleteListener<NameRegistrationResult> completedCallback,
			Object state) throws Exception {
		RegisterNameAgent agent = new RegisterNameAgent(
				HawaiiClient.getServiceBaseUri(), identity, name, state);
		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * 
	 * Helper method to initiate the call to unregister name from DB.
	 * 
	 * @param identity
	 *            the client identity
	 * @param nameRegistration
	 *            specifies a name registration object
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            specifies a user-defined object
	 * @throws Exception
	 *             void
	 */
	public static void unRegisterName(ClientIdentity identity,
			NameRegistrationResult nameRegistration,
			OnCompleteListener<NameRegistrationResult> completedCallback,
			Object state) throws Exception {
		UnregisterNameAgent agent = new UnregisterNameAgent(
				HawaiiClient.getServiceBaseUri(), identity, nameRegistration,
				state);
		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * 
	 * Helper method to initiate the call to associateId
	 * 
	 * @param identity
	 *            the client identity
	 * @param nameRegistration
	 *            specifies a name registration object
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            specifies a user-defined object
	 * @throws Exception
	 *             void
	 */
	public static void associateId(ClientIdentity identity,
			NameRegistrationResult nameRegistration,
			OnCompleteListener<NameRegistrationResult> completedCallback,
			Object state) throws Exception {
		AssociateIdAgent agent = new AssociateIdAgent(
				HawaiiClient.getServiceBaseUri(), identity, nameRegistration,
				state);
		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * 
	 * Helper method to initiate the call to disAssociateId dissociate id from
	 * name
	 * 
	 * @param identity
	 *            the client identity
	 * @param nameRegistration
	 *            specifies a name registration object
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            specifies a user-defined object
	 * @throws Exception
	 *             void
	 */
	public static void disAssociateId(ClientIdentity identity,
			NameRegistrationResult nameRegistration,
			OnCompleteListener<NameRegistrationResult> completedCallback,
			Object state) throws Exception {
		DisassociateIdAgent agent = new DisassociateIdAgent(
				HawaiiClient.getServiceBaseUri(), identity, nameRegistration,
				state);
		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * Execute specified ServiceAgent object
	 * 
	 * @param agent
	 *            the specified ServiceAgent object
	 * @param completedCallback
	 *            completion callback
	 * @throws Exception
	 *             void
	 */
	private static <T extends ServiceResult> void ExecuteAgent(
			ServiceAgent<T> agent, OnCompleteListener<T> completedCallback)
			throws Exception {
		agent.processRequest(completedCallback);
	}
}
