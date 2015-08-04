package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.listview.PullToRefreshBase;
import com.daoshun.lib.listview.PullToRefreshListView;
import com.daoshun.lib.listview.PullToRefreshBase.Mode;
import com.daoshun.lib.view.OnSingleClickListener;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;

import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.getMyCoinsListResult;
import xiaoba.coach.net.result.getMyCoinsListResult.CoinList;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.views.DuiHuanShowDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class ActivityMyCoinsList extends BaseActivity {
	private Context context;
	private ImageView imgBack;
	private TextView tvTitle;
	private PullToRefreshListView pullListCoins;
	private MyCoinAdapter myCoinAda;
	private DuiHuanShowDialog duiHuanDialog;
//	private MyCoinAdapter adapter;
	private List<CoinList> getMycoinList = new ArrayList<CoinList>();
	int mPageNum;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_my_coin_list);
	        context = this;
	        duiHuanDialog = new DuiHuanShowDialog(context);
	        initView();
	        initData();
	        addListener();
	        //doRefresh();
	        pullListCoins.setRefreshing();
	}
	private void initView() {
		// TODO Auto-generated method stub
		imgBack = (ImageView)findViewById(R.id.title_back_img);
		tvTitle = (TextView)findViewById(R.id.title);
		pullListCoins = (PullToRefreshListView)findViewById(R.id.lv_my_coin);
//		adapter=new MyCoinAdapter(context);
//		pullListCoins.setAdapter(adapter);
	}
	private void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("收支记录");
		tvTitle.setTextColor(Color.parseColor("#2c2c2c"));
		imgBack.setImageResource(R.drawable.back_arrow);
		myCoinAda = new MyCoinAdapter(context);
		pullListCoins.setAdapter(myCoinAda);
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
		
		pullListCoins.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
	            @Override
	            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
	            	mPageNum = 1;
	            	doRefresh();

	            }

	            @Override
	            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
	            	mPageNum++;
	            	doRefresh();
	                
	            }
	        });
	}
	
	protected void doRefresh() {
		new MyCoinListPost().execute();
	}
	
	private class MyCoinListPost extends AsyncTask<Void, Void, getMyCoinsListResult> {
		JSONAccessor accessor = new JSONAccessor(ActivityMyCoinsList.this, JSONAccessor.METHOD_POST);
		
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
                        CommonUtils.showToast(ActivityMyCoinsList.this.getApplicationContext(),
                                result.getMessage());
                    if (result.getCode() == 95) {
                        CommonUtils.gotoLogin(ActivityMyCoinsList.this);
                    }
				}
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
					String coinnum = getMycoinList.get(index).getCoinnum()+"";
					String addTime = getMycoinList.get(index).getAddtime();
					// TODO Auto-generated methd stub
					if (!TextUtils.isEmpty(mApplication.mUserInfo.getRealname()))
					{
						duiHuanDialog.setMessage(order,mApplication.mUserInfo.getRealname(),"",coinnum,coinnum,addTime);
					}else{
						duiHuanDialog.setMessage(order,mApplication.mUserInfo.getPhone(),"",coinnum,coinnum,addTime);
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
