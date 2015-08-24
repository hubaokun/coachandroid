package xiaoba.coach.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.activity.AddressSetActivity;
import xiaoba.coach.activity.AddressSetActivity_;
import xiaoba.coach.activity.ClassTimeSetActivity_;
import xiaoba.coach.activity.HomeActivity;
import xiaoba.coach.common.Settings;
import xiaoba.coach.interfaces.NotifyDateSelect;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.Schedule;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.GetScheduleResult;
import xiaoba.coach.net.result.SetDateTimeResult;
import xiaoba.coach.net.result.SetDefaultResult;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.utils.NumberHelper;
import xiaoba.coach.utils.TimeUtil;
import xiaoba.coach.views.CalendarGridView;
import xiaoba.coach.views.CalendarGridViewAdapter;
import xiaoba.coach.views.CalendarGridViewAdapter.BallState;
import xiaoba.coach.views.LoadingDialog;
import xiaoba.coach.views.CalendarGridViewAdapter.DateClickListener;
import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.listview.PullToRefreshBase;
import com.daoshun.lib.listview.PullToRefreshBase.Mode;
import com.daoshun.lib.listview.PullToRefreshScrollView;
import com.daoshun.lib.listview.PullToRefreshScrollView.OnSscrollListener;
import com.daoshun.lib.view.OnSingleClickListener;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.LinearLayout.LayoutParams;

/*
 * 日程设置
 */
public class DateSetFragment extends Fragment {

	HomeActivity mActivity;
	LinearLayout mWeekColumn;
	PullToRefreshScrollView mScrollView;
	RelativeLayout mCalendarLayout;
	TextView mTitle;
	ImageView mToLastMonth;
	ImageView mToNextMonth;
//	LinearLayout mMorningBar; // 早上显示栏
//	LinearLayout mAfternoonBar; // 下午显示栏
//	LinearLayout mNightBar; // 晚上显示栏
//	LinearLayout mMorningSSet; // 早上设置栏
//	LinearLayout mAfternoonSet; // 下午设置栏
//	LinearLayout mNightSet; // 晚上设置栏
//	RelativeLayout mMorSelectAll; // 早上时间全选
//	RelativeLayout mAftSelectAll; // 下午时间全选
//	RelativeLayout mNigSelectAll; // 晚上时间全选
//	ImageView mMorSelectAllImg;
//	ImageView mAftSelectAllImg;
//	ImageView mNigSelectAllImg;
//	TextView mMorSelectAllTv;
//	TextView mAftSelectAllTv;
//	TextView mNigSelectAllTv;
//	View mMorPoint;
//	TextView mMorTime;
//	ImageView mMorArrow;
//	View mAftPoint;
//	TextView mAftTime;
//	ImageView mAftArrow;
//	View mNigPoint;
//	TextView mNigTime;
//	ImageView mNigArrow;
//	Button mAllDaySetClose;
	LinearLayout mOnClassLayout;
	LinearLayout mRestLayout;
	RelativeLayout mArrowUp;
	/************* MORNING TIME *********/
//	private TextView mFiveTime;
//	private TextView mSixTime;
//	private TextView mSevenTime;
//	private TextView mEightTime;
//	private TextView mNineTime;
//	private TextView mTenTime;
//	private TextView mElevenTime;
//	private TextView mFivePrice;
//	private TextView mSixPrice;
//	private TextView mSevenPrice;
//	private TextView mEightPrice;
//	private TextView mNinePrice;
//	private TextView mTenPrice;
//	private TextView mElevenPrice;
//	private TextView mFiveSelect;
//	private TextView mSixSelect;
//	private TextView mSevenSelect;
//	private TextView mEightSelect;
//	private TextView mNineSelect;
//	private TextView mTenSelect;
//	private TextView mElevenSelect;
	/************* AFTERNOON TIME *********/
//	private TextView mTwelveTime;
//	private TextView mThirteenTime;
//	private TextView mFourteenTime;
//	private TextView mFifteenTime;
//	private TextView mSixteenTime;
//	private TextView mSeventeenTime;
//	private TextView mEighteenTime;
//	private TextView mTwelvePrice;
//	private TextView mThirteenPrice;
//	private TextView mFourteenPrice;
//	private TextView mFifteenPrice;
//	private TextView mSixteenPrice;
//	private TextView mSeventeenPrice;
//	private TextView mEighteenPrice;
//	private TextView mTwelveSelect;
//	private TextView mThirteenSelect;
//	private TextView mFourteenSelect;
//	private TextView mFifteenSelect;
//	private TextView mSixteenSelect;
//	private TextView mSeventeenSelect;
//	private TextView mEighteenSelect;
	/************* NIGHT TIME *****************/
//	private TextView mNineteenTime;
//	private TextView mTwentyTime;
//	private TextView mTwentyOneTime;
//	private TextView mTwentyTwoTime;
//	private TextView mTwentyThreeTime;
//	private TextView mNineteenPrice;
//	private TextView mTwentyPrice;
//	private TextView mTwentyOnePrice;
//	private TextView mTwentyTwoPrice;
//	private TextView mTwentyThreePrice;
//	private TextView mNineteenSelect;
//	private TextView mTwentySelect;
//	private TextView mTwentyOneSelect;
//	private TextView mTwentyTwoSelect;
//	private TextView mTwentyThreeSelect;
	// gears
//	private RelativeLayout mFiveGear;
//	private RelativeLayout mSixGear;
//	private RelativeLayout mSevenGear;
//	private RelativeLayout mEightGear;
//	private RelativeLayout mNineGear;
//	private RelativeLayout mTenGear;
//	private RelativeLayout mElevenGear;
//	private RelativeLayout mTwelveGear;
//	private RelativeLayout mThirteenGear;
//	private RelativeLayout mFourteenGear;
//	private RelativeLayout mFifteenGear;
//	private RelativeLayout mSixteenGear;
//	private RelativeLayout mSeventeenGear;
//	private RelativeLayout mEighteenGear;
//	private RelativeLayout mNineteenGear;
//	private RelativeLayout mTwentyGear;
//	private RelativeLayout mTwentyOneGear;
//	private RelativeLayout mTwentyTwoGear;
//	private RelativeLayout mTwentyThreeGear;
	// 批量设置
//	private Button mMorMultiSet;
//	private Button mAftMultiSet;
//	private Button mNigMultiSet;
	public LoadingDialog mLoadingDialog;// 普通加载对话框
	// 判断手势用
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	// 动画
	private Animation slideLeftIn;
	private Animation slideLeftOut;
	private Animation slideRightIn;
	private Animation slideRightOut;
	private ViewFlipper viewFlipper;
	// 基本变量
	private GridView gCurrnetMonthView;// 当前月
	private GridView gView3;// 下一个月
	boolean bIsSelection = false;// 是否是选择事件发生
	private Calendar calStartDate = Calendar.getInstance();// 当前显示的日历
	private Calendar calSelected = Calendar.getInstance(); // 选择的日历
	private Calendar calToday = Calendar.getInstance(); // 今日
	private CalendarGridViewAdapter gAdapter;
	private CalendarGridViewAdapter gAdapterNextMonth;
	private CalendarGridViewAdapter gSelectAdapter;
	private RelativeLayout mainLayout;

	// 保存接口数据
	private GetScheduleResult scheduleResult;
	private AlphaAnimation mAppearAnimation;
	private AlphaAnimation mDisappearAnimation;

	//
	private int iMonthViewCurrentMonth = 0; // 当前视图月
	private int iMonthViewCurrentYear = 0; // 当前视图年
	private int iFirstDayOfWeek = Calendar.MONDAY;

	private static final int mainLayoutID = 88; // 设置主布局ID
	private static final int calLayoutID = 55; // 日历布局ID
	
	private boolean finishLoadingData; // 是否取数据中

	/*
	 * 选中数组 false：选中 true：未选中
	 */
	private boolean[] selectArray = new boolean[19];
	/*
	 * 状态数组 false:休息 true：开课
	 */
	private boolean[] stateArray = new boolean[19];
	/*
	 * 预约数组 false:未预约 true:预约
	 */
	private boolean[] bookArray = new boolean[19];

	private RelativeLayout mHangingArrow;
	private LinearLayout mHangingContent;

	ViewPager mViewPager;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (HomeActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_date_set, container, false);
		mLoadingDialog = new LoadingDialog(mActivity, true);
		resources = this.getResources();

		initView(view);
		addListeners();
		initData();

		mScrollView.setRefreshing();

