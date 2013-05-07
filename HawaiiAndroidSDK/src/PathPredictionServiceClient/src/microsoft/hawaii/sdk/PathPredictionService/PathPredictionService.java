/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.PathPredictionService;

import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.sdk.PathPredictionService.ServiceAgents.PredictLocationAgent;
import microsoft.hawaii.sdk.PathPredictionService.ServiceContracts.PredictLocationRequest;
import microsoft.hawaii.sdk.PathPredictionService.ServiceContracts.PredictLocationResult;

/**
 * Helper class that provides access to the PathPrediction service.
 */
public class PathPredictionService {

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

	/**
	 * 
	 * Helper method to initiate the call to predict path.
	 * 
	 * @param identity
	 *            the client identity
	 * @param request
	 *            Predict location request
	 * @param completedCallback
	 *            complete listener
	 * @param state
	 *            specifies a user-defined object
	 * @throws Exception
	 */
	public static void predictLocation(ClientIdentity identity,
			PredictLocationRequest request,
			OnCompleteListener<PredictLocationResult> completedCallback,
			Object state) throws Exception {
		PredictLocationAgent agent = new PredictLocationAgent(
				HawaiiClient.getServiceBaseUri(), identity, request, state);
		ExecuteAgent(agent, completedCallback);
	}
}
