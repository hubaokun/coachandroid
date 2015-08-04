package xiaoba.coach.activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.R;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

@EActivity(R.layout.activity_about_us)
class AboutUsActivity extends BaseActivity {
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.rating)
	LinearLayout mGoRateing;
	// @ViewById(R.id.check_new_version)
	// LinearLayout mCheckVersion;
	@ViewById(R.id.about_us_title)
	RelativeLayout mTitleLayout;
	@ViewById(R.id.tv_version)
	TextView tvVersion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		/* set it to be full screen */
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	@AfterViews
	void init() {
		mTitle.setText("关于我们");
		mTitleBack.setImageResource(R.drawable.back_arrow);
		mTitle.setTextColor(Color.parseColor("#ffffff"));
		// mTitleLayout.setBackgroundColor(Color.parseColor("#ffffff"));
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String version = packInfo.versionName;
		tvVersion.setText(version);
	}

	@Click(R.id.title_back)
	void goBack() {
		finish();
	}

	@Click(R.id.rating)
	void goRating() {
		Uri uri = Uri.parse("market://details?id=" + getPackageName());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	// @Click(R.id.check_new_version)
	// void checkVersion() {
	// CommonUtils.showToast(AboutUsActivity.this.getApplicationContext(), "当前已是最新版本");
	// }
}
