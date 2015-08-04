package xiaoba.coach.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Hashtable;

import com.daoshun.lib.view.OnSingleClickListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.umeng.socialize.sso.UMSsoHandler;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.utils.Untilly;
import xiaoba.coach.views.ShareDialog;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityShare extends BaseActivity {
	private Context context;
	private ImageView imgBack;
	private TextView tvCenter;
	private TextView tvRight;
	private ShareDialog sharedialog;
	private Button btnShare;
	private ImageView imgErWeiMa;
	private TextView tvInviteCode;
	//前景色
	int FOREGROUND_COLOR=0xff000000;
	//背景色
	int BACKGROUND_COLOR=0xffffffff;
	// 图片宽度的一般
	private static final int IMAGE_HALFWIDTH = 50;

	// 插入到二维码里面的图片对象
	private Bitmap mIcon;
	// 需要插图图片的大小 这里设定为40*40
	int[] mPixels = new int[2 * IMAGE_HALFWIDTH * 2 * IMAGE_HALFWIDTH];
	private String inviteCode = "";
	private String shareUrl = "";
	private LinearLayout llTipOne;
	private LinearLayout llTipTwo;
	private TextView tvTipNumOne;
	private TextView tvTipNumTwo;
	private TextView tvTipOne;
	private TextView tvTipTwo;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_share);
			mIcon = BitmapFactory.decodeResource(getResources(),
					R.drawable.ic_launcher);
	        context = this;
	        sharedialog = new ShareDialog(context,ActivityShare.this);
	        initView();
	        initData();
	        addListener();
			try {
				Bitmap bitmap = cretaeBitmap(shareUrl, mIcon);
				imgErWeiMa.setImageBitmap(bitmap);
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	 
	 private void addListener() {
		// TODO Auto-generated method stub
		imgBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		btnShare.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				sharedialog.setShareUrl(shareUrl);
				sharedialog.show();
			}
		});
		
		tvRight.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,ActivityShareRecord.class);
				startActivity(intent);
			}
		});
	}

	@SuppressLint("DefaultLocale") private void initData() {
		// TODO Auto-generated method stub
		tvCenter.setText("邀请教练加入");
		tvCenter.setTextColor(getResources().getColor(R.color.text_black));
		imgBack.setImageResource(R.drawable.back_arrow);
		tvRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.share_next));
		//CommonUtils.showToast(ActivityShare.this.getApplicationContext(), mApplication.getUserInfo().getInvitecode());
		inviteCode = "c"+mApplication.getUserInfo().getInvitecode().toLowerCase();
		tvInviteCode.setText(inviteCode);
		if (!TextUtils.isEmpty(CoachApplication.mUserInfo.getRealname()))
		{
			String name = "";
//			try {
//				 name = changeCharset(CoachApplication.mUserInfo.getRealname(), "UTF-8");
//				
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			try {
				name = new String( URLEncoder.encode(CoachApplication.mUserInfo.getRealname(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			shareUrl = Settings.SHARE+"code="+inviteCode+"&user="+name;
		}else{
			shareUrl = Settings.SHARE+"code="+inviteCode+"&user=";
		}
		
		if (mApplication.crewardamount==0)
		{
			llTipOne.setVisibility(View.GONE);
		}else{
			llTipOne.setVisibility(View.VISIBLE);
//			tvTipNumOne.setText("1、");
			tvTipOne.setText(getResources().getString(R.string.share_tip1_one)+mApplication.crewardamount+getResources().getString(R.string.share_tip1_two));
		}
		
		if (mApplication.orewardamount == 0)
		{
			llTipTwo.setVisibility(View.GONE);
		}else{
			llTipTwo.setVisibility(View.VISIBLE);
//			if (llTipOne.VISIBLE == View.GONE)
//			{
//				tvTipNumTwo.setText("1、");
//			}else{
//				tvTipNumTwo.setText("2、");
//			}
			tvTipTwo.setText(getResources().getString(R.string.share_tip2_one)+mApplication.orewardamount+getResources().getString(R.string.share_tip2_two));
		}
		//Toast.makeText(context, shareUrl, 0).show();
	}
	
	 public String changeCharset(String str, String newCharset)
			   throws UnsupportedEncodingException {
			  if (str != null) {
			   //用默认字符编码解码字符串。
			   byte[] bs = str.getBytes();
			   //用新的字符编码生成字符串
			   return new String(bs, newCharset);
			  }else{
				  return "";
			  }
			 }

	private void initView()
	 {
		 tvCenter = (TextView)findViewById(R.id.title);
		 imgBack = (ImageView)findViewById(R.id.title_back_img);
		 tvRight = (TextView)findViewById(R.id.title_right_text);
		 btnShare = (Button)findViewById(R.id.btn_share);
		 imgErWeiMa = (ImageView)findViewById(R.id.img_erweima);
		 tvInviteCode = (TextView)findViewById(R.id.tv_invitecode);
		 llTipOne = (LinearLayout)findViewById(R.id.ll_top_one);
		 llTipTwo = (LinearLayout)findViewById(R.id.ll_top_two);
		 tvTipNumOne = (TextView)findViewById(R.id.tv_number_one);
		 tvTipNumTwo = (TextView)findViewById(R.id.tv_number_two);
		 tvTipOne = (TextView)findViewById(R.id.tv_tips_one);
		 tvTipTwo = (TextView)findViewById(R.id.tv_tops_two);
	 }
	
	public Bitmap cretaeBitmap(String str, Bitmap icon) throws WriterException {
		// 缩放一个40*40的图片
		icon = Untilly.zoomBitmap(icon, IMAGE_HALFWIDTH);
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//		hints.put(EncodeHintType.MARGIN, 1);
		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		BitMatrix matrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, 500, 500, hints);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 二维矩阵转为一维像素数组,也就是一直横着排了
		int halfW = width / 2;
		int halfH = height / 2;
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
						&& y > halfH - IMAGE_HALFWIDTH
						&& y < halfH + IMAGE_HALFWIDTH) {
					pixels[y * width + x] = icon.getPixel(x - halfW
							+ IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
				} else {
					if (matrix.get(x, y)) {
						pixels[y * width + x] = FOREGROUND_COLOR;
					} else { // 无信息设置像素点为白色
						pixels[y * width + x] = BACKGROUND_COLOR;
					}
				}

			}
		}
		Bitmap bitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		// 通过像素数组生成bitmap
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

		return bitmap;
	}
}
