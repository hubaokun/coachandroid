package xiaoba.coach.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.IntentsParamNames;
import xiaoba.coach.common.Settings;
import xiaoba.coach.fragment.DateSetFragment;
import xiaoba.coach.fragment.JobOrderFragment;
import xiaoba.coach.fragment.MineFragment;
import xiaoba.coach.fragment.JobOrderFragment.MyLocationListenner;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.Coachscore;
import xiaoba.coach.net.result.AddressResult;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.GetAdvertisementResult;
import xiaoba.coach.net.result.GetAdvertisementWindowResult;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.utils.ImageLoadSaveTask;
import xiaoba.coach.utils.ImageLoadSaveTask.OnImageLoad;
import xiaoba.coach.views.LoadingDialog;
import xiaoba.coach.views.ShowAdvertisementDialog;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.util.DensityUtils;
import com.daoshun.lib.view.OnSingleClickListener;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.pgyersdk.update.PgyUpdateManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@EActivity(R.layout.activity_home)
public class HomeActivity extends FragmentActivity implements OnTouchListener {
	private FragmentManager mFragmentManager;
	private FragmentTransaction mTransaction;
	private Fragment mCurrentFragment, mFrontFragment;
	private Fragment mFragment;
	private String mFrontTag;
	private String mCurrentfragmentTag = "";
	private int mSelectedModule;
	public CoachApplication mApplication;
//	private ShowAdvertisementDialog showAdver;
	@ViewById(R.id.tab_date_set)
	LinearLayout mTabDateSet;
	@ViewById(R.id.tab_job_order)
	LinearLayout mTabJobOrder;
	@ViewById(R.id.tab_mine)
	LinearLayout mTabMine;
	@ViewById(R.id.tab_date_set_img)
	ImageView mTabDateSetImg;
	@ViewById(R.id.tab_job_order_img)
	ImageView mTabJobOrderImg;
	@ViewById(R.id.tab_mine_img)
	ImageView mTabMineImg;
	@ViewById(R.id.tab_date_set_tv)
	TextView mTabDateSetTv;
	@ViewById(R.id.tab_job_order_tv)
	TextView mTabJobOrderTv;
	@ViewById(R.id.tab_mine_tv)
	TextView mTabMineTv;
	LoadingDialog mLoadingDialog;
	NewMsgReceiver receiver;
	IntentFilter filter;
	private Context mContext;
	private ShowAdvertisementDialog showAdvDialog;
//	private LocationClient locationClient;
//	private MyLocationListenner myListener;
	private String locaCity ;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		mContext = HomeActivity.this;
		receiver = new NewMsgReceiver();
		filter = new IntentFilter();
		filter.addAction("xiaoba.newmsg");
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		showAdvDialog = new ShowAdvertisementDialog(mContext);
//		showAdver = new ShowAdvertisementDialog(mContext);
	}

	@AfterViews
	void init() {
		PgyUpdateManager.register(this, Settings.PGY_APP_ID);
		mApplication = CoachApplication.getInstance();
		if (mApplication != null)
			mApplication.addCurrentActivity(this);
		findViews();
		initViews();
		mLoadingDialog = new LoadingDialog(HomeActivity.this);
		mFragmentManager = getSupportFragmentManager();
		mSelectedModule = R.id.tab_job_order;
		switchModule(mSelectedModule, 1);
		
		
		/*
		 * 填写邀请码页面
		 */
//		try {
//			if (mApplication.isInvited==1)
//			{
////				if (judgmentDate(mApplication.getUserInfo().getAddtime()))
////				{
//					startActivity(new Intent(mContext,ActivityInputRecord.class));
////				}
//			}
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		
//		getPosition();
		new GetAdvertisement().execute();
//		new GetAllAddressTask().execute();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (receiver != null) {
			receiver = null;
		}
	}
	
//	public void getPosition() {
//		locationClient = new LocationClient(this);
//		myListener = new MyLocationListenner();
//		locationClient.registerLocationListener(myListener);
//		if (locationClient != null) {
//			setLocationOption();
//			locationClient.start();
//			locationClient.requestLocation();
//		} else {
//			Log.d("TAG", "locClient is null or not started");
//		}
//	}
	
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(2000); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		option.setIsNeedAddress(true);
//		locationClient.setLocOption(option);
	}
	
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
//			if (locationClient != null)
//				locationClient.stop();
			if (location == null)
				return;
			locaCity = location.getCity();
			if (locaCity.contains("市"))
			{
				locaCity = locaCity.replace("市", "");
			}
			Toast.makeText(mContext, locaCity, 0).show();
