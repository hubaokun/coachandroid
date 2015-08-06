package xiaoba.coach.activity;

import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.alipay.AliPayTask;
import xiaoba.coach.alipay.Pparams;
import xiaoba.coach.common.Settings;
import xiaoba.coach.net.result.RechargeResult;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.views.LoadingDialog;

import com.daoshun.lib.communication.http.JSONAccessor;

import android.os.AsyncTask;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

@EActivity(R.layout.activity_recharge)
public class RechargeActivity extends BaseActivity {
	@ViewById(R.id.recharge_cancel)
	ImageView mCancel;
	@ViewById(R.id.recharge_edit)
	EditText mInput;
	@ViewById(R.id.recharge_btn)
	Button mSubmit;

	@AfterViews
	void init() {
		addListeners();
	}

	private void addListeners() {
		mInput.setKeyListener(new NumberKeyListener() {

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

		mInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString().equals("0") && s.length() == 1)
					mInput.setText("");
			}
		});

	}

	@Click(R.id.recharge_cancel)
	void cancel() {
		finish();
	}

	@Click(R.id.recharge_btn)
	void submit() {
		if (mInput.getText().length() > 0) {
			new RechargeTask().execute();
		} else {
			CommonUtils.showToast(RechargeActivity.this.getApplicationContext(), "请输入充值金额");
		}
	}

	private class RechargeTask extends AsyncTask<Void, Void, RechargeResult> {
		JSONAccessor accessor = new JSONAccessor(RechargeActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected RechargeResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("amount", mInput.getText().toString());
			param.put("action", "Recharge");
			param.put("token", CoachApplication.getInstance().getUserInfo().getToken());

			return accessor.execute(Settings.USER_URL, param, RechargeResult.class);
		}

		@Override
		protected void onPostExecute(RechargeResult result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.getCode() == 1) {
					Pparams mPparams = new Pparams();
					mPparams.setBody(result.getBody());
					mPparams.setNotify_url(result.getNotify_url());
					mPparams.setOut_trade_no(result.getOut_trade_no());
					mPparams.setPartner(result.getPartner());
					mPparams.setRsakey(result.getPrivate_key());
					mPparams.setSeller_id(result.getSeller_id());
					mPparams.setSubject(result.getSubject());
					mPparams.setTotal_fee(mInput.getText().toString());
					doAlipay(mPparams, result.getOut_trade_no(), mLoadingDialog);
				} else {
					if (mLoadingDialog != null && mLoadingDialog.isShowing())
						mLoadingDialog.dismiss();
					if (result.getMessage() != null)
						CommonUtils.showToast(RechargeActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(RechargeActivity.this);
					}
				}
			} else {
				if (mLoadingDialog != null && mLoadingDialog.isShowing())
					mLoadingDialog.dismiss();
				CommonUtils.showToast(RechargeActivity.this.getApplicationContext(), RechargeActivity.this.getString(R.string.net_error));
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

	}

	RechargeActivity mActivity = this;
	public void doAlipay(Pparams mPparams, String orderid, LoadingDialog mLoadingDialog) {
		new AliPayTask(mActivity, mPparams, orderid, mLoadingDialog).Execute();
	}
}
