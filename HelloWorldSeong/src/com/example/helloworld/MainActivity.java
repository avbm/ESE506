package com.example.helloworld;


import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;


public class MainActivity extends Activity {

	TextView printer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//final Handler mHandler = new Handler();
		//mHandler.postDelayed(mUpdateUITimerTask, 5 * 1000);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*private final Runnable mUpdateUITimerTask = new Runnable() {
	    public void run() {
	    	
			printer = ((TextView)findViewById(R.id.hello_world));
			printer.setText("I hope this is going to work...");
	    }
	};*/
	
	
}
