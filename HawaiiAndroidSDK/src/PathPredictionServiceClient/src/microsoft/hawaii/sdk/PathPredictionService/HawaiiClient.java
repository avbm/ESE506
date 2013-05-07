/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.PathPredictionService;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class to contain a set of variables related to specific Hawaii Service
 */
public class HawaiiClient {
	/**
	 * Base URL for PathPrediction service, get it from massage file
	 */
	public static final String HAWAII_PP_BASEURL = Messages
			.getString("HawaiiClient.PathPredictionURL"); //$NON-NLS-1$

	/**
	 * Base URI for PathPrediction service
	 */
	private static URI s_baseUri;

	/**
	 * static code snippet to initialize the base URI field
	 */
	static {
		try {
			s_baseUri = new URI(HawaiiClient.HAWAII_PP_BASEURL);
		} catch (URISyntaxException ex) {
			throw new RuntimeException(
					"Failed to initialize base URI field for KeyValue service",
					ex);
		}
	}

	/**
	 * Gets the base URI for Hawaii service
	 * 
	 * @return URI
	 */
	public static URI getServiceBaseUri() {
		return s_baseUri;
	}

}
