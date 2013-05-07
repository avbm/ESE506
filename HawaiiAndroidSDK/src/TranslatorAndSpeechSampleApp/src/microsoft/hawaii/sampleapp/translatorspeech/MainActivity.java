package microsoft.hawaii.sampleapp.translatorspeech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button translateButton = (Button) this
				.findViewById(R.id.main_button_translate);
		Button textToSpeechButton = (Button) this
				.findViewById(R.id.main_button_textToSpeech);
		Button speechToTextButton = (Button) this
				.findViewById(R.id.main_button_speechToText);

		translateButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						TranslatorActivity.class);
				startActivity(intent);
			}
		});

		textToSpeechButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						TextToSpeechActivity.class);
				startActivity(intent);
			}
		});

		speechToTextButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						SpeechToTextActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}
