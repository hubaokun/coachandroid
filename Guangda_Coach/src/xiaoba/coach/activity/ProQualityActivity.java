package xiaoba.coach.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import xiaoba.coach.module.CarType;
import xiaoba.coach.module.School;
import xiaoba.coach.module.UserInfo;
import xiaoba.coach.net.request.PerfectCoachInfoReq;
import xiaoba.coach.net.result.GetCarModelResult;
import xiaoba.coach.net.result.GetCarModelResult.Model;
import xiaoba.coach.net.result.GetCarTypeResult;
import xiaoba.coach.net.result.GetCarTypeResult.CarModel;
import xiaoba.coach.net.result.GetCoachState;
import xiaoba.coach.net.result.GetSchoolResult;
import xiaoba.coach.net.result.GetTeachCarModelResult.TeachCar;
import xiaoba.coach.net.result.PerfectCoachInfoResult;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.utils.DialogUtil;
import xiaoba.coach.views.SelectDialog;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.util.FileUtils;
import com.daoshun.lib.view.OnSingleClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

@EActivity(R.layout.activity_pro_quality)
public class ProQualityActivity extends BaseActivity {
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	// @ViewById(R.id.pencil_id)
	// ImageView mPencilId;
	// @ViewById(R.id.pencil_coach)
	// ImageView mPencilCoach;
	@ViewById(R.id.pencil_scholl_name)
	ImageView imgSchoolName;
	@ViewById(R.id.tv_coach_state)
	TextView tvCoachState;
	// @ViewById(R.id.pencil_cartype_one)
	// ImageView mPencilCartype1;
	@ViewById(R.id.pencil_cartype_two)
	ImageView mPencilCartype2;
	// @ViewById(R.id.pencil_time)
	// ImageView mPencilTime;
	// @ViewById(R.id.photo_id_front)
	// LinearLayout mPhotoIdFront;
	// @ViewById(R.id.photo_id_back)
	// LinearLayout mPhotoIdBack;
	@ViewById(R.id.photo_coach)
	LinearLayout mPhotoCoachCard;
	@ViewById(R.id.photo_coach_car)
	LinearLayout mPhotoCar;
	@ViewById(R.id.check_c1)
	CheckBox cbC1;
	@ViewById(R.id.check_c2)
	CheckBox cbC2;
	/*
	 * @ViewById(R.id.enter_id) EditText mId;
	 * 
	 * @ViewById(R.id.enter_coach_id) EditText mCoachId;
	 */
	// @ViewById(R.id.enter_cartype_one)
	// TextView mCarType1;
	// @ViewById(R.id.enter_card_license)
	// EditText mCardLicnese;
	// @ViewById(R.id.enter_drive_num)
	// // 驾驶证
	// EditText mDrivenum;
	// @ViewById(R.id.enter_car_num)
	// // 车辆行驶证
	// EditText mCarNum;
	// @ViewById(R.id.enter_car_model)
	// // 模型
	// TextView mCarModel;
	@ViewById(R.id.enter_school_name_et)
	EditText mDriveSchoolEt;
	// @ViewById(R.id.add_cartype)
	// ImageView mAdd;
	// @ViewById(R.id.photo_id_front_pic)
	// ImageView mPicIdFront;
	// @ViewById(R.id.photo_id_back_pic)
	// ImageView mPicIdBack;
	@ViewById(R.id.photo_coach_pic)
	ImageView mPicCoach;
	@ViewById(R.id.photo_coach_car_pic)
	ImageView mPicDriveCar;
	@ViewById(R.id.photo_coach_car_front_pic)
	ImageView mPicCarFront;
	@ViewById(R.id.photo_coach_car_back_pic)
	ImageView mPicCarBack;
	// @ViewById(R.id.photo_coach_face_pic)
	// ImageView mPicCoachFace;
	@ViewById(R.id.enter_school_name)
	TextView mSchoolName;
	// @ViewById(R.id.rl_school)
	// RelativeLayout rlSchool;
	// @ViewById(R.id.del_id_front)
	// ImageView mDelIdFront;
	// @ViewById(R.id.del_id_back)
	// ImageView mDelIdBack;
	@ViewById(R.id.del_coach)
	ImageView mDelCoachCard;
	@ViewById(R.id.del_coach_car)
	ImageView mDelDriveCard;
	@ViewById(R.id.del_coach_car_front)
	ImageView mDelCarFront;
	@ViewById(R.id.del_coach_car_back)
	ImageView mDelCarBack;
	// @ViewById(R.id.del_coach_face)
	// ImageView mDelCoachFace;
	// @ViewById(R.id.photo_coach_face)
	// LinearLayout mPhotoCoachFace;
	@ViewById(R.id.photo_coach_car_back)
	LinearLayout mPhotoCarBack;
	@ViewById(R.id.photo_car_front)
	LinearLayout mPhotoCarFront;
	@ViewById(R.id.rl_school)
	RelativeLayout rlSchool;
	// @ViewById(R.id.ll_cartype)
	// LinearLayout mCartypeLayout;
	// @ViewById(R.id.pencil_drivecard)
	// ImageView mPencilDriveCard;
	// @ViewById(R.id.pencil_carcard)
	// ImageView mPencilCarCard;
	/*
	 * 身份证到期时间
	 */
	// @ViewById(R.id.enter_id_invalid_time)
	// TextView mIdInvalidTime;
	// /*
	// * 教练证到期时间
	// */
	// @ViewById(R.id.enter_coach_invalid_time)
	// TextView mCoachInvalidTime;
	/*
	 * 驾驶证到期时间
	 */
	// @ViewById(R.id.enter_drive_invalid_time)
	// TextView mDriveInvalidTime;
	// /*
	// * 车辆行驶证到期时间
	// */
	// @ViewById(R.id.enter_car_invalid_time)
	// TextView mCarInvalidTime;

	// BirthdayDialog mBirthdayDialog;

	private static final int CAMERA_REQUEST_CODE_1 = 2001;
	private static final int CAMERA_REQUEST_CODE_2 = 2002;
	private static final int CAMERA_REQUEST_CODE_3 = 2003;
	private static final int CAMERA_REQUEST_CODE_4 = 2004;
	private static final int CAMERA_REQUEST_CODE_5 = 2005;
	private static final int CAMERA_REQUEST_CODE_6 = 2006;
	private static final int CAMERA_REQUEST_CODE_7 = 2007;
	private static final int ALBUM_REQUEST_CODE_1 = 2008;
	private static final int ALBUM_REQUEST_CODE_2 = 2009;
	private static final int ALBUM_REQUEST_CODE_3 = 2010;
	private static final int ALBUM_REQUEST_CODE_4 = 2011;
	private static final int ALBUM_REQUEST_CODE_5 = 2012;
	private static final int ALBUM_REQUEST_CODE_6 = 2013;
	private static final int ALBUM_REQUEST_CODE_7 = 2014;
	private static final int CROP_REQUEST_CODE_1 = 2015;
	private static final int CROP_REQUEST_CODE_2 = 2016;
	private static final int CROP_REQUEST_CODE_3 = 2017;
	private static final int CROP_REQUEST_CODE_4 = 2018;
	private static final int CROP_REQUEST_CODE_5 = 2019;
	private static final int CROP_REQUEST_CODE_6 = 2020;
	private static final int CROP_REQUEST_CODE_7 = 2021;
	private File file1, file2, file3, file4, file5, file6, file7;

	private String CROP_TEMP_FILE;// 裁剪的时候输出文件名称
	private String PHOTO_TEMP_FILE; // 相机拍照的输出文件名称
	private String photoname;
	private String CoachState;
	Bitmap avater;
	File cropFile;
	int selectType;
	boolean hasTime1, hasTime2, hasTime3, hasTime4;
	List<String> selectmodels = new ArrayList<String>();
	List<Model> models = new ArrayList<GetCarModelResult.Model>();
	// SelectDialog CarTypeDialog, CarModelDialog;
	UserInfo info;
	private List<String> carType = new ArrayList<String>();
	private List<String> carModel = new ArrayList<String>();
	private List<String> getSchool = new ArrayList<String>();
	private SelectDialog schoolDialog;

