package com.chrzha.map;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.ViewSwitcher.ViewFactory;

public class SubActivity extends Activity implements OnClickListener,ViewFactory,OnItemClickListener{

	private ImageSwitcher imageSwitcher;
	private Button buttonPre,buttonNext;
	private int index = 0;
	private List<Drawable> drawableList = new ArrayList<Drawable>();
	private GridView mGridView;
	private int[] imgIds;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub);
		final TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
		tabHost.setup();

		TabSpec spec1 = tabHost.newTabSpec("Tab 1");
		spec1.setContent(R.id.tab1);
		spec1.setIndicator("Tab 1");

		TabSpec spec2 = tabHost.newTabSpec("Tab 2");
		spec2.setIndicator("Tab 2");
		spec2.setContent(R.id.tab2);

		TabSpec spec3 = tabHost.newTabSpec("Tab 3");
		spec3.setIndicator("Tab 3");
		spec3.setContent(R.id.tab3);

		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		tabHost.addTab(spec3);

		tabHost.setCurrentTab(0);

		// 初始化设置一次标签背景
	//	updateTabBackground(tabHost);

		// 选择时背景更改。
		/*tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				updateTabBackground(tabHost);
			}
		});*/
		imageSwitcher = (ImageSwitcher) this.findViewById(R.id.imageSwitcher1);
		buttonPre = (Button) this.findViewById(R.id.buttonPre);
		buttonNext = (Button) this.findViewById(R.id.buttonNext);
		buttonPre.setOnClickListener(this);
		buttonNext.setOnClickListener(this);
		drawableList.add(getResources().getDrawable(R.drawable.item01));
		drawableList.add(getResources().getDrawable(R.drawable.item02));
		drawableList.add(getResources().getDrawable(R.drawable.item03));
		imageSwitcher.setFactory(this);
		if (drawableList.size()>0) {
			imageSwitcher.setImageDrawable(drawableList.get(0));
		}
		
		imgIds = new int[]{R.drawable.item01,R.drawable.item02,R.drawable.item03,R.drawable.item04,
				R.drawable.item05, R.drawable.item06, R.drawable.item07, R.drawable.item08,R.drawable.item09,
				R.drawable.item10, R.drawable.item11, R.drawable.item12};
		
		mGridView = (GridView) findViewById(R.id.gridView1);
		mGridView.setAdapter(new GridAdapter());
		mGridView.setOnItemClickListener(this);
		

	}
	
	public class GridAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return imgIds.length;
		}

		@Override
		public Object getItem(int position) {
			return imgIds[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if(convertView == null){
				convertView = LayoutInflater.from(getApplication()).inflate(R.layout.item, null);
				viewHolder = new ViewHolder();
				viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView1);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
		
			viewHolder.imageView.setPadding(4, 4, 4, 4);
			viewHolder.imageView.setImageResource(imgIds[position]);
			
			return convertView;
		}
		
		
		class ViewHolder {
			public ImageView imageView;
		}
		
	}


	
	 /**
     * 更新Tab标签的背景图
     * @param tabHost
     */
   /* private void updateTabBackground(final TabHost tabHost) {
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View vvv = tabHost.getTabWidget().getChildAt(i);
            if (tabHost.getCurrentTab() == i) {
                //选中后的背景
                vvv.setBackgroundDrawable(getResources().getDrawable(R.drawable.menu_itembg_pressed));
            } else {
                //非选择的背景
                vvv.setBackgroundDrawable(getResources().getDrawable(R.drawable.menu_itembg_focused));
            }
        }
    }*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sub, menu);
		return true;
	}


	@Override
	public View makeView() {
		// TODO Auto-generated method stub
		return new ImageView(SubActivity.this);
	}


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.buttonPre:
			index--;
			if (index<0) {
				index = drawableList.size()-1;
			}
			imageSwitcher.setImageDrawable(drawableList.get(index));
			break;
		case R.id.buttonNext:
			index++;
			if (index>=drawableList.size()) {
				index = 0;
			}
			imageSwitcher.setImageDrawable(drawableList.get(index));
			break;

		default:
			break;
		}
		
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("position", position);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setClass(getApplication(), ShowPhotoActivity.class);
		startActivity(intent);
	}

}
