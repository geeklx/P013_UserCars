package com.liangxiao.usercars.fragmentAdapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {
	private List<Fragment> mFragments;

	// private FragmentManager mFragmentManager;

	// public FragmentAdapter(FragmentManager mFragmentManager,
	// List<Fragment> mFragments) {
	// this.mFragmentManager = mFragmentManager;
	// this.mFragments = mFragments;
	// }

	public FragmentAdapter(FragmentManager fm, List<Fragment> mFragments) {
		super(fm);
		this.mFragments = mFragments;
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

}
