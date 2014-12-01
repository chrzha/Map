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

		// 判断网络连接
		ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting();

		if (wifi | internet) {
			// 执行相关操作
			// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
			// 注意该方法要再setContentView方法之前实现
			SDKInitializer.initialize(getApplicationContext());
			setContentView(R.layout.activity_main);
			// 获取地图控件引用
			mMapView = (MapView) findViewById(R.id.id_bmapView);

		} else {
			Toast.makeText(getApplicationContext(), "亲，网络连了么？",
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
		mMapView = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
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
			/* 指定intent要启动的类 */
			intent.setClass(MainActivity.this, SubActivity.class);
			/* 启动一个新的Activity */
			startActivity(intent);
			/* 关闭当前的Activity */
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
		builder.setMessage("确定要退出吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
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
		builder.setNegativeButton("取消",
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
				// 获取POI检索结果
			}

			public void onGetPoiDetailResult(PoiDetailResult result) {
				// 获取Place详情页检索结果
			}
		};

		mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

		mPoiSearch.searchInCity((new PoiCitySearchOption()).city("北京")
				.keyword("美食").pageNum(10));

		mPoiSearch.destroy();
	}

}
