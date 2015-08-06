package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Comment;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.view.OnSingleClickListener;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.interfaces.OnScaleHaloListener;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.utils.CommonUtils;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMyCoin extends BaseActivity {
	private Context context;
	private TextView tvTitle;
	private ImageView imgBack;
	private TextView tvRight;
	private TextView tvCoachName;
	private TextView tvCoinCount;
//	private EditText etCoin;
	private Button btnPayCoin;
	private String coinnum;
	private int resetCoinNum = 0;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_my_coin);
	        context = this;
	        getData();
	        initView();
	        initData();
	        addListener();
	}
	 private void getData(){
		 
		 Bundle bundl=getIntent().getExtras();
		 coinnum=bundl.getString("coinnum");
		 resetCoinNum = Integer.parseInt(coinnum);

	 }
	private void initView() {
		// TODO Auto-generated method stub
		tvTitle = (TextView)findViewById(R.id.title);
		imgBack = (ImageView)findViewById(R.id.title_back_img);
		tvRight = (TextView)findViewById(R.id.title_right_text);
		tvCoachName = (TextView)findViewById(R.id.tv_coach_name);
		tvCoinCount = (TextView)findViewById(R.id.tv_coin_count);
//		etCoin = (EditText)findViewById(R.id.et_coins);
		btnPayCoin = (Button)findViewById(R.id.btn_sour_pay);
		 if (resetCoinNum>0)
		 {
			 btnPayCoin.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_yellow_round));
			 btnPayCoin.setEnabled(true);
		 }else{
			 btnPayCoin.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_grey_round));
			 btnPayCoin.setEnabled(false);
		 }
	}
	private void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("我的小巴币");
		tvTitle.setTextColor(Color.parseColor("#2c2c2c"));
		imgBack.setImageResource(R.drawable.back_arrow);
		tvRight.setBackgroundResource(R.drawable.btn_coin_meum);
		if (!TextUtils.isEmpty(mApplication.mUserInfo.getRealname()))
		{
		tvCoachName.setText(mApplication.mUserInfo.getRealname()+"教练");
		}else{
			tvCoachName.setText(mApplication.mUserInfo.getPhone());
		}
		tvCoinCount.setText(coinnum);
	}
	private void addListener() {

		// TODO Auto-generated method stub
//		etCoin.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				// TODO Auto-generated method stub
//				if (etCoin.length()==0)
//				{
//					btnPayCoin.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_grey_round));
//				}else{
//					btnPayCoin.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_yellow_round));
//				}
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		btnPayCoin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				int inputCoinNum = Integer.parseInt(etCoin.getText().toString());
//				if (inputCoinNum>resetCoinNum)
//				{
//					CommonUtils.showToast(ActivityMyCoin.this,"您可兑换的小巴币数量不足，请重新输入");
//					etCoin.setText("");
//					return;
//				}
//				if(etCoin.length()!=0){
					new ApplyCoin().execute();
//				}
			}
		});
		tvRight.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent (context,ActivityMyCoinsList.class);
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
	
	private class ApplyCoin extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(ActivityMyCoin.this, JSONAccessor.METHOD_POST);
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "APPLYCOIN");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("coinnum",coinnum);
			return accessor.execute(Settings.CMY_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if(result.getCode()==1){
//				resetCoinNum = resetCoinNum-Integer.parseInt(etCoin.getText().toString());
				tvCoinCount.setText("0");
//				etCoin.setText("");
				Toast.makeText(mApplication, "兑换成功", Toast.LENGTH_SHORT).show();
				btnPayCoin.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_grey_round));
				btnPayCoin.setEnabled(false);
			}
		}
		
	}
}
