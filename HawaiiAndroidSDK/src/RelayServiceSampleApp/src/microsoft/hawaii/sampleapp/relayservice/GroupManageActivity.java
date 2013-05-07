package microsoft.hawaii.sampleapp.relayservice;

import java.util.ArrayList;

import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.relayService.RelayService;
import microsoft.hawaii.sdk.relayService.ServiceContracts.EndpointResult;
import microsoft.hawaii.sdk.relayService.ServiceContracts.GroupResult;
import microsoft.hawaii.sdk.relayService.ServiceContracts.Groups;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GroupManageActivity extends RelayBaseActivity {

	private final static int Group_Expiration_Time_In_Hour = 24;

	private TextView myendpointname;
	private Button createGroupButton;
	private Button deleteGroupButton;
	private Button joinGroupButton;
	private Button leaveGroupButton;

	private ListView availableGroupListView;
	private ListView joinedGroupListView;
	private ProgressBar progressBar;

	private AsyncTask<Void, Integer, AlertDialog.Builder> manageGroupTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_manage);

		this.progressBar = (ProgressBar) this
				.findViewById(R.id.group_progressbar);
		this.myendpointname = (TextView) this
				.findViewById(R.id.group_textview_myendpoint);

		this.createGroupButton = (Button) this
				.findViewById(R.id.group_button_createGroup);
		this.deleteGroupButton = (Button) this
				.findViewById(R.id.group_button_deleteGroup);
		this.joinGroupButton = (Button) this
				.findViewById(R.id.group_button_joinGroup);
		this.leaveGroupButton = (Button) this
				.findViewById(R.id.group_button_leaveGroup);

		this.availableGroupListView = (ListView) this
				.findViewById(R.id.group_listview_availableGroupList);
		this.joinedGroupListView = (ListView) this
				.findViewById(R.id.group_listview_joinedGroupList);

		this.createGroupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				createGroupButtonClick(v);
			}
		});

		this.deleteGroupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				deleteGroupButtonClick(v);
			}
		});

		this.joinGroupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				joinGroupButtonClick(v);
			}
		});

		this.leaveGroupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				leaveGroupButtonClick(v);
			}
		});

		Button backButton = (Button) this.findViewById(R.id.group_button_back);
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				GroupManageActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_group_manage, menu);
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();

		if (this.getEndpoint() == null) {
			Toast.makeText(this, "endpoint info is missing", Toast.LENGTH_LONG)
					.show();
			this.finish();
		}

		refreshMyEndpointArea();
		refreshAvailableGroupListView();
		refreshJoinedGroupListView();
	}

	enum GroupOperationType {
		Create, Delete, Join, Leave, Unspecified
	}

	/**
	 * get group from listview
	 * 
	 * @param operationType
	 * @return GroupResult
	 */
	private GroupResult getCurrentGroup(GroupOperationType operationType) {
		ListView listview = null;
		if (operationType != GroupOperationType.Leave) {
			listview = this.availableGroupListView;
		} else {
			listview = this.joinedGroupListView;
		}

		int pos = listview.getCheckedItemPosition();
		String groupId = listview.getItemAtPosition(pos).toString();

		Groups groups = null;
		if (operationType == GroupOperationType.Leave) {
			groups = this.getEndpoint().getGroups();
		} else {
			groups = this.getGroups();
		}

		if (groups == null) {
			return null;
		}

		return groups.Find(groupId);
	}

	private void updateGroupInfo(GroupResult group,
			GroupOperationType operationType) {
		Groups mygroups = this.getGroups();
		Groups joinedGroups = this.getEndpoint().getGroups();
		String groupId = group.getRegistrationId();

		switch (operationType) {
		case Create:
			// create group
			mygroups.Add(group);
			refreshAvailableGroupListView(groupId);
			break;
		case Delete:
			// delete group
			mygroups.Remove(groupId);
			// remove joined group
			if (joinedGroups != null && joinedGroups.Exists(groupId)) {
				joinedGroups.Remove(groupId);
			}

			refreshAvailableGroupListView();
			refreshJoinedGroupListView();
			break;
		case Join:
			if (joinedGroups != null) {
				joinedGroups.Add(group);
			}

			refreshJoinedGroupListView();
			break;
		case Leave:
			// remove joined group
			if (joinedGroups != null && joinedGroups.Exists(groupId)) {
				joinedGroups.Remove(groupId);
			}

			refreshJoinedGroupListView();
			break;
		default:
			this.showErrorMessage("Invalid operation", null);
		}
	}

	private void manageGroupButtonClick(final View v,
			final GroupOperationType operationType) {
		v.setEnabled(false);

		final GroupManageActivity thisActivity = this;
		class ManageGroupTask extends
				AsyncTask<Void, Integer, AlertDialog.Builder> {

			private RelayApplication application = thisActivity
					.getBaseApplication();
			private GroupResult serviceResult;

			protected AlertDialog.Builder doInBackground(Void... listTypes) {
				GroupResult group = null;
				if (operationType != GroupOperationType.Create
						&& operationType != GroupOperationType.Unspecified) {
					// get group from listview
					group = getCurrentGroup(operationType);
					if (group == null) {
						return dialogToShow(
								"Invalid operation: no group has been selected",
								null);
					}
				}

				OnCompleteListener<GroupResult> listener = new OnCompleteListener<GroupResult>() {
					public void done(GroupResult result) {
						serviceResult = result;
					}
				};

				try {
					EndpointResult endpoint = application.getEndpoint();
					ClientIdentity identity = application.getClientIdentity();
					switch (operationType) {
					case Create:
						RelayService.createGroup(identity,
								Group_Expiration_Time_In_Hour * 60 * 60,
								listener, null);
						break;
					case Delete:
						RelayService.deleteGroup(identity,
								group.getRegistrationId(),
								group.getSecretKey(), listener, null);
						break;
					case Join:
						RelayService.joinGroup(identity,
								group.getRegistrationId(),
								endpoint.getRegistrationId(),
								endpoint.getSecretKey(), listener, null);
						break;
					case Leave:
						RelayService.leaveGroup(identity,
								group.getRegistrationId(),
								endpoint.getRegistrationId(),
								endpoint.getSecretKey(), listener, null);
						break;
					default:
						return dialogToShow(
								"Invalid operation type to manage groups", null);
					}
				} catch (Exception exception) {
					return dialogToShow("Couldn't execute the Relay operation",
							exception);
				}

				if (serviceResult.getStatus() != microsoft.hawaii.hawaiiClientLibraryBase.Status.Success) {
					return dialogToShow("Error occurred when managing group",
							serviceResult.getException());
				} else {
					if (operationType != GroupOperationType.Create
							&& operationType != GroupOperationType.Unspecified) {
						serviceResult = group;
					}
				}

				return null;
			}

			protected void onPreExecute() {
				progressBar.setVisibility(View.VISIBLE);
			}

			protected void onPostExecute(AlertDialog.Builder dialogBuilder) {
				v.setEnabled(true);
				if (dialogBuilder != null) {
					showErrorMessage(dialogBuilder);
				} else {
					updateGroupInfo(serviceResult, operationType);
				}

				progressBar.setVisibility(View.GONE);
				manageGroupTask = null;
			}
		}

		manageGroupTask = new ManageGroupTask();
		manageGroupTask.execute();
	}

	private void createGroupButtonClick(View v) {
		manageGroupButtonClick(v, GroupOperationType.Create);
	}

	private void deleteGroupButtonClick(View v) {
		manageGroupButtonClick(v, GroupOperationType.Delete);
	}

	private void joinGroupButtonClick(View v) {
		// get group from listview
		GroupResult group = getCurrentGroup(GroupOperationType.Join);
		EndpointResult endpoint = this.getEndpoint();
		if (endpoint.getGroups().Exists((group.getRegistrationId()))) {
			this.showErrorMessage("cannot join a group twice", null);
			return;
		}

		manageGroupButtonClick(v, GroupOperationType.Join);
	}

	private void leaveGroupButtonClick(View v) {
		manageGroupButtonClick(v, GroupOperationType.Leave);
	}

	private void refreshMyEndpointArea() {
		EndpointResult endpoint = this.getEndpoint();
		if (endpoint != null) {
			myendpointname.setText(endpoint.getRegistrationId());
		}
	}

	private void refreshAvailableGroupListView() {
		refreshAvailableGroupListView(null);
	}

	private void refreshAvailableGroupListView(String groupToSelect) {
		Groups groups = this.getGroups();
		if (groups == null || groups.getItemCount() <= 0) {
			this.deleteGroupButton.setEnabled(false);
			this.joinGroupButton.setEnabled(false);
			this.availableGroupListView.setAdapter(null);
			return;
		}

		ArrayList<String> items = new ArrayList<String>();
		for (GroupResult group : groups) {
			items.add(group.getRegistrationId());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, items);
		this.availableGroupListView.setAdapter(adapter);

		// highlight the specified group item
		int selectedPos = -1;
		if (!Utility.isStringNullOrEmpty(groupToSelect)) {
			selectedPos = adapter.getPosition(groupToSelect);
		} else {
			selectedPos = adapter.getCount() - 1;
		}

		if (selectedPos >= 0) {
			this.availableGroupListView.setItemChecked(selectedPos, true);
			this.availableGroupListView.setSelection(selectedPos);
			this.deleteGroupButton.setEnabled(true);
			this.joinGroupButton.setEnabled(true);
		}
	}

	private void refreshJoinedGroupListView() {
		refreshJoinedGroupListView(null);
	}

	private void refreshJoinedGroupListView(String groupToSelect) {
		Groups groups = this.getEndpoint().getGroups();
		if (groups == null || groups.getItemCount() <= 0) {
			this.joinedGroupListView.setAdapter(null);
			this.leaveGroupButton.setEnabled(false);
			return;
		}

		ArrayList<String> items = new ArrayList<String>();
		for (GroupResult group : groups) {
			items.add(group.getRegistrationId());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, items);
		this.joinedGroupListView.setAdapter(adapter);

		// highlight the specified group item
		int selectedPos = -1;
		if (!Utility.isStringNullOrEmpty(groupToSelect)) {
			selectedPos = adapter.getPosition(groupToSelect);
		} else {
			selectedPos = adapter.getCount() - 1;
		}

		if (selectedPos >= 0) {
			this.joinedGroupListView.setItemChecked(selectedPos, true);
			this.joinedGroupListView.setSelection(selectedPos);
			this.leaveGroupButton.setEnabled(true);
		}
	}
}
