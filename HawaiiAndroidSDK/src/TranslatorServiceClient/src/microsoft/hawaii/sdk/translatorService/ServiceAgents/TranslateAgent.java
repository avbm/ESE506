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
import microsoft.hawaii.sdk.translatorService.ServiceContracts.TranslateResponse;

/**
 * Class agent to translate the text.
 */
public class TranslateAgent extends ServiceAgent<TranslateResponse> {

	/**
	 * Initializes an instance of the {@link TranslateAgent} class
	 * 
	 * @param baseUri
	 *            base URI object
	 * @param identity
	 *            the client identity
	 * @param text
	 *            the text to be translated
	 * @param to
	 *            the language translate to
	 * @param from
	 *            the language translate from
	 * @param state
	 *            user defined state
	 * @throws Exception
	 */
	public TranslateAgent(URI baseUri, ClientIdentity identity, String text,
			String to, String from, Object state) throws Exception {
		super(TranslateResponse.class, HttpMethod.GET, state);

		Utility.assertNotNull("baseUri", baseUri);
		Utility.assertNotNull("identity", identity);
		Utility.assertStringNotNullOrEmpty("text", text);
		Utility.assertStringNotNullOrEmpty("to", to);
		Utility.assertStringNotNullOrEmpty("from", from);

		this.clientIdentity = identity;

		UriQueryBuilder builder = new UriQueryBuilder();
		builder.add(TranslatorServiceConst.TextKey, text);
		builder.add(TranslatorServiceConst.FromKey, from);
		builder.add(TranslatorServiceConst.ToKey, to);

		this.serviceUri = builder.addToURI(new URI(String.format("%s/%s/%s",
				baseUri.toString(), TranslatorServiceConst.TranslatorSignature,
				TranslatorServiceConst.TranslateKey)));
	}
}
