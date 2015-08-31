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
import xiaoba.coach.net.result.GetDefaultScheduleResult;
import xiaoba.coach.net.result.GetDefaultScheduleResult.DefaultSchedule;
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
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

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
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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
	LinearLayout mMorningSSet; // 早上设置栏
	LinearLayout mAfternoonSet; // 下午设置栏
	LinearLayout mNightSet; // 晚上设置栏
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
	//5
	private RelativeLayout rlFive;
	private TextView tvFiveTime;
	private TextView tvFiveObject;
	private TextView tvFivePrice;
	private ImageView imgFiveSelect;
	private LinearLayout llFiveHasSet;
	private TextView tvFiveNotSet;
	private ImageView imgFiveHasBook;
	//6
	private RelativeLayout rlSix;
	private TextView tvSixTime;
	private TextView tvSixObject;
	private TextView tvSixPrice;
	private ImageView imgSixSelect;
	private LinearLayout llSixHasSet;
	private TextView tvSixNotSet;
	private ImageView imgSixHasBook;
	//7
	private RelativeLayout rlSeven;
	private TextView tvSevenTime;
	private TextView tvSevenObject;
	private TextView tvSevenPrice;
	private ImageView imgSevenSelect;
	private LinearLayout llSevenHasSet;
	private TextView tvSevenNotSet;
	private ImageView imgSevenHasBook;
	//8
	private RelativeLayout rlEight;
	private TextView tvEightTime;
	private TextView tvEightObject;
	private TextView tvEightPrice;
	private ImageView imgEightSelect;
	private LinearLayout llEightHasSet;
	private TextView tvEightNotSet;
	private ImageView imgEightHasBook;
	//9
	private RelativeLayout rlNine;
	private TextView tvNineTime;
	private TextView tvNineObject;
	private TextView tvNinePrice;
	private ImageView imgNineSelect;
	private LinearLayout llNineHasSet;
	private TextView tvNineNotSet;
	private ImageView imgNineHasBook;
	//10
	private RelativeLayout rlTen;
	private TextView tvTenTime;
	private TextView tvTenObject;
	private TextView tvTenPrice;
	private ImageView imgTenSelect;
	private LinearLayout llTenHasSet;
	private TextView tvTenNotSet;
	private ImageView imgTenHasBook;
	//11
	private RelativeLayout rlEleven;
	private TextView tvElevenTime;
	private TextView tvElevenObject;
	private TextView tvElevenPrice;
	private ImageView imgElevenSelect;
	private LinearLayout llElevenHasSet;
	private TextView tvElevenNotSet;
	private ImageView imgElevenHasBook;
	//12
	private RelativeLayout rlTwelve;
	private TextView tvTwelveTime;
	private TextView tvTwelveObject;
	private TextView tvTwelvePrice;
	private ImageView imgTwelveSelect;
	private LinearLayout llTwelveHasSet;
	private TextView tvTwelveNotSet;
	private ImageView imgTwelveHasBook;
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
	//13
	private RelativeLayout rlThirteen;
	private TextView tvThirteenTime;
	private TextView tvThirteenObject;
	private TextView tvThirteenPrice;
	private ImageView imgThirteenSelect;
	private LinearLayout llThirteenHasSet;
	private TextView tvThirteenNotSet;
	private ImageView imgThirteenHasBook;
	//14
	private RelativeLayout rlFourteen;
	private TextView tvFourteenTime;
	private TextView tvFourteenObject;
	private TextView tvFourteenPrice;
	private ImageView imgFourteenSelect;
	private LinearLayout llFourteenHasSet;
	private TextView tvFourteenNotSet;
	private ImageView imgFourteenHasBook;
	//15
	private RelativeLayout rlFifteen;
	private TextView tvFifteenTime;
	private TextView tvFifteenObject;
	private TextView tvFifteenPrice;
	private ImageView imgFifteenSelect;
	private LinearLayout llFifteenHasSet;
	private TextView tvFifteenNotSet;
	private ImageView imgFifteenHasBook;
	//16
	private RelativeLayout rlSixteen;
	private TextView tvSixteenTime;
	private TextView tvSixteenObject;
	private TextView tvSixteenPrice;
	private ImageView imgSixteenSelect;
	private LinearLayout llSixteenHasSet;
	private TextView tvSixteenNotSet;
	private ImageView imgSixteenHasBook;
	//17
	private RelativeLayout rlSeventeen;
	private TextView tvSeventeenTime;
	private TextView tvSeventeenObject;
	private TextView tvSeventeenPrice;
	private ImageView imgSeventeenSelect;
	private LinearLayout llSeventeenHasSet;
	private TextView tvSeventeenNotSet;
	private ImageView imgSeventeenHasBook;
	//18
	private RelativeLayout rlEighteen;
	private TextView tvEighteenTime;
	private TextView tvEighteenObject;
	private TextView tvEighteenPrice;
	private ImageView imgEighteenSelect;
	private LinearLayout llEighteenHasSet;
	private TextView tvEighteenNotSet;
	private ImageView imgEighteenHasBook;
	//19
	private RelativeLayout rlNineteen;
	private TextView tvNineteenTime;
	private TextView tvNineteenObject;
	private TextView tvNineteenPrice;
	private ImageView imgNineteenSelect;
	private LinearLayout llNineteenHasSet;
	private TextView tvNineteenNotSet;
	private ImageView imgNineteenHasBook;
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
	//20
	private RelativeLayout rlTwenty;
	private TextView tvTwentyTime;
	private TextView tvTwentyObject;
	private TextView tvTwentyPrice;
	private ImageView imgTwentySelect;
	private LinearLayout llTwentyHasSet;
	private TextView tvTwentyNotSet;
	private ImageView imgTwentyHasBook;
	//21
	private RelativeLayout rlTwentyOne;
	private TextView tvTwentyOneTime;
	private TextView tvTwentyOneObject;
	private TextView tvTwentyOnePrice;
	private ImageView imgTwentyOneSelect;
	private LinearLayout llTwentyOneHasSet;
	private TextView tvTwentyOneNotSet;
	private ImageView imgTwentyOneHasBook;
	//22
	private RelativeLayout rlTwentyTwo;
	private TextView tvTwentyTwoTime;
	private TextView tvTwentyTwoObject;
	private TextView tvTwentyTwoPrice;
	private ImageView imgTwentyTwoSelect;
	private LinearLayout llTwentyTwoHasSet;
	private TextView tvTwentyTwoNotSet;
	private ImageView imgTwentyTwoHasBook;
	//23
	private RelativeLayout rlTwentyThree;
	private TextView tvTwentyThreeTime;
	private TextView tvTwentyThreeObject;
	private TextView tvTwentyThreePrice;
	private ImageView imgTwentyThreeSelect;
	private LinearLayout llTwentyThreeHasSet;
	private TextView tvTwentyThreeNotSet;
	private ImageView imgTwentyThreeHasBook;
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
	private RelativeLayout rlBottom;
	private RelativeLayout rlCloseClass;
	private LinearLayout llOpenClass;
	private Button btnSetClass;
	private Button btnConfirmOpen;
	private Button btnCloseClass;
	private ArrayList<Integer> chosedHour = new ArrayList<Integer>();
	private boolean chosedIsRest;
	private List<DefaultSchedule> defaultSchedule = new ArrayList<DefaultSchedule>();
	private int SchedulePosition;
	private int IsChosed = 0;
	private boolean ISClickCalender = false;
	private boolean ISCleanChosedHour = false;
	private boolean isNotSet = false;
	private ImageView imgChoseAll;
	private RelativeLayout rlCheck;
	private LinearLayout llCheck;
	private RelativeLayout rlOpenedCheck;
	private LinearLayout llOpenedCheck;
	private ImageView imgOpenedCheck;
	private List<Schedule> chosedSchedule = new ArrayList<Schedule>();
	private boolean isFullDown = false;
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
//		mGestureDetector =  new GestureDetector(mActivity,new MyOnGestureListener());
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
		rlBottom = (RelativeLayout)view.findViewById(R.id.rl_bottom);
		rlCloseClass = (RelativeLayout)view.findViewById(R.id.rl_colse_class);
		llOpenClass = (LinearLayout)view.findViewById(R.id.ll_open_class);
		btnSetClass = (Button)view.findViewById(R.id.btn_set_class);
		btnConfirmOpen = (Button)view.findViewById(R.id.btn_confirm_open);
		btnCloseClass = (Button)view.findViewById(R.id.btn_close_class);
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
		mMorningSSet = (LinearLayout) view.findViewById(R.id.mor_hide_part);
		mAfternoonSet = (LinearLayout) view.findViewById(R.id.afternoon_hide_part);
		mNightSet = (LinearLayout) view.findViewById(R.id.night_hide_part);
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
		//5
		rlFive = (RelativeLayout)mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.rl_time_set_object);
		tvFiveTime = (TextView)mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.tv_time);
		tvFiveObject = (TextView)mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.tv_object);
		tvFivePrice = (TextView)mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.tv_price);
		imgFiveSelect = (ImageView)mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.img_select);
		llFiveHasSet = (LinearLayout)mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.ll_has_set);
		tvFiveNotSet = (TextView)mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.tv_not_set);
		imgFiveHasBook = (ImageView)mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.img_has_book);
		//6
		rlSix = (RelativeLayout)mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.rl_time_set_object);
		tvSixTime = (TextView)mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.tv_time);
		tvSixObject = (TextView)mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.tv_object);
		tvSixPrice = (TextView)mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.tv_price);
		imgSixSelect = (ImageView)mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.img_select);
		llSixHasSet = (LinearLayout)mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.ll_has_set);
		tvSixNotSet = (TextView)mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.tv_not_set);
		imgSixHasBook = (ImageView)mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.img_has_book);
		//7
		rlSeven = (RelativeLayout)mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.rl_time_set_object);
		tvSevenTime = (TextView)mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.tv_time);
		tvSevenObject = (TextView)mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.tv_object);
		tvSevenPrice = (TextView)mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.tv_price);
		imgSevenSelect = (ImageView)mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.img_select);
		llSevenHasSet = (LinearLayout)mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.ll_has_set);
		tvSevenNotSet = (TextView)mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.tv_not_set);
		imgSevenHasBook = (ImageView)mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.img_has_book);
		//8
		rlEight = (RelativeLayout)mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.rl_time_set_object);
		tvEightTime = (TextView)mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.tv_time);
		tvEightObject = (TextView)mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.tv_object);
		tvEightPrice = (TextView)mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.tv_price);
		imgEightSelect = (ImageView)mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.img_select);
		llEightHasSet = (LinearLayout)mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.ll_has_set);
		tvEightNotSet = (TextView)mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.tv_not_set);
		imgEightHasBook = (ImageView)mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.img_has_book);
		//9
		rlNine = (RelativeLayout)mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.rl_time_set_object);
		tvNineTime = (TextView)mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.tv_time);
		tvNineObject = (TextView)mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.tv_object);
		tvNinePrice = (TextView)mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.tv_price);
		imgNineSelect = (ImageView)mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.img_select);
		llNineHasSet = (LinearLayout)mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.ll_has_set);
		tvNineNotSet = (TextView)mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.tv_not_set);
		imgNineHasBook = (ImageView)mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.img_has_book);
		//10
		rlTen = (RelativeLayout)mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.rl_time_set_object);
		tvTenTime = (TextView)mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.tv_time);
		tvTenObject = (TextView)mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.tv_object);
		tvTenPrice = (TextView)mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.tv_price);
		imgTenSelect = (ImageView)mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.img_select);
		llTenHasSet = (LinearLayout)mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.ll_has_set);
		tvTenNotSet = (TextView)mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.tv_not_set);
		imgTenHasBook = (ImageView)mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.img_has_book);
		//11
		rlEleven = (RelativeLayout)mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.rl_time_set_object);
		tvElevenTime = (TextView)mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.tv_time);
		tvElevenObject = (TextView)mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.tv_object);
		tvElevenPrice = (TextView)mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.tv_price);
		imgElevenSelect = (ImageView)mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.img_select);
		llElevenHasSet = (LinearLayout)mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.ll_has_set);
		tvElevenNotSet = (TextView)mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.tv_not_set);
		imgElevenHasBook = (ImageView)mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.img_has_book);
		//12
		rlTwelve = (RelativeLayout)mMorningSSet.findViewById(R.id.tweleve_part).findViewById(R.id.rl_time_set_object);
		tvTwelveTime = (TextView)mMorningSSet.findViewById(R.id.tweleve_part).findViewById(R.id.tv_time);
		tvTwelveObject = (TextView)mMorningSSet.findViewById(R.id.tweleve_part).findViewById(R.id.tv_object);
		tvTwelvePrice = (TextView)mMorningSSet.findViewById(R.id.tweleve_part).findViewById(R.id.tv_price);
		imgTwelveSelect = (ImageView)mMorningSSet.findViewById(R.id.tweleve_part).findViewById(R.id.img_select);
		llTwelveHasSet = (LinearLayout)mMorningSSet.findViewById(R.id.tweleve_part).findViewById(R.id.ll_has_set);
		tvTwelveNotSet = (TextView)mMorningSSet.findViewById(R.id.tweleve_part).findViewById(R.id.tv_not_set);
		imgTwelveHasBook = (ImageView)mMorningSSet.findViewById(R.id.tweleve_part).findViewById(R.id.img_has_book);
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
		
		//13
		rlThirteen = (RelativeLayout)mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.rl_time_set_object);
		tvThirteenTime = (TextView)mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.tv_time);
		tvThirteenObject = (TextView)mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.tv_object);
		tvThirteenPrice = (TextView)mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.tv_price);
		imgThirteenSelect = (ImageView)mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.img_select);
		llThirteenHasSet = (LinearLayout)mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.ll_has_set);
		tvThirteenNotSet = (TextView)mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.tv_not_set);
		imgThirteenHasBook = (ImageView)mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.img_has_book);
		//14
		rlFourteen = (RelativeLayout)mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.rl_time_set_object);
		tvFourteenTime = (TextView)mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.tv_time);
		tvFourteenObject = (TextView)mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.tv_object);
		tvFourteenPrice = (TextView)mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.tv_price);
		imgFourteenSelect = (ImageView)mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.img_select);
		llFourteenHasSet = (LinearLayout)mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.ll_has_set);
		tvFourteenNotSet = (TextView)mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.tv_not_set);
		imgFourteenHasBook = (ImageView)mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.img_has_book);
		//15
		rlFifteen = (RelativeLayout)mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.rl_time_set_object);
		tvFifteenTime = (TextView)mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.tv_time);
		tvFifteenObject = (TextView)mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.tv_object);
		tvFifteenPrice = (TextView)mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.tv_price);
		imgFifteenSelect = (ImageView)mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.img_select);
		llFifteenHasSet = (LinearLayout)mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.ll_has_set);
		tvFifteenNotSet = (TextView)mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.tv_not_set);
		imgFifteenHasBook = (ImageView)mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.img_has_book);
		//16
		rlSixteen = (RelativeLayout)mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.rl_time_set_object);
		tvSixteenTime = (TextView)mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.tv_time);
		tvSixteenObject = (TextView)mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.tv_object);
		tvSixteenPrice = (TextView)mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.tv_price);
		imgSixteenSelect = (ImageView)mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.img_select);
		llSixteenHasSet = (LinearLayout)mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.ll_has_set);
		tvSixteenNotSet = (TextView)mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.tv_not_set);
		imgSixteenHasBook = (ImageView)mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.img_has_book);
		//17
		rlSeventeen = (RelativeLayout)mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.rl_time_set_object);
		tvSeventeenTime = (TextView)mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.tv_time);
		tvSeventeenObject = (TextView)mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.tv_object);
		tvSeventeenPrice = (TextView)mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.tv_price);
		imgSeventeenSelect = (ImageView)mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.img_select);
		llSeventeenHasSet = (LinearLayout)mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.ll_has_set);
		tvSeventeenNotSet = (TextView)mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.tv_not_set);
		imgSeventeenHasBook = (ImageView)mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.img_has_book);
		//18
		rlEighteen = (RelativeLayout)mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.rl_time_set_object);
		tvEighteenTime = (TextView)mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.tv_time);
		tvEighteenObject = (TextView)mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.tv_object);
		tvEighteenPrice = (TextView)mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.tv_price);
		imgEighteenSelect = (ImageView)mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.img_select);
		llEighteenHasSet = (LinearLayout)mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.ll_has_set);
		tvEighteenNotSet = (TextView)mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.tv_not_set);
		imgEighteenHasBook = (ImageView)mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.img_has_book);
		//19
		rlNineteen = (RelativeLayout)mAfternoonSet.findViewById(R.id.nineteen_part).findViewById(R.id.rl_time_set_object);
		tvNineteenTime = (TextView)mAfternoonSet.findViewById(R.id.nineteen_part).findViewById(R.id.tv_time);
		tvNineteenObject = (TextView)mAfternoonSet.findViewById(R.id.nineteen_part).findViewById(R.id.tv_object);
		tvNineteenPrice = (TextView)mAfternoonSet.findViewById(R.id.nineteen_part).findViewById(R.id.tv_price);
		imgNineteenSelect = (ImageView)mAfternoonSet.findViewById(R.id.nineteen_part).findViewById(R.id.img_select);
		llNineteenHasSet = (LinearLayout)mAfternoonSet.findViewById(R.id.nineteen_part).findViewById(R.id.ll_has_set);
		tvNineteenNotSet = (TextView)mAfternoonSet.findViewById(R.id.nineteen_part).findViewById(R.id.tv_not_set);
		imgNineteenHasBook = (ImageView)mAfternoonSet.findViewById(R.id.nineteen_part).findViewById(R.id.img_has_book);
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
		//20
		rlTwenty = (RelativeLayout)mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.rl_time_set_object);
		tvTwentyTime = (TextView)mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.tv_time);
		tvTwentyObject = (TextView)mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.tv_object);
		tvTwentyPrice = (TextView)mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.tv_price);
		imgTwentySelect = (ImageView)mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.img_select);
		llTwentyHasSet = (LinearLayout)mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.ll_has_set);
		tvTwentyNotSet = (TextView)mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.tv_not_set);
		imgTwentyHasBook = (ImageView)mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.img_has_book);
		//21
		rlTwentyOne = (RelativeLayout)mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.rl_time_set_object);
		tvTwentyOneTime = (TextView)mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.tv_time);
		tvTwentyOneObject = (TextView)mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.tv_object);
		tvTwentyOnePrice = (TextView)mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.tv_price);
		imgTwentyOneSelect = (ImageView)mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.img_select);
		llTwentyOneHasSet = (LinearLayout)mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.ll_has_set);
		tvTwentyOneNotSet = (TextView)mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.tv_not_set);
		imgTwentyOneHasBook = (ImageView)mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.img_has_book);
		//22
		rlTwentyTwo = (RelativeLayout)mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.rl_time_set_object);
		tvTwentyTwoTime = (TextView)mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.tv_time);
		tvTwentyTwoObject = (TextView)mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.tv_object);
		tvTwentyTwoPrice = (TextView)mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.tv_price);
		imgTwentyTwoSelect = (ImageView)mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.img_select);
		llTwentyTwoHasSet = (LinearLayout)mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.ll_has_set);
		tvTwentyTwoNotSet = (TextView)mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.tv_not_set);
		imgTwentyTwoHasBook = (ImageView)mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.img_has_book);
		//23
		rlTwentyThree = (RelativeLayout)mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.rl_time_set_object);
		tvTwentyThreeTime = (TextView)mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.tv_time);
		tvTwentyThreeObject = (TextView)mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.tv_object);
		tvTwentyThreePrice = (TextView)mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.tv_price);
		imgTwentyThreeSelect = (ImageView)mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.img_select);
		llTwentyThreeHasSet = (LinearLayout)mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.ll_has_set);
		tvTwentyThreeNotSet = (TextView)mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.tv_not_set);
		imgTwentyThreeHasBook = (ImageView)mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.img_has_book);
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
		imgChoseAll = (ImageView)view.findViewById(R.id.img_check);
		//rlCheck = (RelativeLayout)view.findViewById(R.id.rl_check);
		llCheck = (LinearLayout)view.findViewById(R.id.ll_check);
		llOpenedCheck = (LinearLayout)view.findViewById(R.id.ll_opened_check);
		imgOpenedCheck = (ImageView)view.findViewById(R.id.img_open_check);
		rlOpenedCheck = (RelativeLayout)view.findViewById(R.id.rl_opened_check);
	}
	
	private void setOneHourView(int hour)
	{
		switch (hour) {
		case 5:
			if (imgFiveSelect.getVisibility() == View.VISIBLE)
			{
				imgFiveSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgFiveSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		case 6:
			if (imgSixSelect.getVisibility() == View.VISIBLE)
			{
				imgSixSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgSixSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		case 7:
			if (imgSevenSelect.getVisibility() == View.VISIBLE)
			{
				imgSevenSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgSevenSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		case 8:
			if (imgEightSelect.getVisibility() == View.VISIBLE)
			{
				imgEightSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgEightSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		case 9:
			if (imgNineSelect.getVisibility() == View.VISIBLE)
			{
				imgNineSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgNineSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		case 10:
			if (imgTenSelect.getVisibility() == View.VISIBLE)
			{
				imgTenSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgTenSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		case 11:
			if (imgElevenSelect.getVisibility() == View.VISIBLE)
			{
				imgElevenSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgElevenSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		case 12:
			if (imgTwelveSelect.getVisibility() == View.VISIBLE)
			{
				imgTwelveSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgTwelveSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		case 13:
			if (imgThirteenSelect.getVisibility() == View.VISIBLE)
			{
				imgThirteenSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgThirteenSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		case 14:
			if (imgFourteenSelect.getVisibility() == View.VISIBLE)
			{
				imgFourteenSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgFourteenSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		case 15:
			if (imgFifteenSelect.getVisibility() == View.VISIBLE)
			{
				imgFifteenSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgFifteenSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		case 16:
			if (imgSixteenSelect.getVisibility() == View.VISIBLE)
			{
				imgSixteenSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgSixteenSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		case 17:
			if (imgSeventeenSelect.getVisibility() == View.VISIBLE)
			{
				imgSeventeenSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgSeventeenSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		case 18:
			if (imgEighteenSelect.getVisibility() == View.VISIBLE)
			{
				imgEighteenSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgEighteenSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		case 19:
			if (imgNineteenSelect.getVisibility() == View.VISIBLE)
			{
				imgNineteenSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgNineteenSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			
			break;
		case 20:
			if (imgTwentySelect.getVisibility() == View.VISIBLE)
			{
				imgTwentySelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else
			{
				imgTwentySelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			
			break;
		case 21:
			if (imgTwentyOneSelect.getVisibility() == View.VISIBLE)
			{
				imgTwentyOneSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgTwentyOneSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			
			break;
		case 22:
			if (imgTwentyTwoSelect.getVisibility() == View.VISIBLE){
				imgTwentyTwoSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgTwentyTwoSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			
			break;
		case 23:
			if (imgTwentyThreeSelect.getVisibility() == View.VISIBLE)
			{
				imgTwentyThreeSelect.setVisibility(View.GONE);
				int position = chosedHour.indexOf(hour);
				chosedHour.remove(position);
				imgChoseAll.setVisibility(View.GONE);
				imgOpenedCheck.setVisibility(View.GONE);
			}else{
				imgTwentyThreeSelect.setVisibility(View.VISIBLE);
				chosedHour.add(hour);
			}
			break;
		default:
			break;
		}
	}
	
	
	
	private void setOneHour(int hour,int index)
	{
		if (!finishLoadingData) {
		Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
		return;
	}
	if (isNotSet)
	{
		goToDefault();
	}else
	{
	
	if (equalsDate(calToday.getTime(), calSelected.getTime()) && hour <= mDeadHour) {
		Toast.makeText(mActivity, "当前时间不能设置", Toast.LENGTH_SHORT).show();
	} else {
		if (bookArray[index]) {
			Toast.makeText(mActivity, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
			return;
		}
		int ischosed = 0;
		if (stateArray[index])
		{
			ischosed = 1;
		}else{
			ischosed = 2;
		}
		
		if (IsChosed != ischosed)
		{
			if (IsChosed == 0)
			{
				IsChosed = ischosed;
			}else{
				if (chosedHour.size()>1)
				{
					Toast.makeText(mActivity, "未开课和已开课不能同时选择",0).show();
					return;
				}else{
					for (int h:chosedHour)
					{
						setOneHourView(h);
					}
					IsChosed = ischosed;
				}
			}
		}
		
		if (!isFullDown)
		{
		mScrollView.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
			}
		},500);
		}
		
		if (!isFullDown){
			isFullDown = true;
		}
		setOneHourView(hour);
//		chosedHour.add(hour);
		chosedIsRest = stateArray[index];
		if (chosedHour.size()!=0)
		{
			rlBottom.setVisibility(View.VISIBLE);
			if (chosedIsRest)
			{
				rlCloseClass.setVisibility(View.VISIBLE);
				llOpenClass.setVisibility(View.GONE);
				
			}else{
				llOpenClass.setVisibility(View.VISIBLE);
				rlCloseClass.setVisibility(View.GONE);
			}
		}
		else{
			rlBottom.setVisibility(View.GONE);
			imgChoseAll.setVisibility(View.GONE);
			imgOpenedCheck.setVisibility(View.GONE);
			IsChosed = 0;
			isFullDown = false;
		}
	}
	}
	}

	private void addListeners() {
		/*
		 * click title bar triangle left
		 */
		llCheck.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub

				if (imgChoseAll.getVisibility() == View.VISIBLE)
				{
					imgChoseAll.setVisibility(View.GONE);
					rlBottom.setVisibility(View.GONE);
					for (Schedule schedule:chosedSchedule)
					{
						if (schedule.getIsrest() == 1&& schedule.getExpire() !=1)
						{
							if (chosedHour.contains(schedule.getHour()))
							{
								setOneHourView(schedule.getHour());
							}
						}
					}
				}else{
					imgChoseAll.setVisibility(View.VISIBLE);
					for (Schedule schedule:chosedSchedule)
					{
						if (schedule.getIsrest() == 1&& schedule.getExpire() !=1)
						{
							if (!chosedHour.contains(schedule.getHour()))
							{
								setOneHourView(schedule.getHour());
							}
						}
					}
				}
			}
		});
		
		llOpenedCheck.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				if (imgOpenedCheck.getVisibility() == View.VISIBLE)
				{
					imgOpenedCheck.setVisibility(View.GONE);
					rlBottom.setVisibility(View.GONE);
					for (Schedule schedule:chosedSchedule)
					{
						if (schedule.getIsrest() == 0&& schedule.getExpire() !=1)
						{
							if (chosedHour.contains(schedule.getHour()))
							{
								setOneHourView(schedule.getHour());
							}
						}
					}
				}else{
					imgOpenedCheck.setVisibility(View.VISIBLE);
					for (Schedule schedule:chosedSchedule)
					{
						if (schedule.getIsrest() == 0&& schedule.getExpire() !=1)
						{
							if (!chosedHour.contains(schedule.getHour()))
							{
								setOneHourView(schedule.getHour());
							}
						}
					}
				}
			}
		});
		
		
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
					isFullDown = false;
				}
				if (scrollY > (Settings.DISPLAY_WIDTH - 12) / 7 * (lineNum - 1)) {
					mHangingArrow.setVisibility(View.VISIBLE);
				} else {
					mHangingArrow.setVisibility(View.INVISIBLE);
				}
			}
		});
		
		btnSetClass.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				mActivity.startActivityForResult(new Intent(mActivity, ClassTimeSetActivity_.class).putIntegerArrayListExtra("hour", chosedHour).putExtra("rest",chosedIsRest).putExtra("result", scheduleResult)
						.putExtra("day", TimeUtil.calendarToString(calSelected)), 2001);

			}
		});
		
		btnConfirmOpen.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				new ChangeAllDayScheduleTask("1").execute();
				
			}
		});
		
		btnCloseClass.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				new ChangeAllDayScheduleTask("2").execute();
			}
		});
		
		
		rlTwentyThree.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(23,18);
				
				//mScrollView.setMode(Mode.PULL_FROM_END);
			}
		});
		
		
		rlTwentyTwo.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(22, 17);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				},500);
