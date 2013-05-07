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
import microsoft.hawaii.sdk.translatorService.ServiceContracts.GetLanguagesForSpeakResponse;

/**
 * Class agent to get supported languages for Speak method.
 */
public class GetLanguagesForSpeakAgent extends
		ServiceAgent<GetLanguagesForSpeakResponse> {

	/**
	 * Initializes an instance of the {@link GetLanguagesForSpeakAgent} class
	 * 
	 * @param baseUri
	 *            base URI object
	 * @param identity
	 *            the client identity
	 * @param locale
	 *            the system locale
	 * @param state
	 *            user defined state
	 * @throws Exception
	 */
	public GetLanguagesForSpeakAgent(URI baseUri, ClientIdentity identity,
			String locale, Object state) throws Exception {
		super(GetLanguagesForSpeakResponse.class, HttpMethod.GET, state);

		Utility.assertNotNull("baseUri", baseUri);
		Utility.assertNotNull("identity", identity);
		Utility.assertStringNotNullOrEmpty("locale", locale);

		this.clientIdentity = identity;

		UriQueryBuilder builder = new UriQueryBuilder();
		builder.add(TranslatorServiceConst.LocaleKey, locale);

		this.serviceUri = builder.addToURI(new URI(String.format("%s/%s/%s",
				baseUri.toString(), TranslatorServiceConst.TranslatorSignature,
				TranslatorServiceConst.GetLanguagesForSpeakKey)));
	}
}
