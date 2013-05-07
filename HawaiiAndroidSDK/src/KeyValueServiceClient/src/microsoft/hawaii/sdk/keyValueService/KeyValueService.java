/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService;

import java.util.Date;

import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.sdk.keyValueService.ServiceAgents.CreateAgent;
import microsoft.hawaii.sdk.keyValueService.ServiceAgents.DeleteAgent;
import microsoft.hawaii.sdk.keyValueService.ServiceAgents.DeleteByKeysAgent;
import microsoft.hawaii.sdk.keyValueService.ServiceAgents.GetAgent;
import microsoft.hawaii.sdk.keyValueService.ServiceAgents.GetByKeyAgent;
import microsoft.hawaii.sdk.keyValueService.ServiceAgents.GetByKeysAgent;
import microsoft.hawaii.sdk.keyValueService.ServiceAgents.SetAgent;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.DeleteByKeysRequest;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.DeleteResult;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.GetByKeyResult;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.GetByKeysRequest;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.GetByKeysResult;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.GetResult;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.KeyValueItem;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.SetRequest;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.SetResult;

/**
 * Helper class that provides access to the KeyValue service.
 */
public final class KeyValueService {

	/**
	 * create the specified KeyValueItem collection
	 * 
	 * @param identity
	 *            client identity object
	 * @param kvItems
	 *            the specified KeyValueItem collection
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 *             void
	 */
	public static void create(ClientIdentity identity, KeyValueItem[] kvItems,
			OnCompleteListener<SetResult> completedCallback, Object state)
			throws Exception {
		if (kvItems == null || kvItems.length == 0) {
			throw new IllegalArgumentException(
					"kvItems can not be null or empty");
		}

		// create agent object
		SetRequest request = new SetRequest();
		request.setKvItems(kvItems);
		CreateAgent agent = new CreateAgent(request,
				HawaiiClient.getServiceBaseUri(), identity, state);

		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * Gets KeyValueItem by specified key
	 * 
	 * @param identity
	 *            client identity object
	 * @param key
	 *            the specified key
	 * @param completedCallback
	 *            completed callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 *             void
	 */
	public static void getByKey(ClientIdentity identity, String key,
			OnCompleteListener<GetByKeyResult> completedCallback, Object state)
			throws Exception {
		GetByKeyAgent agent = new GetByKeyAgent(
				HawaiiClient.getServiceBaseUri(), identity, key, null);

		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * get KeyValueItem collection by specified key collection
	 * 
	 * @param identity
	 *            client identity
	 * @param keys
	 *            specified key collection
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 *             void
	 */
	public static void getByKeys(ClientIdentity identity, String[] keys,
			OnCompleteListener<GetByKeysResult> completedCallback, Object state)
			throws Exception {
		if (keys == null || keys.length == 0) {
			throw new IllegalArgumentException("keys can not be null or empty");
		}

		GetByKeysRequest request = new GetByKeysRequest();
		request.setKeys(keys);
		GetByKeysAgent agent = new GetByKeysAgent(request,
				HawaiiClient.getServiceBaseUri(), identity, state);
		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * get KeyValueItem collection by specified filter conditions including
	 * prefix, return size and continuation token
	 * 
	 * @param identity
	 *            client identity
	 * @param prefix
	 *            prefix
	 * @param returnSize
	 *            return size
	 * @param continuationToken
	 *            continuation token
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 *             void
	 */
	public static void get(ClientIdentity identity, String prefix,
			int returnSize, String continuationToken,
			OnCompleteListener<GetResult> completedCallback, Object state)
			throws Exception {
		if (returnSize <= 0) {
			throw new IllegalArgumentException(
					"return size should be greater than 0");
		}

		GetAgent agent = new GetAgent(prefix, returnSize, continuationToken,
				HawaiiClient.getServiceBaseUri(), identity, state);
		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * Create or update specifed KeyValueItem collection
	 * 
	 * @param identity
	 *            client identity object
	 * @param kvItems
	 *            the specified KeyValueItem collection
	 * @param completedCallback
	 *            completed callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 *             void
	 */
	public static void upset(ClientIdentity identity, KeyValueItem[] kvItems,
			OnCompleteListener<SetResult> completedCallback, Object state)
			throws Exception {
		if (kvItems == null || kvItems.length == 0) {
			throw new IllegalArgumentException(
					"kvItems can not be null or empty");
		}

		SetRequest request = new SetRequest();
		request.setKvItems(kvItems);
		SetAgent agent = new SetAgent(request,
				HawaiiClient.getServiceBaseUri(), identity, state);

		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * delete KeyValueItem collection by specifed filter conditions including
	 * prefix and before date time
	 * 
	 * @param identity
	 *            client identity
	 * @param prefix
	 *            prefix
	 * @param before
	 *            before date time
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state
	 * @throws Exception
	 *             void
	 */
	public static void delete(ClientIdentity identity, String prefix,
			Date before, OnCompleteListener<DeleteResult> completedCallback,
			Object state) throws Exception {
		DeleteAgent agent = new DeleteAgent(prefix, before,
				HawaiiClient.getServiceBaseUri(), identity, state);
		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * delete KeyValueItem collection by specifed keys collection
	 * 
	 * @param identity
	 *            client identity object
	 * @param keys
	 *            the specified keys collection
	 * @param completedCallback
	 *            completed callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 *             void
	 */
	public static void deleteByKeys(ClientIdentity identity, String[] keys,
			OnCompleteListener<DeleteResult> completedCallback, Object state)
			throws Exception {
		if (keys == null || keys.length == 0) {
			throw new IllegalArgumentException("keys can not be null or empty");
		}

		DeleteByKeysRequest request = new DeleteByKeysRequest();
		request.setKeys(keys);
		DeleteByKeysAgent agent = new DeleteByKeysAgent(request,
				HawaiiClient.getServiceBaseUri(), identity, state);

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
	 */
	private static <T extends ServiceResult> void ExecuteAgent(
			ServiceAgent<T> agent, OnCompleteListener<T> completedCallback)
			throws Exception {
		agent.processRequest(completedCallback);
	}
}