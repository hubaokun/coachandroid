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
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.GetIncomeDetailResult;
import xiaoba.coach.net.result.GetMyStudentResult.myStudent;
import xiaoba.coach.utils.CommonUtils;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.provider.CalendarContract.Colors;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.view.OnSingleClickListener;

@EActivity(R.layout.activity_get_cash)
public class GetCashActivity extends BaseActivity {

	@ViewById(R.id.get_cash_cancel)
	ImageView mCancel;
	@ViewById(R.id.get_cash_edit)
	EditText mInput;
	@ViewById(R.id.get_cash_btn)
	Button mSubmit;
	@ViewById(R.id.red_part)
	LinearLayout mRedPart;
	@ViewById(R.id.yue_amount)
	TextView mYueAmouont;
	@ViewById(R.id.img_clenr_money)
	ImageView imgClearn;
	@ViewById(R.id.ll_not_enough_money)
	LinearLayout llNotEnoughMoney;
	@ViewById(R.id.ll_get_money)
	LinearLayout llGetMoney;

	float mBalance;
//	float mPro;

	@AfterViews
	void init() {
		addListeners();

		// int w1 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		// int h1 = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		// mRedPart.measure(w1, h1);
		// int contentHeight = mRedPart.getMeasuredHeight();
		// mRedPart.getLayoutParams().width = contentHeight * 480 / 350;

		mBalance = getIntent().getFloatExtra("balance", 0f);
//		mPro = getIntent().getFloatExtra("pro", 0f);
		if ((int) mBalance == 0) {
			//new GetMyBalanceInfoTask().execute();
			llNotEnoughMoney.setVisibility(View.VISIBLE);
			llGetMoney.setVisibility(View.GONE);
		} else {
			llNotEnoughMoney.setVisibility(View.GONE);
			llGetMoney.setVisibility(View.VISIBLE);
		}
		mYueAmouont.setText(mBalance+"");
	}

	@SuppressLint("NewApi")
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
				

				if (s.length()>0)
				{
					imgClearn.setVisibility(View.VISIBLE);
					int money = Integer.parseInt(s.toString());
					if (money>=50)
					{
					mSubmit.setBackground(getResources().getDrawable(R.drawable.selector_yellow_round));
					mSubmit.setClickable(true);
					mSubmit.setTextColor(getResources().getColor(R.color.white));
					}else{
						mSubmit.setBackground(getResources().getDrawable(R.drawable.shape_gray_round));
						mSubmit.setClickable(false);
						mSubmit.setTextColor(0xffcdcdcd);
					}
				}else{
					imgClearn.setVisibility(View.GONE);
					mSubmit.setBackground(getResources().getDrawable(R.drawable.shape_gray_round));
					mSubmit.setClickable(false);
					mSubmit.setTextColor(0xffcdcdcd);
				}
				
				if (s.toString().equals("0") && s.length() == 1)
					mInput.setText("");
			}
		});
		
		imgClearn.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				mInput.setText("");
			}
		});

	}

	@Click(R.id.get_cash_cancel)
	void cancel() {
		finish();
	}

	@Click(R.id.get_cash_btn)
	void submit() {
		int cashnum = 0;
		try {
			cashnum = Integer.parseInt(mInput.getText().toString());
		} catch (Exception e) {
		}
		if (mInput.getText().length() > 0) {
			if (cashnum>=50)
			{
			if (cashnum <= mBalance) {
				new GetCashTask(cashnum).execute();
			} else {
				CommonUtils.showToast(GetCashActivity.this.getApplicationContext(), "您的可提现余额不足，请重新输入");
			}
			}
			else{
				CommonUtils.showToast(GetCashActivity.this.getApplicationContext(), "提现金额不可少于50元");
			}
		} else {
			CommonUtils.showToast(GetCashActivity.this.getApplicationContext(), "请输入提现金额");
		}
	}

	private class GetCashTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(GetCashActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);
		private int remainMoney;
		 public GetCashTask(int RemainMoney) {
			// TODO Auto-generated constructor stub
			 this.remainMoney = RemainMoney;
		}

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			HashMap<String, Object> param = new BaseParam();
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("count", remainMoney+"");
			param.put("action", "ApplyCash");		//提现
			return accessor.execute(Settings.CMY_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					// CommonUtils.showToast(GetCashActivity.this.getApplicationContext(), "提现成功");
					int money = 0;
					try {
						money = Integer.parseInt(mInput.getText().toString());
					} catch (Exception e) {
					}
					setResult(11, new Intent().putExtra("money", money));
					GetCashActivity.this.finish();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(GetCashActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(GetCashActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(GetCashActivity.this.getApplicationContext(), GetCashActivity.this.getString(R.string.net_error));
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

	}

//	private class GetMyBalanceInfoTask extends AsyncTask<Void, Void, GetIncomeDetailResult> {
//		JSONAccessor accessor = new JSONAccessor(GetCashActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);
//
//		@Override
//		protected GetIncomeDetailResult doInBackground(Void... arg0) {
//			HashMap<String, Object> param = new BaseParam();
//			param.put("action", "GetMyBalanceInfo");
//			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
//			return accessor.execute(Settings.USER_URL, param, GetIncomeDetailResult.class);
//
//		}
//
//		@Override
//		protected void onPostExecute(GetIncomeDetailResult result) {
//			super.onPostExecute(result);
//			if (mLoadingDialog != null && mLoadingDialog.isShowing())
//				mLoadingDialog.dismiss();
//			if (result != null) {
//				if (result.getCode() == 1) {
//					mBalance = result.getBalance();
//					mYueAmouont.setText((int) mBalance + "元");
//				} else {
//					if (result.getMessage() != null)
//						CommonUtils.showToast(GetCashActivity.this.getApplicationContext(), result.getMessage());
//					if (result.getCode() == 95) {
//						CommonUtils.gotoLogin(GetCashActivity.this);
//					}
//				}
//			} else {
//				CommonUtils.showToast(GetCashActivity.this.getApplicationContext(), GetCashActivity.this.getString(R.string.net_error));
//			}
//		}
//
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			if (mLoadingDialog != null)
//				mLoadingDialog.show();
//		}
//
//	}

}
