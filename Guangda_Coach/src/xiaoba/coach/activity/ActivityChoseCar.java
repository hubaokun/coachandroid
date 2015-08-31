package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.List;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.view.OnSingleClickListener;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.CarType;
import xiaoba.coach.module.UserInfo;
import xiaoba.coach.net.request.PerfectCoachInfoReq;
import xiaoba.coach.net.result.PerfectCoachInfoResult;
import xiaoba.coach.utils.CommonUtils;

public class ActivityChoseCar extends BaseActivity {
	private Context context;
	private ImageView imgBack;
	private TextView tvTitle;
	private TextView tvRight;
	private RelativeLayout rlC1Normal;
	private RelativeLayout rlC1Select;
	private RelativeLayout rlC2Normal;
	private RelativeLayout rlC2Select;
	private RelativeLayout rlC1;
	private RelativeLayout rlC2;
	private boolean isC1Chosed = false;
	private boolean isC2Chosed = false;
	private UserInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_teach_car);
        context = this;
        info = CoachApplication.getInstance().getUserInfo();
        initView();
        addListener();
        initData();
    }

	private void initView() {
		// TODO Auto-generated method stub
		imgBack = (ImageView)findViewById(R.id.title_back_img);
		tvTitle = (TextView)findViewById(R.id.title);
		tvRight = (TextView)findViewById(R.id.title_right_text);
		rlC1Normal = (RelativeLayout)findViewById(R.id.rl_chose_c1_normal);
		rlC1Select = (RelativeLayout)findViewById(R.id.rl_chose_c1_select);
		rlC2Normal = (RelativeLayout)findViewById(R.id.rl_chose_c2_normal);
		rlC2Select = (RelativeLayout)findViewById(R.id.rl_chose_c2_select);
		rlC1 = (RelativeLayout)findViewById(R.id.rl_c1);
		rlC2 = (RelativeLayout)findViewById(R.id.rl_c2);
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
		
		rlC1.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				if (isC1Chosed)
				{
					rlC1Normal.setVisibility(View.VISIBLE);
					rlC1Select.setVisibility(View.GONE);
					isC1Chosed = false;
				}else{
					rlC1Normal.setVisibility(View.GONE);
					rlC1Select.setVisibility(View.VISIBLE);
					isC1Chosed = true;
				}
			}
		});
		
		rlC2.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				if (isC2Chosed)
				{
					rlC2Normal.setVisibility(View.VISIBLE);
					rlC2Select.setVisibility(View.GONE);
					isC2Chosed = false;
				}else{
					rlC2Normal.setVisibility(View.GONE);
					rlC2Select.setVisibility(View.VISIBLE);
					isC2Chosed = true;
				}
			}
		});
		
		tvRight.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				if (isC1Chosed == false && isC2Chosed == false)
				{
					Toast.makeText(context, "请选择一种准教车型",0).show();
				}else{
					new PerfectCoachInfoTask().execute();
				}
			}
		});
		
	}

	private void initData() {
		// TODO Auto-generated method stub
		if (info.getModellist() != null && info.getModellist().size() > 0) {
			/*
			 * int count = info.getModellist().size(); if (count == 1) { selectmodels.add(info.getModellist().get(0).getModelname()); mCarType1.setText(info.getModellist().get(0).getModelname());
			 * mCarType1.setTextColor(getResources().getColor(R.color.text_black)); } else { for (int i = 0; i < count; i++) { selectmodels.add(info.getModellist().get(i).getModelname()); TextView tv
			 * = new TextView(ProQualityActivity.this); tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(ProQualityActivity.this, 30)));
			 * tv.setText(info.getModellist().get(i).getModelname()); tv.setTextSize(18); mCartypeLayout.addView(tv); } }
			 */
			for (CarType cartype:info.getModellist())
			{
				switch (cartype.getModelid()) {
				case 17:
					isC1Chosed = true;
					break;
				case 18:
					isC2Chosed = true;
					break;
				default:
					break;
				}
			}
			// mCarType1.setTextColor(getResources().getColor(R.color.text_black));
			// mCarType1.setText(info.getModellist().get(0).getModelname());
		}
		imgBack.setImageResource(R.drawable.back_arrow);
		tvTitle.setTextColor(Color.parseColor("#2c2c2c"));
		tvTitle.setText("准教车型");
		tvRight.setText("提交");
		tvRight.setTextColor(Color.parseColor("#ff4c1c"));
		if (isC1Chosed)
		{
			rlC1Select.setVisibility(View.VISIBLE);
			rlC1Normal.setVisibility(View.GONE);
		}else{
			rlC1Normal.setVisibility(View.VISIBLE);
			rlC1Select.setVisibility(View.GONE);
		}
		
		if (isC2Chosed)
		{
			rlC2Select.setVisibility(View.VISIBLE);
			rlC2Normal.setVisibility(View.GONE);
		}else{
			rlC2Select.setVisibility(View.GONE);
			rlC2Normal.setVisibility(View.VISIBLE);
		}
	}
	
	private class PerfectCoachInfoTask extends AsyncTask<Void, Void, PerfectCoachInfoResult> {
		JSONAccessor accessor = new JSONAccessor(ActivityChoseCar.this.getApplicationContext(), JSONAccessor.METHOD_POST_MULTIPART);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected PerfectCoachInfoResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			PerfectCoachInfoReq param = new PerfectCoachInfoReq();
			param.setAction("PERFECTCOACHMODELID");
			param.setCoachid(CoachApplication.getInstance().getUserInfo().getCoachid() + "");
			param.setToken(CoachApplication.getInstance().getUserInfo().getToken());
			StringBuilder modelStr = new StringBuilder();
			if (isC1Chosed)
			{
				modelStr.append("17");
			}
			if (isC2Chosed)
			{
				if (isC1Chosed)
				{
					modelStr.append(",18");
				}else{
					modelStr.append("18");
				}
			}
			String str = modelStr.toString();
			param.setModelid(str);

			return accessor.execute(Settings.USER_URL, param, PerfectCoachInfoResult.class);
		}

		@Override
		protected void onPostExecute(PerfectCoachInfoResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					/*
					 * 保存本地信息
					 */
					UserInfo info = CoachApplication.getInstance().getUserInfo();
				
					List<CarType> temp = new ArrayList<CarType>();
					if (isC1Chosed)
					{
						CarType type = new CarType();
						type.setModelid(17);
						temp.add(type);
					}
					if (isC2Chosed)
					{
						CarType type = new CarType();
						type.setModelid(18);
						temp.add(type);
					}
					info.setModellist(temp);
					info.saveUserInfo(info, ActivityChoseCar.this);
					//
					CommonUtils.showToast(ActivityChoseCar.this.getApplicationContext(), "提交成功");
					ActivityChoseCar.this.finish();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(ActivityChoseCar.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ActivityChoseCar.this);
					}
				}

			} else {
				CommonUtils.showToast(ActivityChoseCar.this.getApplicationContext(), getString(R.string.net_error));
			}
		}
	}
}
