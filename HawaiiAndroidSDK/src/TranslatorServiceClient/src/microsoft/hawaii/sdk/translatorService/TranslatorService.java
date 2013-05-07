/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.translatorService;

import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.DataContract.ServiceResult;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.sdk.translatorService.ServiceAgents.GetLanguagesForSpeakAgent;
import microsoft.hawaii.sdk.translatorService.ServiceAgents.GetLanguagesForTranslateAgent;
import microsoft.hawaii.sdk.translatorService.ServiceAgents.SpeakAgent;
import microsoft.hawaii.sdk.translatorService.ServiceAgents.TranslateAgent;
import microsoft.hawaii.sdk.translatorService.ServiceContracts.GetLanguagesForSpeakResponse;
import microsoft.hawaii.sdk.translatorService.ServiceContracts.GetLanguagesForTranslateResponse;
import microsoft.hawaii.sdk.translatorService.ServiceContracts.SpeakResponse;
import microsoft.hawaii.sdk.translatorService.ServiceContracts.TranslateResponse;

/**
 * Helper class that provides access to the Translator service.
 */
public class TranslatorService {

	/**
	 * Helper method to initiate the call that gets the supported languages for
	 * speak method.
	 * 
	 * @param identity
	 *            the client identity
	 * @param locale
	 *            the system locale
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public static void getLanguagesForSpeak(ClientIdentity identity,
			String locale,
			OnCompleteListener<GetLanguagesForSpeakResponse> completedCallback,
			Object state) throws Exception {
		GetLanguagesForSpeakAgent agent = new GetLanguagesForSpeakAgent(
				HawaiiClient.getServiceBaseUri(), identity, locale, state);
		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * Helper method to initiate the call that gets the supported languages for
	 * translate method.
	 * 
	 * @param identity
	 *            the client identity
	 * @param locale
	 *            the system locale
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public static void getLanguagesForTranslate(
			ClientIdentity identity,
			String locale,
			OnCompleteListener<GetLanguagesForTranslateResponse> completedCallback,
			Object state) throws Exception {
		GetLanguagesForTranslateAgent agent = new GetLanguagesForTranslateAgent(
				HawaiiClient.getServiceBaseUri(), identity, locale, state);
		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * Helper method to initiate the call that speak method.
	 * 
	 * @param identity
	 *            the client identity
	 * @param text
	 *            the content to speak
	 * @param language
	 *            the language of the speech
	 * @param format
	 *            the stream format of the content type. Currently "audio/wav"
	 *            and "audio/mp3" are available.
	 * @param options
	 *            Specifies the quality of the audio signals. Currently
	 *            "MaxQuality" and "MinSize" are available.
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public static void speak(ClientIdentity identity, String text,
			String language, String format, String options,
			OnCompleteListener<SpeakResponse> completedCallback, Object state)
			throws Exception {
		SpeakAgent agent = new SpeakAgent(HawaiiClient.getServiceBaseUri(),
				identity, text, language, format, options, state);
		ExecuteAgent(agent, completedCallback);
	}

	/**
	 * Helper method to initiate the call that translate method.
	 * 
	 * @param identity
	 *            the client identity
	 * @param text
	 *            the text to be translated
	 * @param to
	 *            the language translate to
	 * @param from
	 *            the language translate from
	 * @param completedCallback
	 *            completion callback
	 * @param state
	 *            user defined state object
	 * @throws Exception
	 */
	public static void translate(ClientIdentity identity, String text,
			String to, String from,
			OnCompleteListener<TranslateResponse> completedCallback,
			Object state) throws Exception {
		TranslateAgent agent = new TranslateAgent(
				HawaiiClient.getServiceBaseUri(), identity, text, from, to,
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
	 */
	private static <T extends ServiceResult> void ExecuteAgent(
			ServiceAgent<T> agent, OnCompleteListener<T> completedCallback)
			throws Exception {
		agent.processRequest(completedCallback);
	}
}
