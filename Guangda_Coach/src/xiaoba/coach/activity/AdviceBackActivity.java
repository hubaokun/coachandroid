package xiaoba.coach.activity;


import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.daoshun.lib.communication.http.JSONAccessor;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.utils.CommonUtils;

import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@EActivity(R.layout.activity_advice_back)
public class AdviceBackActivity extends BaseActivity {
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.advice_title)
	RelativeLayout mTitleLayout;
	@ViewById(R.id.advice_et)
	EditText mAdviceEt;

	@AfterViews
	void init() {
		mTitle.setText("意见反馈");
		mTitleBack.setImageResource(R.drawable.back_arrow);
		mTitle.setTextColor(Color.parseColor("#2c2c2c"));
		mTitleRightTv.setText("提交");
		mTitleRightTv.setTextColor(Color.parseColor("#d2d2d2"));
		mTitleRightTv.setClickable(false);
		mTitleRightTv.setVisibility(View.VISIBLE);
		mTitleLayout.setBackgroundColor(Color.parseColor("#ffffff"));
		
		
		mAdviceEt.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				if (mAdviceEt.getText().length() > 0) {
					mTitleRightTv.setTextColor(Color.parseColor("#2c2c2c"));
					mTitleRightTv.setClickable(true);
				} else {
					mTitleRightTv.setTextColor(Color.parseColor("#d2d2d2"));
					mTitleRightTv.setClickable(false);
				}
			}
		});
	}

	@Click(R.id.title_back)
	void goBack() {
		finish();
	}
	
	@Click(R.id.title_right_text)
	void submit(){
		new FeedbackTask().execute();
	}
	
	private class FeedbackTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(AdviceBackActivity.this, JSONAccessor.METHOD_POST);
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "Feedback");
			param.put("studentid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("type", "1");
			param.put("content", mAdviceEt.getText().toString());
			return accessor.execute(Settings.SSET_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
				mLoadingDialog.dismiss();
			}
			if (result != null) {
				if (result.getCode() == 1) {
					CommonUtils.showToast(AdviceBackActivity.this.getApplicationContext(), "提交成功");
					AdviceBackActivity.this.finish();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(AdviceBackActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(AdviceBackActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(AdviceBackActivity.this.getApplicationContext(), AdviceBackActivity.this.getString(R.string.net_error));
			}
		}
		
	}
}
