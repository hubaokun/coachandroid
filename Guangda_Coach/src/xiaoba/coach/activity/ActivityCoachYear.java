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

public class ActivityCoachYear extends BaseActivity {
	private Context context;
	private ImageView imgBack;
	private TextView tvTitle;
	private TextView tvRight;
	private EditText etTeachYear;
	private String teachYear;
	private UserInfo userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_teach_year);
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
		etTeachYear = (EditText)findViewById(R.id.et_teach_year);
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
				teachYear = etTeachYear.getText().toString().trim();
				if (!TextUtils.isEmpty(teachYear))
				{
					new PerfectCoachInfoTask().execute();
				}else{
					Toast.makeText(context, "请输入驾培年龄",0).show();
				}
			}
		});
	}


	private void initData() {
		// TODO Auto-generated method stub
		imgBack.setImageResource(R.drawable.back_arrow);
		tvTitle.setTextColor(Color.parseColor("#2c2c2c"));
		tvTitle.setText("驾培年龄");
		tvRight.setText("完成");
		tvRight.setTextColor(Color.parseColor("#ff4c1c"));
		if(!TextUtils.isEmpty(userinfo.getYears()))
		{
			etTeachYear.setText(userinfo.getYears());
		}
	}
	
	private class PerfectCoachInfoTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(ActivityCoachYear.this.getApplicationContext(), JSONAccessor.METHOD_POST);

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
					info.setYears(teachYear);
					info.saveUserInfo(info, ActivityCoachYear.this);
					//
					CommonUtils.showToast(ActivityCoachYear.this.getApplicationContext(), "修改个人资料成功");
					ActivityCoachYear.this.finish();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(ActivityCoachYear.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ActivityCoachYear.this);
					}
				}
			} else {
				CommonUtils.showToast(ActivityCoachYear.this.getApplicationContext(), getString(R.string.net_error));
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
			param.put("years", teachYear);
			return accessor.execute(Settings.USER_URL, param, BaseResult.class);
		}
	}
}