	@AfterViews
	void init() {
		//new GetAllSchool().execute();
		// new GetCarModelList().execute();
		// new GetTeachCarList().execute();
		mTitle.setText("教练资格资料");
		mTitleBack.setImageResource(R.drawable.back_arrow);
		mTitle.setTextColor(Color.parseColor("#2c2c2c"));
		mTitleRightTv.setText("提交资料");
		// mTitleRightTv.setTextColor(Color.parseColor("#d2d2d2"));
		// mTitleRightTv.setClickable(false);
		mTitleRightTv.setTextColor(Color.parseColor("#2c2c2c"));
		mTitleRightTv.setVisibility(View.VISIBLE);

		// mBirthdayDialog = new BirthdayDialog(this);
		// mBirthdayDialog.setOnComfirmClickListener(new OnComfirmClickListener() {
		//
		// @Override
		// public void onComfirmBtnClick(int year, int month, int day) {
		// String monthStr = month < 10 ? "0" + month : "" + month;
		// String dayStr = day < 10 ? "0" + day : "" + day;
		// switch (selectType) {
		// case 1:
		// mIdInvalidTime.setTextColor(Color.parseColor("#252525"));
		// mIdInvalidTime.setText(year + "-" + monthStr + "-" + dayStr);
		// hasTime1 = true;
		// break;
		// case 2:
		// mCoachInvalidTime.setTextColor(Color.parseColor("#252525"));
		// mCoachInvalidTime.setText(year + "-" + monthStr + "-" + dayStr);
		// hasTime2 = true;
		// break;
		// case 3:
		// mDriveInvalidTime.setTextColor(Color.parseColor("#252525"));
		// mDriveInvalidTime.setText(year + "-" + monthStr + "-" + dayStr);
		// hasTime3 = true;
		// break;
		// case 4:
		// mCarInvalidTime.setTextColor(Color.parseColor("#252525"));
		// mCarInvalidTime.setText(year + "-" + monthStr + "-" + dayStr);
		// hasTime4 = true;
		// break;
		//
		// default:
		// break;
		// }
		// setClickable();
		// }
		// });

		info = CoachApplication.getInstance().getUserInfo();

		new PerfectAccountInfoTask().execute();

		/*
		 * 取carmodels
		 */
		// new GetCarModelTask().execute();
	}

	private void setListeners() {
		/*
		 * if (info.getState()==null) { rlSchool.setClickable(true); mDelCoachCard.setClickable(true); mDelDriveCard.setClickable(true); mDelCarFront.setClickable(true);
		 * mDelCarBack.setClickable(true); }else{
		 */
		if ("1".equals(info.getState()) || "2".equals(info.getState())) // 1：待审核 2：审核通过 3：审核未通过 0:还未注册
		{
			// rlSchool.setClickable(false);
			// mDelCoachCard.setClickable(false);
			// mDelDriveCard.setClickable(false);
			// mDelCarFront.setClickable(false);
			// mDelCarBack.setClickable(false);
			// showStateDialog(info.getState());
			imgSchoolName.setVisibility(View.GONE);
			// mDelCarBack.setVisibility(View.GONE);
			// mDelCarFront.setVisibility(View.GONE);
			// mDelDriveCard.setVisibility(View.GONE);
			// mDelCoachCard.setVisibility(View.GONE);
		} else {
			imgSchoolName.setVisibility(View.VISIBLE);
			// mDelCoachCard.setClickable(true);
			// mDelDriveCard.setClickable(true);
			// mDelCarFront.setClickable(true);
			// mDelCarBack.setClickable(true);
		}
	}

	/* } */

	private void showStateDialog(String state) {
		DialogUtil dUtil = new DialogUtil(new DialogConfirmListener() {

			@Override
			public void doConfirm(String str) {
				// TODO Auto-generated method stub
				if (mDialog != null) {
					mDialog.dismiss();
				}
			}

			@Override
			public void doCancel() {
				// TODO Auto-generated method stub
				if (mDialog != null) {
					mDialog.dismiss();
				}
			}
		});
		if ("1".equals(state)) {
			mDialog = dUtil.CallConfirmDialog("您提交的资料正在审核中，不能修改！","","",ProQualityActivity.this, mDialog);
		} else {
			mDialog = dUtil.CallConfirmDialog("您所提交的资料已审核通过，不能修改。若要修改，请联系客服","","", ProQualityActivity.this, mDialog);
		}
	}

	private void addListeners() {
		// mId.addTextChangedListener(new TextWatcher() {
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
		// if (info.getId_cardnum() != null) {
		// if (!arg0.toString().equals(info.getId_cardnum()))
		// mPencilId.setImageResource(R.drawable.pencil_color);
		// else {
		// mPencilId.setImageResource(R.drawable.pencile);
		// }
		// } else {
		// if (arg0.length() > 0)
		// mPencilId.setImageResource(R.drawable.pencil_color);
		// else {
		// mPencilId.setImageResource(R.drawable.pencile);
		// }
		// }
		// }
		// });
		// mCoachId.addTextChangedListener(new TextWatcher() {
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
		// if (info.getCoach_cardnum() != null) {
		// if (!arg0.toString().equals(info.getCoach_cardnum()))
		// mPencilCoach.setImageResource(R.drawable.pencil_color);
		// else {
		// mPencilCoach.setImageResource(R.drawable.pencile);
		// }
		// } else {
		// if (arg0.length() > 0)
		// mPencilCoach.setImageResource(R.drawable.pencil_color);
		// else {
		// mPencilCoach.setImageResource(R.drawable.pencile);
		// }
		// }
		// }
		// });
		// mDrivenum.addTextChangedListener(new TextWatcher() {
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
		// if (info.getDrive_cardnum() != null) {
		// if (!arg0.toString().equals(info.getDrive_cardnum()))
		// mPencilDriveCard.setImageResource(R.drawable.pencil_color);
		// else {
		// mPencilDriveCard.setImageResource(R.drawable.pencile);
		// }
		// } else {
		// if (arg0.length() > 0)
		// mPencilDriveCard.setImageResource(R.drawable.pencil_color);
		// else {
		// mPencilDriveCard.setImageResource(R.drawable.pencile);
		// }
		// }
		// }
		// });
		// mCarNum.addTextChangedListener(new TextWatcher() {
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
		// if (info.getCar_cardnum() != null) {
		// if (!arg0.toString().equals(info.getCar_cardnum()))
		// mPencilCarCard.setImageResource(R.drawable.pencil_color);
		// else {
		// mPencilCarCard.setImageResource(R.drawable.pencile);
		// }
		// } else {
		// if (arg0.length() > 0)
		// mPencilCarCard.setImageResource(R.drawable.pencil_color);
		// else {
		// mPencilCarCard.setImageResource(R.drawable.pencile);
		// }
		// }
		// }
		// });
		// mCardLicnese.addTextChangedListener(new TextWatcher() {
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
		// if (info.getCarlicense() != null) {
		// if (!arg0.toString().equals(info.getCarlicense()))
		// mPencilTime.setImageResource(R.drawable.pencil_color);
		// else {
		// mPencilTime.setImageResource(R.drawable.pencile);
		// }
		// } else {
		// if (arg0.length() > 0)
		// mPencilTime.setImageResource(R.drawable.pencil_color);
		// else {
		// mPencilTime.setImageResource(R.drawable.pencile);
		// }
		// }
		// }
		// });
	}