		return view;
	}

	public class DateViewpagerAdapter extends PagerAdapter {

		private List<View> viewLists; // 保存传入的view

		public DateViewpagerAdapter(List<View> viewLists) {
			this.viewLists = viewLists;
		}

		@Override
		public int getCount() {
			return viewLists.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View view, int position, Object object) // 销毁Item
		{
			((ViewPager) view).removeView(viewLists.get(position));
		}

		@Override
		public Object instantiateItem(View view, int position) // 实例化Item
		{
			((ViewPager) view).addView(viewLists.get(position), 0);

			return viewLists.get(position);
		}

	}

	// private static Handler handler = new Handler(){
	//
	// @Override
	// public void handleMessage(Message msg) {
	// super.handleMessage(msg);
	// }
	// };

	private void initView(View view) {
		mWeekColumn = (LinearLayout) view.findViewById(R.id.week_column);
		mWeekColumn.setLayoutParams(new LinearLayout.LayoutParams(Settings.DISPLAY_WIDTH, Settings.DISPLAY_WIDTH / 7 / 2));
		mScrollView = (PullToRefreshScrollView) view.findViewById(R.id.dt_scrollview);
		mScrollView.setMode(Mode.PULL_FROM_START);
		mCalendarLayout = (RelativeLayout) view.findViewById(R.id.main_layout);
		mTitle = (TextView) view.findViewById(R.id.title_date);
		mToLastMonth = (ImageView) view.findViewById(R.id.to_last_month);
		// mViewPager = (ViewPager) view.findViewById(R.id.dateset_viewpager);
		/*
		 * at current month
		 */
		mToLastMonth.setClickable(false);
		mToLastMonth.setImageResource(R.drawable.triangle_grey_left);
		mToNextMonth = (ImageView) view.findViewById(R.id.to_next_month);
		Calendar temp = Calendar.getInstance();
		temp.setTime(calSelected.getTime());
		temp.add(Calendar.DAY_OF_MONTH, 30);
		if (temp.get(Calendar.MONTH) == iMonthViewCurrentMonth) {
			mToLastMonth.setClickable(false);
			mToLastMonth.setImageResource(R.drawable.triangle_grey);
		}

//		mMorningBar = (LinearLayout) view.findViewById(R.id.mor_show_part);
//		mAfternoonBar = (LinearLayout) view.findViewById(R.id.afternoon_show_part);
//		mNightBar = (LinearLayout) view.findViewById(R.id.night_show_part);
//		mMorningSSet = (LinearLayout) view.findViewById(R.id.mor_hide_part);
//		mAfternoonSet = (LinearLayout) view.findViewById(R.id.afternoon_hide_part);
//		mNightSet = (LinearLayout) view.findViewById(R.id.night_hide_part);
//		mMorSelectAll = (RelativeLayout) mMorningSSet.findViewById(R.id.all_select_layout);
//		mAftSelectAll = (RelativeLayout) mAfternoonSet.findViewById(R.id.all_select_layout);
//		mNigSelectAll = (RelativeLayout) mNightSet.findViewById(R.id.all_select_layout);
//		mMorSelectAllImg = (ImageView) mMorningSSet.findViewById(R.id.all_select);
//		mAftSelectAllImg = (ImageView) mAfternoonSet.findViewById(R.id.all_select);
//		mNigSelectAllImg = (ImageView) mNightSet.findViewById(R.id.all_select);
//		mNigSelectAllImg.setSelected(true);
//		mAftSelectAllImg.setSelected(true);
//		mMorSelectAllImg.setSelected(true);
//		mMorSelectAllTv = (TextView) mMorningSSet.findViewById(R.id.all_select_tv);
//		mAftSelectAllTv = (TextView) mAfternoonSet.findViewById(R.id.all_select_tv);
//		mNigSelectAllTv = (TextView) mNightSet.findViewById(R.id.all_select_tv);
//		mMorSelectAllTv.setText("取消全选");
//		mAftSelectAllTv.setText("取消全选");
//		mNigSelectAllTv.setText("取消全选");
//		mMorPoint = (View) view.findViewById(R.id.mor_point);
//		mMorTime = (TextView) view.findViewById(R.id.mor_time);
//		mMorArrow = (ImageView) view.findViewById(R.id.mor_arrow);
//		mAftPoint = (View) view.findViewById(R.id.aft_point);
//		mAftTime = (TextView) view.findViewById(R.id.aft_time);
//		mAftArrow = (ImageView) view.findViewById(R.id.aft_arrow);
//		mNigPoint = (View) view.findViewById(R.id.nig_point);
//		mNigTime = (TextView) view.findViewById(R.id.nig_time);
//		mNigArrow = (ImageView) view.findViewById(R.id.nig_arrow);
//		mMorMultiSet = (Button) mMorningSSet.findViewById(R.id.multy_set);
//		mAftMultiSet = (Button) mAfternoonSet.findViewById(R.id.multy_set);
//		mNigMultiSet = (Button) mNightSet.findViewById(R.id.multy_set);
//		mAllDaySetClose = (Button) view.findViewById(R.id.close_class);
		mOnClassLayout = (LinearLayout) view.findViewById(R.id.onclass_layout);
		mRestLayout = (LinearLayout) view.findViewById(R.id.rest_layout);
		// morning time
//		mFiveTime = (TextView) mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.tg_time);
//		mFivePrice = (TextView) mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.tg_price);
//		mFiveSelect = (TextView) mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.tg_select);
//		mSixTime = (TextView) mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.tg_time);
//		mSixPrice = (TextView) mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.tg_price);
//		mSixSelect = (TextView) mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.tg_select);
//		mSevenTime = (TextView) mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.tg_time);
//		mSevenPrice = (TextView) mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.tg_price);
//		mSevenSelect = (TextView) mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.tg_select);
//		mEightTime = (TextView) mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.tg_time);
//		mEightPrice = (TextView) mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.tg_price);
//		mEightSelect = (TextView) mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.tg_select);
//		mNineTime = (TextView) mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.tg_time);
//		mNinePrice = (TextView) mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.tg_price);
//		mNineSelect = (TextView) mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.tg_select);
//		mTenTime = (TextView) mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.tg_time);
//		mTenPrice = (TextView) mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.tg_price);
//		mTenSelect = (TextView) mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.tg_select);
//		mElevenTime = (TextView) mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.tg_time);
//		mElevenPrice = (TextView) mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.tg_price);
//		mElevenSelect = (TextView) mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.tg_select);
		// afternoon time
//		mTwelveTime = (TextView) mAfternoonSet.findViewById(R.id.twelve_part).findViewById(R.id.tg_time);
//		mTwelvePrice = (TextView) mAfternoonSet.findViewById(R.id.twelve_part).findViewById(R.id.tg_price);
//		mTwelveSelect = (TextView) mAfternoonSet.findViewById(R.id.twelve_part).findViewById(R.id.tg_select);
//		mThirteenTime = (TextView) mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.tg_time);
//		mThirteenPrice = (TextView) mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.tg_price);
//		mThirteenSelect = (TextView) mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.tg_select);
//		mFourteenTime = (TextView) mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.tg_time);
//		mFourteenPrice = (TextView) mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.tg_price);
//		mFourteenSelect = (TextView) mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.tg_select);
//		mFifteenTime = (TextView) mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.tg_time);
//		mFifteenPrice = (TextView) mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.tg_price);
//		mFifteenSelect = (TextView) mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.tg_select);
//		mSixteenTime = (TextView) mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.tg_time);
//		mSixteenPrice = (TextView) mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.tg_price);
//		mSixteenSelect = (TextView) mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.tg_select);
//		mSeventeenTime = (TextView) mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.tg_time);
//		mSeventeenPrice = (TextView) mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.tg_price);
//		mSeventeenSelect = (TextView) mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.tg_select);
//		mEighteenTime = (TextView) mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.tg_time);
//		mEighteenPrice = (TextView) mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.tg_price);
//		mEighteenSelect = (TextView) mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.tg_select);
		// night time
//		mNineteenTime = (TextView) mNightSet.findViewById(R.id.nineteen_part).findViewById(R.id.tg_time);
//		mTwentyTime = (TextView) mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.tg_time);
//		mTwentyOneTime = (TextView) mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.tg_time);
//		mTwentyTwoTime = (TextView) mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.tg_time);
//		mTwentyThreeTime = (TextView) mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.tg_time);
//		mNineteenPrice = (TextView) mNightSet.findViewById(R.id.nineteen_part).findViewById(R.id.tg_price);
//		mTwentyPrice = (TextView) mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.tg_price);
//		mTwentyOnePrice = (TextView) mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.tg_price);
//		mTwentyTwoPrice = (TextView) mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.tg_price);
//		mTwentyThreePrice = (TextView) mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.tg_price);
//		mNineteenSelect = (TextView) mNightSet.findViewById(R.id.nineteen_part).findViewById(R.id.tg_select);
//		mTwentySelect = (TextView) mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.tg_select);
//		mTwentyOneSelect = (TextView) mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.tg_select);
//		mTwentyTwoSelect = (TextView) mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.tg_select);
//		mTwentyThreeSelect = (TextView) mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.tg_select);
		// gears
//		mFiveGear = (RelativeLayout) mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.tg_geer);
//		mSixGear = (RelativeLayout) mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.tg_geer);
//		mSevenGear = (RelativeLayout) mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.tg_geer);
//		mEightGear = (RelativeLayout) mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.tg_geer);
//		mNineGear = (RelativeLayout) mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.tg_geer);
//		mTenGear = (RelativeLayout) mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.tg_geer);
//		mElevenGear = (RelativeLayout) mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.tg_geer);
//		mTwelveGear = (RelativeLayout) mAfternoonSet.findViewById(R.id.twelve_part).findViewById(R.id.tg_geer);
//		mThirteenGear = (RelativeLayout) mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.tg_geer);
//		mFourteenGear = (RelativeLayout) mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.tg_geer);
//		mFifteenGear = (RelativeLayout) mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.tg_geer);
//		mSixteenGear = (RelativeLayout) mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.tg_geer);
//		mSeventeenGear = (RelativeLayout) mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.tg_geer);
//		mEighteenGear = (RelativeLayout) mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.tg_geer);
//		mNineteenGear = (RelativeLayout) mNightSet.findViewById(R.id.nineteen_part).findViewById(R.id.tg_geer);
//		mTwentyGear = (RelativeLayout) mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.tg_geer);
//		mTwentyOneGear = (RelativeLayout) mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.tg_geer);
//		mTwentyTwoGear = (RelativeLayout) mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.tg_geer);
//		mTwentyThreeGear = (RelativeLayout) mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.tg_geer);

		mHangingArrow = (RelativeLayout) view.findViewById(R.id.hanging_arrow_part);
		mHangingContent = (LinearLayout) view.findViewById(R.id.hanging_content);
		mArrowUp = (RelativeLayout) view.findViewById(R.id.arrow_part);
	}

	private void addListeners() {
		/*
		 * click title bar triangle left
		 */
		mToLastMonth.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				goToLastMonth();
			}
		});

		/*
		 * click title bar triangle right
		 */
		mToNextMonth.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				goToNextMonth();
			}
		});
		/*
		 * click morning to set morning time for training
		 */
//		mMorningBar.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (mMorningSSet.getVisibility() == View.VISIBLE) {
//					mMorningSSet.setVisibility(View.GONE);
//					mMorArrow.setImageResource(R.drawable.arrow_grey);
//					mMorTime.startAnimation(mAppearAnimation);
//				} else {
//					if (mAfternoonSet.getVisibility() == View.VISIBLE) {
//						mAfternoonSet.setVisibility(View.GONE);
//						mAftArrow.setImageResource(R.drawable.arrow_grey);
//						mAftTime.startAnimation(mAppearAnimation);
//					}
//					if (mNightSet.getVisibility() == View.VISIBLE) {
//						mNightSet.setVisibility(View.GONE);
//						mNigArrow.setImageResource(R.drawable.arrow_grey);
//						mNigTime.startAnimation(mAppearAnimation);
//					}
//					mMorningSSet.setVisibility(View.VISIBLE);
//					mMorArrow.setImageResource(R.drawable.arrow_black_down);
//					mMorTime.startAnimation(mDisappearAnimation);
//				}
//			}
//		});

		/*
		 * click afternoon to set afternoon time for training
		 */
//		mAfternoonBar.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (mAfternoonSet.getVisibility() == View.VISIBLE) {
//					mAfternoonSet.setVisibility(View.GONE);
//					mAftArrow.setImageResource(R.drawable.arrow_grey);
//					// mAftTime.setVisibility(View.VISIBLE);
//					mAftTime.startAnimation(mAppearAnimation);
//				} else {
//					if (mMorningSSet.getVisibility() == View.VISIBLE) {
//						mMorningSSet.setVisibility(View.GONE);
////						mMorArrow.setImageResource(R.drawable.arrow_grey);
////						mMorTime.startAnimation(mAppearAnimation);
//					}
//					if (mNightSet.getVisibility() == View.VISIBLE) {
//						mNightSet.setVisibility(View.GONE);
//						mNigArrow.setImageResource(R.drawable.arrow_grey);
//						mNigTime.startAnimation(mAppearAnimation);
//					}
//					mAfternoonSet.setVisibility(View.VISIBLE);
//					mAftArrow.setImageResource(R.drawable.arrow_black_down);
//					// mAftTime.setVisibility(View.INVISIBLE);
//					mAftTime.startAnimation(mDisappearAnimation);
//				}
//			}
//		});
//
//		/*
//		 * click night to set night time for training
//		 */
//		mNightBar.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (mNightSet.getVisibility() == View.VISIBLE) {
//					mNightSet.setVisibility(View.GONE);
//					mNigArrow.setImageResource(R.drawable.arrow_grey);
//					// mNigTime.setVisibility(View.VISIBLE);
//					mNigTime.startAnimation(mAppearAnimation);
//				} else {
//					if (mAfternoonSet.getVisibility() == View.VISIBLE) {
//						mAfternoonSet.setVisibility(View.GONE);
//						mAftArrow.setImageResource(R.drawable.arrow_grey);
//						mAftTime.startAnimation(mAppearAnimation);
//					}
//					if (mMorningSSet.getVisibility() == View.VISIBLE) {
//						mMorningSSet.setVisibility(View.GONE);
////						mMorArrow.setImageResource(R.drawable.arrow_grey);
////						mMorTime.startAnimation(mAppearAnimation);
//					}
//					mNightSet.setVisibility(View.VISIBLE);
//					mNigArrow.setImageResource(R.drawable.arrow_black_down);
//					// mNigTime.setVisibility(View.INVISIBLE);
//					mNigTime.startAnimation(mDisappearAnimation);
//				}
//			}
//		});

		mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
				finishLoadingData = false;
				new GetScheduleTask().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

			}
		});

		mScrollView.setOnSscrollListener(new OnSscrollListener() {

			@Override
			public void onSScroll(int scrollY) {
				if (scrollY > (Settings.DISPLAY_WIDTH - 12) / 7 * selectItemLine)
				{
					//gCurrnetMonthView.setNumColumns(1);
					mHangingContent.setVisibility(View.VISIBLE);
				}
					//mHangingContent.setVisibility(View.VISIBLE);
				else {
//					RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (Settings.DISPLAY_WIDTH - 12) / 7 * lineNum + (lineNum - 1) * 2);
//					gCurrnetMonthView.setLayoutParams(param);
					mHangingContent.setVisibility(View.INVISIBLE);
					gSelectAdapter.notifyDataSetChanged();
				}
				if (scrollY > (Settings.DISPLAY_WIDTH - 12) / 7 * (lineNum - 1)) {
					mHangingArrow.setVisibility(View.VISIBLE);
				} else {
					mHangingArrow.setVisibility(View.INVISIBLE);
				}
			}
		});

