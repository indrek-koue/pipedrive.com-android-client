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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import org.apache.http.Header;

public class ContactsFragment extends ListFragment {

	public List<Data> data;

	@Override
	public void onStart() {
		super.onStart();

		final String apiToken = getActivity().getPreferences(
				Context.MODE_PRIVATE).getString("api_token", "");

		int start = 0;
		final int limit = 15;
		// final boolean isAppend = false;

		load(apiToken, start, limit, false);

		getListView().setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				Log.d("my", "load more: " + page + " " + totalItemsCount);

				load(apiToken, (page - 1) * limit, limit, true);
				// getListView().getAdapter()
				// adapter.addAll(newContacts);
				// adapter.notifyDataSetChanged();

			}
		});

	}

	private void load(String apiToken, int start, int limit,
			final boolean isAppend) {

		setListShown(false);

		new AsyncHttpClient().get(MainActivity.API_URL + "persons?start="
				+ start + "&limit=" + limit + "&sort_mode=asc&api_token="
				+ apiToken, new BaseJsonHttpResponseHandler<ResponseBody>() {

			@Override
			public void onFailure(int arg0, Header[] arg1, Throwable arg2,
					String arg3, ResponseBody arg4) {
				Util.logError(this.getClass().getSimpleName() + " " + arg2);
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2,
					ResponseBody arg3) {

				if (arg3.getSuccess()) {

					List<String> names = new ArrayList<String>();

					if (arg3.getData() == null || arg3.getData().size() == 0) {
						setListShown(true);
						return;
					}

					Log.d("ss", arg3.getData().size() + "");

					data = arg3.getData();
					for (Data person : arg3.getData()) {
						names.add(person.getName());
					}

					if (isAppend) {
						((ArrayAdapter) getListAdapter()).addAll(names);
						((ArrayAdapter) getListAdapter())
								.notifyDataSetChanged();
					} else {
						setListAdapter(new ArrayAdapter<String>(getActivity(),
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
												.addToBackStack(null).commit();

									}
								});
					}

				}

				setListShown(true);

			}

			@Override
			protected ResponseBody parseResponse(String arg0, boolean arg1)
					throws Throwable {

				return new Gson().fromJson(arg0, ResponseBody.class);
			}
		}

		);
	}

	class ContactsAdapter extends ArrayAdapter<Data> {

		public ContactsAdapter(Context context, int resource, List<Data> objects) {
			super(context, resource, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			// TODO Auto-generated method stub
			return super.getView(position, convertView, parent);
		}

	}

}
