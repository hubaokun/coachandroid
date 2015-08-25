package xiaoba.coach.activity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import net.grobas.view.PolygonImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Comment;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.util.FileUtils;
import com.daoshun.lib.view.OnSingleClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.interfaces.DialogConfirmListener;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.UserInfo;
import xiaoba.coach.net.request.ChangeAvatarReq;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.ChangeAvatarResult;
import xiaoba.coach.net.result.GetSchoolResult;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.views.BirthdayDialog;
import xiaoba.coach.views.GenderDialog;
import xiaoba.coach.views.SelectDialog;
import xiaoba.coach.views.BirthdayDialog.OnComfirmClickListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
 * 个人信息
 */
@EActivity(R.layout.activity_self_material)
public class SelfMaterialActivity extends BaseActivity {
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.rl_name)
	RelativeLayout rlName;
	@ViewById(R.id.rl_geny)
	RelativeLayout rlGeny;
	@ViewById(R.id.rl_coach_pro)
	RelativeLayout rlCoachPro;
	@ViewById(R.id.rl_coach_address)
	RelativeLayout rlCoachAdress;
	@ViewById(R.id.rl_birthday)
	RelativeLayout rlCoachBir;
	@ViewById(R.id.rl_coach_year)
	RelativeLayout rlCoachYear;
	@ViewById(R.id.rl_about_self)
	RelativeLayout rlAboutSelf;
	@ViewById(R.id.tv_birthday)
	TextView tvBirthday;
	@ViewById(R.id.tv_name)
	TextView tvName;
	@ViewById(R.id.tv_geny)
	TextView tvGeny;
	@ViewById(R.id.tv_teach_year)
	TextView tvTeachYear;
	@ViewById(R.id.tv_about_self)
	TextView tvAboutSelf;
//	@ViewById(R.id.account_info)
//	LinearLayout mAccount;
//	@ViewById(R.id.pro_quality)
//	LinearLayout mProQuality;
//	@ViewById(R.id.personal_info)
//	LinearLayout mPersonInfo;
//	@ViewById(R.id.modify_pass)
//	LinearLayout mModifyPass;
//	@ViewById(R.id.total_hours)
//	TextView mTotalHours;
//	@ViewById(R.id.total_rating)
//	TextView mTotalScore;
//	@ViewById(R.id.self_ratingBar)
//	RatingBar mRatingBar;
//	@ViewById(R.id.address_set)
//	LinearLayout mAddressSet;
//	@ViewById(R.id.gender_tv)
//	TextView mGenderTv;
//	@ViewById(R.id.pencil_sex)
//	ImageView mPencilSex;
//	@ViewById(R.id.enter_name)
//	EditText mName;
//	@ViewById(R.id.pencil_name)
//	ImageView mPencilName;

//	@ViewById(R.id.enter_phone_number)
//	TextView mPhonNumber;
//	@ViewById(R.id.pencil_phone_number)
//	ImageView mPencilPhonNumber;

	@ViewById(R.id.potrait)
	PolygonImageView mPortrait;

/*	@ViewById(R.id.tv_change_new_phone)
	TextView tvChangeNewPhone;*/

