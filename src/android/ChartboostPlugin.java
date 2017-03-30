//Copyright (c) 2014 Sang Ki Kwon (Cranberrygame)
//Email: cranberrygame@yahoo.com
//Homepage: http://cranberrygame.github.io
//License: MIT (http://opensource.org/licenses/MIT)
package com.cranberrygame.cordova.plugin.ad.chartboost;

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
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.ChartboostDelegate;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.Model.CBError.CBClickError;
import com.chartboost.sdk.Libraries.CBLogging.Level;
//md5
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//Util
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Surface;
//
import java.util.*;

public class ChartboostPlugin extends CordovaPlugin {
	private static final String LOG_TAG = "ChartboostPlugin";
	private CallbackContext callbackContextKeepCallback;
	//
	protected String email;
	protected String licenseKey;
	public boolean validLicenseKey;
	protected String TEST_APP_ID = "55404fc104b01602ff113e68";
	protected String TEST_APP_SIGNATURE = "ce82ad49841edff7891ae44c3e7a502d522fdadd";	
	//
	protected String appId;
	protected String appSignature;
	//
	protected boolean interstitialAdPreload;
	protected boolean moreAppsAdPreload;
	protected boolean rewardedVideoAdPreload;
	
    	@Override
	public void pluginInitialize() {
		super.pluginInitialize();
		//
    	}
	
	//@Override
	//public void onCreate(Bundle savedInstanceState) {//build error
	//	super.onCreate(savedInstanceState);
	//	//
	//}
	
	@Override
	public void onStart() {
		super.onStart();
		Chartboost.onStart(cordova.getActivity());
	}
	
	@Override
	public void onPause(boolean multitasking) {
		super.onPause(multitasking);
		Chartboost.onPause(cordova.getActivity());
	}
	
