/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sampleapp.keyvalueservice;

import microsoft.hawaii.sampleappbase.HawaiiBaseAuthActivity;
import android.os.Bundle;

/**
 *
 */
public class HawaiiBaseActivity extends HawaiiBaseAuthActivity {

	protected static String s_CommonKeyPrefix;
	protected static int s_commonKeyPrefixLength;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (s_CommonKeyPrefix == null || s_CommonKeyPrefix.length() == 0) {
			s_CommonKeyPrefix = getString(R.string.createkvitem_prefix);
			s_commonKeyPrefixLength = s_CommonKeyPrefix.length();
		}
	}
}
