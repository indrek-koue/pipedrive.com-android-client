package com.example.pipedrivetest;

import java.util.ArrayList;
import java.util.List;

import static com.example.pipedrivetest.util.Util.*;

import com.example.pipedrivetest.model.Data;
import com.example.pipedrivetest.model.ResponseBody;
import com.example.pipedrivetest.util.Util;
import com.google.gson.Gson;
import com.loopj.android.http.*;

import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import org.apache.http.Header;
import org.apache.http.protocol.HTTP;

public class ContactsFragment extends ListFragment {

	public List<Data> data;

	final int itemsPerScreen = 15;
	private boolean canLoadMore = true;

	@Override
	public void onStart() {
		super.onStart();

		final String apiToken = getApiTokenFromPersistantStorage(getActivity());

		queryFromServerAndAppendToViews(apiToken, 0, itemsPerScreen, false);

		getListView().setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Log.d("my", "load more: " + page + " " + totalItemsCount);

				int pagingStart = (page - 1) * itemsPerScreen;

				if (canLoadMore)
					queryFromServerAndAppendToViews(apiToken, pagingStart,
							itemsPerScreen, true);
			}
		});

	}

	/**
	 * 
	 * @param apiToken
	 *            for verifying access to the data
	 * @param start
	 *            paging start
	 * @param limit
	 *            paging count
	 * @param isAppend
	 *            is the new data going to be appended to the end of adapter
	 */
	private void queryFromServerAndAppendToViews(String apiToken, int start,
			int limit, final boolean isAppend) {

		// show loading indicator
		setListShown(false);

		String requestUrl = new Uri.Builder().scheme(API_PROTOCOL)
				.authority(API_AUTHORITY).appendPath(API_VER)
				.appendPath(API_METHOD_CONTACTS)
				.appendQueryParameter("start", Integer.toString(start))
				.appendQueryParameter("limit", Integer.toString(limit))
				.appendQueryParameter("sort_mode", "asc")
				.appendQueryParameter(API_PARAM_TOKEN, apiToken).build()
				.toString();

		new AsyncHttpClient().get(requestUrl,
				new BaseJsonHttpResponseHandler<ResponseBody>() {

					@Override
					public void onFailure(int arg0, Header[] arg1,
							Throwable arg2, String arg3, ResponseBody arg4) {

						setListShown(true);
						requestFailed(getActivity(), arg2, arg4);
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2,
							ResponseBody arg3) {

						if (arg3 != null && arg3.getSuccess()
								&& arg3.getData() != null) {

							canLoadMore = arg3.getAdditional_data()
									.getPagination()
									.getMore_items_in_collection();

							// store reference for later - we need details id
							// when moving to detailsfragment

							if (data == null)
								data = arg3.getData();
							else
								data.addAll(arg3.getData());

							// convert data objects to list of names
							List<String> names = new ArrayList<String>();
							for (Data person : arg3.getData()) {
								names.add(person.getName());
							}

							// is load more or load first time
							if (isAppend) {
								ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
								adapter.addAll(names);
								adapter.notifyDataSetChanged();
							} else {
								setAdapterAndOnClick(names);
							}

						}

						// hide loading indicators
						setListShown(true);

					}

					@Override
					protected ResponseBody parseResponse(String arg0,
							boolean arg1) throws Throwable {

						return new Gson().fromJson(arg0, ResponseBody.class);
					}
				}

		);
	}

	public void setAdapterAndOnClick(List<String> names) {

		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, names));

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				DetailsFragment deFrag = DetailsFragment.newInstance(data.get(
						arg2).getId());

				getFragmentManager().beginTransaction()
						.replace(R.id.fragmentPlaceHolder, deFrag)
						.addToBackStack(null).commit();

			}
		});
	}

	/**
	 * Helper class for determing when we need to load more objects to contacts
	 * list
	 * 
	 * Copyright
	 * https://github.com/thecodepath/android_guides/wiki/Endless-Scrolling-with
	 * -AdapterViews
	 */

	public abstract class EndlessScrollListener implements OnScrollListener {
		// The minimum amount of items to have below your current scroll
		// position
		// before loading more.
		private int visibleThreshold = 0;
		// The current offset index of data you have loaded
		private int currentPage = 0;
		// The total number of items in the dataset after the last load
		private int previousTotalItemCount = 0;
		// True if we are still waiting for the last set of data to load.
		private boolean loading = true;
		// Sets the starting page index
		private int startingPageIndex = 0;

		public EndlessScrollListener() {
		}

		public EndlessScrollListener(int visibleThreshold) {
			this.visibleThreshold = visibleThreshold;
		}

		public EndlessScrollListener(int visibleThreshold, int startPage) {
			this.visibleThreshold = visibleThreshold;
			this.startingPageIndex = startPage;
			this.currentPage = startPage;
		}

		// This happens many times a second during a scroll, so be wary of the
		// code
		// you place here.
		// We are given a few useful parameters to help us work out if we need
		// to
		// load some more data,
		// but first we check if we are waiting for the previous load to finish.
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// If the total item count is zero and the previous isn't, assume
			// the
			// list is invalidated and should be reset back to initial state
			if (totalItemCount < previousTotalItemCount) {
				this.currentPage = this.startingPageIndex;
				this.previousTotalItemCount = totalItemCount;
				if (totalItemCount == 0) {
					this.loading = true;
				}
			}

			// If it’s still loading, we check to see if the dataset count has
			// changed, if so we conclude it has finished loading and update the
			// current page
			// number and total item count.
			if (loading && (totalItemCount > previousTotalItemCount)) {
				loading = false;
				previousTotalItemCount = totalItemCount;
				currentPage++;
			}

			// If it isn’t currently loading, we check to see if we have
			// breached
			// the visibleThreshold and need to reload more data.
			// If we do need to reload some more data, we execute onLoadMore to
			// fetch the data.
			if (!loading
					&& (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
				onLoadMore(currentPage + 1, totalItemCount);
				loading = true;
			}
		}

		// Defines the process for actually loading more data based on page
		public abstract void onLoadMore(int page, int totalItemsCount);

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// Don't take any action on changed
		}
	}

}
