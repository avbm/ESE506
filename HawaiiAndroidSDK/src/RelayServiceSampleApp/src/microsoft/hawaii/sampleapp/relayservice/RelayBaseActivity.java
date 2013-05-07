/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sampleapp.relayservice;

import java.util.List;

import microsoft.hawaii.sampleappbase.HawaiiBaseAuthActivity;
import microsoft.hawaii.sdk.relayService.ServiceContracts.EndpointResult;
import microsoft.hawaii.sdk.relayService.ServiceContracts.Groups;
import microsoft.hawaii.sdk.relayService.ServiceContracts.Message;

/**
 * Base activity for Relay Client
 */
public class RelayBaseActivity extends HawaiiBaseAuthActivity {

	// @Override
	// public void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	//
	// this.getBaseApplication().readStorageInfo();
	// }

	@Override
	public void onStop() {
		super.onStop();

		this.getBaseApplication().saveToStorage();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		this.getBaseApplication().saveToStorage();
	}

	/**
	 * gets current {@link RelayApplication} object
	 * 
	 * @return RelayApplication
	 */
	protected RelayApplication getBaseApplication() {
		return (RelayApplication) this.getApplication();
	}

	protected EndpointResult getEndpoint() {
		return this.getBaseApplication().getEndpoint();
	}

	protected Groups getGroups() {
		return this.getBaseApplication().getGroups();
	}

	protected void setEndpoint(EndpointResult endpoint) {
		this.getBaseApplication().setEndpoint(endpoint);
	}

	protected void setGroups(Groups groups) {
		this.getBaseApplication().setGroups(groups);
	}

	protected List<Message> getMessages() {
		return this.getBaseApplication().getMessages();
	}

}
