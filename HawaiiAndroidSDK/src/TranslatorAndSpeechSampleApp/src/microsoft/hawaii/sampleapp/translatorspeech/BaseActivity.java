/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sampleapp.translatorspeech;

import microsoft.hawaii.sampleappbase.HawaiiBaseAuthActivity;

/**
 *
 */
public class BaseActivity extends HawaiiBaseAuthActivity {

	/**
	 * gets current {@link TranslatorApplication} object
	 * 
	 * @return RelayApplication
	 */
	protected TranslatorApplication getBaseApplication() {
		return (TranslatorApplication) this.getApplication();
	}

}
