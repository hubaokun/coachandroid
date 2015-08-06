package xiaoba.coach.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.R;
import xiaoba.coach.common.IntentsParamNames;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.VercodeResult;
import xiaoba.coach.utils.CommonUtils;

import com.daoshun.lib.communication.http.JSONAccessor;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


/*
 * registe user page
 */
@EActivity(R.layout.activity_registe)
public class RegisteActivity extends BaseActivity {
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.registe_input_phone)
	EditText mPhone;
	@ViewById(R.id.registe_input_code)
	EditText mCode;
	@ViewById(R.id.get_code)
	Button mGetCode;

	boolean isGetPwdPage;
	boolean stopThread;

	@AfterViews
	public void init() {
		if (getIntent().getStringExtra("isGetPwd") != null) {
			isGetPwdPage = true;
		}
		initViews();
		addListeners();
	}

	private void initViews() {
		mTitle.setText(getString(R.string.msg_check));
		mTitle.setTextColor(getResources().getColor(R.color.text_black));
		mTitleRightTv.setText(getString(R.string.next_step));
		mTitleRightTv.setTextColor(getResources().getColor(R.color.text_grey));
		mTitleRightTv.setClickable(false);
		mTitleBack.setImageResource(R.drawable.back_login_black);
	}

	private void addListeners() {
		mPhone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				checkGetCode();
				checkNextStep();
			}
		});

		mCode.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				checkGetCode();
				checkNextStep();
			}
		});
	}

	/*
	 * check the state of getCode btn (enable or not)
	 */
	private void checkGetCode() {
		if (checkPhoneValid()) {
			mGetCode.setEnabled(true);
		} else {
			mGetCode.setEnabled(false);
		}
	}

	/*
	 * judge the phone is valid or not **return--true:valid--fale:invalid
	 */
	private boolean checkPhoneValid() {
		if (mPhone.getText().length() > 0)
			return true;
		return false;
	}

	/*
	 * check the state of nextStep btn (enable or not)
	 */
	private void checkNextStep() {
		if (mPhone.getText().length() > 0 && mCode.getText().length() > 0) {
			mTitleRightTv.setClickable(true);
			mTitleRightTv.setTextColor(getResources().getColor(R.color.text_black));
		} else {
			mTitleRightTv.setClickable(false);
			mTitleRightTv.setTextColor(getResources().getColor(R.color.text_grey));
		}
	}

	@Click(R.id.title_back)
	void returnLastPage() {
		RegisteActivity.this.finish();
	}

	@Click(R.id.title_right_text)
	void gotoNextPage() {
		VerificationCodeTask task = new VerificationCodeTask();
		task.execute();
	}

	@Click(R.id.get_code)
	void getCheckCode() {
		mGetCode.setClickable(false);
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
							mGetCode.setText("剩余" + count + "秒");
						}
					});
				}
				runOnUiThread(new Runnable() {
					public void run() {
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
		JSONAccessor accessor = new JSONAccessor(RegisteActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected VercodeResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetVerCode");
			param.put("phone", mPhone.getText().toString().trim());
			if (isGetPwdPage) {
				param.put("type", "2");
			} else {
				param.put("type", "1"); // registe
			}

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
						CommonUtils.showToast(RegisteActivity.this.getApplicationContext(), result.getMessage());
					mGetCode.setText("获取\n验证码");
					mGetCode.setClickable(true);
				}

			} else {
				CommonUtils.showToast(RegisteActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}

	}

	/*
	 * check the code's validation
	 */
	private class VerificationCodeTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(RegisteActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "VerificationCode");
			param.put("phone", mPhone.getText().toString().trim());
			param.put("type", "1");
			param.put("code", mCode.getText().toString().trim());
			return accessor.execute(Settings.SUSER_URL, param, VercodeResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					if (isGetPwdPage) {
						Intent intent = new Intent(RegisteActivity.this, ModifyPwdActivity_.class);
						intent.putExtra(IntentsParamNames.PHONENUM, mPhone.getText().toString().trim());
						startActivity(intent);
					} else {
						Intent intent = new Intent(RegisteActivity.this, CreateAccountActivity_.class);
						intent.putExtra(IntentsParamNames.PHONENUM, mPhone.getText().toString().trim());
						startActivity(intent);
					}
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(RegisteActivity.this.getApplicationContext(), result.getMessage());
				}

			} else {
				CommonUtils.showToast(RegisteActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}

	}
}
