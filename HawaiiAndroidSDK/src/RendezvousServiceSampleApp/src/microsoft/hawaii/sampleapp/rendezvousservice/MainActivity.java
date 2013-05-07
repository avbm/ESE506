package microsoft.hawaii.sampleapp.rendezvousservice;

import microsoft.hawaii.hawaiiClientLibraryBase.Identities.ClientIdentity;
import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sampleappbase.HawaiiBaseAuthActivity;
import microsoft.hawaii.sdk.RendezvousService.RendezvousService;
import microsoft.hawaii.sdk.RendezvousService.ServiceContracts.NameRegistrationResult;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends HawaiiBaseAuthActivity {

	private EditText nameEditText;
	private EditText registrationIdEditText;
	private ProgressBar progressBar;

	private AsyncTask<Void, Integer, AlertDialog.Builder> rendezvousTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.nameEditText = (EditText) this
				.findViewById(R.id.main_editText_name);
		this.registrationIdEditText = (EditText) this
				.findViewById(R.id.main_edittext_registrationId);
		this.progressBar = (ProgressBar) this
				.findViewById(R.id.main_progressbar);

		Button registerNameButton = (Button) this
				.findViewById(R.id.main_button_registerName);
		Button unregisterNameButton = (Button) this
				.findViewById(R.id.main_button_unregisterName);
		Button associateIdButton = (Button) this
				.findViewById(R.id.main_button_associateId);
		Button disassociateIdButton = (Button) this
				.findViewById(R.id.main_button_disassociateId);
		Button lookupNameButton = (Button) this
				.findViewById(R.id.main_button_lookupName);

		registerNameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				manageButtonClick(v, RendezvousOperationType.RegisterName);
			}
		});

		unregisterNameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				manageButtonClick(v, RendezvousOperationType.UnregisterName);
			}
		});

		associateIdButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				manageButtonClick(v, RendezvousOperationType.AssocaiteId);
			}
		});

		disassociateIdButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				manageButtonClick(v, RendezvousOperationType.DisassociateId);
			}
		});

		lookupNameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				manageButtonClick(v, RendezvousOperationType.LookupName);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	protected RendezvousApplication getBaseApplication() {
		return (RendezvousApplication) this.getApplication();
	}

	enum RendezvousOperationType {
		RegisterName, UnregisterName, AssocaiteId, DisassociateId, LookupName
	}

	private void manageButtonClick(final View v,
			final RendezvousOperationType operationType) {
		String name = nameEditText.getText().toString();
		if (Utility.isStringNullOrEmpty(name)) {
			this.showErrorMessage(
					"Invalid name found. Enter a name and try again", null);
			return;
		}

		String registrationId = registrationIdEditText.getText().toString();
		String secretKey = RendezvousStorage.getSecretKey(
				this.getApplicationContext(), name);

		if (operationType != RendezvousOperationType.RegisterName
				&& operationType != RendezvousOperationType.LookupName) {
			if (operationType != RendezvousOperationType.UnregisterName) {
				if (Utility.isStringNullOrEmpty(registrationId)) {
					this.showErrorMessage(
							"Invalid registration id found. Enter a registration id and try again",
							null);
					return;
				}
			}

			if (Utility.isStringNullOrEmpty(secretKey)) {
				this.showErrorMessage(
						"You are not the owner of this name or the name ownership details are lost. You can't perform this operation",
						null);
				return;
			}
		}

		final NameRegistrationResult registration = new NameRegistrationResult();
		registration.setName(name);
		registration.setId(registrationId);
		registration.setSecretkey(secretKey);

		v.setEnabled(false);

		final MainActivity thisActivity = this;
		class RendezvousTask extends
				AsyncTask<Void, Integer, AlertDialog.Builder> {

			private RendezvousApplication application = thisActivity
					.getBaseApplication();
			private NameRegistrationResult serviceResult;

			protected AlertDialog.Builder doInBackground(Void... listTypes) {
				OnCompleteListener<NameRegistrationResult> listener = new OnCompleteListener<NameRegistrationResult>() {
					public void done(NameRegistrationResult result) {
						serviceResult = result;
					}
				};

				try {
					ClientIdentity identity = application.getClientIdentity();
					switch (operationType) {
					case RegisterName:
						RendezvousService.registerName(identity,
								registration.getName(), listener, null);
						break;
					case UnregisterName:
						RendezvousService.unRegisterName(identity,
								registration, listener, null);
						break;
					case AssocaiteId:
						RendezvousService.associateId(identity, registration,
								listener, null);
						break;
					case DisassociateId:
						RendezvousService.disAssociateId(identity,
								registration, listener, null);
						break;
					case LookupName:
						RendezvousService.lookupName(identity,
								registration.getName(), listener, null);
						break;
					default:
						return dialogToShow(
								"Invalid operation type for rendezvous service",
								null);
					}
				} catch (Exception exception) {
					return dialogToShow(
							"Couldn't execute the rendezvous operation",
							exception);
				}

				if (serviceResult.getStatus() != microsoft.hawaii.hawaiiClientLibraryBase.Status.Success) {
					return dialogToShow(
							"Error occurred when calling rendezvous service",
							serviceResult.getException());
				}

				return null;
			}

			protected void onPreExecute() {
				progressBar.setVisibility(View.VISIBLE);
			}

			protected void onPostExecute(AlertDialog.Builder dialogBuilder) {
				if (dialogBuilder != null) {
					showErrorMessage(dialogBuilder);
				} else {
					updateRegistrationInfo(serviceResult, operationType);
				}

				progressBar.setVisibility(View.GONE);
				v.setEnabled(true);
				rendezvousTask = null;
			}
		}

		rendezvousTask = new RendezvousTask();
		rendezvousTask.execute();
	}

	private void updateRegistrationInfo(NameRegistrationResult result,
			RendezvousOperationType operationType) {
		switch (operationType) {
		case RegisterName:
			this.setName(result.getName());
			RendezvousStorage.setSecretKey(this.getApplicationContext(),
					result.getName(), result.getSecretkey());
			this.showMessage("Registered name successfully");
			break;
		case UnregisterName:
			this.setName(null);
			this.setRegistrationId(null);
			this.showMessage("Unregistered name successfully");
			break;
		case AssocaiteId:
			this.getBaseApplication().setAll(result);
			this.showMessage("Associated Id successfully");
			break;
		case DisassociateId:
			result.setId(null);
			this.getBaseApplication().setAll(result);
			this.showMessage("Disssociated Id successfully");
			break;
		case LookupName:
			this.getBaseApplication().setAll(result);
			this.showMessage(String.format("Looked up Id: %s", result.getId()));
			break;
		default:
			this.showErrorMessage("Invalid operation", null);
		}
	}

	private void setName(String name) {
		this.getBaseApplication().setName(name);
	}

	private void setRegistrationId(String registrationId) {
		this.getBaseApplication().setRegistrationId(registrationId);
	}
}
