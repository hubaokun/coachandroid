package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.listview.PullToRefreshBase;
import com.daoshun.lib.listview.PullToRefreshListView;
import com.daoshun.lib.listview.PullToRefreshBase.Mode;
import com.daoshun.lib.view.OnSingleClickListener;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.activity.ActivityMyCoinsList.HolderView;
import xiaoba.coach.activity.ActivityMyCoinsList.MyCoinAdapter;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.GetCouponHistoryResult;
import xiaoba.coach.net.result.GetCouponHistoryResult.RecordList;
import xiaoba.coach.net.result.getMyCoinsListResult;
import xiaoba.coach.utils.CommonUtils;

public class ActivitySendHistory extends BaseActivity {
	private Context context;
	private ImageView imgBack;
	private TextView tvTitle;
	private TextView tvRight;
	private PullToRefreshListView pullCouponHistory;
	private getCouponHistory couponAda;
	private int mPageNum;
	private ArrayList<RecordList> CouponArray = new ArrayList<RecordList>();
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_send_history);
	        context = this;
	        getData();
	        initView();
	        initData();
	        addListener();
	        pullCouponHistory.setRefreshing();
	}
	private void getData() {
		// TODO Auto-generated method stub
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		tvTitle = (TextView)findViewById(R.id.title);
		imgBack = (ImageView)findViewById(R.id.title_back_img);
		tvRight = (TextView)findViewById(R.id.title_right_text);
		pullCouponHistory = (PullToRefreshListView)findViewById(R.id.pull_coupon_history);
	}

	private void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("发放记录");
		tvTitle.setTextColor(Color.parseColor("#2c2c2c"));
		imgBack.setImageResource(R.drawable.back_login_black);
		couponAda = new getCouponHistory(context);
		pullCouponHistory.setAdapter(couponAda);
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
		
		pullCouponHistory.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            	mPageNum = 0;
            	new getCouponResult().execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            	mPageNum++;
            	new getCouponResult().execute();
            }
        });
	}
	
	private class getCouponResult extends AsyncTask<Void, Void, GetCouponHistoryResult> {
		JSONAccessor accessor = new JSONAccessor(ActivitySendHistory.this, JSONAccessor.METHOD_POST);
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
		}

		@Override
		protected GetCouponHistoryResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GETCOACHCOUPONLIST");
			param.put("pagenum",mPageNum);
			param.put("coachid",CoachApplication.getInstance().getUserInfo().getCoachid());//CoachApplication.getInstance().getUserInfo().getCoachid()
			return accessor.execute(Settings.USER_URL, param, GetCouponHistoryResult.class);
		}

		@Override
		protected void onPostExecute(GetCouponHistoryResult result) {
			
			super.onPostExecute(result);
			if (pullCouponHistory != null)
				pullCouponHistory.onRefreshComplete();
			if (result!=null)
			{
			if (result.getCode() == 1)
			{
				if (result.getRecordlist()!=null&&result.getRecordlist().size()>0)
				{
					if (mPageNum == 0)
					{
						CouponArray.clear();
					}
					CouponArray.addAll(result.getRecordlist());
					couponAda.notifyDataSetChanged();
				}
            if (result.getHasmore() == 0) {
            	pullCouponHistory.setMode(Mode.PULL_FROM_START);
            } else {
            	pullCouponHistory.setMode(Mode.BOTH);
            }
			}else{
				if (result.getMessage()!=null)
				{
					if (result.getMessage() != null)
                        CommonUtils.showToast(ActivitySendHistory.this.getApplicationContext(),
                                result.getMessage());
                    if (result.getCode() == 95) {
                        CommonUtils.gotoLogin(ActivitySendHistory.this);
                    }
				}
			}
			}else{
				Toast.makeText(context, R.string.net_error,0).show();
			}
		}
	}
	
	private class getCouponHistory extends BaseAdapter
	{
		
		 private LayoutInflater inflater;
		 public getCouponHistory(Context context)
		 {
			 this.inflater = LayoutInflater.from(context);
		 }
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return CouponArray.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return CouponArray.get(position);
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
                convertView = inflater.inflate(R.layout.coupon_history_item,null);
                holder.tvName = (TextView)convertView.findViewById(R.id.tv_name);
                holder.tvPhone = (TextView)convertView.findViewById(R.id.tv_phone);
                holder.tvCount = (TextView)convertView.findViewById(R.id.tv_number);
                holder.tvTime = (TextView)convertView.findViewById(R.id.tv_time);
                convertView.setTag(holder);
            }else {
                holder = (HolderView)convertView.getTag();
            }
            
            holder.tvName.setText(CouponArray.get(position).getUsename());
            holder.tvCount.setText(CouponArray.get(position).getUsecount()+"");
            holder.tvPhone.setText(CouponArray.get(position).getUserphone());
            holder.tvTime.setText(CouponArray.get(position).getGettime());
			return convertView;
		}
	}
	
	private class HolderView
	{
		private TextView tvName;
		private TextView tvPhone;
		private TextView tvCount;
		private TextView tvTime;
	}
}
