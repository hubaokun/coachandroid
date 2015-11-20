package xiaoba.coach.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.net.result.RegisteResult;
import xiaoba.coach.net.result.VercodeResult;
import xiaoba.coach.utils.CommonUtils;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.igexin.sdk.PushManager;

@EActivity(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
	/*
	 * submit account and pwd by clicking it
	 */
	@ViewById(R.id.login_submit)
	Button mSubmit;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.login_input_account)
	EditText mAccount;
	@ViewById(R.id.login_input_pwd)
	EditText mPwd;
	// @ViewById(R.id.login_show_pwd)
	// ImageView mShowPwd;
	@ViewById(R.id.title_divider)
	View mDivider;
	@ViewById(R.id.title)
	TextView mTitle;
	// @ViewById(R.id.title_right_text)
	// TextView mRegiste;
	@ViewById(R.id.login_forget_pwd)
	TextView mForgetPwd;
	@ViewById(R.id.login_get_code)
	Button mGetCode;

	private boolean IsPwdShow;

	// Drawable drawable;
	// SpannableString spannable;
	// ImageSpan is;
	// int pwdPointLength;

	@AfterViews
	void init() {
		initView();
		// drawable = LoginActivity.this.getResources().getDrawable(R.drawable.pwd_point);
		// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

		addListeners();

	}

	private void addListeners() {
		/*
		 * show pwd style
		 */
		mPwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				// if (s.length() > pwdPointLength) {
				// spannable = new SpannableString(s.toString());
				// is = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
				// spannable.setSpan(is, 0, s.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
				// pwdPointLength = s.length();
				// mPwd.append(spannable);
				// }
				// mPwd.setSelection(s.length());
				if (s.toString().contains(" ")) {
					s = s.toString().trim();
					mPwd.setText(s);
					mPwd.setSelection(s.length());
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				/*
				 * check the visibility of imgBtn which is right of pwdEdit
				 */
				// if (mPwd.getText().length() > 0) {
				// mShowPwd.setVisibility(View.VISIBLE);
				// } else {
				// mShowPwd.setVisibility(View.GONE);
				// }
				/*
				 * check submit status
				 */
				if (mAccount.getText().length() > 0 && mPwd.getText().length() > 0) {
					mSubmit.setEnabled(true);
					mSubmit.setTextColor(Color.parseColor("#ffffff"));
				} else {
					mSubmit.setEnabled(false);
					mSubmit.setTextColor(getResources().getColor(R.color.text_black));
				}
			}
		});

		mAccount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (mAccount.getText().length() > 0 && mPwd.getText().length() > 0) {
					mSubmit.setEnabled(true);
					mSubmit.setTextColor(Color.parseColor("#ffffff"));
				} else {
					mSubmit.setEnabled(false);
					mSubmit.setTextColor(getResources().getColor(R.color.text_black));
				}
			}
		});

		mAccount.setKeyListener(new NumberKeyListener() {

			@Override
			public int getInputType() {
				return android.text.InputType.TYPE_CLASS_PHONE;
			}

			@Override
			protected char[] getAcceptedChars() {
				char[] numberChars = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

				return numberChars;
			} 
		});

	}

	private void initView() {
		mDivider.setVisibility(View.INVISIBLE);
		mTitle.setText(getString(R.string.login));
		mBack.setVisibility(View.INVISIBLE);
		// mRegiste.setText(getString(R.string.registe));
		// mRegiste.setTextColor(Color.parseColor("#ffffff"));
		// mRegiste.setVisibility(View.VISIBLE);
		mSubmit.setTextColor(getResources().getColor(R.color.text_black));

	}

	/*
	 * judge the phone is valid or not **return--true:valid--fale:invalid
	 */
	private boolean checkPhoneValid() {
		if (mAccount.getText().length() == 11)  // 11位
			return true;
		return false;
	}

	@Click(R.id.login_submit)
	void enterApp(View view) {
		if (!checkPhoneValid()) {
			Toast.makeText(LoginActivity.this, "请输入11位手机号码", Toast.LENGTH_SHORT).show();
			return;
		}
		String account = mAccount.getText().toString();
		String passWord = mPwd.getText().toString();
		LoginTask task = new LoginTask(account,passWord);
		task.execute();
		// startActivity(new Intent(LoginActivity.this, HomeActivity.class));
		// LoginActivity.this.finish();
	}

	@Click(R.id.title_back)
	void exitApp(View view) {
		LoginActivity.this.finish();
	}

	// @Click(R.id.login_show_pwd)
	// void chPwdMode() {
	// if (IsPwdShow) {
	// mPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
	// mPwd.setSelection(mPwd.getText().length());
	// IsPwdShow = !IsPwdShow;
	// } else {
	// mPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
	// mPwd.setSelection(mPwd.getText().length());
	// IsPwdShow = !IsPwdShow;
	// }
	// }

	// @Click(R.id.title_right_text)
	// void gotoRegistePage() {
	// startActivity(new Intent(LoginActivity.this, RegisteActivity.class));
	// }

	@Click(R.id.login_forget_pwd)
	void gotoGetPwd() {
		Intent intent = new Intent(LoginActivity.this, RegisteActivity.class);
		intent.putExtra("isGetPwd", "1");
		startActivity(intent);
	}

	/*
	 * do login request
	 */
	private class LoginTask extends AsyncTask<Void, Void, RegisteResult> {
		
		private String phone;
		private String passWord;
		
		public LoginTask(String phone,String password)
		{
			this.phone = phone;
			this.passWord = password;
		}
		
		JSONAccessor accessor = new JSONAccessor(LoginActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected RegisteResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("action", "Login");
			param.put("loginid", phone.trim());
			param.put("password",passWord.trim());
			param.put("version", mApplication.getVersion());
			param.put("ostype", "2");
			return accessor.execute(Settings.USER_URL, param, RegisteResult.class);
		}

		@SuppressWarnings("static-access")
		@Override
		protected void onPostExecute(RegisteResult result) { 	
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {	
				if (result.getCode() == 1) { 
					// registe ok
					CoachApplication.getInstance().uploadClientId();
					mApplication.isInvited = result.getIsInvited();
					mApplication.crewardamount = result.getCrewardamount();
					mApplication.orewardamount = result.getOrewardamount();
					mApplication.getUserInfo().setCoachInfo(result.getUserInfo());
					//mApplication.mUserInfo = result.getUserInfo();
					mApplication.getUserInfo().saveUserInfo(result.getUserInfo(), LoginActivity.this);
					//System.out.println(".....................success");
//					CommonUtils.showToast(LoginActivity.this, result.getUserInfo().getInvitecode());
//					CommonUtils.showToast(LoginActivity.this,CoachApplication.getInstance().getUserInfo().getInvitecode().toString());
//					CommonUtils.showToast(LoginActivity.this,CoachApplication.getInstance().getUserInfo().getCoachid()+"");
					startActivity(new Intent(LoginActivity.this, HomeActivity_.class));
					LoginActivity.this.finish();
					CommonUtils.showToast(LoginActivity.this.getApplicationContext(), "登录成功");
				} else {
					// account or password error
					if (result.getMessage() != null)
						CommonUtils.showToast(LoginActivity.this.getApplicationContext(), result.getMessage());
				}
			} else {
				CommonUtils.showToast(LoginActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}
	}

	boolean stopThread;

	@Click(R.id.login_get_code)
	void getCheckCode() {
		if (!checkPhoneValid()) {
			CommonUtils.showToast(LoginActivity.this.getApplicationContext(), "请输入11位手机号码");
			return;
		}
		mGetCode.setClickable(false);
		mGetCode.setBackgroundResource(R.drawable.shape_grey_round);
		GetVerCodeTask task = new GetVerCodeTask();
		task.execute();

		stopThread = false;
		new Thread(new Runnable() {
			int count = 60;

			@Override
			public void run() {
				while (count > 0 && !stopThread) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
					count--;
					runOnUiThread(new Runnable() {
						public void run() {
							mGetCode.setText(count + "\"\n后重获");
						}
					});
				}
				runOnUiThread(new Runnable() {
					public void run() {
						mGetCode.setBackgroundResource(R.drawable.shape_yellow_round);
						mGetCode.setText("获取\n验证码");
						mGetCode.setClickable(true);
					}
				});
			}
		}).start();

	}

	/*
	 * get check code
	 */
	private class GetVerCodeTask extends AsyncTask<Void, Void, VercodeResult> {
		JSONAccessor accessor = new JSONAccessor(LoginActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected VercodeResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("action", "GetVerCode");
			param.put("phone", mAccount.getText().toString().trim());
			param.put("type", "1");
			return accessor.execute(Settings.SUSER_URL, param, VercodeResult.class);
		}

		@Override
		protected void onPostExecute(VercodeResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {

				} else {
					stopThread = true;
					if (result.getMessage() != null)
						CommonUtils.showToast(LoginActivity.this.getApplicationContext(), result.getMessage());
					mGetCode.setBackgroundColor(Color.parseColor("#f7941d"));
					mGetCode.setText("获取\n验证码");
					mGetCode.setClickable(true);
				}

			} else {
				CommonUtils.showToast(LoginActivity.this.getApplicationContext(), getString(R.string.net_error));
				stopThread = true;
				mGetCode.setBackgroundColor(Color.parseColor("#f7941d"));
				mGetCode.setText("获取\n验证码");
				mGetCode.setClickable(true);
			}
		}

	}
}