package com.example.pipedrivetest;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_login, container, false);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub

		getView().findViewById(R.id.button1).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						String userName = getUserInputFromEdittext(R.id.editText1);
						String password = getUserInputFromEdittext(R.id.editText2);

						new LoginAsync(getActivity()).execute(userName,
								password);
					}

					private String getUserInputFromEdittext(int id) {
						return ((EditText) getView().findViewById(id))
								.getText().toString();
					}
				});

		super.onStart();
	}

	class LoginAsync extends AsyncTask<String, Void, Boolean> {

		private Activity a;

		public LoginAsync(Activity a) {
			// TODO Auto-generated constructor stub

			this.a = a;

			a.findViewById(R.id.progressBar1).setVisibility(View.VISIBLE);

		}

		@Override
		protected Boolean doInBackground(String... params) {

			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(Boolean result) {

			Toast.makeText(a, result ? "success" : "failed", Toast.LENGTH_LONG)
					.show();

			a.findViewById(R.id.progressBar1).setVisibility(View.GONE);

			super.onPostExecute(result);
		}

	}

}
