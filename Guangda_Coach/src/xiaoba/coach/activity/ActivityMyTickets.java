package xiaoba.coach.activity;

import java.util.HashMap;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.view.OnSingleClickListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.UserInfo;
import xiaoba.coach.net.result.GetCouponLimit;
import xiaoba.coach.net.result.GetStudentRestCouponResult;
import xiaoba.coach.utils.CommonUtils;

public class ActivityMyTickets extends BaseActivity {
	private Context context;
	private ImageView imgBack;
	private TextView tvTitle;
	private TextView tvRight;
	private RelativeLayout rlMyTickets;
	private RelativeLayout rlSendTickets;
	private TextView tvTickets;
	private String ticketNum;
	private TextView tvNoLimit;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_my_tickets);
	        context = this;
	        getData();
	        initView();
	        initData();
	        addListener();
	}
	private void getData() {
		// TODO Auto-generated method stub
		new getCouponlimit().execute();
		ticketNum = getIntent().getStringExtra("ticket");
	}
	private void initView() {
		// TODO Auto-generated method stub
		tvTitle = (TextView)findViewById(R.id.title);
		imgBack = (ImageView)findViewById(R.id.title_back_img);
		tvRight = (TextView)findViewById(R.id.title_right_text);
		rlMyTickets = (RelativeLayout)findViewById(R.id.rl_my_tickets);
		rlSendTickets = (RelativeLayout)findViewById(R.id.rl_send_tickets);
		tvTickets = (TextView)findViewById(R.id.tv_tickets);
		tvNoLimit = (TextView)findViewById(R.id.tv_show_has_limit);
	}

	private void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("小巴券");
		tvTitle.setTextColor(Color.parseColor("#2c2c2c"));
		imgBack.setImageResource(R.drawable.back_arrow);
		tvTickets.setText(ticketNum);
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
		
		rlMyTickets.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context, ActivityGetAllCoupon.class);
				startActivity(intent);
			}
		});
		
		rlSendTickets.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context,ActivitySendCoupon.class));
			}
		});
	}
	
	private class getCouponlimit extends AsyncTask<Void, Void, GetCouponLimit> {
		JSONAccessor accessor = new JSONAccessor(getApplicationContext(), JSONAccessor.METHOD_POST);
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			 if (mLoadingDialog != null)
			 mLoadingDialog.show();
		}
		
		@Override
		protected GetCouponLimit doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GETCOACHCOUPONLIMIT");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());// CoachApplication.getInstance().getUserInfo().getCoachid()
			return accessor.execute(Settings.USER_URL, param, GetCouponLimit.class);
		}
		
		@Override
		protected void onPostExecute(GetCouponLimit result) {
			super.onPostExecute(result);
			 if (mLoadingDialog != null && mLoadingDialog.isShowing())
			 mLoadingDialog.dismiss();
			if (result != null) {
				if(result.getCode() == 1)
				{
				if (result.getGrantlimit() == 1)
				{
					tvNoLimit.setVisibility(View.GONE);
					rlSendTickets.setEnabled(true);
				}else{
					tvNoLimit.setVisibility(View.VISIBLE);
					rlSendTickets.setEnabled(false);
				}
				}
				else{
					CommonUtils.showToast(getApplicationContext(), result.getMessage());
				}
			} else {
				CommonUtils.showToast(getApplicationContext(), getString(R.string.net_error));
			}
		}
	}
	
}