//				}
				
				//mScrollView.setMode(Mode.PULL_FROM_END);
			}
		});
		
		rlTwentyOne.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(21, 16);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				},500);
//				}
				//mScrollView.setMode(Mode.PULL_FROM_END);
			}
		});
		
		
		rlTwenty.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(20, 15);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				},500);
//				}
				//mScrollView.setMode(Mode.PULL_FROM_END);
			}
		});
		
		rlNineteen.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(19, 14);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				},500);
//				}
				//mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
		
		rlEighteen.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(18, 13);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//						}
//				},500);
//				}
				//mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
		
		rlSeventeen.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(17, 12);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				},500);
//				}
				//mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
			}
		});
		
		rlSixteen.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(16, 11);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				},500);
//				}
			}
		});
		
		rlFifteen.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(15, 10);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				},500);
//				}
			}
		});
		
		rlFourteen.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(14, 9);
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				}, 500);
			}
		});
		
		rlThirteen.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(13, 8);
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				}, 500);
			}
		});
		
		rlTwelve.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(12, 7);
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				}, 500);
			}
		});
		
		rlEleven.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(11, 6);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				},500);
//				}
			}
		});
		
		rlTen.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(10,5);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				},500);
//				}
			}
		});
		
		rlNine.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(9,4);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				},500);
