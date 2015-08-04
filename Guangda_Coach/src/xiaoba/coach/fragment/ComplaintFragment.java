package xiaoba.coach.fragment;

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
import xiaoba.coach.activity.AccessActivity;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.ComplaintToMeResult;
import xiaoba.coach.net.result.ComplaintToMeResult.ComplaintList;
import xiaoba.coach.net.result.ComplaintToMeResult.ContentList;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.utils.ImageLoader;
import xiaoba.coach.views.StudentDetailView;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ComplaintFragment extends Fragment {
	private AccessActivity mActivity;
	LayoutInflater mInflater;
	private PullToRefreshListView mListView;
	private int mPage = 0;
	private ComplaintAdapter complaintAda;
	private List<ComplaintList> complaintArray = new ArrayList<ComplaintToMeResult.ComplaintList>();
	private Context context;
	private RelativeLayout rlLocation/* ,rlBackground */;
	private Display display;
	private StudentDetailView studentDetail;
	private RelativeLayout rlNodata;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (AccessActivity) activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_complaint, container, false);
		mInflater = inflater;
		context = mActivity;
		initView(view);
		addListeners();
		initData();
		return view;
	}
	
	private void initView(View view) {
		mListView = (PullToRefreshListView)view. findViewById(R.id.pull_list_complaint);
		rlLocation = (RelativeLayout)view. findViewById(R.id.rl_location);
		rlNodata = (RelativeLayout)view. findViewById(R.id.rl_nodata);
		/* rlBackground = (RelativeLayout)findViewById(R.id.rl_background); */
	}

	private void addListeners() {
		mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				mPage = 0;
				new getComplaintToMe().execute();

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// load one more page's data
				mPage++;
				new getComplaintToMe().execute();
			}
		});
	}

	private void initData() {
		mListView.setRefreshing();
		complaintAda = new ComplaintAdapter(context);
		mListView.setAdapter(complaintAda);
		display = mActivity.getWindowManager().getDefaultDisplay();
		studentDetail = new StudentDetailView(context, rlLocation, display, mActivity);
		// complaintAda = new ComplaintAdapter(getApplicationContext(),complaintArray);
	}

	private class getComplaintToMe extends AsyncTask<Void, Void, ComplaintToMeResult> {
		JSONAccessor accessor = new JSONAccessor(mActivity.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// if (mLoadingDialog != null)
			// mLoadingDialog.show();
		}

		@Override
		protected ComplaintToMeResult doInBackground(Void... params) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetComplaintToMy");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());// CoachApplication.getInstance().getUserInfo().getCoachid()
			param.put("pagenum", mPage);
			return accessor.execute(Settings.CMY_URL, param, ComplaintToMeResult.class);
		}

		@Override
		protected void onPostExecute(ComplaintToMeResult result) {
			super.onPostExecute(result);
			if (mListView != null)
				mListView.onRefreshComplete();
			// if (mLoadingDialog != null && mLoadingDialog.isShowing())
			// mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					if (result.getComplaintlist() != null && result.getComplaintlist().size() == 0) {
						// show no data page
						rlNodata.setVisibility(View.VISIBLE);
					} else if (result.getComplaintlist() != null && result.getComplaintlist().size() > 0) {
						// complaintAda.initDataSet(result.getComplaintlist());
						if (mPage == 0)
							complaintArray.clear();
						complaintArray.addAll(result.getComplaintlist());
						complaintAda.notifyDataSetChanged();

					}
					if (result.getHasmore() == 1) {
						mListView.setMode(Mode.BOTH);
					} else {
						mListView.setMode(Mode.PULL_FROM_START);
					}
				} else {
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

	private class ComplaintAdapter extends BaseAdapter {
		private ImageLoader imageLoader;
		private Context mContext;
		private LayoutInflater inflater;
		private int hasSolution = 0;

		public ComplaintAdapter(Context context) {
			this.mContext = context;
			imageLoader = new ImageLoader(context, R.drawable.portrait_test);
			this.inflater = LayoutInflater.from(context);
		}

		// public void initDataSet()
		// {
		// // mComplaintlist.clear();
		// // mComplaintlist = complaintlist;
		// notifyDataSetChanged();
		// }
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return complaintArray.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return complaintArray.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HolderView holder = null;
			if (convertView == null) {
				holder = new HolderView();
				convertView = inflater.inflate(R.layout.item_complaint_list, null);
				holder.imgStudentAvater = (PolygonImageView) convertView.findViewById(R.id.complaint_student_avater);
				holder.tvHasSolution = (TextView) convertView.findViewById(R.id.tv_has_solution);
				holder.tvComplaintTime = (TextView) convertView.findViewById(R.id.tv_complaint_time);
				holder.llContent = (LinearLayout) convertView.findViewById(R.id.ll_complaint_content);
				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}
			imageLoader.DisplayImage(complaintArray.get(position).getStudentavatar(), holder.imgStudentAvater);
			List<ContentList> mContentList = complaintArray.get(position).getContentlist();
			if (holder.llContent != null) {
				holder.llContent.removeAllViews();
			}
			for (int i = 0; i < mContentList.size(); i++) {
				View viewContent = inflater.inflate(R.layout.item_complaint_child, null);
				TextView tvContent = (TextView) viewContent.findViewById(R.id.tv_complaint_content);
				String contentTag = "#" + mContentList.get(i).getSet() + "#";
				String contentStr = mContentList.get(i).getContent();

				if (mContentList.get(i).getState() == 0) {
					tvContent.setText(Html.fromHtml("<font color=\"#20b478\">" + contentTag + "</font>" + "<font color=\"#252525\">" + contentStr + "</font>"));
					hasSolution++;
				} else {
					tvContent.setText(Html.fromHtml("<font color=\"#d2d2d2\">" + contentTag + contentStr + "</font>"));
				}
				holder.llContent.addView(viewContent);
			}
			String startTime = complaintArray.get(position).getStarttime();
			String endTime = complaintArray.get(position).getEndtime().split(" ")[1];
			if (hasSolution == 0) {
				holder.tvHasSolution.setVisibility(View.VISIBLE);
				holder.tvComplaintTime.setText(Html.fromHtml("<font color=\"#d2d2d2\">" + startTime + "~" + endTime + "</font>"));
			} else {
				holder.tvHasSolution.setVisibility(View.GONE);
				holder.tvComplaintTime.setText(Html.fromHtml("<font color=\"#252525\">" + startTime + "~" + endTime + "</font>"));
			}
			hasSolution = 0;
			holder.imgStudentAvater.setTag(position);
			holder.imgStudentAvater.setOnClickListener(new OnSingleClickListener() {

				@Override
				public void doOnClick(View v) {
					int Position = Integer.valueOf(v.getTag().toString());
					String avaterUrl = complaintArray.get(Position).getStudentavatar();
					String name = complaintArray.get(Position).getName();
					String phone = complaintArray.get(Position).getPhone();
					String number = complaintArray.get(Position).getStudentcardnum();
					float rank = complaintArray.get(Position).getScore();
					studentDetail.setShowDialog(phone, name, number, avaterUrl, rank);
				}
			});
			return convertView;
		}
	}

	private class HolderView {
		private TextView tvHasSolution;
		private PolygonImageView imgStudentAvater;
		private LinearLayout llContent;
		private TextView tvComplaintTime;
	}
}
