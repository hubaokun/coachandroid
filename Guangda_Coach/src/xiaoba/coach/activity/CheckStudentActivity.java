package xiaoba.coach.activity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.ComplaintToMeResult;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.views.PhotoDetailDialog;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.util.DensityUtils;
import com.daoshun.lib.util.FileUtils;
import com.daoshun.lib.view.OnSingleClickListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CheckStudentActivity extends BaseActivity {
	private Context context;
	private ImageView imgIdZhengMian, imgIdFanMian, imgXueYuanZheng;
	private RelativeLayout rlIdZhengMian, rlIdFanMian, rlXueYuanZheng, rlDelIdZhengMian, rlDelIdFanMian, rlDelXueYuanZheng;
	private Dialog mServiceDialog;
	private String PHOTO_TEMP_FILE; // 相机拍照的输出文件名称
	private static int CAMEAR_REQUEST_IDF = 2001;
	private static int CAMEAR_REQUEST_IDB = 2002;
	private static int CAMEAR_REQUEST_CAR = 2003;
	private static int ALBUM_REQUEST_IDF = 3001;
	private static int ALBUM_REQUEST_IDB = 3002;
	private static int ALBUM_REUQEST_CAR = 3003;
	private String IDFace = "student_idface";
	private String IDBack = "student_idback";
	private String CarPic = "student_carpic";
	private File idCardFaceFile;
	private File idCardBackFile;
	private File cardPicFile;
	private TextView tvTitleRight;
	private Bundle bundle;
	private int StudentId;
	ImageView mTitleLeft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_check);
		context = this;
		bundle = getIntent().getExtras();
		StudentId = bundle.getInt("studentId");
		initView();
		addListener();
		initData();
		// showDialog();
	}

	private void initView() {
		imgIdFanMian = (ImageView) findViewById(R.id.img_id_fanmian);
		imgIdZhengMian = (ImageView) findViewById(R.id.img_zhengmian);
		imgXueYuanZheng = (ImageView) findViewById(R.id.img_xueyuanzhao);
		rlIdFanMian = (RelativeLayout) findViewById(R.id.rl_id_fanmian);
		rlIdZhengMian = (RelativeLayout) findViewById(R.id.rl_id_zhengmian);
		rlXueYuanZheng = (RelativeLayout) findViewById(R.id.rl_xueyuanzheng);
		rlDelIdFanMian = (RelativeLayout) findViewById(R.id.rl_del_id_fanmian);
		rlDelIdZhengMian = (RelativeLayout) findViewById(R.id.rl_del_id_zhengmian);
		rlDelXueYuanZheng = (RelativeLayout) findViewById(R.id.rl_del_xueyuanzheng);
		tvTitleRight = (TextView) findViewById(R.id.tv_title_right);
		mTitleLeft = (ImageView) findViewById(R.id.img_title_left);
	}

	private void addListener() {
		mTitleLeft.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				finish();
			}
		});
		
		// 点击照相，判断图片文件是否存在，如果存在则显示大图，没有则显示拍照方式
		rlIdZhengMian.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (idCardFaceFile == null) {
					showMyDialog(CAMEAR_REQUEST_IDF, ALBUM_REQUEST_IDF);
				} else {
					PhotoDetailDialog photoDetail = new PhotoDetailDialog(context, idCardFaceFile);
				}
			}
		});

		rlDelIdZhengMian.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				imgIdZhengMian.setImageBitmap(null);
				idCardFaceFile = null;
				rlDelIdZhengMian.setVisibility(View.GONE);
			}
		});

		rlIdFanMian.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				if (idCardBackFile == null) {
					showMyDialog(CAMEAR_REQUEST_IDB, ALBUM_REQUEST_IDB);
				} else {
					PhotoDetailDialog photoDetail = new PhotoDetailDialog(context, idCardBackFile);
				}
			}
		});

		rlDelIdFanMian.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				imgIdFanMian.setImageBitmap(null);
				idCardBackFile = null;
				rlDelIdFanMian.setVisibility(View.GONE);
			}
		});

		rlXueYuanZheng.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				if (cardPicFile == null) {
					showMyDialog(CAMEAR_REQUEST_CAR, ALBUM_REUQEST_CAR);
				} else {
					PhotoDetailDialog photoDetail = new PhotoDetailDialog(context, cardPicFile);
				}
			}
		});

		rlDelXueYuanZheng.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				imgXueYuanZheng.setImageBitmap(null);
				cardPicFile = null;
				rlDelXueYuanZheng.setVisibility(View.GONE);
			}
		});

		tvTitleRight.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				if (idCardBackFile == null) {
					Toast.makeText(context, R.string.no_id_back, Toast.LENGTH_SHORT).show();
					return;
				}

				if (idCardFaceFile == null) {
					Toast.makeText(context, R.string.no_id_face, Toast.LENGTH_SHORT).show();
					return;
				}

				if (cardPicFile == null) {
					Toast.makeText(context, R.string.no_car_pic, Toast.LENGTH_SHORT).show();
					return;
				}

				new setStudentCheck().execute();
			}
		});
	}

	private void initData() {

	}

	private void showMyDialog(final int camearCode, final int albumCode) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		mServiceDialog = builder.create();
		mServiceDialog.show();
		mServiceDialog.setContentView(R.layout.chose_photo_way);
		mServiceDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		WindowManager.LayoutParams params = mServiceDialog.getWindow().getAttributes();
		int maxWidth = Settings.DISPLAY_WIDTH - DensityUtils.dp2px(context, 40);
		int textWidth = DensityUtils.dp2px(context, 17 * 15);
		if (maxWidth > textWidth)
			params.width = textWidth;
		else
			params.width = maxWidth;
		params.gravity = Gravity.CENTER;
		mServiceDialog.getWindow().setAttributes(params);
		mServiceDialog.setCanceledOnTouchOutside(true);

		TextView choseCamear = (TextView) mServiceDialog.findViewById(R.id.chose_camear);
		TextView choseAlbum = (TextView) mServiceDialog.findViewById(R.id.chose_album);
		choseCamear.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				if (!CommonUtils.isExitsSdcard()) {
					Toast.makeText(CheckStudentActivity.this, "SD卡不存在,不能拍照,请检查手机SD卡", Toast.LENGTH_SHORT).show();
					return;
				}
				switch (camearCode) {
				case 2001:
					PHOTO_TEMP_FILE = "Image_" + IDFace + ".jpg";
					break;
				case 2002:
					PHOTO_TEMP_FILE = "Image_" + IDBack + ".jpg";
					break;
				case 2003:
					PHOTO_TEMP_FILE = "Image_" + CarPic + ".jpg";
					break;
				default:
					break;
				}
				// PHOTO_TEMP_FILE = "Image_" + IDFace + ".jpg";
				File tempFile = new File(Settings.TEMP_PATH, PHOTO_TEMP_FILE);
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
				startActivityForResult(intent, camearCode);
				mServiceDialog.dismiss();
			}
		});

		choseAlbum.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, albumCode);
				mServiceDialog.dismiss();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		String photoPath = null;
		// 相机返回
		if (2001 <= requestCode && requestCode <= 2003 && resultCode == RESULT_OK) {
			File photoFile = new File(Settings.TEMP_PATH, getPhotoName());

			if (photoFile.exists()) {// 如果照片存在
				// cropPhoto(photoFile, 200, 200, requestCode + 14);
				switch (requestCode) {
				case 2001:
					idCardFaceFile = new File(Settings.TEMP_PATH, getPhotoName());
					setIdFace();
					break;
				case 2002:
					idCardBackFile = new File(Settings.TEMP_PATH, getPhotoName());
					setIdBack();
					break;
				case 2003:
					cardPicFile = new File(Settings.TEMP_PATH, getPhotoName());
					setCarPic();
					break;
				default:
					break;
				}
			} else {// 照片不存在,检查是否在data中
				if (data != null && data.hasExtra("data")) {
					Bitmap photo = data.getParcelableExtra("data");
					if (photo != null) {
						switch (requestCode) {
						case 2001:
							idCardFaceFile = new File(Settings.TEMP_PATH, getPhotoName());
							if (idCardFaceFile.exists()) {
								// Bitmap bitmap = CommonUtils.getBitmapFromFile(idCardFaceFile,200,200);
								photo = centerSquareScaleBitmap(photo, 110);
								imgIdZhengMian.setImageBitmap(photo);
							}
							rlDelIdZhengMian.setVisibility(View.VISIBLE);
							break;
						case 2002:
							idCardBackFile = new File(Settings.TEMP_PATH, getPhotoName());
							if (idCardBackFile.exists()) {
								// Bitmap bitmap = CommonUtils.getBitmapFromFile(idCardBackFile,200,200);
								photo = centerSquareScaleBitmap(photo, 110);
								imgIdFanMian.setImageBitmap(photo);
							}
							rlDelIdFanMian.setVisibility(View.VISIBLE);
							break;
						case 2003:
							cardPicFile = new File(Settings.TEMP_PATH, getPhotoName());
							if (cardPicFile.exists()) {
								// Bitmap bitmap = CommonUtils.getBitmapFromFile(cardPicFile,200,200);
								photo = centerSquareScaleBitmap(photo, 110);
								imgXueYuanZheng.setImageBitmap(photo);
							}
							rlDelXueYuanZheng.setVisibility(View.VISIBLE);
							break;
						default:
							break;
						}
						// 保存图片到本地
						// saveBitmapToFile(photo, photoFile);
						// cropPhoto(photoFile, 200, 200, requestCode + 14);
					} else {
						Toast.makeText(CheckStudentActivity.this, "照片拍摄失败.", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(CheckStudentActivity.this, "照片拍摄失败.", Toast.LENGTH_SHORT).show();
				}
			}
		}

		// 相册返回
		if (3001 <= requestCode && requestCode <= 3003 && resultCode == RESULT_OK) {
			photoPath = getPhotoPathByUri(data.getData());
			if (photoPath.length() > 0) {
				// cropPhoto(new File(photoPath), 200, 200, requestCode + 7);
				switch (requestCode) {
				case 3001:
					idCardFaceFile = new File(photoPath);
					setIdFace();
					break;
				case 3002:
					idCardBackFile = new File(photoPath);
					setIdBack();
					break;
				case 3003:
					cardPicFile = new File(photoPath);
					setCarPic();
				default:
					break;
				}
			} else {
				Toast.makeText(CheckStudentActivity.this, "照片获取失败.", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void setIdFace() {
		if (idCardFaceFile.exists()) {
			Bitmap bitmap = CommonUtils.getBitmapFromFile(idCardFaceFile, 200, 200);
			bitmap = centerSquareScaleBitmap(bitmap, 110);
			imgIdZhengMian.setImageBitmap(bitmap);
		}
		rlDelIdZhengMian.setVisibility(View.VISIBLE);
	}

	private void setIdBack() {
		if (idCardBackFile.exists()) {
			Bitmap bitmap = CommonUtils.getBitmapFromFile(idCardBackFile, 200, 200);
			bitmap = centerSquareScaleBitmap(bitmap, 110);
			imgIdFanMian.setImageBitmap(bitmap);
		}
		rlDelIdFanMian.setVisibility(View.VISIBLE);
	}

	private void setCarPic() {
		if (cardPicFile.exists()) {
			Bitmap bitmap = CommonUtils.getBitmapFromFile(cardPicFile, 200, 200);
			bitmap = centerSquareScaleBitmap(bitmap, 110);
			imgXueYuanZheng.setImageBitmap(bitmap);
		}
		rlDelXueYuanZheng.setVisibility(View.VISIBLE);
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

	// 修正bitmap，在bitmap中间截取正方形
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

	private class setStudentCheck extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(CheckStudentActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// if (mLoadingDialog != null)
			// mLoadingDialog.show();
		}

		@Override
		protected BaseResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "StudentCheck");
			param.put("studentid", StudentId);
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());// CoachApplication.getInstance().getUserInfo().getCoachid()
			if(idCardFaceFile!=null)
			{
				idCardFaceFile = compressImageFromFile(idCardFaceFile);
				param.put("idcardf", idCardFaceFile);
			}
			if (idCardBackFile !=null)
			{
				idCardBackFile = compressImageFromFile(idCardBackFile);
				param.put("idcardb", idCardBackFile);
			}
			
			if (cardPicFile!=null)
			{
				cardPicFile = compressImageFromFile(cardPicFile);
				param.put("cardpic", cardPicFile);
			}
			return accessor.execute(Settings.TASK_URL, param, ComplaintToMeResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.getCode() == 1) {
					CommonUtils.showToast(CheckStudentActivity.this.getApplicationContext(), "提交成功");
					idCardFaceFile = null;
					idCardBackFile = null;
					cardPicFile = null;
					CheckStudentActivity.this.finish();
				} else {
					CommonUtils.showToast(CheckStudentActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(CheckStudentActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(CheckStudentActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
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
