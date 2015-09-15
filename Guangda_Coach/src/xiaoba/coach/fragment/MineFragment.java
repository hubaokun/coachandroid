package xiaoba.coach.fragment;

import java.util.HashMap;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.activity.AccessActivity;
import xiaoba.coach.activity.AccountArrangeActivity;
import xiaoba.coach.activity.ActivityGetAllCoupon;
import xiaoba.coach.activity.ActivityMyCoin;
import xiaoba.coach.activity.ActivityMyStudent;
import xiaoba.coach.activity.ActivityShare;
import xiaoba.coach.activity.GetCashActivity_;
import xiaoba.coach.activity.HomeActivity;
import xiaoba.coach.activity.IncomeDetailActivity_;
import xiaoba.coach.activity.NoticeActivity;
import xiaoba.coach.activity.SelfMaterialActivity_;
import xiaoba.coach.activity.SetActivity_;
import xiaoba.coach.common.Settings;
import xiaoba.coach.interfaces.OnScaleHaloListener;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.UserInfo;
import xiaoba.coach.net.result.GetMsgCountResult;
import xiaoba.coach.net.result.RefreshUserMoneyResult;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.views.OverScrollView;
import xiaoba.coach.views.TicketCoinRelusDialog;
import xiaoba.coach.views.OverScrollView.OnScrollListener;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.util.DensityUtils;
import com.daoshun.lib.view.OnSingleClickListener;
import com.google.zxing.oned.rss.FinderPattern;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MineFragment extends Fragment {
	private HomeActivity mActivity;
	private LayoutInflater mInflater;

	private ImageView mPortrait, mSet;
	private TextView mName, mPrice;
	private OverScrollView mOverScrollView;	
	private RelativeLayout mPortraitLayout;
	private LinearLayout mTousuLayout, mEvaluateLayout, mRechargeLayout;
	private RelativeLayout mNoticeLayout;
	private UserInfo userInfo;
//	private TextView mLeftMoney;
//	private TextView mGMoney; // 保证金和冻结金
//	private View bg;
//	private ImageView mBgPolygon;
	private TextView mTousuCount, mEvaluateCount, mNoticeCount, mUnreadCount;
//	private Button mApply;
	DisplayImageOptions options;
	ImageSize mImageSize;
	private LinearLayout mYueLayout;
	float mBalance;
//	Button btnPayCash;
	private TextView tvStudentCount;
	private RatingBar mRatingBar;
	private TextView mHourTv;
	private RelativeLayout rlShare;
	private RelativeLayout rlInfo;
//	private Button btnPayCoins;
	private String coinnum = "0";
	private TextView tv_coin_value;
	private ImageView imgTicketCoin1;
	private ImageView imgTicketCoin2;
	private TicketCoinRelusDialog ticketcoinDialog;
	private TextView tvPhone;
	private TextView tvTime;
	private TextView tvMyMoney;
	private LinearLayout llMyMoney;
	private RelativeLayout rlXiaoBaTicket;
	private RelativeLayout rlXiaoBaCoin;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = (HomeActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mine, container, false);
		mInflater = inflater;
		totalDiastance = DensityUtils.dp2px(mActivity, 168);
		ticketcoinDialog = new TicketCoinRelusDialog(mActivity);
		initView(view);
		addListeners();
		initData();
		return view;
	}

	private void initView(View view) {
		rlInfo = (RelativeLayout)view.findViewById(R.id.rl_info);
		mHourTv = (TextView) view.findViewById(R.id.tv_coupon_value);
		mRatingBar = (RatingBar) view.findViewById(R.id.mine_ratingBar);
//		mGMoney = (TextView) view.findViewById(R.id.mine_gmoney);
//		mLeftMoney = (TextView) view.findViewById(R.id.mine_yue);
		mTousuLayout = (LinearLayout) view.findViewById(R.id.tousu_layout);
		mEvaluateLayout = (LinearLayout) view.findViewById(R.id.evaluate_layout);
		mRechargeLayout = (LinearLayout) view.findViewById(R.id.recharge_layout);
		mNoticeLayout = (RelativeLayout) view.findViewById(R.id.notice_layout);
		rlShare = (RelativeLayout)view.findViewById(R.id.share_layout);
		mPortrait = (ImageView) view.findViewById(R.id.potrait);
		mSet = (ImageView) view.findViewById(R.id.mine_set);
		mName = (TextView) view.findViewById(R.id.name);
		mPrice = (TextView) view.findViewById(R.id.mine_price);
		mOverScrollView = (OverScrollView) view.findViewById(R.id.mine_over_scroll);
		mPortraitLayout = (RelativeLayout) view.findViewById(R.id.portrait_layout);
//		bg = (View) view.findViewById(R.id.mine_bg);
//		mBgPolygon = (ImageView) view.findViewById(R.id.bg_polygon);
		mTousuCount = (TextView) view.findViewById(R.id.tousu_msg_count);
		mEvaluateCount = (TextView) view.findViewById(R.id.evaluation_msg_count);
		mNoticeCount = (TextView) view.findViewById(R.id.notice_msg_count);
//		mUnreadCount = (TextView) view.findViewById(R.id.notice_unread_msg_count);
		mYueLayout = (LinearLayout) view.findViewById(R.id.mine_yue_layout);
//		mApply = (Button) view.findViewById(R.id.mine_apply_btn);
//		btnPayCash = (Button) view.findViewById(R.id.btn_pay_cash);
		tvStudentCount = (TextView) view.findViewById(R.id.tv_student_count);
		int layoutWidth = (Settings.DISPLAY_WIDTH - DensityUtils.dp2px(mActivity, 30)) / 2;
		LayoutParams params = new LayoutParams(layoutWidth, layoutWidth);
		mTousuLayout.setLayoutParams(params);
		mEvaluateLayout.setLayoutParams(params);
		rlShare.setLayoutParams(params);
		mRechargeLayout.setLayoutParams(params);
//		int bgHeight = Settings.DISPLAY_WIDTH * 345 / 640;
//		bg.setLayoutParams(new RelativeLayout.LayoutParams(Settings.DISPLAY_WIDTH, bgHeight));
//		btnPayCoins = (Button)view.findViewById(R.id.btn_pay_coin);
		tv_coin_value=(TextView)view.findViewById(R.id.tv_coin_value);
		imgTicketCoin1 = (ImageView)view.findViewById(R.id.img_ticket_coin_1);
		imgTicketCoin2 = (ImageView)view.findViewById(R.id.img_ticket_coin_2);
		tvPhone = (TextView)view.findViewById(R.id.tv_phone);
		tvTime = (TextView)view.findViewById(R.id.tv_time);
		tvMyMoney = (TextView)view.findViewById(R.id.tv_my_money);
		llMyMoney = (LinearLayout)view.findViewById(R.id.ll_my_money);
		rlXiaoBaTicket = (RelativeLayout)view.findViewById(R.id.rl_xiaoba_ticket);
		rlXiaoBaCoin = (RelativeLayout)view.findViewById(R.id.rl_xiaoba_coin);
	}

	private void addListeners() {
		/*
		 * 跳转设置
		 */
		mSet.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				startActivity(new Intent(mActivity, SetActivity_.class));
			}
		});

		/*
		 * 跳转投诉
		 */
		mTousuLayout.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				Intent intent = new Intent(mActivity, ActivityMyStudent.class);
				startActivity(intent);

			}
		});

		/*
		 * 跳转评价
		 */
		mEvaluateLayout.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				Intent intent = new Intent(mActivity, AccessActivity.class);
				startActivity(intent);
			}
		});

		mNoticeLayout.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				Intent intent = new Intent(mActivity, NoticeActivity.class);
				startActivity(intent);
			}
		});
		
		rlShare.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mActivity,ActivityShare.class);
				startActivity(intent);
			}
		});