//		mMorSelectAll.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (mMorSelectAllImg.isSelected()) {
//					mMorSelectAllImg.setSelected(false);
//					mMorSelectAllTv.setText("全选");
//					if (equalsDate(calToday.getTime(), calSelected.getTime())) {
//						/*
//						 * 今天
//						 */
//						if (mDeadHour < 5 && !bookArray[0]) {
//							mFiveSelect.setVisibility(View.INVISIBLE);
//							selectArray[0] = true;
//						}
//						if (mDeadHour < 6 && !bookArray[1]) {
//							mSixSelect.setVisibility(View.INVISIBLE);
//							selectArray[1] = true;
//						}
//						if (mDeadHour < 7 && !bookArray[2]) {
//							mSevenSelect.setVisibility(View.INVISIBLE);
//							selectArray[2] = true;
//						}
//						if (mDeadHour < 8 && !bookArray[3]) {
//							mEightSelect.setVisibility(View.INVISIBLE);
//							selectArray[3] = true;
//						}
//						if (mDeadHour < 9 && !bookArray[4]) {
//							mNineSelect.setVisibility(View.INVISIBLE);
//							selectArray[4] = true;
//						}
//						if (mDeadHour < 10 && !bookArray[5]) {
//							mTenSelect.setVisibility(View.INVISIBLE);
//							selectArray[5] = true;
//						}
//						if (mDeadHour < 11 && !bookArray[6]) {
//							mElevenSelect.setVisibility(View.INVISIBLE);
//							selectArray[6] = true;
//						}
//					} else {
//						/*
//						 * 明天过后
//						 */
//						if (!bookArray[0]) {
//							mFiveSelect.setVisibility(View.INVISIBLE);
//							selectArray[0] = true;
//						}
//						if (!bookArray[1]) {
//							mSixSelect.setVisibility(View.INVISIBLE);
//							selectArray[1] = true;
//						}
//						if (!bookArray[2]) {
//							mSevenSelect.setVisibility(View.INVISIBLE);
//							selectArray[2] = true;
//						}
//						if (!bookArray[3]) {
//							mEightSelect.setVisibility(View.INVISIBLE);
//							selectArray[3] = true;
//						}
//						if (!bookArray[4]) {
//							mNineSelect.setVisibility(View.INVISIBLE);
//							selectArray[4] = true;
//						}
//						if (!bookArray[5]) {
//							mTenSelect.setVisibility(View.INVISIBLE);
//							selectArray[5] = true;
//						}
//						if (!bookArray[6]) {
//							mElevenSelect.setVisibility(View.INVISIBLE);
//							selectArray[6] = true;
//						}
//					}
//				} else {
//					if (equalsDate(calToday.getTime(), calSelected.getTime())) {
//						/*
//						 * 今天
//						 */
//						if (mDeadHour < 5 && !bookArray[0]) {
//							mFiveSelect.setVisibility(View.VISIBLE);
//							selectArray[0] = false;
//						}
//						if (mDeadHour < 6 && !bookArray[1]) {
//							mSixSelect.setVisibility(View.VISIBLE);
//							selectArray[1] = false;
//						}
//						if (mDeadHour < 7 && !bookArray[2]) {
//							mSevenSelect.setVisibility(View.VISIBLE);
//							selectArray[2] = false;
//						}
//						if (mDeadHour < 8 && !bookArray[3]) {
//							mEightSelect.setVisibility(View.VISIBLE);
//							selectArray[3] = false;
//						}
//						if (mDeadHour < 9 && !bookArray[4]) {
//							mNineSelect.setVisibility(View.VISIBLE);
//							selectArray[4] = false;
//						}
//						if (mDeadHour < 10 && !bookArray[5]) {
//							mTenSelect.setVisibility(View.VISIBLE);
//							selectArray[5] = false;
//						}
//						if (mDeadHour < 11 && !bookArray[6]) {
//							mElevenSelect.setVisibility(View.VISIBLE);
//							selectArray[6] = false;
//						}
//					} else {
//						if (!bookArray[0]) {
//							mFiveSelect.setVisibility(View.VISIBLE);
//							selectArray[0] = false;
//						}
//						if (!bookArray[1]) {
//							mSixSelect.setVisibility(View.VISIBLE);
//							selectArray[1] = false;
//						}
//						if (!bookArray[2]) {
//							mSevenSelect.setVisibility(View.VISIBLE);
//							selectArray[2] = false;
//						}
//						if (!bookArray[3]) {
//							mEightSelect.setVisibility(View.VISIBLE);
//							selectArray[3] = false;
//						}
//						if (!bookArray[4]) {
//							mNineSelect.setVisibility(View.VISIBLE);
//							selectArray[4] = false;
//						}
//						if (!bookArray[5]) {
//							mTenSelect.setVisibility(View.VISIBLE);
//							selectArray[5] = false;
//						}
//						if (!bookArray[6]) {
//							mElevenSelect.setVisibility(View.VISIBLE);
//							selectArray[6] = false;
//						}
//					}
//
//					boolean hasSelect = false;
//					for (int i = 0; i < 7; i++) {
//						if (!selectArray[i]) {
//							hasSelect = true;
//							break;
//						}
//					}
//					if (hasSelect) {
//						mMorSelectAllImg.setSelected(true);
//						mMorSelectAllTv.setText("取消全选");
//					} else {
//						Toast.makeText(mActivity, "当前时间段不可用", Toast.LENGTH_SHORT).show();
//					}
//				}
//			}
//		});

//		mAftSelectAll.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (mAftSelectAllImg.isSelected()) {
//					mAftSelectAllImg.setSelected(false);
//					mAftSelectAllTv.setText("全选");
//
//					if (equalsDate(calToday.getTime(), calSelected.getTime())) {
//						/*
//						 * 今天
//						 */
//
//						if (mDeadHour < 12 && !bookArray[7]) {
//							mTwelveSelect.setVisibility(View.INVISIBLE);
//							selectArray[7] = true;
//						}
//						if (mDeadHour < 13 && !bookArray[8]) {
//							mThirteenSelect.setVisibility(View.INVISIBLE);
//							selectArray[8] = true;
//						}
//						if (mDeadHour < 14 && !bookArray[9]) {
//							mFourteenSelect.setVisibility(View.INVISIBLE);
//							selectArray[9] = true;
//						}
//						if (mDeadHour < 15 && !bookArray[10]) {
//							mFifteenSelect.setVisibility(View.INVISIBLE);
//							selectArray[10] = true;
//						}
//						if (mDeadHour < 16 && !bookArray[11]) {
//							mSixteenSelect.setVisibility(View.INVISIBLE);
//							selectArray[11] = true;
//						}
//						if (mDeadHour < 17 && !bookArray[12]) {
//							mSeventeenSelect.setVisibility(View.INVISIBLE);
//							selectArray[12] = true;
//						}
//						if (mDeadHour < 18 && !bookArray[13]) {
//							mEighteenSelect.setVisibility(View.INVISIBLE);
//							selectArray[13] = true;
//						}
//					} else {
//						if (!bookArray[7]) {
//							mTwelveSelect.setVisibility(View.INVISIBLE);
//							selectArray[7] = true;
//						}
//						if (!bookArray[8]) {
//							mThirteenSelect.setVisibility(View.INVISIBLE);
//							selectArray[8] = true;
//						}
//						if (!bookArray[9]) {
//							mFourteenSelect.setVisibility(View.INVISIBLE);
//							selectArray[9] = true;
//						}
//						if (!bookArray[10]) {
//							mFifteenSelect.setVisibility(View.INVISIBLE);
//							selectArray[10] = true;
//						}
//						if (!bookArray[11]) {
//							mSixteenSelect.setVisibility(View.INVISIBLE);
//							selectArray[11] = true;
//						}
//						if (!bookArray[12]) {
//							mSeventeenSelect.setVisibility(View.INVISIBLE);
//							selectArray[12] = true;
//						}
//						if (!bookArray[13]) {
//							mEighteenSelect.setVisibility(View.INVISIBLE);
//							selectArray[13] = true;
//						}
//					}
//				} else {
//					if (equalsDate(calToday.getTime(), calSelected.getTime())) {
//						/*
//						 * 今天
//						 */
//
//						if (mDeadHour < 12 && !bookArray[7]) {
//							mTwelveSelect.setVisibility(View.VISIBLE);
//							selectArray[7] = false;
//						}
//						if (mDeadHour < 13 && !bookArray[8]) {
//							mThirteenSelect.setVisibility(View.VISIBLE);
//							selectArray[8] = false;
//						}
//						if (mDeadHour < 14 && !bookArray[9]) {
//							mFourteenSelect.setVisibility(View.VISIBLE);
//							selectArray[9] = false;
//						}
//						if (mDeadHour < 15 && !bookArray[10]) {
//							mFifteenSelect.setVisibility(View.VISIBLE);
//							selectArray[10] = false;
//						}
//						if (mDeadHour < 16 && !bookArray[11]) {
//							mSixteenSelect.setVisibility(View.VISIBLE);
//							selectArray[11] = false;
//						}
//						if (mDeadHour < 17 && !bookArray[12]) {
//							mSeventeenSelect.setVisibility(View.VISIBLE);
//							selectArray[12] = false;
//						}
//						if (mDeadHour < 18 && !bookArray[13]) {
//							mEighteenSelect.setVisibility(View.VISIBLE);
//							selectArray[13] = false;
//						}
//					} else {
//						if (!bookArray[7]) {
//							mTwelveSelect.setVisibility(View.VISIBLE);
//							selectArray[7] = false;
//						}
//						if (!bookArray[8]) {
//							mThirteenSelect.setVisibility(View.VISIBLE);
//							selectArray[8] = false;
//						}
//						if (!bookArray[9]) {
//							mFourteenSelect.setVisibility(View.VISIBLE);
//							selectArray[9] = false;
//						}
//						if (!bookArray[10]) {
//							mFifteenSelect.setVisibility(View.VISIBLE);
//							selectArray[10] = false;
//						}
//						if (!bookArray[11]) {
//							mSixteenSelect.setVisibility(View.VISIBLE);
//							selectArray[11] = false;
//						}
//						if (!bookArray[12]) {
//							mSeventeenSelect.setVisibility(View.VISIBLE);
//							selectArray[12] = false;
//						}
//						if (!bookArray[13]) {
//							mEighteenSelect.setVisibility(View.VISIBLE);
//							selectArray[13] = false;
//						}
//					}
//
//					boolean hasSelect = false;
//					for (int i = 7; i < 14; i++) {
//						if (!selectArray[i]) {
//							hasSelect = true;
//							break;
//						}
//					}
//					if (hasSelect) {
//						mAftSelectAllImg.setSelected(true);
//						mAftSelectAllTv.setText("取消全选");
//					} else {
//						Toast.makeText(mActivity, "当前时间段不可用", Toast.LENGTH_SHORT).show();
//					}
//				}
//			}
//		});
//
//		mNigSelectAll.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (mNigSelectAllImg.isSelected()) {
//					mNigSelectAllImg.setSelected(false);
//					mNigSelectAllTv.setText("全选");
//
//					if (equalsDate(calToday.getTime(), calSelected.getTime())) {
//						if (mDeadHour < 19 && !bookArray[14]) {
//							mNineteenSelect.setVisibility(View.INVISIBLE);
//							selectArray[14] = true;
//						}
//						if (mDeadHour < 20 && !bookArray[15]) {
//							mTwentySelect.setVisibility(View.INVISIBLE);
//							selectArray[15] = true;
//						}
//						if (mDeadHour < 21 && !bookArray[16]) {
//							mTwentyOneSelect.setVisibility(View.INVISIBLE);
//							selectArray[16] = true;
//						}
//						if (mDeadHour < 22 && !bookArray[17]) {
//							mTwentyTwoSelect.setVisibility(View.INVISIBLE);
//							selectArray[17] = true;
//						}
//						if (mDeadHour < 23 && !bookArray[18]) {
//							mTwentyThreeSelect.setVisibility(View.INVISIBLE);
//							selectArray[18] = true;
//						}
//					} else {
//						if (!bookArray[14]) {
//							mNineteenSelect.setVisibility(View.INVISIBLE);
//							selectArray[14] = true;
//						}
//						if (!bookArray[15]) {
//							mTwentySelect.setVisibility(View.INVISIBLE);
//							selectArray[15] = true;
//						}
//						if (!bookArray[16]) {
//							mTwentyOneSelect.setVisibility(View.INVISIBLE);
//							selectArray[16] = true;
//						}
//						if (!bookArray[17]) {
//							mTwentyTwoSelect.setVisibility(View.INVISIBLE);
//							selectArray[17] = true;
//						}
//						if (!bookArray[18]) {
//							mTwentyThreeSelect.setVisibility(View.INVISIBLE);
//							selectArray[18] = true;
//						}
//					}
//				} else {
//					if (equalsDate(calToday.getTime(), calSelected.getTime())) {
//						if (mDeadHour < 19 && !bookArray[14]) {
//							mNineteenSelect.setVisibility(View.VISIBLE);
//							selectArray[14] = false;
//						}
//						if (mDeadHour < 20 && !bookArray[15]) {
//							mTwentySelect.setVisibility(View.VISIBLE);
//							selectArray[15] = false;
//						}
//						if (mDeadHour < 21 && !bookArray[16]) {
//							mTwentyOneSelect.setVisibility(View.VISIBLE);
//							selectArray[16] = false;
//						}
//						if (mDeadHour < 22 && !bookArray[17]) {
//							mTwentyTwoSelect.setVisibility(View.VISIBLE);
//							selectArray[17] = false;
//						}
//						if (mDeadHour < 23 && !bookArray[18]) {
//							mTwentyThreeSelect.setVisibility(View.VISIBLE);
//							selectArray[18] = false;
//						}
//					} else {
//						if (!bookArray[14]) {
//							mNineteenSelect.setVisibility(View.VISIBLE);
//							selectArray[14] = false;
//						}
//						if (!bookArray[15]) {
//							mTwentySelect.setVisibility(View.VISIBLE);
//							selectArray[15] = false;
//						}
//						if (!bookArray[16]) {
//							mTwentyOneSelect.setVisibility(View.VISIBLE);
//							selectArray[16] = false;
//						}
//						if (!bookArray[17]) {
//							mTwentyTwoSelect.setVisibility(View.VISIBLE);
//							selectArray[17] = false;
//						}
//						if (!bookArray[18]) {
//							mTwentyThreeSelect.setVisibility(View.VISIBLE);
//							selectArray[18] = false;
//						}
//					}
//
//					boolean hasSelect = false;
//					for (int i = 14; i < 19; i++) {
//						if (!selectArray[i]) {
//							hasSelect = true;
//							break;
//						}
//					}
//					if (hasSelect) {
//						mNigSelectAllImg.setSelected(true);
//						mNigSelectAllTv.setText("取消全选");
//					} else {
//						Toast.makeText(mActivity, "当前时间段不可用", Toast.LENGTH_SHORT).show();
//					}
//				}
//			}
//		});
		/*
		 * stop class
		 */
//		mAllDaySetClose.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//
////				int isrest = 0; //0:没有课，1：有开一节课以上
////				for (Schedule schedule : scheduleResult.getDatelist()) {
////					Date date2;
////					try {
////						date2 = TimeUtil.StringToDate(schedule.getDate());
////					} catch (Exception e) {
////						return;
////					}
////
////					if (equalsDate(calSelected.getTime(), date2)) {
////
////						if (schedule.getHour() == 0) {
////							/*
////							 * 全局设置
////							 */
//////							if (schedule.getState() == 0) {
//////								// rest
//////								mAllDaySetClose.setText("发布课程");
//////								mAllDaySetClose.setBackgroundResource(R.drawable.shape_green_round);
//////								isAllDayOpen = false;
//////							} else if (schedule.getState() == 1) {
//////								mAllDaySetClose.setText("当天停课");
//////								mAllDaySetClose.setBackgroundResource(R.drawable.shape_red_round);
//////								isAllDayOpen = true;
//////							}
////
////						} else {
////							if (schedule.getIsrest() == 0)
////							{
////								isrest = 1;
////							}
////						}
////					}
////				}
////				
////				if (isrest == 0)
////				{
////					Toast.makeText(getActivity(), "至少有一节课是开课状态才能发布", 0).show();
////					return;
////				}
//				
//				if (isAllDayOpen) {
//					new ChangeAllDayScheduleTask("2").execute();
//				} else {
//					showSetDefaultDialog();
//				}
//			}
//		});