	private void setLocalInfo() {
		UserInfo info = CoachApplication.getInstance().getUserInfo();

		if (info.getState() != null) {
			if ("2".equals(info.getState().toString())) {
				mTitleRightTv.setVisibility(View.GONE);
				tvCoachState.setText("您已经通过教练资格认证");
			}

			if ("1".equals(info.getState().toString())) {
				mTitleRightTv.setVisibility(View.GONE);
				tvCoachState.setText("教练资格资料提交成功，正在审核中");
			}

			if ("3".equals(info.getState().toString())) {
				mTitleRightTv.setVisibility(View.VISIBLE);
				tvCoachState.setText("教练资格认证未通过，请完善并确认信息，重新提交认证");
			}

			if ("0".equals(info.getState().toString())) {
				tvCoachState.setText("教练资格认证后，学员才能预约您学车!");
			}
			// Toast.makeText(mApplication, info.getState().toString(), 0).show();
		} else {
			tvCoachState.setText("教练资格认证后，学员才能预约您学车!");
			// mId.setSelection(info.getId_cardnum().length());
		}

		// if (!TextUtils.isEmpty(info.getId_cardnum())) {
		// mId.setText(info.getId_cardnum());
		// }
		// if (!TextUtils.isEmpty(info.getId_cardexptime())) {
		// mIdInvalidTime.setText(info.getId_cardexptime());
		// mIdInvalidTime.setTextColor(Color.parseColor("#252525"));
		// }
		// if (!TextUtils.isEmpty(info.getCoach_cardnum())) {
		// mCoachId.setText(info.getCoach_cardnum());
		// }
		// if (!TextUtils.isEmpty(info.getCoach_cardexptime())) {
		// mCoachInvalidTime.setText(info.getCoach_cardexptime());
		// mCoachInvalidTime.setTextColor(Color.parseColor("#252525"));
		// }
		// if (!TextUtils.isEmpty(info.getDrive_cardnum())) {
		// mDrivenum.setText(info.getDrive_cardnum());
		// }
		// if (!TextUtils.isEmpty(info.getDrive_cardexptime())) {
		// mDriveInvalidTime.setText(info.getDrive_cardexptime());
		// mDriveInvalidTime.setTextColor(Color.parseColor("#252525"));
		// }
		// if (!TextUtils.isEmpty(info.getCar_cardnum())) {
		// mCarNum.setText(info.getCar_cardnum());
		// }
		// if (!TextUtils.isEmpty(info.getCar_cardexptime())) {
		// mCarInvalidTime.setText(info.getCar_cardexptime());
		// mCarInvalidTime.setTextColor(Color.parseColor("#252525"));
		// }
		if (!TextUtils.isEmpty(info.getDriveschool())) {
			mSchoolName.setText(info.getDriveschool());
			mSchoolName.setTextColor(Color.parseColor("#252525"));
		}
		// if (!TextUtils.isEmpty(info.getCarmodel())) {
		// mCarModel.setText(info.getCarmodel());
		// mCarModel.setTextColor(getResources().getColor(R.color.text_black));
		// }
		// if (!TextUtils.isEmpty(info.getCarlicense())) {
		// mCardLicnese.setText(info.getCarlicense());
		// }
		if (info.getModellist() != null && info.getModellist().size() > 0) {
			/*
			 * int count = info.getModellist().size(); if (count == 1) { selectmodels.add(info.getModellist().get(0).getModelname()); mCarType1.setText(info.getModellist().get(0).getModelname());
			 * mCarType1.setTextColor(getResources().getColor(R.color.text_black)); } else { for (int i = 0; i < count; i++) { selectmodels.add(info.getModellist().get(i).getModelname()); TextView tv
			 * = new TextView(ProQualityActivity.this); tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(ProQualityActivity.this, 30)));
			 * tv.setText(info.getModellist().get(i).getModelname()); tv.setTextSize(18); mCartypeLayout.addView(tv); } }
			 */
			for (CarType cartype:info.getModellist())
			{
				switch (cartype.getModelid()) {
				case 17:
					cbC1.setChecked(true);
					break;
				case 18:
					cbC2.setChecked(true);
					break;
				default:
					break;
				}
			}
				
			selectmodels.add(info.getModellist().get(0).getModelname());
			// mCarType1.setTextColor(getResources().getColor(R.color.text_black));
			// mCarType1.setText(info.getModellist().get(0).getModelname());
		}
		DisplayImageOptions options;
		ImageSize mImageSize;
		// 显示图片的配置
		options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
		mImageSize = new ImageSize(200, 200);

		// ImageLoader.getInstance().loadImage(info.getId_cardpicfurl(), mImageSize, options, new ImageLoadingListener() {
		// @Override
		// public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		// if (loadedImage != null) {
		// mPicIdFront.setImageBitmap(loadedImage);
		// mPicIdFront.setVisibility(View.VISIBLE);
		// mPhotoIdFront.setVisibility(View.INVISIBLE);
		// mDelIdFront.setVisibility(View.VISIBLE);
		// }
		// }
		//
		// @Override
		// public void onLoadingStarted(String imageUri, View view) {
		// }
		//
		// @Override
		// public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
		// }
		//
		// @Override
		// public void onLoadingCancelled(String imageUri, View view) {
		// }
		// });
		// ImageLoader.getInstance().loadImage(info.getId_cardpicburl(), mImageSize, options, new ImageLoadingListener() {
		// @Override
		// public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		// if (loadedImage != null) {
		// mPicIdBack.setImageBitmap(loadedImage);
		// mPicIdBack.setVisibility(View.VISIBLE);
		// mPhotoIdBack.setVisibility(View.INVISIBLE);
		// mDelIdBack.setVisibility(View.VISIBLE);
		// }
		// }
		//
		// @Override
		// public void onLoadingStarted(String imageUri, View view) {
		// }
		//
		// @Override
		// public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
		// }
		//
		// @Override
		// public void onLoadingCancelled(String imageUri, View view) {
		// }
		// });
		ImageLoader.getInstance().loadImage(info.getCoach_cardpicurl(), mImageSize, options, new ImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (loadedImage != null) {
					loadedImage = centerSquareScaleBitmap(loadedImage, 109);
					mPicCoach.setImageBitmap(loadedImage);
					mPicCoach.setVisibility(View.VISIBLE);
					mPhotoCoachCard.setVisibility(View.INVISIBLE);
					mDelCoachCard.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onLoadingStarted(String imageUri, View view) {
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
			}
		});
		ImageLoader.getInstance().loadImage(info.getDrive_cardpicurl(), mImageSize, options, new ImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (loadedImage != null) {
					loadedImage = centerSquareScaleBitmap(loadedImage, 109);
					mPicDriveCar.setImageBitmap(loadedImage);
					mPicDriveCar.setVisibility(View.VISIBLE);
					mPhotoCar.setVisibility(View.INVISIBLE);
					mDelDriveCard.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onLoadingStarted(String imageUri, View view) {
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
			}
		});
		ImageLoader.getInstance().loadImage(info.getCar_cardpicfurl(), mImageSize, options, new ImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (loadedImage != null) {
					loadedImage = centerSquareScaleBitmap(loadedImage, 109);
					mPicCarFront.setImageBitmap(loadedImage);
					mPicCarFront.setVisibility(View.VISIBLE);
					mPhotoCarFront.setVisibility(View.INVISIBLE);
					mDelCarFront.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onLoadingStarted(String imageUri, View view) {
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
			}
		});
		ImageLoader.getInstance().loadImage(info.getCar_cardpicburl(), mImageSize, options, new ImageLoadingListener() {
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (loadedImage != null) {
					loadedImage = centerSquareScaleBitmap(loadedImage, 109);
					mPicCarBack.setImageBitmap(loadedImage);
					mPicCarBack.setVisibility(View.VISIBLE);
					mPhotoCarBack.setVisibility(View.INVISIBLE);
					mDelCarBack.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onLoadingStarted(String imageUri, View view) {
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
			}
		});

		// ImageLoader.getInstance().loadImage(info.getRealpicurl(), mImageSize, options, new ImageLoadingListener() {
		// @Override
		// public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
		// if (loadedImage != null) {
		// mPicCoachFace.setImageBitmap(loadedImage);
		// mPicCoachFace.setVisibility(View.VISIBLE);
		// mPhotoCoachFace.setVisibility(View.INVISIBLE);
		// mDelCoachFace.setVisibility(View.VISIBLE);
		// }
		// }
		//
		// @Override
		// public void onLoadingStarted(String imageUri, View view) {
		// }
		//
		// @Override
		// public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
		// }
		//
		// @Override
		// public void onLoadingCancelled(String imageUri, View view) {
		// }
		// });

	}

	void setClickable() {
		if (!mTitleRightTv.isClickable()) {
			mTitleRightTv.setTextColor(Color.parseColor("#2c2c2c"));
			mTitleRightTv.setClickable(true);
		}
	}

	@Click(R.id.title_back)
	void returnLastPage() {
		finish();
	}

	Dialog mDialog = null;

	@Click(R.id.title_right_text)
	void submit() {

		if ("请输入所属驾校名称".equals(mSchoolName.getText().toString())) {
			Toast.makeText(mApplication, "请完善所属驾校", Toast.LENGTH_SHORT).show();
			return;
		}

		if (isOtherSchool) {
			if ("".equals(mDriveSchoolEt.getText().toString())) {
				Toast.makeText(mApplication, "请完善所属驾校", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		
		if (!cbC1.isChecked()&&!cbC2.isChecked())
		{
			Toast.makeText(mApplication, "请选择教学用车型号", Toast.LENGTH_SHORT).show();
			return;
		}
		
//		if (info.getCoach_cardpicurl()==null||"".equals(info.getCoach_cardpicurl()))
//		{
//		if (file3==null) {
//			Toast.makeText(mApplication, "请完善教练证照片", Toast.LENGTH_SHORT).show();
//			return;
//		}
//		}
		
		if (mDelCoachCard.getVisibility()!=View.VISIBLE)
		{
			Toast.makeText(mApplication, "请完善教练证照片", Toast.LENGTH_SHORT).show();
			return;
		}
		
//		if (info.getDrive_cardpicurl() == null||"".equals(info.getDrive_cardpicurl()))
//		{
//		if (file4 == null) {
//			Toast.makeText(mApplication, "请完善驾驶证照片", Toast.LENGTH_SHORT).show();
//			return;
//		}
//		}
		
		if (mDelDriveCard.getVisibility() != View.VISIBLE)
		{
			Toast.makeText(mApplication, "请完善驾驶证照片", Toast.LENGTH_SHORT).show();
			return;
		}

//		if (info.getCar_cardpicfurl() == null||"".equals(info.getCar_cardpicfurl()))
//		{
//		if (file5 == null) {
//			Toast.makeText(mApplication, "请完善车辆行驶证正本", Toast.LENGTH_SHORT).show();
//			return;
//		}
//		}
		
		if (mDelCarFront.getVisibility() != View.VISIBLE)
		{
			Toast.makeText(mApplication, "请完善车辆行驶证正本", Toast.LENGTH_SHORT).show();
			return;
		}

//		if (info.getCar_cardpicburl() == null ||"".equals(info.getCar_cardpicburl()))
//		{
//		if (file6 == null) {
//			Toast.makeText(mApplication, "请完善车辆行驶证副本", Toast.LENGTH_SHORT).show();
//			return;
//		}
//		}
		
		if (mDelCarBack.getVisibility() != View.VISIBLE)
		{
			Toast.makeText(mApplication, "请完善车辆行驶证副本", Toast.LENGTH_SHORT).show();
			return;
		}
		
		

		DialogUtil dUtil = new DialogUtil(new DialogConfirmListener() {

			@Override
			public void doConfirm(String str) {
				// TODO Auto-generated method stub

				new PerfectCoachInfoTask().execute();
				mTitleRightTv.setVisibility(View.GONE);
				info.setState("1");
				if (mDialog != null) {
					mDialog.dismiss();
				}
			}

			@Override
			public void doCancel() {
				// TODO Auto-generated method stub
				if (mDialog != null) {
					mDialog.dismiss();
				}
			}
		});
		mDialog = dUtil.CallConfirmDialog("提交后将会进入审核状态，在未通过审核前学员无法预约您的课程.","","",ProQualityActivity.this, mDialog);
	}

	// @Click(R.id.pencil_id)
	// void enterId() {
	// // mPencilId.setImageResource(R.drawable.pencile_grey);
	// mId.setClickable(true);
	// mId.setFocusableInTouchMode(true);
	// setClickable();
	// }

	// @Click(R.id.pencil_coach)
	// void enterCoachId() {
	// // mPencilCoach.setImageResource(R.drawable.pencile_grey);
	// mCoachId.setClickable(true);
	// mCoachId.setFocusableInTouchMode(true);
	// setClickable();
	// }

	// @Click(R.id.pencil_time)
	// void enterCardTime() {
	// // mPencilTime.setImageResource(R.drawable.pencile_grey);
	// mCardLicnese.setClickable(true);
	// mCardLicnese.setFocusableInTouchMode(true);
	// setClickable();
	// }

	// @Click(R.id.photo_id_front)
	// void showPhotoIdFront() {
	// showMyDialog(CAMERA_REQUEST_CODE_1, ALBUM_REQUEST_CODE_1);
	// }
	//
	// @Click(R.id.photo_id_back)
	// void showPhotoIdBack() {
	// showMyDialog(CAMERA_REQUEST_CODE_2, ALBUM_REQUEST_CODE_2);
	// }

	@Click(R.id.photo_coach)
	void showPhotoCoachCard() {
		showMyDialog(CAMERA_REQUEST_CODE_3, ALBUM_REQUEST_CODE_3);
	}

	@Click(R.id.photo_coach_car)
	void showPhotoDriveCard() {
		showMyDialog(CAMERA_REQUEST_CODE_4, ALBUM_REQUEST_CODE_4);
	}

	@Click(R.id.photo_coach_car_back)
	void showPhotoCarBack() {
		showMyDialog(CAMERA_REQUEST_CODE_6, ALBUM_REQUEST_CODE_6);
	}

	@Click(R.id.photo_car_front)
	void showPhotoCarFront() {
		showMyDialog(CAMERA_REQUEST_CODE_5, ALBUM_REQUEST_CODE_5);
	}

	// @Click(R.id.photo_coach_face)
	// void showPhotoCoach() {
	// showMyDialog(CAMERA_REQUEST_CODE_7, ALBUM_REQUEST_CODE_7);
	// }
	//
	// @Click(R.id.del_id_front)
	// void deleteIdFront() {
	// file1 = null;
	// mPicIdFront.setVisibility(View.INVISIBLE);
	// mPhotoIdFront.setVisibility(View.VISIBLE);
	// mDelIdFront.setVisibility(View.INVISIBLE);
	// }
	//
	// @Click(R.id.del_id_back)
	// void deleteIdBack() {
	// file2 = null;
	// mPicIdBack.setVisibility(View.INVISIBLE);
	// mPhotoIdBack.setVisibility(View.VISIBLE);
	// mDelIdBack.setVisibility(View.INVISIBLE);
	// }

	@Click(R.id.del_coach)
	void deleteCoachCard() {
		if ("1".equals(info.getState()) || "2".equals(info.getState())) {
			showStateDialog(info.getState());
		} else {
			file3 = null;
			mPicCoach.setVisibility(View.INVISIBLE);
			mPhotoCoachCard.setVisibility(View.VISIBLE);
			mDelCoachCard.setVisibility(View.INVISIBLE);
		}
	}

	@Click(R.id.del_coach_car)
	void deleteCoachCar() {
		if ("1".equals(info.getState()) || "2".equals(info.getState())) {
			showStateDialog(info.getState());
		} else {
			file4 = null;
			mPicDriveCar.setVisibility(View.INVISIBLE);
			mPhotoCar.setVisibility(View.VISIBLE);
			mDelDriveCard.setVisibility(View.INVISIBLE);
		}
	}

	@Click(R.id.del_coach_car_front)
	void deleteCarFront() {

		if ("1".equals(info.getState()) || "2".equals(info.getState())) {
			showStateDialog(info.getState());
		} else {
			file5 = null;
			mPicCarFront.setVisibility(View.INVISIBLE);
			mPhotoCarFront.setVisibility(View.VISIBLE);
			mDelCarFront.setVisibility(View.INVISIBLE);
		}
	}

	@Click(R.id.del_coach_car_back)
	void deleteCarBack() {

		if ("1".equals(info.getState()) || "2".equals(info.getState())) {
			showStateDialog(info.getState());
		} else {
			file6 = null;
			mPicCarBack.setVisibility(View.INVISIBLE);
			mPhotoCarBack.setVisibility(View.VISIBLE);
			mDelCarBack.setVisibility(View.INVISIBLE);
		}
	}

	// @Click(R.id.del_coach_face)
	// void deleteCoachFace() {
	// file7 = null;
	// mPicCoachFace.setVisibility(View.INVISIBLE);
	// mPhotoCoachFace.setVisibility(View.VISIBLE);
	// mDelCoachFace.setVisibility(View.INVISIBLE);
	// }

	// @Click(R.id.add_cartype)
	// void addCartype() {
	// if (CarTypeDialog != null) {
	// setClickable();
	// CarTypeDialog.show();
	// }
	// }
	//
	// @Click(R.id.enter_cartype_one)
	// void addCartypeTv() {
	// if (CarTypeDialog != null) {
	// setClickable();
	// CarTypeDialog.show();
	// }
	// }
	//
	// @Click(R.id.enter_car_model)
	// void addCarModelTv() {
	// if (CarModelDialog != null) {
	// setClickable();
	// CarModelDialog.show();
	// }
	// }
	//
	// @Click(R.id.add_carmodel)
	// void addCarModel() {
	// if (CarModelDialog != null) {
	// setClickable();
	// CarModelDialog.show();
	// }
	// }

	Dialog mPhotoDialog;

	private void showMyDialog(final int cameraCode, final int albumCode) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ProQualityActivity.this);
		mPhotoDialog = builder.create();
		mPhotoDialog.show();
		mPhotoDialog.setContentView(R.layout.photo_dialog);
		mPhotoDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
		WindowManager.LayoutParams params = mPhotoDialog.getWindow().getAttributes();
		params.gravity = Gravity.BOTTOM;
		params.width = WindowManager.LayoutParams.MATCH_PARENT;
		mPhotoDialog.getWindow().setAttributes(params);

		TextView camera = (TextView) mPhotoDialog.findViewById(R.id.dialog_camera);
		TextView album = (TextView) mPhotoDialog.findViewById(R.id.dialog_album);

		camera.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (!CommonUtils.isExitsSdcard()) {
					Toast.makeText(ProQualityActivity.this, "SD卡不存在,不能拍照,请检查手机SD卡", Toast.LENGTH_SHORT).show();
					return;
				}
				// 每次拍照重新生成文件名称
				PHOTO_TEMP_FILE = "Image_" + System.currentTimeMillis() + ".jpg";
				File tempFile = new File(Settings.TEMP_PATH, PHOTO_TEMP_FILE);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
				startActivityForResult(intent, cameraCode);
				mPhotoDialog.dismiss();
			}
		});

