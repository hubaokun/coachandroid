package xiaoba.coach.activity;

import java.util.Calendar;
import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.UserInfo;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.views.BirthdayDialog;
import xiaoba.coach.views.GenderDialog;
import xiaoba.coach.views.WheelCityDialog;
import xiaoba.coach.views.BirthdayDialog.OnComfirmClickListener;

import com.baidu.mapapi.map.InfoWindow;
import com.daoshun.lib.communication.http.JSONAccessor;

import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@EActivity(R.layout.activity_personal_info)
public class PersonalInfoActivity extends BaseActivity {
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
//	@ViewById(R.id.pencil_sex)
//	ImageView mPencilSex;
	@ViewById(R.id.pencil_city)
	ImageView mPencilCity;
	@ViewById(R.id.pencil_birthday)
	ImageView mPencilBirthday;
	// @ViewById(R.id.pencil_scholl_name)
	// ImageView mPencilSchoolName;
	@ViewById(R.id.pencil_contact_loc)
	ImageView mPencilContactLoc;
	@ViewById(R.id.pencil_contact_name)
	ImageView mPencilContactName;
	@ViewById(R.id.pencil_contact_phone)
	ImageView mPencilContactPhone;
	@ViewById(R.id.pencil_teach_age)
	ImageView mPencilTeachAge;
	@ViewById(R.id.pencil_self_eval)
	ImageView mPencilSelfEval;
	@ViewById(R.id.tv_birthday)
	TextView mBirthTv;
	// @ViewById(R.id.gender_tv)
	// TextView mGenderTv;
	@ViewById(R.id.city_tv)
	TextView mCityTv;
	// @ViewById(R.id.enter_school_name)
	// EditText mSchoolName;
	@ViewById(R.id.enter_contact_loc)
	EditText mContactLoc;
	@ViewById(R.id.enter_contact_name)
	EditText mContactName;
	@ViewById(R.id.enter_contact_phone)
	EditText mContactPhone;
	@ViewById(R.id.enter_teach_age)
	EditText mTeachAge;
	@ViewById(R.id.enter_self_eval)
	EditText mSelfEval;

	BirthdayDialog mBirthdayDialog;
	WheelCityDialog mWheelCityDialog;
	// GenderDialog mGenderDialog;
	boolean isClickable, hasGender, hasDate, hasCity;
	UserInfo userInfo;
	private String provinceId,cityId,zoneId;

	@AfterViews
	void init() {
		mTitle.setText("个人资料");
		mTitleBack.setImageResource(R.drawable.back_arrow);
		mTitle.setTextColor(Color.parseColor("#2c2c2c"));
		mTitleRightTv.setText("提交资料");
		// mTitleRightTv.setTextColor(Color.parseColor("#d2d2d2"));
		// mTitleRightTv.setClickable(false);
		mTitleRightTv.setTextColor(Color.parseColor("#2c2c2c"));
		mTitleRightTv.setVisibility(View.VISIBLE);

		mBirthdayDialog = new BirthdayDialog(this);
		mBirthdayDialog.setOnComfirmClickListener(new OnComfirmClickListener() {

			@Override
			public void onComfirmBtnClick(int year, int month, int day) {
				/*
				 * 判断日期
				 */
				Calendar today = Calendar.getInstance();
				boolean deadTime = false;
				if (year > today.get(Calendar.YEAR)) {
					deadTime = true;
				} else if (year == today.get(Calendar.YEAR)) {
					if (month > today.get(Calendar.MONTH)) {
						deadTime = true;
					} else if (month == today.get(Calendar.MONTH) && day > today.get(Calendar.DAY_OF_MONTH)) {
						deadTime = true;
					}
				}

				if (deadTime) {
					Toast.makeText(PersonalInfoActivity.this, "请选择今天以前的日期", Toast.LENGTH_SHORT).show();
					return;
				}
				/*
				 * 显示修改后的日期
				 */
				String monthStr = month < 10 ? "0" + month : "" + month;
				String dayStr = day < 10 ? "0" + day : "" + day;
				mBirthTv.setTextColor(Color.parseColor("#252525"));
				mBirthTv.setText(year + "-" + monthStr + "-" + dayStr);
				hasDate = true;
				setClickable();
			}
		});

		mWheelCityDialog = new WheelCityDialog(this);
		mWheelCityDialog.setOnComfirmClickListener(new WheelCityDialog.OnComfirmClickListener() {

			@Override
			public void onComfirmBtnClick(String province, String city,String zone,String provinceid,String cityid,String zoneid) {
				mCityTv.setText(province + " " + city+"	"+zone);
				mCityTv.setTextColor(Color.parseColor("#252525"));
				hasCity = true;
				provinceId = provinceid;
				cityId = cityid;
				zoneId = zoneid;
				setClickable();
			}
		});
		// mGenderDialog = new GenderDialog(this);
		// mGenderDialog.setOnComfirmClickListener(new GenderDialog.OnComfirmClickListener() {
		//
		// @Override
		// public void onComfirmBtnClick(String gender) {
		// mGenderDialog.dismiss();
		// mGenderTv.setText(gender);
		// mGenderTv.setTextColor(Color.parseColor("#252525"));
		// hasGender = true;
		// setClickable();
		// }
		// });

		userInfo = CoachApplication.getInstance().getUserInfo();

		setLocalInfo();

		addListeners();
	}

