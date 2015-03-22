package com.liangxiao.usercars.application;

import java.util.Hashtable;

import android.app.Application;

import com.thinkland.sdk.util.CommonFun;

public class UserCarsApplication extends Application {
	private static UserCarsApplication webserinfo = null;

	@Override
	public void onCreate() {
		super.onCreate();
		CommonFun.initialize(getApplicationContext(), false);
	}

	private Hashtable<String, Object> memCacheRegion = new Hashtable<String, Object>();

	public static UserCarsApplication getWebserinfo() {
		if (webserinfo == null)
			webserinfo = new UserCarsApplication();
		return webserinfo;
	}

	/**
	 * 将对象保存到内存缓存中
	 * 
	 * @param key
	 * @param value
	 */
	public void setMemCache(String key, Object value) {
		memCacheRegion.put(key, value);
	}

	/**
	 * 从内存缓存中获取对象
	 * 
	 * @param key
	 * @return
	 */
	public Object getMemCache(String key) {
		return memCacheRegion.get(key);
	}
}
