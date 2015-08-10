package xiaoba.coach.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.activity.CheckStudentActivity;
import xiaoba.coach.activity.CommentActivity_;
import xiaoba.coach.activity.HistoryOrderActivity_;
import xiaoba.coach.activity.HomeActivity;
import xiaoba.coach.common.IntentsParamNames;
import xiaoba.coach.common.Settings;
import xiaoba.coach.interfaces.DialogConfirmListener;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.StudentInfo;
import xiaoba.coach.module.TaskInfo;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.UndoneOrderListResult;
import xiaoba.coach.utils.CacheImageLoader;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.utils.DialogUtil;
import xiaoba.coach.views.LoadingDialog;

import net.grobas.view.PolygonImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.listview.PullToRefreshBase;
import com.daoshun.lib.listview.PullToRefreshBase.Mode;
import com.daoshun.lib.listview.PullToRefreshPinnedSectionListView;
import com.daoshun.lib.util.DensityUtils;
import com.daoshun.lib.util.ImageLoader.OnLoadListener;
import com.daoshun.lib.view.OnSingleClickListener;
import com.hb.views.PinnedSectionListView.PinnedSectionListAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/*
 * show undone job order
 */
public class JobOrderFragment extends Fragment {
	public static PullToRefreshPinnedSectionListView mListView;
	HomeActivity mActivity;
	LinearLayout mNoData;
	LayoutInflater mInflater;
	JobOrderAdapter mAdapter;
	TextView mHis; // go to page displaying history orders
	int mShowHidePosition = -1;
	int mPage; // indicate the pages'number loaded
	int mSelection; // mark the selection of the listview
	ArrayList<TaskInfo> list = new ArrayList<TaskInfo>(); // list data container
	private static final int[] COLORS = new int[] { R.color.green_light, R.color.orange_light, R.color.blue_light, R.color.red_light };
	private String mDateToday; // format"2015-05-21"
	private final static int COMMENT_REQUEST_CODE = 1002;
	CacheImageLoader mImageLoader;
	private LocationClient locationClient;
	private MyLocationListenner myListener;
	private String latitude, longitude, detail;
	LoadingDialog mLoadingDialog;
	
	
	public void getPosition() {
		locationClient = new LocationClient(mActivity.getApplicationContext());
		myListener = new MyLocationListenner();
		locationClient.registerLocationListener(myListener);
		if (locationClient != null) {
			setLocationOption();
			locationClient.start();
			locationClient.requestLocation();
		} else {
			Log.d("TAG", "locClient is null or not started");
		}
	}

