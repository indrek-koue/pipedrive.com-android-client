package com.example.pipedrivetest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	public static final String API_KEY_PIPEDRIVE = "6b36cf0c4b2fa9c4bbfc0596912d30fe04c55e55";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getFragmentManager().beginTransaction()
				.replace(R.id.fragmentPlaceHolder, new ContactsFragment()).commit();

	}

}
