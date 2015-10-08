package xiaoba.coach.activity;

import java.util.HashMap;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.view.OnSingleClickListener;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.GetRecommendResult;
import xiaoba.coach.net.result.GetSendInviteCodeResult;
import xiaoba.coach.utils.CommonUtils;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityInputRecord extends BaseActivity {
	private Context mcontext;
	private ImageView imgBack;
	private EditText etRecord;
	private Button btnRecord;
	private LinearLayout llCancelRecord;
	private TextView tvTitle;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_input_recode);
	        mcontext = this;
	        initView();
	        initData();
	        addListener();
	    }

	private void initView() {
		// TODO Auto-generated method stub
		imgBack = (ImageView)findViewById(R.id.title_back_img);
		tvTitle = (TextView)findViewById(R.id.title);
		etRecord = (EditText)findViewById(R.id.et_input_record);
		btnRecord = (Button)findViewById(R.id.btn_record);
		llCancelRecord = (LinearLayout)findViewById(R.id.ll_record);
	}

	private void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("邀请码");
		tvTitle.setTextColor(Color.parseColor("#2c2c2c"));
		imgBack.setImageResource(R.drawable.back_arrow);
	}

	private void addListener() {
		// TODO Auto-generated method stub
		imgBack.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (mcontext,ProQualityActivity_.class);
				startActivity(intent);
				finish();
			}
		});
		
		llCancelRecord.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (mcontext,ProQualityActivity_.class);
				startActivity(intent);
				finish();
			}
		});
		
		btnRecord.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(etRecord.getText().toString().trim()))
				{
					new getSendInviteCoed().execute();
				}else{
					CommonUtils.showToast(getApplicationContext(),"您输入的推荐码不能为空，请重新输入");
				}
			}
		});
	}
	
	private class getSendInviteCoed extends AsyncTask<Void, Void, GetSendInviteCodeResult> {
		JSONAccessor accessor = new JSONAccessor(getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// if (mLoadingDialog != null)
			// mLoadingDialog.show();
		}
		
		@Override
		protected GetSendInviteCodeResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "CHEAKINVITECODE");
			param.put("InvitedPeopleid", CoachApplication.getInstance().getUserInfo().getCoachid());// CoachApplication.getInstance().getUserInfo().getCoachid()
			param.put("InviteCode", etRecord.getText().toString().trim());
			param.put("type",1);
			return accessor.execute(Settings.CRECOMM, param, GetSendInviteCodeResult.class);
		}
		
		@Override
		protected void onPostExecute(GetSendInviteCodeResult result) {
			super.onPostExecute(result);
			// if (mLoadingDialog != null && mLoadingDialog.isShowing())
			// mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					if (result.getInviteCode() == 1)
					{
					if (result.getIsRecommended()==1)
					{
						//CommonUtils.showToast(getApplicationContext(),"恭喜您，受邀请成功");
						Intent intent = new Intent (mcontext,ProQualityActivity_.class);
						startActivity(intent);
						finish();
					}else{
						CommonUtils.showToast(getApplicationContext(),"您输入的推荐码有误，请重新输入");
					}
					}else{
						CommonUtils.showToast(getApplicationContext(),"您输入的推荐码格式错误，请重新输入");
					}
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ActivityInputRecord.this);
					}
				}
			} else {
				CommonUtils.showToast(getApplicationContext(), getString(R.string.net_error));
			}
		}
	}
}
