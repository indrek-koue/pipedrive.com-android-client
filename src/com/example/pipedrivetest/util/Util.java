package com.example.pipedrivetest.util;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class Util {

	public static final String API_URL = "http://api.pipedrive.com/v1/";
	public static final String PREFKEY_API_TOKEN = "api_token";

	public static final String API_METHOD_AUTHORIZATIONS = "authorizations";
	public static final String API_METHOD_AUTHORIZATIONS_PARAM_PASSWORD = "password";
	public static final String API_METHOD_AUTHORIZATIONS_PARAM_EMAIL = "email";

	public static void logError(String s) {
		Log.e("MY", s);
	}
	
	public static void showMessage(Activity a, String msg){
		Toast.makeText(a, msg, Toast.LENGTH_LONG).show();;
	}

}
