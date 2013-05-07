package microsoft.hawaii.sampleapp.translatorspeech;

import java.io.ByteArrayInputStream;
import java.util.List;

import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sampleappbase.HawaiiBaseApplication;
import microsoft.hawaii.sdk.translatorService.TranslatorService;
import microsoft.hawaii.sdk.translatorService.ServiceContracts.GetLanguagesForSpeakResponse;
import microsoft.hawaii.sdk.translatorService.ServiceContracts.SpeakResponse;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class TextToSpeechActivity extends BaseActivity {

	private MediaPlayer mediaPlayer;

	private ProgressBar progressBar;
	private EditText fromTextEdit;
	private Spinner fromLanguagesSpinner;
	private Button speakButton;

	private AsyncTask<Void, Integer, AlertDialog.Builder> getLanguagesTask;
	private AsyncTask<Void, Integer, AlertDialog.Builder> getSpeechTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_to_speech);

		this.progressBar = (ProgressBar) this
				.findViewById(R.id.textToSpeech_progressBar);
		this.fromTextEdit = (EditText) this
				.findViewById(R.id.textToSpeech_editText_fromText);
		this.fromLanguagesSpinner = (Spinner) this
				.findViewById(R.id.textToSpeech_spinner_languages);
		this.speakButton = (Button) this
				.findViewById(R.id.textToSpeech_button_speak);

		this.speakButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onSpeakButtonClick(v);
			}
		});

		Button backButton = (Button) this
				.findViewById(R.id.textToSpeech_button_back);
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TextToSpeechActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_text_to_speech, menu);
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();

		bindLanguagePickers(false);
	}

	@Override
	public void onPause() {
		super.onPause();

		this.releaseMediaPlayer();
		if (this.getLanguagesTask != null) {
			this.getLanguagesTask = null;
		}

		if (this.getSpeechTask != null) {
			this.getSpeechTask = null;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		this.releaseMediaPlayer();
	}

	private void onSpeakButtonClick(final View v) {
		final String text = this.fromTextEdit.getText().toString();
		if (Utility.isStringNullOrEmpty(text)) {
			this.showErrorMessage("please input some text before translating",
					null);
			return;
		}

		v.setEnabled(false);
		final TextToSpeechActivity thisActivity = this;
		class GetSpeechTask extends
				AsyncTask<Void, Integer, AlertDialog.Builder> {

			private HawaiiBaseApplication application = thisActivity
					.getBaseApplication();
			private SpeakResponse serviceResult;

			protected AlertDialog.Builder doInBackground(Void... listTypes) {
				String fromLan = thisActivity.fromLanguagesSpinner
						.getSelectedItem().toString();
				try {
					TranslatorService.speak(application.getClientIdentity(),
							text, fromLan, "audio/mp3", "MinSize",
							new OnCompleteListener<SpeakResponse>() {
								public void done(SpeakResponse result) {
									serviceResult = result;
								}
							}, null);
				} catch (Exception exception) {
					return dialogToShow(
							"Couldn't execute the text to speech operation",
							exception);
				}

				if (serviceResult.getStatus() != microsoft.hawaii.hawaiiClientLibraryBase.Status.Success) {
					return dialogToShow(
							"Error when trying to convert text to speech",
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
					thisActivity.showMessage("start to play speech audio");
					thisActivity.playAudio(serviceResult.getAudio());
				}

				v.setEnabled(true);
				progressBar.setVisibility(View.GONE);
				getSpeechTask = null;
			}
		}

		getSpeechTask = new GetSpeechTask();
		getSpeechTask.execute();
	}

	private void playAudio(byte[] audio) {
		this.releaseMediaPlayer();

		ByteArrayInputStream inputStream = new ByteArrayInputStream(audio);
		this.mediaPlayer = MediaUtility.createMediaPlayer(inputStream);
		if (this.mediaPlayer != null) {
			this.mediaPlayer.start();
		} else {
			this.showErrorMessage("cannot create MediaPlayer object", null);
		}
	}

	private void releaseMediaPlayer() {
		MediaUtility.releaseMediaPlayer(this.mediaPlayer);
		this.mediaPlayer = null;
	}

	private void bindLanguagePickers(boolean calledFromTask) {
		List<String> languageList = this.getBaseApplication()
				.getSpeechLanguageList();
		if (languageList != null) {
			if (languageList.isEmpty()) {
				this.speakButton.setEnabled(false);
				this.showErrorMessage("no languages are available", null);
				return;
			}

			this.speakButton.setEnabled(true);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item, languageList);
			this.fromLanguagesSpinner.setAdapter(adapter);
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
		final TextToSpeechActivity thisActivity = this;
		class GetLanguagesTask extends
				AsyncTask<Void, Integer, AlertDialog.Builder> {

			private HawaiiBaseApplication application = thisActivity
					.getBaseApplication();
			private GetLanguagesForSpeakResponse serviceResult;

			protected AlertDialog.Builder doInBackground(Void... listTypes) {
				try {
					TranslatorService
							.getLanguagesForSpeak(
									application.getClientIdentity(),
									"en",
									new OnCompleteListener<GetLanguagesForSpeakResponse>() {
										public void done(
												GetLanguagesForSpeakResponse result) {
											serviceResult = result;
										}
									}, null);
				} catch (Exception exception) {
					return dialogToShow(
							"Couldn't execute the text to speech operation",
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
					thisActivity.getBaseApplication().setSpeechLanguageList(
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
