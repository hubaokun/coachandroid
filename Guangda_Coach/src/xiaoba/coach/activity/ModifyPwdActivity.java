package xiaoba.coach.activity;

import java.util.HashMap;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.R;
import xiaoba.coach.common.IntentsParamNames;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.utils.CommonUtils;

import com.daoshun.lib.communication.http.JSONAccessor;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

@EActivity(R.layout.activity_modify_pwd)
public class ModifyPwdActivity extends BaseActivity {
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.enter_new_pwd)
	EditText mNewPwd;
	private String mPhoneNum;

	@AfterViews
	public void init() {
		mPhoneNum = getIntent().getStringExtra(IntentsParamNames.PHONENUM);

		initViews();
		addListeners();
	}

	private void initViews() {
		mTitle.setText(getString(R.string.modify_pwd));
		mTitle.setTextColor(getResources().getColor(R.color.text_black));
		mTitleRightTv.setText(getString(R.string.confirm));
		mTitleRightTv.setTextColor(getResources().getColor(R.color.text_grey));
		mTitleRightTv.setVisibility(View.VISIBLE);
		mTitleRightTv.setClickable(false);
		mTitleBack.setImageResource(R.drawable.back_login_black);
	}

	private void addListeners() {
		mNewPwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (mNewPwd.getText().length() > 0) {
					mTitleRightTv.setClickable(true);
					mTitleRightTv.setTextColor(getResources().getColor(R.color.text_black));
				}
			}
		});
	}

	@Click(R.id.title_back)
	void returnLastPage() {
		ModifyPwdActivity.this.finish();
	}

	@Click(R.id.title_right_text)
	void submit() {
		if (mNewPwd.getText().length() > 0) {
			FindPswTask task = new FindPswTask();
			task.execute();
		} else {
			CommonUtils.showToast(ModifyPwdActivity.this.getApplicationContext(), getString(R.string.enter_new_pwd));
		}
	}

	private class FindPswTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(ModifyPwdActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "FindPsw");
			param.put("phone", mPhoneNum);
			param.put("newpassword", CommonUtils.MD5(mNewPwd.getText().toString().trim()).toLowerCase());
			return accessor.execute(Settings.USER_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					// registe ok
					startActivity(new Intent(ModifyPwdActivity.this, HomeActivity_.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
				} else {
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ModifyPwdActivity.this);
						if (result.getMessage() != null)
							CommonUtils.showToast(ModifyPwdActivity.this.getApplicationContext(), result.getMessage());
					}
				}

			} else {
				CommonUtils.showToast(ModifyPwdActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}

	}
}
