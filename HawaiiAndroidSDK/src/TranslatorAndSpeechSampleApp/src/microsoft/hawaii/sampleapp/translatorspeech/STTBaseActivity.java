/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sampleapp.translatorspeech;

import microsoft.hawaii.sampleappbase.HawaiiBaseApplication.AuthenticationType;

/**
 *
 */
public class STTBaseActivity extends BaseActivity {
	/**
	 * 
	 * getAuthenticationTypeFromApp Returns AuthenticationType.GUID
	 * 
	 * @return AuthenticationType
	 */
	@Override
	protected AuthenticationType getAuthenticationTypeFromApp() {
		return AuthenticationType.GUID;
	}

}
