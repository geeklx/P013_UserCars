package com.liangxiao.usercars.toast;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class myToast extends Handler {
	private Activity activity;

	public myToast(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void handleMessage(Message msg) {

		switch (msg.arg1) {
		case 1:
			showInfo("登录成功！");
			break;
		case 2:
			showInfo("手机号或者验证码不能为空!");
			break;
		case 3:
			showInfo("发送成功!");
			break;
		case 4:
			showInfo("网络未连接!");
			break;
		case 5:
			showInfo("暂无数据!");
			break;
		case 6:
			showInfo("请填写手机号码");
			break;
		case 7:
			showInfo("发送失败!");
			break;
		case 8:
			showInfo("用户名或手机号不能空！");
			break;
		case 9:
			showInfo("密码不能空！");
			break;
		default:
			break;
		}
		super.handleMessage(msg);
	}

	/**
	 * 显示提示信息
	 * 
	 * @param info
	 */
	public void showInfo(String info) {
		Toast.makeText(activity.getApplicationContext(), info,
				Toast.LENGTH_SHORT).show();
	}
}
