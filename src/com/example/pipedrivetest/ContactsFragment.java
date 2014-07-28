package com.example.pipedrivetest;

import java.util.ArrayList;
import java.util.List;

import com.example.pipedrivetest.model.Data;
import com.example.pipedrivetest.model.ResponseBody;
import com.example.pipedrivetest.util.Util;
import com.google.gson.Gson;
import com.loopj.android.http.*;

import android.app.ListFragment;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import org.apache.http.Header;

public class ContactsFragment extends ListFragment {

	public List<Data> data;

	@Override
	public void onStart() {

		new AsyncHttpClient().get(
				"http://api.pipedrive.com/v1/persons?start=0&sort_mode=asc&api_token="
						+ getActivity().getPreferences(Context.MODE_PRIVATE)
								.getString("api_token", ""),
				new BaseJsonHttpResponseHandler<ResponseBody>() {

					@Override
					public void onFailure(int arg0, Header[] arg1,
							Throwable arg2, String arg3, ResponseBody arg4) {
						Util.logError(this.getClass().getSimpleName() + " "
								+ arg2);
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2,
							ResponseBody arg3) {

						if (arg3.getSuccess()) {

							List<String> names = new ArrayList<String>();

							data = arg3.getData();
							for (Data person : arg3.getData()) {
								names.add(person.getName());
							}

							setListAdapter(new ArrayAdapter<String>(
									getActivity(),
									android.R.layout.simple_list_item_1, names));

							getListView().setOnItemClickListener(
									new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {

											DetailsFragment deFrag = DetailsFragment
													.newInstance(data.get(arg2)
															.getId());

											getFragmentManager()
													.beginTransaction()
													.replace(
															R.id.fragmentPlaceHolder,
															deFrag)
													.addToBackStack(null)
													.commit();

										}
									});

						}

					}

					@Override
					protected ResponseBody parseResponse(String arg0,
							boolean arg1) throws Throwable {

						return new Gson().fromJson(arg0, ResponseBody.class);
					}
				}

		);

		super.onStart();
	}

}