		album.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, albumCode);
				mPhotoDialog.dismiss();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1000)
		{
			if (data!=null)
			{
				mSchoolName.setText(data.getStringExtra("school"));
			}
		}

		// 相机返回2001-2007
		if (2001 <= requestCode && requestCode <= 2007 && resultCode == RESULT_OK) {
			File photoFile = new File(Settings.TEMP_PATH, getPhotoName());

			if (photoFile.exists()) {// 如果照片存在
				cropPhoto(photoFile, 200, 200, requestCode + 14);
			} else {// 照片不存在,检查是否在data中
				if (data != null && data.hasExtra("data")) {
					Bitmap photo = data.getParcelableExtra("data");
					if (photo != null) {
						// 保存图片到本地
						//saveBitmapToFile(photo, photoFile);
						cropPhoto(photoFile, 200, 200, requestCode + 14);
					} else {
						Toast.makeText(ProQualityActivity.this, "照片拍摄失败.", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(ProQualityActivity.this, "照片拍摄失败.", Toast.LENGTH_SHORT).show();
				}
			}

		}

		// 相册返回2008-2014
		if (2008 <= requestCode && requestCode <= 2014 && resultCode == RESULT_OK) {
			String photoPath = getPhotoPathByUri(data.getData());
			if (photoPath.length() > 0) {
				cropPhoto(new File(photoPath), 200, 200, requestCode + 7);
			}
		}
		// 返回裁剪
		if (requestCode <= 2021 && requestCode >= 2015 && resultCode == RESULT_OK) {
			setClickable();
			switch (requestCode) {
			// case CROP_REQUEST_CODE_1:
			// photoname = getCropPhotoName();
			// file1 = new File(Settings.TEMP_PATH, photoname);
			// if (file1.exists()) {
			// avater = CommonUtils.getBitmapFromFile(file1, 200, 200);
			// mPicIdFront.setImageBitmap(avater);
			// mPicIdFront.setVisibility(View.VISIBLE);
			// mPhotoIdFront.setVisibility(View.INVISIBLE);
			// mDelIdFront.setVisibility(View.VISIBLE);
			// }
			// break;
			// case CROP_REQUEST_CODE_2:
			// photoname = getCropPhotoName();
			// file2 = new File(Settings.TEMP_PATH, photoname);
			// if (file2.exists()) {
			// avater = CommonUtils.getBitmapFromFile(file2, 200, 200);
			// mPicIdBack.setImageBitmap(avater);
			// mPicIdBack.setVisibility(View.VISIBLE);
			// mPhotoIdBack.setVisibility(View.INVISIBLE);
			// mDelIdBack.setVisibility(View.VISIBLE);
			// }
			// break;
			case CROP_REQUEST_CODE_3:
				photoname = getCropPhotoName();
				file3 = new File(Settings.TEMP_PATH, photoname);
				if (file3.exists()) {
					avater = CommonUtils.getBitmapFromFile(file3, 200, 200);
					mPicCoach.setImageBitmap(avater);
					mPicCoach.setVisibility(View.VISIBLE);
					mPhotoCoachCard.setVisibility(View.INVISIBLE);
					mDelCoachCard.setVisibility(View.VISIBLE);
				}
				break;
			case CROP_REQUEST_CODE_4:
				photoname = getCropPhotoName();
				file4 = new File(Settings.TEMP_PATH, photoname);
				if (file4.exists()) {
					avater = CommonUtils.getBitmapFromFile(file4, 200, 200);
					mPicDriveCar.setImageBitmap(avater);
					mPicDriveCar.setVisibility(View.VISIBLE);
					mPhotoCar.setVisibility(View.INVISIBLE);
					mDelDriveCard.setVisibility(View.VISIBLE);
				}
				break;
			case CROP_REQUEST_CODE_5:
				photoname = getCropPhotoName();
				file5 = new File(Settings.TEMP_PATH, photoname);
				if (file5.exists()) {
					avater = CommonUtils.getBitmapFromFile(file5, 200, 200);
					mPicCarFront.setImageBitmap(avater);
					mPicCarFront.setVisibility(View.VISIBLE);
					mPhotoCarFront.setVisibility(View.INVISIBLE);
					mDelCarFront.setVisibility(View.VISIBLE);
				}
				break;
			case CROP_REQUEST_CODE_6:
				photoname = getCropPhotoName();
				file6 = new File(Settings.TEMP_PATH, photoname);
				if (file6.exists()) {
					avater = CommonUtils.getBitmapFromFile(file6, 200, 200);
					mPicCarBack.setImageBitmap(avater);
					mPicCarBack.setVisibility(View.VISIBLE);
					mPhotoCarBack.setVisibility(View.INVISIBLE);
					mDelCarBack.setVisibility(View.VISIBLE);
				}
				break;
			// case CROP_REQUEST_CODE_7:
			// photoname = getCropPhotoName();
			// file7 = new File(Settings.TEMP_PATH, photoname);
			// if (file7.exists()) {
			// avater = CommonUtils.getBitmapFromFile(file7, 200, 200);
			// mPicCoachFace.setImageBitmap(avater);
			// mPicCoachFace.setVisibility(View.VISIBLE);
			// mPhotoCoachFace.setVisibility(View.INVISIBLE);
			// mDelCoachFace.setVisibility(View.VISIBLE);
			// }
			// break;

			default:
				break;
			}
		}
		
		
	}

	public Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
		if (null == bitmap || edgeLength <= 0) {
			return null;
		}

		Bitmap result = bitmap;
		int widthOrg = bitmap.getWidth();
		int heightOrg = bitmap.getHeight();

		if (widthOrg > edgeLength && heightOrg > edgeLength) {
			// 压缩到一个最小长度是edgeLength的bitmap
			int longerEdge = (int) (edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
			int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
			int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
			Bitmap scaledBitmap;

			try {
				scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
			} catch (Exception e) {
				return null;
			}

			// 从图中截取正中间的正方形部分。
			int xTopLeft = (scaledWidth - edgeLength) / 2;
			int yTopLeft = (scaledHeight - edgeLength) / 2;

			try {
				result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
				scaledBitmap.recycle();
			} catch (Exception e) {
				return null;
			}
		}

		return result;
	}

	public String getCropPhotoName() {
		return CROP_TEMP_FILE;
	}

	public String getPhotoName() {
		return PHOTO_TEMP_FILE;
	}

	public String getPhotoPathByUri(Uri selectedImage) {
		Cursor cursor = getContentResolver().query(selectedImage, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex("_data");
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			cursor = null;

			if (picturePath == null || picturePath.equals("null")) {
				return "";
			}
			return picturePath;
		} else {
			File file = new File(selectedImage.getPath());
			if (!file.exists()) {
				return "";
			}
			return file.getAbsolutePath();
		}
	}

	// 截取图片
	public void cropPhoto(File file, int width, int height, final int cropcode) {
		// CROP_TEMP_FILE = "Crop_" + System.currentTimeMillis() + ".jpg";
		// Intent intent = new Intent("com.android.camera.action.CROP");
		// intent.setDataAndType(Uri.fromFile(file), "image/*");
		// intent.putExtra("crop", "true");
		// // aspectX aspectY 是宽高的比例
		// intent.putExtra("aspectX", width);
		// intent.putExtra("aspectY", height);
		// // outputX outputY 是裁剪图片宽高
		// intent.putExtra("scale", true);// 黑边
		// intent.putExtra("scaleUpIfNeeded", true);// 黑边
		// intent.putExtra("outputX", width);
		// intent.putExtra("outputY", height);
		// intent.putExtra("return-data", true);
		// intent.putExtra("output", Uri.fromFile(new File(Settings.TEMP_PATH, CROP_TEMP_FILE))); // 专入目标文件
		// startActivityForResult(intent, cropcode);

		switch (cropcode) {
		// case CROP_REQUEST_CODE_1:
		// file1 = file;
		// if (file1.exists()) {
		// avater = CommonUtils.getBitmapFromFile(file1, 200, 200);
		// mPicIdFront.setImageBitmap(avater);
		// mPicIdFront.setVisibility(View.VISIBLE);
		// mPhotoIdFront.setVisibility(View.INVISIBLE);
		// mDelIdFront.setVisibility(View.VISIBLE);
		// }
		// break;
		// case CROP_REQUEST_CODE_2:
		// file2 = file;
		// if (file2.exists()) {
		// avater = CommonUtils.getBitmapFromFile(file2, 200, 200);
		// mPicIdBack.setImageBitmap(avater);
		// mPicIdBack.setVisibility(View.VISIBLE);
		// mPhotoIdBack.setVisibility(View.INVISIBLE);
		// mDelIdBack.setVisibility(View.VISIBLE);
		// }
		// break;
		case CROP_REQUEST_CODE_3:
			file3 = file;
			if (file3.exists()) {
				avater = CommonUtils.getBitmapFromFile(file3, 200, 200);
				avater = centerSquareScaleBitmap(avater, 109);
				mPicCoach.setImageBitmap(avater);
				mPicCoach.setVisibility(View.VISIBLE);
				mPhotoCoachCard.setVisibility(View.INVISIBLE);
				mDelCoachCard.setVisibility(View.VISIBLE);
			}
			break;
		case CROP_REQUEST_CODE_4:
			file4 = file;
			if (file4.exists()) {
				avater = CommonUtils.getBitmapFromFile(file4, 200, 200);
				avater = centerSquareScaleBitmap(avater, 109);
				mPicDriveCar.setImageBitmap(avater);
				mPicDriveCar.setVisibility(View.VISIBLE);
				mPhotoCar.setVisibility(View.INVISIBLE);
				mDelDriveCard.setVisibility(View.VISIBLE);
			}
			break;
		case CROP_REQUEST_CODE_5:
			file5 = file;
			if (file5.exists()) {
				avater = CommonUtils.getBitmapFromFile(file5, 200, 200);
				avater = centerSquareScaleBitmap(avater, 109);
				mPicCarFront.setImageBitmap(avater);
				mPicCarFront.setVisibility(View.VISIBLE);
				mPhotoCarFront.setVisibility(View.INVISIBLE);
				mDelCarFront.setVisibility(View.VISIBLE);
			}
			break;
		case CROP_REQUEST_CODE_6:
			file6 = file;
			if (file6.exists()) {
				avater = CommonUtils.getBitmapFromFile(file6, 200, 200);
				avater = centerSquareScaleBitmap(avater, 109);
				mPicCarBack.setImageBitmap(avater);
				mPicCarBack.setVisibility(View.VISIBLE);
				mPhotoCarBack.setVisibility(View.INVISIBLE);
				mDelCarBack.setVisibility(View.VISIBLE);
			}
			break;
		// case CROP_REQUEST_CODE_7:
		// file7 = file;
		// if (file7.exists()) {
		// avater = CommonUtils.getBitmapFromFile(file7, 200, 200);
		// mPicCoachFace.setImageBitmap(avater);
		// mPicCoachFace.setVisibility(View.VISIBLE);
		// mPhotoCoachFace.setVisibility(View.INVISIBLE);
		// mDelCoachFace.setVisibility(View.VISIBLE);
		// }
		// break;

		default:
			break;
		}
	}

	public boolean saveBitmapToFile(Bitmap bitmap, File file) {
		BufferedOutputStream bos = null;
		try {
			File tempPicFile = new File(Settings.TEMP_PATH, FileUtils.getFileNameByPath(file.getPath()) + Settings.PICTURE_TEMP_EXTENSION);
			
			tempPicFile.delete();
			file.delete();

			tempPicFile.getParentFile().mkdirs();
			tempPicFile.createNewFile();

			bos = new BufferedOutputStream(new FileOutputStream(tempPicFile));
			bitmap.compress(CompressFormat.JPEG, 100, bos);

			bos.flush();
			bos.close();
			bos = null;

			return tempPicFile.renameTo(file);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				bos = null;
			}
		}
		return false;
	}

	// @Click(R.id.enter_id_invalid_time)
	// void showIdDialog() {
	// selectType = 1;
	// if (mBirthdayDialog != null)
	// mBirthdayDialog.show();
	// }
	//
	// @Click(R.id.enter_coach_invalid_time)
	// void showCoachDialog() {
	// selectType = 2;
	// if (mBirthdayDialog != null)
	// mBirthdayDialog.show();
	// }
	//
	// @Click(R.id.enter_drive_invalid_time)
	// void showDriveDialog() {
	// selectType = 3;
	// if (mBirthdayDialog != null)
	// mBirthdayDialog.show();
	// }
	//
	// @Click(R.id.enter_car_invalid_time)
	// void showCarDialog() {
	// selectType = 4;
	// if (mBirthdayDialog != null)
	// mBirthdayDialog.show();
	// }

	@Click(R.id.rl_school)
	void schoolname() {

		if ("1".equals(info.getState()) || "2".equals(info.getState())) {
			showStateDialog(info.getState());
		} else {
			setClickable();
//			if (schoolDialog != null)
//				schoolDialog.show();
			Intent intent = new Intent(ProQualityActivity.this,FilteSchoolListView.class);
			startActivityForResult(intent, 1000);
		}
	}

	private String driveSchoolId;

	private class PerfectCoachInfoTask extends AsyncTask<Void, Void, PerfectCoachInfoResult> {
		JSONAccessor accessor = new JSONAccessor(ProQualityActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST_MULTIPART);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected PerfectCoachInfoResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			PerfectCoachInfoReq param = new PerfectCoachInfoReq();
			param.setAction("PerfectCoachInfo");
			param.setCoachid(CoachApplication.getInstance().getUserInfo().getCoachid() + "");
			param.setToken(CoachApplication.getInstance().getUserInfo().getToken());
			// if (mId.isFocusable())
			// param.setIdnum(mId.getText().toString());
			// if (mCoachId.isFocusable())
			// param.setCoachcardnum(mCoachId.getText().toString());
			// if (mCardLicnese.isFocusable())
			// param.setCarlicense(mCardLicnese.getText().toString());
			// if (mCarModel.isFocusable())
			// param.setCarmodelid(m.getText().toString());
//			if (isOtherSchool) {
//				param.setDriveschool(mDriveSchoolEt.getText().toString());
//			} else {
//				if (driveSchoolId != null)
//					param.setDriveschoolid(driveSchoolId);
//			}
			param.setDriveschool(mSchoolName.getText().toString().trim());
			if (!isOtherSchool)
			{
				param.setDriveschoolid(driveSchoolId);
			}
			// if (mCarNum.isFocusable())
			// param.setCarcardnum(mCarNum.getText().toString());
			// if (mDrivenum.isFocusable())
			// param.setDrivecardnum(mDrivenum.getText().toString());
			// if (hasTime1)
			// param.setIdcardextime(mIdInvalidTime.getText().toString());
			// if (hasTime2)
			// param.setCoachcardextime(mCoachInvalidTime.getText().toString());
			// if (hasTime3)
			// param.setDrivecardextime(mDriveInvalidTime.getText().toString());
			// if (hasTime4)
			// param.setCarcardextime(mCarInvalidTime.getText().toString());
//			if (selectmodels.size() > 0) {
//				StringBuilder modelStr = new StringBuilder();
//				for (String item : selectmodels) {
//					modelStr.append(item);
//					modelStr.append(",");
//				}
//				if (modelStr.length() > 0) {
//					String str = modelStr.toString().substring(0, modelStr.length() - 1);
//					for (CarModel model : carmodelList) {
//						if (model.getModelname() != null && model.getModelname().equals(str))
//							param.setModelid(model.getModelid() + "");
//					}
//				}
//			}
			StringBuilder modelStr = new StringBuilder();
			if (cbC1.isChecked())
			{
				modelStr.append("17");
			}
			if (cbC2.isChecked())
			{
				modelStr.append(",18");
			}
			String str = modelStr.toString();
			param.setModelid(str);
			
			/*
			 * 教学用车
			 */
			// if (hasTeachCar) {
			// param.setCarmodel(mCarModel.getText().toString());
			// param.setCarmodelid(carmodelId + "");
			// }

//			if (file1 != null)
//				param.setCardpic1(compressImageFromFile(file1));
//			if (file2 != null)
//				param.setCardpic2(compressImageFromFile(file2));
			if (file3 != null)
				param.setCardpic3(compressImageFromFile(file3));
			if (file4 != null)
				param.setCardpic4(compressImageFromFile(file4));
			if (file5 != null)
				param.setCardpic5(compressImageFromFile(file5));
			if (file6 != null)
				param.setCardpic6(compressImageFromFile(file6));
//			if (file7 != null)
//				param.setCardpic7(compressImageFromFile(file7));

			return accessor.execute(Settings.USER_URL, param, PerfectCoachInfoResult.class);
		}

		@Override
		protected void onPostExecute(PerfectCoachInfoResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					/*
					 * 保存本地信息
					 */
					UserInfo info = CoachApplication.getInstance().getUserInfo();
					// info.setId_cardnum(mId.getText().toString());
					// info.setCoach_cardnum(mCoachId.getText().toString());
					// info.setCar_cardnum(mCarNum.getText().toString());
					// info.setDrive_cardnum(mDrivenum.getText().toString());
					// info.setCoach_cardnum(mCoachId.getText().toString());
					// info.setCarlicense(mCardLicnese.getText().toString());
					// if (hasTime1)
					// info.setId_cardexptime(mIdInvalidTime.getText().toString());
					// if (hasTime2)
					// info.setCoach_cardexptime(mCoachInvalidTime.getText().toString());
					// if (hasTime3)
					// info.setDrive_cardexptime(mDriveInvalidTime.getText().toString());
					// if (hasTime4)
					// info.setCar_cardexptime(mCarInvalidTime.getText().toString());
					if (result.getCradpic1url() != null)
						info.setId_cardpicfurl(result.getCradpic1url());
					if (result.getCradpic2url() != null)
						info.setId_cardpicburl(result.getCradpic2url());
					if (result.getCradpic3url() != null)
						info.setCoach_cardpicurl(result.getCradpic3url());
					if (result.getCradpic4url() != null)
						info.setDrive_cardpicurl(result.getCradpic4url());
					if (result.getCradpic5url() != null)
						info.setCar_cardpicfurl(result.getCradpic5url());
					if (result.getCradpic6url() != null)
						info.setCar_cardpicburl(result.getCradpic6url());
					if (result.getCradpic7url() != null)
						info.setRealpicurl(result.getCradpic7url());
					if (isOtherSchool) {
						if (!TextUtils.isEmpty(mDriveSchoolEt.getText()))
							info.setDriveschool(mDriveSchoolEt.getText().toString());
					} else {
						if (!TextUtils.isEmpty(mSchoolName.getText()))
							info.setDriveschool(mSchoolName.getText().toString());
					}
					// if (hasTeachCar) {
					// info.setCarmodel(mCarModel.getText().toString());
					// }
//					if (selectmodels != null && selectmodels.size() > 0) {
//						List<CarType> temp = new ArrayList<CarType>();
//						for (CarModel car : carmodelList) {
//							if (car.getModelname() != null && car.getModelname().equals(selectmodels.get(0))) {
//								CarType type = new CarType();
//								type.setModelid(car.getModelid());
//								type.setModelname(car.getModelname());
//								temp.add(type);
//								break;
//							}
//						}
//						info.setModellist(temp);
//					}
					List<CarType> temp = new ArrayList<CarType>();
					if (cbC1.isChecked())
					{
						CarType type = new CarType();
						type.setModelid(17);
						temp.add(type);
					}
					if (cbC2.isChecked())
					{
						CarType type = new CarType();
						type.setModelid(18);
						temp.add(type);
					}
					info.setModellist(temp);
					info.saveUserInfo(info, ProQualityActivity.this);
					//
					CommonUtils.showToast(ProQualityActivity.this.getApplicationContext(), "提交成功");
					ProQualityActivity.this.finish();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(ProQualityActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ProQualityActivity.this);
					}
				}

			} else {
				CommonUtils.showToast(ProQualityActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}
	}

	// private class GetCarModelTask extends AsyncTask<Void, Void, GetCarModelResult> {
	// JSONAccessor accessor = new JSONAccessor(ProQualityActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);
	//
	// @Override
	// protected GetCarModelResult doInBackground(Void... arg0) {
	// return accessor.execute(Settings.USER_URL, null, GetCarModelResult.class);
	// }
	//
	// @Override
	// protected void onPostExecute(GetCarModelResult result) {
	// super.onPostExecute(result);
	// if (result != null && result.getCode() == 1) {
	// if (result.getModellist() != null && result.getModellist().size() > 0) {
	// models = result.getModellist();
	//
	// String[] content = (String[]) models.toArray();
	// dialog = new SelectDialog(ProQualityActivity.this, content);
	// dialog.setAdapter();
	// dialog.setmConfirmListener(new DialogConfirmListener() {
	//
	// @Override
	// public void doConfirm(String str) {
	// if (str != null) {
	// if (!selectmodels.contains(str)) {
	// selectmodels.add(str);
	// TextView tv = new TextView(ProQualityActivity.this);
	// tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(ProQualityActivity.this, 30)));
	// tv.setText(str);
	// mCartypeLayout.addView(tv);
	// }
	// }
	// dialog.dismiss();
	// }
	//
	// @Override
	// public void doCancel() {
	// dialog.dismiss();
	// }
	// });
	// } else {
	//
	// }
	// }
	// }
	//
	// }

	List<CarModel> carmodelList = new ArrayList<GetCarTypeResult.CarModel>();

	// private class GetCarModelList extends AsyncTask<Void, Void, GetCarTypeResult> {
	// JSONAccessor accessor = new JSONAccessor(ProQualityActivity.this, JSONAccessor.METHOD_POST);
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// // if (mLoadingDialog != null)
	// // mLoadingDialog.show();
	// }
	//
	// @Override
	// protected GetCarTypeResult doInBackground(Void... params) {
	// HashMap<String, Object> param = new BaseParam();
	// param.put("action", "GetCarModel");
	// return accessor.execute(Settings.USER_URL, param, GetCarTypeResult.class);
	// }
	//
	// @Override
	// protected void onPostExecute(GetCarTypeResult result) {
	// super.onPostExecute(result);
	//
	// if (result != null) {
	// if (result.getCode() == 1) {
	// if (result.getModellist() != null && result.getModellist().size() > 0) {
	// carmodelList = result.getModellist();
	// }
	// for (int i = 0; i < carmodelList.size(); i++) {
	// String cartype = carmodelList.get(i).getModelname().toString();
	// carType.add(cartype);
	// }
	// String[] cartype = new String[carType.size()];
	// for (int i = 0; i < carType.size(); i++) {
	// cartype[i] = carType.get(i);
	// }
	// // CarTypeDialog = new SelectDialog(ProQualityActivity.this, cartype);
	// // CarTypeDialog.setAdapter();
	// // CarTypeDialog.setmConfirmListener(new DialogConfirmListener() {
	// //
	// // @Override
	// // public void doConfirm(String str) {
	// // if (str != null && !selectmodels.contains(str)) {
	// // // if (!selectmodels.contains(str)) {
	// // // selectmodels.add(str);
	// // // TextView tv = new TextView(ProQualityActivity.this);
	// // // tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(ProQualityActivity.this, 30)));
	// // // tv.setText(str);
	// // // tv.setTextSize(18);
	// // // mCartypeLayout.addView(tv);
	// // // }
	// //
	// // /*
	// // * 改成单选
	// // */
	// // /*
	// // * mCartypeLayout.removeAllViewsInLayout(); selectmodels.clear(); selectmodels.add(str); TextView tv = new TextView(ProQualityActivity.this); tv.setLayoutParams(new
	// // * LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(ProQualityActivity.this, 30))); tv.setText(str); tv.setTextSize(18);
	// // * mCartypeLayout.addView(tv);
	// // */
	// // selectmodels.clear();
	// // selectmodels.add(str);
	// // mCarType1.setText(str);
	// // }
	// // CarTypeDialog.dismiss();
	// // }
	// //
	// // @Override
	// // public void doCancel() {
	// // CarTypeDialog.dismiss();
	// // }
	// // });
	// } else {
	// if (result.getMessage() != null)
	// CommonUtils.showToast(ProQualityActivity.this, result.getMessage());
	// if (result.getCode() == 95) {
	// CommonUtils.gotoLogin(ProQualityActivity.this);
	// }
	// }
	// } else {
	// CommonUtils.showToast(ProQualityActivity.this, getString(R.string.net_error));
	// }
	// }
	// }

	List<TeachCar> teachCars;
	int carmodelId;
	boolean hasTeachCar;

	// private class GetTeachCarList extends AsyncTask<Void, Void, GetTeachCarModelResult> {
	// JSONAccessor accessor = new JSONAccessor(ProQualityActivity.this, JSONAccessor.METHOD_POST);
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// // if (mLoadingDialog != null)
	// // mLoadingDialog.show();
	// }
	//
	// @Override
	// protected GetTeachCarModelResult doInBackground(Void... params) {
	// HashMap<String, Object> param = new BaseParam();
	// param.put("action", "GetAllTeachCarModel");
	// return accessor.execute(Settings.CMY_URL, param, GetTeachCarModelResult.class);
	// }
	//
	// @Override
	// protected void onPostExecute(GetTeachCarModelResult result) {
	// super.onPostExecute(result);
	//
	// if (result != null) {
	// if (result.getCode() == 1) {
	//
	// if (result.getTeachcarlist() != null) {
	// teachCars = result.getTeachcarlist();
	// }
	// for (int i = 0; i < teachCars.size(); i++) {
	// carModel.add(result.getTeachcarlist().get(i).getModelname().toString());
	// }
	// String[] carmodel = new String[carModel.size()];
	// for (int i = 0; i < carModel.size(); i++) {
	// carmodel[i] = carModel.get(i);
	// }
	// CarModelDialog = new SelectDialog(ProQualityActivity.this, carmodel);
	// CarModelDialog.setAdapter();
	// CarModelDialog.setmConfirmListener(new DialogConfirmListener() {
	//
	// @Override
	// public void doConfirm(String str) {
	// hasTeachCar = true;
	// mCarModel.setText(str);
	// mCarModel.setTextColor(getResources().getColor(R.color.text_black));
	// for (TeachCar car : teachCars) {
	// if (car.getModelname() != null && car.getModelname().equals(str)) {
	// carmodelId = car.getModelid();
	// }
	// }
	// CarModelDialog.dismiss();
	// }
	//
	// @Override
	// public void doCancel() {
	// CarModelDialog.dismiss();
	// }
	// });
	// } else {
	// if (result.getMessage() != null)
	// CommonUtils.showToast(ProQualityActivity.this, result.getMessage());
	// if (result.getCode() == 95) {
	// CommonUtils.gotoLogin(ProQualityActivity.this);
	// }
	// }
	// } else {
	// CommonUtils.showToast(ProQualityActivity.this, getString(R.string.net_error));
	// }
	// }
	// }

	List<School> schoollist;
	boolean isOtherSchool;

	private class GetAllSchool extends AsyncTask<Void, Void, GetSchoolResult> {
		JSONAccessor accessor = new JSONAccessor(ProQualityActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST_MULTIPART);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected GetSchoolResult doInBackground(Void... params) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetAllSchool");
			return accessor.execute(Settings.CMY_URL, param, GetSchoolResult.class);
		}

		@Override
		protected void onPostExecute(GetSchoolResult result) {
			super.onPostExecute(result);

			if (result != null) {
				if (result.getCode() == 1) {
					for (int i = 0; i < result.getSchoollist().size(); i++) {
						getSchool.add(result.getSchoollist().get(i).getName().toString());
					}
					int length = getSchool.size();
					String[] school = new String[length + 1];
					// school = (String[])getSchool.toArray();
					for (int i = 0; i < length; i++) {
						school[i] = getSchool.get(i);
					}
					school[length] = "其他";

					schoollist = result.getSchoollist();
					School other = new School();
					other.setName("其他");
					schoollist.add(other);
					schoolDialog = new SelectDialog(ProQualityActivity.this, school);
					schoolDialog.setAdapter();
					schoolDialog.setmConfirmListener(new DialogConfirmListener() {

						@Override
						public void doConfirm(String str) {
							// if (str != null) {
							// if (!selectmodels.contains(str)) {
							// selectmodels.add(str);
							// TextView tv = new TextView(ProQualityActivity.this);
							// tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtils.dp2px(ProQualityActivity.this, 30)));
							// tv.setText(str);
							// mCartypeLayout.addView(tv);
							// }
							// }
							if (str != null && str.equals("其他")) {
								isOtherSchool = true;
								mDriveSchoolEt.setVisibility(View.VISIBLE);
								mSchoolName.setVisibility(View.INVISIBLE);
								mDriveSchoolEt.requestFocus();
								InputMethodManager imm = (InputMethodManager) ProQualityActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
								imm.showSoftInputFromInputMethod(mDriveSchoolEt.getWindowToken(), 0);
								schoolDialog.dismiss();
								return;
							} else {
								isOtherSchool = false;
								mDriveSchoolEt.setVisibility(View.INVISIBLE);
								mSchoolName.setVisibility(View.VISIBLE);
							}
							if (schoollist != null) {
								for (School ss : schoollist)
									if (ss.getName() != null && ss.getName().equals(str))
										driveSchoolId = ss.getSchoolid() + "";
							}
							if (mSchoolName.getVisibility() == View.INVISIBLE) {
								mDriveSchoolEt.setVisibility(View.INVISIBLE);
								mSchoolName.setVisibility(View.VISIBLE);
							}
							mSchoolName.setText(str);
							mSchoolName.setTextColor(getResources().getColor(R.color.text_black));
							schoolDialog.dismiss();
						}

						@Override
						public void doCancel() {
							schoolDialog.dismiss();
						}
					});
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(ProQualityActivity.this, result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ProQualityActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(ProQualityActivity.this, getString(R.string.net_error));
			}
		}
	}

	private class PerfectAccountInfoTask extends AsyncTask<Void, Void, GetCoachState> {
		JSONAccessor accessor = new JSONAccessor(ProQualityActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPostExecute(GetCoachState result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					info.setState(String.valueOf(result.getCoachinfo().getState()));
					setListeners();
					addListeners();
					setLocalInfo();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(ProQualityActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ProQualityActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(ProQualityActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected GetCoachState doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetCoachDetail");
			param.put("coachid", mApplication.getUserInfo().getCoachid());
			return accessor.execute(Settings.SBOOK_URL, param, GetCoachState.class);
		}
	}

	/**
	 * 图片按比例大小压缩方法（宽高1200）
	 * 
	 * @param file
	 * @return
	 */
	public static File compressImageFromFile(File file) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;// 只读边,不读内容
		Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), newOpts);

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		float hh = 400f;// 这里设置高度为800f
		float ww = 400f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1; // be=1表示不缩放
		if (w > h && w > ww) { // 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) { // 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
		newOpts.inPurgeable = true;// 同时设置才会有效
		newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

		bitmap = BitmapFactory.decodeFile(file.getPath(), newOpts);
		// return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
		// 其实是无效的,大家尽管尝试
		// bitmap = compressImage(bitmap);//压缩好比例大小后再进行质量压缩

		return getFileFromBitmap(bitmap, file);
	}

	/**
	 * bitmap转file，压缩85%
	 * 
	 * @param bmp
	 * @param file
	 * @return
	 */
	private static File getFileFromBitmap(Bitmap bmp, File file) {
		try {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			// 直接压缩80%
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

}
