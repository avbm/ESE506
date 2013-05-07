/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.speechToTextService.ServiceAgents;

import java.net.URI;

import microsoft.hawaii.hawaiiClientLibraryBase.HttpMethod;
import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.speechToTextService.SpeechToTextServiceConst;
import microsoft.hawaii.sdk.speechToTextService.ServiceContracts.SpeechServiceResult;

/**
 * This class provides helper methods used to communicate with the Hawaii
 * Speech-to-Text service. It has helper methods for contacting Hawaii
 * Speech-to-Text Service to receive all available grammars in the server.
 * Currently, only the 'Dictation' grammar is available for a general purpose
 * speech-to-text translation.
 */
public class SpeechGrammarsAgent extends ServiceAgent<SpeechServiceResult> {

	/**
	 * Initializes an instance of the {@link SpeechGrammarsAgent} class
	 * 
	 * @param baseUri
	 *            the service base URI
	 * @param identity
	 *            the client identity
	 * @param state
	 *            user defined state
	 * @throws Exception
	 */
	public SpeechGrammarsAgent(URI baseUri, ClientIdentity identity,
			Object state) throws Exception {
		super(SpeechServiceResult.class, HttpMethod.GET, state);

		Utility.assertNotNull("baseUri", baseUri);
		Utility.assertNotNull("identity", identity);

		this.clientIdentity = identity;
		this.serviceUri = new URI(String.format("%s/%s", baseUri.toString(),
				SpeechToTextServiceConst.Signature));
	}
}
