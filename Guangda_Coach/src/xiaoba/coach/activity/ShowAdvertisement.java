package xiaoba.coach.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.igexin.sdk.PushManager;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.GetAdvertisementResult;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.utils.ImageLoadSaveTask;

public class ShowAdvertisement extends BaseActivity {
	ImageView imgAdver;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.show_advertisement);
	        initView();
	        initData();
	    }
	private void initData() {
		// TODO Auto-generated method stub
		new GetAdvertisement().execute();
	}
	private void initView() {
		// TODO Auto-generated method stub
		imgAdver = (ImageView)findViewById(R.id.img_advertisement);
	}
	
	private class GetAdvertisement extends AsyncTask<Void, Void, GetAdvertisementResult> {
		JSONAccessor accessor = new JSONAccessor(ShowAdvertisement.this.getApplicationContext(), JSONAccessor.METHOD_POST);

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
//						showAdver.setImage(result.getConfig().getAdvertisement_url());
						   try {
				               new ImageLoadSaveTask(ShowAdvertisement.this, imgAdver).execute(result.getConfig().getAdvertisement_url());
				           } catch (Exception e) {
				               e.printStackTrace();
				           }
						Timer timer = new Timer(); 
						TimerTask task = new TimerTask() {  
						    @Override  
						    public void run() {   
								startActivity(new Intent(ShowAdvertisement.this, HomeActivity_.class));
								ShowAdvertisement.this.finish();
						     } 
						 };
						timer.schedule(task, 1000 * 5); //5秒后
					}
				} else {
					 if (result.getMessage() != null)
					 CommonUtils.showToast(ShowAdvertisement.this.getApplicationContext(), result.getMessage());
				}
			}
			else {
				
			}
		}
	}
}