//		llMyMoney.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				String account = CoachApplication.getInstance().getUserInfo().getAlipay_account();
//				if (account != null && !account.equals(""))
//					startActivityForResult(new Intent(mActivity, GetCashActivity_.class).putExtra("balance", mBalance), 11);
//				else {
//					Intent intent = new Intent (mActivity,AccountArrangeActivity.class);
//					startActivity(intent);
//					Toast.makeText(mActivity, "请先去添加支付宝账号", Toast.LENGTH_SHORT).show();
//					
//				}
//
//			}
//		});

		rlXiaoBaTicket.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				Intent intent = new Intent(mActivity, ActivityGetAllCoupon.class);
				startActivity(intent);
			}
		});
		
		rlXiaoBaCoin.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent (mActivity,ActivityMyCoin.class);
				Bundle bundle=new Bundle();
				bundle.putString("coinnum",coinnum);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

		/*
		 * 修改个人资料
		 */
		
		rlInfo.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(mActivity, SelfMaterialActivity_.class), 1001);
			}
		});

		mOverScrollView.setScaleHaloListener(new OnScaleHaloListener() {
			@SuppressLint("NewApi") @Override
			public void transform(int overscrollDistance) {
				if (overscrollDistance >= 0 && mScrollDistance == 0) {// 设置过度滚动时，头像区域的位置
					mPortraitLayout.setTranslationY(overscrollDistance);
//					mBgPolygon.setScaleX(1 + overscrollDistance * 1.0f / totalDiastance);
//					mBgPolygon.setScaleY(1 + overscrollDistance * 1.0f / totalDiastance);
				}
				if (overscrollDistance <= 0 && mScrollDistance > 0) {
					mOverUpDistance = mScrollDistance - overscrollDistance;
					if (mOverUpDistance <= totalDiastance) {
						mPortraitLayout.setAlpha(1 - mOverUpDistance * 1.0f / totalDiastance);
						mPortraitLayout.setScaleY(1 - mOverUpDistance * 1.0f / totalDiastance);
						mPortraitLayout.setTranslationY(-mScrollDistance);
					} else {
						mPortraitLayout.setAlpha(0);
						mPortraitLayout.setScaleY(0);
					}
				}
			}
		});

		/*
		 * 充值
		 */
		mRechargeLayout.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				startActivity(new Intent(mActivity, IncomeDetailActivity_.class));
			}
		});
		/*
		 * 余额
		 */
		
		llMyMoney.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				startActivity(new Intent(mActivity, IncomeDetailActivity_.class));
			}
		});
		
		
