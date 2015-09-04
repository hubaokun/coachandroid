package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.interfaces.DialogConfirmListener;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.Schedule;
import xiaoba.coach.net.result.AddressResult.AddressInfo;
import xiaoba.coach.net.result.GetAllSubjectResult;
import xiaoba.coach.net.result.AddressResult;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.GetScheduleByDateResult;
import xiaoba.coach.net.result.GetScheduleResult;
import xiaoba.coach.net.result.SetDateTimeResult;
import xiaoba.coach.net.result.GetAllSubjectResult.SubjectInfo;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.utils.DialogUtil;
import xiaoba.coach.views.PriceDialog;
import xiaoba.coach.views.PriceDialog.OnComfirmClickListener;
import xiaoba.coach.views.SelectDialog;
import xiaoba.coach.views.WheelCityDialog;

import com.baidu.mapapi.map.Text;
import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.util.DensityUtils;
import com.daoshun.lib.view.OnSingleClickListener;
import com.google.gson.Gson;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
 * 课时设置
 */
@EActivity(R.layout.activity_class_time_set)
public class ClassTimeSetActivity extends BaseActivity {
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.modify_classset_price)
	ImageView mModifyPrice; // 修改单价
	@ViewById(R.id.modify_classset_content)
	ImageView mModifyContent; // 修改教学内容
	@ViewById(R.id.modify_classset_loca)
	ImageView mModifLoc; // 修改教学地点
	@ViewById(R.id.classset_hours)
	TextView mHours; // 修改的时间
	@ViewById (R.id.ll_modify_loca)
	LinearLayout llChangeLoca;
/*	@ViewById(R.id.btn_isrest)
	ImageView mIsRest;*/
/*	@ViewById(R.id.open_close_tip)
	TextView mCloseTip;*/
	@ViewById(R.id.classset_price_title)
	TextView mPriceTi;
	@ViewById(R.id.classset_loc_title)
	TextView mLocTi;
	@ViewById(R.id.classset_content_title)
	TextView mContentTi;
	@ViewById(R.id.classset_loc_tv)
	TextView mLocTv;
	@ViewById(R.id.classset_content_tv)
	TextView mContentTv;
	@ViewById(R.id.classset_price_et)
	TextView mPriceEt;

