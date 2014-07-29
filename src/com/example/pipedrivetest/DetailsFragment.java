package com.example.pipedrivetest;

import static com.example.pipedrivetest.util.Util.*;
import org.apache.http.Header;

import com.example.pipedrivetest.model.ResponseBody;
import com.example.pipedrivetest.model.ResponseBodyDetails;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.example.pipedrivetest.model.Data;
import com.example.pipedrivetest.util.Util;
import com.google.gson.Gson;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailsFragment extends Fragment {

	private static final String CONTACT_ID = "id";
	private View loadingIndicator;

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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_details, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		loadingIndicator = getView().findViewById(R.id.progressBar1);
		loadingIndicator.setVisibility(View.VISIBLE);

		int contactId = getArguments().getInt(CONTACT_ID);

		String requestUrl = API_URL + API_METHOD_CONTACTS + contactId + "?"
				+ API_PARAM_TOKEN
				+ getApiTokenFromPersistantStorage(getActivity());

		new AsyncHttpClient().get(requestUrl,
				new BaseJsonHttpResponseHandler<ResponseBodyDetails>() {

					@Override
					public void onFailure(int arg0, Header[] arg1,
							Throwable arg2, String arg3,
							ResponseBodyDetails arg4) {

						loadingIndicator.setVisibility(View.GONE);
						logError(arg2.toString());
						showMessage(getActivity(), arg4.getError());
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2,
							ResponseBodyDetails arg3) {

						loadingIndicator.setVisibility(View.GONE);

						// display retrived data
						if (arg3 != null && arg3.getSuccess()
								&& arg3.getData() != null)
							((TextView) getView().findViewById(R.id.textView1))
									.setText(arg3.getData().toString());
					}

					@Override
					protected ResponseBodyDetails parseResponse(String arg0,
							boolean arg1) throws Throwable {

						return new Gson().fromJson(arg0,
								ResponseBodyDetails.class);
					}
				});

	}

}
