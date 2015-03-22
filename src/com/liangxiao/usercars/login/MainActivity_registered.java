package com.liangxiao.usercars.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.liangxiao.usercars.MainActivity_login;
import com.liangxiao.usercars.R;
import com.liangxiao.usercars.toast.BaseActivity;
import com.liangxiao.usercars.toast.myToast;
import com.thinkland.sdk.sms.SMSCaptcha;
import com.thinkland.sdk.util.BaseData.ResultCallBack;

public class MainActivity_registered extends BaseActivity implements
		OnClickListener, TextWatcher {
	private static final String TAG = "接收验证码";
	private static final int UPDATE_TIME = 60;
	private int time = UPDATE_TIME;
	private ImageView iv_back, iv_clear1;
	private EditText et_write_phone, et_put_identify;// 手机号,验证码
	private Button btn_unreceive_identify, btn_submit;// 获取,下一步
	private TextView tv_useragreement, tv_userphone, tv_titleName;// 用户协议,电话,标题
	private Handler handler;
	private Message msg;
	private int i;
	private SMSCaptcha smsCaptcha;
	private String phone, verificationCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usercars_login);
		handler = new myToast(MainActivity_registered.this);
		smsCaptcha = SMSCaptcha.getInstance();
		initView();

	}

	/**
	 * 初始化参数
	 */
	private void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_back);// 返回
		tv_titleName = (TextView) findViewById(R.id.tv_titleName);
		tv_titleName.setText(getResources().getString(R.string.zhuce));
		tv_titleName.setTextColor(getResources().getColor(R.color.black3));
		iv_clear1 = (ImageView) findViewById(R.id.iv_clear1);// 删除
		et_write_phone = (EditText) findViewById(R.id.et_write_phone);// 填写手机号
		et_write_phone.addTextChangedListener(this);
		et_put_identify = (EditText) findViewById(R.id.et_put_identify);// 填写验证码
		et_put_identify.addTextChangedListener(this);
		btn_unreceive_identify = (Button) findViewById(R.id.btn_unreceive_identify);// 获取
		btn_unreceive_identify.setEnabled(true);
		btn_unreceive_identify
				.setBackgroundResource(R.drawable.smssdk_btn_enable);
		btn_submit = (Button) findViewById(R.id.btn_submit);// 下一步
		btn_submit.setEnabled(false);
		btn_submit.setBackgroundResource(R.drawable.smssdk_btn_disenable);
		tv_useragreement = (TextView) findViewById(R.id.tv_useragreement);// 用户协议
		tv_userphone = (TextView) findViewById(R.id.tv_userphone);// 电话
		tv_userphone.setText(splitPhoneNum(tv_userphone.getText().toString()));
		tv_userphone.setTextColor(getResources().getColor(
				R.color.uc_login_shuoming));
		iv_back.setOnClickListener(this);
		iv_clear1.setOnClickListener(this);
		btn_unreceive_identify.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		tv_useragreement.setOnClickListener(this);
		tv_userphone.setOnClickListener(this);

		handlerMessage(i);

		et_write_phone.setText("");
		et_write_phone.requestFocus();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.iv_clear1:
			et_write_phone.getText().clear();
			et_put_identify.getText().clear();
			break;
		case R.id.tv_useragreement:
			Intent intent = new Intent(MainActivity_registered.this,
					MainActivity_login.class);
			startActivity(intent);
			break;
		case R.id.tv_userphone:
			final String telPhone = splitPhoneNumRe(tv_userphone.getText()
					.toString());
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示框")
					.setIcon(R.drawable.ic_launcher)
					.setCancelable(false)
					.setMessage("拨打电话:" + splitPhoneNum(telPhone))
					.setPositiveButton(R.string.smssdk_ok,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intentphone = new Intent();
									// 系统默认的action，用来打开默认的电话界面
									intentphone.setAction(Intent.ACTION_CALL);
									// 需要拨打的号码
									intentphone.setData(Uri.parse("tel:"
											+ splitPhoneNumRe(tv_userphone
													.getText().toString())));
									MainActivity_registered.this
											.startActivity(intentphone);
								}
							})
					.setNegativeButton(R.string.smssdk_cancel,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							});
			AlertDialog dialog = builder.create();
			dialog.show();

			break;
		case R.id.btn_unreceive_identify:
			// 刷新
			if (!et_write_phone.getText().toString().trim().equals("")) {
				if (btn_unreceive_identify.getText().toString().equals("获取")) {
					getVerificationCode();
				} else {
					// 再次获取验证码
					showDialogMessage();
				}
			} else {
				handlerMessage(6);
			}
			break;
		case R.id.btn_submit:
			// 完成部分
			Submit();
			break;
		default:
			break;
		}
	}

	/**
	 * 获取验证码
	 */
	private void getVerificationCode() {
		showDialog(getResources().getString(
				R.string.smssdk_get_verification_code_content));

		smsCaptcha.sendCaptcha(et_write_phone.getText().toString().trim(),
				new ResultCallBack() {

					@Override
					public void onResult(int code, String reason, String result) {
						closeDialog();
						// 读秒部分
						countDown();
						msg = handler.obtainMessage();
						msg.arg1 = 3;
						handler.sendMessage(msg);
						Log.v(TAG, "code:" + code + ";reason:" + reason
								+ ";result:" + result);
					}
				});
	}

	/**
	 * 再次获取验证码
	 */
	private void showDialogMessage() {
		// 重新获取验证码
		String strContent = getResources().getString(
				R.string.smssdk_resend_identify_code);
		AlertDialog.Builder builder = new AlertDialog.Builder(
				MainActivity_registered.this);
		builder.setTitle("提示框")
				.setIcon(R.drawable.ic_launcher)
				.setCancelable(false)
				.setMessage(strContent)
				.setPositiveButton(R.string.smssdk_ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								showDialog(getResources()
										.getString(
												R.string.smssdk_get_verification_code_content));

								smsCaptcha.sendCaptcha(et_write_phone.getText()
										.toString().trim(),
										new ResultCallBack() {

											@Override
											public void onResult(int code,
													String reason, String result) {
												closeDialog();
												// 读秒部分显示为"60秒"-"重置"的过程
												countDown();
												msg = handler.obtainMessage();
												msg.arg1 = 3;
												handler.sendMessage(msg);
												Log.v(TAG, "code:" + code
														+ ";reason:" + reason
														+ ";result:" + result);
											}
										});
							}
						})
				.setNegativeButton(R.string.smssdk_cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								// finish();
							}
						});

		AlertDialog dialog = builder.create();
		dialog.show();

	}

	/**
	 * 读秒部分显示为"60秒"-"重置"的过程
	 */
	private void countDown() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (time-- > 0) {
					// 接收短信大约需要60seconds
					String unReceive = getResources().getString(
							R.string.smssdk_receive_msg1, time);// 显示为60秒
					// 刷新
					updaTvunReceive1(unReceive);
					Log.v("time is about ", unReceive);
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				String unReceive = getResources().getString(
						R.string.smssdk_unreceive_identify_code1, time);// 显示为重置
				updaTvunReceive2(unReceive);
				time = UPDATE_TIME;
			}
		}).start();
	}

	/**
	 * 刷新操作1
	 * 
	 * @param unReceive
	 */

	private void updaTvunReceive1(final String unReceive) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				btn_unreceive_identify.setText(Html.fromHtml(unReceive));
				btn_unreceive_identify.setEnabled(false);
				btn_unreceive_identify
						.setBackgroundResource(R.drawable.smssdk_btn_disenable);
				et_write_phone.setEnabled(false);
				iv_clear1.setVisibility(View.GONE);
			}
		});
	}

	/**
	 * 刷新操作2
	 * 
	 * @param unReceive
	 */

	private void updaTvunReceive2(final String unReceive) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				btn_unreceive_identify.setText(Html.fromHtml(unReceive));
				btn_unreceive_identify.setEnabled(true);
				btn_unreceive_identify
						.setBackgroundResource(R.drawable.smssdk_btn_enable);
				et_write_phone.setEnabled(true);
				iv_clear1.setVisibility(View.VISIBLE);
			}
		});
	}

	private void Submit() {
		if (!et_write_phone.getText().toString().trim().equals("")
				&& !et_put_identify.getText().toString().trim().equals("")) {
			phone = et_write_phone.getText().toString().trim();
			verificationCode = et_put_identify.getText().toString().trim();
			showDialog(getResources().getString(R.string.smssdk_loading));
			smsCaptcha.commitCaptcha(phone, verificationCode,
					new ResultCallBack() {

						@Override
						public void onResult(int code, String reason,
								String result) {
							closeDialog();
							Log.v(TAG, "code:" + code + ";resason:" + reason
									+ ";result:" + result);
							if (code == 0) {
								handlerMessage(3);
							} else {
								handlerMessage(7);
							}
						}
					});
		} else {
			handlerMessage(2);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int after, int count) {
		if (et_write_phone.getText().toString().trim().equals("")) {
			btn_unreceive_identify.setText("获取");
		}

		if (s.length() > 0) {
			btn_submit.setEnabled(true);
			iv_clear1.setVisibility(View.VISIBLE);
			btn_submit.setBackgroundResource(R.drawable.smssdk_btn_enable);
		} else {
			btn_submit.setEnabled(false);
			iv_clear1.setVisibility(View.GONE);
			btn_submit.setBackgroundResource(R.drawable.smssdk_btn_disenable);
		}
	}

	/**
	 * 异步消息提示
	 * 
	 * @param i
	 */
	private void handlerMessage(int i) {
		msg = handler.obtainMessage();
		msg.arg1 = i;
		handler.sendMessage(msg);
	}

	/**
	 * 手机号显示格式400-808-0688
	 * 
	 * @param phone
	 * @return
	 */
	private String splitPhoneNum(String phone) {
		StringBuilder builder = new StringBuilder(phone);
		builder.reverse();
		for (int i = 4, len = builder.length(); i < len; i += 4) {
			builder.insert(i, '-');
		}

		builder.reverse();
		return builder.toString();

	}

	/**
	 * 手机号显示格式4008080688
	 * 
	 * @param phone
	 * @return
	 */
	private String splitPhoneNumRe(String phone) {
		phone = phone.replace("-", "");
		return phone.toString();

	}
}
