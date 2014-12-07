package com.chrzha.map;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.Window;

public class GuideActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 6000; // 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//
		setContentView(R.layout.activity_guide);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				 
				Intent intent = new Intent(GuideActivity.this,MainActivity.class);
				 
				GuideActivity.this.startActivity(intent);
				//һ��Ҫ�ǵ��޸�ֵ
				SharedPreferences preferences = getSharedPreferences(
					      "first_pref", MODE_PRIVATE);
					    Editor editor = preferences.edit();
					    editor.putBoolean("isFirstIn", false);
					    editor.commit();
				GuideActivity.this.finish();
			}

		}, SPLASH_DISPLAY_LENGHT);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.guide, menu);
		return true;
	}

}
