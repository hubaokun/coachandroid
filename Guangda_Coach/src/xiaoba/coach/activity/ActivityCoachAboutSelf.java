package xiaoba.coach.activity;

import java.util.HashMap;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.view.OnSingleClickListener;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.UserInfo;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.utils.CommonUtils;

public class ActivityCoachAboutSelf extends BaseActivity {
	private Context context;
	private ImageView imgBack;
	private TextView tvTitle;
	private TextView tvRight;
	private EditText etAboutSelf;
	private String aboutSelf;
	private UserInfo userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_about_self);
        context = this;
        userinfo = mApplication.getUserInfo();
        initView();
        addListener();
        initData();
    }

	private void initView() {
		// TODO Auto-generated method stub
		imgBack = (ImageView)findViewById(R.id.title_back_img); 
		tvTitle = (TextView)findViewById(R.id.title); 
		tvRight = (TextView)findViewById(R.id.title_right_text); 
		etAboutSelf = (EditText)findViewById(R.id.et_about_self); 
	}

	private void addListener() {
		// TODO Auto-generated method stub
		imgBack.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		tvRight.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				aboutSelf = etAboutSelf.getText().toString().trim();
				if (!TextUtils.isEmpty(aboutSelf))
				{
					new PerfectCoachInfoTask().execute();
				}else{
					Toast.makeText(context, "请输入个人评价", 0).show();
				}
			}
		});
	}


	private void initData() {
		// TODO Auto-generated method stub
		imgBack.setImageResource(R.drawable.back_arrow);
		tvTitle.setTextColor(Color.parseColor("#2c2c2c"));
		tvTitle.setText("个人评价");
		tvRight.setText("完成");
		tvRight.setTextColor(Color.parseColor("#ff4c1c"));
		if (!TextUtils.isEmpty(userinfo.getSelfeval()))
		{
			etAboutSelf.setText(userinfo.getSelfeval());
		}
	}
	
	private class PerfectCoachInfoTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(ActivityCoachAboutSelf.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			accessor.enableJsonLog(true);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					/*
					 * 保存本地信息
					 */
					UserInfo info = CoachApplication.getInstance().getUserInfo();
					// if (mSchoolName.isFocusable())
					// info.setDrive_school(mSchoolName.getText().toString());
					info.setSelfeval(aboutSelf);
					info.saveUserInfo(info, ActivityCoachAboutSelf.this);
					//
					CommonUtils.showToast(ActivityCoachAboutSelf.this.getApplicationContext(), "修改个人资料成功");
					ActivityCoachAboutSelf.this.finish();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(ActivityCoachAboutSelf.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ActivityCoachAboutSelf.this);
					}
				}
			} else {
				CommonUtils.showToast(ActivityCoachAboutSelf.this.getApplicationContext(), getString(R.string.net_error));
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
			HashMap<String, Object> param = new BaseParam();
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("action", "PerfectPersonInfo");
			// if (mSchoolName.isFocusable())
			// param.put("driveschool", mSchoolName.getText().toString());
			param.put("selfeval", aboutSelf);
			return accessor.execute(Settings.USER_URL, param, BaseResult.class);
		}
	}
}
