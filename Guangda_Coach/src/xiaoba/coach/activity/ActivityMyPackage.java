package xiaoba.coach.activity;

import com.daoshun.lib.view.OnSingleClickListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.module.UserInfo;
import xiaoba.coach.net.result.GetCouponResult.couponList;

public class ActivityMyPackage extends BaseActivity {
	private Context context;
	private ImageView imgBack;
	private TextView tvTitle;
	private TextView tvRight;
	private LinearLayout llYue;
	private RelativeLayout rlCoupon;
	private TextView tvMyMoney;
	private TextView tvMyCoupon;
	private UserInfo userInfo;
	private String CouponCount;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_my_package);
	        context = this;
	        getData();
	        initView();
	        initData();
	        addListener();
	}
	private void getData() {
		// TODO Auto-generated method stub
		userInfo = CoachApplication.getInstance().getUserInfo();
		CouponCount = getIntent().getStringExtra("coupon");
	}
	private void initView() {
		// TODO Auto-generated method stub
		tvTitle = (TextView)findViewById(R.id.title);
		imgBack = (ImageView)findViewById(R.id.title_back_img);
		tvRight = (TextView)findViewById(R.id.title_right_text);
		llYue = (LinearLayout)findViewById(R.id.ll_my_money);
		rlCoupon = (RelativeLayout)findViewById(R.id.rl_my_coupon);
		tvMyMoney = (TextView)findViewById(R.id.tv_my_money);
		tvMyCoupon = (TextView)findViewById(R.id.tv_my_coupon);
	}

	private void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("我的钱包");
		tvTitle.setTextColor(Color.parseColor("#2c2c2c"));
		imgBack.setImageResource(R.drawable.back_arrow);
		
		if (userInfo.getMoney() != null)
			tvMyMoney.setText(userInfo.getMoney().contains(".") ? userInfo.getMoney().substring(0, userInfo.getMoney().indexOf(".")) : userInfo.getMoney());
		else {
			tvMyMoney.setText("0");
		}
		
		tvMyCoupon.setText(CouponCount);
	}
	
	private void addListener() {
		// TODO Auto-generated method stub
		llYue.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context, IncomeDetailActivity_.class));
			}
		});
		
		rlCoupon.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent (context,ActivityMyCoin.class);
				Bundle bundle=new Bundle();
				bundle.putString("coinnum",CouponCount);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		imgBack.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