//			stopLocClient();
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	
//	private void stopLocClient() {
//		if (locationClient != null && locationClient.isStarted()) {
//			locationClient.stop();
//			locationClient = null;
//		}
//	}

	public class NewMsgReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context arg0, Intent data) {
			showNewMsgDialog(data.getStringExtra("content"));
			abortBroadcast(); // Receiver接收到广播后中断广播
		}
	}

	public int getmSelectedModule() {
		return mSelectedModule;
	}

	public void setmSelectedModule(int mSelectedModule) {
		this.mSelectedModule = mSelectedModule;
	}

	private void initViews() {

	}

	private void findViews() {

	}

	@Override
	protected void onResume() {
		super.onResume();
		//PgyFeedbackShakeManager.register(HomeActivity.this, Settings.PGY_APP_ID);
		registerReceiver(receiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		//PgyFeedbackShakeManager.unregister();
		unregisterReceiver(receiver);
	}

	public void switchModule(int moudleId, int preMoudleId) {

		if (moudleId != preMoudleId) {

			mFrontTag = "fragment" + moudleId;
			mTransaction = mFragmentManager.beginTransaction();
			// 保存当前显示fragment标志
			mCurrentFragment = mFragmentManager.findFragmentByTag(mCurrentfragmentTag);

			if (mCurrentFragment != null) {
				// mTransaction.detach(mCurrentFragment);
				mTransaction.hide(mCurrentFragment);
			}
			// 要显示的fragment，存在attach,不存在创建
			mFrontFragment = mFragmentManager.findFragmentByTag(mFrontTag);
			if (mFrontFragment != null) {
				// mTransaction.attach(mFrontFragment);
				mTransaction.show(mFrontFragment);
			} else {
				mFrontFragment = makeFragment(moudleId);
				mTransaction.add(R.id.home_content, mFrontFragment, mFrontTag);
			}
			// 保存当前显示fragment标志
			mCurrentfragmentTag = mFrontTag;
			mTransaction.commit();
			selectColor(moudleId);
		}
	}

	private Fragment makeFragment(int moudleId) {

		switch (moudleId) {
		case R.id.tab_job_order:
			mFragment = new JobOrderFragment();
			break;
		case R.id.tab_date_set:
			mFragment = new DateSetFragment();
			break;
		case R.id.tab_mine:
			mFragment = new MineFragment();
			break;
		default:
			break;
		}
		return mFragment;
	}

	@SuppressWarnings("deprecation")
	private void selectColor(int moudleId) {

		/*
		 * set selected state
		 */
		switch (moudleId) {
		case R.id.tab_job_order:
			mTabJobOrder.setSelected(true);
			mTabJobOrderImg.setImageResource(R.drawable.joborder);
			mTabJobOrderTv.setTextColor(Color.parseColor("#1c1c1c"));
			break;
		case R.id.tab_date_set:
			mTabDateSet.setSelected(true);
			mTabDateSetImg.setImageResource(R.drawable.date_set_black);
			mTabDateSetTv.setTextColor(Color.parseColor("#1c1c1c"));
			break;
		case R.id.tab_mine:
			mTabMine.setSelected(true);
			mTabMineImg.setImageResource(R.drawable.mine_black);
			mTabMineTv.setTextColor(Color.parseColor("#1c1c1c"));
			break;
		default:
			break;
		}

		/*
		 * restore the unselected state of last selected item
		 */
		if (mSelectedModule != moudleId) {
			switch (mSelectedModule) {
			case R.id.tab_job_order:
				mTabJobOrder.setSelected(false);
				mTabJobOrderImg.setImageResource(R.drawable.joborder_white);
				mTabJobOrderTv.setTextColor(Color.parseColor("#d8d8d8"));
				break;
			case R.id.tab_date_set:
				mTabDateSet.setSelected(false);
				mTabDateSetImg.setImageResource(R.drawable.date_set);
				mTabDateSetTv.setTextColor(Color.parseColor("#d8d8d8"));
				break;
			case R.id.tab_mine:
				mTabMine.setSelected(false);
				mTabMineImg.setImageResource(R.drawable.mine);
				mTabMineTv.setTextColor(Color.parseColor("#d8d8d8"));
				break;
			default:
				break;
			}
		}
		mSelectedModule = moudleId;
	}

	@Click(R.id.tab_job_order)
	void clickJogOrder() {
		switchModule(R.id.tab_job_order, mSelectedModule);
		if (mFragmentManager.findFragmentByTag("fragment" + R.id.tab_job_order) != null)
			((JobOrderFragment) mFragmentManager.findFragmentByTag("fragment" + R.id.tab_job_order)).getmListView().setRefreshing();
	}

	@Click(R.id.tab_date_set)
	void clickDateSet() {
		switchModule(R.id.tab_date_set, mSelectedModule);
	}

	@Click(R.id.tab_mine)
	void clickMine() {
		switchModule(R.id.tab_mine, mSelectedModule);
		if (mFragmentManager.findFragmentByTag("fragment" + R.id.tab_mine) != null)
			((MineFragment) mFragmentManager.findFragmentByTag("fragment" + R.id.tab_mine)).refreshInfo();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1002) {
			if (data != null) {
				Coachscore score = new Coachscore();
				score.setScore1(data.getIntExtra(IntentsParamNames.RATINGAT, 0));
				score.setScore2(data.getIntExtra(IntentsParamNames.RATINGQU, 0));
				score.setScore3(data.getIntExtra(IntentsParamNames.RATINGLO, 0));
				score.setScore((data.getIntExtra(IntentsParamNames.RATINGAT, 0) + data.getIntExtra(IntentsParamNames.RATINGQU, 0) + data.getIntExtra(IntentsParamNames.RATINGLO, 0)) / 3);
				score.setContent(data.getStringExtra(IntentsParamNames.COMMENT_CONTENT));

				int orderId = data.getIntExtra(IntentsParamNames.ORDERID, -1);
				new EvaluateTask(score, orderId).execute();
			} else {

			}
			/*
			 * 2xxx (request from fragment DatesetFragment)
			 */
		} else if (requestCode == 2001) {
			if (mFragmentManager.findFragmentByTag("fragment" + R.id.tab_date_set) != null)
				mFragmentManager.findFragmentByTag("fragment" + R.id.tab_date_set).onActivityResult(requestCode, resultCode, data);
		} else {
			/*
			 * 申请提现返回
			 */
			if (mFragmentManager.findFragmentByTag("fragment" + R.id.tab_mine) != null)
				mFragmentManager.findFragmentByTag("fragment" + R.id.tab_mine).onActivityResult(requestCode, resultCode, data);
		}

	}

	/*
	 * submit the coach's evaluation to his/her student
	 */
	private class EvaluateTask extends AsyncTask<Void, Void, BaseResult> {

		JSONAccessor accessor = new JSONAccessor(HomeActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		Coachscore score;
		int orderid;

		public EvaluateTask(Coachscore score, int orderid) {
			this.score = score;
			this.orderid = orderid;
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
			param.put("action", "EvaluationTask");
			param.put("userid", mApplication.getUserInfo().getCoachid());
			param.put("type", "1"); // 1: coach to student
			param.put("orderid", orderid);
			param.put("score1", score.getScore1()); //
			param.put("score2", score.getScore2()); //
			param.put("score3", score.getScore3()); //
			param.put("content", score.getContent()); //
			return accessor.execute(Settings.TASK_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					CommonUtils.showToast(HomeActivity.this.getApplicationContext(), "评价成功");
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(HomeActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(HomeActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(HomeActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// if (mSelectedModule == R.id.tab_date_set) {
		// return ((DateSetFragment) mFragmentManager.findFragmentByTag("fragment" + R.id.tab_date_set)).onTouch(arg0, arg1);
		// }
		return false;
	}

	public void refreshMineMsg() {
		if (mFragmentManager.findFragmentByTag("fragment" + R.id.tab_mine) != null)
			((MineFragment) (mFragmentManager.findFragmentByTag("fragment" + R.id.tab_mine))).refreshMsg();
	}

	private Dialog mServiceDialog;

	public void showNewMsgDialog(String content) {
		AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
		mServiceDialog = builder.create();
		mServiceDialog.show();
		mServiceDialog.setContentView(R.layout.dialog_new_msg);
		mServiceDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		WindowManager.LayoutParams params = mServiceDialog.getWindow().getAttributes();
		int maxWidth = Settings.DISPLAY_WIDTH - DensityUtils.dp2px(HomeActivity.this, 40);
		int textWidth = DensityUtils.dp2px(HomeActivity.this, 17 * 15);
		if (maxWidth > textWidth)
			params.width = textWidth;
		else
			params.width = maxWidth;
		params.gravity = Gravity.CENTER;
		mServiceDialog.getWindow().setAttributes(params);
		mServiceDialog.setCanceledOnTouchOutside(true);

		TextView contentTv = (TextView) mServiceDialog.findViewById(R.id.new_msg_content);
		TextView mButton = (TextView) mServiceDialog.findViewById(R.id.new_msg_confirm);
		contentTv.setText(content);
		mButton.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (mSelectedModule == R.id.tab_job_order)
				{
					JobOrderFragment.mListView.setRefreshing();
				}
				mServiceDialog.dismiss();
			}
		});

	}
	
	public static boolean judgmentDate(String date1) throws Exception { 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d HH:mm:ss"); 
        Date addDate = sdf.parse(date1); 
 
        Date Today = new Date(); 
 
        long cha = Today.getTime() - addDate.getTime(); 
 
        if(cha<0){
 
          return false; 
 
        }
 
        double result = cha * 1.0 / (1000 * 60 * 60);
 
        if(result<=6){ 
 
             return true; 
 
        }else{ 
 
             return false; 
 
        } 
 
    }
	
	private class GetAdvertisement extends AsyncTask<Void, Void, GetAdvertisementWindowResult> {

		JSONAccessor accessor = new JSONAccessor(HomeActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		Coachscore score;
		int orderid;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected GetAdvertisementWindowResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GETADVERTISEMENT");
			param.put("coachid", mApplication.getUserInfo().getCoachid());
			param.put("type", "1"); // 1: coach 2 student
			param.put("model", "2"); //1:ios 2 android
			param.put("width",Settings.DISPLAY_WIDTH+"");
			param.put("height",Settings.DISPLAY_HEIGHT+"");
			return accessor.execute(Settings.ADVER_URL, param, GetAdvertisementWindowResult.class);
		}

		@Override
		protected void onPostExecute(final GetAdvertisementWindowResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					if ("1".equals(result.getC_flag()))
					{

						//showAdvDialog.setImage(result.getC_img_android());
						showAdvDialog.setImageAdvertisement(result.getC_img_android());
						ImageLoadSaveTask.setImageShowListener(new OnImageLoad() {
							
							@Override
							public void showCancle(Boolean image) {
								// TODO Auto-generated method stub
								showAdvDialog.show();
							}
						});

						showAdvDialog.imgAdvertisement.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								showAdvDialog.dismiss();
								String url = "";
								String code = "c"+mApplication.mUserInfo.getInvitecode().toLowerCase();
								String name = "";
								if (!TextUtils.isEmpty(CoachApplication.mUserInfo.getRealname()))
								{
								try
								{
									name = new String( URLEncoder.encode(CoachApplication.mUserInfo.getRealname(), "UTF-8"));
								}catch(UnsupportedEncodingException e)
								{
									e.printStackTrace();
								}
								 url = result.getC_url().toString()+"code="+code+"&name="+name;
								}else{
								url = result.getC_url().toString()+"code="+code+"&name=";
								}
								Uri u = Uri.parse(url);  
								 Intent it = new Intent(Intent.ACTION_VIEW, u);
								 HomeActivity.this.startActivity(it); 
							}
						});
					}
					else if ("2".equals(result.getC_flag())){

						showAdvDialog.setImageAdvertisement(result.getC_img_android());
						ImageLoadSaveTask.setImageShowListener(new OnImageLoad() {
							
							@Override
							public void showCancle(Boolean image) {
								// TODO Auto-generated method stub
								showAdvDialog.show();
							}
						});
						showAdvDialog.imgAdvertisement.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								showAdvDialog.dismiss();
								// TODO Auto-generated method stub
								startActivity(new Intent(HomeActivity.this,ActivityShare.class));
							}
						});
					}
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(HomeActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(HomeActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(HomeActivity.this.getApplicationContext(), getString(R.string.net_error));
			}

		}

	}

}