//	String mor, aft, nig;
	int /*single,*/ textLength/* 一个小时占据的宽度 */;
	boolean isRest; // 时间段是否休息
	boolean isClick; // 保存按钮
	String day; // 修改的日期
	String[] content; // 上车地点数组
	String[] locContent; // 教学课程数据
	GetScheduleResult result;
	/*
	 * 课程id
	 */
	int subjectId;
	/*
	 * 上车地址id
	 */
	int addressId;
	
	private Dialog mDialog = null;
	private PriceDialog pricerDialog;
	private ArrayList<Integer> Hour = new ArrayList<Integer>();
	private String addressDetail;
	private String subjectDetail;
	@AfterViews
	void init() {
		mTitle.setText(getString(R.string.class_time_set));
		mTitle.setTextColor(getResources().getColor(R.color.text_black));
		mTitleRightTv.setText(getString(R.string.save));
		mTitleRightTv.setTextColor(getResources().getColor(R.color.text_grey));
		mTitleRightTv.setClickable(false);
		mTitleBack.setImageResource(R.drawable.back_arrow);
		textLength = (Settings.DISPLAY_WIDTH - DensityUtils.dp2px(ClassTimeSetActivity.this, 12)) / getTextViewLength(mHours, "00:00、");
		pricerDialog = new PriceDialog(ClassTimeSetActivity.this);
		addListeners();
		getIntentData();
		new prepareTask().execute();
		new GetAllSubjectTask().execute();
		new GetAllAddressTask().execute();
		mApplication.setSaveSet(false);

		llChangeLoca.setOnClickListener(new OnSingleClickListener() {
			@Override
			public void doOnClick(View v) {
				if (locContent != null && locContent.length > 0) {
					setRightClick();
					dialog = new SelectDialog(ClassTimeSetActivity.this, locContent);
					String address = mContentTv.getText().toString().trim();
					int index = 0;
					if (!TextUtils.isEmpty(address))
					{
						for (int i=0;i<locContent.length;i++)
						{
							if (address.contains(locContent[i]))
							{
								index = i;
								break;
							}
						}
					}
					dialog.setAdapter(index);
					dialog.setmConfirmListener(new DialogConfirmListener() {

						@Override
						public void doConfirm(String str) {
							if (str != null)
							{
								mLocTv.setText(str);
								addressDetail = str;
							}
							if (addresses != null && addresses.size() > 0) {
								for (AddressInfo info : addresses) {
									if (info.getDetail() != null && info.getDetail().equals(str))
										addressId = info.getAddressid();
								}
							}
							dialog.dismiss();
						}

						@Override
						public void doCancel() {
							dialog.dismiss();
						}
					});
					dialog.show();
				} else {
					CommonUtils.showToast(ClassTimeSetActivity.this, "暂未取得上车地点");
				}

			}
		});

//		mPriceEt.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//			}
//
//			@Override
//			public void afterTextChanged(Editable arg0) {
//				if (lastPrice != null) {
//					if (mPriceEt.getText().toString() != null && !mPriceEt.getText().toString().equals(lastPrice)) {
//						mModifyPrice.setImageResource(R.drawable.pencil_color);
//						setRightClick();
//					} else {
//						mModifyPrice.setImageResource(R.drawable.pencile);
//					}
//				} else {
//					if (mPriceEt.getText().length() > 0) {
//						mModifyPrice.setImageResource(R.drawable.pencil_color);
//						setRightClick();
//					} else {
//						mModifyPrice.setImageResource(R.drawable.pencile);
//					}
//				}
//			}
//		});
	}

	private class prepareTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			List<String> contentList = new ArrayList<String>();
			// 缩小list
			if (list != null) {
				for (Schedule schedule1 : list) {
					if (schedule1.getDate() != null && schedule1.getDate().equals(day)) {
						dayList.add(schedule1);
//						if (single != 0) {
//							if (schedule1.getHour() != single)
//								templist.remove(schedule1);
//						} else {
//							if (mor != null) {
//								if (schedule1.getHour() <= 11 && schedule1.getHour() >= 5) {
//									if (mor.charAt(schedule1.getHour() - 5) != 49)
//										templist.remove(schedule1);
//								} else {
//									templist.remove(schedule1);
//								}
//							} else if (aft != null) {
//								if (schedule1.getHour() <= 18 && schedule1.getHour() >= 12) {
//									if (aft.charAt(schedule1.getHour() - 12) != 49)
//										templist.remove(schedule1);
//								} else {
//									templist.remove(schedule1);
//								}
//
//							} else if (nig != null) {
//								if (schedule1.getHour() <= 23 && schedule1.getHour() >= 19) {
//									if (nig.charAt(schedule1.getHour() - 19) != 49)
//										templist.remove(schedule1);
//								} else {
//									templist.remove(schedule1);
//								}
//							}
//						}
//						
						for (int i=0;i<Hour.size();i++)
						{
							if (schedule1.getHour() == Hour.get(i))
							{
								templist.add(schedule1);
							}
						}
						if (schedule1.getAddressdetail() != null && !contentList.contains(schedule1.getAddressdetail())) {
							contentList.add(schedule1.getAddressdetail());
						}
					} else {
//						templist.remove(schedule1);
					}
				}
				list = templist;
				sortList();
			}
			if (contentList != null) {
				locContent = contentList.toArray(new String[contentList.size()]);

				contentList.clear();
				if (list != null && list.size() > 0)
					for (Schedule schedule2 : list) {
						if (schedule2.getDate() != null && schedule2.getDate().equals(day)) {
							if (schedule2.getSubject() != null && !contentList.contains(schedule2.getSubject())) {
								contentList.add(schedule2.getSubject());
							}
						}
					}
				content = contentList.toArray(new String[contentList.size()]);
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			for (int hour:Hour)
			{
				if (list != null && list.size() > 0) {
					Schedule ssSchedule = list.get(0);
					if (ssSchedule.getSubject() != null) {
						mContentTv.setText(ssSchedule.getSubject());
					}
					if (ssSchedule.getAddressdetail() != null) {
						mLocTv.setText(ssSchedule.getAddressdetail());
					}

					addressId = ssSchedule.getAddressid();
					subjectId = ssSchedule.getSubjectid();
					if (ssSchedule.getHour() == hour && ssSchedule.getPrice() != null) {
						String temp = ssSchedule.getPrice().contains(".") ? ssSchedule.getPrice().substring(0, ssSchedule.getPrice().indexOf(".")) : ssSchedule.getPrice();
						lastPrice = temp;
						mPriceEt.setText(temp);
						//mPriceEt.setSelection(temp.length());
					}
				}
			}
			
//			if (single != 0) {
//				/*
//				 * 单个小时显示价钱
//				 */
//				if (list != null && list.size() > 0) {
//					Schedule ssSchedule = list.get(0);
//					if (ssSchedule.getSubject() != null) {
//						mContentTv.setText(ssSchedule.getSubject());
//					}
//					if (ssSchedule.getAddressdetail() != null) {
//						mLocTv.setText(ssSchedule.getAddressdetail());
//					}
//
//					addressId = ssSchedule.getAddressid() + "";
//					subjectId = ssSchedule.getSubjectid() + "";
//					if (ssSchedule.getHour() == single && ssSchedule.getPrice() != null) {
//						String temp = ssSchedule.getPrice().contains(".") ? ssSchedule.getPrice().substring(0, ssSchedule.getPrice().indexOf(".")) : ssSchedule.getPrice();
//						lastPrice = temp;
//						mPriceEt.setText(temp);
//						//mPriceEt.setSelection(temp.length());
//					}
//				}
//			} else {
//				if (smallestSchedule != null) {
//					if (smallestSchedule.getSubject() != null) {
//						mContentTv.setText(smallestSchedule.getSubject());
//					}
//					if (smallestSchedule.getAddressdetail() != null) {
//						mLocTv.setText(smallestSchedule.getAddressdetail());
//					}
//					addressId = smallestSchedule.getAddressid() + "";
//					subjectId = smallestSchedule.getSubjectid() + "";
//					String temp = smallestSchedule.getPrice().contains(".") ? smallestSchedule.getPrice().substring(0, smallestSchedule.getPrice().indexOf(".")) : smallestSchedule.getPrice();
//					mPriceEt.setText(temp);
//					//mPriceEt.setSelection(temp.length());
//				}
//			}
		}
	}

	Schedule smallestSchedule;

	/*
	 * 取到最小的时间
	 */
	private void sortList() {
		if (list != null && list.size() > 0) {
			smallestSchedule = list.get(0);
			/*
			 * 遍历once
			 */
			for (Schedule schedule : list) {
				if (schedule.getHour() < smallestSchedule.getHour())
					smallestSchedule = schedule;
			}
		}
	}

	String lastPrice;

	private void addListeners() {
		pricerDialog.setOnComfirmClickListener(new OnComfirmClickListener() {

			@Override
			public void onComfirmBtnClick(String hundred, String tends,
					String single) {
				// TODO Auto-generated method stub
				//Toast.makeText(ClassTimeSetActivity.this, hundred+"  "+tends+"  "+single,Toast.LENGTH_SHORT).show();
				if (!"0".equals(hundred))
				{
					mPriceEt.setText(hundred+tends+single);
				}else{
					mPriceEt.setText(tends+single);
				}
				pricerDialog.dismiss();
			}
		});
		// 输入控制
//		mPriceEt.setKeyListener(new NumberKeyListener() {
//
//			@Override
//			public int getInputType() {
//				return android.text.InputType.TYPE_CLASS_PHONE;
//			}
//
//			@Override
//			protected char[] getAcceptedChars() {
//				char[] numberChars = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
//
//				return numberChars;
//			}
//		});
	}

	List<Schedule> list, templist = new ArrayList<Schedule>();
	private ArrayList<Schedule> dayList = new ArrayList<Schedule>();

	private void getIntentData() {
		Intent intent = getIntent();
//		mor = intent.getStringExtra("mor");
//		aft = intent.getStringExtra("aft");
//		nig = intent.getStringExtra("nig");
//		single = intent.getIntExtra("single", 0);
		Hour = intent.getIntegerArrayListExtra("hour");
		Collections.sort(Hour);
		day = intent.getStringExtra("day");
		isRest = !intent.getBooleanExtra("rest", false); // 需求修改使得状态反向 false:rest
		if ((Object) intent.getSerializableExtra("result") instanceof GetScheduleResult) {
			result = (GetScheduleResult) intent.getSerializableExtra("result");
			list = result.getDatelist();
//			if (list != null)
//				templist.addAll(list);
		}
		setHours();
		checkTimeState();
	}

	private void checkTimeState() {
//		if (isRest) {
////			mIsRest.setImageResource(R.drawable.button_off);
////			mCloseTip.setText(ClassTimeSetActivity.this.getString(R.string.classset_close_tip));
//			mPriceTi.setTextColor(Color.parseColor("#d2d2d2"));
//			mLocTi.setTextColor(Color.parseColor("#d2d2d2"));
//			mLocTv.setTextColor(Color.parseColor("#d2d2d2"));
//			mContentTi.setTextColor(Color.parseColor("#d2d2d2"));
//			mContentTv.setTextColor(Color.parseColor("#d2d2d2"));
//			mModifyPrice.setVisibility(View.INVISIBLE);
//			mModifLoc.setVisibility(View.INVISIBLE);
//			mModifyContent.setVisibility(View.INVISIBLE);
//			mPriceEt.setFocusable(false);
//			mPriceEt.setFocusableInTouchMode(false);
//			mPriceEt.setTextColor(Color.parseColor("#d2d2d2"));
//		} else {
//			mIsRest.setImageResource(R.drawable.button_on);
//			mCloseTip.setText(ClassTimeSetActivity.this.getString(R.string.classset_open_tip));
			mPriceTi.setTextColor(Color.parseColor("#252525"));
			mLocTi.setTextColor(Color.parseColor("#252525"));
			mLocTv.setTextColor(Color.parseColor("#252525"));
			mContentTi.setTextColor(Color.parseColor("#252525"));
			mContentTv.setTextColor(Color.parseColor("#252525"));
			mModifyPrice.setVisibility(View.VISIBLE);
			mModifLoc.setVisibility(View.VISIBLE);
			mModifyContent.setVisibility(View.VISIBLE);
			mPriceEt.setFocusable(true);
			mPriceEt.setFocusableInTouchMode(true);
			mPriceEt.setTextColor(Color.parseColor("#252525"));
//		}
	}

	/*
	 * 将参数转成时间
	 */
	private void setHours() {
//		if (mor != null) {
//			mHours.setText(getHourStr(mor, 5));
//		} else if (aft != null) {
//			mHours.setText(getHourStr(aft, 12));
//		} else if (nig != null) {
//			mHours.setText(getHourStr(nig, 19));
//		} else if (single != 0) {
//			mHours.setText(single + ":00");
//		}
//		StringBuilder hourbuilder = new StringBuilder();
//		for (int hour:Hour)
//		{
//			hourbuilder.append(hour+":00,");
//		}
		mHours.setText(getHourStr());
	}

	//String data, int startIndex
	StringBuilder getHourStr() {
		int count = 0;
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		String hour = "";
		for (int hour1:Hour)
		{
			if (hour1<10)
			{
				hour = "0"+hour1;
			}else{
				hour = ""+hour1;
			}
			
			if (count <= textLength) {
				if (first) {
					sb.append(hour + ":00");
					count++;
					first = false;
					continue;
				}
				if (count==textLength-1) {
					sb.append("\n" + hour + ":00、");
					count = 0;
					first = true;
					continue;
				}
				sb.append("、" + hour + ":00");
				count++;
			}
		}
//		for (int i = 0; i < data.length(); i++) {
//			if (data.charAt(i) == 49 && count <= textLength) {
//				if (i + startIndex < 10) {
//					hour = "0" + (i + startIndex);
//				} else {
//					hour = "" + (i + startIndex);
//				}
//				if (first) {
//					sb.append(hour + ":00");
//					count++;
//					first = false;
//					continue;
//				}
//				if (count % textLength == 0) {
//					sb.append("\n" + hour + ":00、");
//					count = 0;
//					first = true;
//					continue;
//				}
//				sb.append("、 " + hour + ":00");
//				count++;
//			}
//		}
		return sb;
	}

	public int getTextViewLength(TextView textView, String text) {
		TextPaint paint = textView.getPaint();
		// 得到使用该paint写上text的时候,像素为多少
		float textLength = paint.measureText(text);
		return (int) textLength;
	}

	@Click(R.id.title_back)
	void returnLastPage() {
		if(isClick)
		{
		DialogUtil dUtil = new DialogUtil(new DialogConfirmListener() {

			@Override
			public void doConfirm(String str) {
				if (mDialog != null)
				{
					mDialog.dismiss();
					if (TextUtils.isEmpty(mPriceEt.getText())) {
						Toast.makeText(ClassTimeSetActivity.this.getApplicationContext(), "请输入单价", Toast.LENGTH_SHORT).show();
					} else {
						new SetDateTimeTask().execute();
						//...\
						
					}
				}
			}

			@Override
			public void doCancel() {
				if (mDialog != null)
					mDialog.dismiss();
					ClassTimeSetActivity.this.finish();
			}
		});
		mDialog = dUtil.CallConfirmDialog("请点击保存，让您的修改生效","保存","放弃",ClassTimeSetActivity.this, mDialog);
		}else{
			ClassTimeSetActivity.this.finish();
		}
		
	}

	@Click(R.id.title_right_text)
	void gotoNextPage() {
		if (TextUtils.isEmpty(mPriceEt.getText())) {
			Toast.makeText(ClassTimeSetActivity.this.getApplicationContext(), "请输入单价", Toast.LENGTH_SHORT).show();
		} else {
			new SetDateTimeTask().execute();
			//....
			//setDateTime();
		}
	}
	
	private void setDateTime()
	{
		for (int hour:Hour)
		{
//			HourJson hj = new HourJson();
//			hj.setHour(hour + "");
//			Schedule temp = null;
			if (list != null) {
				for (int i=0;i<list.size();i++) {
					Schedule schedule = list.get(i);
					if (schedule.getDate() != null && schedule.getDate().equals(day)) {
						if (schedule.getHour() == hour) {
							//temp = schedule;
							if (mPriceEt.getText().length() > 0)
							{
								String price = mPriceEt.getText().toString().trim();
								list.get(i).setPrice(price);
							}

							if (addressDetail !=null) {
								list.get(i).setAddressid(addressId);
								list.get(i).setAddressdetail(addressDetail);
							}
							if (subjectDetail != null) {
								list.get(i).setSubjectid(subjectId);
								list.get(i).setSubject(subjectDetail);
							}
							break;
						}
					}
				}
			}
		}
		
		for (int i=0;i<dayList.size();i++)
		{
			for (Schedule scheudle:list)
			{
				if (dayList.get(i).getHour() == scheudle.getHour())
				{
					dayList.set(i, scheudle);
					break;
				}
			}
		}
		setResult(0, new Intent().putExtra("list", dayList).putExtra("day", day));
		CommonUtils.showToast(ClassTimeSetActivity.this.getApplicationContext(), "设置成功");
		ClassTimeSetActivity.this.finish();
	}

	SelectDialog dialog;

	@Click(R.id.ll_modify_content)
	void showContent() {
		if (content != null && content.length > 0) {
			setRightClick();
			dialog = new SelectDialog(this, content);
			String subject = mContentTv.getText().toString().trim();
			int index = 0;
			if (!TextUtils.isEmpty(subject))
			{
				for (int i=0;i<content.length;i++)
				{
					if (subject.contains(content[i]))
					{
						index = i;
						break;
					}
				}
			}
//			dialog.mYearWheel.setCurrentItem(index);
			dialog.setmConfirmListener(new DialogConfirmListener() {

				@Override
				public void doConfirm(String str) {
					if (str != null)
					{
						mContentTv.setText(str);
						subjectDetail = str;
					}
					if (subjectInfos != null && subjectInfos.size() > 0) {
						for (SubjectInfo info : subjectInfos) {
							if (info.getSubjectname() != null && info.getSubjectname().equals(str))
								subjectId = info.getSubjectid();
						}
					}

					dialog.dismiss();
				}

				@Override
				public void doCancel() {
					dialog.dismiss();
				}
			});
			dialog.setAdapter(index);
			dialog.show();
		} else {
			CommonUtils.showToast(ClassTimeSetActivity.this, "暂未取得课程内容");
		}
	}

	@Click(R.id.ll_chang_price)
	void inputPrice() {

		pricerDialog.show();
		setRightClick();
//		mPriceEt.setFocusable(true);
//		mPriceEt.requestFocus();
	}

	@Click(R.id.btn_isrest)
	void switchBtnState() {
		setRightClick();
		isRest = !isRest;
		checkTimeState();
	}

	void setRightClick() {
		if (!isClick) {
			mTitleRightTv.setTextColor(getResources().getColor(R.color.text_black));
			mTitleRightTv.setClickable(true);
			isClick = true;
		}
	}

	/*
	 * 
	 */
	private class SetDateTimeTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(ClassTimeSetActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);
		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					mApplication.setSaveSet(true);
					for (int hour:Hour)
					{
//						HourJson hj = new HourJson();
//						hj.setHour(hour + "");
//						Schedule temp = null;
						if (list != null) {
							for (int i=0;i<list.size();i++) {
								Schedule schedule = list.get(i);
								if (schedule.getDate() != null && schedule.getDate().equals(day)) {
									if (schedule.getHour() == hour) {
										//temp = schedule;
										if (mPriceEt.getText().length() > 0)
										{
											String price = mPriceEt.getText().toString().trim();
											list.get(i).setPrice(price);
										}

										if (addressDetail !=null) {
											list.get(i).setAddressid(addressId);
											list.get(i).setAddressdetail(addressDetail);
										}
										if (subjectDetail != null) {
											list.get(i).setSubjectid(subjectId);
											list.get(i).setSubject(subjectDetail);
										}
										break;
									}
								}
							}
						}
					}
					
					for (int i=0;i<dayList.size();i++)
					{
						for (Schedule scheudle:list)
						{
							if (dayList.get(i).getHour() == scheudle.getHour())
							{
								dayList.set(i, scheudle);
								break;
							}
						}
					}
					setResult(0, new Intent().putExtra("list", dayList).putExtra("day", day));
					CommonUtils.showToast(ClassTimeSetActivity.this.getApplicationContext(), "设置成功");
					ClassTimeSetActivity.this.finish();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(ClassTimeSetActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ClassTimeSetActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(ClassTimeSetActivity.this.getApplicationContext(), ClassTimeSetActivity.this.getString(R.string.net_error));
			}
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
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("action", "SetDateTime");
			param.put("day", day);
			param.put("setjson", new Gson().toJson(getJsonObject()));
			return accessor.execute(Settings.SCHEDULE_URL, param, BaseResult.class);
		}

	}

	private List<HourJson> getJsonObject() {
		List<HourJson> hjlist = new ArrayList<ClassTimeSetActivity.HourJson>();
		for (int hour:Hour)
		{
			HourJson hj = new HourJson();
			hj.setHour(hour + "");
			Schedule temp = null;
			if (list != null) {
				for (Schedule schedule : list) {
					if (schedule.getDate() != null && schedule.getDate().equals(day)) {
						if (schedule.getHour() == hour) {
							temp = schedule;
							break;
						}
					}
				}
			}
			setHj(temp, hj);
			hjlist.add(hj);
		}
		
//		if (single != 0) {
//			HourJson hj = new HourJson();
//			hj.setHour(single + "");
//			Schedule temp = null;
//
//			if (list != null) {
//				for (Schedule schedule : list) {
//					if (schedule.getDate() != null && schedule.getDate().equals(day)) {
//						if (schedule.getHour() == single) {
//							temp = schedule;
//							break;
//						}
//					}
//				}
//			}
//			setHj(temp, hj);
//			hjlist.add(hj);
//		} else if (mor != null) {
//			for (int i = 0; i < mor.length(); i++) {
//				HourJson hj = new HourJson();
//				if (mor.charAt(i) == 49) {
//					hj.setHour(5 + i + "");
//					Schedule temp = null;
//					if (list != null) {
//						for (Schedule schedule : list) {
//							if (schedule.getDate() != null && schedule.getDate().equals(day)) {
//								if (schedule.getHour() == (5 + i)) {
//									temp = schedule;
//									break;
//								}
//							}
//						}
//					}
//					setHj(temp, hj);
//					hjlist.add(hj);
//				}
//			}
//		} else if (aft != null) {
//			for (int i = 0; i < aft.length(); i++) {
//				HourJson hj = new HourJson();
//				if (aft.charAt(i) == 49) {
//					hj.setHour(12 + i + "");
//					Schedule temp = null;
//
//					if (list != null) {
//						for (Schedule schedule : list) {
//							if (schedule.getDate() != null && schedule.getDate().equals(day)) {
//								if (schedule.getHour() == (12 + i)) {
//									temp = schedule;
//									break;
//								}
//							}
//						}
//					}
//
//					setHj(temp, hj);
//					hjlist.add(hj);
//				}
//			}
//		} else if (nig != null) {
//			for (int i = 0; i < nig.length(); i++) {
//				HourJson hj = new HourJson();
//				if (nig.charAt(i) == 49) {
//					hj.setHour(19 + i + "");
//					Schedule temp = null;
//
//					if (list != null) {
//						for (Schedule schedule : list) {
//							if (schedule.getDate() != null && schedule.getDate().equals(day)) {
//								if (schedule.getHour() == (19 + i)) {
//									temp = schedule;
//									break;
//								}
//							}
//						}
//					}
//
//					setHj(temp, hj);
//					hjlist.add(hj);
//				}
//			}
//		}

		return hjlist;
	}

	private void setHj(Schedule temp, HourJson hj) {
		if (temp != null) {
			hj.setCancelstate(temp.getCancelstate() + "");
		} else {
			hj.setCancelstate("1");
		}

		if (mPriceEt.getText().length() > 0)
			hj.setPrice(mPriceEt.getText().toString());
		else {
			if (temp != null) {
				hj.setPrice(temp.getPrice());
			}
		}

		if (addressId != 0) {
			hj.setAddressid(addressId + "");
		} else {
			if (temp != null) {
				hj.setAddressid(temp.getAddressid() + "");
			}
		}
		if (subjectId != 0) {
			hj.setSubjectid(subjectId+"");
		} else {
			if (temp != null) {
				hj.setSubjectid(temp.getSubjectid() + "");
			}
		}

		/*
		 * 1:rest 0:open
		 */
		if (isRest)
			hj.setIsrest("1");
		else {
			hj.setIsrest("0");
		}
		if (temp != null)
			hj.setState(temp.getState() + "");
		else {
			hj.setState("0");
		}
	}

	/*
	 * 获取上传数据，转成json
	 */
