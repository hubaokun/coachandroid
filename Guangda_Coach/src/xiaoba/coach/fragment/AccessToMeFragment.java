package xiaoba.coach.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.activity.AccessActivity;
import xiaoba.coach.activity.SelfMaterialActivity;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.MyMyEvaluationResult;
import xiaoba.coach.net.result.MyMyEvaluationResult.EvaluationResult;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.utils.ImageLoader;
import xiaoba.coach.views.RoundImageView;

import net.grobas.view.PolygonImageView;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.listview.PullToRefreshBase;
import com.daoshun.lib.listview.PullToRefreshListView;
import com.daoshun.lib.listview.PullToRefreshBase.Mode;
import com.daoshun.lib.view.OnSingleClickListener;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AccessToMeFragment extends Fragment {
	private AccessActivity mActivity;
	LayoutInflater mInflater;
	private int mPage = 0;
	private PullToRefreshListView mListView;
	private List<EvaluationResult> EvaluationArray = new ArrayList<MyMyEvaluationResult.EvaluationResult>();
	private EvaluationToMeAdapter evaluationToMeAda; 
	private Context context;
	private RelativeLayout rlNodata;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (AccessActivity) activity;
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_access, container, false);
		mInflater = inflater;
		context = mActivity;
		initView(view);
		addListeners();
		initData();
		return view;
	}
	private void initView(View view)
	{
		mListView = (PullToRefreshListView)view.findViewById(R.id.pull_list_access);
		rlNodata = (RelativeLayout)view.findViewById(R.id.rl_nodata);
	}
	
	private void addListeners()
	{
		mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				mPage = 0;
				new getMyAccess().execute();

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// load one more page's data
				mPage++;
				new getMyAccess().execute();
			}
		});
	}
	
	private void initData()
	{
		mListView.setRefreshing();
		evaluationToMeAda = new EvaluationToMeAdapter(context);
		mListView.setAdapter(evaluationToMeAda);
	}
	
	private class getMyAccess extends AsyncTask<Void, Void, MyMyEvaluationResult>
	{
		JSONAccessor accessor = new JSONAccessor(mActivity.getApplicationContext(), JSONAccessor.METHOD_POST);
		
		@Override
		protected MyMyEvaluationResult doInBackground(Void... params) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetEvaluationToMy");
			param.put("coachid",CoachApplication.getInstance().getUserInfo().getCoachid());//CoachApplication.getInstance().getUserInfo().getCoachid()
			param.put("pagenum", mPage);
			return accessor.execute(Settings.CMY_URL, param,MyMyEvaluationResult.class);
		}
		@Override
		protected void onPostExecute(MyMyEvaluationResult result) {
			super.onPostExecute(result);
			if (mListView != null)
				mListView.onRefreshComplete();
			if (result!=null)
			{
				if (result.getCode() == 1) {
					if (result.getEvaluationlist() != null && result.getEvaluationlist().size() == 0) {
						// show no data page
						rlNodata.setVisibility(View.VISIBLE);
					}else if (result.getEvaluationlist() != null && result.getEvaluationlist().size()>0)
					{
						if (mPage == 0)
							EvaluationArray.clear();
						EvaluationArray.addAll(result.getEvaluationlist());
						
						evaluationToMeAda.notifyDataSetChanged();
					}
					
					if (result.getHasmore() == 1) {
						mListView.setMode(Mode.BOTH);
					} else {
						mListView.setMode(Mode.PULL_FROM_START);
					}
				}
				else {
					if (result.getMessage() != null)
						CommonUtils.showToast(mActivity.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(mActivity);
					}
				}
			} else {
				CommonUtils.showToast(mActivity.getApplicationContext(), getString(R.string.net_error));
			}
		}
	}
	
	private class EvaluationToMeAdapter extends BaseAdapter
	{
		 private ImageLoader imageLoader;
		 private Context mContext;
		 private LayoutInflater inflater;
		public EvaluationToMeAdapter(Context context)
		{
			this.mContext = context;
			 imageLoader = new ImageLoader(context,R.drawable.portrait_test);
			 this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return EvaluationArray.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return EvaluationArray.get(position);
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
			if (convertView == null)
			{
				holder = new HolderView();
				convertView = inflater.inflate(R.layout.item_fragment_access_to_me,null);
				holder.imgStudentAvater = (PolygonImageView)convertView.findViewById(R.id.img_student_avater);
				holder.ratingRank = (RatingBar)convertView.findViewById(R.id.ratingBar);
				holder.tvAccessContent = (TextView)convertView.findViewById(R.id.tv_access_to_me_content);
				holder.tvTime = (TextView)convertView.findViewById(R.id.tv_access_to_me_time);
				 convertView.setTag(holder);
            }else {
                holder = (HolderView)convertView.getTag();
            }
			imageLoader.DisplayImage(EvaluationArray.get(position).getStudentavatar(),holder.imgStudentAvater);
			if (!"".equals(EvaluationArray.get(position).getContent()))
			{
				holder.tvAccessContent.setVisibility(View.VISIBLE);
				holder.tvAccessContent.setText(EvaluationArray.get(position).getContent());
			}else{
				holder.tvAccessContent.setVisibility(View.GONE);
			}
			
			holder.ratingRank.setRating(EvaluationArray.get(position).getScore());
			String startTime = EvaluationArray.get(position).getStarttime();
			String endTime = EvaluationArray.get(position).getEndtime().split(" ")[1];
			holder.tvTime.setText(startTime+"~"+endTime);
			holder.imgStudentAvater.setTag(position);
			holder.imgStudentAvater.setOnClickListener(new OnSingleClickListener() {
				
				@Override
				public void doOnClick(View v) {
					int Position = Integer.valueOf(v.getTag().toString());
					String avaterUrl = EvaluationArray.get(Position).getStudentavatar();
					String name = EvaluationArray.get(Position).getName();
					String phone = EvaluationArray.get(Position).getPhone();
					String number = EvaluationArray.get(Position).getStudentcardnum();
					float rank = EvaluationArray.get(Position).getScore();
					mActivity.studentDetail.setShowDialog(phone, name, number, avaterUrl, rank);
				}
			});
			return convertView;
		}
	}
	
	private class HolderView
	{
		private RatingBar ratingRank;
		private TextView tvAccessContent;
		private PolygonImageView imgStudentAvater;
		private TextView tvTime;
	}
}
