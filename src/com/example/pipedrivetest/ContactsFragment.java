package com.example.pipedrivetest;

import java.util.ArrayList;
import java.util.List;

import static com.example.pipedrivetest.util.Util.*;

import com.example.pipedrivetest.model.Data;
import com.example.pipedrivetest.model.ResponseBody;
import com.google.gson.Gson;
import com.loopj.android.http.*;

import android.app.ListFragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import org.apache.http.Header;

/**
 * Displays all your contacts from your pipedrive account.
 * 
 * @author indrek k�ue
 * 
 */
public class ContactsFragment extends ListFragment {

	private static final String KEY_LIST_SCROLL_STATE = "listScrollState";

	// loaded contacts- we need the ID of specific contacts from here when
	// navigatin to detailsFragment and this list maintains objects already
	// loaded so we don't have to do it again
	private ArrayList<Data> loadedContacts;

	// count of items loaded in single batch
	private final int itemsPerScreen = 15;

	// does the server have more items to load
	private boolean canLoadMore = true;

	// indicator displayed in the end of the list when user scrolls to the end
	// of list and there is more items to load
	private ProgressBar loadingMoreProgressIndicator;

	// holds scroll state of the listview, so when user returns to this fragment
	// from another fragment through backstack (fragment is not destroyed yet)
	// we can restore the position where
	// user was previously scrolled
	private Parcelable listScrollState;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		// do we have a state to restore?
		if (savedInstanceState != null
				&& savedInstanceState
						.getParcelableArrayList(KEY_LOADED_OBJECTS) != null) {
			loadedContacts = savedInstanceState
					.getParcelableArrayList(KEY_LOADED_OBJECTS);
			listScrollState = savedInstanceState
					.getParcelable(KEY_LIST_SCROLL_STATE);
		}

		super.onCreate(savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();

		loadingMoreProgressIndicator = new ProgressBar(getActivity());

		final String apiToken = getApiTokenFromPersistantStorage(getActivity());

		// do we have some loaded data or do we need to reload it from server?
		if (loadedContacts != null) {
			setAdapterAndOnClick(convertDataListToPersonNameList(loadedContacts));

			// restore previous scroll position
			if (listScrollState != null)
				getListView().onRestoreInstanceState(listScrollState);
		} else {
			queryFromServerAndAppendToViews(apiToken, 0, itemsPerScreen, false);
		}

		getListView().setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// triggered when user scrolls to the end of the list
				Log.d(TAG, "load more: " + page + " " + totalItemsCount);

				int pagingStart = (page - 1) * itemsPerScreen;

				if (canLoadMore)
					queryFromServerAndAppendToViews(apiToken, pagingStart,
							itemsPerScreen, true);
			}
		});

	}

	@Override
	public void onStop() {
		super.onStop();

		// save scrollposition of the list when user navigates to another
		// fragment (fragment is not destroied yet)
		listScrollState = getListView().onSaveInstanceState();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

		// put loaded contacts+listview state to bundle to retrieve after
		// configuration change
		outState.putParcelableArrayList(KEY_LOADED_OBJECTS, loadedContacts);

		if (getActivity() != null && getView() != null)
			outState.putParcelable(KEY_LIST_SCROLL_STATE, getListView()
					.onSaveInstanceState());

		super.onSaveInstanceState(outState);
	}

	/**
	 * 
	 * @param apiToken
	 *            for verifying access to the data
	 * @param start
	 *            paging start
	 * @param itemCount
	 *            paging count
	 * @param isAppend
	 *            is the new data going to be appended to the end of adapter
	 */
	private void queryFromServerAndAppendToViews(String apiToken, int start,
			int itemCount, final boolean isAppend) {

		// show loading more indicator
		loadingMoreProgressIndicator.setVisibility(View.VISIBLE);

		String requestUrl = new Uri.Builder().scheme(API_PROTOCOL)
				.authority(API_AUTHORITY).appendPath(API_VER)
				.appendPath(API_METHOD_CONTACTS)
				.appendQueryParameter("start", Integer.toString(start))
				.appendQueryParameter("limit", Integer.toString(itemCount))
				.appendQueryParameter("sort_mode", "asc")
				.appendQueryParameter(API_PARAM_TOKEN, apiToken).build()
				.toString();

		new AsyncHttpClient().get(requestUrl,
				new BaseJsonHttpResponseHandler<ResponseBody>() {

					@Override
					public void onFailure(int arg0, Header[] arg1,
							Throwable arg2, String arg3, ResponseBody arg4) {

						requestFailed(getActivity(), arg2, arg4);
						loadingMoreProgressIndicator.setVisibility(View.GONE);
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2,
							ResponseBody arg3) {

						loadingMoreProgressIndicator.setVisibility(View.GONE);

						if (isResultValidAndUiReady(arg3, getActivity())
								&& arg3.getData() != null) {

							// is there more data available
							canLoadMore = arg3.getAdditional_data()
									.getPagination()
									.getMore_items_in_collection();

							// if there isn't more data to load in the next
							// request, remove footer because
							// setting visibility to gone would hide the
							// indicator BUT would still display empty list item
							if (canLoadMore == false)
								getListView().removeFooterView(
										loadingMoreProgressIndicator);

							// store reference for later - we need details id
							// when moving to DetailsFragment
							if (loadedContacts == null)
								loadedContacts = arg3.getData();
							else
								loadedContacts.addAll(arg3.getData());

							// do we need to append to the end of the list or is
							// it the first load for this list
							if (isAppend) {
								@SuppressWarnings("unchecked")
								ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
								adapter.addAll(convertDataListToPersonNameList(arg3
										.getData()));
								adapter.notifyDataSetChanged();
							} else {
								setAdapterAndOnClick(convertDataListToPersonNameList(arg3
										.getData()));
							}

						}

					}

					@Override
					protected ResponseBody parseResponse(String arg0,
							boolean arg1) throws Throwable {

						return new Gson().fromJson(arg0, ResponseBody.class);
					}
				}

		);
	}

	/**
	 * Creates an adapter and adds adapter to list. Also adds onClickListener to
	 * listview ites
	 * 
	 * @param names
	 *            List of strings to attach to view
	 */
	public void setAdapterAndOnClick(List<String> names) {

		getListView().addFooterView(loadingMoreProgressIndicator);

		// making a custom adapter with custom child views would allow us to
		// display the items more throughly and allow us to use viewHolder
		// pattern for more optimized result but currently this is out of scope
		// of this assignment and would be "over-engineering"
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, names));

		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if (loadedContacts == null || loadedContacts.size() <= arg2)
					return;

				// switch to details fragment
				DetailsFragment detailsFragment = DetailsFragment
						.newInstance(loadedContacts.get(arg2).getId());

				getFragmentManager().beginTransaction()
						.replace(R.id.fragmentPlaceHolder, detailsFragment)
						.addToBackStack(null).commit();

			}
		});
	}

	/**
	 * Extracts person names from data list
	 * 
	 * @param contacts
	 *            To extract names from
	 * @return List of names
	 */
	public List<String> convertDataListToPersonNameList(List<Data> contacts) {
		List<String> names = new ArrayList<String>();
		for (Data person : contacts) {
			names.add(person.getName());
		}
		return names;
	}

	/**
	 * Helper class for determine when we need to load more objects to contacts
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

			// If it�s still loading, we check to see if the dataset count has
			// changed, if so we conclude it has finished loading and update the
			// current page
			// number and total item count.
			if (loading && (totalItemCount > previousTotalItemCount)) {
				loading = false;
				previousTotalItemCount = totalItemCount;
				currentPage++;
			}

			// If it isn�t currently loading, we check to see if we have
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
