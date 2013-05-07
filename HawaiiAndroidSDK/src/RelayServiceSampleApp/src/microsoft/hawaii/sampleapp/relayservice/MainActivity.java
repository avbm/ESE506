package microsoft.hawaii.sampleapp.relayservice;

import java.util.ArrayList;

import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.sdk.relayService.RelayService;
import microsoft.hawaii.sdk.relayService.ServiceContracts.EndpointResult;
import microsoft.hawaii.sdk.relayService.ServiceContracts.GroupResult;
import microsoft.hawaii.sdk.relayService.ServiceContracts.Groups;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends RelayBaseActivity {

	private final static String RelayApplicationName = "AndroidRelaySampleClient";

	private ListView mygrouplistview;
	private TextView myendpointname;
	private Button createEndpointButton;
	private ProgressBar progressBar;
	private Button manageGroupButton;
	private Button sendMessageButton;
	private Button receiveMessageButton;

	private AsyncTask<Void, Integer, AlertDialog.Builder> createEndpointTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.progressBar = (ProgressBar) this
				.findViewById(R.id.main_progressbar);
		this.mygrouplistview = (ListView) this
				.findViewById(R.id.main_listview_mygroups);
		this.myendpointname = (TextView) this
				.findViewById(R.id.main_textview_myendpoint);

		this.createEndpointButton = (Button) this
				.findViewById(R.id.main_button_createEndpoint);
		this.manageGroupButton = (Button) this
				.findViewById(R.id.main_button_manageGroup);
		this.sendMessageButton = (Button) this
				.findViewById(R.id.main_button_sendMessage);
		this.receiveMessageButton = (Button) this
				.findViewById(R.id.main_button_receiveMessage);

		this.createEndpointButton
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						onCreateEndpointButtonClick(v);
					}
				});

		// if (this.getEndpoint() != null) {
		// this.createEndpointButton.setEnabled(false);
		// }

		this.manageGroupButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						GroupManageActivity.class);
				startActivity(intent);
			}
		});

		this.sendMessageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						SendMessageActivity.class);
				startActivity(intent);
			}
		});

		this.receiveMessageButton
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								ReceiveMessageActivity.class);
						startActivity(intent);
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();

		refreshMyEndpointArea();
		refreshMyGroupListView();
	}

	private void refreshMyEndpointArea() {
		EndpointResult endpoint = this.getEndpoint();
		boolean endpointExist = (endpoint != null);
		this.manageGroupButton.setEnabled(endpointExist);
		this.sendMessageButton.setEnabled(endpointExist);
		this.receiveMessageButton.setEnabled(endpointExist);

		this.myendpointname.setText(endpointExist ? endpoint
				.getRegistrationId() : "");
	}

	private void refreshMyGroupListView() {
		EndpointResult endpoint = this.getEndpoint();
		if (endpoint == null) {
			return;
		}

		Groups groups = endpoint.getGroups();
		if (groups == null || groups.getItemCount() <= 0) {
			return;
		}

		ArrayList<String> items = new ArrayList<String>();
		for (GroupResult group : groups) {
			items.add(group.getRegistrationId());
		}

		this.mygrouplistview.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_single_choice, items));
		int selectedPos = 0;
		this.mygrouplistview.setItemChecked(selectedPos, true);
	}

	private void onCreateEndpointButtonClick(View v) {
		this.createEndpointButton.setEnabled(false);

		final MainActivity thisActivity = this;
		class CreateEndpointTask extends
				AsyncTask<Void, Integer, AlertDialog.Builder> {

			private RelayApplication application = thisActivity
					.getBaseApplication();

			private EndpointResult serviceResult;

			protected AlertDialog.Builder doInBackground(Void... listTypes) {
				try {
					// Check whether the active endpoint object is null. If
					// null, create a new end point. If not null, delete the
					// existing one and
					// recreate another new end point.
					EndpointResult endpoint = application.getEndpoint();
					if (endpoint != null) {
						RelayService.deleteEndPoint(
								application.getClientIdentity(),
								endpoint.getRegistrationId(),
								endpoint.getSecretKey(), null, null);
						application.getGroups().Clear();
					}

					RelayService.createEndPoint(
							application.getClientIdentity(),
							RelayApplicationName,
							new OnCompleteListener<EndpointResult>() {
								public void done(EndpointResult result) {
									serviceResult = result;
								}
							}, null);
				} catch (Exception exception) {
					return dialogToShow("Couldn't execute the Relay operation",
							exception);
				}

				if (serviceResult.getStatus() == microsoft.hawaii.hawaiiClientLibraryBase.Status.Success) {
					application.setEndpoint(serviceResult);
				} else {
					return dialogToShow("Error creating a new endpoint",
							serviceResult.getException());
				}

				return null;
			}

			protected void onPreExecute() {
				progressBar.setVisibility(View.VISIBLE);
				mygrouplistview.setAdapter(null);
			}

			protected void onPostExecute(AlertDialog.Builder dialogBuilder) {
				if (dialogBuilder != null) {
					showErrorMessage(dialogBuilder);
				} else {
					refreshMyEndpointArea();
				}

				progressBar.setVisibility(View.GONE);
				createEndpointButton.setEnabled(true);
				createEndpointTask = null;
			}
		}

		createEndpointTask = new CreateEndpointTask();
		createEndpointTask.execute();
	}
}
