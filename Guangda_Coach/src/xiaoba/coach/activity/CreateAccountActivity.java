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
import xiaoba.coach.net.result.RegisteResult;
import xiaoba.coach.utils.CommonUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daoshun.lib.communication.http.JSONAccessor;

/*
 * create a new account
 */
@EActivity(R.layout.activity_create_account)
public class CreateAccountActivity extends BaseActivity {
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.ca_input_name)
	EditText mName;
	@ViewById(R.id.ca_input_idcard)
	EditText mIdCard;
	@ViewById(R.id.ca_input_pwd)
	EditText mPwd;
	private String mPhoneNum;

	@AfterViews
	public void init() {
		mPhoneNum = getIntent().getStringExtra(IntentsParamNames.PHONENUM);

		initViews();
		addListeners();
	}

	private void initViews() {
		mTitle.setText(getString(R.string.create_account));
		mTitle.setTextColor(getResources().getColor(R.color.text_black));
		mTitleRightTv.setText(getString(R.string.registe));
		mTitleRightTv.setTextColor(getResources().getColor(R.color.text_grey));
		mTitleRightTv.setClickable(false);
		mTitleBack.setImageResource(R.drawable.back_login_black);
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
				checkRegisteState();
			}
		});

		mIdCard.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				checkRegisteState();
			}
		});

		mPwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				checkRegisteState();
			}
		});
	}

	/*
	 * check that whether the btn registe is enable.
	 */
	private void checkRegisteState() {
		if (mName.getText().length() > 0 && mIdCard.getText().length() > 0 && mPwd.getText().length() > 0) {
			mTitleRightTv.setClickable(true);
			mTitleRightTv.setTextColor(getResources().getColor(R.color.text_black));
		} else {
			mTitleRightTv.setClickable(false);
			mTitleRightTv.setTextColor(getResources().getColor(R.color.text_grey));
		}

	}

	@Click(R.id.title_back)
	void returnLastPage() {
		CreateAccountActivity.this.finish();
	}

	@Click(R.id.title_right_text)
	void gotoNextPage() {
		RegisteTask task = new RegisteTask();
		task.execute();
	}

	/*
	 * do registe request
	 */
	@SuppressLint("DefaultLocale")
	private class RegisteTask extends AsyncTask<Void, Void, RegisteResult> {
		JSONAccessor accessor = new JSONAccessor(CreateAccountActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected RegisteResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			HashMap<String, Object> param = new BaseParam();
			param.put("phone", mPhoneNum);
			param.put("action", "Register");
			param.put("realname", mName.getText().toString());
			param.put("idnum", mIdCard.getText().toString());
			param.put("password", CommonUtils.MD5(mPwd.getText().toString()).toLowerCase());
			return accessor.execute(Settings.USER_URL, param, RegisteResult.class);
		}

		@Override
		protected void onPostExecute(RegisteResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					// registe ok
					startActivity(new Intent(CreateAccountActivity.this, RelatedMaterialActivity_.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
				} else {
					if (result.getMessage() != null && result.getCode() == 2)
						CommonUtils.showToast(CreateAccountActivity.this.getApplicationContext(), result.getMessage());
				}

			} else {
				CommonUtils.showToast(CreateAccountActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}

	}
}