	@Override
	public void onResume(boolean multitasking) {
		super.onResume(multitasking);
		Chartboost.onResume(cordova.getActivity());
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Chartboost.onStop(cordova.getActivity());
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Chartboost.onDestroy(cordova.getActivity());
	}
	
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
		else if (action.equals("preloadInterstitialAd")) {
			preloadInterstitialAd(action, args, callbackContext);
			
			return true;
		}
		else if (action.equals("showInterstitialAd")) {
			showInterstitialAd(action, args, callbackContext);
						
			return true;
		}
		else if (action.equals("preloadMoreAppsAd")) {
			preloadMoreAppsAd(action, args, callbackContext);
			
			return true;
		}
		else if (action.equals("showMoreAppsAd")) {
			showMoreAppsAd(action, args, callbackContext);
						
			return true;
		}
		else if (action.equals("preloadRewardedVideoAd")) {
			preloadRewardedVideoAd(action, args, callbackContext);
			
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
		final String appSignature = args.getString(1);
		Log.d(LOG_TAG, String.format("%s", appId));			
		Log.d(LOG_TAG, String.format("%s", appSignature));
		
		callbackContextKeepCallback = callbackContext;
			
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_setUp(appId, appSignature);
			}
		});
	}
	
	private void preloadInterstitialAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String location = args.getString(0);
		Log.d(LOG_TAG, String.format("%s", location));
		
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_preloadInterstitialAd(location);
			}
		});
	}

	private void showInterstitialAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String location = args.getString(0);
		Log.d(LOG_TAG, String.format("%s", location));

		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showInterstitialAd(location);
			}
		});
	}

	private void preloadMoreAppsAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String location = args.getString(0);
		Log.d(LOG_TAG, String.format("%s", location));

		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_preloadMoreAppsAd(location);
			}
		});
	}

	private void showMoreAppsAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String location = args.getString(0);
		Log.d(LOG_TAG, String.format("%s", location));

		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showMoreAppsAd(location);
			}
		});
	}

	private void preloadRewardedVideoAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String location = args.getString(0);
		Log.d(LOG_TAG, String.format("%s", location));

		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_preloadRewardedVideoAd(location);
			}
		});
	}

	private void showRewardedVideoAd(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		final String location = args.getString(0);
		Log.d(LOG_TAG, String.format("%s", location));

		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showRewardedVideoAd(location);
			}
		});
	}
	
	public void _setLicenseKey(String email, String licenseKey) {
		this.email = email;
		this.licenseKey = licenseKey;
		
		this.validLicenseKey = false;
		Log.d(LOG_TAG, String.format("%s", "valid licenseKey"));
	}
	
	private void _setUp(String appId, String appSignature) {
		this.appId = appId;
		this.appSignature = appSignature;
		
		if (!validLicenseKey) {
			if (new Random().nextInt(100) <= 1) {//0~99					
				this.appId = TEST_APP_ID;
				this.appSignature = TEST_APP_SIGNATURE;
			}
		}
		
		Chartboost.startWithAppId(cordova.getActivity(), this.appId , this.appSignature);
		Chartboost.setLoggingLevel(Level.ALL);		
		Chartboost.onCreate(cordova.getActivity());
		Chartboost.onStart(cordova.getActivity());
		Chartboost.setDelegate(new MyChartboostDelegate());
	}

	private void _preloadInterstitialAd(String location) {
		interstitialAdPreload = true;
		
		Chartboost.cacheInterstitial(location);	
	}

	private void _showInterstitialAd(String location) {
		interstitialAdPreload = false;		

		Chartboost.showInterstitial(location);	
	}

	private void _preloadMoreAppsAd(String location) {
		moreAppsAdPreload = true;
		
		Chartboost.cacheMoreApps(location);	
	}

	private void _showMoreAppsAd(String location) {
		moreAppsAdPreload = false;
		
		Chartboost.showMoreApps(location);	
	}

	private void _preloadRewardedVideoAd(String location) {
		rewardedVideoAdPreload = true;
		
		Chartboost.cacheRewardedVideo(location);	
	}

	private void _showRewardedVideoAd(String location) {
		rewardedVideoAdPreload = false;
		
		Chartboost.showRewardedVideo(location);	
	}
	
	class MyChartboostDelegate extends ChartboostDelegate {
	
		@Override
		public boolean shouldRequestInterstitial(String location) {
			Log.d(LOG_TAG, "shouldRequestInterstitial: "+ (location != null ? location : "null"));		
			return true;
		}
	
		@Override
		public void didCacheInterstitial(String location) {
			Log.i(LOG_TAG, "didCacheInterstitial: "+ (location != null ? location : "null"));
						
    			if (interstitialAdPreload) {
			
				JSONObject result = new JSONObject();
				try {
					result.put("event", "onInterstitialAdPreloaded");
					result.put("message", location);
				}
				catch(JSONException ex){
				}		
    				
    				PluginResult pr = new PluginResult(PluginResult.Status.OK, result);
    				pr.setKeepCallback(true);
    				callbackContextKeepCallback.sendPluginResult(pr);		
    			}
    		
			JSONObject result = new JSONObject();
			try {
				result.put("event", "onInterstitialAdLoaded");
				result.put("message", location);
			}
			catch(JSONException ex){
			}			
		
			PluginResult pr = new PluginResult(PluginResult.Status.OK, result);
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);				
		}
	
		@Override
		public void didFailToLoadInterstitial(String location, CBImpressionError error) {
			Log.d(LOG_TAG, "didFailToLoadInterstitial: "+ (location != null ? location : "null")+ ", " + error.name());
		}

		@Override
		public boolean shouldDisplayInterstitial(String location) {
			Log.d(LOG_TAG, "shouldDisplayInterstitial: "+ (location != null ? location : "null"));
			
			return true;
		}
		
		@Override
		public void didDisplayInterstitial(String location) {
			Log.d(LOG_TAG, "didDisplayInterstitial: "+ (location != null ? location : "null"));
			
			JSONObject result = new JSONObject();
			try {
				result.put("event", "onInterstitialAdShown");
				result.put("message", location);
			}
			catch(JSONException ex){
			}			
			
			PluginResult pr = new PluginResult(PluginResult.Status.OK, result);
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);				
		}
	
		@Override
		public void didFailToRecordClick(String uri, CBClickError error) {
			Log.d(LOG_TAG, "didFailToRecordClick: " + (uri != null ? uri : "null") + ", error: " + error.name());
		}		
		
		@Override
		public void didClickInterstitial(String location) {
			Log.d(LOG_TAG, "didClickInterstitial: "+ (location != null ? location : "null"));
		}
		
		@Override
		public void didCloseInterstitial(String location) {
			Log.d(LOG_TAG, "didCloseInterstitial: "+ (location != null ? location : "null"));
		}

		@Override
		public void didDismissInterstitial(String location) {
			Log.d(LOG_TAG, "didDismissInterstitial: "+ (location != null ? location : "null"));
			
			JSONObject result = new JSONObject();
			try {
				result.put("event", "onInterstitialAdHidden");
				result.put("message", location);
			}
			catch(JSONException ex){
			}			
			
			PluginResult pr = new PluginResult(PluginResult.Status.OK, result);
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);			
		}
		
		//------------------------
		@Override
		public boolean shouldRequestMoreApps(String location) {
			Log.d(LOG_TAG, "shouldRequestMoreApps: " +  (location != null ? location : "null"));
			return true;
		}

		@Override
		public void didCacheMoreApps(String location) {
			Log.d(LOG_TAG, "didCacheMoreApps: " +  (location != null ? location : "null"));
			
    			if (moreAppsAdPreload) {
				JSONObject result = new JSONObject();
				try {
					result.put("event", "onMoreAppsAdPreloaded");
					result.put("message", location);
				}
				catch(JSONException ex){
				}			
    			
    				PluginResult pr = new PluginResult(PluginResult.Status.OK, result);
    				pr.setKeepCallback(true);
    				callbackContextKeepCallback.sendPluginResult(pr);		
    			}
    		
			JSONObject result = new JSONObject();
			try {
				result.put("event", "onMoreAppsAdLoaded");
				result.put("message", location);
			}
			catch(JSONException ex){
			}			
			
			PluginResult pr = new PluginResult(PluginResult.Status.OK, result);
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);			
		}
		
		@Override
		public void didFailToLoadMoreApps(String location, CBImpressionError error) {
			Log.d(LOG_TAG, "didFailToLoadMoreApps: " +  (location != null ? location : "null")+ " Error: "+ error.name());
		}
				
		@Override
		public boolean shouldDisplayMoreApps(String location) {
			Log.d(LOG_TAG, "shouldDisplayMoreApps: " +  (location != null ? location : "null"));
			
			return true;
		}
	
		@Override
		public void didDisplayMoreApps(String location) {
			Log.d(LOG_TAG, "didDisplayMoreApps: " +  (location != null ? location : "null"));
			
			JSONObject result = new JSONObject();
			try {
				result.put("event", "onMoreAppsAdShown");
				result.put("message", location);
			}
			catch(JSONException ex){
			}			
			
			PluginResult pr = new PluginResult(PluginResult.Status.OK, result);
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);			
		}
			
		@Override
		public void didClickMoreApps(String location) {
			Log.d(LOG_TAG, "didClickMoreApps: "+  (location != null ? location : "null"));
		}
		
		@Override
		public void didCloseMoreApps(String location) {
			Log.d(LOG_TAG, "didCloseMoreApps: "+  (location != null ? location : "null"));
		}

		@Override
		public void didDismissMoreApps(String location) {
			Log.d(LOG_TAG, "didDismissMoreApps: "+  (location != null ? location : "null"));
			
			JSONObject result = new JSONObject();
			try {
				result.put("event", "onMoreAppsAdHidden");
				result.put("message", location);
			}
			catch(JSONException ex){
			}			
			
			PluginResult pr = new PluginResult(PluginResult.Status.OK, result);
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);				
		}
		
		//------------------------		
		@Override
		public void didCacheRewardedVideo(String location) {
			Log.d(LOG_TAG, "didCacheRewardedVideo: "+  (location != null ? location : "null"));
					
    			if (rewardedVideoAdPreload) {
				JSONObject result = new JSONObject();
				try {
					result.put("event", "onRewardedVideoAdPreloaded");
					result.put("message", location);
				}
				catch(JSONException ex){
				}			
    			
    				PluginResult pr = new PluginResult(PluginResult.Status.OK, result);
    				pr.setKeepCallback(true);
    				callbackContextKeepCallback.sendPluginResult(pr);		
    			}
    		
			JSONObject result = new JSONObject();
			try {
				result.put("event", "onRewardedVideoAdLoaded");
				result.put("message", location);
			}
			catch(JSONException ex){
			}			
			
			PluginResult pr = new PluginResult(PluginResult.Status.OK, result);
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);				
		}
		
		@Override
		public void didFailToLoadRewardedVideo(String location,	CBImpressionError error) {
			Log.d(LOG_TAG, "didFailToLoadRewardedVideo: " + (location != null ? location : "null")+ ", "+ error.name());			
		}
		
		@Override
		public boolean shouldDisplayRewardedVideo(String location) {
			Log.d(LOG_TAG, "shouldDisplayRewardedVideo: " + (location != null ? location : "null"));
			
			return true;
		}
	
		@Override
		public void didDisplayRewardedVideo(String location) {
			Log.d(LOG_TAG, "didDisplayRewardedVideo: " + (location != null ? location : "null"));
			
			JSONObject result = new JSONObject();
			try {
				result.put("event", "onRewardedVideoAdShown");
				result.put("message", location);
			}
			catch(JSONException ex){
			}			
			
			PluginResult pr = new PluginResult(PluginResult.Status.OK, result);
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);				
		}
				
		@Override
		public void didClickRewardedVideo(String location) {
			Log.d(LOG_TAG, "didClickRewardedVideo: " + (location != null ? location : "null"));
		}
		
		@Override
		public void didCloseRewardedVideo(String location) {
			Log.d(LOG_TAG, "didCloseRewardedVideo: " + (location != null ? location : "null"));
		}		

		@Override
		public void didDismissRewardedVideo(String location) {
			Log.d(LOG_TAG, "didDismissRewardedVideo: " + (location != null ? location : "null"));
			
			JSONObject result = new JSONObject();
			try {
				result.put("event", "onRewardedVideoAdHidden");
				result.put("message", location);
			}
			catch(JSONException ex){
			}			
			
			PluginResult pr = new PluginResult(PluginResult.Status.OK, result);
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);				
		}

		@Override
		public void didCompleteRewardedVideo(String location, int reward) {
			Log.d(LOG_TAG, "didCompleteRewardedVideo: " + (location != null ? location : "null") + ", "+reward);
			
			JSONObject result = new JSONObject();
			try {
				result.put("event", "onRewardedVideoAdCompleted");
				result.put("message", location);
			}
			catch(JSONException ex){
			}			
			
			PluginResult pr = new PluginResult(PluginResult.Status.OK, result);
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);				
		}

		//----------------------
		@Override
		public void willDisplayVideo(String location) {
			Log.d(LOG_TAG, "willDisplayVideo: " + (location != null ? location : "null"));
		}			
	};	
}
