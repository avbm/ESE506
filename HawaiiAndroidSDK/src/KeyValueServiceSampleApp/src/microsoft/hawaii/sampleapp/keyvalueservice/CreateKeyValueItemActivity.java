/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 */
package microsoft.hawaii.sampleapp.keyvalueservice;

import java.util.Date;

import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sdk.keyValueService.KeyValueService;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.KeyValueItem;
import microsoft.hawaii.sdk.keyValueService.ServiceContracts.SetResult;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 *
 */
public class CreateKeyValueItemActivity extends HawaiiBaseActivity {
	static final int MISSING_KEY_FIELD_VALUE = 10;

	private Button submitButton;
	private ProgressBar progressBar;
	private TextView operationStatusTextView;

	private AsyncTask<Void, Void, AlertDialog.Builder> currentTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_create_keyvalueitem);

		this.operationStatusTextView = (TextView) findViewById(R.id.createkvitem_operationstatus);
		this.progressBar = (ProgressBar) findViewById(R.id.createkvitem_progressbar);
		this.submitButton = (Button) findViewById(R.id.createkvitem_add_button);
		this.submitButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				onClickSubmitButton(view);
			}
		});

		Button backButton = (Button) this
				.findViewById(R.id.createkvitem_back_button);
		backButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CreateKeyValueItemActivity.this.finish();
			}
		});
	}

	private void onClickSubmitButton(View view) {
		this.submitButton.setEnabled(false);

		final EditText keyEditText = (EditText) findViewById(R.id.createkvitem_key_edittext);
		final EditText valueEditText = (EditText) findViewById(R.id.createkvitem_value_edittext);
		final String key = keyEditText.getText().toString().trim();
		final String value = valueEditText.getText().toString().trim();

		if (Utility.isStringNullOrEmpty(key)) {
			this.showErrorMessage(
					getString(R.string.error_missing_key_field_value), null);
			return;
		}

		final CreateKeyValueItemActivity thisActivity = this;
		class CreateKeyValueItemTask extends
				AsyncTask<Void, Void, AlertDialog.Builder> {
			private SetResult serviceResult;

			protected void onPreExecute() {
				keyEditText.setVisibility(View.GONE);
				valueEditText.setVisibility(View.GONE);
				progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			protected AlertDialog.Builder doInBackground(Void... params) {
				try {
					KeyValueItem[] kvItems = new KeyValueItem[] { new KeyValueItem(
							s_CommonKeyPrefix + key, value, new Date()) };
					KeyValueService.upset(thisActivity.getBaseApplication()
							.getClientIdentity(), kvItems,
							new OnCompleteListener<SetResult>() {
								public void done(SetResult result) {
									serviceResult = result;
								}
							}, null);
				} catch (Exception exception) {
					return dialogToShow("Couldn't create the item", exception);
				}

				if (serviceResult.getStatus() != microsoft.hawaii.hawaiiClientLibraryBase.Status.Success) {
					return dialogToShow("Error creating a new endpoint",
							serviceResult.getException());
				}

				return null;
			}

			protected void onPostExecute(AlertDialog.Builder builder) {
				if (builder != null) {
					thisActivity.showErrorMessage(builder);
				} else {
					operationStatusTextView.setText(serviceResult.getStatus()
							.toString());
					thisActivity.finishCurrent(true);
				}

				progressBar.setVisibility(View.GONE);
				keyEditText.setVisibility(View.VISIBLE);
				valueEditText.setVisibility(View.VISIBLE);
			}
		}

		currentTask = new CreateKeyValueItemTask();
		currentTask.execute();
	}

	@Override
	protected void onPause() {
		super.onPause();
		AsyncTask<Void, Void, AlertDialog.Builder> task = currentTask;
		if (task != null) {
			task.cancel(true);
			currentTask = null;
		}
	}

	public final static String CREATE_OPERATION_RESULT = "CREATE_OPERATION_RESULT";

	private void finishCurrent(boolean successful) {
		if (successful) {
			this.getIntent().putExtra(CREATE_OPERATION_RESULT, true);
			this.setResult(RESULT_OK, this.getIntent());
		}

		this.finish();
	}
}
