/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sampleapp.relayservice;

import java.util.ArrayList;
import java.util.List;

import microsoft.hawaii.hawaiiClientLibraryBase.Util.StringUtil;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.relayService.ServiceContracts.EndpointResult;
import microsoft.hawaii.sdk.relayService.ServiceContracts.GroupResult;
import microsoft.hawaii.sdk.relayService.ServiceContracts.Groups;
import microsoft.hawaii.sdk.relayService.ServiceContracts.Message;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Helper class to store Endpoint id and secret key into the mobile's isolated
 * storage for endpoint persistence purpose
 */
public final class RelayStorage {

	public final static String RELAY_STORAGE_PREFS_NAME = "Relay_Android_Client_Data";

	private final static String ValueSeperator = "||";
	private final static String ObjectSeperator = "|col|";
	private final static String EndpointKey = "Endpoint";
	private final static String CreatedGroupsKey = "CreatedGroups";
	private final static String JoinedGroupsKey = "JoinedGroups";
	private final static String MessagesKey = "Messages";

	public static void saveEndpoint(Context context, EndpointResult endpoint) {
		Utility.assertNotNull("context", context);

		if (endpoint == null) {
			removeKey(context, EndpointKey);
			removeKey(context, JoinedGroupsKey);
		} else {
			String endpointValue = String.format("%s%s%s",
					endpoint.getRegistrationId(), ValueSeperator,
					endpoint.getSecretKey());

			setValue(context, EndpointKey, endpointValue);
			saveGroups(context, JoinedGroupsKey, endpoint.getGroups());
		}
	}

	public static EndpointResult readEndpoint(Context context) {
		Utility.assertNotNull("context", context);

		String endpointValue = getValue(context, EndpointKey);
		if (Utility.isStringNullOrEmpty(endpointValue)) {
			return null;
		}

		EndpointResult endpoint = null;
		String[] values = endpointValue.split(ValueSeperator);
		if (values == null || values.length != 2) {
			return null;
		}

		endpoint = new EndpointResult();
		endpoint.setRegistrationId(values[0]);
		endpoint.setSecretKey(values[1]);
		endpoint.setGroups(readGroups(context, JoinedGroupsKey));
		return endpoint;
	}

	public static void saveGroups(Context context, Groups groups) {
		saveGroups(context, CreatedGroupsKey, groups);
	}

	public static Groups readGroups(Context context) {
		return readGroups(context, CreatedGroupsKey);
	}

	public static void saveMessages(Context context, List<Message> messages) {
		Utility.assertNotNull("context", context);
		saveMessages(context, MessagesKey, messages);
	}

	public static List<Message> readMessages(Context context) {
		Utility.assertNotNull("context", context);
		return readMessages(context, MessagesKey);
	}

	private static void saveMessages(Context context, String key,
			List<Message> messages) {
		if (messages == null || messages.size() < 1) {
			removeKey(context, key);
			return;
		}

		List<String> messageItems = new ArrayList<String>();
		for (Message message : messages) {
			messageItems.add(String.format("%s%s%s%s%s", message.getFrom(),
					ValueSeperator, message.getTo(), ValueSeperator,
					new String(message.getBody())));
		}

		String messagesValue = StringUtil.join(messageItems, ObjectSeperator);
		setValue(context, key, messagesValue);
	}

	private static List<Message> readMessages(Context context, String key) {
		List<Message> messages = new ArrayList<Message>();

		String value = getValue(context, key);
		if (Utility.isStringNullOrEmpty(value)) {
			return messages;
		}

		String[] messagesItems = value.split(ObjectSeperator);
		if (messagesItems == null || messagesItems.length == 0) {
			return messages;
		}

		for (String item : messagesItems) {
			String[] messageItems = item.split(ValueSeperator);
			if (messageItems == null || messageItems.length != 3) {
				continue;
			}

			Message message = new Message();
			message.setFrom(messageItems[0]);
			message.setTo(messageItems[1]);
			message.setValid(true);
			message.setBody(messageItems[2].getBytes());

			messages.add(message);
		}

		return messages;
	}

	private static void saveGroups(Context context, String key, Groups groups) {
		// If the groups is null, remove the entry.
		if (groups == null || groups.getItemCount() < 1) {
			removeKey(context, key);
			return;
		}

		List<String> groupItems = new ArrayList<String>();
		for (GroupResult group : groups) {
			groupItems.add(String.format("%s%s%s%s%s", group.getName(),
					ValueSeperator, group.getRegistrationId(), ValueSeperator,
					group.getSecretKey()));
		}

		String groupsValue = StringUtil.join(groupItems, ObjectSeperator);
		RelayStorage.setValue(context, key, groupsValue);
	}

	private static Groups readGroups(Context context, String key) {
		Groups groups = new Groups();

		String value = getValue(context, key);
		if (Utility.isStringNullOrEmpty(value)) {
			return groups;
		}

		String[] groupsItems = value.split(ObjectSeperator);
		if (groupsItems == null || groupsItems.length == 0) {
			return groups;
		}

		for (String item : groupsItems) {
			String[] groupItems = item.split(ValueSeperator);
			if (groupItems == null || groupItems.length != 3) {
				continue;
			}

			GroupResult group = new GroupResult();
			group.setName(groupItems[0]);
			group.setRegistrationId(groupItems[1]);
			group.setSecretKey(groupItems[2]);
			groups.Add(group);
		}

		return groups;
	}

	private static void removeKey(Context context, String key) {
		if (Utility.isStringNullOrEmpty(key)) {
			return;
		}

		SharedPreferences settings = context.getSharedPreferences(
				RELAY_STORAGE_PREFS_NAME, 0);
		String value = settings.getString(key, null);
		if (value != null) {
			SharedPreferences.Editor editor = settings.edit();
			editor.remove(key);
			editor.commit();
		}
	}

	private static String getValue(Context context, String key) {
		if (Utility.isStringNullOrEmpty(key)) {
			return null;
		}

		SharedPreferences settings = context.getSharedPreferences(
				RELAY_STORAGE_PREFS_NAME, 0);
		return settings.getString(key, "");
	}

	private static void setValue(Context context, String key, String value) {
		Utility.assertStringNotNullOrEmpty("key", key);
		Utility.assertStringNotNullOrEmpty("value", value);

		SharedPreferences settings = context.getSharedPreferences(
				RELAY_STORAGE_PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();

		editor.putString(key, value);
		editor.commit();
	}

}
