package com.taohu.androidcookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

	private WebView wv;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//url = "http://www.pivotaltracker.com/";
		//url = "http://99designs.com/";
		wv = (WebView) findViewById(R.id.wv_webtab);
		url = "http://9slides.com/";
		setCookies2();
		initWebView(R.id.wv_webtab);
		String cookie = CookieManager.getInstance().getCookie(url);
		Log.d("Cookie", url + "   " + cookie);
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("Set-Cookie", cookie);
		wv.loadUrl(url, map);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void setCookies(JSONObject json) throws JSONException {
		// Log.d("Cookie", "enter setCookies()");
		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		JSONArray json_cookie_arr = json.getJSONArray("cookies");
		for (int i = 0; i < json_cookie_arr.length(); i++) {
			JSONObject json_cookie = json_cookie_arr.getJSONObject(i);
			String cookie = getCookieFromJSON(json_cookie);
			Log.d("Cookie", cookie);
			cookieManager.setCookie(json.getString("url"), cookie);
			// Log.d("Cookie",
			// cookieManager.getCookie(json_cookie.getString("url")));
		}
		CookieSyncManager.getInstance().sync();
	}

	private void setCookies2(){
		// Log.d("Cookie", "enter setCookies()");
		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeAllCookie();
		cookieManager.setAcceptCookie(true);
		//String cookieStr = "url=https://www.pivotaltracker.com/;domain=null;name=t_session;path=/;value=BAh7DDoUbGFzdF9sb2dpbl9kYXRlVTogQWN0aXZlU3VwcG9ydDo6VGltZVdpdGhab25lWwhJdToJVGltZQ32iBzAAAAAHgY6H0BtYXJzaGFsX3dpdGhfdXRjX2NvZXJjaW9uVCIfUGFjaWZpYyBUaW1lIChVUyAmIENhbmFkYSlJdTsHDe6IHMAAAAAeBjsIVDoPZXhwaXJlc19hdEl1OwcN1okcgPnRrCgGOwhGOhZza2lwX2Vycm9yX2VzY2FwZVQ6HXZpZXdlZF9kYXNoYm9hcmRfbWVzc2FnZVQ6FXNpZ25pbl9wZXJzb25faWRpA7KJEjoQX2NzcmZfdG9rZW4iMStRY2RLUmovWEFSYkJZbG5kVllYR1VpQTgwMXpSZEdTOXkxa3dlenU0UDg9Og9zZXNzaW9uX2lkIiUyNGE5MjFlOGNmY2ZiNzBjMzhlYzJjNTJlYzFkZmUxZQ%3D%3D--f93c43d2e449fde01cab06b56a3d31180698cb74";
		//String cookieStr = "url=https://www.pivotaltracker.com/;domain=.pivotaltracker.com;name=t_session;path=/;value=BAh7DDoUbGFzdF9sb2dpbl9kYXRlVTogQWN0aXZlU3VwcG9ydDo6VGltZVdpdGhab25lWwhJdToJVGltZQ32iBzAAAAAHgY6H0BtYXJzaGFsX3dpdGhfdXRjX2NvZXJjaW9uVCIfUGFjaWZpYyBUaW1lIChVUyAmIENhbmFkYSlJdTsHDe6IHMAAAAAeBjsIVDoPZXhwaXJlc19hdEl1OwcN1okcgPnRrCgGOwhGOhZza2lwX2Vycm9yX2VzY2FwZVQ6HXZpZXdlZF9kYXNoYm9hcmRfbWVzc2FnZVQ6FXNpZ25pbl9wZXJzb25faWRpA7KJEjoQX2NzcmZfdG9rZW4iMStRY2RLUmovWEFSYkJZbG5kVllYR1VpQTgwMXpSZEdTOXkxa3dlenU0UDg9Og9zZXNzaW9uX2lkIiUyNGE5MjFlOGNmY2ZiNzBjMzhlYzJjNTJlYzFkZmUxZQ%3D%3D--f93c43d2e449fde01cab06b56a3d31180698cb74";
		String cookieStr = "domain=.9slides.com;name=forms9slides;path=/;value=827BE93D78F40881746DA7177AB6EF41B64877AAEFE8A678B3D64FC0D75C22A9322700E425B358914E3F8FC2B299733852E5DAF6B6500A81F1DD1FAAC22D02F6A4250F07A213C910680A57DC890062BE878C26E98AD5F8F7746135AEECFF940739248165BC3CE42F6E20FE2C3ADC14BF80264FCDE64B28D50A08B5FFFDE38C2D4B851D097E63BEF17332FEE97B6CFFAA9A9C4E78C144EE9774DD3422E78E009446C2E500B07CF542807B41524CD469DC"; 
		cookieManager.setCookie(url, cookieStr);
		CookieSyncManager.getInstance().sync();
	}

	public static String getCookieFromJSON(JSONObject json_cookie) throws JSONException {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("url", json_cookie.getString("url")));
		params.add(new BasicNameValuePair("domain", json_cookie.getString("domain")));
		params.add(new BasicNameValuePair("name", json_cookie.getString("name")));
		params.add(new BasicNameValuePair("path", json_cookie.getString("path")));
		params.add(new BasicNameValuePair("value", json_cookie.getString("value")));
		StringBuilder cookie = new StringBuilder();
		for (NameValuePair p : params)
			cookie.append(p + ";");
		cookie.deleteCharAt(cookie.length() - 1);
		return cookie.toString();
	}

	public void initWebView(int containerId) {
		
		wv.getSettings().setAppCacheEnabled(true);
		wv.getSettings().setAppCachePath(this.getCacheDir().getAbsolutePath());
		wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setWebViewClient(new WebTabClient());
		wv.setWebChromeClient(new WebTabChromeClient());
	}

	public class WebTabClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return false;
		}

		// @Override
		// public void onLoadResource(WebView view, String url) {
		// //Log.d("Search", url);
		// super.onLoadResource(view, url);
		// }
		// @Override
		// public void onPageStarted(WebView view, String url, Bitmap favicon) {
		// super.onPageStarted(view, url, favicon);
		// Log.d("Tab", " onPageStarted() ");
		// if (mOnTabPageStartedListener==null)
		// throw new
		// NullPointerException("OnTabPageStartedListener should be initialized");
		// mOnTabPageStartedListener.onTabPageStarted(view,
		// Long.parseLong(getTag())-1);
		// }
		//
		// @Override
		// public void onPageFinished(WebView view, String url) {
		// super.onPageFinished(view, url);
		// if (mOnTabPageFinishedListener==null)
		// throw new
		// NullPointerException("OnTabPageFinishedListener should be initialized");
		// mOnTabPageFinishedListener.onTabPageFinished(view,
		// Long.parseLong(getTag())-1);
		// }
	}

	public class WebTabChromeClient extends WebChromeClient {
		// @Override
		// public void onReceivedIcon(WebView view, Bitmap icon) {
		// super.onReceivedIcon(view, icon);
		// Log.d("Tab", " onReceivedIcon() " + icon);
		// if (mOnTabIconReceivedListener==null)
		// throw new
		// NullPointerException("OnTabIconReceivedListener should be initialized");
		// mOnTabIconReceivedListener.onTabIconReceived(view,
		// Long.parseLong(getTag())-1, icon);
		// }
	}

}
