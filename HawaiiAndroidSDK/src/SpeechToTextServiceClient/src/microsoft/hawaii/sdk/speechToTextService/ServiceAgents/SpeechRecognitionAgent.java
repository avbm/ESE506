/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.speechToTextService.ServiceAgents;

import java.net.URI;

import microsoft.hawaii.hawaiiClientLibraryBase.HttpMethod;
import microsoft.hawaii.hawaiiClientLibraryBase.PostableServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.Exceptions.HawaiiException;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.sdk.speechToTextService.ServiceContracts.RecognitionRequest;
import microsoft.hawaii.sdk.speechToTextService.ServiceContracts.SpeechServiceResult;

/**
 * This class provides helper methods used to communicate with the Hawaii
 * Speech-to-Text service. It has helper methods for contacting Hawaii
 * Speech-to-Text Service to execute the speech-to-text translation. It accepts
 * an audio stream as input, sends it to the speech service and receives a list
 * of potential texts corresponding to the audio input.
 */
public class SpeechRecognitionAgent extends
		PostableServiceAgent<RecognitionRequest, SpeechServiceResult> {

	/**
	 * Initializes an instance of the {@link SpeechRecognitionAgent} class
	 * 
	 * @param request
	 *            {@link RecognitionRequest} object
	 * @param baseUri
	 *            base URI object
	 * @param identity
	 *            client identity
	 * @param state
	 *            user defined state
	 * @throws Exception
	 */
	public SpeechRecognitionAgent(RecognitionRequest request, URI baseUri,
			ClientIdentity identity, Object state) throws Exception {
		super(request, SpeechServiceResult.class, baseUri, identity,
				HttpMethod.POST, state);
	}

	@Override
	protected byte[] getRequestBodyData() throws HawaiiException {
		return this.request.getSpeechBuffer();
	}
}
