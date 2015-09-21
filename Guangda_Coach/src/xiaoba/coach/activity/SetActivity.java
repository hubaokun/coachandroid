package xiaoba.coach.activity;


import java.io.File;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.utils.FileUtilLj;
import xiaoba.coach.views.BaseDialog;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@EActivity(R.layout.activity_set)
public class SetActivity extends BaseActivity {
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.receive_notice)
	ImageView mNotice;
	@ViewById(R.id.advice_return)
	LinearLayout mAdvice;
	@ViewById(R.id.about_us)
	LinearLayout mAbout;
	@ViewById(R.id.btn_logout)
	Button mLogOut;
	@ViewById(R.id.set_title)
	RelativeLayout mTitleLayout;
	@ViewById(R.id.rules_coach)
	LinearLayout llRules;
	@ViewById(R.id.ll_clear)
	LinearLayout llClear;
	@ViewById(R.id.tv_clear)
	TextView tvClear;
	boolean isReceiveNotice;

	@AfterViews
	void init() {
		mTitle.setText("设置");
		mTitleBack.setImageResource(R.drawable.back_arrow);
		mTitle.setTextColor(Color.parseColor("#2c2c2c"));
		mTitleLayout.setBackgroundColor(Color.parseColor("#ffffff"));
		tvClear.setText(getFileSize());
	}

	@Click(R.id.title_back)
	void goBack() {
		finish();
	}

	@Click(R.id.receive_notice)
	void switchNoticeState() {
		if (isReceiveNotice) {
			mNotice.setImageResource(R.drawable.button_off);
		} else {
			mNotice.setImageResource(R.drawable.button_on);
		}

		isReceiveNotice = !isReceiveNotice;

	}

	@Click(R.id.advice_return)
	void gotoAdvicePage() {
		startActivity(new Intent(SetActivity.this, AdviceBackActivity_.class));
	}

	@Click(R.id.about_us)
	void gotoAboutUsPage() {
		startActivity(new Intent(SetActivity.this, AboutUsActivity_.class));
	}

	@Click(R.id.btn_logout)
	void logout() {
		CoachApplication.mUserInfo = null;
		Editor editor = SetActivity.this.getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit();
		editor.clear();
		editor.commit();
		CoachApplication.getInstance().removeAllActivity();		
		if (!SetActivity.this.isFinishing())
			SetActivity.this.finish();
		startActivity(new Intent(SetActivity.this, LoginActivity_.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	}
	
	@Click(R.id.rules_coach)
	void CoachRules()
	{
		Intent intent = new Intent (SetActivity.this,ActivityCoachRules.class);
		startActivity(intent);
	}
	
	@Click(R.id.ll_clear)
	void clearCacho()
	{
		new ClearDialog(this).show();
	}
	
	public String getFileSize() {
		String str = FileUtilLj.formatFileSize(FileUtilLj.getFileSize(new File(Settings.TEMP_PATH)));
		return str;
	}
	
	private class ClearDialog extends BaseDialog {

		public ClearDialog(Context context) {
			super(context, R.style.dialog);
		}

		public ClearDialog(Context context, int theme) {
			super(context, R.style.dialog);
		}

		public ClearDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
			super(context, cancelable, cancelListener);
		}

		@Override
		protected int getLayoutId() {
			return R.layout.clear_dialog;
		}

		@Override
		protected void findViews() {
			findViewById(R.id.tv_clear).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					cleanCache();
					tvClear.setText(getFileSize());
					dismiss();
				}
			});
			findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
		}

		@Override
		protected void setWindowParam() {
			setWindowParams(-1, -2, Gravity.CENTER);
		}

	}
	
	public void cleanCache() {
		/**
		 * 清理缓存
		 */
		File cacheFolder = new File(Settings.PIC_PATH);

		// 清理所有子文件
		for (File file : cacheFolder.listFiles()) {
			if (!file.isDirectory())
				file.delete();
		}
		cacheFolder = new File(Settings.TEMP_PATH);

		// 清理所有子文件
		for (File file : cacheFolder.listFiles()) {
			if (!file.isDirectory()) {
				file.delete();
			}
		}
		Toast.makeText(this, "缓存已清除！", 0).show();
	}
}
