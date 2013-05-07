/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.translatorService.ServiceAgents;

import java.net.URI;

import microsoft.hawaii.hawaiiClientLibraryBase.HttpMethod;
import microsoft.hawaii.hawaiiClientLibraryBase.ServiceAgent;
import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.UriQueryBuilder;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.translatorService.TranslatorServiceConst;
import microsoft.hawaii.sdk.translatorService.ServiceContracts.SpeakResponse;

/**
 * Class agent to Speak the text.
 */
public class SpeakAgent extends ServiceAgent<SpeakResponse> {

	/**
	 * Initializes an instance of the {@link SpeakAgent} class
	 * 
	 * @param baseUri
	 *            base URI object
	 * @param identity
	 *            the client identity
	 * @param text
	 *            the text
	 * @param language
	 *            the language to speak in
	 * @param format
	 *            the stream format of the content type. Currently "audio/wav"
	 *            and "audio/mp3" are available.
	 * @param options
	 *            Specifies the quality of the audio signals. Currently
	 *            "MaxQuality" and "MinSize" are available.
	 * @param state
	 *            user defined state
	 * @throws Exception
	 */
	public SpeakAgent(URI baseUri, ClientIdentity identity, String text,
			String language, String format, String options, Object state)
			throws Exception {
		super(SpeakResponse.class, HttpMethod.GET, state);

		Utility.assertNotNull("baseUri", baseUri);
		Utility.assertNotNull("identity", identity);
		Utility.assertStringNotNullOrEmpty("text", text);
		Utility.assertStringNotNullOrEmpty("language", language);
		Utility.assertStringNotNullOrEmpty("format", format);
		Utility.assertStringNotNullOrEmpty("options", options);

		this.clientIdentity = identity;

		UriQueryBuilder builder = new UriQueryBuilder();
		builder.add(TranslatorServiceConst.TextKey, text);
		builder.add(TranslatorServiceConst.SpeakLanguageKey, language);
		builder.add(TranslatorServiceConst.SpeakFormatKey, format);
		builder.add(TranslatorServiceConst.SpeakOptionsKey, options);

		this.serviceUri = builder.addToURI(new URI(String.format("%s/%s/%s",
				baseUri.toString(), TranslatorServiceConst.TranslatorSignature,
				TranslatorServiceConst.SpeakKey)));
	}
}
