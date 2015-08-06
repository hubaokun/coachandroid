package xiaoba.coach.activity;

import java.util.HashMap;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.view.OnSingleClickListener;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.UserInfo;
import xiaoba.coach.net.result.AliAccountResult;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.CashTypeResult;
import xiaoba.coach.utils.CommonUtils;

public class AccountArrangeActivity extends BaseActivity {
	TextView mTitle;
	ImageView mTitleBack;
	TextView mTitleRightTv;
	FrameLayout mBack;
	EditText mAccount;
//	TextView mTip;
//	Button mClear; // 清除账号
//	ImageView mRadio;
	int type; // 支付类型 0:自提 1:驾校提

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_arrange);

		findViews();
		addListeners();
		initData();

	}

	private void initData() {
		String account = CoachApplication.getInstance().getUserInfo().getAlipay_account();
		if (account != null && account.length() > 0) {
			mAccount.setText(account);
			mAccount.setSelection(account.length());
		}

		type = CoachApplication.getInstance().getUserInfo().getCashtype();
//		if (type == 1) {
//			mRadio.setImageResource(R.drawable.button_on);
//			mTip.setText("您现在提现的金额将会转到您所在的驾校");
//		}
	}

	private void addListeners() {
		mBack.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				finish();
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
				String accountstr = "";
				if (CoachApplication.getInstance().getUserInfo().getAlipay_account() != null)
					accountstr = CoachApplication.getInstance().getUserInfo().getAlipay_account();

				if (mAccount.getText().length() > 0 && !mAccount.getText().toString().equals(accountstr)) {
					mTitleRightTv.setTextColor(Color.parseColor("#2c2c2c"));
					mTitleRightTv.setClickable(true);
				} else {
					mTitleRightTv.setTextColor(Color.parseColor("#d2d2d2"));
					mTitleRightTv.setClickable(false);
				}
			}
		});

		mTitleRightTv.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				new ChangeAliAccountTask().execute();
			}
		});

//		mClear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!TextUtils.isEmpty(CoachApplication.getInstance().getUserInfo().getAlipay_account()))
//					new DelAliTask().execute();
//			}
//		});
//		
//		mRadio.setOnClickListener(new OnSingleClickListener() {
//			
//			@Override
//			public void doOnClick(View v) {
//				new ChangeApplyTypeTask().execute();
//			}
//		});
	}

	private void findViews() {
		mTitle = (TextView) findViewById(R.id.title);
		mTitleBack = (ImageView) findViewById(R.id.title_back_img);
		mTitleRightTv = (TextView) findViewById(R.id.title_right_text);
		mBack = (FrameLayout) findViewById(R.id.title_back);
		mAccount = (EditText) findViewById(R.id.account);

		mTitle.setText("账户管理");
		mTitleBack.setImageResource(R.drawable.back_arrow);
		mTitle.setTextColor(Color.parseColor("#2c2c2c"));
		mTitleRightTv.setText("提交");
		mTitleRightTv.setTextColor(Color.parseColor("#d2d2d2"));
		mTitleRightTv.setClickable(false);
		mTitleRightTv.setVisibility(View.VISIBLE);

//		mTip = (TextView) findViewById(R.id.account_tip);
//		mRadio = (ImageView) findViewById(R.id.receive_notice);
//		mClear = (Button) findViewById(R.id.clear_account);
	}

	/*
	 * 修改支付宝账号
	 */
	private class ChangeAliAccountTask extends AsyncTask<Void, Void, AliAccountResult> {
		JSONAccessor accessor = new JSONAccessor(AccountArrangeActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected AliAccountResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			HashMap<String, Object> param = new BaseParam();
			param.put("userid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("type", "1"); // 1:coach
			param.put("aliaccount", mAccount.getText().toString());
			param.put("action", "ChangeAliAccount");
			return accessor.execute(Settings.CMY_URL, param, AliAccountResult.class);
		}

		@Override
		protected void onPostExecute(AliAccountResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();

			if (result != null) {
				if (result.getCode() == 1) {
					/*
					 * save aliaccount to local memory
					 */
					Editor editor = AccountArrangeActivity.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit();
					editor.putString("alipay_account", result.getAliacount());
					editor.commit();
					CoachApplication.mUserInfo = new UserInfo().loadUserInfo(AccountArrangeActivity.this);
					CommonUtils.showToast(AccountArrangeActivity.this.getApplicationContext(), "提交成功");
					AccountArrangeActivity.this.finish();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(AccountArrangeActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(AccountArrangeActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(AccountArrangeActivity.this.getApplicationContext(), AccountArrangeActivity.this.getString(R.string.net_error));
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

	}

	/*
	 * 清除之前的账号
	 */
	private class DelAliTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(AccountArrangeActivity.this, JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("userid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("action", "DelAliAccount");
			param.put("type", "1");
			return accessor.execute(Settings.CMY_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
				mLoadingDialog.dismiss();
			}
			if (result != null) {
				if (result.getCode() == 1) {
					mAccount.setText("");
					UserInfo info = CoachApplication.getInstance().getUserInfo();
					info.setAlipay_account("");
					Editor editor = AccountArrangeActivity.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit();
					editor.putString("alipay_account", "");
					editor.commit();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(AccountArrangeActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(AccountArrangeActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(AccountArrangeActivity.this.getApplicationContext(), AccountArrangeActivity.this.getString(R.string.net_error));
			}

		}

	}

	/*
	 * 
	 */
	private class ChangeApplyTypeTask extends AsyncTask<Void, Void, CashTypeResult> {
		JSONAccessor accessor = new JSONAccessor(AccountArrangeActivity.this, JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected CashTypeResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("action", "ChangeApplyType");
			if (type == 0)
				param.put("setvalue", 1);
			else
				param.put("setvalue", 0);
			return accessor.execute(Settings.CMY_URL, param, CashTypeResult.class);
		}

		@Override
		protected void onPostExecute(CashTypeResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
				mLoadingDialog.dismiss();
			}
			if (result != null) {
				if (result.getCode() == 1) {
//					if (result.getCashtype() == 1) {
//						type = 1;
//						mRadio.setImageResource(R.drawable.button_on);
//						mTip.setText("您现在提现的金额将会转到您所在的驾校");
//					} else {
//						type = 0;
//						mRadio.setImageResource(R.drawable.button_off);
//						mTip.setText("您现在提现的金额将会直接转到您的支付宝账户");
//					}
					UserInfo info = CoachApplication.getInstance().getUserInfo();
					info.setCashtype(result.getCashtype());
					Editor editor = AccountArrangeActivity.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit();
					editor.putInt("cashtype", result.getCashtype());
					editor.commit();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(AccountArrangeActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(AccountArrangeActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(AccountArrangeActivity.this.getApplicationContext(), AccountArrangeActivity.this.getString(R.string.net_error));
			}

		}

	}

}
