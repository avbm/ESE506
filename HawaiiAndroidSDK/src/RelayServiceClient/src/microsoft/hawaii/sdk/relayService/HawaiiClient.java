/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.relayService;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class to contain a set of variables related to specific Hawaii Service.
 */
public final class HawaiiClient {
	/**
	 * Base URI for hawaii service
	 */
	public static final String HAWAII_RELAY_BASEURL = Messages
			.getString("HawaiiClient.Relay_BaseUrl"); //$NON-NLS-1$

	/**
	 * Base URI for Hawaii service
	 */
	private static URI s_baseUri;

	/**
	 * Static code snippet to initialize the base URI field
	 */
	static {
		try {
			s_baseUri = new URI(HawaiiClient.HAWAII_RELAY_BASEURL);
		} catch (URISyntaxException ex) {
			throw new RuntimeException(
					"Failed to initialize base URI field for Relay service", ex);
		}
	}

	/**
	 * Gets the base URI for Hawaii service
	 * 
	 * @return URI Returns the URI.
	 */
	public static URI getServiceBaseUri() {
		return s_baseUri;
	}
}
