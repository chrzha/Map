package com.chrzha.map;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.Window;

public class SplashActivity extends Activity {

	boolean isFirstIn = false;
	private final int SPLASH_DISPLAY_LENGHT = 1000; // �ӳ�����
	Intent intent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//�����ޱ�����
		setContentView(R.layout.activity_splash);

		SharedPreferences preferences = getSharedPreferences("first_pref",
				MODE_PRIVATE);
		isFirstIn = preferences.getBoolean("isFirstIn", true);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				if (isFirstIn) {
					intent = new Intent(SplashActivity.this,GuideActivity.class);
				}else {
					intent = new Intent(SplashActivity.this,MainActivity.class);
					
				}
				SplashActivity.this.startActivity(intent);
				SplashActivity.this.finish();
			}

		}, SPLASH_DISPLAY_LENGHT);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
