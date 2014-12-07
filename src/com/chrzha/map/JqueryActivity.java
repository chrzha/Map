package com.chrzha.map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JqueryActivity extends Activity {
	private static final String TAG = "JqueryActivity";
	private ProgressDialog dialog;
	private WebView webView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_jquery);

		webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().supportMultipleWindows();
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.loadUrl("file:///android_asset/index.jsp");
		//webView.loadUrl("http://192.168.1.104:8088/hotel");

		dialog = new ProgressDialog(this);

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				dialog.setProgress(newProgress * 100);
				super.onProgressChanged(view, newProgress);
			}
		});
		webView.setWebViewClient(new MyWebViewClient());

	}

	private class MyWebViewClient extends WebViewClient {
		@Override
		public void onLoadResource(WebView view, String url) {

			Log.i(TAG, "onLoadResource:" + url);

			super.onLoadResource(view, url);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			Log.i(TAG, "onReceivedError:" + failingUrl + " \n errorcode="
					+ errorCode);
			super.onReceivedError(view, errorCode, description, failingUrl);
		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.i(TAG, "shouldOverrideUrlLoading:" + url);
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			Log.i(TAG, "onPageStarted:" + url);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			Log.i(TAG, "onPageFinished:" + url);

		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);

	}
}