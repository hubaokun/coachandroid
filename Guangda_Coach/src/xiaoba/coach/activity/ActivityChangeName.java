package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.api.view.OnViewChangedNotifier;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.listview.PullToRefreshListView;
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
import xiaoba.coach.net.result.GetMyStudentResult;
import xiaoba.coach.net.result.GetMyStudentResult.myStudent;
import xiaoba.coach.utils.CommonUtils;

public class ActivityChangeName extends BaseActivity {
	private Context context;
	private ImageView imgBack;
	private TextView tvTitle;
	private TextView tvRight;
	private EditText etName;
	private String name;
	private UserInfo userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
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
		etName = (EditText)findViewById(R.id.et_name);
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
				name = etName.getText().toString().trim();
				if (!TextUtils.isEmpty(name))
				{
					new PerfectAccountInfoTask().execute();
				}else{
					Toast.makeText(context, "请填写姓名", 0).show();
				}
			}
		});
	}


	private void initData() {
		// TODO Auto-generated method stub
		imgBack.setImageResource(R.drawable.back_arrow);
		tvTitle.setTextColor(Color.parseColor("#2c2c2c"));
		tvTitle.setText("姓名");
		tvRight.setText("完成");
		tvRight.setTextColor(Color.parseColor("#ff4c1c"));
		if (!TextUtils.isEmpty(userinfo.getRealname()))
		{
			etName.setText(userinfo.getRealname());
		}
	}
	
	private class PerfectAccountInfoTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(ActivityChangeName.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "PerfectAccountInfo");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("realname", name);

//			if (mSchoolName.isFocusable()) {
//				school = mSchoolName.getText().toString();
//				param.put("drive_school", school);
//			}
//			if (mGenderTv.getText().toString().equals("男")) {
//				param.put("gender", "1");
//				gender = "1";
//			} else if (mGenderTv.getText().toString().equals("女")) {
//				param.put("gender", "2");
//				gender = "2";
//			}
			return accessor.execute(Settings.USER_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.getCode() == 1) {
					// registe ok
					CommonUtils.showToast(ActivityChangeName.this.getApplicationContext(), "提交资料成功");
					UserInfo info = new UserInfo();
					if (name != null)
						info.saveRealName(name, ActivityChangeName.this.getApplicationContext());
					finish();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(ActivityChangeName.this.getApplicationContext(), result.getMessage());
				}

			} else {
				CommonUtils.showToast(ActivityChangeName.this.getApplicationContext(), getString(R.string.net_error));
				if (result.getCode() == 95) {
					CommonUtils.gotoLogin(ActivityChangeName.this);
				}
			}

			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
		}
	}
}
