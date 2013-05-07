package microsoft.hawaii.sampleapp.relayservice;

import java.util.ArrayList;

import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.StringUtil;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.relayService.RelayService;
import microsoft.hawaii.sdk.relayService.ServiceContracts.EndpointResult;
import microsoft.hawaii.sdk.relayService.ServiceContracts.GroupResult;
import microsoft.hawaii.sdk.relayService.ServiceContracts.Groups;
import microsoft.hawaii.sdk.relayService.ServiceContracts.MessageResult;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SendMessageActivity extends RelayBaseActivity {

	private TextView myendpointname;

	private EditText messageContentEditText;
	private EditText idEditText;
	private ListView joinedGroupListView;
	private Button sendToGroupButton;
	private Button sendToAllButton;
	private Button sendToIdButton;

	private ProgressBar progressBar;

	private AsyncTask<Void, Integer, AlertDialog.Builder> sendMessageTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_message);

		this.progressBar = (ProgressBar) this
				.findViewById(R.id.message_send_progressbar);
		this.myendpointname = (TextView) this
				.findViewById(R.id.message_send_textview_myendpoint);

		this.idEditText = (EditText) this
				.findViewById(R.id.message_send_textedit_endpointID);
		this.messageContentEditText = (EditText) this
				.findViewById(R.id.message_send_textedit_message);

		this.joinedGroupListView = (ListView) this
				.findViewById(R.id.message_send_listview_joinedGroupList);

		this.sendToGroupButton = (Button) this
				.findViewById(R.id.message_send_button_sendToGroup);
		this.sendToAllButton = (Button) this
				.findViewById(R.id.message_send_button_sendToAll);
		this.sendToIdButton = (Button) this
				.findViewById(R.id.message_send_button_sendToEndpointID);

		this.sendToGroupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				sendToGroupButtonClick(v);
			}
		});

		this.sendToAllButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				sendToAllButtonClick(v);
			}
		});

		this.sendToIdButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				sendToIdButtonClick(v);
			}
		});

		Button backButton = (Button) this
				.findViewById(R.id.message_send_button_back);
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SendMessageActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_send_message, menu);
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

		this.displayMyEndpointArea();
		this.displayJoinedGroupListView();
	}

	private void sendToGroupButtonClick(View v) {
		// get group id from listview
		int pos = this.joinedGroupListView.getCheckedItemPosition();
		String groupId = this.joinedGroupListView.getItemAtPosition(pos)
				.toString();

		Groups groups = this.getEndpoint().getGroups();
		GroupResult group = groups.Find(groupId);
		if (group == null) {
			this.showErrorMessage("Selected group is not joined by endpoint",
					null);
		}

		sendMessage(v, groupId);
	}

	private void sendToAllButtonClick(View v) {
		// get all groups
		ArrayList<String> groupIds = new ArrayList<String>();
		Groups groups = this.getEndpoint().getGroups();
		if (groups == null || groups.getItemCount() < 1) {
			this.showErrorMessage("no group exists!", null);
		}

		for (GroupResult group : groups) {
			groupIds.add(group.getRegistrationId());
		}

		String recipientIdList = StringUtil.join(groupIds, ",");
		sendMessage(v, recipientIdList);
	}

	private void sendToIdButtonClick(View v) {
		// get group id from edittext
		String id = this.idEditText.getText().toString();
		if (Utility.isStringNullOrEmpty(id)) {
			this.showErrorMessage("Endpoint or Group id is empty", null);
		}

		sendMessage(v, id);
	}

	private void sendMessage(final View v, final String recipientIdList) {
		v.setEnabled(false);

		final SendMessageActivity thisActivity = this;
		class SendMessageTask extends
				AsyncTask<Void, Integer, AlertDialog.Builder> {

			private RelayApplication application = thisActivity
					.getBaseApplication();
			private MessageResult serviceResult;

			protected AlertDialog.Builder doInBackground(Void... listTypes) {
				try {
					String message = thisActivity.messageContentEditText
							.getText().toString();
					if (Utility.isStringNullOrEmpty(message)) {
						return dialogToShow("Message content is empty", null);
					}

					EndpointResult endpoint = application.getEndpoint();
					RelayService.sendMessage(application.getClientIdentity(),
							message.getBytes(), endpoint.getRegistrationId(),
							recipientIdList, endpoint.getSecretKey(),
							new OnCompleteListener<MessageResult>() {
								public void done(MessageResult result) {
									serviceResult = result;
								}
							}, null);
				} catch (Exception exception) {
					return dialogToShow("Couldn't execute the Relay operation",
							exception);
				}

				if (serviceResult.getStatus() != microsoft.hawaii.hawaiiClientLibraryBase.Status.Success) {
					return dialogToShow("Error sending message",
							serviceResult.getException());
				}

				return null;
			}

			protected void onPreExecute() {
				progressBar.setVisibility(View.VISIBLE);
			}

			protected void onPostExecute(AlertDialog.Builder dialogBuilder) {
				if (dialogBuilder != null) {
					thisActivity.showErrorMessage(dialogBuilder);
				} else {
					thisActivity.showMessage("send successfully");
				}

				progressBar.setVisibility(View.GONE);
				v.setEnabled(true);
				sendMessageTask = null;
			}
		}

		sendMessageTask = new SendMessageTask();
		sendMessageTask.execute();
	}

	private void displayMyEndpointArea() {
		EndpointResult endpoint = this.getEndpoint();
		if (endpoint != null) {
			myendpointname.setText(endpoint.getRegistrationId());
		}
	}

	private void displayJoinedGroupListView() {
		Groups groups = this.getEndpoint().getGroups();
		if (groups == null || groups.getItemCount() <= 0) {
			this.joinedGroupListView.setAdapter(null);
			this.sendToGroupButton.setEnabled(false);
			this.sendToAllButton.setEnabled(false);
			return;
		}

		this.sendToGroupButton.setEnabled(true);
		this.sendToAllButton.setEnabled(true);

		ArrayList<String> items = new ArrayList<String>();
		for (GroupResult group : groups) {
			items.add(group.getRegistrationId());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, items);
		this.joinedGroupListView.setAdapter(adapter);

		// highlight the specified group item
		int selectedPos = 0;
		this.joinedGroupListView.setItemChecked(selectedPos, true);
		this.sendToGroupButton.setEnabled(true);
		this.sendToAllButton.setEnabled(true);
	}
}
