package com.example.pipedrivetest;

import org.apache.http.Header;

import com.example.pipedrivetest.model.auth.*;

import static com.example.pipedrivetest.util.Util.*;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class LoginFragment extends Fragment {

	private View loadingIndicator;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_login, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		Boolean isUserAuthenticated = !getApiTokenFromPersistantStorage(
				getActivity()).equals("");

		// if user is authenticated, skip login fragment
		if (isUserAuthenticated) {
			getFragmentManager().beginTransaction()
					.replace(R.id.fragmentPlaceHolder, new ContactsFragment())
					.commit();
			return;
		}

		loadingIndicator = getView().findViewById(R.id.progressBar1);

		getView().findViewById(R.id.button1).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						String userName = getUserInputFromEdittext(R.id.editText1);
						String password = getUserInputFromEdittext(R.id.editText2);
						authenticate(userName, password);

					}

					private String getUserInputFromEdittext(int id) {
						return ((EditText) getView().findViewById(id))
								.getText().toString();
					}
				});

	}

	private void authenticate(String userName, String password) {

		loadingIndicator.setVisibility(View.VISIBLE);

		RequestParams params = new RequestParams();
		params.add(API_METHOD_AUTHORIZATIONS_PARAM_EMAIL, userName);
		params.add(API_METHOD_AUTHORIZATIONS_PARAM_PASSWORD, password);

		String requestUrl = new Uri.Builder().scheme(API_PROTOCOL)
				.authority(API_AUTHORITY).appendPath(API_VER)
				.appendPath(API_METHOD_AUTHORIZATIONS).build().toString();

		new AsyncHttpClient().post(requestUrl, params,
				new BaseJsonHttpResponseHandler<AuthorizationResponse>() {

					@Override
					public void onFailure(int arg0, Header[] arg1,
							Throwable arg2, String arg3,
							AuthorizationResponse arg4) {

						loadingIndicator.setVisibility(View.GONE);
						requestFailed(getActivity(), arg2, arg4);
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2,
							AuthorizationResponse arg3) {

						loadingIndicator.setVisibility(View.GONE);

						if (arg3 != null && arg3.getSuccess()
								&& arg3.getData() != null) {

							// if has multiple api keys per account, picks first
							String apiToken = arg3.getData().get(0)
									.getApi_token();

							saveApiTokenToPersistantStorage(apiToken);

							// display next fragment
							getFragmentManager()
									.beginTransaction()
									.replace(R.id.fragmentPlaceHolder,
											new ContactsFragment()).commit();
						}
					}

					@Override
					protected AuthorizationResponse parseResponse(String arg0,
							boolean arg1) throws Throwable {

						return new Gson().fromJson(arg0,
								AuthorizationResponse.class);
					}
				});

	}

	/**
	 * @return true if the new values were successfully written to persistent
	 *         storage.
	 */
	protected boolean saveApiTokenToPersistantStorage(String apiToken) {
		return getActivity().getPreferences(Context.MODE_PRIVATE).edit()
				.putString(PREFKEY_API_TOKEN, apiToken).commit();
	}

}
