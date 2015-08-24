/*package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.view.OnSingleClickListener;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.fragment.DateSetFragment.RefreshBallStateTask;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.Schedule;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.GetScheduleResult;
import xiaoba.coach.net.result.SetDateTimeResult;
import xiaoba.coach.net.result.SetDefaultResult;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.utils.TimeUtil;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityDateSet extends BaseActivity {
	private Context context;
	LinearLayout mMorningSSet; // 早上设置栏
	LinearLayout mAfternoonSet; // 下午设置栏
	LinearLayout mNightSet; // 晚上设置栏
	LinearLayout mMorSelectAll; // 早上时间全选
	LinearLayout mAftSelectAll; // 下午时间全选
	LinearLayout mNigSelectAll; // 晚上时间全选
	ImageView mMorSelectAllImg;
	ImageView mAftSelectAllImg;
	ImageView mNigSelectAllImg;
//	TextView mMorSelectAllTv;
//	TextView mAftSelectAllTv;
//	TextView mNigSelectAllTv;
	View mMorPoint;
	TextView mMorTime;
	ImageView mMorArrow;
	View mAftPoint;
	TextView mAftTime;
	ImageView mAftArrow;
	View mNigPoint;
	TextView mNigTime;
	ImageView mNigArrow;
	LinearLayout mOnClassLayout;
	LinearLayout mRestLayout;
//	RelativeLayout mArrowUp;
	*//************* MORNING TIME *********//*
	private TextView mFiveTime;
	private TextView mSixTime;
	private TextView mSevenTime;
	private TextView mEightTime;
	private TextView mNineTime;
	private TextView mTenTime;
	private TextView mElevenTime;
	private TextView mFivePrice;
	private TextView mSixPrice;
	private TextView mSevenPrice;
	private TextView mEightPrice;
	private TextView mNinePrice;
	private TextView mTenPrice;
	private TextView mElevenPrice;
	private TextView mFiveSelect;
	private TextView mSixSelect;
	private TextView mSevenSelect;
	private TextView mEightSelect;
	private TextView mNineSelect;
	private TextView mTenSelect;
	private TextView mElevenSelect;
	*//************* AFTERNOON TIME *********//*
	private TextView mTwelveTime;
	private TextView mThirteenTime;
	private TextView mFourteenTime;
	private TextView mFifteenTime;
	private TextView mSixteenTime;
	private TextView mSeventeenTime;
	private TextView mEighteenTime;
	private TextView mTwelvePrice;
	private TextView mThirteenPrice;
	private TextView mFourteenPrice;
	private TextView mFifteenPrice;
	private TextView mSixteenPrice;
	private TextView mSeventeenPrice;
	private TextView mEighteenPrice;
	private TextView mTwelveSelect;
	private TextView mThirteenSelect;
	private TextView mFourteenSelect;
	private TextView mFifteenSelect;
	private TextView mSixteenSelect;
	private TextView mSeventeenSelect;
	private TextView mEighteenSelect;
	*//************* NIGHT TIME *****************//*
	private TextView mNineteenTime;
	private TextView mTwentyTime;
	private TextView mTwentyOneTime;
	private TextView mTwentyTwoTime;
	private TextView mTwentyThreeTime;
	private TextView mNineteenPrice;
	private TextView mTwentyPrice;
	private TextView mTwentyOnePrice;
	private TextView mTwentyTwoPrice;
	private TextView mTwentyThreePrice;
	private TextView mNineteenSelect;
	private TextView mTwentySelect;
	private TextView mTwentyOneSelect;
	private TextView mTwentyTwoSelect;
	private TextView mTwentyThreeSelect;
	// gears
	private RelativeLayout mFiveGear;
	private RelativeLayout mSixGear;
	private RelativeLayout mSevenGear;
	private RelativeLayout mEightGear;
	private RelativeLayout mNineGear;
	private RelativeLayout mTenGear;
	private RelativeLayout mElevenGear;
	private RelativeLayout mTwelveGear;
	private RelativeLayout mThirteenGear;
	private RelativeLayout mFourteenGear;
	private RelativeLayout mFifteenGear;
	private RelativeLayout mSixteenGear;
	private RelativeLayout mSeventeenGear;
	private RelativeLayout mEighteenGear;
	private RelativeLayout mNineteenGear;
	private RelativeLayout mTwentyGear;
	private RelativeLayout mTwentyOneGear;
	private RelativeLayout mTwentyTwoGear;
	private RelativeLayout mTwentyThreeGear;
	private Button btnMultiset;
	
//	private boolean finishLoadingData; // 是否取数据中

	
	 * 选中数组 false：选中 true：未选中
	 
	private boolean[] selectArray = new boolean[19];
	
	 * 状态数组 false:休息 true：开课
	 
	private boolean[] stateArray = new boolean[19];
	
	 * 预约数组 false:未预约 true:预约
	 
	private boolean[] bookArray = new boolean[19];
	private int Year,Month,Day;
	private Bundle bundle;
	private TextView tvCenter;
	private ImageView imgBack;
	private Calendar calSelecter;
	private Calendar calToday;
	private GetScheduleResult scheduleResult;
	Button mAllDaySetClose;
	HashMap<String, Schedule> hourMap = new HashMap<String, Schedule>();
	HashMap<String, HashMap> daySpan = new HashMap<String, HashMap>();
//	private Button mMorMultiSet;
//	private Button mAftMultiSet;
//	private Button mNigMultiSet;
	private String mon,date;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_date_set);
	        context = this;
	        bundle = getIntent().getExtras();
	        initView();
	        initData();
	        addListener();
	    }
	private void initData() {
		// TODO Auto-generated method stub
		calToday = Calendar.getInstance();
		setTimes();
		calSelecter = (Calendar) bundle.getSerializable("calendar");
		Year = calSelecter.get(Calendar.YEAR);
		Month = calSelecter.get(Calendar.MONTH)+1;
		if (Month<10)
		{
			mon = "0"+String.valueOf(Month);
		}else{
			mon = String.valueOf(Month);
		}
		
		Day = calSelecter.get(Calendar.DAY_OF_MONTH);
		if (Day<10)
		{
			date = "0"+String.valueOf(Day);
		}else{
			date = String.valueOf(Day);
		}
		tvCenter.setText(Year+"年"+Month+"月"+Day+"日");
		scheduleResult = (GetScheduleResult) bundle.getSerializable("dataresult");
		initDataResult();
	}
	
	private void setTimes() {
		mFiveTime.setText("05:00");
		mSixTime.setText("06:00");
		mSevenTime.setText("07:00");
		mEightTime.setText("08:00");
		mNineTime.setText("09:00");
		mTenTime.setText("10:00");
		mElevenTime.setText("11:00");
		mTwelveTime.setText("12:00");
		mThirteenTime.setText("13:00");
		mFourteenTime.setText("14:00");
		mFifteenTime.setText("15:00");
		mSixteenTime.setText("16:00");
		mSeventeenTime.setText("17:00");
		mEighteenTime.setText("18:00");
		mNineteenTime.setText("19:00");
		mTwentyTime.setText("20:00");
		mTwentyOneTime.setText("21:00");
		mTwentyTwoTime.setText("22:00");
		mTwentyThreeTime.setText("23:00");
		// default rest time 12:00 & 18:00
		stateArray[7] = true;
		stateArray[13] = true;
		mTwelveTime.setTextColor(Color.parseColor("#d2d2d2"));
		mTwelvePrice.setTextColor(Color.parseColor("#d2d2d2"));
		mTwelvePrice.setText("休息");
		mTwelvePrice.setBackgroundColor(Color.parseColor("#f3f3f3"));
		mEighteenTime.setTextColor(Color.parseColor("#d2d2d2"));
		mEighteenPrice.setTextColor(Color.parseColor("#d2d2d2"));
		mEighteenPrice.setBackgroundColor(Color.parseColor("#f3f3f3"));
		mEighteenPrice.setText("休息");
	}
	
	private void initDataResult()
	{
		reformData();
		mDeadHour = scheduleResult.getHour();
		checkUpdate(calSelecter);
	}
	
	void reformData() {
		if (scheduleResult != null && scheduleResult.getDatelist() != null) {
			for (Schedule sc : scheduleResult.getDatelist()) {
				if (!daySpan.containsKey(sc.getDate())) {
					
					 * 如果当前hashmap没有保存这个日期的数据
					 
					HashMap<String, Schedule> temp = getNewHourMap();
					temp.put(sc.getHour() + "", sc);
					daySpan.put(sc.getDate(), temp);
				} else {
					daySpan.get(sc.getDate()).put(sc.getHour() + "", sc);
				}

			}
		}
	}
	
	HashMap<String, Schedule> getNewHourMap() {
		hourMap = new HashMap<String, Schedule>();
		for (int i = 0; i <= 18; i++) {
			Schedule sc = new Schedule();
			hourMap.put(5 + i + "", sc);
		}
		return hourMap;
	}
	

	boolean isAllDayOpen;
	Dialog setDefaultDialog;
	boolean defaultSel = false;
	ImageView select;

	protected void showSetDefaultDialog() {
		defaultSel = false;

		AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDateSet.this);
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
					
					 * 全局设置
					 
					if (schedule.getState() == 0) {
						// rest
						mAllDaySetClose.setText("发布课程");
						isAllDayOpen = false;
					} else if (schedule.getState() == 1) {
						mAllDaySetClose.setText("当天停课");
						isAllDayOpen = true;
					}

				} else {
					
					 * 单个小时设置
					 
					switch (schedule.getHour()) {
					case 5:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mFiveTime, mFivePrice, mFiveSelect, 0);
						break;
					case 6:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mSixTime, mSixPrice, mSixSelect, 1);
						break;
					case 7:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mSevenTime, mSevenPrice, mSevenSelect, 2);
						break;
					case 8:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mEightTime, mEightPrice, mEightSelect, 3);
						break;
					case 9:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mNineTime, mNinePrice, mNineSelect, 4);
						break;
					case 10:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mTenTime, mTenPrice, mTenSelect, 5);
						break;
					case 11:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mElevenTime, mElevenPrice, mElevenSelect, 6);
						break;
					case 12:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mTwelveTime, mTwelvePrice, mTwelveSelect, 7);
						break;
					case 13:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mThirteenTime, mThirteenPrice, mThirteenSelect, 8);
						break;
					case 14:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mFourteenTime, mFourteenPrice, mFourteenSelect, 9);
						break;
					case 15:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mFifteenTime, mFifteenPrice, mFifteenSelect, 10);
						break;
					case 16:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mSixteenTime, mSixteenPrice, mSixteenSelect, 11);
						break;
					case 17:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mSeventeenTime, mSeventeenPrice, mSeventeenSelect, 12);
						break;
					case 18:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mEighteenTime, mEighteenPrice, mEighteenSelect, 13);
						break;
					case 19:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mNineteenTime, mNineteenPrice, mNineteenSelect, 14);
						break;
					case 20:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mTwentyTime, mTwentyPrice, mTwentySelect, 15);
						break;
					case 21:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mTwentyOneTime, mTwentyOnePrice, mTwentyOneSelect, 16);
						break;
					case 22:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mTwentyTwoTime, mTwentyTwoPrice, mTwentyTwoSelect, 17);
						break;
					case 23:
						modifyOneHourState(schedule.getIsrest(), schedule.getPrice(), mTwentyThreeTime, mTwentyThreePrice, mTwentyThreeSelect, 18);
						break;
					}
				}
			}
		}

//		checkDayTime();
//		checkSelect();
	}
	
	Schedule schd;
	int mDeadHour;

	private void modifyOneHourState(int isrest, String pricenum, TextView time, TextView price, TextView select, int pos) {
		if (isrest == 1) {
			time.setTextColor(Color.parseColor("#d2d2d2"));
			price.setTextColor(Color.parseColor("#d2d2d2"));
			price.setText("休息");
			price.setBackgroundColor(Color.parseColor("#f3f3f3"));
			stateArray[pos] = false;
		} else {
			time.setTextColor(Color.parseColor("#20b478"));
			price.setTextColor(Color.parseColor("#ffffff"));
			price.setBackgroundColor(Color.parseColor("#20b478"));
			if (pricenum != null)
				price.setText("单价  " + (pricenum.contains(".") ? pricenum.substring(0, pricenum.indexOf(".")) : pricenum) + "元");
			else {
				price.setText("");
			}
			stateArray[pos] = true;
		}

		if (equalsDate(calToday.getTime(), calSelecter.getTime()) && (pos + 5) <= mDeadHour) {
			select.setVisibility(View.INVISIBLE);
			selectArray[pos] = true;
		} else {
			if (select.getVisibility() == View.INVISIBLE) {
				select.setVisibility(View.VISIBLE);
				select.setBackgroundResource(R.color.text_black);
			}
			selectArray[pos] = false;
		}

		if (daySpan.get(TimeUtil.calendarToString(calSelecter)) != null)
			schd = (Schedule) daySpan.get(TimeUtil.calendarToString(calSelecter)).get(5 + pos + "");
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
			
			 * select.setTextColor(getResources().getColor(R.color.white)); select.setBackgroundColor(Color.parseColor("#000000"));
			 
		}
	}
	
	private Boolean equalsDate(Date date1, Date date2) {

		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate()) {
			return true;
		} else {
			return false;
		}

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
		
		mMorSelectAll.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (mMorSelectAllImg.isSelected()) {
					mMorSelectAllImg.setSelected(false);
//					mMorSelectAllTv.setText("全选");
					if (equalsDate(calToday.getTime(), calSelecter.getTime())) {
						
						 * 今天
						 
						if (mDeadHour < 5 && !bookArray[0]) {
							mFiveSelect.setVisibility(View.INVISIBLE);
							selectArray[0] = true;
						}
						if (mDeadHour < 6 && !bookArray[1]) {
							mSixSelect.setVisibility(View.INVISIBLE);
							selectArray[1] = true;
						}
						if (mDeadHour < 7 && !bookArray[2]) {
							mSevenSelect.setVisibility(View.INVISIBLE);
							selectArray[2] = true;
						}
						if (mDeadHour < 8 && !bookArray[3]) {
							mEightSelect.setVisibility(View.INVISIBLE);
							selectArray[3] = true;
						}
						if (mDeadHour < 9 && !bookArray[4]) {
							mNineSelect.setVisibility(View.INVISIBLE);
							selectArray[4] = true;
						}
						if (mDeadHour < 10 && !bookArray[5]) {
							mTenSelect.setVisibility(View.INVISIBLE);
							selectArray[5] = true;
						}
						if (mDeadHour < 11 && !bookArray[6]) {
							mElevenSelect.setVisibility(View.INVISIBLE);
							selectArray[6] = true;
						}
					} else {
						
						 * 明天过后
						 
						if (!bookArray[0]) {
							mFiveSelect.setVisibility(View.INVISIBLE);
							selectArray[0] = true;
						}
						if (!bookArray[1]) {
							mSixSelect.setVisibility(View.INVISIBLE);
							selectArray[1] = true;
						}
						if (!bookArray[2]) {
							mSevenSelect.setVisibility(View.INVISIBLE);
							selectArray[2] = true;
						}
						if (!bookArray[3]) {
							mEightSelect.setVisibility(View.INVISIBLE);
							selectArray[3] = true;
						}
						if (!bookArray[4]) {
							mNineSelect.setVisibility(View.INVISIBLE);
							selectArray[4] = true;
						}
						if (!bookArray[5]) {
							mTenSelect.setVisibility(View.INVISIBLE);
							selectArray[5] = true;
						}
						if (!bookArray[6]) {
							mElevenSelect.setVisibility(View.INVISIBLE);
							selectArray[6] = true;
						}
					}
				} else {
					if (equalsDate(calToday.getTime(), calSelecter.getTime())) {
						
						 * 今天
						 
						if (mDeadHour < 5 && !bookArray[0]) {
							mFiveSelect.setVisibility(View.VISIBLE);
							selectArray[0] = false;
						}
						if (mDeadHour < 6 && !bookArray[1]) {
							mSixSelect.setVisibility(View.VISIBLE);
							selectArray[1] = false;
						}
						if (mDeadHour < 7 && !bookArray[2]) {
							mSevenSelect.setVisibility(View.VISIBLE);
							selectArray[2] = false;
						}
						if (mDeadHour < 8 && !bookArray[3]) {
							mEightSelect.setVisibility(View.VISIBLE);
							selectArray[3] = false;
						}
						if (mDeadHour < 9 && !bookArray[4]) {
							mNineSelect.setVisibility(View.VISIBLE);
							selectArray[4] = false;
						}
						if (mDeadHour < 10 && !bookArray[5]) {
							mTenSelect.setVisibility(View.VISIBLE);
							selectArray[5] = false;
						}
						if (mDeadHour < 11 && !bookArray[6]) {
							mElevenSelect.setVisibility(View.VISIBLE);
							selectArray[6] = false;
						}
					} else {
						if (!bookArray[0]) {
							mFiveSelect.setVisibility(View.VISIBLE);
							selectArray[0] = false;
						}
						if (!bookArray[1]) {
							mSixSelect.setVisibility(View.VISIBLE);
							selectArray[1] = false;
						}
						if (!bookArray[2]) {
							mSevenSelect.setVisibility(View.VISIBLE);
							selectArray[2] = false;
						}
						if (!bookArray[3]) {
							mEightSelect.setVisibility(View.VISIBLE);
							selectArray[3] = false;
						}
						if (!bookArray[4]) {
							mNineSelect.setVisibility(View.VISIBLE);
							selectArray[4] = false;
						}
						if (!bookArray[5]) {
							mTenSelect.setVisibility(View.VISIBLE);
							selectArray[5] = false;
						}
						if (!bookArray[6]) {
							mElevenSelect.setVisibility(View.VISIBLE);
							selectArray[6] = false;
						}
					}

					boolean hasSelect = false;
					for (int i = 0; i < 7; i++) {
						if (!selectArray[i]) {
							hasSelect = true;
							break;
						}
					}
					if (hasSelect) {
						mMorSelectAllImg.setSelected(true);
//						mMorSelectAllTv.setText("取消全选");
					} else {
						Toast.makeText(ActivityDateSet.this, "当前时间段不可用", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		mAftSelectAll.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (mAftSelectAllImg.isSelected()) {
					mAftSelectAllImg.setSelected(false);
//					mAftSelectAllTv.setText("全选");

					if (equalsDate(calToday.getTime(), calSelecter.getTime())) {
						
						 * 今天
						 

						if (mDeadHour < 12 && !bookArray[7]) {
							mTwelveSelect.setVisibility(View.INVISIBLE);
							selectArray[7] = true;
						}
						if (mDeadHour < 13 && !bookArray[8]) {
							mThirteenSelect.setVisibility(View.INVISIBLE);
							selectArray[8] = true;
						}
						if (mDeadHour < 14 && !bookArray[9]) {
							mFourteenSelect.setVisibility(View.INVISIBLE);
							selectArray[9] = true;
						}
						if (mDeadHour < 15 && !bookArray[10]) {
							mFifteenSelect.setVisibility(View.INVISIBLE);
							selectArray[10] = true;
						}
						if (mDeadHour < 16 && !bookArray[11]) {
							mSixteenSelect.setVisibility(View.INVISIBLE);
							selectArray[11] = true;
						}
						if (mDeadHour < 17 && !bookArray[12]) {
							mSeventeenSelect.setVisibility(View.INVISIBLE);
							selectArray[12] = true;
						}
						if (mDeadHour < 18 && !bookArray[13]) {
							mEighteenSelect.setVisibility(View.INVISIBLE);
							selectArray[13] = true;
						}
					} else {
						if (!bookArray[7]) {
							mTwelveSelect.setVisibility(View.INVISIBLE);
							selectArray[7] = true;
						}
						if (!bookArray[8]) {
							mThirteenSelect.setVisibility(View.INVISIBLE);
							selectArray[8] = true;
						}
						if (!bookArray[9]) {
							mFourteenSelect.setVisibility(View.INVISIBLE);
							selectArray[9] = true;
						}
						if (!bookArray[10]) {
							mFifteenSelect.setVisibility(View.INVISIBLE);
							selectArray[10] = true;
						}
						if (!bookArray[11]) {
							mSixteenSelect.setVisibility(View.INVISIBLE);
							selectArray[11] = true;
						}
						if (!bookArray[12]) {
							mSeventeenSelect.setVisibility(View.INVISIBLE);
							selectArray[12] = true;
						}
						if (!bookArray[13]) {
							mEighteenSelect.setVisibility(View.INVISIBLE);
							selectArray[13] = true;
						}
					}
				} else {
					if (equalsDate(calToday.getTime(), calSelecter.getTime())) {
						
						 * 今天
						 

						if (mDeadHour < 12 && !bookArray[7]) {
							mTwelveSelect.setVisibility(View.VISIBLE);
							selectArray[7] = false;
						}
						if (mDeadHour < 13 && !bookArray[8]) {
							mThirteenSelect.setVisibility(View.VISIBLE);
							selectArray[8] = false;
						}
						if (mDeadHour < 14 && !bookArray[9]) {
							mFourteenSelect.setVisibility(View.VISIBLE);
							selectArray[9] = false;
						}
						if (mDeadHour < 15 && !bookArray[10]) {
							mFifteenSelect.setVisibility(View.VISIBLE);
							selectArray[10] = false;
						}
						if (mDeadHour < 16 && !bookArray[11]) {
							mSixteenSelect.setVisibility(View.VISIBLE);
							selectArray[11] = false;
						}
						if (mDeadHour < 17 && !bookArray[12]) {
							mSeventeenSelect.setVisibility(View.VISIBLE);
							selectArray[12] = false;
						}
						if (mDeadHour < 18 && !bookArray[13]) {
							mEighteenSelect.setVisibility(View.VISIBLE);
							selectArray[13] = false;
						}
					} else {
						if (!bookArray[7]) {
							mTwelveSelect.setVisibility(View.VISIBLE);
							selectArray[7] = false;
						}
						if (!bookArray[8]) {
							mThirteenSelect.setVisibility(View.VISIBLE);
							selectArray[8] = false;
						}
						if (!bookArray[9]) {
							mFourteenSelect.setVisibility(View.VISIBLE);
							selectArray[9] = false;
						}
						if (!bookArray[10]) {
							mFifteenSelect.setVisibility(View.VISIBLE);
							selectArray[10] = false;
						}
						if (!bookArray[11]) {
							mSixteenSelect.setVisibility(View.VISIBLE);
							selectArray[11] = false;
						}
						if (!bookArray[12]) {
							mSeventeenSelect.setVisibility(View.VISIBLE);
							selectArray[12] = false;
						}
						if (!bookArray[13]) {
							mEighteenSelect.setVisibility(View.VISIBLE);
							selectArray[13] = false;
						}
					}

					boolean hasSelect = false;
					for (int i = 7; i < 14; i++) {
						if (!selectArray[i]) {
							hasSelect = true;
							break;
						}
					}
					if (hasSelect) {
						mAftSelectAllImg.setSelected(true);
//						mAftSelectAllTv.setText("取消全选");
					} else {
						Toast.makeText(ActivityDateSet.this, "当前时间段不可用", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		mNigSelectAll.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (mNigSelectAllImg.isSelected()) {
					mNigSelectAllImg.setSelected(false);
//					mNigSelectAllTv.setText("全选");
//
					if (equalsDate(calToday.getTime(), calSelecter.getTime())) {
						if (mDeadHour < 19 && !bookArray[14]) {
							mNineteenSelect.setVisibility(View.INVISIBLE);
							selectArray[14] = true;
						}
						if (mDeadHour < 20 && !bookArray[15]) {
							mTwentySelect.setVisibility(View.INVISIBLE);
							selectArray[15] = true;
						}
						if (mDeadHour < 21 && !bookArray[16]) {
							mTwentyOneSelect.setVisibility(View.INVISIBLE);
							selectArray[16] = true;
						}
						if (mDeadHour < 22 && !bookArray[17]) {
							mTwentyTwoSelect.setVisibility(View.INVISIBLE);
							selectArray[17] = true;
						}
						if (mDeadHour < 23 && !bookArray[18]) {
							mTwentyThreeSelect.setVisibility(View.INVISIBLE);
							selectArray[18] = true;
						}
					} else {
						if (!bookArray[14]) {
							mNineteenSelect.setVisibility(View.INVISIBLE);
							selectArray[14] = true;
						}
						if (!bookArray[15]) {
							mTwentySelect.setVisibility(View.INVISIBLE);
							selectArray[15] = true;
						}
						if (!bookArray[16]) {
							mTwentyOneSelect.setVisibility(View.INVISIBLE);
							selectArray[16] = true;
						}
						if (!bookArray[17]) {
							mTwentyTwoSelect.setVisibility(View.INVISIBLE);
							selectArray[17] = true;
						}
						if (!bookArray[18]) {
							mTwentyThreeSelect.setVisibility(View.INVISIBLE);
							selectArray[18] = true;
						}
					}
				} else {
					if (equalsDate(calToday.getTime(), calSelecter.getTime())) {
						if (mDeadHour < 19 && !bookArray[14]) {
							mNineteenSelect.setVisibility(View.VISIBLE);
							selectArray[14] = false;
						}
						if (mDeadHour < 20 && !bookArray[15]) {
							mTwentySelect.setVisibility(View.VISIBLE);
							selectArray[15] = false;
						}
						if (mDeadHour < 21 && !bookArray[16]) {
							mTwentyOneSelect.setVisibility(View.VISIBLE);
							selectArray[16] = false;
						}
						if (mDeadHour < 22 && !bookArray[17]) {
							mTwentyTwoSelect.setVisibility(View.VISIBLE);
							selectArray[17] = false;
						}
						if (mDeadHour < 23 && !bookArray[18]) {
							mTwentyThreeSelect.setVisibility(View.VISIBLE);
							selectArray[18] = false;
						}
					} else {
						if (!bookArray[14]) {
							mNineteenSelect.setVisibility(View.VISIBLE);
							selectArray[14] = false;
						}
						if (!bookArray[15]) {
							mTwentySelect.setVisibility(View.VISIBLE);
							selectArray[15] = false;
						}
						if (!bookArray[16]) {
							mTwentyOneSelect.setVisibility(View.VISIBLE);
							selectArray[16] = false;
						}
						if (!bookArray[17]) {
							mTwentyTwoSelect.setVisibility(View.VISIBLE);
							selectArray[17] = false;
						}
						if (!bookArray[18]) {
							mTwentyThreeSelect.setVisibility(View.VISIBLE);
							selectArray[18] = false;
						}
					}

					boolean hasSelect = false;
					for (int i = 14; i < 19; i++) {
						if (!selectArray[i]) {
							hasSelect = true;
							break;
						}
					}
					if (hasSelect) {
						mNigSelectAllImg.setSelected(true);
//						mNigSelectAllTv.setText("取消全选");
					} else {
						Toast.makeText(ActivityDateSet.this, "当前时间段不可用", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		
		 * stop class
		 
		mAllDaySetClose.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (isAllDayOpen) {
					new ChangeAllDayScheduleTask("2").execute();
				} else {
					showSetDefaultDialog();
				}
			}
		});

		mMorningSSet.findViewById(R.id.five_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 5 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[0]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[0]) {
						mFiveSelect.setVisibility(View.INVISIBLE);
						selectArray[0] = true;
						fixMorAllSelect();
					} else {
						mFiveSelect.setVisibility(View.VISIBLE);
						selectArray[0] = false;
					}
				}
			}
		});
		mMorningSSet.findViewById(R.id.six_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 6 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[1]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[1]) {
						mSixSelect.setVisibility(View.INVISIBLE);
						selectArray[1] = true;
						fixMorAllSelect();
					} else {
						mSixSelect.setVisibility(View.VISIBLE);
						selectArray[1] = false;
					}
				}
			}
		});
		mMorningSSet.findViewById(R.id.seven_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 7 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[2]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[2]) {
						mSevenSelect.setVisibility(View.INVISIBLE);
						selectArray[2] = true;
						fixMorAllSelect();
					} else {
						mSevenSelect.setVisibility(View.VISIBLE);
						selectArray[2] = false;
					}
				}
			}
		});
		mMorningSSet.findViewById(R.id.eight_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 8 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[3]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[3]) {
						mEightSelect.setVisibility(View.INVISIBLE);
						selectArray[3] = true;
						fixMorAllSelect();
					} else {
						mEightSelect.setVisibility(View.VISIBLE);
						selectArray[3] = false;
					}
				}
			}
		});
		mMorningSSet.findViewById(R.id.nine_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 9 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[4]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[4]) {
						mNineSelect.setVisibility(View.INVISIBLE);
						selectArray[4] = true;
						fixMorAllSelect();
					} else {
						mNineSelect.setVisibility(View.VISIBLE);
						selectArray[4] = false;
					}
				}
			}
		});
		mMorningSSet.findViewById(R.id.ten_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 10 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[5]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[5]) {
						mTenSelect.setVisibility(View.INVISIBLE);
						selectArray[5] = true;
						fixMorAllSelect();
					} else {
						mTenSelect.setVisibility(View.VISIBLE);
						selectArray[5] = false;
					}
				}
			}
		});

		mMorningSSet.findViewById(R.id.eleven_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 11 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[6]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[6]) {
						mElevenSelect.setVisibility(View.INVISIBLE);
						selectArray[6] = true;
					} else {
						mElevenSelect.setVisibility(View.VISIBLE);
						selectArray[6] = false;
					}
				}
			}
		});

		mAfternoonSet.findViewById(R.id.twelve_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 12 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[7]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[7]) {
						mTwelveSelect.setVisibility(View.INVISIBLE);
						selectArray[7] = true;
						fixAftAllSelect();
					} else {
						mTwelveSelect.setVisibility(View.VISIBLE);
						selectArray[7] = false;
					}
				}
			}
		});
		mAfternoonSet.findViewById(R.id.thirteen_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 13 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[8]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[8]) {
						mThirteenSelect.setVisibility(View.INVISIBLE);
						selectArray[8] = true;
						fixAftAllSelect();
					} else {
						mThirteenSelect.setVisibility(View.VISIBLE);
						selectArray[8] = false;
					}
				}
			}
		});
		mAfternoonSet.findViewById(R.id.fourteen_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 14 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[9]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[9]) {
						mFourteenSelect.setVisibility(View.INVISIBLE);
						selectArray[9] = true;
						fixAftAllSelect();
					} else {
						mFourteenSelect.setVisibility(View.VISIBLE);
						selectArray[9] = false;
					}
				}
			}
		});
		mAfternoonSet.findViewById(R.id.fifteen_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 15 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[10]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[10]) {
						mFifteenSelect.setVisibility(View.INVISIBLE);
						selectArray[10] = true;
						fixAftAllSelect();
					} else {
						mFifteenSelect.setVisibility(View.VISIBLE);
						selectArray[10] = false;
					}
				}
			}
		});
		mAfternoonSet.findViewById(R.id.sixteen_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 16 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[11]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[11]) {
						mSixteenSelect.setVisibility(View.INVISIBLE);
						selectArray[11] = true;
						fixAftAllSelect();
					} else {
						mSixteenSelect.setVisibility(View.VISIBLE);
						selectArray[11] = false;
					}
				}
			}
		});
		mAfternoonSet.findViewById(R.id.seventeen_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 17 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[12]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[12]) {
						mSeventeenSelect.setVisibility(View.INVISIBLE);
						selectArray[12] = true;
						fixAftAllSelect();
					} else {
						mSeventeenSelect.setVisibility(View.VISIBLE);
						selectArray[12] = false;
					}
				}
			}
		});

		mAfternoonSet.findViewById(R.id.eighteen_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 18 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[13]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[13]) {
						mEighteenSelect.setVisibility(View.INVISIBLE);
						selectArray[13] = true;
						fixAftAllSelect();
					} else {
						mEighteenSelect.setVisibility(View.VISIBLE);
						selectArray[13] = false;
					}
				}
			}
		});

		mNightSet.findViewById(R.id.nineteen_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 19 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[14]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[14]) {
						mNineteenSelect.setVisibility(View.INVISIBLE);
						selectArray[14] = true;
						fixNigAllSelect();
					} else {
						mNineteenSelect.setVisibility(View.VISIBLE);
						selectArray[14] = false;
					}
				}
			}
		});
		mNightSet.findViewById(R.id.twenty_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 20 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[15]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[15]) {
						mTwentySelect.setVisibility(View.INVISIBLE);
						selectArray[15] = true;
						fixNigAllSelect();
					} else {
						mTwentySelect.setVisibility(View.VISIBLE);
						selectArray[15] = false;
					}
				}

			}
		});
		mNightSet.findViewById(R.id.twentyone_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 21 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[16]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[16]) {
						mTwentyOneSelect.setVisibility(View.INVISIBLE);
						selectArray[16] = true;
						fixNigAllSelect();
					} else {
						mTwentyOneSelect.setVisibility(View.VISIBLE);
						selectArray[16] = false;
					}
				}
			}
		});
		mNightSet.findViewById(R.id.twentytwo_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 22 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[17]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[17]) {
						mTwentyTwoSelect.setVisibility(View.INVISIBLE);
						selectArray[17] = true;
						fixNigAllSelect();
					} else {
						mTwentyTwoSelect.setVisibility(View.VISIBLE);
						selectArray[17] = false;
					}
				}
			}
		});
		mNightSet.findViewById(R.id.twentythree_part).setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 23 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能选择", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[18]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					if (!selectArray[18]) {
						mTwentyThreeSelect.setVisibility(View.INVISIBLE);
						selectArray[18] = true;
						fixNigAllSelect();
					} else {
						mTwentyThreeSelect.setVisibility(View.VISIBLE);
						selectArray[18] = false;
					}
				}
			}
		});

		mFiveGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 5 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[0]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 5).putExtra("rest", stateArray[0]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
//					showSetClass.show();
				}
			}
		});

		mSixGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 6 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[1]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 6).putExtra("rest", stateArray[1]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});

		mSevenGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 7 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[2]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 7).putExtra("rest", stateArray[2]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});

		mEightGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 8 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[3]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 8).putExtra("rest", stateArray[3]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});
		mNineGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 9 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[4]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 9).putExtra("rest", stateArray[4]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});
		mTenGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 10 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[5]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 10).putExtra("rest", stateArray[5]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});
		mElevenGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 11 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[6]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 11).putExtra("rest", stateArray[6]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});
		mTwelveGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 12 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[7]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 12).putExtra("rest", stateArray[7]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});
		mThirteenGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 13 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[8]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 13).putExtra("rest", stateArray[8]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});
		mFourteenGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 14 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[9]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 14).putExtra("rest", stateArray[9]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});
		mFifteenGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 15 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[10]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 15).putExtra("rest", stateArray[10]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});
		mSixteenGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 16 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[11]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 16).putExtra("rest", stateArray[11]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});
		mSeventeenGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 17 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[12]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 17).putExtra("rest", stateArray[12]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});
		mEighteenGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 18 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[13]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 18).putExtra("rest", stateArray[13]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});
		mNineteenGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 19 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[14]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 19).putExtra("rest", stateArray[14]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});
		mTwentyGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 20 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[15]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 20).putExtra("rest", stateArray[15]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});

		mTwentyOneGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 21 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[16]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 21).putExtra("rest", stateArray[16]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});

		mTwentyTwoGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 22 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[17]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 22).putExtra("rest", stateArray[17]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});

		mTwentyThreeGear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
//				if (!finishLoadingData) {
//					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if (equalsDate(calToday.getTime(), calSelecter.getTime()) && 23 <= mDeadHour) {
					Toast.makeText(ActivityDateSet.this, "当前时间不能设置", Toast.LENGTH_SHORT).show();
				} else {
					if (bookArray[18]) {
						Toast.makeText(ActivityDateSet.this, "当前时段已约，不能修改", Toast.LENGTH_SHORT).show();
						return;
					}
					ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("single", 23).putExtra("rest", stateArray[18]).putExtra("result", scheduleResult)
							.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
				}
			}
		});
		
		btnMultiset.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				boolean isRest = false;
				boolean hasSelect = false;
				for (int j = 0; j < 19; j++) {
					if (!selectArray[j]) {
						hasSelect = true;
						break;
					}
				}
				if (!hasSelect) {
					Toast.makeText(ActivityDateSet.this, "请选择时间", Toast.LENGTH_SHORT).show();
					return;
				}
				StringBuilder mor = new StringBuilder();
				for (int i = 0; i <= 18; i++) {
					if (!selectArray[i]) {
						if (equalsDate(calToday.getTime(), calSelecter.getTime()) && (i + 5) <= mDeadHour) {
							mor.append("0");
						} else {
							if (bookArray[i])
								mor.append("0");
							else {
								mor.append("1");
								if (!stateArray[i])
									isRest = true;
							}
						}
						if (!stateArray[i])
							isRest = true;
					} else {
						mor.append("0");
					}
				}

				if (!mor.toString().contains("1")) {
					Toast.makeText(ActivityDateSet.this, "当前时间段无法设置", Toast.LENGTH_SHORT).show();
					return;
				}
				ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("mor", mor.toString()).putExtra("rest", !isRest).putExtra("result", scheduleResult)
						.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
			}
		});

//		mMorMultiSet.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				
////				if (!finishLoadingData) {
////					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
////					return;
////				}
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
//					Toast.makeText(ActivityDateSet.this, "请选择时间", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				StringBuilder mor = new StringBuilder();
//				for (int i = 0; i <= 6; i++) {
//					if (!selectArray[i]) {
//						if (equalsDate(calToday.getTime(), calSelecter.getTime()) && (i + 5) <= mDeadHour) {
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
//					Toast.makeText(ActivityDateSet.this, "当前时间段无法设置", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("mor", mor.toString()).putExtra("rest", !isRest).putExtra("result", scheduleResult)
//						.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
//			}
//		});
//
//		mAftMultiSet.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				
////				if (!finishLoadingData) {
////					Toast.makeText(ActivityDateSet.this, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
////					return;
////				}
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
//					Toast.makeText(ActivityDateSet.this, "请选择时间", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				StringBuilder mor = new StringBuilder();
//				for (int i = 7; i <= 13; i++) {
//					if (!selectArray[i]) {
//						if (equalsDate(calToday.getTime(), calSelecter.getTime()) && (i + 5) <= mDeadHour) {
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
//					Toast.makeText(ActivityDateSet.this, "当前时间段无法设置", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("aft", mor.toString()).putExtra("rest", !isRest).putExtra("result", scheduleResult)
//						.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
//			}
//		});
//
//		mNigMultiSet.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				
////				if (!finishLoadingData) {
////					Toast.makeText(mActivity, "加载数据中，请稍后再试", Toast.LENGTH_SHORT).show();
////					return;
////				}
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
//					Toast.makeText(ActivityDateSet.this, "请选择时间", Toast.LENGTH_SHORT).show();
//					return;
//				}
//				StringBuilder mor = new StringBuilder();
//				for (int i = 14; i <= 18; i++) {
//					if (!selectArray[i]) {
//						if (equalsDate(calToday.getTime(), calSelecter.getTime()) && (i + 5) <= mDeadHour) {
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
//					Toast.makeText(ActivityDateSet.this, "当前时间段无法设置", Toast.LENGTH_SHORT).show();
//					return;
//				}
//
//				ActivityDateSet.this.startActivityForResult(new Intent(ActivityDateSet.this, ClassTimeSetActivity_.class).putExtra("nig", mor.toString()).putExtra("rest", !isRest).putExtra("result", scheduleResult)
//						.putExtra("day", TimeUtil.calendarToString(calSelecter)), 2001);
//			}
//		});
	}
	private void initView() {
		// TODO Auto-generated method stub
//		mMorningBar = (LinearLayout)findViewById(R.id.mor_show_part);
//		mAfternoonBar = (LinearLayout)findViewById(R.id.afternoon_show_part);
//		mNightBar = (LinearLayout)findViewById(R.id.night_show_part);

		mMorningSSet = (LinearLayout)findViewById(R.id.mor_hide_part);
		mAfternoonSet = (LinearLayout)findViewById(R.id.afternoon_hide_part);
		mNightSet = (LinearLayout)findViewById(R.id.night_hide_part);
//		mMorMultiSet = (Button) mMorningSSet.findViewById(R.id.multy_set);
//		mAftMultiSet = (Button) mAfternoonSet.findViewById(R.id.multy_set);
//		mNigMultiSet = (Button) mNightSet.findViewById(R.id.multy_set);
		btnMultiset = (Button)findViewById(R.id.btn_multiset);
		mMorSelectAll = (LinearLayout)findViewById(R.id.ll_mor_select);
		mAftSelectAll = (LinearLayout)findViewById(R.id.ll_aft_select);
		mNigSelectAll = (LinearLayout)findViewById(R.id.ll_nig_select);
		mMorSelectAllImg = (ImageView)findViewById(R.id.mor_select);
		mAftSelectAllImg = (ImageView)findViewById(R.id.aft_select);
		mNigSelectAllImg = (ImageView)findViewById(R.id.nig_select);
		mNigSelectAllImg.setSelected(true);
		mAftSelectAllImg.setSelected(true);
		mMorSelectAllImg.setSelected(true);
//		mMorSelectAllTv = (TextView) mMorningSSet.findViewById(R.id.all_select_tv);
//		mAftSelectAllTv = (TextView) mAfternoonSet.findViewById(R.id.all_select_tv);
//		mNigSelectAllTv = (TextView) mNightSet.findViewById(R.id.all_select_tv);
//		mMorSelectAllTv.setText("取消全选");
//		mAftSelectAllTv.setText("取消全选");
//		mNigSelectAllTv.setText("取消全选");
		mMorPoint = (View)findViewById(R.id.mor_point);
		mMorTime = (TextView)findViewById(R.id.mor_time);
		mMorArrow = (ImageView)findViewById(R.id.mor_arrow);
		mAftPoint = (View)findViewById(R.id.aft_point);
		mAftTime = (TextView)findViewById(R.id.aft_time);
		mAftArrow = (ImageView)findViewById(R.id.aft_arrow);
		mNigPoint = (View)findViewById(R.id.nig_point);
		mNigTime = (TextView)findViewById(R.id.nig_time);
		mNigArrow = (ImageView)findViewById(R.id.nig_arrow);
//		mMorMultiSet = (Button) mMorningSSet.findViewById(R.id.multy_set);
//		mAftMultiSet = (Button) mAfternoonSet.findViewById(R.id.multy_set);
//		mNigMultiSet = (Button) mNightSet.findViewById(R.id.multy_set);
		mAllDaySetClose = (Button)findViewById(R.id.close_class);
		mOnClassLayout = (LinearLayout)findViewById(R.id.onclass_layout);
		mRestLayout = (LinearLayout)findViewById(R.id.rest_layout);
		// morning time
		mFiveTime = (TextView) mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.tg_time);
		mFivePrice = (TextView) mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.tg_price);
		mFiveSelect = (TextView) mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.tg_select);
		mSixTime = (TextView) mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.tg_time);
		mSixPrice = (TextView) mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.tg_price);
		mSixSelect = (TextView) mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.tg_select);
		mSevenTime = (TextView) mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.tg_time);
		mSevenPrice = (TextView) mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.tg_price);
		mSevenSelect = (TextView) mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.tg_select);
		mEightTime = (TextView) mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.tg_time);
		mEightPrice = (TextView) mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.tg_price);
		mEightSelect = (TextView) mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.tg_select);
		mNineTime = (TextView) mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.tg_time);
		mNinePrice = (TextView) mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.tg_price);
		mNineSelect = (TextView) mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.tg_select);
		mTenTime = (TextView) mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.tg_time);
		mTenPrice = (TextView) mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.tg_price);
		mTenSelect = (TextView) mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.tg_select);
		mElevenTime = (TextView) mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.tg_time);
		mElevenPrice = (TextView) mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.tg_price);
		mElevenSelect = (TextView) mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.tg_select);
		// afternoon time
		mTwelveTime = (TextView) mAfternoonSet.findViewById(R.id.twelve_part).findViewById(R.id.tg_time);
		mTwelvePrice = (TextView) mAfternoonSet.findViewById(R.id.twelve_part).findViewById(R.id.tg_price);
		mTwelveSelect = (TextView) mAfternoonSet.findViewById(R.id.twelve_part).findViewById(R.id.tg_select);
		mThirteenTime = (TextView) mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.tg_time);
		mThirteenPrice = (TextView) mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.tg_price);
		mThirteenSelect = (TextView) mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.tg_select);
		mFourteenTime = (TextView) mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.tg_time);
		mFourteenPrice = (TextView) mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.tg_price);
		mFourteenSelect = (TextView) mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.tg_select);
		mFifteenTime = (TextView) mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.tg_time);
		mFifteenPrice = (TextView) mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.tg_price);
		mFifteenSelect = (TextView) mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.tg_select);
		mSixteenTime = (TextView) mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.tg_time);
		mSixteenPrice = (TextView) mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.tg_price);
		mSixteenSelect = (TextView) mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.tg_select);
		mSeventeenTime = (TextView) mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.tg_time);
		mSeventeenPrice = (TextView) mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.tg_price);
		mSeventeenSelect = (TextView) mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.tg_select);
		mEighteenTime = (TextView) mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.tg_time);
		mEighteenPrice = (TextView) mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.tg_price);
		mEighteenSelect = (TextView) mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.tg_select);
		// night time
		mNineteenTime = (TextView) mNightSet.findViewById(R.id.nineteen_part).findViewById(R.id.tg_time);
		mTwentyTime = (TextView) mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.tg_time);
		mTwentyOneTime = (TextView) mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.tg_time);
		mTwentyTwoTime = (TextView) mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.tg_time);
		mTwentyThreeTime = (TextView) mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.tg_time);
		mNineteenPrice = (TextView) mNightSet.findViewById(R.id.nineteen_part).findViewById(R.id.tg_price);
		mTwentyPrice = (TextView) mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.tg_price);
		mTwentyOnePrice = (TextView) mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.tg_price);
		mTwentyTwoPrice = (TextView) mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.tg_price);
		mTwentyThreePrice = (TextView) mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.tg_price);
		mNineteenSelect = (TextView) mNightSet.findViewById(R.id.nineteen_part).findViewById(R.id.tg_select);
		mTwentySelect = (TextView) mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.tg_select);
		mTwentyOneSelect = (TextView) mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.tg_select);
		mTwentyTwoSelect = (TextView) mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.tg_select);
		mTwentyThreeSelect = (TextView) mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.tg_select);
		// gears
		mFiveGear = (RelativeLayout) mMorningSSet.findViewById(R.id.five_part).findViewById(R.id.tg_geer);
		mSixGear = (RelativeLayout) mMorningSSet.findViewById(R.id.six_part).findViewById(R.id.tg_geer);
		mSevenGear = (RelativeLayout) mMorningSSet.findViewById(R.id.seven_part).findViewById(R.id.tg_geer);
		mEightGear = (RelativeLayout) mMorningSSet.findViewById(R.id.eight_part).findViewById(R.id.tg_geer);
		mNineGear = (RelativeLayout) mMorningSSet.findViewById(R.id.nine_part).findViewById(R.id.tg_geer);
		mTenGear = (RelativeLayout) mMorningSSet.findViewById(R.id.ten_part).findViewById(R.id.tg_geer);
		mElevenGear = (RelativeLayout) mMorningSSet.findViewById(R.id.eleven_part).findViewById(R.id.tg_geer);
		mTwelveGear = (RelativeLayout) mAfternoonSet.findViewById(R.id.twelve_part).findViewById(R.id.tg_geer);
		mThirteenGear = (RelativeLayout) mAfternoonSet.findViewById(R.id.thirteen_part).findViewById(R.id.tg_geer);
		mFourteenGear = (RelativeLayout) mAfternoonSet.findViewById(R.id.fourteen_part).findViewById(R.id.tg_geer);
		mFifteenGear = (RelativeLayout) mAfternoonSet.findViewById(R.id.fifteen_part).findViewById(R.id.tg_geer);
		mSixteenGear = (RelativeLayout) mAfternoonSet.findViewById(R.id.sixteen_part).findViewById(R.id.tg_geer);
		mSeventeenGear = (RelativeLayout) mAfternoonSet.findViewById(R.id.seventeen_part).findViewById(R.id.tg_geer);
		mEighteenGear = (RelativeLayout) mAfternoonSet.findViewById(R.id.eighteen_part).findViewById(R.id.tg_geer);
		mNineteenGear = (RelativeLayout) mNightSet.findViewById(R.id.nineteen_part).findViewById(R.id.tg_geer);
		mTwentyGear = (RelativeLayout) mNightSet.findViewById(R.id.twenty_part).findViewById(R.id.tg_geer);
		mTwentyOneGear = (RelativeLayout) mNightSet.findViewById(R.id.twentyone_part).findViewById(R.id.tg_geer);
		mTwentyTwoGear = (RelativeLayout) mNightSet.findViewById(R.id.twentytwo_part).findViewById(R.id.tg_geer);
		mTwentyThreeGear = (RelativeLayout) mNightSet.findViewById(R.id.twentythree_part).findViewById(R.id.tg_geer);
		tvCenter = (TextView)findViewById(R.id.title_date);
		imgBack = (ImageView)findViewById(R.id.img_title_left);
//		mHangingArrow = (RelativeLayout)findViewById(R.id.hanging_arrow_part);
//		mHangingContent = (LinearLayout)findViewById(R.id.hanging_content);
//		mArrowUp = (RelativeLayout)findViewById(R.id.arrow_part);
	}
	
	protected void fixMorAllSelect() {
		// 修正全选
		boolean hasSelect = false;
		for (int i = 0; i < 7; i++) {
			if (!selectArray[i]) {
				hasSelect = true;
				break;
			}
		}
		if (!hasSelect) {
			mMorSelectAllImg.setSelected(false);
//			mMorSelectAllTv.setText("全选");
		}

	}

	protected void fixAftAllSelect() {
		// 修正全选
		boolean hasSelect = false;
		for (int i = 7; i < 14; i++) {
			if (!selectArray[i]) {
				hasSelect = true;
				break;
			}
		}
		if (!hasSelect) {
			mAftSelectAllImg.setSelected(false);
//			mAftSelectAllTv.setText("全选");
		}

	}

	protected void fixNigAllSelect() {
		// 修正全选
		boolean hasSelect = false;
		for (int i = 14; i < 19; i++) {
			if (!selectArray[i]) {
				hasSelect = true;
				break;
			}
		}
		if (!hasSelect) {
			mNigSelectAllImg.setSelected(false);
//			mNigSelectAllTv.setText("全选");
		}

	}
	
	private List<Schedule> datelist;
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 2001 && data != null) {
			try {
				// Update THE DateList reserved
				updateLocalDateList(((SetDateTimeResult) data.getSerializableExtra("list")).getDatelist(), data.getStringExtra("day"));
			} catch (Exception e) {
				//Log.e(TAG, "convert serializable error");
			}
		}
	}
	
	private void updateLocalDateList(List<Schedule> datelist, String day) {
		if (datelist != null && datelist.size() > 0) {
//			new UpdateScheduleTask(datelist, day).execute();
			scheduleResult.getDatelist().clear();
			scheduleResult.getDatelist().addAll(datelist);
			initDataResult();
		}
	}
	
	private class ChangeAllDayScheduleTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(ActivityDateSet.this.getApplicationContext(), JSONAccessor.METHOD_POST);
		String type;
		//String day;

		public ChangeAllDayScheduleTask(String type) {
			this.type = type;
			//day = TimeUtil.calendarToString(gAdapter.getSelectedDate());
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
			param.put("action", "ChangeAllDaySchedule");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("day", Year+"-"+mon+"-"+date);
			param.put("type", type); // 1:open 2: close
			return accessor.execute(Settings.SCHEDULE_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();

			if (result != null) {
				if (result.getCode() == 1) {
					if (type != null && type.equals("1")) {
						isAllDayOpen = true;
						mAllDaySetClose.setText("当天停课");
						//new ChangeClassStateTask(1, day).execute();
					} else {
						isAllDayOpen = false;
						mAllDaySetClose.setText("发布课程");
						//new ChangeClassStateTask(0, day).execute();
					}
				} else {
					if (result.getCode() == 95) {
						if (result.getMessage() != null)
							CommonUtils.showToast(ActivityDateSet.this.getApplicationContext(), result.getMessage());
						CommonUtils.gotoLogin(ActivityDateSet.this);
					} else {
						if (result.getMessage() != null)
							if (defaultSel)
								CommonUtils.showToast(ActivityDateSet.this.getApplicationContext(), result.getMessage());
							else {
								CommonUtils.showToast(ActivityDateSet.this.getApplicationContext(), "设置成功");
							}
					}
				}

			} else {
				CommonUtils.showToast(ActivityDateSet.this.getApplicationContext(), ActivityDateSet.this.getString(R.string.net_error));
			}
		}

	}
	
	private class SetDefaultTask extends AsyncTask<Void, Void, SetDefaultResult> {
		JSONAccessor accessor = new JSONAccessor(ActivityDateSet.this, JSONAccessor.METHOD_POST);

		@Override
		protected SetDefaultResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "SetDefault");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("day", TimeUtil.calendarToString(calSelecter));
			return accessor.execute(Settings.SCHEDULE_URL, param, SetDefaultResult.class);
		}

		@Override
		protected void onPostExecute(SetDefaultResult result) {
			super.onPostExecute(result);
			if (result != null && result.getCode() == 1) {
				Toast.makeText(ActivityDateSet.this, "设为默认课程成功", Toast.LENGTH_SHORT).show();
				List<Schedule> list = scheduleResult.getDatelist();
				int count = 0;
				List<Schedule> temp = new ArrayList<Schedule>();
				for (; count < list.size(); count++) {
					if (list.get(count).getDate().equals(TimeUtil.calendarToString(calSelecter))) {
						temp.add(list.get(count));
					}
				}
				list.remove(temp);
				list.addAll(result.getDatelist());
			} else {
				if (result != null && result.getCode() == 95) {
					if (result.getMessage() != null)
						Toast.makeText(ActivityDateSet.this, result.getMessage(), Toast.LENGTH_SHORT).show();
					CommonUtils.gotoLogin(ActivityDateSet.this);
				} else {
					Toast.makeText(ActivityDateSet.this, "设为默认课程失败", Toast.LENGTH_SHORT).show();
				}
			}

		}

	}
	
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
			//new RefreshBallStateTask().execute();
		}

	}
}
*/