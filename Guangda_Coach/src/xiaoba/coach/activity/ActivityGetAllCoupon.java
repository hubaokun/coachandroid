package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.grobas.view.PolygonImageView;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.ComplaintToMeResult;
import xiaoba.coach.net.result.GetCouponResult;
import xiaoba.coach.net.result.GetCouponResult.couponList;
import xiaoba.coach.utils.CommonUtils;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.listview.PullToRefreshBase;
import com.daoshun.lib.listview.PullToRefreshListView;
import com.daoshun.lib.listview.PullToRefreshBase.Mode;
import com.daoshun.lib.view.OnSingleClickListener;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityGetAllCoupon extends BaseActivity {
	private RelativeLayout rlNoConpou;
	private GridView gridCoupon;
	private ImageView imgBack;
	private int mPage;
	private List<couponList> CouponArray = new ArrayList<GetCouponResult.couponList>();
	private TextView tvHasChosed,tvCountHour,tvCountMoney;
	private Button btnPayCash;
	private CouponGridAdapter couponGridAda;
	private Context context;
	private List<Boolean> hasChoseCouponArray = new ArrayList<Boolean>();
	private int hasChosed = 0;
	private List<Integer> hasChosedArray = new ArrayList<Integer>();
	private TextView tvTicketNumber,tvHasPayTicketNumber;
	private LinearLayout llNotice;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_get_all_coupon);
	        context = this;
	        initView();
	        addListener();
			initData();
	    }
	 
	 private void initView()
	 {
		 rlNoConpou = (RelativeLayout)findViewById(R.id.rl_no_conpou);
		 imgBack = (ImageView)findViewById(R.id.img_title_left);
		 gridCoupon = (GridView)findViewById(R.id.grid_coupon);
		 tvHasChosed = (TextView)findViewById(R.id.tv_has_chosed_conpou);
		 tvCountHour = (TextView)findViewById(R.id.tv_count_hour);
		 tvCountMoney = (TextView)findViewById(R.id.tv_count);
		 btnPayCash = (Button)findViewById(R.id.btn_pay_cash);
		 tvTicketNumber = (TextView)findViewById(R.id.tv_ticket_number);
		 tvHasPayTicketNumber = (TextView)findViewById(R.id.tv_has_pay_ticket_number);
		 llNotice = (LinearLayout)findViewById(R.id.ll_notice);
	 }
	 
	 private void addListener()
	 {
		 imgBack.setOnClickListener(new OnSingleClickListener() {
				
				@Override
				public void doOnClick(View v) {
					// TODO Auto-generated method stub
					ActivityGetAllCoupon.this.finish();
				}
			});
		 
		 gridCoupon.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (CouponArray.get(position).getState()==1)  //未兑换
				{
				ImageView imgHasSelect = (ImageView)view.findViewById(R.id.img_selected);
				if (hasChoseCouponArray.get(position))  //为true,小巴券被选中
				{
					hasChoseCouponArray.set(position, false);
					imgHasSelect.setVisibility(View.GONE);
					hasChosed = hasChosed-1;
				}else{
					hasChoseCouponArray.set(position, true);
					imgHasSelect.setVisibility(View.VISIBLE);
					hasChosed = hasChosed+1;
				}
				
				int timeCount = 0;
				int MoneyCount = 0;
				for (int i = 0;i<hasChoseCouponArray.size();i++)
				{
					if (hasChoseCouponArray.get(i))
					{
						timeCount = timeCount+CouponArray.get(i).getValue();
						MoneyCount = MoneyCount+CouponArray.get(i).getValue()*CouponArray.get(i).getMoney_value();
					}
				}
				tvHasChosed.setText(String.valueOf(hasChosed));
				tvCountHour.setText(String.valueOf(timeCount));
				tvCountMoney.setText(String.valueOf(MoneyCount));
				}else{
					return;
				}
			}
		});
		 
		 btnPayCash.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				if (hasChosed != 0)
				{
				StringBuffer sbMoneyCount = new StringBuffer();
				for (int i =0 ;i<hasChoseCouponArray.size();i++)
				{
					if (hasChoseCouponArray.get(i))
					{
						int recordid = CouponArray.get(i).getRecordid();
						sbMoneyCount.append(recordid);
						sbMoneyCount.append(",");
						hasChosedArray.add(recordid);
					}
				}
				sbMoneyCount.deleteCharAt(sbMoneyCount.length()-1);
				String recordids = sbMoneyCount.toString();
				new setApplyCoupon(recordids).execute();
				}
				else{
					Toast.makeText(context, R.string.no_chosed,Toast.LENGTH_SHORT).show();
				}
			}
		});
	 }
	 
	 private void initData()
	 {
		 new getAllConpou().execute();
		 couponGridAda = new CouponGridAdapter(context);
		 gridCoupon.setAdapter(couponGridAda);
	 }
	 
	 private class getAllConpou extends AsyncTask<Void, Void, GetCouponResult>
	 {
		 JSONAccessor accessor = new JSONAccessor(ActivityGetAllCoupon.this.getApplicationContext(), JSONAccessor.METHOD_POST);
		 @Override
			protected void onPreExecute() {
				super.onPreExecute();
//				if (mLoadingDialog != null)
//					mLoadingDialog.show();
			}
		 
		 @Override
			protected GetCouponResult doInBackground(Void... params) {
				HashMap<String, Object> param = new BaseParam();
				param.put("action", "getAllCoupon");
				param.put("coachid",CoachApplication.getInstance().getUserInfo().getCoachid());//CoachApplication.getInstance().getUserInfo().getCoachid()
				return accessor.execute(Settings.CMY_URL, param,GetCouponResult.class);
			}
		 @Override
			protected void onPostExecute(GetCouponResult result) {
				super.onPostExecute(result);
//				if (mLoadingDialog != null && mLoadingDialog.isShowing())
//					mLoadingDialog.dismiss();
				if (result != null) {
					if (result.getCode() == 1) {
						if (result.getCouponlist() != null && result.getCouponlist().size() == 0) {
							// show no data page
							rlNoConpou.setVisibility(View.VISIBLE);
							llNotice.setVisibility(View.GONE);
						}else if (result.getCouponlist() != null && result.getCouponlist().size()>0)
						{
							llNotice.setVisibility(View.VISIBLE);
//							complaintAda.initDataSet(result.getComplaintlist());
							if (mPage == 0)
								CouponArray.clear();
							CouponArray.addAll(result.getCouponlist());
							for (int i =0;i<CouponArray.size();i++)
							{
								hasChoseCouponArray.add(false);
							}
							 int ticketCount = CouponArray.size();
							 int canPayTicket = 0;
							 tvTicketNumber.setText(ticketCount+"");
							 for(int i=0;i<ticketCount;i++)
							 {
								 if (CouponArray.get(i).getState()==1)
								 {
									 canPayTicket++;
								 }
							 }
							 tvHasPayTicketNumber.setText(canPayTicket+"");
							 couponGridAda.notifyDataSetChanged();
						}
					} else {
						if (result.getMessage() != null)
							CommonUtils.showToast(ActivityGetAllCoupon.this.getApplicationContext(), result.getMessage());
						if (result.getCode() == 95) {
							CommonUtils.gotoLogin(ActivityGetAllCoupon.this);
						}
					}
				} else {
					CommonUtils.showToast(ActivityGetAllCoupon.this.getApplicationContext(), getString(R.string.net_error));
				}
			}
	 }
	 
	 private class setApplyCoupon  extends AsyncTask<Void, Void, BaseResult>
	 {
		 private String RecordIds;
		 public setApplyCoupon(String recordids)
		 {
			 RecordIds = recordids;
		 }
		 JSONAccessor accessor = new JSONAccessor(ActivityGetAllCoupon.this.getApplicationContext(), JSONAccessor.METHOD_POST);
		 @Override
			protected void onPreExecute() {
				super.onPreExecute();
				if (mLoadingDialog != null)
					mLoadingDialog.show();
			}

		@Override
		protected BaseResult doInBackground(Void... params) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "ApplyCoupon");
			param.put("coachid",CoachApplication.getInstance().getUserInfo().getCoachid());//CoachApplication.getInstance().getUserInfo().getCoachid()
			param.put("recordids",RecordIds);
			return accessor.execute(Settings.CMY_URL, param,BaseResult.class);
		}
		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					if (result.getMessage() != null)
						CommonUtils.showToast(ActivityGetAllCoupon.this.getApplicationContext(), result.getMessage());
					for (int i=0;i<hasChosedArray.size();i++)
					{
						int index= hasChosedArray.get(i);
						for (int j = 0;j<CouponArray.size();j++)
						{
							if (CouponArray.get(j).getRecordid()==index)
							{
								CouponArray.get(j).setState(2);
							}
						}
					}
					//couponGridAda.notifyDataSetChanged();
					hasChosed = 0;
					tvHasChosed.setText("0");
					tvCountMoney.setText("0");
					tvCountHour.setText("0");
					hasChoseCouponArray.clear();
					for (int i =0;i<CouponArray.size();i++)
					{
						hasChoseCouponArray.add(false);
					}
					//couponGridAda.notifyDataSetChanged();
					hasChosedArray.clear();
					new getAllConpou().execute();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(ActivityGetAllCoupon.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ActivityGetAllCoupon.this);
					}
				}
			} else {
				CommonUtils.showToast(ActivityGetAllCoupon.this.getApplicationContext(), getString(R.string.net_error));
			}
		}
	 }
	 
	 private class CouponGridAdapter extends BaseAdapter
	 {
		 private LayoutInflater inflater;
		 public CouponGridAdapter(Context context)
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
                convertView = inflater.inflate(R.layout.item_coupon_list,null);
                holder.tvHour = (TextView)convertView.findViewById(R.id.tv_coupon_hour);
                holder.tvFrom = (TextView)convertView.findViewById(R.id.tv_from);
                holder.tvTime = (TextView)convertView.findViewById(R.id.tv_time);
                holder.imgSelected = (ImageView)convertView.findViewById(R.id.img_selected);
                holder.tvHasPay = (TextView)convertView.findViewById(R.id.tv_has_pay);
                holder.tvXiaoBaTicket = (TextView)convertView.findViewById(R.id.tv_xiaoba_ticket);
                convertView.setTag(holder);
            }else {
                holder = (HolderView)convertView.getTag();
            }
            if (hasChoseCouponArray.get(position))
            {
            	holder.imgSelected.setVisibility(View.VISIBLE);
            }else{
            	holder.imgSelected.setVisibility(View.GONE);
            }
            
            if (CouponArray.get(position).getState()==1) //未兑换
            {
            	holder.tvHasPay.setVisibility(View.GONE);
            	holder.tvHour.setTextColor(getResources().getColor(R.color.text_black));
            	holder.tvXiaoBaTicket.setTextColor(getResources().getColor(R.color.text_black));
            }else
            {
            	holder.tvHasPay.setVisibility(View.VISIBLE);
            	holder.tvHour.setTextColor(getResources().getColor(R.color.text_grey));
            	holder.tvXiaoBaTicket.setTextColor(getResources().getColor(R.color.text_grey));
            }
            String value = String.valueOf(CouponArray.get(position).getValue());
            
            holder.tvHour.setText(value+getResources().getString(R.string.hour));
            switch (CouponArray.get(position).getOwnertype()) {
			case 0:
				holder.tvFrom.setText(R.string.from_xiaoba);
				break;
			case 1:
				holder.tvFrom.setText(R.string.from_school);
				break;
			case 2:
				holder.tvFrom.setText(R.string.from_coach);
				break;
			default:
				break;
			}
            holder.tvTime.setText(CouponArray.get(position).getGettime());
			return convertView;
		}
		 
	 }
	 
	 private class HolderView
	 {
		 private TextView tvHour;
		 private TextView tvFrom;
		 private TextView tvTime;
		 private TextView tvHasPay;
		 private TextView tvXiaoBaTicket;
		 private ImageView imgSelected;
	 }
}
