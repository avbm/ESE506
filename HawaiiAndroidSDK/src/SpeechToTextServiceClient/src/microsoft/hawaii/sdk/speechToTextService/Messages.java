/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.speechToTextService;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * This is the Messages Class to help get final string from message properties
 */
public class Messages {

	/**
	 * A final static string represents the bundle name
	 */
	private static final String BUNDLE_NAME = "microsoft.hawaii.sdk.speechToTextService.messages"; //$NON-NLS-1$

	/**
	 * A final static resource bundle object
	 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	/**
	 * Initializes an instance of the {@link Messages} class
	 */
	private Messages() {
	}

	/**
	 * Gets the the value by key in the bundle
	 * 
	 * @param key
	 *            the key name
	 * @return String Returns the value in the bundle.
	 */
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
