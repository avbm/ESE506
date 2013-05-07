/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sdk.relayService.ServiceContracts;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A container class for Group objects.
 */
public class Groups implements Iterable<GroupResult> {
	/**
	 * List field to store the Group items.
	 */
	private ArrayList<GroupResult> items;

	public Iterator<GroupResult> iterator() {
		return items.iterator();
	}

	/**
	 * Initializes an instance of the {@link Groups} class
	 */
	public Groups() {
		this.items = new ArrayList<GroupResult>();
	}

	/**
	 * Gets the number of items.
	 * 
	 * @return int Returns the items count
	 */
	public int getItemCount() {
		return this.items.size();
	}

	/**
	 * Method top add a Group object into the container.
	 * 
	 * @param group
	 *            the GroupResult object.
	 */
	public void Add(GroupResult group) {
		this.items.add(group);
	}

	/**
	 * Method to check whether a group exists in the container.
	 * 
	 * @param GroupId
	 *            the group Id.
	 * @return Boolean Returns Boolean value indicating whether this group is in
	 *         the container.
	 */
	public Boolean Exists(String groupId) {
		return this.Find(groupId) != null;
	}

	/**
	 * Method to retrieve a Group item from the container.
	 * 
	 * @param groupId
	 *            the group Id.
	 * @return Group Returns the group if it exists.
	 */
	public GroupResult Find(String groupId) {
		for (GroupResult group : this.items) {
			if (group.getRegistrationId() == groupId) {
				return group;
			}
		}

		return null;
	}

	/**
	 * Method to remove a group from the container based on its id.
	 * 
	 * @param groupId
	 *            the group Id
	 */
	public void Remove(String groupId) {
		GroupResult group = this.Find(groupId);
		if (group != null) {
			this.items.remove(group);
		}
	}

	/**
	 * Clears all item from the container.
	 */
	public void Clear() {
		this.items.clear();
	}
}
