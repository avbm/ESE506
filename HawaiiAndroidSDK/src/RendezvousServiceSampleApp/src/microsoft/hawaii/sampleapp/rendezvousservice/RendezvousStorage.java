/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sampleapp.rendezvousservice;

import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 */
public final class RendezvousStorage {

	public final static String RENDEZVOUS_STORAGE_PREFS_NAME = "Relay_Android_Client_Data";

	public static String getSecretKey(Context context, String key) {
		return getValue(context, key);
	}

	public static void setSecretKey(Context context, String key,
			String secretKey) {
		setValue(context, key, secretKey);
	}

	private static String getValue(Context context, String key) {
		if (Utility.isStringNullOrEmpty(key)) {
			return null;
		}

		SharedPreferences settings = context.getSharedPreferences(
				RENDEZVOUS_STORAGE_PREFS_NAME, 0);
		return settings.getString(key, "");
	}

	private static void setValue(Context context, String key, String value) {
		Utility.assertStringNotNullOrEmpty("key", key);
		Utility.assertStringNotNullOrEmpty("value", value);

		SharedPreferences settings = context.getSharedPreferences(
				RENDEZVOUS_STORAGE_PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();

		editor.putString(key, value);
		editor.commit();
	}

}
