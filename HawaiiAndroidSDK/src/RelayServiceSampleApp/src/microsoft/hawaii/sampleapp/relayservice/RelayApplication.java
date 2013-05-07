/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sampleapp.relayservice;

import java.util.List;

import microsoft.hawaii.sampleappbase.HawaiiBaseApplication;
import microsoft.hawaii.sdk.relayService.ServiceContracts.EndpointResult;
import microsoft.hawaii.sdk.relayService.ServiceContracts.Groups;
import microsoft.hawaii.sdk.relayService.ServiceContracts.Message;
import android.content.Context;

/**
 * Relay application
 */
public class RelayApplication extends HawaiiBaseApplication {

	private EndpointResult endpoint;
	private Groups groups;
	private List<Message> messages;

	@Override
	public void onCreate() {
		super.onCreate();

		this.readStorageInfo();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

		this.saveToStorage();
	}

	@Override
	public void onTrimMemory(int level) {
		super.onTrimMemory(level);

		this.saveToStorage();
	}

	public void readStorageInfo() {
		Context context = this.getApplicationContext();

		try {
			this.endpoint = RelayStorage.readEndpoint(context);
			this.groups = RelayStorage.readGroups(context);
			this.messages = RelayStorage.readMessages(context);
		} catch (Exception e) {

		}
	}

	public void saveToStorage() {
		Context context = this.getApplicationContext();

		try {
			RelayStorage.saveEndpoint(context, this.endpoint);
			RelayStorage.saveGroups(context, this.groups);
			RelayStorage.saveMessages(context, this.messages);
		} catch (Exception e) {

		}
	}

	/**
	 * @return the endpoint
	 */
	public EndpointResult getEndpoint() {
		return endpoint;
	}

	/**
	 * @param endpoint
	 *            the endpoint to set
	 */
	public void setEndpoint(EndpointResult endpoint) {
		this.endpoint = endpoint;
	}

	/**
	 * @return the groups
	 */
	public Groups getGroups() {
		return groups;
	}

	/**
	 * @param groups
	 *            the groups to set
	 */
	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	/**
	 * @return the messages
	 */
	public List<Message> getMessages() {
		return messages;
	}

	/**
	 * @param messages
	 *            the messages to set
	 */
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

}
