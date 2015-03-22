package com.liangxiao.usercars.guide;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;

import com.liangxiao.usercars.MainActivity_login;
import com.liangxiao.usercars.R;

public class Guide extends Activity implements OnPageChangeListener {
	private MyViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private ImageView[] dots;
	private int[] ids = { R.id.iv1, R.id.iv2, R.id.iv3 };// 导航
	private int[] idss = { R.drawable.guide_one, R.drawable.guide_two,
			R.drawable.guide_three };
	private SharedPreferences preferences;

	@SuppressLint("WorldReadableFiles")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usercars_guide);
		preferences = getSharedPreferences("count", MODE_WORLD_READABLE);
		int count = preferences.getInt("count", 0);
		// 判断程序与第几次运行，如果是第一次运行则跳转到引导页面
		if (count == 0) {
			Editor editor = preferences.edit();
			// 存入数据
			editor.putInt("count", ++count);
			// 提交修改
			editor.commit();
			initViews();
			initDots();
		} else {
			Intent intent = new Intent(this, WelcomeActivity.class);
			this.startActivity(intent);
			this.finish();
		}

	}

	private void initViews() {

		// adapter
		vp = (MyViewPager) findViewById(R.id.viewpager);
		vpAdapter = new ViewPagerAdapter(idss, this, vp);
		vp.setAdapter(vpAdapter);
		vp.setOnPageChangeListener(this);

	}

	private void initDots() {
		dots = new ImageView[idss.length];
		for (int i = 0; i < idss.length; i++) {
			dots[i] = (ImageView) findViewById(ids[i]);
		}
	}

	private Boolean misScrolled = false;

	@Override
	public void onPageScrollStateChanged(int state) {
		switch (state) {
		case ViewPager.SCROLL_STATE_DRAGGING:
			misScrolled = false;
			break;
		case ViewPager.SCROLL_STATE_SETTLING:
			misScrolled = true;
			break;
		case ViewPager.SCROLL_STATE_IDLE:
			if (vp.getCurrentItem() == vp.getAdapter().getCount() - 1
					&& !misScrolled) {
				startActivity(new Intent(this, MainActivity_login.class));
				Guide.this.finish();
			}
			misScrolled = true;
			break;
		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		for (int i = 0; i < ids.length; i++) {
			if (arg0 == i) {
				dots[i].setImageResource(R.drawable.check);
			} else {
				dots[i].setImageResource(R.drawable.encheck);
			}
		}

	}

}
