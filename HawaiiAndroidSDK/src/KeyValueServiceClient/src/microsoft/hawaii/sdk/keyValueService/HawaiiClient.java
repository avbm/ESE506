/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.keyValueService;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Class to contain a set of variables related to specific Hawaii Service
 * 
 */
public final class HawaiiClient {

	/**
	 * Base url for Hawaii keyvalue service
	 */
	public static final String HAWAII_KVS_BASEURL = Messages
			.getString("HawaiiClient.KVS_BaseUrl"); //$NON-NLS-1$

	/**
	 * Base URI for Hawaii service
	 */
	private static URI s_baseUri;

	/**
	 * static code snippet to initialize the base URI field
	 */
	static {
		try {
			s_baseUri = new URI(HawaiiClient.HAWAII_KVS_BASEURL);
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