//		mYueLayout.setOnClickListener(new OnSingleClickListener() {
//
//			@Override
//			public void doOnClick(View v) {
//				startActivity(new Intent(mActivity, IncomeDetailActivity_.class));
//			}
//		});

		/*
		 * 滚动监听
		 */
		mOverScrollView.setOnScrollListener(new OnScrollListener() {
			/*
			 * 滚动距离
			 */
			int deltaY;

			@SuppressLint("NewApi") @Override
			public void onScroll(int l, int t, int oldl, int oldt) {
				deltaY = t - oldt; // 瞬时滚动距离
				mScrollDistance = (int) mOverScrollView.getScrollY(); // 总的滚动长度

				if (mScrollDistance <= totalDiastance) {
					mPortraitLayout.setAlpha(1 - mScrollDistance * 1.0f / totalDiastance);
					mPortraitLayout.setScaleY(1 - mScrollDistance * 1.0f / totalDiastance);
					mPortraitLayout.setTranslationY(-mScrollDistance);
				} else {
					mPortraitLayout.setAlpha(0);
					mPortraitLayout.setScaleY(0);
				}
			}
		});
		
		imgTicketCoin1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ticketcoinDialog.show();
			}
		});
		
		imgTicketCoin2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ticketcoinDialog.show();
			}
		});
	}

	int mScrollDistance;// 滚动距离
	int totalDiastance; // 总长
	int mOverUpDistance;

	private void initData() {
		// 显示图片的配置
		options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

		mImageSize = new ImageSize(300, 300);

		setMoney();
	}

	/*
	 * show local data saved
	 */
	private void setLocalUserInfo() {
		userInfo = CoachApplication.getInstance().getUserInfo();
		if (!TextUtils.isEmpty(userInfo.getRealname()))
		{
			mName.setText(userInfo.getRealname());
		}else{
			mName.setText("未设置");
		}
		
		if (!TextUtils.isEmpty(userInfo.getPhone()))
		{
			tvPhone.setText(userInfo.getPhone());
		}else{
			tvPhone.setText("未设置");
		}
		
		tvTime.setText("已累计培训"+userInfo.getTotaltime()+"个小时");

		SpannableString ss = new SpannableString(userInfo.getPrice() + "元/小时  上车地址");
		ss.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), 0, userInfo.getPrice().length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		ss.setSpan(new ForegroundColorSpan(Color.parseColor("#ffffff")), ss.length() - 4, ss.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		mPrice.setText(ss);

		ImageLoader.getInstance().loadImage(userInfo.getAvatarurl(), mImageSize, options, new ImageLoadingListener() {

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

		mRatingBar.setRating(userInfo.getScore());
	}

	private void setMoney() {
		userInfo = CoachApplication.getInstance().getUserInfo();

		if (userInfo.getMoney() != null)
			tvMyMoney.setText(userInfo.getMoney().contains(".") ? userInfo.getMoney().substring(0, userInfo.getMoney().indexOf(".")) : userInfo.getMoney());
		else {
			tvMyMoney.setText("0");
		}
		/*
		 * 需求修改为隐藏（机智的我,果然改回来了）
		 */
		String fmoney = userInfo.getFmoney() == null ? "0" : userInfo.getFmoney();
		String gmoney = userInfo.getGmoney() == null ? "0" : userInfo.getGmoney();
		if (gmoney.contains("."))
			gmoney = gmoney.substring(0, gmoney.indexOf("."));
		if (fmoney.contains("."))
			fmoney = fmoney.substring(0, fmoney.indexOf("."));
		int lmoneyInt = 0;
		try {
			lmoneyInt = Integer.parseInt(userInfo.getMoney().contains(".") ? userInfo.getMoney().substring(0, userInfo.getMoney().indexOf(".")) : userInfo.getMoney());
		} catch (Exception e) {
		}
		int gmoneyInt = 0;
		try {
			gmoneyInt = Integer.parseInt(gmoney);
		} catch (Exception e) {
		}
		int canUseMoney = lmoneyInt - gmoneyInt;
		/*
		 * 对可用金额处理（小于0时显示为0）
		 */
		if (canUseMoney < 0)
			canUseMoney = 0;
//		SpannableString ss = new SpannableString("(" + canUseMoney + "元可用余额/" + fmoney + "元冻结金)");
//		ss.setSpan(new ForegroundColorSpan(Color.parseColor("#e0483d")), 1, (canUseMoney + "").length() + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//		ss.setSpan(new ForegroundColorSpan(Color.parseColor("#e0483d")), (canUseMoney + "").length() + 7, fmoney.length() + (canUseMoney + "").length() + 7, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//		ss.setSpan(new ForegroundColorSpan(Color.parseColor("#d2d2d2")), ss.toString().indexOf("/"), ss.toString().indexOf("/") + 1, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//		mGMoney.setText(ss);
	}

	@Override
	public void onResume() {
		super.onResume();

		refreshInfo();
	}

	public void refreshInfo() {
		new RefreshUserMoneyTask().execute();

		refreshMsg();

		setLocalUserInfo();
	}

	public void refreshMsg() {
		new GetMessageCountTask().execute();
	}

	private class RefreshUserMoneyTask extends AsyncTask<Void, Void, RefreshUserMoneyResult> {
		JSONAccessor accessor = new JSONAccessor(mActivity.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPostExecute(RefreshUserMoneyResult result) {
			super.onPostExecute(result);
			if (result != null && result.getCode() == 1) {
				if (result.getMoney() == null) {
					result.setMoney("" + 0);
				} else {

					try {
						mBalance = Float.valueOf(result.getMoney()) - Float.valueOf(result.getGmoney());
					} catch (Exception e) {
						mBalance = 0f;
					}
				}
				if (mBalance<0)
				{
					mBalance = 0;
				}
				if (result.getFmoney() == null)
					result.setFmoney("" + 0);
				if (result.getGmoney() == null)
					result.setGmoney("" + 0);
				if (result.getCouponhour() > 0) {
					mHourTv.setText(result.getCouponhour() + "");
				}
				if (!result.getMoney().equals(userInfo.getMoney()) || !result.getFmoney().equals(userInfo.getFmoney()) || !result.getGmoney().equals(userInfo.getGmoney())
						|| result.getCouponhour() != userInfo.getCouponhour()) {
					userInfo.setMoney(result.getMoney());
					userInfo.setFmoney(result.getFmoney());
					userInfo.setGmoney(result.getGmoney());
					userInfo.setCouponhour(result.getCouponhour());
					userInfo.saveUserInfo(userInfo, mActivity);
					setMoney();
				}
				if(result.getCoinnum()>=0){
					tv_coin_value.setText(String.valueOf(result.getCoinnum()));
					coinnum=String.valueOf(result.getCoinnum());
				}
			} else {
				if (result != null && result.getCode() == 95) {
					if (result.getMessage() != null)
						CommonUtils.showToast(mActivity, result.getMessage());
					CommonUtils.gotoLogin(mActivity);
				}
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected RefreshUserMoneyResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("userid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("action", "refreshUserMoney");
			param.put("usertype", "1"); // 1:coach
			return accessor.execute(Settings.SYSTEM_URL, param, RefreshUserMoneyResult.class);
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1001 && data != null) {
			setLocalUserInfo();
		}

		if (requestCode == 11 && data != null) {
			showCashDialog(data.getIntExtra("money", 0));
		}

	}

	Dialog setCashDialog;

	private void showCashDialog(int money) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		setCashDialog = builder.create();
		setCashDialog.show();
		setCashDialog.setContentView(R.layout.dialog_tixian);
		setCashDialog.setCanceledOnTouchOutside(true);
		setCashDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		// WindowManager.LayoutParams params = setDialog.getWindow().getAttributes();

		ImageView cancel = (ImageView) setCashDialog.findViewById(R.id.recharge_cancel);
		TextView tip = (TextView) setCashDialog.findViewById(R.id.money_tip);
		tip.setText("您申请的" + money + "元金额已提交成功，请等待审核，我们会在3个工作日内联系您！");

		cancel.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				setCashDialog.dismiss();
			}
		});
	}

	class GetMessageCountTask extends AsyncTask<Void, Void, GetMsgCountResult> {
		JSONAccessor accessor = new JSONAccessor(mActivity, JSONAccessor.METHOD_POST);

		@Override
		protected GetMsgCountResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetMessageCount");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			return accessor.execute(Settings.CMY_URL, param, GetMsgCountResult.class);
		}

		@Override
		protected void onPostExecute(GetMsgCountResult result) {
			super.onPostExecute(result);
			if (result != null && result.getCode() == 1) {
				mTousuCount.setText("投诉" + (result.getComplaint1() + result.getComplaint2()) + "条");
				mEvaluateCount.setText("评价" + (result.getEvaluation1() + result.getEvaluation2()) + "条");
				if (result.getNoticecount() !=0)
				{
					mNoticeCount.setVisibility(View.VISIBLE);
					mNoticeCount.setText(result.getNoticecount()+"");
				}else{
					mNoticeCount.setVisibility(View.GONE);
				}
				tvStudentCount.setText(result.getStudentcount() + "位");
//				if (result.getNoticecount() == 0)
//					mUnreadCount.setVisibility(View.INVISIBLE);
//				else {
//					mUnreadCount.setVisibility(View.VISIBLE);
//					mUnreadCount.setText("" + result.getNoticecount());
//				}
			} else {
				if (result != null && result.getCode() == 95) {
					CommonUtils.gotoLogin(mActivity);
				}
			}
		}
	}
}