//	@ViewById(R.id.user_address)
//	TextView mUserAddress;
//	@ViewById(R.id.enter_school_name)
//	TextView mSchoolName;

	GenderDialog mGenderDialog;
	boolean isClickable, hasGender, hasDate, hasCity;
	private Bitmap avatar; // 头像
	private static final int CAMERA_REQUEST_CODE = 1001;
	private static final int ALBUM_REQUEST_CODE = 1002;
	private static final int CROP_REQUEST_CODE = 1004;
	private String CROP_TEMP_FILE;// 裁剪的时候输出文件名称
	private String PHOTO_TEMP_FILE; // 相机拍照的输出文件名称
	private String mPotraitID; // 上传图像后返回

	DisplayImageOptions options;
	ImageSize mImageSize;
	UserInfo info;
	BirthdayDialog mBirthdayDialog;

	@AfterViews
	void init() {
		mTitle.setText("个人信息");
		mTitleBack.setImageResource(R.drawable.back_arrow);
		mTitle.setTextColor(Color.parseColor("#2c2c2c"));
//		mTitleRightTv.setText("提交资料");
//		mTitleRightTv.setTextColor(Color.parseColor("#d2d2d2"));
//		mTitleRightTv.setClickable(false);
//		mTitleRightTv.setVisibility(View.VISIBLE);
		mBirthdayDialog = new BirthdayDialog(this);
		initData();
		addListener();
	}

	private void addListener() {
		/*tvChangeNewPhone.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				Intent intent = new Intent(SelfMaterialActivity.this,ActivityChangePhone.class);
				startActivity(intent);
			}
		});*/
		
		rlName.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SelfMaterialActivity.this,ActivityChangeName.class));
			}
		});
		
		rlCoachYear.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SelfMaterialActivity.this,ActivityCoachYear.class));
			}
		});
		
		rlAboutSelf.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SelfMaterialActivity.this,ActivityCoachAboutSelf.class));
			}
		});
		
		rlCoachBir.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				mBirthdayDialog.show();
			}
		});
		
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
					Toast.makeText(SelfMaterialActivity.this, "请选择今天以前的日期", Toast.LENGTH_SHORT).show();
					return;
				}
				/*
				 * 显示修改后的日期
				 */
				String monthStr = month < 10 ? "0" + month : "" + month;
				String dayStr = day < 10 ? "0" + day : "" + day;
				tvBirthday.setTextColor(Color.parseColor("#252525"));
				tvBirthday.setText(year + "-" + monthStr + "-" + dayStr);
				hasDate = true;
				setClickable();
			}
		});
	}

	void setClickable() {
		if (!mTitleRightTv.isClickable()) {
			mTitleRightTv.setTextColor(Color.parseColor("#2c2c2c"));
			mTitleRightTv.setClickable(true);
		}
	}
	
	private void setInfo()
	{
		info = CoachApplication.getInstance().getUserInfo();
		if (info.getRealname() != null) {
		tvName.setText(info.getRealname());
	}
		if (info.getGender()!=null)
		{
			if ("1".equals(info.getGender()))
			{
				tvGeny.setText("男");
			}else{
				tvGeny.setText("女");
			}

		}
		
		if (info.getYears() != null) {
			tvTeachYear.setText(info.getYears());
		}
		
		if (info.getSelfeval() != null) {
			tvAboutSelf.setText(info.getSelfeval());
		}
	}

	private void initData() {
		
		info = CoachApplication.getInstance().getUserInfo();
//		if (info != null) {
//			mTotalHours.setText("累计培训学时" + info.getTotaltime() + "小时");
//			mTotalScore.setText("综合评分" + info.getScore() + "分");
//			mRatingBar.setRating(info.getScore());
//		}

		mGenderDialog = new GenderDialog(this);
		mGenderDialog.setOnComfirmClickListener(new GenderDialog.OnComfirmClickListener() {

			@Override
			public void onComfirmBtnClick(String gender) {
				mGenderDialog.dismiss();
				tvGeny.setText(gender);
				new PerfectAccountInfoTask().execute();
//				mGenderTv.setTextColor(Color.parseColor("#252525"));
				hasGender = true;
				setClickable();
			}
		});

		// 显示图片的配置
		options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		mImageSize = new ImageSize(150, 150);

		ImageLoader.getInstance().loadImage(info.getAvatarurl(), mImageSize, options, new ImageLoadingListener() {

			@Override
			public void onLoadingStarted(String imageUri, View view) {

			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (loadedImage != null) {
					mPortrait.setImageBitmap(loadedImage);
				}
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {

			}
		});
		
		if (info.getBirthday()!=null)
		{
			tvBirthday.setText(info.getBirthday());
		}
		
//		if (info.getDrive_school() !=null)
//		{
//			mSchoolName.setText(info.getDrive_school());
//			mSchoolName.setTextColor(getResources().getColor(R.color.text_black));
//		}

//		if (info.getRealname() != null) {
//			mName.setText(info.getRealname());
//			mName.setSelection(info.getRealname().length());
//		}

//		if (info.getPhone() != null)
//
//			mPhonNumber.setText(info.getPhone().trim());


//		if (info.getDrive_school() != null)
//			mSchoolName.setText(info.getDrive_school());
//
//		if (info.getGender() != null && !info.getGender().equals("0")) {
//			mGenderTv.setTextColor(Color.parseColor("#2c2c2c"));
//			if (info.getGender().equals("1"))
//				mGenderTv.setText("男");
//			else {
//				mGenderTv.setText("女");
//			}
//		}
//
//		mName.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//
//			}
//
//			@Override
//			public void afterTextChanged(Editable arg0) {
//				if (info.getRealname() != null) {
//					if (!arg0.toString().equals(info.getRealname()))
//						mPencilName.setImageResource(R.drawable.pencil_color);
//					else {
//						mPencilName.setImageResource(R.drawable.pencile);
//					}
//				} else {
//					if (arg0.length() > 0)
//						mPencilName.setImageResource(R.drawable.pencil_color);
//					else {
//						mPencilName.setImageResource(R.drawable.pencile);
//					}
//				}
//			}
//		});

//		mPhonNumber.addTextChangedListener(new TextWatcher() {
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
//				if (info.getPhone() != null) {
//					if (!arg0.toString().equals(info.getPhone()))
//						mPencilPhonNumber.setImageResource(R.drawable.pencil_color);
//					else {
//						mPencilPhonNumber.setImageResource(R.drawable.pencile);
//					}
//				} else {
//					if (arg0.length() > 0)
//						mPencilPhonNumber.setImageResource(R.drawable.pencil_color);
//					else {
//						mPencilPhonNumber.setImageResource(R.drawable.pencile);
//					}
//				}
//			}
//		});

//		mSchoolName.addTextChangedListener(new TextWatcher() {
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
//				if (info.getDrive_school() != null) {
//					if (!arg0.toString().equals(info.getDrive_school()))
//						mPencilSchoolName.setImageResource(R.drawable.pencil_color);
//					else {
//						mPencilSchoolName.setImageResource(R.drawable.pencile);
//					}
//				} else {
//					if (arg0.length() > 0)
//						mPencilSchoolName.setImageResource(R.drawable.pencil_color);
//					else {
//						mPencilSchoolName.setImageResource(R.drawable.pencile);
//					}
//				}
//			}
//		});

//		mGenderTv.addTextChangedListener(new TextWatcher() {
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
//				if (info.getGender() != null) {
//					String genderStr = "";
//					if (info.getGender().equals("1")) {
//						genderStr = "男";
//					} else if (info.getGender().equals("2")) {
//						genderStr = "女";
//					}
//					if (!arg0.toString().equals(genderStr))
//						mPencilSex.setImageResource(R.drawable.pencil_color);
//					else {
//						mPencilSex.setImageResource(R.drawable.pencile);
//					}
//				} else {
//					if (arg0.length() > 0)
//						mPencilSex.setImageResource(R.drawable.pencil_color);
//					else {
//						mPencilSex.setImageResource(R.drawable.pencile);
//					}
//				}
//			}
//		});
//		
//		if("".equals(mName.getText().toString()))
//		{
//			mPencilName.setVisibility(View.VISIBLE);
//		}else{
//			mPencilName.setVisibility(View.GONE);
//		}
//		
//		if ("男".equals(mGenderTv.getText().toString())||"女".equals(mGenderTv.getText().toString()))
//		{
//			mPencilSex.setVisibility(View.GONE);
//		}else{
//			mPencilSex.setVisibility(View.VISIBLE);
//		}
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		setInfo();
//		if (!TextUtils.isEmpty(info.getDefaultAddress())) {
//			mUserAddress.setText(info.getDefaultAddress());
//			mUserAddress.setTextColor(Color.parseColor("#2c2c2c"));
//		} else {
//			mUserAddress.setText("未设置");
//			mUserAddress.setTextColor(Color.parseColor("#d2d2d2"));
//		}
	}

	@Click(R.id.title_back)
	void goBack() {
		doFinish();
	}

	@Override
	public void onBackPressed() {
		doFinish();
	}

	private void doFinish() {
		setResult(1002, new Intent().putExtra("refresh", 1));
		finish();
	}

//	@Click(R.id.account_info)
//	void goToAccountPage() {
//		startActivity(new Intent(SelfMaterialActivity.this, AccountInfoActivity_.class));
//	}
//
	@Click(R.id.rl_coach_pro)
	void goToQualityPage() {
		startActivity(new Intent(SelfMaterialActivity.this, ProQualityActivity_.class));
	}
//
//	@Click(R.id.personal_info)
//	void goToPersonalPage() {
//		startActivity(new Intent(SelfMaterialActivity.this, PersonalInfoActivity_.class));
//	}
//
//	@Click(R.id.modify_pass)
//	void goToModifyPassPage() {
//		startActivity(new Intent(SelfMaterialActivity.this, ModifyPassActivity_.class));
//	}
//
	@Click(R.id.rl_coach_address)
	void goToAddressList() {
		startActivity(new Intent(SelfMaterialActivity.this, AddressSetActivity_.class));
	}

//	@Click(R.id.pencil_scholl_name)
//	void showSchoolName() {
////		mPencilSchoolName.setImageResource(R.drawable.pencile_grey);
//		mSchoolName.setFocusable(true);
//		mSchoolName.setFocusableInTouchMode(true);
//		mSchoolName.requestFocus();
//		setClickable();
//	}
//
//	@Click(R.id.enter_school_name)
//	void wuyan() {
////		mPencilSchoolName.setImageResource(R.drawable.pencile_grey);
//		mSchoolName.setFocusable(true);
//		mSchoolName.setFocusableInTouchMode(true);
//		mSchoolName.requestFocus();
//		setClickable();
//	}
//
//	@Click(R.id.pencil_name)
//	void showName() {
//		mName.setFocusable(true);
//		mName.setFocusableInTouchMode(true);
//		mName.requestFocus();
//		setClickable();
//	}
//
//	@Click(R.id.enter_name)
//	void wuyan1() {
//		mName.setFocusable(true);
//		mName.setFocusableInTouchMode(true);
//		mName.requestFocus();
//		setClickable();
//	}


//	@Click(R.id.pencil_phone_number)
//	void showPhoneNumer() {
////		mPencilPhonNumber.setImageResource(R.drawable.pencile_grey);
//		mPhonNumber.setFocusable(true);
//		mPhonNumber.setFocusableInTouchMode(true);
//		mPhonNumber.requestFocus();
//		setClickable();
//	}

//	@Click(R.id.enter_phone_number)
//	void wuyan2() {
//		mPhonNumber.setFocusable(true);
//		mPhonNumber.setFocusableInTouchMode(true);
//		mPhonNumber.requestFocus();
//		setClickable();
//	}


//	@Click(R.id.pencil_sex)
//	void showSelectSex() {
//		mGenderDialog.show();
//	}
//
	@Click(R.id.rl_geny)
	void wuyan3() {
		mGenderDialog.show();
	}

	@Click(R.id.potrait)
	void uploadPortrait() {
		showMyDialog();
	}

	@Click(R.id.title_right_text)
	void uploadProfile() {
//		if (!mGenderTv.getText().toString().equals("男") && !mGenderTv.getText().toString().equals("女")) {
//			CommonUtils.showToast(SelfMaterialActivity.this.getApplicationContext(), "请填写性别");
//		} else {
//			new PerfectAccountInfoTask().execute();
//		}
	}

	private class PerfectAccountInfoTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(SelfMaterialActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);
		String name, school, gender, phone;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "PerfectAccountInfo");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
