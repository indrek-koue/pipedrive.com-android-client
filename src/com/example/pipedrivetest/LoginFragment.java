package com.example.pipedrivetest;

import org.apache.http.Header;

import com.example.pipedrivetest.model.ResponseBody;
import com.example.pipedrivetest.model.auth.*;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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

						// new LoginAsync(getActivity()).execute(userName,
						// password);

						authenticate(userName, password);

					}

					private String getUserInputFromEdittext(int id) {
						return ((EditText) getView().findViewById(id))
								.getText().toString();
					}
				});

		super.onStart();
	}

	protected void authenticate(String userName, String password) {
		// TODO Auto-generated method stub
		getView().findViewById(R.id.progressBar1).setVisibility(View.VISIBLE);

		RequestParams params = new RequestParams();
		params.add("email", userName);
		params.add("password", password);

		new AsyncHttpClient().post(
				"http://api.pipedrive.com/v1/authorizations", params,
				new BaseJsonHttpResponseHandler<AuthorizationResponse>() {

					@Override
					public void onFailure(int arg0, Header[] arg1,
							Throwable arg2, String arg3,
							AuthorizationResponse arg4) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2,
							AuthorizationResponse arg3) {
						// TODO Auto-generated method stub

						getView().findViewById(R.id.progressBar1)
								.setVisibility(View.GONE);

						// if has multiple api keys per account, picks first
						if (arg3.getSuccess()) {
							saveApiTokenToPreferences(arg3.getData().get(0)
									.getApi_token());
							getFragmentManager()
									.beginTransaction()
									.replace(R.id.fragmentPlaceHolder,
											new ContactsFragment()).commit();
						}

					}

					@Override
					protected AuthorizationResponse parseResponse(String arg0,
							boolean arg1) throws Throwable {
						// TODO Auto-generated method stub
						return new Gson().fromJson(arg0,
								AuthorizationResponse.class);
					}
				});

	}

	// class LoginAsync extends AsyncTask<String, Void, Boolean> {
	//
	// private Activity a;
	//
	// public LoginAsync(Activity a) {
	// // TODO Auto-generated constructor stub
	//
	// this.a = a;
	//
	// a.findViewById(R.id.progressBar1).setVisibility(View.VISIBLE);
	//
	// }
	//
	// @Override
	// protected Boolean doInBackground(String... params) {
	//
	// // api.pipedrive.com/v1/persons?start=0&sort_mode=asc&api_token=
	// // 6b36cf0c4b2fa9c4bbfc0596912d30fe04c55e55
	//
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Boolean result) {
	//
	// Toast.makeText(a, result ? "success" : "failed", Toast.LENGTH_LONG)
	// .show();
	//
	// a.findViewById(R.id.progressBar1).setVisibility(View.GONE);
	//
	// super.onPostExecute(result);
	// }
	//
	// }

	protected boolean saveApiTokenToPreferences(String api_token) {
		// TODO Auto-generated method stub

		return getActivity().getPreferences(Context.MODE_PRIVATE).edit()
				.putString("api_token", api_token).commit();

	}

}
