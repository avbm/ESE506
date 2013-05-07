package microsoft.hawaii.sampleapp.translatorspeech;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import microsoft.hawaii.hawaiiClientLibraryBase.Listeners.OnCompleteListener;
import microsoft.hawaii.hawaiiClientLibraryBase.Util.Utility;
import microsoft.hawaii.sampleappbase.HawaiiBaseApplication;
import microsoft.hawaii.sdk.speechToTextService.SpeechToTextService;
import microsoft.hawaii.sdk.speechToTextService.ServiceContracts.SpeechServiceResult;
import android.app.AlertDialog;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class SpeechToTextActivity extends STTBaseActivity {

	private MediaPlayer mediaPlayer;
	private MediaRecorder mediaRecorder;

	private ProgressBar progressBar;
	private Spinner grammarsSpinner;
	private ListView recognizedResultListView;

	private Button recordButton;
	private Button stopButton;
	private Button playButton;
	private Button recognizeButton;
	private Button loadResourceButton;

	private boolean isAudioRecording;
	private boolean isAudioPlaying;
	private byte[] audioBuffer;
	private List<String> recognitionList;

	private AsyncTask<Void, Integer, AlertDialog.Builder> speechToTextTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_speech_to_text);

		this.progressBar = (ProgressBar) this
				.findViewById(R.id.speechToText_progressBar);
		this.grammarsSpinner = (Spinner) this
				.findViewById(R.id.speechToText_spinner_grammarList);
		this.recognizedResultListView = (ListView) this
				.findViewById(R.id.speechToText_listView_recognizedResultList);

		this.recordButton = (Button) this
				.findViewById(R.id.speechToText_button_record);
		this.stopButton = (Button) this
				.findViewById(R.id.speechToText_button_stop);
		this.playButton = (Button) this
				.findViewById(R.id.speechToText_button_play);
		this.recognizeButton = (Button) this
				.findViewById(R.id.speechToText_button_recognize);

		this.recordButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				recordButtonOnClick(v);
			}
		});

		this.stopButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopButtonOnClick(v);
			}
		});

		this.playButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				playButtonOnClick(v);
			}
		});

		this.recognizeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				recognizeButtonOnClick(v);
			}
		});

		this.loadResourceButton = (Button) this
				.findViewById(R.id.speechToText_button_loadResource);
		this.loadResourceButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadResourceButtonOnClick(v);
			}
		});

		Button backButton = (Button) this
				.findViewById(R.id.speechToText_button_back);
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				SpeechToTextActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_speech_to_text, menu);
		return true;
	}

	@Override
	public void onStart() {
		super.onStart();

		refreshGrammarList(false);
	}

	@Override
	public void onPause() {
		super.onPause();

		if (this.speechToTextTask != null) {
			this.speechToTextTask = null;
		}

		if (this.isAudioPlaying) {
			this.releaseMediaPlayer();
		}

		if (this.isAudioRecording) {
			this.releaseMediaRecorder();
		}
	}

	private void recordButtonOnClick(View v) {
		try {
			if (this.isAudioRecording) {
				this.mediaRecorder.stop();
				this.releaseMediaRecorder();
			}

			this.mediaRecorder = MediaUtility.createMediaRecorder();
			this.mediaRecorder.start();
		} catch (Exception ex) {
			this.showErrorMessage("failed to create MediaRecorder", ex);
		}

		this.isAudioRecording = true;
		this.isAudioPlaying = false;

		this.setActionButtonEnabledStates(true, true, false, false);
		this.loadResourceButton.setEnabled(false);
	}

	private void stopButtonOnClick(View v) {
		this.setActionButtonEnabledStates(true, false, true, true);

		if (this.isAudioRecording) {
			this.mediaRecorder.stop();
			this.isAudioRecording = false;
			this.audioBuffer = MediaUtility.getTempAudioFileContent();
		} else if (this.isAudioPlaying) {
			this.mediaPlayer.stop();
			this.isAudioPlaying = false;
		}

		this.loadResourceButton.setEnabled(true);
	}

	private void playButtonOnClick(View v) {
		this.isAudioPlaying = true;
		this.isAudioRecording = false;
		this.playAudio(this.audioBuffer);
		this.setActionButtonEnabledStates(false, true, false, false);
		this.loadResourceButton.setEnabled(false);
	}

	private void loadResourceButtonOnClick(View v) {
		InputStream fis = null;
		try {
			fis = this.getResources().openRawResource(R.raw.test);
			if (fis != null) {
				this.audioBuffer = new byte[fis.available()];
				fis.read(this.audioBuffer);
				fis.close();
			}
		} catch (Exception ex) {
			this.showErrorMessage("failed to read audio from resource file", ex);
			return;
		}

		this.setActionButtonEnabledStates(true, false, true, true);
	}

	private void recognizeButtonOnClick(final View v) {
		if (this.audioBuffer == null || this.audioBuffer.length < 1) {
			this.showErrorMessage("record audio firstly", null);
			return;
		}

		v.setEnabled(false);
		final SpeechToTextActivity thisActivity = this;
		class RecognizeSpeechTask extends
				AsyncTask<Void, Integer, AlertDialog.Builder> {

			private HawaiiBaseApplication application = thisActivity
					.getBaseApplication();
			private SpeechServiceResult serviceResult;

			protected AlertDialog.Builder doInBackground(Void... listTypes) {
				String grammar = thisActivity.grammarsSpinner.getSelectedItem()
						.toString();
				if (Utility.isStringNullOrEmpty(grammar)) {
					grammar = "Dictation";
				}

				try {
					SpeechToTextService.Recognition(thisActivity.audioBuffer,
							grammar, application.getClientIdentity(),
							new OnCompleteListener<SpeechServiceResult>() {
								public void done(SpeechServiceResult result) {
									serviceResult = result;
								}
							}, null);
				} catch (Exception exception) {
					return dialogToShow(
							"Couldn't execute the speech to text operation",
							exception);
				}

				if (serviceResult.getStatus() != microsoft.hawaii.hawaiiClientLibraryBase.Status.Success) {
					return dialogToShow("Error when recognizing speech",
							serviceResult.getException());
				}

				return null;
			}

			protected void onPreExecute() {
				if (thisActivity.recognitionList != null) {
					thisActivity.recognitionList = null;
					refreshRecognitionListView(false);
				}

				progressBar.setVisibility(View.VISIBLE);
			}

			protected void onPostExecute(AlertDialog.Builder dialogBuilder) {
				if (dialogBuilder != null) {
					showErrorMessage(dialogBuilder);
				} else {
					thisActivity.recognitionList = Arrays.asList(serviceResult
							.getItems());
					refreshRecognitionListView(true);
				}

				progressBar.setVisibility(View.GONE);
				v.setEnabled(true);
				speechToTextTask = null;
			}
		}

		speechToTextTask = new RecognizeSpeechTask();
		speechToTextTask.execute();
	}

	private void refreshRecognitionListView(boolean displayError) {
		if (this.recognitionList == null || this.recognitionList.isEmpty()) {
			this.recognizedResultListView.setAdapter(null);
			if (displayError) {
				this.showErrorMessage("failed to recognize your speech", null);
			}
			return;
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, this.recognitionList);
		this.recognizedResultListView.setAdapter(adapter);
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

	private void releaseMediaRecorder() {
		MediaUtility.releaseMediaRecorder(this.mediaRecorder);
		this.mediaRecorder = null;
	}

	private void setActionButtonEnabledStates(boolean record, boolean stop,
			boolean play, boolean recognize) {
		this.recordButton.setEnabled(record);
		this.stopButton.setEnabled(stop);
		this.playButton.setEnabled(play);
		this.recognizeButton.setEnabled(recognize);
	}

	private void refreshGrammarList(boolean calledFromTask) {
		List<String> grammarList = this.getBaseApplication().getGrammarList();
		if (grammarList != null) {
			if (grammarList.isEmpty()) {
				this.showErrorMessage("no grammars are available", null);
				return;
			}

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item, grammarList);
			this.grammarsSpinner.setAdapter(adapter);

		} else {
			if (!calledFromTask) {
				getGrammarList(true);
			} else {
				this.showErrorMessage("fail to get available grammars", null);
			}
		}

		this.setActionButtonEnabledStates(true, false, false, false);
	}

	private void getGrammarList(final boolean refreshUI) {
		final SpeechToTextActivity thisActivity = this;
		class GetGrammarListTask extends
				AsyncTask<Void, Integer, AlertDialog.Builder> {

			private HawaiiBaseApplication application = thisActivity
					.getBaseApplication();
			private SpeechServiceResult serviceResult;

			protected AlertDialog.Builder doInBackground(Void... listTypes) {
				try {
					SpeechToTextService.GetGrammars(
							application.getClientIdentity(),
							new OnCompleteListener<SpeechServiceResult>() {
								public void done(SpeechServiceResult result) {
									serviceResult = result;
								}
							}, null);
				} catch (Exception exception) {
					return dialogToShow(
							"Couldn't execute the speech to text operation",
							exception);
				}

				if (serviceResult.getStatus() != microsoft.hawaii.hawaiiClientLibraryBase.Status.Success) {
					return dialogToShow(
							"Error when getting available grammars",
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
					thisActivity.getBaseApplication().setGrammarList(
							Arrays.asList(serviceResult.getItems()));
					if (refreshUI) {
						refreshGrammarList(true);
					}
				}

				progressBar.setVisibility(View.GONE);
				speechToTextTask = null;
			}
		}

		speechToTextTask = new GetGrammarListTask();
		speechToTextTask.execute();
	}
}
