package com.example.pipedrivetest;

import static com.example.pipedrivetest.util.Util.*;

import org.apache.http.Header;

import com.example.pipedrivetest.model.Data;
import com.example.pipedrivetest.model.ResponseBodyDetails;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.google.gson.Gson;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Displays the details of selected contact
 * 
 * @author indrek k�ue
 * 
 */
public class DetailsFragment extends Fragment {

	// key for storing contact ID in bundle
	private static final String CONTACT_ID = "id";

	// UI indicator if loading is taking place
	private View loadingIndicator;

	private Data loadedContact;

	/*
	 * Using arguments bundle guarantees when the fragment is recreated by the
	 * system that the values from bundle are restored.
	 * 
	 * Why am I using static method? I could overload the default constructor,
	 * but then I would also need to create an empty constructor which would
	 * result in messier code
	 */
	public static DetailsFragment newInstance(Number contactId) {

		DetailsFragment detailsFrag = new DetailsFragment();

		Bundle bundle = new Bundle();
		bundle.putInt(CONTACT_ID, contactId.intValue());
		detailsFrag.setArguments(bundle);

		return detailsFrag;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// do we have a state to restore?
		if (savedInstanceState != null
				&& savedInstanceState.getParcelable(KEY_LOADED_OBJECTS) != null) {
			loadedContact = savedInstanceState
					.getParcelable(KEY_LOADED_OBJECTS);
		}

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_details, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		loadingIndicator = getView().findViewById(R.id.progressBar1);

		if (loadedContact != null) {
			// user already loaded
			attachToUi(loadedContact);

		} else {
			loadingIndicator.setVisibility(View.VISIBLE);

			// setup request
			int contactId = getArguments().getInt(CONTACT_ID);
			String apiToken = getApiTokenFromPersistantStorage(getActivity());
			String requestUrl = new Uri.Builder().scheme(API_PROTOCOL)
					.authority(API_AUTHORITY).appendPath(API_VER)
					.appendPath(API_METHOD_CONTACTS)
					.appendPath(Integer.toString(contactId))
					.appendQueryParameter(API_PARAM_TOKEN, apiToken).build()
					.toString();

			new AsyncHttpClient().get(requestUrl,
					new BaseJsonHttpResponseHandler<ResponseBodyDetails>() {

						@Override
						public void onFailure(int arg0, Header[] arg1,
								Throwable arg2, String arg3,
								ResponseBodyDetails arg4) {

							loadingIndicator.setVisibility(View.GONE);
							requestFailed(getActivity(), arg2, arg4);
						}

						@Override
						public void onSuccess(int arg0, Header[] arg1,
								String arg2, ResponseBodyDetails arg3) {

							loadingIndicator.setVisibility(View.GONE);

							// display retrived data
							if (isResultValidAndUiReady(arg3, getActivity())
									&& arg3.getData() != null) {

								loadedContact = arg3.getData();
								attachToUi(loadedContact);
							}
						}

						@Override
						protected ResponseBodyDetails parseResponse(
								String arg0, boolean arg1) throws Throwable {

							return new Gson().fromJson(arg0,
									ResponseBodyDetails.class);
						}
					});
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		// put loaded contacts+listview state to bundle to retrieve after
		// configuration change
		outState.putParcelable(KEY_LOADED_OBJECTS, loadedContact);

		super.onSaveInstanceState(outState);
	}

	public void attachToUi(Data contact) {
		((TextView) getView().findViewById(R.id.textView1)).setText(contact
				.toString());
	}

}