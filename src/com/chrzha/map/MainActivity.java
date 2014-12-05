package com.chrzha.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

public class MainActivity extends Activity {

	private MapView mMapView = null;
	private BaiduMap mBaiduMap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		//get connection
		ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting();

		if (wifi | internet) {
			// ִ����ز���
			// ��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext
			// ע��÷���Ҫ��setContentView����֮ǰʵ��
			SDKInitializer.initialize(getApplicationContext());
			setContentView(R.layout.activity_main);
			// ��ȡ��ͼ�ؼ�����
			mMapView = (MapView) findViewById(R.id.id_bmapView);

		} else {
			Toast.makeText(getApplicationContext(), "�ף���������ô��",
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onDestroy();
		mMapView = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// ��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onPause();
	}

	public void showMenu(View view) {
		super.openOptionsMenu();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, Menu.FIRST + 1, 1, "General Map");
		menu.add(Menu.NONE, Menu.FIRST + 2, 2, "Satellite Map");
		menu.add(Menu.NONE, Menu.FIRST + 3, 3, "Other Test");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case Menu.FIRST + 1:
			mBaiduMap = mMapView.getMap();
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
			break;

		case Menu.FIRST + 2:
			mBaiduMap = mMapView.getMap();
			mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
			break;
		case Menu.FIRST + 3:
			Intent intent = new Intent();
			/* ָ��intentҪ�������� */
			intent.setClass(MainActivity.this, SubActivity.class);
			/* ����һ���µ�Activity */
			startActivity(intent);
			/* �رյ�ǰ��Activity */
			// MainActivity.this.finish();

			break;
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			dialog();
			return true;
		}
		return true;
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setMessage("ȷ��Ҫ�˳���?");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// AccoutList.this.finish();
						// System.exit(1);
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}
				});
		builder.setNegativeButton("ȡ��",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	public void search() {
		
		PoiSearch mPoiSearch = PoiSearch.newInstance();

		OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
			public void onGetPoiResult(PoiResult result) {
				// ��ȡPOI�������
			}

			public void onGetPoiDetailResult(PoiDetailResult result) {
				// ��ȡPlace����ҳ�������
			}
		};

		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

		mPoiSearch.searchInCity((new PoiCitySearchOption()).city("����")
				.keyword("��ʳ").pageNum(10));

		mPoiSearch.destroy();
	}

}