//		mMorningSSet.findViewById(R.id.five_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 5 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[0]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[0]) {
//						mFiveSelect.setVisibility(View.INVISIBLE);
//						selectArray[0] = true;
//						fixMorAllSelect();
//					} else {
//						mFiveSelect.setVisibility(View.VISIBLE);
//						selectArray[0] = false;
//					}
//				}
//			}
//		});
//		mMorningSSet.findViewById(R.id.six_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 6 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[1]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[1]) {
//						mSixSelect.setVisibility(View.INVISIBLE);
//						selectArray[1] = true;
//						fixMorAllSelect();
//					} else {
//						mSixSelect.setVisibility(View.VISIBLE);
//						selectArray[1] = false;
//					}
//				}
//			}
//		});
//		mMorningSSet.findViewById(R.id.seven_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 7 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[2]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[2]) {
//						mSevenSelect.setVisibility(View.INVISIBLE);
//						selectArray[2] = true;
//						fixMorAllSelect();
//					} else {
//						mSevenSelect.setVisibility(View.VISIBLE);
//						selectArray[2] = false;
//					}
//				}
//			}
//		});
//		mMorningSSet.findViewById(R.id.eight_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 8 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[3]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[3]) {
//						mEightSelect.setVisibility(View.INVISIBLE);
//						selectArray[3] = true;
//						fixMorAllSelect();
//					} else {
//						mEightSelect.setVisibility(View.VISIBLE);
//						selectArray[3] = false;
//					}
//				}
//			}
//		});
//		mMorningSSet.findViewById(R.id.nine_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 9 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[4]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[4]) {
//						mNineSelect.setVisibility(View.INVISIBLE);
//						selectArray[4] = true;
//						fixMorAllSelect();
//					} else {
//						mNineSelect.setVisibility(View.VISIBLE);
//						selectArray[4] = false;
//					}
//				}
//			}
//		});
//		mMorningSSet.findViewById(R.id.ten_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 10 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[5]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[5]) {
//						mTenSelect.setVisibility(View.INVISIBLE);
//						selectArray[5] = true;
//						fixMorAllSelect();
//					} else {
//						mTenSelect.setVisibility(View.VISIBLE);
//						selectArray[5] = false;
//					}
//				}
//			}
//		});
//
//		mMorningSSet.findViewById(R.id.eleven_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 11 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[6]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[6]) {
//						mElevenSelect.setVisibility(View.INVISIBLE);
//						selectArray[6] = true;
//					} else {
//						mElevenSelect.setVisibility(View.VISIBLE);
//						selectArray[6] = false;
//					}
//				}
//			}
//		});
//
//		mAfternoonSet.findViewById(R.id.twelve_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 12 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[7]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[7]) {
//						mTwelveSelect.setVisibility(View.INVISIBLE);
//						selectArray[7] = true;
//						fixAftAllSelect();
//					} else {
//						mTwelveSelect.setVisibility(View.VISIBLE);
//						selectArray[7] = false;
//					}
//				}
//			}
//		});
//		mAfternoonSet.findViewById(R.id.thirteen_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 13 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[8]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[8]) {
//						mThirteenSelect.setVisibility(View.INVISIBLE);
//						selectArray[8] = true;
//						fixAftAllSelect();
//					} else {
//						mThirteenSelect.setVisibility(View.VISIBLE);
//						selectArray[8] = false;
//					}
//				}
//			}
//		});
//		mAfternoonSet.findViewById(R.id.fourteen_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 14 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[9]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[9]) {
//						mFourteenSelect.setVisibility(View.INVISIBLE);
//						selectArray[9] = true;
//						fixAftAllSelect();
//					} else {
//						mFourteenSelect.setVisibility(View.VISIBLE);
//						selectArray[9] = false;
//					}
//				}
//			}
//		});
//		mAfternoonSet.findViewById(R.id.fifteen_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 15 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[10]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[10]) {
//						mFifteenSelect.setVisibility(View.INVISIBLE);
//						selectArray[10] = true;
//						fixAftAllSelect();
//					} else {
//						mFifteenSelect.setVisibility(View.VISIBLE);
//						selectArray[10] = false;
//					}
//				}
//			}
//		});
//		mAfternoonSet.findViewById(R.id.sixteen_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 16 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[11]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[11]) {
//						mSixteenSelect.setVisibility(View.INVISIBLE);
//						selectArray[11] = true;
//						fixAftAllSelect();
//					} else {
//						mSixteenSelect.setVisibility(View.VISIBLE);
//						selectArray[11] = false;
//					}
//				}
//			}
//		});
//		mAfternoonSet.findViewById(R.id.seventeen_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 17 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[12]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[12]) {
//						mSeventeenSelect.setVisibility(View.INVISIBLE);
//						selectArray[12] = true;
//						fixAftAllSelect();
//					} else {
//						mSeventeenSelect.setVisibility(View.VISIBLE);
//						selectArray[12] = false;
//					}
//				}
//			}
//		});
//
//		mAfternoonSet.findViewById(R.id.eighteen_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 18 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[13]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[13]) {
//						mEighteenSelect.setVisibility(View.INVISIBLE);
//						selectArray[13] = true;
//						fixAftAllSelect();
//					} else {
//						mEighteenSelect.setVisibility(View.VISIBLE);
//						selectArray[13] = false;
//					}
//				}
//			}
//		});
//
//		mNightSet.findViewById(R.id.nineteen_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 19 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[14]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[14]) {
//						mNineteenSelect.setVisibility(View.INVISIBLE);
//						selectArray[14] = true;
//						fixNigAllSelect();
//					} else {
//						mNineteenSelect.setVisibility(View.VISIBLE);
//						selectArray[14] = false;
//					}
//				}
//			}
//		});
//		mNightSet.findViewById(R.id.twenty_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 20 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[15]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[15]) {
//						mTwentySelect.setVisibility(View.INVISIBLE);
//						selectArray[15] = true;
//						fixNigAllSelect();
//					} else {
//						mTwentySelect.setVisibility(View.VISIBLE);
//						selectArray[15] = false;
//					}
//				}
//
//			}
//		});
//		mNightSet.findViewById(R.id.twentyone_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 21 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[16]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[16]) {
//						mTwentyOneSelect.setVisibility(View.INVISIBLE);
//						selectArray[16] = true;
//						fixNigAllSelect();
//					} else {
//						mTwentyOneSelect.setVisibility(View.VISIBLE);
//						selectArray[16] = false;
//					}
//				}
//			}
//		});
//		mNightSet.findViewById(R.id.twentytwo_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 22 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[17]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[17]) {
//						mTwentyTwoSelect.setVisibility(View.INVISIBLE);
//						selectArray[17] = true;
//						fixNigAllSelect();
//					} else {
//						mTwentyTwoSelect.setVisibility(View.VISIBLE);
//						selectArray[17] = false;
//					}
//				}
//			}
//		});
//		mNightSet.findViewById(R.id.twentythree_part).setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 23 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能选择", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[18]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					if (!selectArray[18]) {
//						mTwentyThreeSelect.setVisibility(View.INVISIBLE);
//						selectArray[18] = true;
//						fixNigAllSelect();
//					} else {
//						mTwentyThreeSelect.setVisibility(View.VISIBLE);
//						selectArray[18] = false;
//					}
//				}
//			}
//		});
//
//		mFiveGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 5 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[0]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 5).putExtra("rest", stateArray[0]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//
//		mSixGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 6 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[1]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 6).putExtra("rest", stateArray[1]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//
//		mSevenGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 7 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[2]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 7).putExtra("rest", stateArray[2]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//
//		mEightGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 8 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[3]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 8).putExtra("rest", stateArray[3]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//		mNineGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 9 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[4]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 9).putExtra("rest", stateArray[4]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//		mTenGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 10 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[5]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 10).putExtra("rest", stateArray[5]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//		mElevenGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 11 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[6]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 11).putExtra("rest", stateArray[6]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//		mTwelveGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 12 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[7]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 12).putExtra("rest", stateArray[7]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//		mThirteenGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 13 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[8]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 13).putExtra("rest", stateArray[8]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//		mFourteenGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 14 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[9]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 14).putExtra("rest", stateArray[9]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//		mFifteenGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 15 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[10]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 15).putExtra("rest", stateArray[10]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//		mSixteenGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 16 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[11]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 16).putExtra("rest", stateArray[11]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//		mSeventeenGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 17 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[12]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 17).putExtra("rest", stateArray[12]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//		mEighteenGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 18 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[13]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 18).putExtra("rest", stateArray[13]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//		mNineteenGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 19 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[14]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 19).putExtra("rest", stateArray[14]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//		mTwentyGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 20 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[15]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 20).putExtra("rest", stateArray[15]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//
//		mTwentyOneGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 21 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[16]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 21).putExtra("rest", stateArray[16]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//
//		mTwentyTwoGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 22 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[17]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 22).putExtra("rest", stateArray[17]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});
//
//		mTwentyThreeGear.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				if (equalsDate(calToday.getTime(), calSelected.getTime()) && 23 <= mDeadHour) {
//					Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
//				} else {
//					if (bookArray[18]) {
//						Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
//						return;
//					}
//					mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("single", 23).putExtra("rest", stateArray[18]).putExtra("result", scheduleResult)
//							.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//				}
//			}
//		});

//		mMorMultiSet.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				boolean isRest = false;
//				boolean hasSelect = false;
//				for (int j = 0; j < 7; j++) {
//					if (!selectArray[j]) {
//						hasSelect = true;
//						break;
//					}
//				}
//				if (!hasSelect) {
//					Toast.makeText(mActivity, "请选择时间", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				StringBuilder mor = new StringBuilder();
//				for (int i = 0; i <= 6; i++) {
//					if (!selectArray[i]) {
//						if (equalsDate(calToday.getTime(), calSelected.getTime()) && (i + 5) <= mDeadHour) {
//							mor.append("0");
//						} else {
//							if (bookArray[i])
//								mor.append("0");
//							else {
//								mor.append("1");
//								if (!stateArray[i])
//									isRest = true;
//							}
//						}
//						if (!stateArray[i])
//							isRest = true;
//					} else {
//						mor.append("0");
//					}
//				}
//
//				if (!mor.toString().contains("1")) {
//					Toast.makeText(mActivity, "当前时间段无法设置", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("mor", mor.toString()).putExtra("rest", !isRest).putExtra("result", scheduleResult)
//						.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//			}
//		});
//
//		mAftMultiSet.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				
//				boolean isRest = false;
//				boolean hasSelect = false;
//				for (int j = 7; j < 14; j++) {
//					if (!selectArray[j]) {
//						hasSelect = true;
//						break;
//					}
//				}
//				if (!hasSelect) {
//					Toast.makeText(mActivity, "请选择时间", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				StringBuilder mor = new StringBuilder();
//				for (int i = 7; i <= 13; i++) {
//					if (!selectArray[i]) {
//						if (equalsDate(calToday.getTime(), calSelected.getTime()) && (i + 5) <= mDeadHour) {
//							mor.append("0");
//						} else {
//							if (bookArray[i])
//								mor.append("0");
//							else {
//								mor.append("1");
//								if (!stateArray[i])
//									isRest = true;
//							}
//						}
//					} else {
//						mor.append("0");
//					}
//
//				}
//
//				if (!mor.toString().contains("1")) {
//					Toast.makeText(mActivity, "当前时间段无法设置", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("aft", mor.toString()).putExtra("rest", !isRest).putExtra("result", scheduleResult)
//						.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//			}
//		});
//
//		mNigMultiSet.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				
//				if (!finishLoadingData) {
//					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				
//				boolean isRest = false;
//				boolean hasSelect = false;
//				for (int j = 14; j < 19; j++) {
//					if (!selectArray[j]) {
//						hasSelect = true;
//						break;
//					}
//				}
//				if (!hasSelect) {
//					Toast.makeText(mActivity, "请选择时间", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				StringBuilder mor = new StringBuilder();
//				for (int i = 14; i <= 18; i++) {
//					if (!selectArray[i]) {
//						if (equalsDate(calToday.getTime(), calSelected.getTime()) && (i + 5) <= mDeadHour) {
//							mor.append("0");
//						} else {
//							if (bookArray[i])
//								mor.append("0");
//							else {
//								if (!stateArray[i])
//									isRest = true;
//								mor.append("1");
//							}
//						}
//						if (!stateArray[i])
//							isRest = true;
//					} else {
//						mor.append("0");
//					}
//				}
//
//				if (!mor.toString().contains("1")) {
//					Toast.makeText(mActivity, "当前时间段无法设置", Toast.LENGTH_SHORT).show();
//					return;
//				}
//
//				mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putExtra("nig", mor.toString()).putExtra("rest", !isRest).putExtra("result", scheduleResult)
//						.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);
//			}
//		});

		mHangingArrow.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_UP);
					}
				});
			}
		});

		mArrowUp.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
					}
				});
			}
		});
	}

	Handler handler = new Handler() {

	};

	@Override
	public void onDetach() {
		super.onDetach();
		if (handler != null) {
			handler.removeCallbacks(null);
			handler = null;
		}
	};

