package com.example.pipedrivetest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// avoid re-adding the same fragment on configuration change
		if (savedInstanceState == null)
			getFragmentManager().beginTransaction()
					.replace(R.id.fragmentPlaceHolder, new LoginFragment())
					.commit();

	}

}
