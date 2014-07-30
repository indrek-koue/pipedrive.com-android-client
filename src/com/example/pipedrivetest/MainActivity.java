package com.example.pipedrivetest;

import android.os.Bundle;
import android.app.Activity;

/**
 * This is the first activity started when application starts and holds all the
 * content (Fragments)
 * 
 * @author indrek kõue
 * 
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// avoid re-adding the same fragment on configuration change
		if (savedInstanceState == null) {

			// First fragment displayed to user is LoginFragment. If user has
			// active API token in persistent storage, then the LoginFragment is
			// replaced instantly with ContactsFragment (takes place inside of
			// LoginFragment)
			getFragmentManager().beginTransaction()
					.replace(R.id.fragmentPlaceHolder, new LoginFragment())
					.commit();
		}
	}

}
