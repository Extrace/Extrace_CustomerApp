package com.track.app.customer;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zxing.activity.CaptureActivity;

public class MainActivity extends FragmentActivity {
	/**
	 * 定义基本成员变量
	 */
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragments;
	private ImageView mTabLine;
	private ImageView mSearchImageView;

	private TextView mPackageTextView;
	private TextView mNodeTextView;
	private TextView mCenterTextView;

	private int mCurrentIndex;
	private int screenWidth1_3;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initTabLine();
		initView();
	}

	/**
	 * 初始化view
	 */


	private void initView() {
		
		

		
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		mPackageTextView = (TextView) findViewById(R.id.id_tv_package);
		mNodeTextView = (TextView) findViewById(R.id.id_tv_node);
		mCenterTextView = (TextView) findViewById(R.id.id_tv_mycenter);
		mSearchImageView = (ImageView) findViewById(R.id.id_iv_search);
		mFragments = new ArrayList<Fragment>();

		TransPackageTabFragment tab_package = new TransPackageTabFragment();
		TransNodeTabFragment tab_node = new TransNodeTabFragment();
		MyCenterTabFragment tab_center = new MyCenterTabFragment();

		// 将每个fragment都添加到mFragments里面
		mFragments.add(tab_package);
		mFragments.add(tab_node);
		mFragments.add(tab_center);

		// 为了适配viewpager初始化mAdapter，重写其中的方法，
		// 参数设为getSupportFragmentManager()
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mFragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return mFragments.get(arg0);
			}
		};

		// 给viewPager添加适配器，这样就能正常显示每个fragment了
		mViewPager.setAdapter(mAdapter);

		// 添加每个textview的点击事件
		setClickListener();

		// 为viewpager添加页面改变时的监听器
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			// 当fragment被选中时，分别设置文本标题颜色
			@Override
			public void onPageSelected(int position) {

				// 每当其中一个fragment被选中时，其他文本恢复为黑色
				resetTextColor();

				switch (position) {
				case 0:
					mPackageTextView.setTextColor(Color.parseColor("#87CEEB"));
					break;
				case 1:
					mNodeTextView.setTextColor(Color.parseColor("#87CEEB"));
					break;
				case 2:
					mCenterTextView.setTextColor(Color.parseColor("#87CEEB"));
					break;
				}
				mCurrentIndex = position;
			}

			// 当fragment滑动时，需要设置的代码
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPx) {
				// TODO Auto-generated method stub
				LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine
						.getLayoutParams();
				if (mCurrentIndex == position) {
					lp.leftMargin = (int) (screenWidth1_3 * (mCurrentIndex + positionOffset));
				} else {
					lp.leftMargin = (int) (screenWidth1_3 * (mCurrentIndex - 1 + positionOffset));
				}
				mTabLine.setLayoutParams(lp);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	
	private void setClickListener() {
		mNodeTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mViewPager.setCurrentItem(1);
			}
		});
		mPackageTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mViewPager.setCurrentItem(0);
			}
		});
		mCenterTextView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mViewPager.setCurrentItem(2);
			}
		});
		mSearchImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent openCameraIntent = new Intent(MainActivity.this,
						CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});
	}

	/**
	 * 初始化title2下面的tabline
	 */
	private void initTabLine() {
		mTabLine = (ImageView) findViewById(R.id.id_iv_tabline);
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		screenWidth1_3 = outMetrics.widthPixels / 3;
		LayoutParams lp = mTabLine.getLayoutParams();
		lp.width = screenWidth1_3;
		mTabLine.setLayoutParams(lp);
	}

	// 重置title2的文本颜色为黑色
	protected void resetTextColor() {
		mPackageTextView.setTextColor(Color.BLACK);
		mNodeTextView.setTextColor(Color.BLACK);
		mCenterTextView.setTextColor(Color.BLACK);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
