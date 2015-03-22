package com.liangxiao.usercars.toast;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class BaseActivity extends Activity {
	private ProgressDialog progressDialog;
	private Handler handler;

	public void sendMessage(JSONObject jsonObject, int i) {
		Message msg = new Message();
		msg.obj = jsonObject;
		msg.what = i;
		handler.sendMessage(msg);
	}

	public void showDialog(String s) {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage(s);
		progressDialog.setCancelable(false);
		progressDialog.show();
	}

	public void closeDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public void showToast(String s) {
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
	}
}
