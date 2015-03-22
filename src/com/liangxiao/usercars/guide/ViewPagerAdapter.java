package com.liangxiao.usercars.guide;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ViewPagerAdapter extends PagerAdapter {
	private int[] idss;
	private MyViewPager mViewPager;
	private Context mContext;

	public ViewPagerAdapter(int[] idss, Context mContext, MyViewPager mViewPager) {
		this.idss = idss;
		this.mViewPager = mViewPager;
		this.mContext = mContext;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);

	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(mContext);
		imageView.setImageResource(idss[position]);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		container.addView(imageView);
		mViewPager.setObjectForPosition(imageView, position);
		return imageView;
	}

	@Override
	public int getCount() {
		return idss.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

}