	private void addListeners() {
		// mSchoolName.addTextChangedListener(new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// }
		//
		// @Override
		// public void afterTextChanged(Editable arg0) {
		// if (userInfo.getDrive_school() != null) {
		// if (!arg0.toString().equals(userInfo.getDrive_school())) {
		// mPencilSchoolName.setImageResource(R.drawable.pencil_color);
		// } else {
		// mPencilSchoolName.setImageResource(R.drawable.pencile);
		// }
		//
		// } else {
		// if (arg0.length() > 0) {
		// mPencilSchoolName.setImageResource(R.drawable.pencil_color);
		// } else {
		// mPencilSchoolName.setImageResource(R.drawable.pencile);
		// }
		// }
		// }
		// });
		mContactLoc.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (userInfo.getAddress() != null) {
					if (!arg0.toString().equals(userInfo.getAddress())) {
						mPencilContactLoc.setImageResource(R.drawable.pencil_color);
					} else {
						mPencilContactLoc.setImageResource(R.drawable.pencile);
					}

				} else {
					if (arg0.length() > 0) {
						mPencilContactLoc.setImageResource(R.drawable.pencil_color);
					} else {
						mPencilContactLoc.setImageResource(R.drawable.pencile);
					}
				}
			}
		});
		mContactName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (userInfo.getUrgent_person() != null) {
					if (!arg0.toString().equals(userInfo.getUrgent_person())) {
						mPencilContactName.setImageResource(R.drawable.pencil_color);
					} else {
						mPencilContactName.setImageResource(R.drawable.pencile);
					}

				} else {
					if (arg0.length() > 0) {
						mPencilContactName.setImageResource(R.drawable.pencil_color);
					} else {
						mPencilContactName.setImageResource(R.drawable.pencile);
					}
				}
			}
		});
		mContactPhone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (userInfo.getUrgent_phone() != null) {
					if (!arg0.toString().equals(userInfo.getUrgent_phone())) {
						mPencilContactPhone.setImageResource(R.drawable.pencil_color);
					} else {
						mPencilContactPhone.setImageResource(R.drawable.pencile);
					}

				} else {
					if (arg0.length() > 0) {
						mPencilContactPhone.setImageResource(R.drawable.pencil_color);
					} else {
						mPencilContactPhone.setImageResource(R.drawable.pencile);
					}
				}
			}
		});

		mTeachAge.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (userInfo.getYears() != null) {
					if (!arg0.toString().equals(userInfo.getYears())) {
						mPencilTeachAge.setImageResource(R.drawable.pencil_color);
					} else {
						mPencilTeachAge.setImageResource(R.drawable.pencile);
					}

				} else {
					if (arg0.length() > 0) {
						mPencilTeachAge.setImageResource(R.drawable.pencil_color);
					} else {
						mPencilTeachAge.setImageResource(R.drawable.pencile);
					}
				}
			}
		});

		mSelfEval.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (userInfo.getSelfeval() != null) {
					if (!arg0.toString().equals(userInfo.getSelfeval())) {
						mPencilSelfEval.setImageResource(R.drawable.pencil_color);
					} else {
						mPencilSelfEval.setImageResource(R.drawable.pencile);
					}

				} else {
					if (arg0.length() > 0) {
						mPencilSelfEval.setImageResource(R.drawable.pencil_color);
					} else {
						mPencilSelfEval.setImageResource(R.drawable.pencile);
					}
				}
			}
		});
	}

	private void setLocalInfo() {
		UserInfo info = CoachApplication.getInstance().getUserInfo();
		// if (info.getDrive_school() != null) {
		// mSchoolName.setText(info.getDrive_school());
		// }
		// if (info.getGender() != null) {
		// if (info.getGender().equals("1"))
		// mGenderTv.setText("男");
		// else if (info.getGender().equals("2"))
		// mGenderTv.setText("女");
		// }
		if (!TextUtils.isEmpty(info.getBirthday())) {
			mBirthTv.setText(info.getBirthday());
			mBirthTv.setTextColor(Color.parseColor("#2c2c2c"));
		} else {
			mBirthTv.setText("请选择出生年月");
			mBirthTv.setTextColor(Color.parseColor("#a4abbd"));
		}
		if (!TextUtils.isEmpty(info.getLocationname())) {
			mCityTv.setText(info.getLocationname());
			mCityTv.setTextColor(Color.parseColor("#2c2c2c"));
		} else {
			mCityTv.setText("请选择所在城市");
			mCityTv.setTextColor(Color.parseColor("#a4abbd"));
		}
		if (info.getAddress() != null) {
			mContactLoc.setText(info.getAddress());
			mContactLoc.setSelection(info.getAddress().length());
		}
		if (info.getUrgent_person() != null) {
			mContactName.setText(info.getUrgent_person());
		}
		if (info.getUrgent_phone() != null) {
			mContactPhone.setText(info.getUrgent_phone());
		}
		if (info.getYears() != null) {
			mTeachAge.setText(info.getYears());
		}
		if (info.getSelfeval() != null) {
			mSelfEval.setText(info.getSelfeval());
		}
	}

	@Click(R.id.title_back)
	void returnLastPage() {
		finish();
	}

	@Click(R.id.title_right_text)
	void submit() {
		new PerfectCoachInfoTask().execute();
	}

	// @Click(R.id.pencil_sex)
	// void showSelectSex() {
	// mGenderDialog.show();
	// }
	//
	// @Click(R.id.gender_tv)
	// void wuyan1() {
	// mGenderDialog.show();
	// }

	@Click(R.id.pencil_city)
	void showSelectCity() {
		mWheelCityDialog.show();
	}

	@Click(R.id.city_tv)
	void wuyan2() {
		mWheelCityDialog.show();
	}

	@Click(R.id.pencil_birthday)
	void showSelectBirthDay() {
		mBirthdayDialog.show();
	}

	@Click(R.id.tv_birthday)
	void wuyan3() {
		mBirthdayDialog.show();
	}

	// @Click(R.id.pencil_scholl_name)
	// void showSchoolName() {
	// // mPencilSchoolName.setImageResource(R.drawable.pencile_grey);
	// mSchoolName.setFocusable(true);
	// mSchoolName.setFocusableInTouchMode(true);
	// mSchoolName.requestFocus();
	// setClickable();
	// }

	@Click(R.id.pencil_contact_loc)
	void showContactLoc() {
		// mPencilContactLoc.setImageResource(R.drawable.pencile_grey);
		mContactLoc.setFocusable(true);
		mContactLoc.setFocusableInTouchMode(true);
		mContactLoc.requestFocus();
		setClickable();
	}

	@Click(R.id.pencil_contact_name)
	void showContactName() {
		// mPencilContactName.setImageResource(R.drawable.pencile_grey);
		mContactName.setFocusable(true);
		mContactName.setFocusableInTouchMode(true);
		setClickable();
	}

	@Click(R.id.pencil_contact_phone)
	void showContactPhone() {
		// mPencilContactPhone.setImageResource(R.drawable.pencile_grey);
		mContactPhone.setFocusable(true);
		mContactPhone.setFocusableInTouchMode(true);
		mContactPhone.requestFocus();
		setClickable();
	}

	@Click(R.id.pencil_teach_age)
	void showTeachAge() {
		// mPencilTeachAge.setImageResource(R.drawable.pencile_grey);
		mTeachAge.setFocusable(true);
		mTeachAge.setFocusableInTouchMode(true);
		mTeachAge.requestFocus();
		setClickable();
	}

	@Click(R.id.pencil_self_eval)
	void showSelfEval() {
		// mPencilSelfEval.setImageResource(R.drawable.pencile_grey);
		mSelfEval.setFocusable(true);
		mSelfEval.setFocusableInTouchMode(true);
		mSelfEval.requestFocus();
		setClickable();
	}

	void setClickable() {
		if (!mTitleRightTv.isClickable()) {
			mTitleRightTv.setTextColor(Color.parseColor("#2c2c2c"));
			mTitleRightTv.setClickable(true);
		}
	}

	/*
	 * 
	 */
	private class PerfectCoachInfoTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(PersonalInfoActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			accessor.enableJsonLog(true);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					/*
					 * 保存本地信息
					 */
					UserInfo info = CoachApplication.getInstance().getUserInfo();
					// if (mSchoolName.isFocusable())
					// info.setDrive_school(mSchoolName.getText().toString());
					if (mContactLoc.isFocusable())
						info.setAddress(mContactLoc.getText().toString());
					if (mContactName.isFocusable())
						info.setUrgent_person(mContactName.getText().toString());
					if (mContactPhone.isFocusable())
						info.setUrgent_phone(mContactPhone.getText().toString());
					if (mTeachAge.isFocusable())
						info.setYears(mTeachAge.getText().toString());
					if (mSelfEval.isFocusable())
						info.setSelfeval(mSelfEval.getText().toString());
					// if (hasGender)
					// info.setGender(mGenderTv.getText().toString());
					if (hasDate)
						info.setBirthday(mBirthTv.getText().toString());
					if (hasCity)
						info.setLocationname(mCityTv.getText().toString());

					info.saveUserInfo(info, PersonalInfoActivity.this);
					//
					CommonUtils.showToast(PersonalInfoActivity.this.getApplicationContext(), "修改个人资料成功");
					PersonalInfoActivity.this.finish();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(PersonalInfoActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(PersonalInfoActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(PersonalInfoActivity.this.getApplicationContext(), getString(R.string.net_error));
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
			HashMap<String, Object> param = new BaseParam();
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("action", "PerfectPersonInfo");
			// if (mSchoolName.isFocusable())
			// param.put("driveschool", mSchoolName.getText().toString());
			if (mContactLoc.isFocusable())
				param.put("address", mContactLoc.getText().toString());
			if (mContactName.isFocusable())
				param.put("urgentperson", mContactName.getText().toString());
			if (mContactPhone.isFocusable())
				param.put("urgentphone", mContactPhone.getText().toString());
			if (mTeachAge.isFocusable())
				param.put("years", mTeachAge.getText().toString());
			if (mSelfEval.isFocusable())
				param.put("selfeval", mSelfEval.getText().toString());
			// if (hasGender)
			// param.put("gender", mGenderTv.getText().toString());
			if (hasDate)
				param.put("birthday", mBirthTv.getText().toString());
			if (hasCity)
				param.put("cityid", cityId);
				param.put("provinceid",provinceId);
				param.put("areaid", zoneId);
			return accessor.execute(Settings.USER_URL, param, BaseResult.class);
		}
	}

}