//				}
			}
		});
		
		rlEight.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(8,3);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				},500);
//				}
			}
		});
		
		rlSeven.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(7,2);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				},500);
//				}
			}
		});
		
		rlSix.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(6,1);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				},500);
//				}
			}
		});
		
		rlFive.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				setOneHour(5,0);
//				if (!isFullDown)
//				{
//				mScrollView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						mScrollView.getRefreshableView().fullScroll(ScrollView.FOCUS_DOWN);
//					}
//				},500);
//				}
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
						isFullDown = false;
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
						isFullDown = false;
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
//		new GetScheduleTask().execute();
		// if (!isFirstReq && mLoadingDialog != null) {
		// mLoadingDialog.show();
		// }
		// mScrollView.setRefreshing();
	}

	private void setTimes() {
		tvFiveTime.setText("05:00");
		tvSixTime.setText("06:00");
		tvSevenTime.setText("07:00");
		tvEightTime.setText("08:00");
		tvNineTime.setText("09:00");
		tvTenTime.setText("10:00");
		tvElevenTime.setText("11:00");
		tvTwelveTime.setText("12:00");
		tvThirteenTime.setText("13:00");
		tvFourteenTime.setText("14:00");
		tvFifteenTime.setText("15:00");
		tvSixteenTime.setText("16:00");
		tvSeventeenTime.setText("17:00");
		tvEighteenTime.setText("18:00");
		tvNineteenTime.setText("19:00");
		tvTwentyTime.setText("20:00");
		tvTwentyOneTime.setText("21:00");
		tvTwentyTwoTime.setText("22:00");
		tvTwentyThreeTime.setText("23:00");
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
		new RefreshBallStateTask(true,true).execute();
		new GetDefaultScheduleTask(calSelected).execute();
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
		new RefreshBallStateTask(true,true).execute();
		new GetDefaultScheduleTask(calSelected).execute();
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
//				chosedHour.clear();
//				IsChosed = 0;
				new GetDefaultScheduleTask(calendar).execute();
//				Intent intent = new Intent(mActivity,ActivityDateSet.class);
//				startActivity(intent);
				
				/*
				 *在当前页面设置时间段
				 */

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
			new RefreshBallStateTask(false,false).execute();
		}
	}

	/*
	 * get coach schedule
	 */

	// 获得当前日期的课程
	boolean isFirstReq = true;
	int mDeadHour;

	private class GetDefaultScheduleTask extends AsyncTask<Void, Void, GetDefaultScheduleResult>
	{
		private Calendar mCalendar;
		public GetDefaultScheduleTask(Calendar calendar)
		{
			mCalendar = calendar;
//			if (chosedHour.size() == 0)
//			{
//				rlBottom.setVisibility(View.GONE);
//			}
			chosedHour.clear();
			IsChosed = 0;
			isFullDown = false;
			rlBottom.setVisibility(View.GONE);
			imgChoseAll.setVisibility(View.GONE);
			imgOpenedCheck.setVisibility(View.GONE);
		}
		
		JSONAccessor accessor = new JSONAccessor(mActivity, JSONAccessor.METHOD_POST);
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		@Override
		protected GetDefaultScheduleResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GETDEFAULTSCHEDULE");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			return accessor.execute(Settings.SCHEDULE_URL, param, GetDefaultScheduleResult.class);
		}
		@Override
		protected void onPostExecute(GetDefaultScheduleResult result) {
			super.onPostExecute(result);
			defaultSchedule = result.getDefaultSchedule();
			checkUpdate(mCalendar,true,true);
		}
	}
	
	
	
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
						 //checkUpdate(calSelected,false,false);
					}
				}
				/*
				 * 刷新当前选择日期位置上方行数
				 */
				setSelectLine(getSelectLine(calSelected));

				/*
				 * reform data
				 */
				isNotSet = false;
				if (result.getCode() == 5) {
					goToDefault();
					isNotSet = true;
					NotSet();
				}
				new ReformTask().execute();

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
		boolean IsClickCalender;
		boolean IsCleanChosedHour;
		
		public RefreshBallStateTask(boolean isClickCalender,boolean isCleanChosedHour) {
			// TODO Auto-generated constructor stub
			IsClickCalender = isClickCalender;
			IsCleanChosedHour = isCleanChosedHour;
		}
		
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

				boolean isOpen = false;
				tempHour = daySpan.get(str);
				dayPos = getStringPositionInAdapter(str);
				for (String hourStr : tempHour.keySet()) {
					tempsc = tempHour.get(hourStr);
					if (tempsc.getExpire() == 0)
					{
						if (tempsc.getIsrest() == 0)
						{
							isOpen = true;
						}
					}
//					if (tempsc.getExpire()==1||tempsc.getIsrest() == 1) {
//						
//					}
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
			checkUpdate(calSelected,IsClickCalender,IsCleanChosedHour);
			
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
	public void checkUpdate(Calendar selectedDate,boolean isClickCalender,boolean isCleanChosedHour) {
		ISClickCalender = isClickCalender;
		ISCleanChosedHour = isCleanChosedHour;
		chosedSchedule.clear();
		if (ISCleanChosedHour)
		{
			chosedHour.clear();
			IsChosed = 0;
		}
		if (scheduleResult == null)
			return;
		if (scheduleResult.getDatelist() == null)
			return;
		
		for (int positon = 0;positon<scheduleResult.getDatelist().size();positon++) {

			Schedule schedule = scheduleResult.getDatelist().get(positon);
			Date date2;
			try {
				date2 = TimeUtil.StringToDate(schedule.getDate());
			} catch (Exception e) {
				return;
			}

			if (equalsDate(selectedDate.getTime(), date2)) {
				SchedulePosition = positon;
				chosedSchedule.add(schedule);
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
					switch (schedule.getHour()) {
					case 5:
						modifyOneHourState(tvFiveTime, tvFivePrice, tvFiveObject,tvFiveNotSet,imgFiveSelect,imgFiveHasBook,llFiveHasSet,rlFive, 0,schedule);
						break;
					case 6:
						modifyOneHourState(tvSixTime, tvSixPrice, tvSixObject,tvSixNotSet,imgSixSelect,imgSixHasBook,llSixHasSet,rlSix,1,schedule);
						break;
					case 7:
						modifyOneHourState(tvSevenTime,tvSevenPrice,tvSevenObject,tvSevenNotSet,imgSevenSelect,imgSevenHasBook,llSevenHasSet,rlSeven,2,schedule);
						break;
					case 8:
						modifyOneHourState(tvEightTime,tvEightPrice,tvEightObject,tvEightNotSet,imgEightSelect,imgEightHasBook,llEightHasSet,rlEight,3,schedule);
						break;
					case 9:
						modifyOneHourState(tvNineTime,tvNinePrice,tvNineObject,tvNineNotSet,imgNineSelect,imgNineHasBook,llNineHasSet,rlNine, 4,schedule);
						break;
					case 10:
						modifyOneHourState(tvTenTime,tvTenPrice,tvTenObject,tvTenNotSet,imgTenSelect,imgTenHasBook,llTenHasSet,rlTen,5,schedule);
						break;
					case 11:
						modifyOneHourState(tvElevenTime,tvElevenPrice,tvElevenObject,tvElevenNotSet,imgElevenSelect,imgElevenHasBook,llElevenHasSet,rlEleven,6,schedule);
						break;
					case 12:
						modifyOneHourState(tvTwelveTime,tvTwelvePrice,tvTwelveObject,tvTwelveNotSet,imgTwelveSelect,imgTwelveHasBook,llTwelveHasSet,rlTwelve,7,schedule);
						break;
					case 13:
						modifyOneHourState(tvThirteenTime,tvThirteenPrice,tvThirteenObject,tvThirteenNotSet,imgThirteenSelect,imgThirteenHasBook,llThirteenHasSet,rlThirteen, 8,schedule);
						break;
					case 14:
						modifyOneHourState(tvFourteenTime,tvFourteenPrice,tvFourteenObject,tvFourteenNotSet,imgFourteenSelect,imgFourteenHasBook,llFourteenHasSet,rlFourteen,9,schedule);
						break;
					case 15:
						modifyOneHourState(tvFifteenTime,tvFifteenPrice,tvFifteenObject,tvFifteenNotSet,imgFifteenSelect,imgFifteenHasBook,llFifteenHasSet, rlFifteen,10,schedule);
						break;
					case 16:
						modifyOneHourState(tvSixteenTime,tvSixteenPrice,tvSixteenObject,tvSixteenNotSet,imgSixteenSelect,imgSixteenHasBook,llSixteenHasSet, rlSixteen, 11,schedule);
						break;
					case 17:
						modifyOneHourState(tvSeventeenTime,tvSeventeenPrice,tvSeventeenObject,tvSeventeenNotSet,imgSeventeenSelect,imgSeventeenHasBook,llSeventeenHasSet, rlSeventeen, 12,schedule);
						break;
					case 18:
						modifyOneHourState(tvEighteenTime,tvEighteenPrice,tvEighteenObject,tvEighteenNotSet,imgEighteenSelect,imgEighteenHasBook,llEighteenHasSet, rlEighteen,13,schedule);
						break;
					case 19:
						modifyOneHourState(tvNineteenTime,tvNineteenPrice,tvNineteenObject,tvNineteenNotSet,imgNineteenSelect,imgNineteenHasBook,llNineteenHasSet, rlNineteen, 14,schedule);
						break;
					case 20:
						modifyOneHourState(tvTwentyTime,tvTwentyPrice,tvTwentyObject,tvTwentyNotSet,imgTwentySelect,imgTwentyHasBook,llTwentyHasSet, rlTwenty, 15,schedule);
						break;
					case 21:
						modifyOneHourState(tvTwentyOneTime,tvTwentyOnePrice,tvTwentyOneObject,tvTwentyOneNotSet,imgTwentyOneSelect,imgTwentyOneHasBook,llTwentyOneHasSet, rlTwentyOne,16,schedule);
						break;
					case 22:
						modifyOneHourState(tvTwentyTwoTime,tvTwentyTwoPrice,tvTwentyTwoObject,tvTwentyTwoNotSet,imgTwentyTwoSelect,imgTwentyTwoHasBook,llTwentyTwoHasSet, rlTwentyTwo,17,schedule);
						break;
					case 23:
						modifyOneHourState(tvTwentyThreeTime,tvTwentyThreePrice,tvTwentyThreeObject,tvTwentyThreeNotSet,imgTwentyThreeSelect,imgTwentyThreeHasBook,llTwentyThreeHasSet, rlTwentyThree,18,schedule);
						break;
					}
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
	
	
	private void NotSet()
	{
		modifyOneHourState(tvFiveTime, tvFivePrice, tvFiveObject,tvFiveNotSet,imgFiveSelect,imgFiveHasBook,llFiveHasSet,rlFive, 0,null);
		modifyOneHourState(tvSixTime, tvSixPrice, tvSixObject,tvSixNotSet,imgSixSelect,imgSixHasBook,llSixHasSet,rlSix,1,null);
		modifyOneHourState(tvSevenTime,tvSevenPrice,tvSevenObject,tvSevenNotSet,imgSevenSelect,imgSevenHasBook,llSevenHasSet,rlSeven,2,null);
		modifyOneHourState(tvEightTime,tvEightPrice,tvEightObject,tvEightNotSet,imgEightSelect,imgEightHasBook,llEightHasSet,rlEight,3,null);
		modifyOneHourState(tvNineTime,tvNinePrice,tvNineObject,tvNineNotSet,imgNineSelect,imgNineHasBook,llNineHasSet,rlNine, 4,null);
		modifyOneHourState(tvTenTime,tvTenPrice,tvTenObject,tvTenNotSet,imgTenSelect,imgTenHasBook,llTenHasSet,rlTen,5,null);
		modifyOneHourState(tvElevenTime,tvElevenPrice,tvElevenObject,tvElevenNotSet,imgElevenSelect,imgElevenHasBook,llElevenHasSet,rlEleven,6,null);
		modifyOneHourState(tvTwelveTime,tvTwelvePrice,tvTwelveObject,tvTwelveNotSet,imgTwelveSelect,imgTwelveHasBook,llTwelveHasSet,rlTwelve,7,null);
		modifyOneHourState(tvThirteenTime,tvThirteenPrice,tvThirteenObject,tvThirteenNotSet,imgThirteenSelect,imgThirteenHasBook,llThirteenHasSet,rlThirteen, 8,null);
		modifyOneHourState(tvFourteenTime,tvFourteenPrice,tvFourteenObject,tvFourteenNotSet,imgFourteenSelect,imgFourteenHasBook,llFourteenHasSet,rlFourteen,9,null);
		modifyOneHourState(tvFifteenTime,tvFifteenPrice,tvFifteenObject,tvFifteenNotSet,imgFifteenSelect,imgFifteenHasBook,llFifteenHasSet, rlFifteen,10,null);
		modifyOneHourState(tvSixteenTime,tvSixteenPrice,tvSixteenObject,tvSixteenNotSet,imgSixteenSelect,imgSixteenHasBook,llSixteenHasSet, rlSixteen, 11,null);
		modifyOneHourState(tvSeventeenTime,tvSeventeenPrice,tvSeventeenObject,tvSeventeenNotSet,imgSeventeenSelect,imgSeventeenHasBook,llSeventeenHasSet, rlSeventeen, 12,null);
		modifyOneHourState(tvEighteenTime,tvEighteenPrice,tvEighteenObject,tvEighteenNotSet,imgEighteenSelect,imgEighteenHasBook,llEighteenHasSet, rlEighteen,13,null);
		modifyOneHourState(tvNineteenTime,tvNineteenPrice,tvNineteenObject,tvNineteenNotSet,imgNineteenSelect,imgNineteenHasBook,llNineteenHasSet, rlNineteen, 14,null);
		modifyOneHourState(tvTwentyTime,tvTwentyPrice,tvTwentyObject,tvTwentyNotSet,imgTwentySelect,imgTwentyHasBook,llTwentyHasSet, rlTwenty, 15,null);
		modifyOneHourState(tvTwentyOneTime,tvTwentyOnePrice,tvTwentyOneObject,tvTwentyOneNotSet,imgTwentyOneSelect,imgTwentyOneHasBook,llTwentyOneHasSet, rlTwentyOne,16,null);
		modifyOneHourState(tvTwentyTwoTime,tvTwentyTwoPrice,tvTwentyTwoObject,tvTwentyTwoNotSet,imgTwentyTwoSelect,imgTwentyTwoHasBook,llTwentyTwoHasSet, rlTwentyTwo,17,null);
		modifyOneHourState(tvTwentyThreeTime,tvTwentyThreePrice,tvTwentyThreeObject,tvTwentyThreeNotSet,imgTwentyThreeSelect,imgTwentyThreeHasBook,llTwentyThreeHasSet, rlTwentyThree,18,null);
	}

	/**
	 * @param time
	 * @param price
	 * @param subject
	 * @param notSet
	 * @param imgHasBook
	 * @param llHasSet
	 * @param rlBack
	 * @param pos
	 * @param schedule
	 */
	private void modifyOneHourState(TextView time, TextView price, TextView subject,TextView notSet,ImageView imgSelect,ImageView imgHasBook,LinearLayout llHasSet,RelativeLayout rlBack,int pos,Schedule schedule) {
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
		if (ISCleanChosedHour)
		{
		if (imgSelect.getVisibility() == View.VISIBLE)
		{
			imgSelect.setVisibility(View.GONE);
		}
		}
//		else{
//			if (chosedHour.equals(schedule.getHour()))
//			{
//				if (imgSelect.getVisibility() == View.VISIBLE)
//				{
//					imgSelect.setVisibility(View.GONE);
//				}
//			}
//		}
		if (isNotSet||schedule == null)
		{
			rlBack.setBackgroundResource(R.drawable.date_set_not_set);
			llHasSet.setVisibility(View.INVISIBLE);
			notSet.setVisibility(View.VISIBLE);
			imgHasBook.setVisibility(View.GONE);
			notSet.setText("未设置");
		}else
		{
		if (schedule.getExpire() == 1)    //已过期
		{
			//rlBack.setBackground(getResources().getDrawable(R.drawable.date_set_passed));
			rlBack.setBackgroundResource(R.drawable.date_set_passed);
			llHasSet.setVisibility(View.INVISIBLE);
			notSet.setVisibility(View.VISIBLE);
			notSet.setTextColor(getResources().getColor(R.color.date_gray));
			notSet.setText("已过期");
			time.setTextColor(getResources().getColor(R.color.date_gray));
			imgHasBook.setVisibility(View.GONE);
			return;
		}
		
		if (schedule.getIsrest() ==1)  //未开课
		{
			//rlBack.setBackground(getResources().getDrawable(R.drawable.date_set_not_set));
			rlBack.setBackgroundResource(R.drawable.date_set_not_set);
			llHasSet.setVisibility(View.VISIBLE);
			notSet.setVisibility(View.INVISIBLE);
			String pricenum = schedule.getPrice();
			if (pricenum != null)
			price.setText(pricenum.contains(".") ? pricenum.substring(0, pricenum.indexOf(".")) : pricenum);
		else {
			price.setText("");
			}
			price.setTextColor(getResources().getColor(R.color.date_back));
			time.setTextColor(getResources().getColor(R.color.date_back));
			subject.setText(schedule.getSubject());
			subject.setTextColor(getResources().getColor(R.color.date_back));
			imgHasBook.setVisibility(View.GONE);
			stateArray[pos] = false;
			bookArray[pos] = false;
			if (ISClickCalender)
			{
			if (defaultSchedule.size() !=0)
			{
				for(int i=0;i<defaultSchedule.size();i++)
				{
					if (defaultSchedule.get(i).getHour() == pos+5)
					{
					price.setText(defaultSchedule.get(i).getPrice()+"");
					subject.setText(defaultSchedule.get(i).getSubject());
					scheduleResult.getDatelist().get(SchedulePosition).setPrice(defaultSchedule.get(i).getPrice()+"");
					scheduleResult.getDatelist().get(SchedulePosition).setSubject(defaultSchedule.get(i).getSubject());
					scheduleResult.getDatelist().get(SchedulePosition).setAddressdetail(defaultSchedule.get(i).getAddressdetail());
					break;
					}
				}
			}
			}
		}else{
			stateArray[pos] = true;
			llHasSet.setVisibility(View.VISIBLE);
			notSet.setVisibility(View.INVISIBLE);
			time.setTextColor(getResources().getColor(R.color.white));
			String pricenum = schedule.getPrice();
			if (pricenum != null)
			price.setText(pricenum.contains(".") ? pricenum.substring(0, pricenum.indexOf(".")) : pricenum);
		else {
			price.setText("");
			}
			subject.setText(schedule.getSubject());
			if (daySpan.get(TimeUtil.calendarToString(calSelected)) != null)
				schd = (Schedule) daySpan.get(TimeUtil.calendarToString(calSelected)).get(5 + pos + "");
			if (daySpan != null && schd != null && schd.getHasbooked() == 1) {
				//rlBack.setBackground(getResources().getDrawable(R.drawable.date_set_has_book));
				bookArray[pos] = true;
				rlBack.setBackgroundResource(R.drawable.date_set_has_book);
				imgHasBook.setVisibility(View.VISIBLE);
				if (schedule.getBookedername()!=null)
				{
					price.setText(schedule.getBookedername());
				}
				price.setTextColor(getResources().getColor(R.color.date_orange));
				subject.setTextColor(getResources().getColor(R.color.date_orange));
			} else {
				bookArray[pos] = false;
				//rlBack.setBackground(getResources().getDrawable(R.drawable.date_set_not_chosed));
				rlBack.setBackgroundResource(R.drawable.date_set_not_chosed);
				imgHasBook.setVisibility(View.GONE);
				price.setTextColor(getResources().getColor(R.color.date_green));
				subject.setTextColor(getResources().getColor(R.color.date_green));
			}
		}
		}
//		else{
//			if (isrest == 1) {
//				time.setTextColor(Color.parseColor("#d2d2d2"));
//				price.setTextColor(Color.parseColor("#d2d2d2"));
//				price.setText("未开课");
//				price.setBackgroundColor(Color.parseColor("#e5e5e5"));
//				stateArray[pos] = false;
//			} else {
//				time.setTextColor(Color.parseColor("#20b478"));
//				price.setTextColor(Color.parseColor("#d2d2d2"));
//				price.setBackgroundColor(Color.parseColor("#20b478"));
//				if (pricenum != null)
//					price.setText("单价  " + (pricenum.contains(".") ? pricenum.substring(0, pricenum.indexOf(".")) : pricenum) + "元");
//				else {
//					price.setText("");
//				}
//				stateArray[pos] = true;
//			}
//		}

//		if (equalsDate(calToday.getTime(), calSelected.getTime()) && (pos + 5) <= mDeadHour) {
//			select.setVisibility(View.INVISIBLE);
//			selectArray[pos] = true;
//		} else {
//			if (select.getVisibility() == View.INVISIBLE) {
//				select.setVisibility(View.VISIBLE);
//				select.setBackgroundResource(R.color.text_black);
//			}
//			selectArray[pos] = false;
//		}

//		if (daySpan.get(TimeUtil.calendarToString(calSelected)) != null)
//			schd = (Schedule) daySpan.get(TimeUtil.calendarToString(calSelected)).get(5 + pos + "");
//		if (daySpan != null && schd != null && schd.getHasbooked() == 1) {
//			select.setText("已约");
//			selectArray[pos] = true;
//			bookArray[pos] = true;
//			if (select.getVisibility() == View.INVISIBLE) {
//				select.setVisibility(View.VISIBLE);
//				// if (equalsDate(calToday.getTime(), calSelected.getTime()))
//				// select.setBackgroundResource(R.color.text_grey);
//				// else {
//				// select.setBackgroundColor(Color.parseColor("#000000"));
//				// }
//			}
//		} else {
//			select.setText("选中");
//			selectArray[pos] = false;
//			bookArray[pos] = false;
//			/*
//			 * select.setTextColor(getResources().getColor(R.color.white)); select.setBackgroundColor(Color.parseColor("#000000"));
//			 */
//		}
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
					if (type.equals("1"))
					{
					for (int hour:chosedHour)
					{
					if (schedule.getHour() == hour)
					{
						schedule.setIsrest(0);
					}
					}
					chosedScheduleArray.add(schedule);
					}else{
						for (int hour:chosedHour)
						{
						if (schedule.getHour() == hour)
						{
							schedule.setIsrest(1);
							chosedScheduleArray.add(schedule);
						}
						}
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
					chosedHour.clear();
					IsChosed = 0;
					isFullDown = false;
					rlBottom.setVisibility(View.GONE);
					imgChoseAll.setVisibility(View.GONE);
					imgOpenedCheck.setVisibility(View.GONE);
					if (type != null && type.equals("1")) {
//						isAllDayOpen = true;
//						mAllDaySetClose.setText("当天停课");
//						mAllDaySetClose.setBackgroundResource(R.drawable.shape_red_round);
						new ChangeClassStateTask(1, day).execute();
					} else {
//						isAllDayOpen = false;
//						mAllDaySetClose.setText("发布课程");
//						mAllDaySetClose.setBackgroundResource(R.drawable.shape_green_round);
						new ChangeClassStateTask(0, day).execute();
					}
					CommonUtils.showToast(mActivity.getApplicationContext(), "设置成功");
				} else {
					if (result.getCode() == 95) {
						if (result.getMessage() != null)
							CommonUtils.showToast(mActivity.getApplicationContext(), result.getMessage());
						CommonUtils.gotoLogin(mActivity);
					} else {
						if (result.getMessage() != null)
						{
							CommonUtils.showToast(mActivity.getApplicationContext(), result.getMessage());
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
							if (ballState.get(position).isOpen()) {
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
//			if (scheduleResult != null) {
//				boolean hasHourZero = false;
//				for (Schedule sc : scheduleResult.getDatelist()) {
//					if (sc.getDate() != null && sc.getDate().equals(day) && sc.getHour() == 0) {
//						hasHourZero = true;
//						sc.setState(state);
//					}
//				}
//				if (!hasHourZero) {
//					Schedule sd = new Schedule();
//					sd.setState(state);
//					scheduleResult.getDatelist().add(sd);
//				}
//			}
			
			if (scheduleResult != null) {
				boolean hasHourZero = false;
				for (int i = 0;i<scheduleResult.getDatelist().size();i++) {
					Schedule sc = scheduleResult.getDatelist().get(i);
					if (sc.getDate() != null && sc.getDate().equals(day)) {
						for (int hour:chosedHour)
						{
							if (sc.getHour()==hour)
							{
								if (state == 1)
								{
									scheduleResult.getDatelist().get(i).setIsrest(0);
								}
								else{
									scheduleResult.getDatelist().get(i).setIsrest(1);
								}
							}
						}
					}
				}
			}

//			if (daySpan != null) {
//				for (String date : daySpan.keySet()) {
//					if (date.equals(day)) {
//						HashMap<String, Schedule> hm = daySpan.get(date);
//						boolean hasSetState = false;
//						for (String hour : hm.keySet()) {
//							if (hour.equals("0")) {
//								hm.get("0").setState(state);
//								hasSetState = true;
//							}
//						}
//
//						if (!hasSetState) {
//							Schedule sd = new Schedule();
//							sd.setState(state);
//							hm.put("0", sd);
//						}
//					}
//				}
//			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			new RefreshBallStateTask(false,true).execute();
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
				updateLocalDateList(( (ArrayList<Schedule>)data.getSerializableExtra("list")), data.getStringExtra("day"));
			} catch (Exception e) {
				Log.e(TAG, "convert serializable error");
			}
		}
	}

	private void updateLocalDateList(ArrayList<Schedule> datelist, String day) {
		if (datelist != null && datelist.size() > 0) {
			new UpdateScheduleTask(datelist, day).execute();
		}
	}

	public class UpdateScheduleTask extends AsyncTask<Void, Void, Void> {
		private ArrayList<Schedule> datelist;
		boolean hasSc;
		String day;

		public UpdateScheduleTask(ArrayList<Schedule> datelist, String day) {
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
	
	
/*	 private String getActionName(int action) {
	        String name = "";
	        switch (action) {
	            case MotionEvent.ACTION_DOWN: {
	                name = "ACTION_DOWN";
	                break;
	            }
	            case MotionEvent.ACTION_MOVE: {
	                name = "ACTION_MOVE";
	                break;
	            }
	            case MotionEvent.ACTION_UP: {
	                name = "ACTION_UP";
	                break;
	            }
	            default:
	            break;
	        }
	        return name;
	    }

	    class MyOnGestureListener extends SimpleOnGestureListener {
	        @Override
	        public boolean onSingleTapUp(MotionEvent e) {
	            Log.i(getClass().getName(), "onSingleTapUp-----" + getActionName(e.getAction()));
	            Toast.makeText(mActivity, getClass().getName().toString()+"-----"+"onSingleTapUp-----" + getActionName(e.getAction()),0).show();
	            return false;
	        }

	        @Override
	        public void onLongPress(MotionEvent e) {
	            Log.i(getClass().getName(), "onLongPress-----" + getActionName(e.getAction()));
	            Toast.makeText(mActivity, getClass().getName().toString()+"-----"+"onLongPress-----" + getActionName(e.getAction()),0).show();
	        }

	        @Override
	        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
	            Log.i(getClass().getName(),
	                    "onScroll-----" + getActionName(e2.getAction()) + ",(" + e1.getX() + "," + e1.getY() + ") ,("
	                            + e2.getX() + "," + e2.getY() + ")");
	            Toast.makeText(mActivity, getClass().getName().toString()+"-----"+"onScroll-----" + getActionName(e2.getAction()) + ",(" + e1.getX() + "," + e1.getY() + ") ,("
                        + e2.getX() + "," + e2.getY() + ")",0).show();
	            return false;
	        }

	        @Override
	        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	            Log.i(getClass().getName(),
	                    "onFling-----" + getActionName(e2.getAction()) + ",(" + e1.getX() + "," + e1.getY() + ") ,("
	                            + e2.getX() + "," + e2.getY() + ")");
	            Toast.makeText(mActivity, getClass().getName().toString()+"-----"+"onFling-----" + getActionName(e2.getAction()) + ",(" + e1.getX() + "," + e1.getY() + ") ,("
                        + e2.getX() + "," + e2.getY() + ")",0).show();
	            return false;
	        }

	        @Override
	        public void onShowPress(MotionEvent e) {
	            Log.i(getClass().getName(), "onShowPress-----" + getActionName(e.getAction()));
	            Toast.makeText(mActivity, getClass().getName().toString()+"-----"+"onShowPress-----" + getActionName(e.getAction()),0).show();
	        }

	        @Override
	        public boolean onDown(MotionEvent e) {
	            Log.i(getClass().getName(), "onDown-----" + getActionName(e.getAction()));
	            Toast.makeText(mActivity, getClass().getName().toString()+"-----"+"onDown-----" + getActionName(e.getAction()),0).show();
	            return false;
	        }

	        @Override
	        public boolean onDoubleTap(MotionEvent e) {
	            Log.i(getClass().getName(), "onDoubleTap-----" + getActionName(e.getAction()));
	            Toast.makeText(mActivity, getClass().getName().toString()+"-----"+"onDoubleTap-----" + getActionName(e.getAction()),0).show();
	            return false;
	        }

	        @Override
	        public boolean onDoubleTapEvent(MotionEvent e) {
	            Log.i(getClass().getName(), "onDoubleTapEvent-----" + getActionName(e.getAction()));
	            Toast.makeText(mActivity, getClass().getName().toString()+"-----"+"onDoubleTapEvent-----" + getActionName(e.getAction()),0).show();
	            return false;
	        }

	        @Override
	        public boolean onSingleTapConfirmed(MotionEvent e) {
	            Log.i(getClass().getName(), "onSingleTapConfirmed-----" + getActionName(e.getAction()));
	            Toast.makeText(mActivity, getClass().getName().toString()+"-----"+"onSingleTapConfirmed-----" + getActionName(e.getAction()),0).show();
	            return false;
	        }
	    }*/
}