//	protected void fixMorAllSelect() {
//		// 修正全选
//		boolean hasSelect = false;
//		for (int i = 0; i < 7; i++) {
//			if (!selectArray[i]) {
//				hasSelect = true;
//				break;
//			}
//		}
//		if (!hasSelect) {
//			mMorSelectAllImg.setSelected(false);
//			mMorSelectAllTv.setText("全选");
//		}
//
//	}
//
//	protected void fixAftAllSelect() {
//		// 修正全选
//		boolean hasSelect = false;
//		for (int i = 7; i < 14; i++) {
//			if (!selectArray[i]) {
//				hasSelect = true;
//				break;
//			}
//		}
//		if (!hasSelect) {
//			mAftSelectAllImg.setSelected(false);
//			mAftSelectAllTv.setText("全选");
//		}
//
//	}
//
//	protected void fixNigAllSelect() {
//		// 修正全选
//		boolean hasSelect = false;
//		for (int i = 14; i < 19; i++) {
//			if (!selectArray[i]) {
//				hasSelect = true;
//				break;
//			}
//		}
//		if (!hasSelect) {
//			mNigSelectAllImg.setSelected(false);
//			mNigSelectAllTv.setText("全选");
//		}
//
//	}

	boolean isAllDayOpen;
	Dialog setDefaultDialog;
	boolean defaultSel = false;
	ImageView select;

	protected void showSetDefaultDialog() {
		defaultSel = false;

		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		setDefaultDialog = builder.create();
		setDefaultDialog.show();
		setDefaultDialog.setContentView(R.layout.dialog_setdefault);
		setDefaultDialog.setCanceledOnTouchOutside(true);
		setDefaultDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		// WindowManager.LayoutParams params =
		// setDialog.getWindow().getAttributes();

		Button confirm = (Button) setDefaultDialog.findViewById(R.id.confirm);
		Button cancel = (Button) setDefaultDialog.findViewById(R.id.cancel);
		select = (ImageView) setDefaultDialog.findViewById(R.id.default_select);
		LinearLayout selLayout = (LinearLayout) setDefaultDialog.findViewById(R.id.default_select_layout);

		selLayout.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (defaultSel)
					select.setImageResource(R.drawable.all_unselected);
				else
					select.setImageResource(R.drawable.all_selected);

				defaultSel = !defaultSel;
			}
		});

		confirm.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				new ChangeAllDayScheduleTask("1").execute();
				if (defaultSel)
					new SetDefaultTask().execute();
				setDefaultDialog.dismiss();
			}
		});

		cancel.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				setDefaultDialog.dismiss();
			}
		});

	}

	Dialog goToDefaultDialog;

	private void goToDefault() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		goToDefaultDialog = builder.create();
		goToDefaultDialog.show();
		goToDefaultDialog.setContentView(R.layout.dialog_confirm_address);
		goToDefaultDialog.setCanceledOnTouchOutside(true);
		goToDefaultDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		// WindowManager.LayoutParams params =
		// setDialog.getWindow().getAttributes();

		TextView cancel = (TextView) goToDefaultDialog.findViewById(R.id.confirm);

		cancel.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				startActivity(new Intent(mActivity, AddressSetActivity_.class));
				goToDefaultDialog.dismiss();
			}
		});
	}

	private void initData() {
		UpdateStartDateForMonth();

		slideLeftIn = AnimationUtils.loadAnimation(mActivity, R.anim.slide_left_in);
		slideRightIn = AnimationUtils.loadAnimation(mActivity, R.anim.slide_right_in);
		slideLeftOut = AnimationUtils.loadAnimation(mActivity, R.anim.slide_left_out);
		slideRightOut = AnimationUtils.loadAnimation(mActivity, R.anim.slide_right_out);

		// slideLeftOut.setAnimationListener(animationListener);
		// slideRightOut.setAnimationListener(animationListener);

		View view = generateContentView();
		mCalendarLayout.addView(view);

		/*
		 * init animation
		 */
		mDisappearAnimation = new AlphaAnimation(1, 0);
		mDisappearAnimation.setDuration(500);
		mDisappearAnimation.setFillAfter(true);
		mAppearAnimation = new AlphaAnimation(0, 1);
		mAppearAnimation.setDuration(500);
		mAppearAnimation.setFillAfter(true);

		setTimes();

		ballState = gAdapter.getBallState();

	}

	@Override
	public void onResume() {
		super.onResume();
		// if (!isFirstReq && mLoadingDialog != null) {
		// mLoadingDialog.show();
		// }
		// mScrollView.setRefreshing();
	}

	private void setTimes() {
//		mFiveTime.setText("05:00");
//		mSixTime.setText("06:00");
//		mSevenTime.setText("07:00");
//		mEightTime.setText("08:00");
//		mNineTime.setText("09:00");
//		mTenTime.setText("10:00");
//		mElevenTime.setText("11:00");
//		mTwelveTime.setText("12:00");
//		mThirteenTime.setText("13:00");
//		mFourteenTime.setText("14:00");
//		mFifteenTime.setText("15:00");
//		mSixteenTime.setText("16:00");
//		mSeventeenTime.setText("17:00");
//		mEighteenTime.setText("18:00");
//		mNineteenTime.setText("19:00");
//		mTwentyTime.setText("20:00");
//		mTwentyOneTime.setText("21:00");
//		mTwentyTwoTime.setText("22:00");
//		mTwentyThreeTime.setText("23:00");
//		// default rest time 12:00 & 18:00
//		stateArray[7] = true;
//		stateArray[13] = true;
//		mTwelveTime.setTextColor(Color.parseColor("#d2d2d2"));
//		mTwelvePrice.setTextColor(Color.parseColor("#d2d2d2"));
//		mTwelvePrice.setText("未开课");
//		mTwelvePrice.setBackgroundColor(Color.parseColor("#e5e5e5"));
//		mEighteenTime.setTextColor(Color.parseColor("#d2d2d2"));
//		mEighteenPrice.setTextColor(Color.parseColor("#d2d2d2"));
//		mEighteenPrice.setBackgroundColor(Color.parseColor("#e5e5e5"));
//		mEighteenPrice.setText("未开课");
	}

	void goToLastMonth() {
		/*
		 * current can not go to month before
		 */
		if (calToday.get(Calendar.MONTH) == iMonthViewCurrentMonth)
			return;
		if (calToday.get(Calendar.MONTH) == (iMonthViewCurrentMonth - 1)) {
			mToLastMonth.setClickable(false);
			mToLastMonth.setImageResource(R.drawable.triangle_grey_left);
			mToNextMonth.setClickable(true);
			mToNextMonth.setImageResource(R.drawable.selector_triangle);
		}
		viewFlipper.setInAnimation(slideRightIn);
		viewFlipper.setOutAnimation(slideRightOut);
		setPrevViewItem();
		viewFlipper.showPrevious();
	}

	void goToNextMonth() {
		if (calToday.get(Calendar.MONTH) == (iMonthViewCurrentMonth + 1))
			return;
		if (calToday.get(Calendar.MONTH) == iMonthViewCurrentMonth) {
			mToLastMonth.setClickable(true);
			mToLastMonth.setImageResource(R.drawable.selector_triangle_left);
			mToNextMonth.setClickable(false);
			mToNextMonth.setImageResource(R.drawable.triangle_grey);
		}
		viewFlipper.setInAnimation(slideLeftIn);
		viewFlipper.setOutAnimation(slideLeftOut);
		setNextViewItem();
		viewFlipper.showNext();
	}

	// 上一个月
	private void setPrevViewItem() {
		iMonthViewCurrentMonth--;// 当前选择月--
		// 如果当前月为负数的话显示上一年
		if (iMonthViewCurrentMonth == -1) {
			iMonthViewCurrentMonth = 11;
			iMonthViewCurrentYear--;
		}
		calStartDate.setTime(calToday.getTime());
		String s = calStartDate.get(Calendar.YEAR) + "年" + (NumberHelper.LeftPad_Tow_Zero(calStartDate.get(Calendar.MONTH) + 1) + "月");
		mTitle.setText(s);

		calSelected = calToday;
		gAdapter.setSelectedDate(calSelected);
		gSelectAdapter = gAdapter;
		handler.post(new Runnable() {
			@Override
			public void run() {
				mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_UP);
			}
		});

		setSelectLine(getSelectLine(calSelected));
		new RefreshBallStateTask().execute();
	}

	// 当月
	private void setToDayViewItem() {
		calSelected.setTimeInMillis(calToday.getTimeInMillis());
		calSelected.setFirstDayOfWeek(iFirstDayOfWeek);
		calStartDate.setTimeInMillis(calToday.getTimeInMillis());
		calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);

	}

	// 下一个月
	private void setNextViewItem() {
		iMonthViewCurrentMonth++;
		if (iMonthViewCurrentMonth == 12) {
			iMonthViewCurrentMonth = 0;
			iMonthViewCurrentYear++;
		}
		calStartDate.set(Calendar.DAY_OF_MONTH, 1);
		calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
		calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);
		String s = calStartDate.get(Calendar.YEAR) + "年" + (NumberHelper.LeftPad_Tow_Zero(calStartDate.get(Calendar.MONTH) + 1) + "月");
		mTitle.setText(s);

		calSelected = calStartDate;
		gAdapterNextMonth.setSelectedDate(calSelected);
		gSelectAdapter = gAdapterNextMonth;
		handler.post(new Runnable() {
			@Override
			public void run() {
				mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_UP);
			}
		});
		setSelectLine(getSelectLine(calSelected));
		new RefreshBallStateTask().execute();
	}

	AnimationListener animationListener = new AnimationListener() {
		@Override
		public void onAnimationStart(Animation animation) {
			String s = calStartDate.get(Calendar.YEAR) + "年" + (NumberHelper.LeftPad_Tow_Zero(calStartDate.get(Calendar.MONTH) + 1) + "月");
			mTitle.setText(s);
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			// 当动画完成后调用
			CreateGirdView();
		}
	};

	/*
	 * gridview 行数
	 */
	int lineNum;

	private void CreateGirdView() {
		Calendar tempSelected2 = Calendar.getInstance();
		Calendar tempSelected3 = Calendar.getInstance();
		tempSelected2.setTime(calStartDate.getTime());
		tempSelected3.setTime(calStartDate.getTime());

		gCurrnetMonthView = new CalendarGridView(mActivity);
		gAdapter = new CalendarGridViewAdapter(mActivity, tempSelected2);
		gCurrnetMonthView.setAdapter(gAdapter);

		gView3 = new CalendarGridView(mActivity);
		tempSelected3.add(Calendar.MONTH, 1);
		gAdapterNextMonth = new CalendarGridViewAdapter(mActivity, tempSelected3);
		gView3.setAdapter(gAdapterNextMonth);

		/*
		 * 动态设置gridview高度
		 */
		lineNum = getWeeksInMonth();
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (Settings.DISPLAY_WIDTH - 12) / 7 * lineNum + (lineNum - 1) * 2);
		gCurrnetMonthView.setLayoutParams(param);
		gView3.setLayoutParams(param);

		if (viewFlipper.getChildCount() != 0) {
			viewFlipper.removeAllViews();
		}

		/*
		 * 点击回调
		 */
		NotifyDateSelect mNotifyDateSelect = new NotifyDateSelect() {

			@Override
			public void notify(Calendar calendar) {
				
				calSelected = calendar;
				
				/*
				 *跳转到时间段界面 
				 */
				
//				Intent intent = new Intent(mActivity,ActivityDateSet.class);
//				startActivity(intent);
				
				/*
				 *在当前页面设置时间段
				 */
				checkUpdate(calendar);
				/*
				 * 当点击位置处于上次点击下方时，如果已经显示就隐藏
				 */
				if (getSelectLine(calendar) > selectItemLine) {
					mHangingContent.setVisibility(View.INVISIBLE);
				}
				setSelectLine(getSelectLine(calendar)); // 刷新行数
				initialHangingContent(calendar, calToday); // 刷新悬停内容
			}
		};
		gAdapter.setmNotifyDateSelect(mNotifyDateSelect);
		gAdapterNextMonth.setmNotifyDateSelect(mNotifyDateSelect);

		viewFlipper.addView(gCurrnetMonthView);
		viewFlipper.addView(gView3);

		// List<View> list = new ArrayList<View>();
		// list.add(gCurrnetMonthView);
		// list.add(gView3);
		// mVgAdapter = new DateViewpagerAdapter(list);
		// mViewPager.setAdapter(mVgAdapter);
		// mViewPager.getLayoutParams().height = (Settings.DISPLAY_WIDTH - 12) /
		// 7 * lineNum + (lineNum - 1) * 2;
	}

	DateViewpagerAdapter mVgAdapter;

	/*
	 * 获取一个月中有几个星期
	 */
	private int getWeeksInMonth() {
		Calendar temp = Calendar.getInstance();
		if (calStartDate.get(Calendar.MONTH) == 11) // 设置年份为下一年
			temp.set(Calendar.YEAR, calStartDate.get(Calendar.YEAR) + 1);
		else {
			temp.set(Calendar.YEAR, calStartDate.get(Calendar.YEAR));
		}
		temp.set(Calendar.MONTH, iMonthViewCurrentMonth);
		temp.set(Calendar.DATE, 1); // 设置成当月第一天
		int weekday = temp.get(Calendar.DAY_OF_WEEK); // 获取第一天是星期几
		int maxdays = temp.getActualMaximum(Calendar.DAY_OF_MONTH); // 获取一个月总共几天
		if (weekday == 1) {
			// sunday
		} else {
			maxdays = maxdays + weekday - 1;
		}

		if (maxdays % 7 == 0) {
			return maxdays / 7;
		} else {
			return maxdays / 7 + 1;
		}
	}

	/*
	 * 获取选中的item所在行数-1(上方行数)
	 */
	int selectItemLine;

	protected int getSelectLine(Calendar selectCalendar) {
		int days = selectCalendar.get(Calendar.DAY_OF_MONTH);
		if (selectCalendar.get(Calendar.DAY_OF_WEEK) == 1) {
			days--;
		} else {
			days -= selectCalendar.get(Calendar.DAY_OF_WEEK);
		}
		if (days <= 0)
			return 0;
		if (days % 7 != 0) {
			return days / 7 + 1;
		} else {
			return days / 7;
		}
	}

	protected void setSelectLine(int lines) {
		selectItemLine = lines;
	}

	// 根据改变的日期更新日历
	// 填充日历控件用
	private void UpdateStartDateForMonth() {
		calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
		iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月
		iMonthViewCurrentYear = calStartDate.get(Calendar.YEAR);// 得到当前日历显示的年

		String s = calStartDate.get(Calendar.YEAR) + "年" + (NumberHelper.LeftPad_Tow_Zero(calStartDate.get(Calendar.MONTH) + 1) + "月");
		mTitle.setText(s);

		// 星期一是2 星期天是1 填充剩余天数
		int iDay = 0;
		int iFirstDayOfWeek = Calendar.MONDAY;
		int iStartDay = iFirstDayOfWeek;
		if (iStartDay == Calendar.MONDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}
		if (iStartDay == Calendar.SUNDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}
		calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);

	}

	// 生成内容视图
	private View generateContentView() {
		viewFlipper = new ViewFlipper(mActivity);
		viewFlipper.setId(calLayoutID);

		mainLayout = new RelativeLayout(mActivity); // 创建一个布局（整体内容）
		RelativeLayout.LayoutParams params_main = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mainLayout.setLayoutParams(params_main);
		mainLayout.setId(mainLayoutID);

		calStartDate = getCalendarStartDate();

		CreateGirdView();

		RelativeLayout.LayoutParams params_cal = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		viewFlipper.setPadding(0, 0, 0, 0);
		mainLayout.addView(viewFlipper, params_cal);

		return mainLayout;
	}

	private Calendar getCalendarStartDate() {
		calToday.setTimeInMillis(System.currentTimeMillis());
		calToday.setFirstDayOfWeek(iFirstDayOfWeek);

		if (calSelected.getTimeInMillis() == 0) {
			calStartDate.setTimeInMillis(System.currentTimeMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		} else {
			calStartDate.setTimeInMillis(calSelected.getTimeInMillis());
			calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
		}

		return calStartDate;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	};

	private class ReformTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			reformData();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			new RefreshBallStateTask().execute();
		}
	}

	/*
	 * get coach schedule
	 */

	// 获得当前日期的课程
	boolean isFirstReq = true;
	int mDeadHour;

	private class GetScheduleTask extends AsyncTask<Void, Void, GetScheduleResult> {
		JSONAccessor accessor = new JSONAccessor(mActivity, JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected GetScheduleResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetSchedule");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());

			return accessor.execute(Settings.SCHEDULE_URL, param, GetScheduleResult.class);
		}

		@Override
		protected void onPostExecute(GetScheduleResult result) {
			super.onPostExecute(result);
			if (mScrollView != null)
				mScrollView.onRefreshComplete();

			if (mLoadingDialog != null)
				mLoadingDialog.dismiss();

			if (result != null) {
				scheduleResult = result;

				mDeadHour = scheduleResult.getHour();
				if (result.getCode() == 1) {
					/*
					 * set today's calenday
					 */
					
					CoachApplication.mApplication.setMaxTays(result.getMaxdays());
					Calendar temp = null;
					if (result.getToday() != null) {
						try {
							temp = TimeUtil.StringToCalendar(result.getToday());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (calToday != null) {
						calToday = temp;
						if (isFirstReq) {
							calSelected = calToday;
							gAdapter.setCalToday(calToday);
							gAdapterNextMonth.setCalToday(calToday);
							isFirstReq = false;
						}
						if (calSelected.get(Calendar.MONTH) == iMonthViewCurrentMonth) {
							/*
							 * 当月
							 */
							gAdapter.setSelectedDate(calSelected);
							gAdapter.notifyDataSetChanged();
							gSelectAdapter = gAdapter;
						} else {
							gAdapterNextMonth.setSelectedDate(calSelected);
							gAdapterNextMonth.notifyDataSetChanged();
							gSelectAdapter = gAdapterNextMonth;
						}
						// checkUpdate(DateSetFragment.calSelected);
					}
				}
				/*
				 * 刷新当前选择日期位置上方行数
				 */
				setSelectLine(getSelectLine(calSelected));

				/*
				 * reform data
				 */
				new ReformTask().execute();

				if (result.getCode() == 5) {
					goToDefault();
				}
				if (result.getCode() == 95) {

					if (result.getMessage() != null)
						Toast.makeText(mActivity, result.getMessage(), Toast.LENGTH_SHORT).show();
					CommonUtils.gotoLogin(mActivity);
				}
			} else {
				/*
				 * 刷新当前选择日期位置上方行数
				 */
				setSelectLine(getSelectLine(calSelected));
				/*
				 * 生成悬停部分(RefreshBallStateTask中会刷新。故放在网络异常位置)
				 */
				initialHangingContent(calSelected, calToday);
			}
		}
	}

	HashMap<String, Schedule> hourMap = new HashMap<String, Schedule>();
	HashMap<String, HashMap> daySpan = new HashMap<String, HashMap>();

	/*
	 * 获取一个新的HASHMAP对象.存储一天的时间
	 */
	HashMap<String, Schedule> getNewHourMap() {
		hourMap = new HashMap<String, Schedule>();
		for (int i = 0; i <= 18; i++) {
			Schedule sc = new Schedule();
			hourMap.put(5 + i + "", sc);
		}
		return hourMap;
	}

	/*
	 * 刷新获取数组后重新组合数据方便查询和遍历
	 */
	void reformData() {
		if (scheduleResult != null && scheduleResult.getDatelist() != null) {
			for (Schedule sc : scheduleResult.getDatelist()) {
				if (!daySpan.containsKey(sc.getDate())) {
					/*
					 * 如果当前hashmap没有保存这个日期的数据
					 */
					HashMap<String, Schedule> temp = getNewHourMap();
					temp.put(sc.getHour() + "", sc);
					daySpan.put(sc.getDate(), temp);
				} else {
					daySpan.get(sc.getDate()).put(sc.getHour() + "", sc);
				}

			}
		}
	}

	/*
	 * @param xxxx-xx-xx
	 * 
	 * @RETURN POSITION IN BALLSTATE
	 */
	int getStringPositionInAdapter(String str) {
		Calendar calendar = Calendar.getInstance();
		Calendar strCalendar = null;
		try {
			strCalendar = TimeUtil.StringToCalendar(str);
		} catch (Exception e) {
		}

		calendar.setTime(calSelected.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, 1); // 设为当月第一天
		int weekIndex = calendar.get(Calendar.DAY_OF_WEEK);
		if (strCalendar != null && strCalendar.get(Calendar.MONTH) == iMonthViewCurrentMonth) {
			return strCalendar.get(Calendar.DAY_OF_MONTH) + weekIndex - 2;
		}
		return 0;
	}

	/*
	 * 刷新adapter保存三色球状态的列表,最后刷新主线程
	 */
	public class RefreshBallStateTask extends AsyncTask<Void, Void, Void> {
		HashMap<String, Schedule> tempHour;
		Schedule tempsc;
		int dayPos;

		@Override
		protected Void doInBackground(Void... arg0) {
			/*
			 * 重置ballstate
			 */
			for (BallState state : ballState) {
				state.setShowB(false);
				state.setShowY(false);
				state.setShowR(false);
				state.setOpen(false);
			}

			/*
			 * 遍历数据存储
			 */
			for (String str : daySpan.keySet()) {
				try {
					if (TimeUtil.StringToCalendar(str).get(Calendar.MONTH) != iMonthViewCurrentMonth)
						continue;
				} catch (Exception e) {
				}

				boolean isOpen = true;
				tempHour = daySpan.get(str);
				dayPos = getStringPositionInAdapter(str);
				for (String hourStr : tempHour.keySet()) {
					tempsc = tempHour.get(hourStr);
					if (hourStr.equals("0") && tempsc.getState() == 0) {
						isOpen = false;
					}
				}
				if (isOpen)
				{
					ballState.get(dayPos).setOpen(true);
				
					for (String hourStr : tempHour.keySet()) {
						tempsc = tempHour.get(hourStr);
						if (tempsc.getIsrest() == 0) {
							/*
							 * 开课
							 */
							if (tempsc.getHour() <= 11 && tempsc.getHour() > 0) {
								ballState.get(dayPos).setShowY(true);
							} else if (tempsc.getHour() <= 18 && tempsc.getHour()>0) {
								ballState.get(dayPos).setShowR(true);
							} else if (tempsc.getHour() <= 23 && tempsc.getHour()>0) {
								ballState.get(dayPos).setShowB(true);
							}
						}
					}
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (iMonthViewCurrentMonth == calToday.get(Calendar.MONTH)) {
				gAdapter.setBallState(ballState);
				gAdapter.notifyDataSetChanged();
				gSelectAdapter = gAdapter;
			} else {
				gAdapterNextMonth.setBallState(ballState);
				gAdapterNextMonth.notifyDataSetChanged();
				gSelectAdapter = gAdapterNextMonth;
			}
			initialHangingContent(calSelected, calToday);
			checkUpdate(calSelected);
			
			finishLoadingData = true;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}

	/*
	 * 修改日历下部分内容(注意与上面日历分开)
	 */
	public void checkUpdate(Calendar selectedDate) {
		if (scheduleResult == null)
			return;
		if (scheduleResult.getDatelist() == null)
			return;
		
		for (Schedule schedule : scheduleResult.getDatelist()) {
			Date date2;
			try {
				date2 = TimeUtil.StringToDate(schedule.getDate());
			} catch (Exception e) {
				return;
			}

			if (equalsDate(selectedDate.getTime(), date2)) {

				if (schedule.getHour() == 0) {
					/*
					 * 全局设置
					 */
//					if (schedule.getState() == 0) {
//						// rest
//						mAllDaySetClose.setText("发布课程");
//						mAllDaySetClose.setBackgroundResource(R.drawable.shape_green_round);
//						isAllDayOpen = false;
//					} else if (schedule.getState() == 1) {
//						mAllDaySetClose.setText("当天停课");
//						mAllDaySetClose.setBackgroundResource(R.drawable.shape_red_round);
//						isAllDayOpen = true;
//					}

				} else {
					/*
					 * 单个小时设置
					 */
//					switch (schedule.getHour()) {
//					case 5:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mFiveTime, mFivePrice, mFiveSelect, 0,schedule.getExpire());
//						break;
//					case 6:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mSixTime, mSixPrice, mSixSelect, 1,schedule.getExpire());
//						break;
//					case 7:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mSevenTime, mSevenPrice, mSevenSelect, 2,schedule.getExpire());
//						break;
//					case 8:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mEightTime, mEightPrice, mEightSelect, 3,schedule.getExpire());
//						break;
//					case 9:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mNineTime, mNinePrice, mNineSelect, 4,schedule.getExpire());
//						break;
//					case 10:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mTenTime, mTenPrice, mTenSelect, 5,schedule.getExpire());
//						break;
//					case 11:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mElevenTime, mElevenPrice, mElevenSelect, 6,schedule.getExpire());
//						break;
//					case 12:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mTwelveTime, mTwelvePrice, mTwelveSelect, 7,schedule.getExpire());
//						break;
//					case 13:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mThirteenTime, mThirteenPrice, mThirteenSelect, 8,schedule.getExpire());
//						break;
//					case 14:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mFourteenTime, mFourteenPrice, mFourteenSelect, 9,schedule.getExpire());
//						break;
//					case 15:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mFifteenTime, mFifteenPrice, mFifteenSelect, 10,schedule.getExpire());
//						break;
//					case 16:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mSixteenTime, mSixteenPrice, mSixteenSelect, 11,schedule.getExpire());
//						break;
//					case 17:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mSeventeenTime, mSeventeenPrice, mSeventeenSelect, 12,schedule.getExpire());
//						break;
//					case 18:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mEighteenTime, mEighteenPrice, mEighteenSelect, 13,schedule.getExpire());
//						break;
//					case 19:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mNineteenTime, mNineteenPrice, mNineteenSelect, 14,schedule.getExpire());
//						break;
//					case 20:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mTwentyTime, mTwentyPrice, mTwentySelect, 15,schedule.getExpire());
//						break;
//					case 21:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mTwentyOneTime, mTwentyOnePrice, mTwentyOneSelect, 16,schedule.getExpire());
//						break;
//					case 22:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mTwentyTwoTime, mTwentyTwoPrice, mTwentyTwoSelect, 17,schedule.getExpire());
//						break;
//					case 23:
//						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mTwentyThreeTime, mTwentyThreePrice, mTwentyThreeSelect, 18,schedule.getExpire());
//						break;
//					}
				}
			}
		}

		//checkDayTime();
		//checkSelect();
	}

	/*
	 * 修改全选显示情况
	 */
//	private void checkSelect() {
//		// morning
//		if (hasSel(0, 7)) {
//			mMorSelectAllImg.setSelected(true);
//			mMorSelectAllTv.setText("取消全选");
//		} else {
//			mMorSelectAllImg.setSelected(false);
//			mMorSelectAllTv.setText("全选");
//		}
//		// afternoon
//		if (hasSel(7, 7)) {
//			mAftSelectAllImg.setSelected(true);
//			mAftSelectAllTv.setText("取消全选");
//		} else {
//			mAftSelectAllImg.setSelected(false);
//			mAftSelectAllTv.setText("全选");
//		}
//		// night
//		if (hasSel(14, 5)) {
//			mNigSelectAllImg.setSelected(true);
//			mNigSelectAllTv.setText("取消全选");
//		} else {
//			mNigSelectAllImg.setSelected(false);
//			mNigSelectAllTv.setText("全选");
//		}
//	}

	private boolean hasSel(int start, int length) {
		for (int i = start; i < (start + length); i++) {
			if (!selectArray[i])
				return true;
		}
		return false;
	}

	/*
	 * get time space
	 */
//	private void checkDayTime() {
//		String morTimeStr = checkMANTime(0, 6);
////		if (morTimeStr != null) {
////			mMorTime.setText(morTimeStr);
////		}
//		// afternoon
//		String aftTimeStr = checkMANTime(7, 13);
//		if (aftTimeStr != null) {
//			mAftTime.setText(aftTimeStr);
//		}
//		// night
//		String nigTimeStr = checkMANTime(14, 18);
//		if (nigTimeStr != null) {
//			mNigTime.setText(nigTimeStr);
//		}
//
//		// Check Points
//		checkPoints();
//	}

	/*
	 * set the points' color and visibility
	 */
//	private void checkPoints() {
//		checkMorRest();
//		checkAftRest();
//		checkNigRest();
//	}

	/*
	 * 
	 */
//	private void checkMorRest() {
////		if (isRest(0, 6)) {
////			mMorPoint.setBackgroundResource(R.drawable.shape_grey_ball);
////		} else {
////			mMorPoint.setBackgroundResource(R.drawable.shape_yellow_ball);
////		}
//
//		if (isRest(7, 13)) {
//			mAftPoint.setBackgroundResource(R.drawable.shape_grey_ball);
//		} else {
//			mAftPoint.setBackgroundResource(R.drawable.shape_red_ball);
//		}
//
//		if (isRest(14, 18)) {
//			mNigPoint.setBackgroundResource(R.drawable.shape_grey_ball);
//		} else {
//			mNigPoint.setBackgroundResource(R.drawable.shape_blue_ball);
//		}
//
//	}

	private boolean isRest(int start, int end) {
		for (int i = start; i <= end; i++) {
			if (stateArray[i])
				return false;
		}
		return true;
	}

	private void checkAftRest() {

	}

	private void checkNigRest() {

	}

	/*
	 * 
	 */
	private String checkMANTime(int start, int end) {
		StringBuilder sb = new StringBuilder();
		int i = start;
		while (i <= end) {
			if (stateArray[i]) {
				int j = getSpan(i, end);
				sb.append(getTimeSpanStr(i, j) + "/");
				i = j;
			}
			i += 1;
		}
		if (sb.toString().contains("/"))
			return sb.toString().substring(0, sb.length() - 1);
		else {
			return sb.toString();
		}

	}

	/*
	 * 获取非休息时间间隔
	 */
	private String getTimeSpanStr(int startPos, int endPos) {
		return (startPos + 5) + ":00-" + (endPos + 6) + ":00";
	}

	/*
	 * get open class time span
	 */
	private int getSpan(int i, int lastIndex) {
		int j = i + 1;
		while (j <= lastIndex) {
			if (!stateArray[j])
				return j - 1;
			j++;
		}
		return j - 1;
	}

	/*
	 * set one hour's state rest or not 0:not rest
	 */
	Schedule schd;

	private void modifyOneHourState(int isrest, String pricenum, TextView time, TextView price, TextView select, int pos,int expire) {
//		if (expire == 1)
//		{
//			time.setTextColor(Color.parseColor("#d2d2d2"));
//			price.setTextColor(Color.parseColor("#d2d2d2"));
//			if (pricenum != null)
//				price.setText("单价  " + (pricenum.contains(".") ? pricenum.substring(0, pricenum.indexOf(".")) : pricenum) + "元");
//			else {
//				price.setText("");
//			}
//			price.setBackgroundColor(Color.parseColor("#e5e5e5"));
//		}else{
//			time.setTextColor(Color.parseColor("#20b478"));
//			price.setTextColor(Color.parseColor("#ffffff"));
//			price.setBackgroundColor(Color.parseColor("#20b478"));
//			if (pricenum != null)
//				price.setText("单价  " + (pricenum.contains(".") ? pricenum.substring(0, pricenum.indexOf(".")) : pricenum) + "元");
//			else {
//				price.setText("");
//			}
//		}
//		
		if (expire == 1)    //已过期
		{
		if (isrest == 1) {
			time.setTextColor(Color.parseColor("#d2d2d2"));
			price.setTextColor(Color.parseColor("#d2d2d2"));
			price.setText("未开课");
			price.setBackgroundColor(Color.parseColor("#e5e5e5"));
			stateArray[pos] = false;
		} else {
			time.setTextColor(Color.parseColor("#d2d2d2"));
			price.setTextColor(Color.parseColor("#d2d2d2"));
			price.setBackgroundColor(Color.parseColor("#e5e5e5"));
			if (pricenum != null)
				price.setText("单价  " + (pricenum.contains(".") ? pricenum.substring(0, pricenum.indexOf(".")) : pricenum) + "元");
			else {
				price.setText("");
			}
			stateArray[pos] = true;
		}
		}else{
			if (isrest == 1) {
				time.setTextColor(Color.parseColor("#d2d2d2"));
				price.setTextColor(Color.parseColor("#d2d2d2"));
				price.setText("未开课");
				price.setBackgroundColor(Color.parseColor("#e5e5e5"));
				stateArray[pos] = false;
			} else {
				time.setTextColor(Color.parseColor("#20b478"));
				price.setTextColor(Color.parseColor("#d2d2d2"));
				price.setBackgroundColor(Color.parseColor("#20b478"));
				if (pricenum != null)
					price.setText("单价  " + (pricenum.contains(".") ? pricenum.substring(0, pricenum.indexOf(".")) : pricenum) + "元");
				else {
					price.setText("");
				}
				stateArray[pos] = true;
			}
		}

		if (equalsDate(calToday.getTime(), calSelected.getTime()) && (pos + 5) <= mDeadHour) {
			select.setVisibility(View.INVISIBLE);
			selectArray[pos] = true;
		} else {
			if (select.getVisibility() == View.INVISIBLE) {
				select.setVisibility(View.VISIBLE);
				select.setBackgroundResource(R.color.text_black);
			}
			selectArray[pos] = false;
		}

		if (daySpan.get(TimeUtil.calendarToString(calSelected)) != null)
			schd = (Schedule) daySpan.get(TimeUtil.calendarToString(calSelected)).get(5 + pos + "");
		if (daySpan != null && schd != null && schd.getHasbooked() == 1) {
			select.setText("已约");
			selectArray[pos] = true;
			bookArray[pos] = true;
			if (select.getVisibility() == View.INVISIBLE) {
				select.setVisibility(View.VISIBLE);
				// if (equalsDate(calToday.getTime(), calSelected.getTime()))
				// select.setBackgroundResource(R.color.text_grey);
				// else {
				// select.setBackgroundColor(Color.parseColor("#000000"));
				// }
			}
		} else {
			select.setText("选中");
			selectArray[pos] = false;
			bookArray[pos] = false;
			/*
			 * select.setTextColor(getResources().getColor(R.color.white)); select.setBackgroundColor(Color.parseColor("#000000"));
			 */
		}
	}

	private Boolean equalsDate(Date date1, Date date2) {

		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate()) {
			return true;
		} else {
			return false;
		}

	}

	/*
	 * Change one Day's Schedule
	 */
	private class ChangeAllDayScheduleTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(mActivity.getApplicationContext(), JSONAccessor.METHOD_POST);
		String type;
		String day;

		public ChangeAllDayScheduleTask(String type) {
			this.type = type;
			day = TimeUtil.calendarToString(gAdapter.getSelectedDate());
		}

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
			List<Schedule> chosedScheduleArray = new ArrayList<Schedule>();
			for (Schedule schedule:scheduleResult.getDatelist())
			{
				Date date2 = null;
				try {
					date2 = TimeUtil.StringToDate(schedule.getDate());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (equalsDate(calSelected.getTime(), date2)) {
					if (schedule.getHour() !=0)
					{
						chosedScheduleArray.add(schedule);
					}
				}
			}
			
			String chosedSchedule = new Gson().toJson(chosedScheduleArray);
			param.put("action", "ChangeAllDaySchedule");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("day", day);
			param.put("type", type); // 1:open 2: close
			param.put("setjson", chosedSchedule);
			return accessor.execute(Settings.SCHEDULE_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();

			if (result != null) {
				if (result.getCode() == 1) {
//					if (type != null && type.equals("1")) {
//						isAllDayOpen = true;
//						mAllDaySetClose.setText("当天停课");
//						mAllDaySetClose.setBackgroundResource(R.drawable.shape_red_round);
//						new ChangeClassStateTask(1, day).execute();
//					} else {
//						isAllDayOpen = false;
//						mAllDaySetClose.setText("发布课程");
//						mAllDaySetClose.setBackgroundResource(R.drawable.shape_green_round);
//						new ChangeClassStateTask(0, day).execute();
//					}
				} else {
					if (result.getCode() == 95) {
						if (result.getMessage() != null)
							CommonUtils.showToast(mActivity.getApplicationContext(), result.getMessage());
						CommonUtils.gotoLogin(mActivity);
					} else {
						if (result.getMessage() != null)
							if (defaultSel)
								CommonUtils.showToast(mActivity.getApplicationContext(), result.getMessage());
							else {
								CommonUtils.showToast(mActivity.getApplicationContext(), "设置成功");
							}
					}
				}

			} else {
				CommonUtils.showToast(mActivity.getApplicationContext(), mActivity.getString(R.string.net_error));
			}
		}

	}

	Resources resources;
	ArrayList<CalendarGridViewAdapter.BallState> ballState = new ArrayList<CalendarGridViewAdapter.BallState>();

	/*
	 * 初始化悬停部分显示的日期等
	 */
	
	private static Calendar mCalen;
	public void initialHangingContent(Calendar selectCalendar, Calendar calToday) {
		mHangingContent.removeAllViewsInLayout();
		Calendar temp = Calendar.getInstance();
		temp.setTime(selectCalendar.getTime());
		Date myDate;
		Calendar monday = getLastSunday(temp);
		int position = 0; // select item 在adapter中位置
		/*
		 * 循环7次
		 */
		for (int i = 0; i < 7; i++) {
			if (i == 0) {
			} else {
				monday.add(Calendar.DAY_OF_MONTH, 1);
			}
			myDate = monday.getTime();

			final View iv = LayoutInflater.from(mActivity).inflate(R.layout.item_date_picker, null);
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams((Settings.DISPLAY_WIDTH - 12) / 7, (Settings.DISPLAY_WIDTH - 12) / 7);
			if (i != 0) // 不是第一个view 设置marginLeft 2px
				param.setMargins(2, 0, 0, 0);
			iv.setLayoutParams(param);
			/*
			 * get balls
			 */
			View yBall = (View) iv.findViewById(R.id.yellow_ball);
			View rBall = (View) iv.findViewById(R.id.red_ball);
			View bBall = (View) iv.findViewById(R.id.blue_ball);
			// 背景铺色
			
			// 今天的处理
			TextView txtToDay = (TextView) iv.findViewById(R.id.calendar_status);
			// 日期开始
			TextView txtDay = (TextView) iv.findViewById(R.id.calendar_date);
			int day = myDate.getDate(); // 设置日期
			txtDay.setText(String.valueOf(day));
			iv.setTag(myDate);

			yBall.setVisibility(View.GONE);
			rBall.setVisibility(View.GONE);
			bBall.setVisibility(View.GONE);

			iMonthViewCurrentMonth = selectCalendar.get(Calendar.MONTH);// 得到当前日历显示的月
			// 判断是否是当前月
			if (monday.get(Calendar.MONTH) == iMonthViewCurrentMonth) {
				txtDay.setVisibility(View.VISIBLE);
				txtToDay.setVisibility(View.VISIBLE);
				if (beforeDate(myDate, calToday.getTime())) {
					txtToDay.setTextColor(resources.getColor(R.color.unable_grey));
					txtDay.setTextColor(resources.getColor(R.color.unable_grey));
					txtToDay.setText("不可操作");
					iv.setBackgroundColor(Color.parseColor("#222222"));
					iv.setClickable(false);
					iv.setEnabled(false);
				} else {
					if (equalsDate(calToday.getTime(), myDate)) {
						// 当前日期
						txtDay.setTextColor(resources.getColor(R.color.text_green));
						txtToDay.setTextColor(resources.getColor(R.color.text_green));
						txtToDay.setText("今日");
						iv.setBackgroundColor(Color.parseColor("#2b3733"));
						iv.setClickable(true);
						iv.setEnabled(true);
						iv.setOnClickListener(new OnSingleClickListener() {
							
							@Override
							public void doOnClick(View v) {
								// TODO Auto-generated method stub
								ivClick((Date) v.getTag(), v);
							}
						});
//						iv.setOnClickListener(new OnSingleClickListener() {
//							
//							@Override
//							public void doOnClick(View v) {
//								// TODO Auto-generated method stub
//								CommonUtils.showToast(mActivity, iv.getTag().toString());
//							}
//						});
						//iv.setOnClickListener(new dateClickListener((Calendar) iv.getTag(), iv));
						//iv.setOnClickListener(new dateClickListener(gAdapter, (Calendar) iv.getTag(), iv,false,0));
					} else {
						if (daysBetween(calToday.getTime(), myDate)) {
							txtToDay.setTextColor(resources.getColor(R.color.not_open));
							txtDay.setTextColor(resources.getColor(R.color.not_open));
							iv.setClickable(true);
							iv.setEnabled(true);
							iv.setBackgroundColor(Color.parseColor("#2b3733"));
							iv.setOnClickListener(new OnSingleClickListener() {
								@Override
								public void doOnClick(View v) {
									// TODO Auto-generated method stub
									ivClick((Date) v.getTag(), v);
								}
							});
//							iv.setOnClickListener(new dateClickListener((Calendar) iv.getTag(), iv));
//							iv.setOnClickListener(new OnSingleClickListener() {
//								
//								@Override
//								public void doOnClick(View v) {
//									// TODO Auto-generated method stub
//									CommonUtils.showToast(mActivity, v.getTag().toString());
//								}
//							});
							//iv.setOnClickListener(new dateClickListener(gAdapter, (Calendar) iv.getTag(), iv,false,0));

							position = selectItemLine * 7 + i;
							if (ballState.get(position).isShowY()) {
								yBall.setVisibility(View.VISIBLE);
							} else {
							}
							if (ballState.get(position).isShowR()) {
								rBall.setVisibility(View.VISIBLE);
							} else {
							}
							if (ballState.get(position).isShowB()) {
								bBall.setVisibility(View.VISIBLE);
							} else {
							}

							/*
							 * 若是有球显示,字为白色
							 */
							if (ballState.get(position).isShowY() || ballState.get(position).isShowR() || ballState.get(position).isShowB()) {
								txtDay.setTextColor(resources.getColor(R.color.white));
								txtToDay.setText("已开课");
								txtToDay.setTextColor(resources.getColor(R.color.white));
								iv.setBackgroundColor(Color.parseColor("#2c4021"));
//								if (txtToDay.getVisibility() == View.VISIBLE)
//									txtToDay.setVisibility(View.INVISIBLE);
							}
						} else {
							txtDay.setTextColor(resources.getColor(R.color.unable_grey));
							txtToDay.setTextColor(resources.getColor(R.color.unable_grey));
							txtToDay.setText("不可操作");
							iv.setBackgroundColor(Color.parseColor("#222222"));
							iv.setClickable(false);
						}

					}
				}
			} else {
				txtDay.setVisibility(View.INVISIBLE);
				txtToDay.setVisibility(View.INVISIBLE);
				iv.setBackgroundColor(Color.parseColor("#2b3733"));
			}

			// 设置背景颜色
			if (equalsDate(selectCalendar.getTime(), myDate)) {
				// 选择的
				iv.setBackgroundColor(resources.getColor(R.color.white));
				txtDay.setTextColor(resources.getColor(R.color.text_black));
				txtToDay.setTextColor(resources.getColor(R.color.text_black));
				// gAdapter.selectedView = iv;
			} else {
//				iv.setBackgroundColor(Color.parseColor("#444444"));
			}
			mHangingContent.addView(iv);
		}
		if (getSelectLine(selectCalendar) > selectItemLine)
			mHangingContent.setVisibility(View.INVISIBLE);
		else {

		}
	}
	
	private void ivClick(Date mDate, View view)
	{
		Calendar mCarlandar=Calendar.getInstance();
		mCarlandar.setTime(mDate);
		if (equalsDate(mCarlandar.getTime(), calSelected.getTime())) {
			return; // click selected item
		}
		Date selectedDate = calSelected.getTime();
		Date todayDate = calToday.getTime();
//		Date mDate = mCarlandar.getTime();
		gSelectAdapter.selectedView.setBackgroundColor(Color.parseColor("#2b3733"));
		view.setBackgroundColor(Color.parseColor("#ffffff"));
		gSelectAdapter.yBall = (View) gSelectAdapter.selectedView.findViewById(R.id.yellow_ball);
		gSelectAdapter.rBall = (View) gSelectAdapter.selectedView.findViewById(R.id.red_ball);
		gSelectAdapter.bBall = (View) gSelectAdapter.selectedView.findViewById(R.id.blue_ball);
//		if (mIsHanging)
//		{
//			int data = mDate.getDate();
//			int add = mPosition - data;
//			mCarlandar.add(Calendar.DAY_OF_MONTH, add);
//		}
		gSelectAdapter.changeColor((ViewGroup) view, resources.getColor(R.color.text_black));
		if (equalsDate(calSelected.getTime(), calToday.getTime())) {
			// last selected date is today
			gSelectAdapter.changeColor((ViewGroup) gSelectAdapter.selectedView, resources.getColor(R.color.text_green));
		} else {
			gSelectAdapter.changeColor((ViewGroup) gSelectAdapter.selectedView, resources.getColor(R.color.white));
			if (gSelectAdapter.yBall.getVisibility() == View.VISIBLE || gSelectAdapter.rBall.getVisibility() == View.VISIBLE || gSelectAdapter.bBall.getVisibility() == View.VISIBLE)
			{
				gSelectAdapter.selectedView.setBackgroundColor(Color.parseColor("#2c4021"));
			}
//			else {
//				changeColor((ViewGroup) selectedView, resources.getColor(R.color.unable_grey));
//			}
		}
		calSelected = mCarlandar;
		gSelectAdapter.calSelected = mCarlandar;
		gSelectAdapter.selectedView = view;
		if (gSelectAdapter.mNotifyDateSelect != null)
			gSelectAdapter.mNotifyDateSelect.notify(calSelected);
	}
	
//	public class dateClickListener extends OnSingleClickListener
//	{
//		Calendar mCarlandar;
//		View view;
//		//	boolean mIsHanging;
//		//	int mPosition;
//
//	public dateClickListener(Calendar mCarlandar, View view) {
//		this.mCarlandar = mCarlandar;
//		this.view = view;
////		mIsHanging = isHanging;
////		mPosition = position;
//	}
//
//	@Override
//	public void doOnClick(View v) {
//		if (equalsDate(mCarlandar.getTime(), calSelected.getTime())) {
//			return; // click selected item
//		}
//		Date selectedDate = calSelected.getTime();
//		Date todayDate = calToday.getTime();
//		Date mDate = mCarlandar.getTime();
//		gSelectAdapter.selectedView.setBackgroundColor(Color.parseColor("#2b3733"));
//		view.setBackgroundColor(Color.parseColor("#ffffff"));
//		gSelectAdapter.yBall = (View) gSelectAdapter.selectedView.findViewById(R.id.yellow_ball);
//		gSelectAdapter.rBall = (View) gSelectAdapter.selectedView.findViewById(R.id.red_ball);
//		gSelectAdapter.bBall = (View) gSelectAdapter.selectedView.findViewById(R.id.blue_ball);
////		if (mIsHanging)
////		{
////			int data = mDate.getDate();
////			int add = mPosition - data;
////			mCarlandar.add(Calendar.DAY_OF_MONTH, add);
////		}
//		gSelectAdapter.changeColor((ViewGroup) view, resources.getColor(R.color.text_black));
//		if (equalsDate(calSelected.getTime(), calToday.getTime())) {
//			// last selected date is today
//			gSelectAdapter.changeColor((ViewGroup) gSelectAdapter.selectedView, resources.getColor(R.color.text_green));
//		} else {
//			gSelectAdapter.changeColor((ViewGroup) gSelectAdapter.selectedView, resources.getColor(R.color.white));
//			if (gSelectAdapter.yBall.getVisibility() == View.VISIBLE || gSelectAdapter.rBall.getVisibility() == View.VISIBLE || gSelectAdapter.bBall.getVisibility() == View.VISIBLE)
//			{
//				gSelectAdapter.selectedView.setBackgroundColor(Color.parseColor("#2c4021"));
//			}
////			else {
////				changeColor((ViewGroup) selectedView, resources.getColor(R.color.unable_grey));
////			}
//		}
//		calSelected = mCarlandar;
//		gSelectAdapter.selectedView = v;
//		if (gSelectAdapter.mNotifyDateSelect != null)
//			gSelectAdapter.mNotifyDateSelect.notify(calSelected);
//
//	}
//	}

	/*
	 * 刷新课程状态
	 */
	private class ChangeClassStateTask extends AsyncTask<Void, Void, Void> {

		String day;
		int state;

		public ChangeClassStateTask(int state, String date) {
			day = date;
			this.state = state;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			if (scheduleResult != null) {
				boolean hasHourZero = false;
				for (Schedule sc : scheduleResult.getDatelist()) {
					if (sc.getDate() != null && sc.getDate().equals(day) && sc.getHour() == 0) {
						hasHourZero = true;
						sc.setState(state);
					}
				}
				if (!hasHourZero) {
					Schedule sd = new Schedule();
					sd.setState(state);
					scheduleResult.getDatelist().add(sd);
				}
			}

			if (daySpan != null) {
				for (String date : daySpan.keySet()) {
					if (date.equals(day)) {
						HashMap<String, Schedule> hm = daySpan.get(date);
						boolean hasSetState = false;
						for (String hour : hm.keySet()) {
							if (hour.equals("0")) {
								hm.get("0").setState(state);
								hasSetState = true;
							}
						}

						if (!hasSetState) {
							Schedule sd = new Schedule();
							sd.setState(state);
							hm.put("0", sd);
						}
					}
				}

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			new RefreshBallStateTask().execute();
		}

	}

	/*
	 * 获得和上周日相差的天数
	 */
	private int getSundayPlus(Calendar cd) {
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/*
	 * 获取上周日的calendar
	 */
	public Calendar getLastSunday(Calendar currentDate) {
		int mondayPlus = this.getSundayPlus(currentDate);
		currentDate.add(Calendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(monday);
		return c;
	}

	/*
	 * date1 < date2
	 */
	private Boolean beforeDate(Date date1, Date date2) {

		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() < date2.getDate()) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * in 30 days
	 */
	public boolean daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days)) <CoachApplication.mApplication.getMaxTays();
	}

	/*
	 * 设置默认课程
	 */
	private class SetDefaultTask extends AsyncTask<Void, Void, SetDefaultResult> {
		JSONAccessor accessor = new JSONAccessor(mActivity, JSONAccessor.METHOD_POST);

		@Override
		protected SetDefaultResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "SetDefault");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("day", TimeUtil.calendarToString(calSelected));
			return accessor.execute(Settings.SCHEDULE_URL, param, SetDefaultResult.class);
		}

		@Override
		protected void onPostExecute(SetDefaultResult result) {
			super.onPostExecute(result);
			if (result != null && result.getCode() == 1) {
				Toast.makeText(mActivity, "设为默认课程成功", Toast.LENGTH_SHORT).show();
				List<Schedule> list = scheduleResult.getDatelist();
				int count = 0;
				List<Schedule> temp = new ArrayList<Schedule>();
				for (; count < list.size(); count++) {
					if (list.get(count).getDate().equals(TimeUtil.calendarToString(calSelected))) {
						temp.add(list.get(count));
					}
				}
				list.remove(temp);
				list.addAll(result.getDatelist());
			} else {
				if (result != null && result.getCode() == 95) {
					if (result.getMessage() != null)
						Toast.makeText(mActivity, result.getMessage(), Toast.LENGTH_SHORT).show();
					CommonUtils.gotoLogin(mActivity);
				} else {
					Toast.makeText(mActivity, "设为默认课程失败", Toast.LENGTH_SHORT).show();
				}
			}

		}

	}

	public static final String TAG = "DateSetFragment";

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 2001 && data != null) {
			try {
				// Update THE DateList reserved
				updateLocalDateList(((SetDateTimeResult) data.getSerializableExtra("list")).getDatelist(), data.getStringExtra("day"));
			} catch (Exception e) {
				Log.e(TAG, "convert serializable error");
			}
		}
	}

	private void updateLocalDateList(List<Schedule> datelist, String day) {
		if (datelist != null && datelist.size() > 0) {
			new UpdateScheduleTask(datelist, day).execute();
		}
	}

	public class UpdateScheduleTask extends AsyncTask<Void, Void, Void> {
		private List<Schedule> datelist;
		boolean hasSc;
		String day;

		public UpdateScheduleTask(List<Schedule> datelist, String day) {
			this.datelist = datelist;
			this.day = day;
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			/*
			 * 清掉当日数据
			 */
			List<Schedule> temp = new ArrayList<Schedule>();
			for (Schedule sche : scheduleResult.getDatelist()) {
				if (sche.getDate() != null && day.equals(sche.getDate())) {
					temp.add(sche);
				} else {
				}
			}
			scheduleResult.getDatelist().removeAll(temp);
			/*
			 * 将新数据加入
			 */
			scheduleResult.getDatelist().addAll(datelist);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			new ReformTask().execute();
		}

	}

}
