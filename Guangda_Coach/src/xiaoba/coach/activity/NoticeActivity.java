package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.interfaces.DialogConfirmListener;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.getNoticeResult;
import xiaoba.coach.net.result.getNoticeResult.NoticeList;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.utils.DialogUtil;
import xiaoba.coach.utils.OnDoubleClick;
import xiaoba.coach.views.NoticeDetailView;

import net.grobas.view.PolygonImageView;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.listview.PullToRefreshBase;
import com.daoshun.lib.listview.PullToRefreshListView;
import com.daoshun.lib.listview.PullToRefreshBase.Mode;
import com.daoshun.lib.view.OnSingleClickListener;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NoticeActivity extends BaseActivity {
	private PullToRefreshListView mListView;
	private ImageView imgBack;
	private int mPage = 0;
	private List<NoticeList> NoticeArray = new ArrayList<getNoticeResult.NoticeList>();
	private NoticeAdapter noticeAda;
	private RelativeLayout mNoData;
	private NoticeDetailView noticeDetail;
	private RelativeLayout rlLocation;
	private Display display;
	private Dialog mDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);
		initView();
		addListener();
		initData();
	}

	private void initView() {
		mListView = (PullToRefreshListView) findViewById(R.id.pull_list_notice);
		imgBack = (ImageView) findViewById(R.id.img_title_left);
		mNoData = (RelativeLayout) findViewById(R.id.rl_nodata);
		rlLocation = (RelativeLayout) findViewById(R.id.rl_location);
	}

	private void addListener() {
		imgBack.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				NoticeActivity.this.finish();
			}
		});
		mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				mPage = 0;
				new getNotice().execute();

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// load one more page's data
				mPage++;
				new getNotice().execute();
			}
		});
	}

	private void initData() {
		mListView.setRefreshing();
		noticeAda = new NoticeAdapter(getApplicationContext());
		mListView.setAdapter(noticeAda);
		display = getWindowManager().getDefaultDisplay();
		noticeDetail = new NoticeDetailView(getApplicationContext(), rlLocation, display, this);
	}

	private class getNotice extends AsyncTask<Void, Void, getNoticeResult> {
		JSONAccessor accessor = new JSONAccessor(NoticeActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected getNoticeResult doInBackground(Void... params) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetNotices");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());// CoachApplication.getInstance().getUserInfo().getCoachid()
			param.put("pagenum", mPage);
			return accessor.execute(Settings.CMY_URL, param, getNoticeResult.class);
		}

		@Override
		protected void onPostExecute(getNoticeResult result) {
			super.onPostExecute(result);
			if (mListView != null)
				mListView.onRefreshComplete();
			if (result != null) {
				if (result.getCode() == 1) {
					if (result.getDatalist() != null && result.getDatalist().size() == 0) {
						// show no data page
						mNoData.setVisibility(View.VISIBLE);
					} else if (result.getDatalist() != null && result.getDatalist().size() > 0) {
						// complaintAda.initDataSet(result.getComplaintlist());
						if (mPage == 0)
							NoticeArray.clear();
						NoticeArray.addAll(result.getDatalist());
						noticeAda.notifyDataSetChanged();
					}
					if (result.getHasmore() == 1) {
						mListView.setMode(Mode.BOTH);
					} else {
						mListView.setMode(Mode.PULL_FROM_START);
					}
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(NoticeActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(NoticeActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(NoticeActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}
	}

	private class DelNotice extends AsyncTask<Void, Void, BaseResult> {
		private int noticeId;
		private int Position;

		public DelNotice(int noticeid, int position) {
			noticeId = noticeid;
			Position = position;
		}

		JSONAccessor accessor = new JSONAccessor(NoticeActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected BaseResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "DelNotice");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());// CoachApplication.getInstance().getUserInfo().getCoachid()
			param.put("noticeid", noticeId);
			return accessor.execute(Settings.CMY_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.getCode() == 1) {
					NoticeArray.remove(Position);
					noticeAda.notifyDataSetChanged();
				}
				CommonUtils.showToast(NoticeActivity.this.getApplicationContext(), result.getMessage());
				if (result.getCode() == 95) {
					CommonUtils.gotoLogin(NoticeActivity.this);
				}
			} else {
				CommonUtils.showToast(NoticeActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}
	}

	private class NoticeAdapter extends BaseAdapter {
		private Context mContext;
		private LayoutInflater inflater;

		public NoticeAdapter(Context context) {
			mContext = context;
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return NoticeArray.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return NoticeArray.get(position);
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
				convertView = inflater.inflate(R.layout.item_activity_notice, null);
				holder.tvNoticeContent = (TextView) convertView.findViewById(R.id.tv_notice_content);
				holder.tvNoticeTime = (TextView) convertView.findViewById(R.id.tv_notice_time);
				holder.tvNoticeContent.setOnClickListener(new OnSingleClickListener() {

					@Override
					public void doOnClick(View v) {
						int Position = Integer.valueOf(v.getTag().toString());
						int noticeId = NoticeArray.get(Position).getNoticeid();
						String content = NoticeArray.get(Position).getContent().trim();
						noticeDetail.setNoticeContent(content);
						new ReadNotice(noticeId, Position).execute();
					}
				});
				holder.tvNoticeContent.setOnLongClickListener(new OnLongClickListener() {

					@Override
					public boolean onLongClick(final View v) {
						DialogUtil dUtil = new DialogUtil(new DialogConfirmListener() {
							int Position = Integer.valueOf(v.getTag().toString());
							int noticeId = NoticeArray.get(Position).getNoticeid();

							@Override
							public void doConfirm(String str) {
								new DelNotice(noticeId, Position).execute();
								if (mDialog != null)
									mDialog.dismiss();
							}

							@Override
							public void doCancel() {
								if (mDialog != null)
									mDialog.dismiss();
							}
						});
						mDialog = dUtil.CallConfirmDialog("删除通知","","",NoticeActivity.this, mDialog);
						return true;
					}
				});
				convertView.setTag(holder);
			} else {
				holder = (HolderView) convertView.getTag();
			}

			holder.tvNoticeContent.setText(NoticeArray.get(position).getContent().trim());
			holder.tvNoticeTime.setText(NoticeArray.get(position).getAddtime());
			holder.tvNoticeContent.setTag(position);

			return convertView;
		}

		private class HolderView {
			private TextView tvNoticeContent;
			private TextView tvNoticeTime;
		}
	}
	
	private class ReadNotice extends AsyncTask<Void, Void, BaseResult> {
		private int noticeId;
		private int Position;

		public ReadNotice(int noticeid, int position) {
			noticeId = noticeid;
			Position = position;
		}

		JSONAccessor accessor = new JSONAccessor(NoticeActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected BaseResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "READNOTICE");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());// CoachApplication.getInstance().getUserInfo().getCoachid()
			param.put("noticeid", noticeId);
			return accessor.execute(Settings.CMY_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.getCode() == 1) {
					
				}else{
					if (result.getMessage()!=null)
					{
						CommonUtils.showToast(NoticeActivity.this.getApplicationContext(), result.getMessage());
					}
				}
				if (result.getCode() == 95) {
					CommonUtils.gotoLogin(NoticeActivity.this);
				}
			} else {
				CommonUtils.showToast(NoticeActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}
	}
}
