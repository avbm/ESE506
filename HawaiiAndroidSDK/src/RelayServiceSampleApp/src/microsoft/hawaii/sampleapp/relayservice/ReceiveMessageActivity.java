package microsoft.hawaii.sampleapp.relayservice;

import java.util.ArrayList;
import java.util.List;

import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.sdk.relayService.RelayService;
import microsoft.hawaii.sdk.relayService.ServiceContracts.EndpointResult;
import microsoft.hawaii.sdk.relayService.ServiceContracts.Message;
import microsoft.hawaii.sdk.relayService.ServiceContracts.MessageResult;
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

public class ReceiveMessageActivity extends RelayBaseActivity {

	private TextView messageCountTextView;
	private ListView messageListView;
	private Button receiveMessageButton;

	private ProgressBar progressBar;

	private AsyncTask<Void, Integer, AlertDialog.Builder> receiveMessageTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receive_message);

		this.progressBar = (ProgressBar) this
				.findViewById(R.id.message_receive_progressbar);
		this.messageCountTextView = (TextView) this
				.findViewById(R.id.message_receive_textview_messageCount);
		this.messageListView = (ListView) this
				.findViewById(R.id.message_receive_listview_messageList);

		this.receiveMessageButton = (Button) this
				.findViewById(R.id.message_receive_button_receiveMessages);
		this.receiveMessageButton
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						receiveMessageButtonClick(v);
					}
				});

		Button backButton = (Button) this
				.findViewById(R.id.message_receive_button_back);
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ReceiveMessageActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_receive_message, menu);
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

		this.refreshMessageListView();
	}

	private void receiveMessageButtonClick(View v) {
		this.receiveMessageButton.setEnabled(false);
		this.refreshMessageListView();
	}

	private void refreshMessageListView() {
		EndpointResult endpoint = this.getEndpoint();
		if (endpoint == null) {
			this.showErrorMessage("Your endpoint is invalid", null);
			this.finish();
			return;
		}

		receiveMessage(endpoint);
	}

	private void receiveMessage(final EndpointResult endpoint) {
		final ReceiveMessageActivity thisActivity = this;
		class ReceiveMessageTask extends
				AsyncTask<Void, Integer, AlertDialog.Builder> {

			private RelayApplication application = thisActivity
					.getBaseApplication();
			private MessageResult serviceResult;

			protected AlertDialog.Builder doInBackground(Void... listTypes) {
				try {
					RelayService.receiveMessage(
							application.getClientIdentity(),
							endpoint.getRegistrationId(),
							endpoint.getSecretKey(), "",
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
					return dialogToShow("Error receiving messages",
							serviceResult.getException());
				}

				return null;
			}

			protected void onPreExecute() {
				progressBar.setVisibility(View.VISIBLE);
				receiveMessageButton.setEnabled(false);
			}

			protected void onPostExecute(AlertDialog.Builder dialogBuilder) {
				if (dialogBuilder != null) {
					showErrorMessage(dialogBuilder);
				} else {
					Message[] messages = serviceResult.getMessages();
					if (messages == null || messages.length <= 0) {
						thisActivity.showErrorMessage(
								"No new messages available", null);
					} else {
						List<Message> messageList = thisActivity.getMessages();
						for (Message message : messages) {
							messageList.add(message);
						}

						displayMessages(messageList);
					}
				}

				progressBar.setVisibility(View.GONE);
				receiveMessageButton.setEnabled(true);
				receiveMessageTask = null;
			}
		}

		receiveMessageTask = new ReceiveMessageTask();
		receiveMessageTask.execute();
	}

	private void displayMessages(List<Message> messages) {
		ArrayList<String> items = new ArrayList<String>();
		this.messageCountTextView.setText(Integer.toString(messages.size()));

		for (Message message : messages) {
			StringBuilder builder = new StringBuilder();
			builder.append("From: ");
			builder.append(message.getFrom() + "\n");
			builder.append("To: ");
			builder.append(message.getTo() + "\n");
			builder.append("Msg: " + new String(message.getBody()));
			builder.append("\n");
			items.add(builder.toString());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, items);
		this.messageListView.setAdapter(adapter);
	}
}
