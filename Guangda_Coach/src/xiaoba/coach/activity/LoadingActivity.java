package xiaoba.coach.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.igexin.sdk.PushManager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.GetAdvertisementResult;
import xiaoba.coach.net.result.RegisteResult;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.utils.DataBaseUtil;
import xiaoba.coach.utils.ImageLoadSaveTask;

@EActivity(R.layout.activity_loading)
public class LoadingActivity extends BaseActivity {
	@ViewById(R.id.loading_version)
	TextView version;
	@ViewById(R.id.img_advertisement)
	ImageView imgAdver;

	Thread loginThread;
	
	private Context mContext;

	@AfterViews
	void init() {
		mContext = this;
		// 初始化个推sdk
		PushManager.getInstance().initialize(this.getApplicationContext()); // 初始化个推sdk

		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		 if (packInfo != null)
		 version.setText("版本" + packInfo.versionName);
		//version.setText("版本");
		 boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	        if(hasSDCard){
	            copyDataBaseToPhone();
	        }else{
	            Toast.makeText(mContext, "未检测到SDCard", Toast.LENGTH_LONG).show();
	        }
	}
	
	private void copyDataBaseToPhone() {
        DataBaseUtil util = new DataBaseUtil(LoadingActivity.this);
        // 判断数据库是否存在
        boolean dbExist = util.checkDataBase();

        if (dbExist) {

        } else {// 不存在就把raw里的数据库写入手机
            try {
                util.copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

	private void doLogin() {
		stopThread = false;
		loginThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (Exception e) {
				}

				if (!stopThread)
					runOnUiThread(new Runnable() {
						public void run() {
							new GetAdvertisement().execute();
						}
					});
			}
		});
		loginThread.start();
	}

	boolean stopThread = false;

	@Override
	protected void onPause() {
		super.onPause();
		stopThread = true;
		loginThread.interrupt();
		loginThread = null;
	}

	@Override
	protected void onResume() {
		super.onResume();
		doLogin();
	}

	/*
	 * do login request
	 */
	private class LoginTask extends AsyncTask<Void, Void, RegisteResult> {

		private String phone;
		private String passWord;

		public LoginTask(String phone, String password) {
			this.phone = phone;
			this.passWord = password;
		}

		JSONAccessor accessor = new JSONAccessor(LoadingActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// if (mLoadingDialog != null)
			// mLoadingDialog.show();
		}

		@Override
		protected RegisteResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("action", "Login");
			param.put("loginid", phone.trim());
			param.put("password", passWord.trim());
			return accessor.execute(Settings.USER_URL, param, RegisteResult.class);
		}

		@Override
		protected void onPostExecute(RegisteResult result) {
			super.onPostExecute(result);
			// if (mLoadingDialog != null && mLoadingDialog.isShowing())
			// mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					// registe ok
					CoachApplication.getInstance().uploadClientId();
					if (result.getUserInfo() != null) {
						mApplication.isInvited = result.getIsInvited();
						mApplication.crewardamount = result.getCrewardamount();
						mApplication.orewardamount = result.getOrewardamount();
						mApplication.getUserInfo().setCoachInfo(result.getUserInfo());
						mApplication.getUserInfo().saveUserInfo(result.getUserInfo(), LoadingActivity.this);
						startActivity(new Intent(LoadingActivity.this, HomeActivity_.class));
						LoadingActivity.this.finish();
						CommonUtils.showToast(LoadingActivity.this.getApplicationContext(), "登录成功");
					} else {
						CommonUtils.showToast(LoadingActivity.this.getApplicationContext(), "用户数据丢失，请联系小巴学车");
					}
				} else {
					// account or password error
					if (result.getMessage() != null)
						CommonUtils.showToast(LoadingActivity.this.getApplicationContext(), result.getMessage());

					/*
					 * 未自动登录
					 */
					startActivity(new Intent(LoadingActivity.this, LoginActivity_.class));
					LoadingActivity.this.finish();
				}
			} else {
				CommonUtils.showToast(LoadingActivity.this.getApplicationContext(), getString(R.string.net_error));
				startActivity(new Intent(LoadingActivity.this, LoginActivity_.class));
				LoadingActivity.this.finish();
			}
		}
	}
	
	private class GetAdvertisement extends AsyncTask<Void, Void, GetAdvertisementResult> {
		JSONAccessor accessor = new JSONAccessor(LoadingActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected GetAdvertisementResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "CHECKCONFIG");
			return accessor.execute(Settings.SYSTEM_URL, param, GetAdvertisementResult.class);
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(final GetAdvertisementResult result) {
			super.onPostExecute(result);

			if (result != null) {
				if (result.getCode() == 1) {
					if (result.getConfig().getAdvertisement_flag() == 1)
					{
						imgAdver.setVisibility(View.VISIBLE);
//						showAdver.setImage(result.getConfig().getAdvertisement_url());
						   try {
				               new ImageLoadSaveTask(LoadingActivity.this, imgAdver).execute(result.getConfig().getAdvertisement_url());
				           } catch (Exception e) {
				               e.printStackTrace();
				           }
						Timer timer = new Timer(); 
						TimerTask task = new TimerTask() {  
						    @Override  
						    public void run() {   
						    	if (mApplication.getUserInfo().getPhone() != null && !mApplication.getUserInfo().getPhone().equals("")) {
									String account = mApplication.getUserInfo().getPhone();
									String password = mApplication.getUserInfo().getPassword();
									new LoginTask(account, password).execute();
								} else {
									startActivity(new Intent(LoadingActivity.this, LoginActivity_.class));
									LoadingActivity.this.finish();
								}
						     }
						 };
						timer.schedule(task, 1000 * 3); //5秒后
					}else{
						imgAdver.setVisibility(View.GONE);
				    	if (mApplication.getUserInfo().getPhone() != null && !mApplication.getUserInfo().getPhone().equals("")) {
							String account = mApplication.getUserInfo().getPhone();
							String password = mApplication.getUserInfo().getPassword();
							new LoginTask(account, password).execute();
						} else {
							startActivity(new Intent(LoadingActivity.this, LoginActivity_.class));
							LoadingActivity.this.finish();
						}
					}
				} else {
					 if (result.getMessage() != null)
					 CommonUtils.showToast(LoadingActivity.this.getApplicationContext(), result.getMessage());
				}
			}
			else {
				
			}
		}
	}
}
