package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.grobas.view.PolygonImageView;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.listview.PullToRefreshBase;
import com.daoshun.lib.listview.PullToRefreshListView;
import com.daoshun.lib.listview.PullToRefreshBase.Mode;
import com.daoshun.lib.view.OnSingleClickListener;
import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.ComplaintToMeResult;
import xiaoba.coach.net.result.GetRecommendResult;
import xiaoba.coach.net.result.GetRecommendResult.Recommend;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.views.ShareDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityShareRecord<T> extends BaseActivity {
	private Context context;
	private TextView tvTitle;
	private ImageView imgBack;
	private PullToRefreshListView lvRecord;
	private int mPage;
	private List<Recommend> recommedList = new ArrayList<Recommend>();
	private RecordAdapter recordAda;
	private TextView tvCoachCount;
//	private TextView tvMoneyCount;
	private ImageView imgNoRecord;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_share_list);
	        context = this;
	        initView();
	        initData();
	        addListener();
	    }
	private void initView() {
		// TODO Auto-generated method stub
		tvTitle = (TextView)findViewById(R.id.title);
		imgBack = (ImageView)findViewById(R.id.title_back_img);
		lvRecord = (PullToRefreshListView)findViewById(R.id.lv_share);
		tvCoachCount = (TextView)findViewById(R.id.tv_share_count);
//		tvMoneyCount = (TextView)findViewById(R.id.tv_share_mon_count);
		imgNoRecord = (ImageView)findViewById(R.id.img_no_record);
	}
	private void initData() {
		// TODO Auto-generated method stub
		recordAda = new RecordAdapter();
		lvRecord.setAdapter(recordAda);
		lvRecord.setRefreshing();
		tvTitle.setText("邀请记录");
		tvTitle.setTextColor(getResources().getColor(R.color.text_black));
		imgBack.setImageResource(R.drawable.back_arrow);
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
		
		lvRecord.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				mPage = 1;
				new getRecommed().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// load one more page's data
				mPage++;
				new getRecommed().execute();
			}
		});
	}
	
	private class getRecommed extends AsyncTask<Void, Void, GetRecommendResult> {
		JSONAccessor accessor = new JSONAccessor(getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// if (mLoadingDialog != null)
			// mLoadingDialog.show();
		}
		
		@Override
		protected GetRecommendResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "CGETRECOMMENDLIST");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());// CoachApplication.getInstance().getUserInfo().getCoachid()
			param.put("pagenum", mPage);
			return accessor.execute(Settings.CRECOMM, param, GetRecommendResult.class);
		}
		
		@Override
		protected void onPostExecute(GetRecommendResult result) {
			super.onPostExecute(result);
			if (lvRecord != null)
				lvRecord.onRefreshComplete();
			// if (mLoadingDialog != null && mLoadingDialog.isShowing())
			// mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					if (result.getRecommendList() != null && result.getRecommendList().size() == 0) {
						// show no data page
						//rlNodata.setVisibility(View.VISIBLE);
					} else if (result.getRecommendList() != null && result.getRecommendList().size() > 0) {
						if (result.getRflag() == 1)
						{
							lvRecord.setVisibility(View.VISIBLE);
							imgNoRecord.setVisibility(View.GONE);
						tvCoachCount.setText(""+result.getTotal());
//						tvMoneyCount.setText(""+result.getTotalreward());
						// complaintAda.initDataSet(result.getComplaintlist());
						if (mPage == 1)
						{
							recommedList.clear();
						}
						recommedList.addAll(result.getRecommendList());
						recordAda.notifyDataSetChanged();
						}else{
							lvRecord.setVisibility(View.GONE);
							imgNoRecord.setVisibility(View.VISIBLE);
						}
					}
					if (result.getHasmore() == 1) {
						lvRecord.setMode(Mode.BOTH);
					} else {
						lvRecord.setMode(Mode.PULL_FROM_START);
					}
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ActivityShareRecord.this);
					}
				}
			} else {
				CommonUtils.showToast(getApplicationContext(), getString(R.string.net_error));
			}
		}
		
	}
	
	private class RecordAdapter extends BaseAdapter
	{
		private LayoutInflater inflater;
		public RecordAdapter()
		{
			this.inflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return recommedList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return recommedList.get(position);
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
			if (convertView == null) {
				holder = new HolderView();
				convertView = inflater.inflate(R.layout.record_list_item, null);
				holder.tvRelaName = (TextView) convertView.findViewById(R.id.tv_coach_name);
				holder.tvStatePass = (TextView) convertView.findViewById(R.id.tv_state_pass);
				holder.tvStateOpen = (TextView)convertView.findViewById(R.id.tv_state_open);
//				holder.tvReward = (TextView) convertView.findViewById(R.id.tv_reward);
				holder.tvTime = (TextView) convertView.findViewById(R.id.tv_input_date);
				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}
			if (!TextUtils.isEmpty(recommedList.get(position).getInvitedpeoplename()))
			{
				holder.tvRelaName.setText(recommedList.get(position).getInvitedpeoplename());
			}else{
				String phone = recommedList.get(position).getInvitedpeopletelphone();
				if (!TextUtils.isEmpty(phone))
				{
				holder.tvRelaName.setText(phone.substring(0, 3)+"****"+phone.substring(7,phone.length()));
				}else{
					holder.tvRelaName.setText("");
				}
			}
			if (recommedList.get(position).getIschecked()==1)  //2是认证，0,1,3是未认证
			{
				holder.tvStatePass.setText("已认证");
			}else{
				holder.tvStatePass.setText("未认证");
			}
			
			if (recommedList.get(position).getIsOrder() == 1)
			{
				holder.tvStateOpen.setText("已开单");
			}else{
				holder.tvStateOpen.setText("未开单");
			}
			
			//holder.tvReward.setText(""+recommedList.get(position).getReward());
			
			holder.tvTime.setText(recommedList.get(position).getAddtime().split(" ")[0]);
			
			return convertView;
		}
	}
	
	private class HolderView
	{
		private TextView tvRelaName;
		private TextView tvStatePass;
//		private TextView tvReward;
		private TextView tvTime;
		private TextView tvStateOpen;
	}
	
}
