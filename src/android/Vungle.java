//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://www.github.com/cranberrygame
//License: MIT (http://opensource.org/licenses/MIT)
package com.cranberrygame.cordova.plugin.ad.vungle;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import android.annotation.TargetApi;
import android.app.Activity;
import android.util.Log;
//
import com.vungle.publisher.AdConfig;
import com.vungle.publisher.Orientation;
import com.vungle.publisher.VunglePub;
//md5
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//Util
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Surface;
//
import java.util.*;

public class Vungle extends CordovaPlugin {
	private final String LOG_TAG = "Vungle";
	private CallbackContext callbackContextKeepCallback;
	//
	protected String email;
	protected String licenseKey;
	public boolean validLicenseKey;
	protected String TEST_APP_ID = "xxx";//com.cranberrygame.pluginsforcordova
	//
	protected String appId;
	
	// get the VunglePub instance
	final VunglePub vunglePub = VunglePub.getInstance();
	
    	@Override
	public void pluginInitialize() {
		super.pluginInitialize();
    	}
	
	//@Override
	//public void onCreate(Bundle savedInstanceState) {//build error
		//super.onCreate(savedInstanceState);
	//}
	
	//@Override
	//public void onStart() {
		//super.onStart();
	//}
	
	@Override
	public void onPause(boolean multitasking) {
		super.onPause(multitasking);
		vunglePub.onPause();
	}
	
	@Override
	public void onResume(boolean multitasking) {
		super.onResume(multitasking);
		vunglePub.onResume();
	}
	
	//@Override
	//public void onStop() {
		//super.onStop();
	//}
	
	//@Override
	//public void onDestroy() {
		//super.onDestroy();
	//}

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		if (action.equals("setLicenseKey")) {
			setLicenseKey(action, args, callbackContext);

			return true;
		}	
		else if (action.equals("setUp")) {
			setUp(action, args, callbackContext);

			return true;
		}
		else if (action.equals("showRewardedVideoAd")) {
			showRewardedVideoAd(action, args, callbackContext);
						
			return true;
		}
		
		return false; // Returning false results in a "MethodNotFound" error.
	}

	private void setLicenseKey(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String email = args.getString(0);
		final String licenseKey = args.getString(1);				
		Log.d(LOG_TAG, String.format("%s", email));			
		Log.d(LOG_TAG, String.format("%s", licenseKey));
		
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_setLicenseKey(email, licenseKey);
			}
		});
	}
	
	private void setUp(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String appId = args.getString(0);
		Log.d(LOG_TAG, String.format("%s", appId));			
		
		callbackContextKeepCallback = callbackContext;
			
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_setUp(appId);
			}
		});
	}

	private void showRewardedVideoAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showRewardedVideoAd();
			}
		});
	}
	
	public void _setLicenseKey(String email, String licenseKey) {
		this.email = email;
		this.licenseKey = licenseKey;
		
		Log.d(LOG_TAG, String.format("%s", "valid licenseKey"));
		this.validLicenseKey = true;
	}
	
	private void _setUp(String appId) {
		this.appId = appId;
		
		if (!validLicenseKey) {
			if (new Random().nextInt(100) <= 1) {//0~99					
				this.appId = TEST_APP_ID;
			}
		}

		vunglePub.init(cordova.getActivity(), appId);
		
		final AdConfig config = vunglePub.getGlobalAdConfig();
		config.setOrientation(Orientation.autoRotate);//for android
	}
	
	private void _showRewardedVideoAd() {
		vunglePub.playAd();		
	}
} 