//	private String getJson() {
//		StringBuilder sb = new StringBuilder();
//		sb.append("[");
//		if (single != 0) {
//			/*
//			 * 单个小时设置
//			 */
//			sb.append("{\"hour\":\"" + single + "\"");
//			if (isRest)
//				sb.append(",\"isrest\":\"1\"");
//			else {
//				sb.append(",\"isrest\":\"0\"");
//			}
//			sb.append(",\"state\":\"0\"");
//			Schedule temp = null;
//
//			if (list != null) {
//				for (Schedule schedule : list) {
//					if (schedule.getDate() != null && schedule.getDate().equals(day)) {
//						if (schedule.getHour() == single) {
//							temp = schedule;
//							break;
//						}
//					}
//				}
//			}
//			if (temp != null) {
//				sb.append(",\"cancelstate\":\"" + temp.getCancelstate() + "\"");
//			} else {
//				sb.append(",\"cancelstate\":\"1\"");
//			}
//
//			if (mPriceEt.getText().length() > 0)
//				sb.append(",\"price\":\"" + mPriceEt.getText() + "\"");
//			else {
//				if (temp != null) {
//					sb.append(",\"price\":\"" + temp.getPrice() + "\"");
//				}
//			}
//
//			if (addressId != null) {
//				sb.append(",\"addressid\":\"" + addressId + "\"");
//			} else {
//				if (temp != null) {
//					sb.append(",\"addressid\":\"" + temp.getAddressid() + "\"");
//				}
//			}
//			if (subjectId != null) {
//				sb.append(",\"subjectid\":\"" + subjectId + "\"}");
//			} else {
//				if (temp != null) {
//					sb.append(",\"subjectid\":\"" + temp.getSubjectid() + "\"");
//				}
//			}
//		} else if (mor != null) {
//			/*
//			 * 早上设置
//			 */
//			getSpanJson(sb, mor, 5);
//		} else if (aft != null) {
//			/*
//			 * 下午设置
//			 */
//			getSpanJson(sb, aft, 12);
//		} else if (nig != null) {
//			/*
//			 * 晚上设置
//			 */
//			getSpanJson(sb, nig, 19);
//		}
//		sb.append("]");
//
//		return sb.toString();
//	}

