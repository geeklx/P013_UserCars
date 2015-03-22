package com.liangxiao.usercars.tabviewpager;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liangxiao.usercars.R;
import com.liangxiao.usercars.application.UserCarsApplication;
import com.liangxiao.usercars.fragment.AddressFragment;
import com.liangxiao.usercars.fragment.FrdFragment;
import com.liangxiao.usercars.fragment.FrdFragment3;
import com.liangxiao.usercars.fragment.SettingFragment;
import com.liangxiao.usercars.fragment.WeixinFragment;
import com.liangxiao.usercars.fragmentAdapter.FragmentAdapter;

@SuppressLint("ShowToast")
public class MainActivity_FragmentViewpager extends FragmentActivity implements
		OnClickListener {
	private ViewPager mViewPager;
	private FragmentAdapter mAdapter;
	private List<Fragment> mFragments;

	private ImageButton iv_weixin;
	private ImageButton iv_frd;
	private ImageButton iv_frd3;
	private ImageButton iv_address;
	private ImageButton iv_settings;
	private LinearLayout ll_weixin;
	private LinearLayout ll_frd;
	private LinearLayout ll_frd3;
	private LinearLayout ll_address;
	private LinearLayout ll_settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usercar_fv_main);
		String phoneNum = UserCarsApplication.getWebserinfo()
				.getMemCache("phoneNum").toString();
		String pwd = UserCarsApplication.getWebserinfo().getMemCache("pwd")
				.toString();
		Toast.makeText(MainActivity_FragmentViewpager.this,
				phoneNum + "," + pwd, 5).show();
		initView();
		initEvent();
		setSelect(0);
	}

	private void initEvent() {
		ll_weixin.setOnClickListener(this);
		ll_frd.setOnClickListener(this);
		ll_frd3.setOnClickListener(this);
		ll_address.setOnClickListener(this);
		ll_settings.setOnClickListener(this);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				int mCurrentItem = mViewPager.getCurrentItem();
				setTab(mCurrentItem);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		iv_weixin = (ImageButton) findViewById(R.id.id_tab_weixin_img);
		iv_frd = (ImageButton) findViewById(R.id.id_tab_frd_img);
		iv_frd3 = (ImageButton) findViewById(R.id.id_tab_frd_img3);
		iv_address = (ImageButton) findViewById(R.id.id_tab_address_img);
		iv_settings = (ImageButton) findViewById(R.id.id_tab_settings_img);
		ll_weixin = (LinearLayout) findViewById(R.id.id_tab_weixin);
		ll_frd = (LinearLayout) findViewById(R.id.id_tab_frd);
		ll_frd3 = (LinearLayout) findViewById(R.id.id_tab_frd3);
		ll_address = (LinearLayout) findViewById(R.id.id_tab_address);
		ll_settings = (LinearLayout) findViewById(R.id.id_tab_settings);

		mFragments = new ArrayList<Fragment>();
		Fragment mTab01 = new WeixinFragment();
		Fragment mTab02 = new FrdFragment();
		Fragment mTab023 = new FrdFragment3();
		Fragment mTab03 = new AddressFragment();
		Fragment mTab04 = new SettingFragment();
		mFragments.add(mTab01);
		mFragments.add(mTab02);
		mFragments.add(mTab023);
		mFragments.add(mTab03);
		mFragments.add(mTab04);

		mAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments);

		mViewPager.setAdapter(mAdapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_tab_weixin:
			setSelect(0);
			break;
		case R.id.id_tab_frd:
			setSelect(1);
			break;
		case R.id.id_tab_frd3:
			setSelect(2);
			break;
		case R.id.id_tab_address:
			setSelect(3);
			break;
		case R.id.id_tab_settings:
			setSelect(4);
			break;
		default:
			break;
		}
	}

	private void setSelect(int i) {
		setTab(i);
		mViewPager.setCurrentItem(i);
	}

	private void setTab(int i) {
		resetImg();
		switch (i) {
		case 0:
			iv_weixin.setImageResource(R.drawable.tab_weixin_pressed);
			break;
		case 1:
			iv_frd.setImageResource(R.drawable.tab_find_frd_pressed);
			break;
		case 2:
			iv_frd3.setImageResource(R.drawable.tab_find_frd_pressed);
			break;
		case 3:
			iv_address.setImageResource(R.drawable.tab_address_pressed);
			break;
		case 4:
			iv_settings.setImageResource(R.drawable.tab_settings_pressed);
			break;
		default:
			break;
		}
	}

	/**
	 * ÇÐ»»Í¼Æ¬Îª°µÉ«
	 */
	private void resetImg() {
		iv_weixin.setImageResource(R.drawable.tab_weixin_normal);
		iv_frd.setImageResource(R.drawable.tab_find_frd_normal);
		iv_frd3.setImageResource(R.drawable.tab_find_frd_normal);
		iv_address.setImageResource(R.drawable.tab_address_normal);
		iv_settings.setImageResource(R.drawable.tab_settings_normal);
	}
}