	// 设置相关参数
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(2000); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		option.setIsNeedAddress(true);
		locationClient.setLocOption(option);
	}

	/**
	 * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中
	 */
	
	boolean hasLoc = false;
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (locationClient != null)
				locationClient.stop();
			if (location == null)
				return;
			latitude = location.getLatitude() + "";
			longitude = location.getLongitude() + "";
			detail = location.getAddrStr();
			hasLoc = true;
			stopLocClient();
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopLocClient();
	}
	
	private void stopLocClient() {
		if (locationClient != null && locationClient.isStarted()) {
			locationClient.stop();
			locationClient = null;
		}
	}

	@Override
	public void onStop() {
		stopLocClient();
		super.onStop();
	}
	
	
	public PullToRefreshPinnedSectionListView getmListView() {
		return mListView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (HomeActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_job_order, container, false);
		mInflater = inflater;
		mImageLoader = new CacheImageLoader(mActivity);
		mLoadingDialog = new LoadingDialog(mActivity);
		initView(view);
		addListeners();
		initData();
//		getPosition();

		return view;
	}

	private void initView(View view) {
		mListView = (PullToRefreshPinnedSectionListView) view.findViewById(R.id.listview);
		mNoData = (LinearLayout) view.findViewById(R.id.job_order_no_data);
		mHis = (TextView) view.findViewById(R.id.title_right_text);

		if (mListView.getRefreshableView().isFastScrollEnabled()) {
			mAdapter = new FastScrollAdapter();
			mListView.setAdapter(mAdapter);
		} else {
			mAdapter = new JobOrderAdapter();
			mListView.setAdapter(mAdapter);
		}

		// ((PinnedSectionListView)mListView.getRefreshableView()).setShadowVisible(false);
	}

	private void initData() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mDateToday = sdf.format(date);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mListView.setRefreshing();
	}

	private void addListeners() {
		mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				mPage = 0; 
				//CommonUtils.showToast(mActivity.getApplicationContext(), CoachApplication.getInstance().getUserInfo().getCoachid()+"");
				new GetNowTask().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				// load one more page's data
				mPage++;
				new GetNowTask().execute();
			}
		});

		mHis.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				startActivity(new Intent(mActivity, HistoryOrderActivity_.class));
			}
		});
	}

	/*
	 * get the list of undone job orders
	 */
	private class GetNowTask extends AsyncTask<Void, Void, UndoneOrderListResult> {
		JSONAccessor accessor = new JSONAccessor(mActivity.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected UndoneOrderListResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			HashMap<String, Object> param = new BaseParam();
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("pagenum", mPage);
			param.put("action", "GetNowTask");
			return accessor.execute(Settings.TASK_URL, param,  UndoneOrderListResult.class);
		}

		@Override
		protected void onPostExecute(UndoneOrderListResult result) {
			super.onPostExecute(result);
			if (mListView != null)
				mListView.onRefreshComplete();
			if (result != null) {
				if (result.getCode() == 1) {
					if (result.getTasklist() != null && result.getTasklist().size() == 0) {
						// show no data page
						mNoData.setVisibility(View.VISIBLE);
					} else if (result.getTasklist() != null && result.getTasklist().size() > 0) {
						if (mNoData.getVisibility() == View.VISIBLE)
							mNoData.setVisibility(View.INVISIBLE);
						if (mPage == 0)
							list.clear();
						list.addAll(result.getTasklist());
						mAdapter.initDataSet();
						mAdapter.notifyDataSetChanged();
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
				CommonUtils.showToast(mActivity.getApplicationContext(), mActivity.getString(R.string.net_error));
			}
		}

	}

	/*
	 * 
	 */
	HashMap<String, String> dateMap;

	private class JobOrderAdapter extends BaseAdapter implements PinnedSectionListAdapter {
		List<Item> itemlist;
		DisplayImageOptions options;
		ImageSize mImageSize;

		public JobOrderAdapter() {
			initDataSet();

			// 显示图片的配置
			options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

			mImageSize = new ImageSize(150, 150);
		}

		public void initDataSet() {
			int sectionsNumber = 0;
			itemlist = new ArrayList<JobOrderFragment.Item>();
			int sectionPosition = 0, listPosition = 0;
			dateMap = new HashMap<String, String>(); // save the dates in the list
			/*
			 * iterates the list to get sectionNumber
			 */
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getStart_time().contains(" ")) {
					String sec = list.get(i).getStart_time().substring(0, list.get(i).getStart_time().indexOf(" "));
					if (!dateMap.containsValue(sec)) {
						dateMap.put("" + sectionsNumber, sec);
						sectionsNumber++;
					}
				}
			}
			/*
			 * create a new list with sections in it
			 */
			for (int i = 0; i < sectionsNumber; i++) {
				// initial section and section's value
				Item section = new Item(Item.SECTION, dateMap.get("" + i));
				section.sectionPosition = sectionPosition;
				section.listPosition = listPosition++;
				onSectionAdded(section, sectionPosition);
				itemlist.add(section);

				/*
				 * iterate the list(maintain data without section) to set item in itemlist
				 */
				String startTime, secstr;
				for (int j = 0; j < list.size(); j++) {
					/*
					 * except the null data
					 */
					if (list.get(j) != null && list.get(j).getStart_time() != null) {
					} else {
						continue;
					}
					/*
					 * add item
					 */
					startTime = list.get(j).getStart_time().substring(0, list.get(j).getStart_time().indexOf(" "));
					secstr = dateMap.get("" + i);
					if (startTime == null)
						continue;
					if (secstr == null)
						continue;
					// if (!list.get(j).getStart_time().contains(" "))
					// continue;
					if (startTime.compareTo(secstr) < 0) {
						continue;
					} else if (startTime.equals(secstr)) {
						Item item = new Item(Item.ITEM, "");
						item.sectionPosition = sectionPosition;
						item.listPosition = listPosition++;
						itemlist.add(item);
					} else if (startTime.compareTo(secstr) > 0) {
						break;
					}
				}

				sectionPosition++;
			}
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public int getItemViewType(int position) {
			return getItem(position).type;
		}

		protected void prepareSections(int sectionsNumber) {
		}

		protected void onSectionAdded(Item section, int sectionPosition) {
		}

		@Override
		public int getCount() {
			return itemlist.size();
		}

		@Override
		public Item getItem(int pos) {
			return itemlist.get(pos);
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		int dataPosition = 0;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (position > 0)
				dataPosition = position - getItem(position).sectionPosition - 1;
			else {
			}
			if (getItem(position).type == Item.SECTION) {
				convertView = mInflater.inflate(R.layout.item_section, null);
				TextView date = (TextView) convertView.findViewById(R.id.date);
				convertView.setTag("" + position);
				convertView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(mActivity.getApplicationContext(), 33.5f)));
				if (getItem(position).text.contains(mDateToday)) {
					date.setBackgroundColor(Color.parseColor("#1c1c1c"));
					date.setTextColor(Color.parseColor("#ffffff"));
				} else {
					date.setBackgroundColor(Color.parseColor("#f3f3f3"));
					date.setTextColor(Color.parseColor("#1c1c1c"));
				}
				String weekday = getWeekDay(getItem(position).text);
				if (weekday == null) {
					weekday = "";
				}
				String diff = getOrderDiff(getItem(position).text);
				if (diff == null) {
					diff = "";
				}
				date.setText(diff + "  " + getItem(position).text + "  " + weekday);
			} else {
				convertView = mInflater.inflate(R.layout.item_job_order, null);
				LinearLayout mainPart = (LinearLayout) convertView.findViewById(R.id.jo_main_part);
				RelativeLayout hidePart = (RelativeLayout) convertView.findViewById(R.id.hide_content);
				ImageView arrow = (ImageView) convertView.findViewById(R.id.jo_item_arrow);
				mainPart.setOnClickListener(new ShowHideListener(hidePart, arrow, position));
				TextView location = (TextView) convertView.findViewById(R.id.loc_detail);
				TextView startTime = (TextView) convertView.findViewById(R.id.start_time);
				TextView endTime = (TextView) convertView.findViewById(R.id.end_time);
				TextView stuName = (TextView) convertView.findViewById(R.id.jo_stuname);
				TextView stuTel = (TextView) convertView.findViewById(R.id.jo_stutel);
				TextView stuCard = (TextView) convertView.findViewById(R.id.jo_stucard);
				TextView price = (TextView) convertView.findViewById(R.id.joborder_price);
				Button btn = (Button) convertView.findViewById(R.id.jo_stubtn);
				LinearLayout tousu = (LinearLayout) convertView.findViewById(R.id.jo_tousu);
				LinearLayout contact = (LinearLayout) convertView.findViewById(R.id.jo_contact);
				TextView orderSuc = (TextView) convertView.findViewById(R.id.del_order);
				LinearLayout belowOC = (LinearLayout) convertView.findViewById(R.id.below_del_order);
				PolygonImageView portrait = (PolygonImageView) convertView.findViewById(R.id.potrait);
				ImageView bigPortrait = (ImageView) convertView.findViewById(R.id.big_portrait);
				LinearLayout llCancel = (LinearLayout)convertView.findViewById(R.id.ll_order_cancel);
				Button btnNotCancel = (Button)convertView.findViewById(R.id.btn_not_cancel);
				Button btnCancel = (Button)convertView.findViewById(R.id.btn_cancel);
				ImageView imgPayType = (ImageView)convertView.findViewById(R.id.img_paytype);
				btnNotCancel.setTag(dataPosition);
				btnCancel.setTag(dataPosition);
				TaskInfo info = list.get(dataPosition); // item data

				/*
				 * judge whether show the hide part
				 */
				if (info.getAgreecancel()==0)// 0是订单要取消，1是不取消
				{
					mainPart.setBackgroundColor(getResources().getColor(R.color.job_back_yellow));
					llCancel.setVisibility(View.VISIBLE);
					btn.setVisibility(View.GONE);
					btnCancel.setOnClickListener(new OnSingleClickListener() {
						
						@Override
						public void doOnClick(View v) {
							// TODO Auto-generated method stub
							new SetOrderCancel(0,list.get(Integer.parseInt(v.getTag().toString())).getOrderid()).execute();
						}
					});
					
					btnNotCancel.setOnClickListener(new OnSingleClickListener() {
						
						@Override
						public void doOnClick(View v) {
							// TODO Auto-generated method stub
							new SetOrderCancel(1,list.get(Integer.parseInt(v.getTag().toString())).getOrderid()).execute();
						}
					});
				}else{
					llCancel.setVisibility(View.GONE);
					btn.setVisibility(View.VISIBLE);
				}
				
				if (position == mShowHidePosition) {
					hidePart.setVisibility(View.VISIBLE);
					arrow.setImageResource(R.drawable.arrow_blue);
				} else {
					if (hidePart.getVisibility() == View.VISIBLE)
						hidePart.setVisibility(View.GONE);
					arrow.setImageResource(R.drawable.arrow_grey);
				}
				
			

				if (info == null)
					return convertView;

				/*
				 * contact student
				 */
				if (info.getStudentinfo() != null && info.getStudentinfo().getPhone() != null) {
					contact.setOnClickListener(new ContactListener(info.getStudentinfo().getPhone()));
					tousu.setOnClickListener(new MessageListener(info.getStudentinfo().getPhone()));

				} else {
					contact.setOnClickListener(new ContactListener(null));
					tousu.setOnClickListener(new MessageListener(null));
				}

				/*
				 * set price
				 */
				if (info.getTotal() != null) {
					if (info.getTotal().contains(".")) {
						price.setText(info.getTotal().substring(0, info.getTotal().indexOf(".")) + "元");
					}
				} else {
					price.setText("");
				}
				
				switch (info.getPaytype()) {
				case Settings.MONEY:
					imgPayType.setVisibility(View.VISIBLE);
					imgPayType.setImageResource(R.drawable.money);
					break;
				case Settings.COIN:
					imgPayType.setVisibility(View.VISIBLE);
					imgPayType.setImageResource(R.drawable.coin);
					break;
				case Settings.COUPON:
					imgPayType.setVisibility(View.VISIBLE);
					imgPayType.setImageResource(R.drawable.ticket);
					break;
				case Settings.WITHPAYTYPE:
					imgPayType.setVisibility(View.GONE);
				default:
					break;
				}

				/*
				 * set address
				 */
				if (info.getDetail() != null)
					location.setText(info.getDetail());
				else
					location.setText("");
				/*
				 * set start time
				 */
				if (info.getStart_time() != null) {
					setSpannableTime(startTime, getTime(info.getStart_time()));
				} else {
					startTime.setText("");
				}
				/*
				 * set end time
				 */
				if (info.getEnd_time() != null) {
					setSpannableTime(endTime, getTime(info.getEnd_time()));
				} else {
					endTime.setText("");
				}
				/*
				 * check coach state (change time's font)
				 */
				switch (info.getState()) {
				case 0:
					startTime.setTextColor(Color.parseColor("#1c1c1c"));
					endTime.setTextColor(Color.parseColor("#1c1c1c"));
					setGreenBtn(btn);
					break;

				case 1:
					startTime.setTextColor(Color.parseColor("#e0483e"));
					endTime.setTextColor(Color.parseColor("#e0483e"));
					setGreenBtn(btn);
					break;

				case 2:
					startTime.setTextColor(Color.parseColor("#e0483e"));
					endTime.setTextColor(Color.parseColor("#e0483e"));
					setGreenBtn(btn);
					break;

				case 3:
					startTime.setTextColor(Color.parseColor("#1c1c1c"));
					endTime.setTextColor(Color.parseColor("#1c1c1c"));
					setBlueBtn(btn);
					break;

				default:
					break;
				}

				/*
				 * set btn click listener
				 */
				btn.setOnClickListener(new ConfrimOnListener(info, btn, startTime, endTime, dataPosition, mainPart, hidePart, orderSuc, belowOC, arrow));

				/*
				 * set student infomation l
				 */
				portrait.setImageResource(R.drawable.portrait_test);
				bigPortrait.setBackgroundResource(0);
				bigPortrait.setImageResource(R.drawable.portrait_test);
				if (info.getStudentinfo() != null) {
					StudentInfo stuInfo = info.getStudentinfo();

					if (stuInfo.getCoachstate() == 1) {
						mImageLoader.loadImage(stuInfo.getAvatarurl(), portrait, new myloadListener(portrait, bigPortrait));
					} else {
						portrait.setImageResource(R.drawable.portrait_untest);
						bigPortrait.setImageResource(R.drawable.portrait_untest);
					}
					/*
					 * set student name
					 */
					if (stuInfo.getRealname() != null)
						stuName.setText("学员姓名  " + stuInfo.getRealname());
					else
						stuName.setText("学员姓名  未设置");
					/*
					 * set student telephone
					 */
					if (stuInfo.getPhone() != null)
						stuTel.setText("联系电话 " + stuInfo.getPhone());
					else
						stuTel.setText("联系电话  未设置");
					/*
					 * set student card number
					 */
					if (stuInfo.getStudent_cardnum() != null)
						stuCard.setText("学员证号  " + stuInfo.getStudent_cardnum());
					else
						stuCard.setText("学员证号  未设置");
				} else {
					stuName.setText("");
					stuTel.setText("");
					stuCard.setText("");
				}

			}
			return convertView;
		}

		private void sendSMS(String phonenum)

		{

			Uri smsToUri = Uri.parse("smsto:" + phonenum);

			Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);

			// intent.putExtra("sms_body", smsBody);

			startActivity(intent);

		}

