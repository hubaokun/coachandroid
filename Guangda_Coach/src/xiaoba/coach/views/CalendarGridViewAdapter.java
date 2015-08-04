package xiaoba.coach.views;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.interfaces.NotifyDateSelect;
import xiaoba.coach.net.result.GetScheduleResult;
import xiaoba.coach.utils.TimeUtil;

import com.daoshun.lib.view.OnSingleClickListener;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalendarGridViewAdapter extends BaseAdapter {

	private Calendar calStartDate = Calendar.getInstance();// 当前显示的日历
	private static Calendar calSelected = Calendar.getInstance(); // 选择的日历
	private View selectedView;
	private GetScheduleResult result;
	private NotifyDateSelect mNotifyDateSelect;

	private Activity activity;
	private static Calendar calToday = Calendar.getInstance(); // 今日
	private int iMonthViewCurrentMonth = 0; // 当前视图月
	private Resources resources;

	/*
	 * constructor
	 */
	public CalendarGridViewAdapter(Activity a, Calendar cal) {
		calStartDate = cal;
		activity = a;
		resources = activity.getResources();
		titles = getDates();
	}

	public CalendarGridViewAdapter(Activity a) {
		activity = a;
		resources = activity.getResources();
	}

	public CalendarGridViewAdapter(Activity a, GetScheduleResult result) {
		activity = a;
		resources = activity.getResources();
		this.result = result;

		/*
		 * set today's date (according to server's date recorder)
		 */
		if (result != null && result.getToday() != null)
			try {
				calToday = TimeUtil.StringToCalendar(result.getToday());
			} catch (Exception e) {
			}

		titles = getDates();
	}

	public void setScheduleResult(GetScheduleResult result) {
		this.result = result;

		if (result != null && result.getToday() != null)
			try {
				calToday = TimeUtil.StringToCalendar(result.getToday());
			} catch (Exception e) {
			}
	}

	public void setSelectedDate(Calendar cal) {
		calSelected = cal;
	}

	public Calendar getSelectedDate() {
		return calSelected;
	}

	public Calendar getCalToday() {
		return calToday;
	}

	public void setCalToday(Calendar calToday) {
		CalendarGridViewAdapter.calToday = calToday;
	}

	// 根据改变的日期更新日历
	// 填充日历控件用
	private void UpdateStartDateForMonth() {
		calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
		iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月

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

		calStartDate.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位

	}

	ArrayList<java.util.Date> titles;
	ArrayList<BallState> ballState = new ArrayList<CalendarGridViewAdapter.BallState>();

	public ArrayList<BallState> getBallState() {
		return ballState;
	}

	public void setBallState(ArrayList<BallState> ballState) {
		this.ballState = ballState;
	}

	/*
	 * 控制三球状态
	 */
	public class BallState {
		boolean showY; // 显示黄球
		boolean showR; // 显示红球
		boolean showB; // 显示蓝球

		public boolean isShowY() {
			return showY;
		}

		public void setShowY(boolean showY) {
			this.showY = showY;
		}

		public boolean isShowR() {
			return showR;
		}

		public void setShowR(boolean showR) {
			this.showR = showR;
		}

		public boolean isShowB() {
			return showB;
		}

		public void setShowB(boolean showB) {
			this.showB = showB;
		}

	}

	private ArrayList<java.util.Date> getDates() {

		UpdateStartDateForMonth();

		ArrayList<java.util.Date> alArrayList = new ArrayList<java.util.Date>();

		int num = getWeeksInMonth();

		for (int i = 1; i <= 7 * num; i++) {
			alArrayList.add(calStartDate.getTime());
			ballState.add(new BallState());
			calStartDate.add(Calendar.DAY_OF_MONTH, 1);
		}

		return alArrayList;
	}

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

	@Override
	public int getCount() {
		return titles.size();
	}

	@Override
	public Object getItem(int position) {
		return titles.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	ViewHolder holder;

	@Override
	public View getView(int position, View iv, ViewGroup parent) {
		Date myDate = (Date) getItem(position);
		Calendar calCalendar = Calendar.getInstance();
		calCalendar.setTime(myDate);

		int iMonth = calCalendar.get(Calendar.MONTH);

		if (iv != null) {
			holder = (ViewHolder) iv.getTag();
		} else {
			holder = new ViewHolder();
			iv = LayoutInflater.from(activity).inflate(R.layout.item_date_picker, null);
			iv.setLayoutParams(new AbsListView.LayoutParams((Settings.DISPLAY_WIDTH - 12) / 7, (Settings.DISPLAY_WIDTH - 12) / 7));
			holder.yBall = (View) iv.findViewById(R.id.yellow_ball);
			holder.rBall = (View) iv.findViewById(R.id.red_ball);
			holder.bBall = (View) iv.findViewById(R.id.blue_ball);
			// 今天的处理
			holder.txtToDay = (TextView) iv.findViewById(R.id.calendar_status);
			// 日期开始
			holder.txtDay = (TextView) iv.findViewById(R.id.calendar_date);
			iv.setTag(holder);
		}

		// 背景铺色
		iv.setBackgroundColor(Color.parseColor("#222222"));

		int day = myDate.getDate(); // 设置日期
		holder.txtDay.setText(String.valueOf(day));
//		iv.setTag(myDate);

		holder.yBall.setVisibility(View.GONE);
		holder.rBall.setVisibility(View.GONE);
		holder.bBall.setVisibility(View.GONE);

		// 判断是否是当前月
		if (iMonth == iMonthViewCurrentMonth) {
			holder.txtDay.setVisibility(View.VISIBLE);
			holder.txtToDay.setVisibility(View.VISIBLE);
			if (beforeDate(myDate, calToday.getTime())) {
				holder.txtToDay.setTextColor(resources.getColor(R.color.unable_grey));
				holder.txtDay.setTextColor(resources.getColor(R.color.unable_grey));
				holder.txtToDay.setText("不可操作");
				iv.setClickable(false);
			} else {
				if (equalsDate(calToday.getTime(), myDate)) {
					// 当前日期
					holder.txtDay.setTextColor(resources.getColor(R.color.text_green));
					holder.txtToDay.setTextColor(resources.getColor(R.color.text_green));
					holder.txtToDay.setText("今日");
					iv.setClickable(true);
					iv.setOnClickListener(new DateClickListener(calCalendar, iv));
				} else {
					if (daysBetween(calToday.getTime(), myDate)) {
						holder.txtToDay.setText("未开课");
						holder.txtToDay.setTextColor(resources.getColor(R.color.unable_grey));
						holder.txtDay.setTextColor(resources.getColor(R.color.unable_grey));
						iv.setClickable(true);
						iv.setOnClickListener(new DateClickListener(calCalendar, iv));

						if (ballState.get(position).isShowY()) {
							holder.yBall.setVisibility(View.VISIBLE);
						} else {
						}
						if (ballState.get(position).isShowR()) {
							holder.rBall.setVisibility(View.VISIBLE);
						} else {
						}
						if (ballState.get(position).isShowB()) {
							holder.bBall.setVisibility(View.VISIBLE);
						} else {
						}

						/*
						 * 若是有球显示,字为白色
						 */
						if (ballState.get(position).isShowY() || ballState.get(position).isShowR() || ballState.get(position).isShowB()) {
							holder.txtDay.setTextColor(resources.getColor(R.color.white));

							if (holder.txtToDay.getVisibility() == View.VISIBLE)
								holder.txtToDay.setVisibility(View.INVISIBLE);
						}
					} else {
						holder.txtDay.setTextColor(resources.getColor(R.color.unable_grey));
						holder.txtToDay.setTextColor(resources.getColor(R.color.unable_grey));
						holder.txtToDay.setText("不可操作");
						iv.setClickable(false);
					}
				}
			}
		} else {
			holder.txtDay.setVisibility(View.INVISIBLE);
			holder.txtToDay.setVisibility(View.INVISIBLE);
			iv.setBackgroundColor(Color.parseColor("#222222"));
			iv.setClickable(false);
			return iv;
		}

		// 设置背景颜色
		if (equalsDate(calSelected.getTime(), myDate)) {
			// 选择的
			iv.setBackgroundColor(resources.getColor(R.color.white));
			holder.txtDay.setTextColor(resources.getColor(R.color.text_black));
			holder.txtToDay.setTextColor(resources.getColor(R.color.text_black));
			selectedView = iv;
		} else {
			iv.setBackgroundColor(Color.parseColor("#222222"));
		}

		return iv;
	}

	private class ViewHolder {
		TextView txtDay;
		TextView txtToDay;
		View iv;
		View yBall;
		View rBall;
		View bBall;
	}

	/*
	 * 
	 */
	private Calendar getCalandar(int position) {
		Date myDate = (Date) getItem(position);
		Calendar calCalendar = Calendar.getInstance();
		calCalendar.setTime(myDate);
		return calCalendar;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	private class DateClickListener extends OnSingleClickListener {

		Calendar mCarlandar;
		View view;

		public DateClickListener(Calendar mCarlandar, View view) {
			this.mCarlandar = mCarlandar;
			this.view = view;
		}

		@Override
		public void doOnClick(View v) {
			if (equalsDate(mCarlandar.getTime(), calSelected.getTime())) {
				return; // click selected item
			}
			selectedView.setBackgroundColor(Color.parseColor("#222222"));
			view.setBackgroundColor(Color.parseColor("#ffffff"));
			yBall = (View) selectedView.findViewById(R.id.yellow_ball);
			rBall = (View) selectedView.findViewById(R.id.red_ball);
			bBall = (View) selectedView.findViewById(R.id.blue_ball);

			changeColor((ViewGroup) view, resources.getColor(R.color.text_black));
			if (equalsDate(calSelected.getTime(), calToday.getTime())) {
				// last selected date is today
				changeColor((ViewGroup) selectedView, resources.getColor(R.color.text_green));
			} else {
				if (yBall.getVisibility() == View.VISIBLE || rBall.getVisibility() == View.VISIBLE || bBall.getVisibility() == View.VISIBLE)
					changeColor((ViewGroup) selectedView, resources.getColor(R.color.white));
				else {
					changeColor((ViewGroup) selectedView, resources.getColor(R.color.unable_grey));
				}
			}
			calSelected = mCarlandar;
			selectedView = v;

			if (mNotifyDateSelect != null)
				mNotifyDateSelect.notify(calSelected);

		}

	}

	/*
	 * 
	 */
	void changeColor(ViewGroup group, int colorRes) {
		for (int i = 0; i < group.getChildCount(); i++) {
			if (group.getChildAt(i) instanceof TextView) {
				((TextView) group.getChildAt(i)).setTextColor(colorRes);
			} else if (group.getChildAt(i) instanceof ViewGroup)
				changeColor((ViewGroup) group.getChildAt(i), colorRes);
		}

	}

	/*
	 * date1 == date2 return true
	 */
	private Boolean equalsDate(Date date1, Date date2) {

		if (date1.getYear() == date2.getYear() && date1.getMonth() == date2.getMonth() && date1.getDate() == date2.getDate()) {
			return true;
		} else {
			return false;
		}

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

		return Integer.parseInt(String.valueOf(between_days)) <= 29;
	}

	View yBall, rBall, bBall;
	TextView status;

	/*
	 * 隐藏黄球
	 */
	public void hideYellowPoint() {
		yBall = (View) selectedView.findViewById(R.id.yellow_ball);
		rBall = (View) selectedView.findViewById(R.id.red_ball);
		bBall = (View) selectedView.findViewById(R.id.blue_ball);
		status = (TextView) selectedView.findViewById(R.id.calendar_status);
		yBall.setVisibility(View.GONE);
		if (status != null)
			if (rBall.getVisibility() == View.GONE && bBall.getVisibility() == View.GONE)
				if (equalsDate(calSelected.getTime(), calToday.getTime()))
					status.setText("今日");
				else {
					status.setText("未开课");
				}
			else {
				if (!equalsDate(calSelected.getTime(), calToday.getTime()))
					status.setText("");
				else
					status.setText("今日");
			}

	}

	/*
	 * 隐藏红球
	 */
	public void hideRedPoint() {
		yBall = (View) selectedView.findViewById(R.id.yellow_ball);
		rBall = (View) selectedView.findViewById(R.id.red_ball);
		bBall = (View) selectedView.findViewById(R.id.blue_ball);
		status = (TextView) selectedView.findViewById(R.id.calendar_status);
		rBall.setVisibility(View.GONE);
		if (status != null)
			if (yBall.getVisibility() == View.GONE && bBall.getVisibility() == View.GONE)
				if (equalsDate(calSelected.getTime(), calToday.getTime()))
					status.setText("今日");
				else {
					status.setText("未开课");
				}
			else {
				if (!equalsDate(calSelected.getTime(), calToday.getTime()))
					status.setText("");
				else
					status.setText("今日");
			}
	}

	/*
	 * 隐藏蓝球
	 */
	public void hideBluePoint() {
		yBall = (View) selectedView.findViewById(R.id.yellow_ball);
		rBall = (View) selectedView.findViewById(R.id.red_ball);
		bBall = (View) selectedView.findViewById(R.id.blue_ball);
		status = (TextView) selectedView.findViewById(R.id.calendar_status);
		bBall.setVisibility(View.GONE);
		if (status != null)
			if (rBall.getVisibility() == View.GONE && yBall.getVisibility() == View.GONE)
				if (equalsDate(calSelected.getTime(), calToday.getTime()))
					status.setText("今日");
				else {
					status.setText("未开课");
				}
			else {
				if (!equalsDate(calSelected.getTime(), calToday.getTime()))
					status.setText("");
				else
					status.setText("今日");
			}
	}

	public NotifyDateSelect getmNotifyDateSelect() {
		return mNotifyDateSelect;
	}

	public void setmNotifyDateSelect(NotifyDateSelect mNotifyDateSelect) {
		this.mNotifyDateSelect = mNotifyDateSelect;
	}

}