//	private void getSpanJson(StringBuilder sb, String data, int index) {
//		sb.append("{\"hour\":\"");
//		StringBuilder hour = new StringBuilder();
//		for (int i = 0; i < data.length(); i++) {
//			if (data.charAt(i) == 49) {
//				hour.append(index + i);
//				hour.append(",");
//			}
//		}
//		String hourStr = hour.toString();
//		if (hourStr.contains(","))
//			hourStr = hourStr.substring(0, hourStr.length() - 1);
//		sb.append(hourStr);
//		sb.append("\"");
//		if (isRest)
//			sb.append(",\"isrest\":\"1\"");
//		else {
//			sb.append(",\"isrest\":\"0\"");
//		}
//		sb.append(",\"state\":\"0\"");
//		Schedule temp = null;
//
//		if (list != null) {
//			for (Schedule schedule : list) {
//				if (schedule.getDate() != null && schedule.getDate().equals(day)) {
//					if (schedule.getHour() == single) {
//						temp = schedule;
//						break;
//					}
//				}
//			}
//		}
//		if (temp != null) {
//			sb.append(",\"cancelstate\":\"" + temp.getCancelstate() + "\"");
//		} else {
//			sb.append(",\"cancelstate\":\"1\"");
//		}
//
//		if (mPriceEt.getText().length() > 0)
//			sb.append(",\"price\":\"" + mPriceEt.getText() + "\"");
//		else {
//			if (temp != null) {
//				sb.append(",\"price\":\"" + temp.getPrice() + "\"");
//			}
//		}
//
//		if (addressId != null) {
//			sb.append(",\"addressid\":\"" + addressId + "\"");
//		} else {
//			if (temp != null) {
//				sb.append(",\"addressid\":\"" + temp.getAddressid() + "\"");
//			}
//		}
//		if (subjectId != null) {
//			sb.append(",\"subjectid\":\"" + subjectId + "\"}");
//		} else {
//			if (temp != null) {
//				sb.append(",\"subjectid\":\"" + temp.getSubjectid() + "\"");
//			}
//		}
//	}

	public class HourJson {
		String hour;
		String state;
		String cancelstate;
		String price;
		String isrest;
		String addressid;
		String subjectid;

		public String getHour() {
			return hour;
		}

		public void setHour(String hour) {
			this.hour = hour;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public String getCancelstate() {
			return cancelstate;
		}

		public void setCancelstate(String cancelstate) {
			this.cancelstate = cancelstate;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getIsrest() {
			return isrest;
		}

		public void setIsrest(String isrest) {
			this.isrest = isrest;
		}

		public String getAddressid() {
			return addressid;
		}

		public void setAddressid(String addressid) {
			this.addressid = addressid;
		}

		public String getSubjectid() {
			return subjectid;
		}

		public void setSubjectid(String subjectid) {
			this.subjectid = subjectid;
		}

	}

	List<SubjectInfo> subjectInfos = new ArrayList<GetAllSubjectResult.SubjectInfo>();

	private class GetAllSubjectTask extends AsyncTask<Void, Void, GetAllSubjectResult> {
		JSONAccessor accessor = new JSONAccessor(ClassTimeSetActivity.this, JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected GetAllSubjectResult doInBackground(Void... params) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetAllSubject");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			return accessor.execute(Settings.CMY_URL, param, GetAllSubjectResult.class);
		}

		@Override
		protected void onPostExecute(GetAllSubjectResult result) {
			super.onPostExecute(result);

			if (result != null) {
				if (result.getCode() == 1) {
					if (result.getSubjectlist() != null && result.getSubjectlist().size() > 0) {
						subjectInfos = result.getSubjectlist();
						content = new String[result.getSubjectlist().size()];
						for (int i = 0; i < result.getSubjectlist().size(); i++) {
							content[i] = result.getSubjectlist().get(i).getSubjectname();
						}
					}
				} else {
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ClassTimeSetActivity.this);
						if (result.getMessage() != null)
							CommonUtils.showToast(ClassTimeSetActivity.this.getApplicationContext(), result.getMessage());
					}
					// if (result.getMessage() != null)
					// CommonUtils.showToast(ClassTimeSetActivity.this, result.getMessage());
				}
			} else {
				// CommonUtils.showToast(ClassTimeSetActivity.this, getString(R.string.net_error));
			}
		}
	}

	/*
	 * 获取所有地址
	 */
	List<AddressInfo> addresses = new ArrayList<AddressInfo>();

	private class GetAllAddressTask extends AsyncTask<Void, Void, AddressResult> {
		JSONAccessor accessor = new JSONAccessor(ClassTimeSetActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected AddressResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetAllAddress");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			return accessor.execute(Settings.CMY_URL, param, AddressResult.class);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected void onPostExecute(AddressResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();

			if (result != null) {
				if (result.getCode() == 1) {
					if (result.getAddresslist() != null && result.getAddresslist().size() > 0) {
						addresses = result.getAddresslist();
						locContent = new String[result.getAddresslist().size()];
						for (int i = 0; i < result.getAddresslist().size(); i++) {
							locContent[i] = result.getAddresslist().get(i).getDetail();
						}
					}
				} else {
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ClassTimeSetActivity.this);

						if (result.getMessage() != null)
							CommonUtils.showToast(ClassTimeSetActivity.this.getApplicationContext(), result.getMessage());
					}
					// if (result.getMessage() != null)
					// CommonUtils.showToast(ClassTimeSetActivity.this.getApplicationContext(), result.getMessage());
				}
			} else {
			}
		}
	}
}