//		class MImageLoaderListener extends SimpleImageLoadingListener {
//			PolygonImageView portrait;
//			ImageView bigPortrait;
//
//			public MImageLoaderListener(PolygonImageView portrait, ImageView bigPortrait) {
//				this.portrait = portrait;
//				this.bigPortrait = bigPortrait;
//			}
//
//			@Override
//			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//				super.onLoadingComplete(imageUri, view, loadedImage);
//				if (loadedImage != null) {
//					portrait.setImageBitmap(loadedImage);
//					bigPortrait.setImageBitmap(loadedImage);
//				}
//			}
//		}

		class myloadListener implements OnLoadListener {
			PolygonImageView portrait;
			ImageView bigPortrait;

			public myloadListener(PolygonImageView portrait, ImageView bigPortrait) {
				this.portrait = portrait;
				this.bigPortrait = bigPortrait;
			}

			@Override
			public void onLoad(Bitmap loadedImage, View arg1) {
				if (loadedImage != null) {
					portrait.setImageBitmap(loadedImage);
					bigPortrait.setBackgroundDrawable(new BitmapDrawable(loadedImage));
					bigPortrait.setImageResource(0);
				}
			}

		}

		/*
		 * go to send msg
		 */
		class MessageListener extends OnSingleClickListener {
			String phone;

			public MessageListener(String phone) {
				this.phone = phone;
			}

			@Override
			public void doOnClick(View v) {
				if (phone != null)
					sendSMS(phone);
				else {
					Toast.makeText(mActivity.getApplicationContext(), mActivity.getString(R.string.lacktel), Toast.LENGTH_SHORT).show();
				}
			}
		}

		private String getOrderDiff(String date) {
			if (date.contains(mDateToday))
				return "今日任务";
			else {
				int diff = daysBetween(date, mDateToday);
				if (diff > 0) {
					if (diff == 1)
						return "昨日任务";
					else {
						return diff + "日前任务";
					}
				} else if (diff < 0) {
					if (diff == -1)
						return "明日任务";
					else
						return Math.abs(diff) + "日后任务";
				}
			}
			return "";
		}

		public int daysBetween(String smdate, String bdate) {
			long between_days = 0;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				cal.setTime(sdf.parse(smdate));
				long time1 = cal.getTimeInMillis();
				cal.setTime(sdf.parse(bdate));
				long time2 = cal.getTimeInMillis();
				between_days = (time2 - time1) / (1000 * 3600 * 24);
			} catch (Exception e) {
			}
			return Integer.parseInt(String.valueOf(between_days));
		}

		private String getWeekDay(String time) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(time);
				return getWeekOfDate(date);
			} catch (Exception e) {
				return null;
			}
		}

		public String getWeekOfDate(Date dt) {
			String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (w < 0)
				w = 0;
			return weekDays[w];
		}

		private void setGreenBtn(Button btn) {
			btn.setBackgroundResource(R.drawable.shape_green_round);
			btn.setText(mActivity.getString(R.string.confirm_on));
		}

		private void setBlueBtn(Button btn) {
			btn.setBackgroundResource(R.drawable.shape_blue_round);
			btn.setText(mActivity.getString(R.string.confirm_off));
		}

		/*
		 * show the hide view which display the student info (name ,tel , cardnum)
		 */
		class ShowHideListener extends OnSingleClickListener {

			View view;
			ImageView arrow;
			int position;

			public ShowHideListener(View view, ImageView arrow, int position) {
				this.view = view;
				this.arrow = arrow;
				this.position = position;
			}

			@Override
			public void doOnClick(View v) {
				if (view.getVisibility() == View.VISIBLE) {
					view.setVisibility(View.GONE);
					arrow.setImageResource(R.drawable.arrow_grey);
					mShowHidePosition = -1;
				} else {
					view.setVisibility(View.VISIBLE);
					arrow.setImageResource(R.drawable.arrow_blue);
					mShowHidePosition = position;
				}

				mAdapter.notifyDataSetChanged();
			}

		}

		/*
		 * contact click listener
		 */
		class ContactListener extends OnSingleClickListener {

			String tel;

			public ContactListener(String tel) {
				this.tel = tel;
			}

			@Override
			public void doOnClick(View v) {
				if (tel == null) {
					Toast.makeText(mActivity.getApplicationContext(), mActivity.getString(R.string.lacktel), Toast.LENGTH_SHORT).show();
					return;
				}
				try {
					startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel)));
				} catch (Exception e) {
					CommonUtils.showToast(mActivity.getApplicationContext(), "");
				}

			}
		}

		/*
		 * confirmon click listener
		 */
		class ConfrimOnListener extends OnSingleClickListener {

			TaskInfo info;
			Button btn;
			int position;
			LinearLayout mainPart, belowOC;
			TextView OrderSuc;
			ImageView arrow;
			RelativeLayout hidePart;
			TextView startTime;
			TextView endTime;

			public ConfrimOnListener(TaskInfo info, Button btn, TextView startTime, TextView endTime, int position, LinearLayout mainPart, RelativeLayout hidePart, TextView OrderSuc,
					LinearLayout belowOC, ImageView arrow) {
				this.info = info;
				this.btn = btn;
				this.position = position;
				this.mainPart = mainPart;
				this.hidePart = hidePart;
				this.OrderSuc = OrderSuc;
				this.arrow = arrow;
				this.belowOC = belowOC;
				this.startTime = startTime;
				this.endTime = endTime;
			}

			Dialog mDialog = null;

			@Override
			public void doOnClick(View v) {
				getPosition();
				if (info.getState() == 3) {

					DialogUtil dUtil = new DialogUtil(new DialogConfirmListener() {

						@Override
						public void doConfirm(String str) {
							GetOffTask task = new GetOffTask(info, position, mainPart, hidePart, OrderSuc, belowOC, arrow);
							task.execute();
							if (mDialog != null)
								mDialog.dismiss();
						}

						@Override
						public void doCancel() {
							if (mDialog != null)
								mDialog.dismiss();
						}
					});
					mDialog = dUtil.CallConfirmDialog("确认下车吗？","","",mActivity, mDialog);
					
				} else {
					if (isFirstItem(position)) {

					} else {
						CommonUtils.showToast(mActivity.getApplicationContext(), mActivity.getString(R.string.order_undone));
						return;
					}
					// judge does the student perfect his or her material
					if (info != null && info.getStudentinfo() != null) {
						if (info.getStudentinfo().getCoachstate() == 0) {
							// unchecked
							CallDialog(info, btn, startTime, endTime, position);
							// CommonUtils.showToast(mActivity.getApplicationContext(), "弹框");
						} else {
							DialogUtil dUtil = new DialogUtil(new DialogConfirmListener() {

								@Override
								public void doConfirm(String str) {
									/*
									 * do get on request
									 */
									GetOnTask task = new GetOnTask(info, btn, startTime, endTime, position);
									task.execute();

									if (mDialog != null)
										mDialog.dismiss();
								}

								@Override
								public void doCancel() {
									if (mDialog != null)
										mDialog.dismiss();
								}
							});
							mDialog = dUtil.CallConfirmDialog("上车时帮学员刷一下学员卡","确认上车","",mActivity, mDialog);
						}
					} else {
						CommonUtils.showToast(mActivity.getApplicationContext(), mActivity.getString(R.string.perfect_material));
					}
				}
			}
		}

		@Override
		public boolean isItemViewTypePinned(int viewType) {
			return viewType == Item.SECTION;
		}
		
		/*
		 * 判断是否第一个未上车任务单
		 */
		public boolean isFirstItem(int position) {
			int pos = 0;
			for (TaskInfo ti : list) {
				if (ti.getState() != 3) {
					if (pos == position)
						return true;
					else {
						return false;
					}
				}
				pos++;
			}
			return false;
		}

		/*
		 * get on coach's car net request
		 */
		private class GetOnTask extends AsyncTask<Void, Void, BaseResult> {

			JSONAccessor accessor = new JSONAccessor(mActivity.getApplicationContext(), JSONAccessor.METHOD_POST);
			TaskInfo info;

			Button btn;
			int position;
			TextView startTime;
			TextView endTime;

			public GetOnTask(TaskInfo info, Button btn, TextView startTime, TextView endTime, int position) {
				this.info = info;
				this.btn = btn;
				this.position = position;
				this.startTime = startTime;
				this.endTime = endTime;
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();

				if (mLoadingDialog != null)
					mLoadingDialog.show();
			}

			@Override
			protected BaseResult doInBackground(Void... arg0) {
				/*
				 * 等待定位
				 */
				try {
					int count = 0;
					while (count < 20 && !hasLoc) {
						Thread.sleep(100);
						count++;
					}
				} catch (Exception e) {
				}
				stopLocClient();
				
				HashMap<String, Object> param = new BaseParam();
				param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
				param.put("orderid", info.getOrderid());
				param.put("lat", latitude);
				param.put("lon", longitude);
				param.put("detail", detail);
				param.put("action", "ConfirmOn");
				return accessor.execute(Settings.TASK_URL, param, BaseResult.class);
			}

			@Override
			protected void onPostExecute(BaseResult result) {
				super.onPostExecute(result);
				if (mLoadingDialog != null && mLoadingDialog.isShowing())
					mLoadingDialog.dismiss();
				hasLoc = false;
				if (result != null) {
					if (result.getCode() == 1) {
						list.get(position).setState(3); // change item state from list
						btn.setBackgroundResource(R.drawable.shape_blue_round);
						btn.setText(mActivity.getString(R.string.confirm_off));
						startTime.setTextColor(Color.parseColor("#1c1c1c"));
						endTime.setTextColor(Color.parseColor("#1c1c1c"));
					} else if (result.getCode() == 2) {
						CommonUtils.showToast(mActivity.getApplicationContext(), mActivity.getString(R.string.confirm_on_tip1));
					} else if (result.getCode() == 3) {
						CommonUtils.showToast(mActivity.getApplicationContext(), mActivity.getString(R.string.confirm_on_tip2));
					} else if (result.getCode() == 4) {
						CommonUtils.showToast(mActivity.getApplicationContext(), mActivity.getString(R.string.confirm_on_tip3));
					}

				} else {
					CommonUtils.showToast(mActivity.getApplicationContext(), mActivity.getString(R.string.net_error));
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(mActivity);
					}
				}
			}

		}

		/*
		 * get off coach's car net request
		 */
		private class GetOffTask extends AsyncTask<Void, Void, BaseResult> {

			JSONAccessor accessor = new JSONAccessor(mActivity.getApplicationContext(), JSONAccessor.METHOD_POST);
			TaskInfo info;
			LoadingDialog mLoadingDialog;
			LinearLayout mainPart, belowOC;
			TextView OrderSuc;
			ImageView arrow;
			RelativeLayout hidePart;
			int position;

			public GetOffTask(TaskInfo info, int position, LinearLayout mainPart, RelativeLayout hidePart, TextView OrderSuc, LinearLayout belowOC, ImageView arrow) {
				this.info = info;
				this.mainPart = mainPart;
				this.hidePart = hidePart;
				this.OrderSuc = OrderSuc;
				this.arrow = arrow;
				this.belowOC = belowOC;
				this.position = position;
			}

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				mLoadingDialog = new LoadingDialog(mActivity);
				if (mLoadingDialog != null)
					mLoadingDialog.show();
			}

			@Override
			protected BaseResult doInBackground(Void... arg0) {
				/*
				 * 等待定位
				 */
				try {
					int count = 0;
					while (count < 20 && !hasLoc) {
						Thread.sleep(100);
						count++;
					}
				} catch (Exception e) {
				}
				stopLocClient();
				
				HashMap<String, Object> param = new BaseParam();
				param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
				param.put("orderid", info.getOrderid());
				param.put("lat", latitude);
				param.put("lon", longitude);
				param.put("detail", detail);
				param.put("action", "ConfirmDown");
				return accessor.execute(Settings.TASK_URL, param, BaseResult.class);
			}

			@Override
			protected void onPostExecute(BaseResult result) {
				super.onPostExecute(result);
				if (mLoadingDialog != null && mLoadingDialog.isShowing())
					mLoadingDialog.dismiss();
				hasLoc = false;
				if (result != null) {
					if (result.getCode() == 1) {
						// animation
						ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.0f);
						animation.setDuration(500);
						animation.setFillAfter(true);
						animation.setAnimationListener(new AnimationListener() {

							@Override
							public void onAnimationStart(Animation arg0) {

							}

							@Override
							public void onAnimationRepeat(Animation arg0) {

							}

							@Override
							public void onAnimationEnd(Animation arg0) {
								hidePart.setVisibility(View.GONE);
								belowOC.setVisibility(View.INVISIBLE);
								OrderSuc.setVisibility(View.VISIBLE);
								arrow.setVisibility(View.INVISIBLE);
								TimerTask task = new TimerTask() {

									@Override
									public void run() {
										mActivity.runOnUiThread(new Runnable() {

											@Override
											public void run() {
												TranslateAnimation tAnimation = new TranslateAnimation(0, Settings.DISPLAY_WIDTH, 0, 0);
												tAnimation.setDuration(500);
												tAnimation.setFillAfter(true);
												tAnimation.setAnimationListener(new AnimationListener() {

													@Override
													public void onAnimationStart(Animation arg0) {

													}

													@Override
													public void onAnimationRepeat(Animation arg0) {

													}

													@Override
													public void onAnimationEnd(Animation arg0) {
														// delete item from list
														list.remove(position);
														mAdapter.initDataSet();
														mAdapter.notifyDataSetChanged();

														Intent intent = new Intent(mActivity, CommentActivity_.class);
														intent.putExtra(IntentsParamNames.ORDERID, info.getOrderid());
														mActivity.startActivityForResult(intent, COMMENT_REQUEST_CODE);
													}
												});

												mainPart.startAnimation(tAnimation);

											}
										});

									}
								};
								new Timer().schedule(task, 500);
							}
						});
						hidePart.startAnimation(animation);
					} else if (result.getCode() == 2) {
						CommonUtils.showToast(mActivity.getApplicationContext(), mActivity.getString(R.string.confirm_off_tip1));
					} else if (result.getCode() == 3) {
						CommonUtils.showToast(mActivity.getApplicationContext(), mActivity.getString(R.string.confirm_off_tip2));
					} else if (result.getCode() == 95) {
							CommonUtils.gotoLogin(mActivity);
					}
				} else {
					CommonUtils.showToast(mActivity.getApplicationContext(), mActivity.getString(R.string.net_error));
				}
			}

		}

		Dialog mServiceDialog;

		void CallDialog(TaskInfo info, Button btn, TextView startTime, TextView endTime, final int position) {
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
			mServiceDialog = builder.create();
			mServiceDialog.show();
			mServiceDialog.setContentView(R.layout.dialog_vertical_layout);
			mServiceDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			WindowManager.LayoutParams params = mServiceDialog.getWindow().getAttributes();
			int maxWidth = Settings.DISPLAY_WIDTH - DensityUtils.dp2px(mActivity, 40);
			int textWidth = DensityUtils.dp2px(mActivity, 17 * 15);
			if (maxWidth > textWidth)
				params.width = textWidth;
			else
				params.width = maxWidth;
			params.gravity = Gravity.CENTER;
			mServiceDialog.getWindow().setAttributes(params);
			mServiceDialog.setCanceledOnTouchOutside(true);

			TextView goToCheck = (TextView) mServiceDialog.findViewById(R.id.go_to_check);
			TextView getOnCar = (TextView) mServiceDialog.findViewById(R.id.get_on_car);
			TextView cancel = (TextView) mServiceDialog.findViewById(R.id.dialog_cancel);

			cancel.setOnClickListener(new OnSingleClickListener() {

				@Override
				public void doOnClick(View v) {
					mServiceDialog.dismiss();
				}
			});

			goToCheck.setOnClickListener(new OnSingleClickListener() {

				@Override
				public void doOnClick(View v) {
					// CommonUtils.showToast(mActivity.getApplicationContext(), "璺宠浆楠岃瘉椤甸潰");
					Intent intent = new Intent(mActivity, CheckStudentActivity.class);
					Bundle bundle = new Bundle();
					int studenid = list.get(position).getStudentinfo().getStudentid();
					bundle.putInt("studentId", studenid);
					intent.putExtras(bundle);
					startActivity(intent);
					mServiceDialog.dismiss();
				}
			});

			getOnCar.setOnClickListener(new GetOnCarListener(info, btn, startTime, endTime, position));
		}

		class GetOnCarListener extends OnSingleClickListener {
			TaskInfo info;
			Button btn;
			int position;
			TextView startTime;
			TextView endTime;

			public GetOnCarListener(TaskInfo info, Button btn, TextView startTime, TextView endTime, int position) {
				this.info = info;
				this.btn = btn;
				this.position = position;
				this.startTime = startTime;
				this.endTime = endTime;
			}

			@Override
			public void doOnClick(View v) {
				// get on
				GetOnTask task = new GetOnTask(info, btn, startTime, endTime, position);
				task.execute();
				mServiceDialog.dismiss();
			}

		}
	}

	/*
	 * get time (HH:mm:ss)
	 */
	String getTime(String time) {
		return time.substring(time.lastIndexOf(" ") + 1, time.lastIndexOf(":"));
	}

	/*
	 * get date (yyyy:MM:dd)
	 */
	String getDate(String time) {
		return time.substring(0, time.indexOf(" "));
	}

	void setSpannableTime(TextView tv, String time) {
		tv.setText("");
		SpannableString str = new SpannableString(time);
		str.setSpan(new AbsoluteSizeSpan(26, true), 0, time.indexOf(":") + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		str.setSpan(new AbsoluteSizeSpan(18, true), time.indexOf(":"), str.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		tv.append(str);
	}

	/*
	 * @param date(format "2015-05-21")
	 * 
	 * @return 0:== >0: larger than today <0: smaller than today
	 */
	int compareToday(String date) {
		return date.compareTo(mDateToday);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	static class Item {

		public static final int ITEM = 0;
		public static final int SECTION = 1;

		public final int type;
		public final String text;

		public int sectionPosition;
		public int listPosition;

		public Item(int type, String text) {
			this.type = type;
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}

	}

	class FastScrollAdapter extends JobOrderAdapter implements SectionIndexer {

		private Item[] sections;

		@Override
		protected void prepareSections(int sectionsNumber) {
			sections = new Item[sectionsNumber];
		}

		@Override
		protected void onSectionAdded(Item section, int sectionPosition) {
			sections[sectionPosition] = section;
		}

		@Override
		public Item[] getSections() {
			return sections;
		}

		@Override
		public int getPositionForSection(int section) {
			if (section >= sections.length) {
				section = sections.length - 1;
			}
			return sections[section].listPosition;
		}

		@Override
		public int getSectionForPosition(int position) {
			if (position >= getCount()) {
				position = getCount() - 1;
			}
			return getItem(position).sectionPosition;
		}

	}
	
	private class SetOrderCancel extends AsyncTask<Void, Void, BaseResult> {
		private int Agree;
		private int Orderid;
		public SetOrderCancel(int agree,int orderid)
		{
			this.Agree = agree;
			Orderid = orderid;
		}
		JSONAccessor accessor = new JSONAccessor(mActivity.getApplicationContext(), JSONAccessor.METHOD_POST);
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			HashMap<String, Object> param = new BaseParam();
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("orderid", Orderid);
			param.put("agree",Agree);
			param.put("action", "CancelOrderAgree");
			return accessor.execute(Settings.SORDER_URL, param,  BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result.getCode() == 1)
			{
				CommonUtils.showToast(mActivity.getApplicationContext(),result.getMessage());
			}else if (result.getCode() == 2){
				CommonUtils.showToast(mActivity.getApplicationContext(),result.getMessage());
			}else{
				if (result.getMessage() != null)
					CommonUtils.showToast(mActivity.getApplicationContext(), result.getMessage());
				if (result.getCode() == 95) {
					CommonUtils.gotoLogin(mActivity);
				}
			}
			mListView.setRefreshing();
		}
	}

}
