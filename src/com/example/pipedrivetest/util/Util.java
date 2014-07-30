package com.example.pipedrivetest.util;

import com.example.pipedrivetest.model.BaseResponse;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Holds functionality which is used throughout the whole application
 * 
 * @author indrek kõue
 * 
 */
public class Util {

	public static final String TAG = "MY";
	public static final String PREFKEY_API_TOKEN = "api_token";

	public static final String API_PROTOCOL = "http";
	public static final String API_AUTHORITY = "api.pipedrive.com";
	public static final String API_VER = "v1";

	public static final String API_METHOD_AUTHORIZATIONS = "authorizations";
	public static final String API_METHOD_AUTHORIZATIONS_PARAM_PASSWORD = "password";
	public static final String API_METHOD_AUTHORIZATIONS_PARAM_EMAIL = "email";
	public static final String API_METHOD_CONTACTS = "persons";

	public static final String API_PARAM_TOKEN = "api_token";

	public static void logError(String s) {
		Log.e(TAG, s);
	}

	/**
	 * Shows message to UI using android.widget.Toast
	 * 
	 * @param activity
	 *            Activity which is the parent of the Toast message
	 * @param msg
	 *            Message to display
	 */
	public static void showMessage(Activity activity, String msg) {
		Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * Logs and shows an error message when request to server has failed
	 * 
	 * @param activity
	 *            Activity used to display Toast message
	 * @param t
	 *            Exception occurred
	 * @param response
	 *            Server response
	 */
	public static void requestFailed(Activity activity, Throwable t,
			BaseResponse response) {

		logError(t.toString());
		if (response != null)
			showMessage(activity, response.getError());
	}

	/**
	 * Retrieves API token from persistent storage
	 * 
	 * @param a
	 *            Activity through which persistent storage (SharedPreferences)
	 *            is accessed
	 * @return API token string if exists, otherwise empty string
	 */
	public static String getApiTokenFromPersistantStorage(Activity a) {
		return a.getPreferences(Context.MODE_PRIVATE).getString(
				PREFKEY_API_TOKEN, "");
	}

	/**
	 * Validates if the result returned from server isn't null and activity
	 * exist on the give time to start drawing views to UI
	 * 
	 * @param response
	 *            Server response
	 * @param activity
	 *            Activity to be checked if exists
	 * @return True if data can be attached to UI
	 */
	public static boolean isResultValidAndUiReady(BaseResponse response,
			Activity activity) {
		return response != null && response.getSuccess() && activity != null;
	}

}
