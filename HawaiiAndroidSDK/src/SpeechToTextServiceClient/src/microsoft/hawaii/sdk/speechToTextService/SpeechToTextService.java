/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.speechToTextService;

import java.net.URI;

import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.UriUtility;
import microsoft.hawaii.sdk.speechToTextService.ServiceAgents.SpeechGrammarsAgent;
import microsoft.hawaii.sdk.speechToTextService.ServiceAgents.SpeechRecognitionAgent;
import microsoft.hawaii.sdk.speechToTextService.ServiceContracts.RecognitionRequest;
import microsoft.hawaii.sdk.speechToTextService.ServiceContracts.SpeechServiceResult;

/**
 * Helper class that provides access to the SpeechToText service.
 */
public final class SpeechToTextService {

	/**
	 * Helper method to initiate the call that gets the grammars.
	 * 
	 * @param identity
	 *            the client identity
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public static void GetGrammars(ClientIdentity identity,
			OnCompleteListener<SpeechServiceResult> completedCallback,
			Object state) throws Exception {
		SpeechGrammarsAgent agent = new SpeechGrammarsAgent(
				HawaiiClient.getServiceBaseUri(), identity, state);

		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * Helper method to initiate the call to recognition speech.
	 * 
	 * @param speechBuffer
	 *            the speech buffer
	 * @param grammar
	 *            the required grammar
	 * @param identity
	 *            the client identity
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object void
	 * @throws Exception
	 */
	public static void Recognition(byte[] speechBuffer, String grammar,
			ClientIdentity identity,
			OnCompleteListener<SpeechServiceResult> completedCallback,
			Object state) throws Exception {
		RecognitionRequest request = new RecognitionRequest();
		request.setSpeechBuffer(speechBuffer);

		SpeechRecognitionAgent agent = new SpeechRecognitionAgent(request,
				new URI(String.format("%s/%s/%s",
						HawaiiClient.getServiceBaseUri(),
						SpeechToTextServiceConst.Signature,
						UriUtility.safeEncode(grammar))), identity, state);

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
