package com.example.pipedrivetest;

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

	private static final String ARGUMENT_ID = "id";

	public static DetailsFragment newInstance(Number number) {

		DetailsFragment detailsFrag = new DetailsFragment();

		Bundle bundle = new Bundle();
		bundle.putInt(ARGUMENT_ID, number.intValue());
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

		new AsyncHttpClient().get("http://api.pipedrive.com/v1/persons/"
				+ getArguments().getInt(ARGUMENT_ID)
				+ "?api_token=6b36cf0c4b2fa9c4bbfc0596912d30fe04c55e55",
				new BaseJsonHttpResponseHandler<ResponseBodyDetails>() {

					@Override
					public void onFailure(int arg0, Header[] arg1,
							Throwable arg2, String arg3,
							ResponseBodyDetails arg4) {
						Util.logError(this.getClass().getSimpleName() + " "
								+ arg2);
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2,
							ResponseBodyDetails arg3) {
						getView().findViewById(R.id.progressBar1)
								.setVisibility(View.GONE);
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

		super.onStart();
	}

}