//			if (mName.isFocusable()) {
//				name = mName.getText().toString();
//				param.put("realname", name);
//			}
//			if (info.getPhone() != null) {
//				phone = info.getPhone();
//				param.put("phone", phone);
//			}
//			if (mSchoolName.isFocusable()) {
//				school = mSchoolName.getText().toString();
//				param.put("drive_school", school);
//			}
			if (tvGeny.getText().toString().equals("男")) {
				param.put("gender", "1");
				gender = "1";
			} else if (tvGeny.getText().toString().equals("女")) {
				param.put("gender", "2");
				gender = "2";
			}
			return accessor.execute(Settings.USER_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.getCode() == 1) {
					// registe ok
					CommonUtils.showToast(SelfMaterialActivity.this.getApplicationContext(), "提交资料成功");
					UserInfo info = new UserInfo();
//					if (name != null)
//						info.saveRealName(name, SelfMaterialActivity.this.getApplicationContext());
//					if (phone != null)
//						info.savePhone(phone, SelfMaterialActivity.this.getApplicationContext());
//					if (school != null)
//						info.saveDriveSchool(school, SelfMaterialActivity.this.getApplicationContext());
					if (gender != null)
						info.saveGender(gender, SelfMaterialActivity.this.getApplicationContext());
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(SelfMaterialActivity.this.getApplicationContext(), result.getMessage());
				}

			} else {
				CommonUtils.showToast(SelfMaterialActivity.this.getApplicationContext(), getString(R.string.net_error));
				if (result.getCode() == 95) {
					CommonUtils.gotoLogin(SelfMaterialActivity.this);
				}
			}

			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 相机返回1001
		if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
			File photoFile = new File(Settings.TEMP_PATH, getPhotoName());
			if (photoFile.exists()) {// 如果照片存在
				cropPhoto(photoFile, 200, 200);
			} else {// 照片不存在,检查是否在data中
				if (data != null && data.hasExtra("data")) {
					Bitmap photo = data.getParcelableExtra("data");
					if (photo != null) {
						// 保存图片到本地
						saveBitmapToFile(photo, photoFile);
						cropPhoto(photoFile, 200, 200);
					} else {
						Toast.makeText(SelfMaterialActivity.this, "照片拍摄失败.", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(SelfMaterialActivity.this, "照片拍摄失败.", Toast.LENGTH_SHORT).show();
				}
			}
		}

		// 相册返回1002
		if (requestCode == ALBUM_REQUEST_CODE && resultCode == RESULT_OK) {
			String photoPath = getPhotoPathByUri(data.getData());
			Bitmap bitmap  = BitmapFactory.decodeFile(photoPath);
			try {
				bitmap.compress(CompressFormat.JPEG, 95, new FileOutputStream(Settings.TEMP_PATH+"/"+PHOTO_TEMP_FILE));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File photoFile = new File(Settings.TEMP_PATH, getPhotoName());
			if (photoPath.length() > 0) {
				cropPhoto(photoFile, 200, 200);
			}
		}
		// 返回裁剪
		if (requestCode == CROP_REQUEST_CODE && resultCode == RESULT_OK) {
			cropFile = new File(Settings.TEMP_PATH, getCropPhotoName());
			if (cropFile.exists()) {
				avatar = CommonUtils.getBitmapFromFile(cropFile, 200, 200);
				if (avatar != null)
					mPortrait.setImageBitmap(avatar);
				new UploadAvatarTask().execute();
			}
		}
	}
	
	public static byte[] File2byte(String filePath)  
    {  
        byte[] buffer = null;  
        try  
        {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream();  
            byte[] b = new byte[1024];  
            int n;  
            while ((n = fis.read(b)) != -1)  
            {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        }  
        catch (FileNotFoundException e)  
        {  
            e.printStackTrace();  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        return buffer;  
    }  

	File cropFile;

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
	public void cropPhoto(File file, int width, int height) {
		CROP_TEMP_FILE = "Crop_" + System.currentTimeMillis() + ".jpeg";
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(Uri.fromFile(file), "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", width);
		intent.putExtra("aspectY", height);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("scale", true);// 黑边
		intent.putExtra("scaleUpIfNeeded", true);// 黑边
		intent.putExtra("outputX", width);
		intent.putExtra("outputY", height);
		intent.putExtra("return-data", true);
		intent.putExtra("output", Uri.fromFile(new File(Settings.TEMP_PATH, CROP_TEMP_FILE))); // 专入目标文件
		startActivityForResult(intent, Settings.CROP);
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
	
//	  public static void convert(String source, String formatName, String result) {
//	        try {
//	            File f = new File(source);
//	            f.canRead();
////	            BufferedImage src = ImageIO.read(f);
////	            ImageIO.write(src, formatName, new File(result));
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	    }

	    /**
	     * @param args
	     */
//	    public static void main(String[] args) {
//	        // TODO Auto-generated method stub
//
//
//	        ImgConverter.
//
//	    }

	
	public File saveBitmapToFileForPng(Bitmap bitmap, File file) {
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

			return tempPicFile;

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
		return null;
	}

	Dialog mPhotoDialog;

	private void showMyDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(SelfMaterialActivity.this);
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
					Toast.makeText(SelfMaterialActivity.this, "SD卡不存在,不能拍照,请检查手机SD卡", Toast.LENGTH_SHORT).show();
					return;
				}
				// 每次拍照重新生成文件名称
				PHOTO_TEMP_FILE = "Image_" + System.currentTimeMillis() + ".jpg";
				File tempFile = new File(Settings.TEMP_PATH, PHOTO_TEMP_FILE);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
				startActivityForResult(intent, Settings.CAMERA);
				mPhotoDialog.dismiss();
			}
		});

		album.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				PHOTO_TEMP_FILE = "Image_" + System.currentTimeMillis() + ".jpg";
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, Settings.ALBUM);
				mPhotoDialog.dismiss();
			}
		});
	}

	private class UploadAvatarTask extends AsyncTask<Void, Void, ChangeAvatarResult> {
		JSONAccessor accessor = new JSONAccessor(SelfMaterialActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST_MULTIPART);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected ChangeAvatarResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			ChangeAvatarReq param = new ChangeAvatarReq();
			param.setAction("ChangeAvatar");
			param.setAvatar(cropFile);
			param.setCoachid(CoachApplication.getInstance().getUserInfo().getCoachid());
			param.setToken(CoachApplication.getInstance().getUserInfo().getToken());
			return accessor.execute(Settings.USER_URL, param, ChangeAvatarResult.class);
		}

		@Override
		protected void onPostExecute(ChangeAvatarResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					// registe ok
					CommonUtils.showToast(SelfMaterialActivity.this.getApplicationContext(), "上传头像成功");
					CoachApplication.getInstance().getUserInfo().saveAvatarUrl(result.getAvatarurl(), SelfMaterialActivity.this.getApplicationContext());
				} else {
					if (result.getMessage() != null && result.getCode() == 2)
						CommonUtils.showToast(SelfMaterialActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(SelfMaterialActivity.this);
					}
				}

			} else {
				CommonUtils.showToast(SelfMaterialActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}
	}
}
