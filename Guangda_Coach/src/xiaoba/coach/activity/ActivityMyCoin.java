package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Comment;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.listview.PullToRefreshListView;
import com.daoshun.lib.listview.PullToRefreshBase.Mode;
import com.daoshun.lib.view.OnSingleClickListener;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.activity.ActivityMyCoinsList.HolderView;
import xiaoba.coach.activity.ActivityMyCoinsList.MyCoinAdapter;
import xiaoba.coach.common.Settings;
import xiaoba.coach.interfaces.OnScaleHaloListener;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.getMyCoinsListResult;
import xiaoba.coach.net.result.getMyCoinsListResult.CoinList;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.views.DuiHuanShowDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
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
	private PullToRefreshListView pullListCoins;
	private DuiHuanShowDialog duiHuanDialog;
	private List<CoinList> getMycoinList = new ArrayList<CoinList>();
	private MyCoinAdapter myCoinAda;
	int mPageNum;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_my_coin);
	        context = this;
	        duiHuanDialog = new DuiHuanShowDialog(context);
	        getData();
	        initView();
	        initData();
	        addListener();
	        new MyCoinListPost().execute();
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
		pullListCoins = (PullToRefreshListView)findViewById(R.id.lv_my_coin);
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
		myCoinAda = new MyCoinAdapter(context);
		pullListCoins.setAdapter(myCoinAda);
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
//		tvRight.setOnClickListener(new OnSingleClickListener() {
//			
//			@Override
//			public void doOnClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent (context,ActivityMyCoinsList.class);
//				startActivity(intent);
//			}
//		});
		
		imgBack.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	private class MyCoinListPost extends AsyncTask<Void, Void, getMyCoinsListResult> {
		JSONAccessor accessor = new JSONAccessor(ActivityMyCoin.this, JSONAccessor.METHOD_POST);
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		@Override
		protected getMyCoinsListResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GETMYCOINRECORD");
			param.put("pagenum",mPageNum);
			param.put("coachid",CoachApplication.getInstance().getUserInfo().getCoachid());//CoachApplication.getInstance().getUserInfo().getCoachid()
			return accessor.execute(Settings.USER_URL, param, getMyCoinsListResult.class);
		}

		@Override
		protected void onPostExecute(getMyCoinsListResult result) {
			
			super.onPostExecute(result);
			if (pullListCoins != null)
				pullListCoins.onRefreshComplete();
			if (result.getCode() == 1)
			{
				if (result.getRecordlist()!=null&&result.getRecordlist().size()>0)
				{
					if (mPageNum == 1)
					{
						getMycoinList.clear();
					}
					getMycoinList.addAll(result.getRecordlist());
					myCoinAda.setData();
					
				}
            if (result.getHasmore() == 0) {
            	pullListCoins.setMode(Mode.PULL_FROM_START);
            } else {
            	pullListCoins.setMode(Mode.BOTH);
            }
			}else{
				if (result.getMessage()!=null)
				{
					if (result.getMessage() != null)
                        CommonUtils.showToast(ActivityMyCoin.this.getApplicationContext(),
                                result.getMessage());
                    if (result.getCode() == 95) {
                        CommonUtils.gotoLogin(ActivityMyCoin.this);
                    }
				}
			}
		}
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
				getMycoinList.clear();
				new MyCoinListPost().execute();
			}
		}
	}
	
	public class MyCoinAdapter extends BaseAdapter
	{
		
		 private LayoutInflater inflater;
		 public MyCoinAdapter(Context context)
		 {
			 this.inflater = LayoutInflater.from(context);
		 }
		 
	   public void setData() {
	            notifyDataSetChanged();
	        }
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return getMycoinList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return getMycoinList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HolderView holder = null;
            if (convertView == null){
                holder = new HolderView();
                convertView = inflater.inflate(R.layout.my_coin_item,null);
                holder.tvCoinFrom = (TextView)convertView.findViewById(R.id.tv_coin_from);
                holder.tvCoinTitle = (TextView)convertView.findViewById(R.id.tv_coins_title);
                holder.tvCoinMoney = (TextView)convertView.findViewById(R.id.tv_coin_money);
                holder.btnCoinShow = (Button)convertView.findViewById(R.id.btn_coin_show);
                holder.tv_time=(TextView)convertView.findViewById(R.id.tv_time);
                convertView.setTag(holder);
            }else {
                holder = (HolderView)convertView.getTag();
            }
            if (getMycoinList.get(position).getPayertype() == 2)  //兑换           pertype = 0:平台，1：驾校，2：教练，3：学员 
            {
            	holder.tvCoinTitle.setText("小巴币兑换");
            	holder.tvCoinMoney.setText("-"+getMycoinList.get(position).getCoinnum());
            	holder.tvCoinMoney.setTextColor(getResources().getColor(R.color.text_green));
            }else{
            	holder.tvCoinTitle.setText("订单支付");
            	holder.tvCoinMoney.setText("+"+getMycoinList.get(position).getCoinnum());
            	holder.tvCoinMoney.setTextColor(getResources().getColor(R.color.text_red));
            }
            
            holder.tv_time.setText(getMycoinList.get(position).getAddtime());
            
            holder.tvCoinFrom.setText(getMycoinList.get(position).getPayername());
            holder.btnCoinShow.setTag(position);
            holder.btnCoinShow.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int index = Integer.valueOf(v.getTag().toString());
					String order = getMycoinList.get(index).getReceiverid()+"";
					String coinOrder = "";
					int lengh = order.length();
					for (int i=11;i>lengh;i--){
						coinOrder = coinOrder+"0";
					}
					coinOrder = coinOrder+order;
					String coinnum = getMycoinList.get(index).getCoinnum()+"";
					String addTime = getMycoinList.get(index).getAddtime();
					// TODO Auto-generated methd stub
					if (!TextUtils.isEmpty(mApplication.mUserInfo.getRealname()))
					{
						duiHuanDialog.setMessage(coinOrder,mApplication.mUserInfo.getRealname(),"",coinnum,coinnum,addTime);
					}else{
						duiHuanDialog.setMessage(coinOrder,mApplication.mUserInfo.getPhone(),"",coinnum,coinnum,addTime);
					}
				}
			});
			return convertView;
		}
	}
	
	public class HolderView
	{
		private TextView tvCoinTitle;
		private TextView tvCoinMoney;
		private Button btnCoinShow;
		private TextView tvCoinFrom,tv_time;
		
	}
}
