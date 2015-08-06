package xiaoba.coach.activity;

import java.util.HashMap;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.UserInfo;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.utils.CommonUtils;

import com.daoshun.lib.communication.http.JSONAccessor;

import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

@EActivity(R.layout.activity_account_info)
public class AccountInfoActivity extends BaseActivity {
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.enter_new_pass)
	EditText mName;
	@ViewById(R.id.reenter_new_pass)
	EditText mPhone;
	@ViewById(R.id.pencil_up)
	ImageView mPencilUp;
	@ViewById(R.id.pencil_down)
	ImageView mPencilDown;

	@AfterViews
	void init() {
		mTitle.setText("账号信息");
		mTitleBack.setImageResource(R.drawable.back_arrow);
		mTitle.setTextColor(Color.parseColor("#2c2c2c"));
		mTitleRightTv.setText("确定");
		mTitleRightTv.setTextColor(Color.parseColor("#d2d2d2"));
		mTitleRightTv.setClickable(false);
		mTitleRightTv.setVisibility(View.VISIBLE);

		addListeners();
	}

	private void addListeners() {
		mName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (mName.getText().length() > 0 && mPhone.getText().length() > 0) {
					mTitleRightTv.setTextColor(Color.parseColor("#2c2c2c"));
					mTitleRightTv.setClickable(true);
				} else {
					mTitleRightTv.setTextColor(Color.parseColor("#d2d2d2"));
					mTitleRightTv.setClickable(false);
				}
			}
		});

		mPhone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (mName.getText().length() > 0 && mPhone.getText().length() > 0) {
					mTitleRightTv.setTextColor(Color.parseColor("#2c2c2c"));
					mTitleRightTv.setClickable(true);
				} else {
					mTitleRightTv.setTextColor(Color.parseColor("#d2d2d2"));
					mTitleRightTv.setClickable(false);
				}
			}
		});

		mPhone.setKeyListener(new NumberKeyListener() {

			@Override
			public int getInputType() {
				return InputType.TYPE_CLASS_PHONE;
			}

			@Override
			protected char[] getAcceptedChars() {
				char[] numberChars = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
				return numberChars;
			}
		});
	}

	@Click(R.id.title_back)
	void returnLastPage() {
		finish();
	}

	@Click(R.id.title_right_text)
	void submit() {
		new PerfectAccountInfoTask().execute();
	}

	@Click(R.id.pencil_up)
	void enterName() {
		mPencilUp.setImageResource(R.drawable.pencil_color);
		mName.setFocusable(true);
		mName.setFocusableInTouchMode(true);
		mName.requestFocus();
	}

	@Click(R.id.pencil_down)
	void enterPhoneNumber() {
		mPencilDown.setImageResource(R.drawable.pencil_color);
		mPhone.setFocusable(true);
		mPhone.setFocusableInTouchMode(true);
		mPhone.requestFocus();
	}

	/*
	 * 完善账号信息
	 */
	private class PerfectAccountInfoTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(AccountInfoActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					/*
					 * 保存本地信息
					 */
					UserInfo info = CoachApplication.getInstance().getUserInfo();
					info.setRealname(mName.getText().toString());
					info.setPhone(mPhone.getText().toString());
					info.saveUserInfo(info, AccountInfoActivity.this);
					//
					CommonUtils.showToast(AccountInfoActivity.this.getApplicationContext(), "完善账号信息成功");
					AccountInfoActivity.this.finish();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(AccountInfoActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(AccountInfoActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(AccountInfoActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			HashMap<String, Object> param = new BaseParam();
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("readname", mName.getText().toString());
			param.put("phone", mPhone.getText().toString());
			return accessor.execute(Settings.USER_URL, param, BaseResult.class);
		}

	}
}
