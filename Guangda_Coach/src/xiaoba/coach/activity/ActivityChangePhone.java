package xiaoba.coach.activity;

import java.util.HashMap;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.view.OnSingleClickListener;

import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.VercodeResult;
import xiaoba.coach.utils.CommonUtils;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityChangePhone extends BaseActivity {
	
	private EditText etNewPhone,etCode;
	private Button btnCode;
	private TextView tvTitleRight;
	private ImageView imgTitleLeft;
	private long mill;
	private CountDownTimer timer = new CountDownTimer(60000, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			mill = millisUntilFinished;
			btnCode.setText((millisUntilFinished / 1000) + "″后重获取");
		}

		@Override
		public void onFinish() {
			cancel();
			btnCode.setText("获取\n验证码");
			btnCode.setSelected(false);
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_phone);
		initView();
		addListener();
		initData();
	}

	private void initData() {
		
	}

	private void addListener() {
		// TODO Auto-generated method stub
		btnCode.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				timer.start();
				new GetVerCodeTask().execute();
			}
		});
		
		imgTitleLeft.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				finish();
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		etNewPhone = (EditText)findViewById(R.id.et_new_phone);
		etCode = (EditText)findViewById(R.id.et_code);
		btnCode = (Button)findViewById(R.id.get_code);
		imgTitleLeft = (ImageView)findViewById(R.id.img_title_left);
		tvTitleRight = (TextView)findViewById(R.id.tv_title_right);
	}
	
	private class GetVerCodeTask extends AsyncTask<Void, Void, VercodeResult> {
		JSONAccessor accessor = new JSONAccessor(ActivityChangePhone.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected VercodeResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetVerCode");
			param.put("phone", etNewPhone.getText().toString().trim());
			param.put("type", "1");
			return accessor.execute(Settings.SUSER_URL, param, VercodeResult.class);
		}

		@Override
		protected void onPostExecute(VercodeResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {

				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(ActivityChangePhone.this.getApplicationContext(), result.getMessage());
					
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ActivityChangePhone.this);
					}
					btnCode.setText("获取\n验证码");
					btnCode.setClickable(true);
				}

			} else {
				CommonUtils.showToast(ActivityChangePhone.this.getApplicationContext(), getString(R.string.net_error));
				btnCode.setText("获取\n验证码");
				btnCode.setClickable(true);
			}
		}
	}

}
