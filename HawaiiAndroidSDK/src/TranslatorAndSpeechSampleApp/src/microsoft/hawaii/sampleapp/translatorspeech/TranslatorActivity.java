package microsoft.hawaii.sampleapp.translatorspeech;

import java.util.List;

import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sampleappbase.HawaiiBaseApplication;
import microsoft.hawaii.sdk.translatorService.TranslatorService;
import microsoft.hawaii.sdk.translatorService.ServiceContracts.GetLanguagesForTranslateResponse;
import microsoft.hawaii.sdk.translatorService.ServiceContracts.TranslateResponse;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class TranslatorActivity extends BaseActivity {

	private ProgressBar progressBar;
	private EditText fromTextEdit;
	private TextView toTextEdit;
	private Button translateButton;
	private Spinner fromLanguagesSpinner;
	private Spinner toLanguagesSpinner;

	private AsyncTask<Void, Integer, AlertDialog.Builder> translateTask;
	private AsyncTask<Void, Integer, AlertDialog.Builder> getLanguagesTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_translator);

		this.progressBar = (ProgressBar) this
				.findViewById(R.id.translator_progressBar);
		this.fromTextEdit = (EditText) this
				.findViewById(R.id.translator_editText_fromText);
		this.toTextEdit = (TextView) this
				.findViewById(R.id.translator_textView_toText);
		this.fromLanguagesSpinner = (Spinner) this
				.findViewById(R.id.translator_spinner_fromLanguages);
		this.toLanguagesSpinner = (Spinner) this
				.findViewById(R.id.translator_spinner_toLanguages);

		this.translateButton = (Button) this
				.findViewById(R.id.translator_button_translate);
		this.translateButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onTranslateButtonClick(v);
			}
		});

		Button backButton = (Button) this
				.findViewById(R.id.translator_button_back);
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TranslatorActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_translator, menu);
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();

		bindLanguagePickers(false);
	}

	private void onTranslateButtonClick(final View v) {
		final String text = this.fromTextEdit.getText().toString();
		if (Utility.isStringNullOrEmpty(text)) {
			this.showErrorMessage("please input some text before translating",
					null);
			return;
		}

		v.setEnabled(false);
		final TranslatorActivity thisActivity = this;
		class TranslateTask extends
				AsyncTask<Void, Integer, AlertDialog.Builder> {

			private HawaiiBaseApplication application = thisActivity
					.getBaseApplication();
			private TranslateResponse serviceResult;

			protected AlertDialog.Builder doInBackground(Void... listTypes) {
				String fromLan = thisActivity.fromLanguagesSpinner
						.getSelectedItem().toString();
				String toLan = thisActivity.toLanguagesSpinner
						.getSelectedItem().toString();
				try {
					TranslatorService.translate(
							application.getClientIdentity(), text, toLan,
							fromLan,
							new OnCompleteListener<TranslateResponse>() {
								public void done(TranslateResponse result) {
									serviceResult = result;
								}
							}, null);
				} catch (Exception exception) {
					return dialogToShow(
							"Couldn't execute the translator operation",
							exception);
				}

				if (serviceResult.getStatus() != microsoft.hawaii.hawaiiClientLibraryBase.Status.Success) {
					return dialogToShow("Error when translating",
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
					thisActivity.toTextEdit.setText(serviceResult
							.getTextTranslated());
				}

				v.setEnabled(true);
				progressBar.setVisibility(View.GONE);
				translateTask = null;
			}
		}

		translateTask = new TranslateTask();
		translateTask.execute();
	}

	private void bindLanguagePickers(boolean calledFromTask) {
		List<String> languageList = this.getBaseApplication()
				.getTranslateLanguageList();
		if (languageList != null) {
			if (languageList.isEmpty()) {
				this.translateButton.setEnabled(false);
				this.showErrorMessage("no languages are available", null);
				return;
			}

			this.translateButton.setEnabled(true);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item, languageList);
			this.fromLanguagesSpinner.setAdapter(adapter);
			this.toLanguagesSpinner.setAdapter(adapter);
		} else {
			if (languageList == null) {
				if (!calledFromTask) {
					getAvailableLanguages(true);
				} else {
					this.showErrorMessage("fail to get available languages",
							null);
				}
			}
		}
	}

	private void getAvailableLanguages(final boolean refreshPickers) {
		final TranslatorActivity thisActivity = this;
		class GetLanguagesTask extends
				AsyncTask<Void, Integer, AlertDialog.Builder> {

			private HawaiiBaseApplication application = thisActivity
					.getBaseApplication();
			private GetLanguagesForTranslateResponse serviceResult;

			protected AlertDialog.Builder doInBackground(Void... listTypes) {
				try {
					TranslatorService
							.getLanguagesForTranslate(
									application.getClientIdentity(),
									"en",
									new OnCompleteListener<GetLanguagesForTranslateResponse>() {
										public void done(
												GetLanguagesForTranslateResponse result) {
											serviceResult = result;
										}
									}, null);
				} catch (Exception exception) {
					return dialogToShow(
							"Couldn't execute the translator operation",
							exception);
				}

				if (serviceResult.getStatus() != microsoft.hawaii.hawaiiClientLibraryBase.Status.Success) {
					return dialogToShow(
							"Error when getting available languages",
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
					thisActivity.getBaseApplication().setTranslateLanguageList(
							serviceResult.getSupportedLanguages());
					if (refreshPickers) {
						bindLanguagePickers(true);
					}
				}

				progressBar.setVisibility(View.GONE);
				getLanguagesTask = null;
			}
		}

		getLanguagesTask = new GetLanguagesTask();
		getLanguagesTask.execute();
	}
}
