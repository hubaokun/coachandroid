package xiaoba.coach.activity;

import xiaoba.coach.R;
import xiaoba.coach.fragment.AccessToMeFragment;
import xiaoba.coach.fragment.ComplaintFragment;
import xiaoba.coach.fragment.DateSetFragment;
import xiaoba.coach.fragment.JobOrderFragment;
import xiaoba.coach.fragment.MineFragment;
import xiaoba.coach.fragment.MyAccessFragment;
import xiaoba.coach.views.LoadingDialog;
import xiaoba.coach.views.StudentDetailView;

import com.daoshun.lib.view.OnSingleClickListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AccessActivity extends BaseActivity {
	private TextView tvMyAccess, tvAccessToMe;
	private ImageView imgBack;
	private FragmentManager mFragmentManager;
	private FragmentTransaction mTransaction;
	private Fragment mCurrentFragment, mFrontFragment;
	private Fragment mFragment;
	private String mFrontTag;
	private String mCurrentfragmentTag = "";
	LoadingDialog mLoadingDialog;
	private int mSelectedModule;
	private Context context;
	private RelativeLayout rlLocation/* ,rlBackground */;
	private Display display;
	public StudentDetailView studentDetail;
	private TextView tvComplain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_access);
		context = getApplicationContext();
		initView();
		addListener();
		initData();
		init();
	}

	private void init() {
		mLoadingDialog = new LoadingDialog(AccessActivity.this);
		mFragmentManager = getSupportFragmentManager();
		mSelectedModule = R.id.tv_my_access;
		switchModule(mSelectedModule, 1);
	}

	private void initView() {
		tvMyAccess = (TextView) findViewById(R.id.tv_my_access);
		tvAccessToMe = (TextView) findViewById(R.id.tv_access_to_me);
		imgBack = (ImageView) findViewById(R.id.img_title_left);
		rlLocation = (RelativeLayout) findViewById(R.id.rl_location);
		tvComplain = (TextView)findViewById(R.id.tv_complain);
	}

	private void addListener() {
		imgBack.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				AccessActivity.this.finish();
			}
		});

		tvMyAccess.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				switchModule(R.id.tv_my_access, mSelectedModule);
			}
		});

		tvAccessToMe.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				switchModule(R.id.tv_access_to_me, mSelectedModule);
			}
		});
		
		tvComplain.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				switchModule(R.id.tv_complain, mSelectedModule);
			}
		});
	}

	private void initData() {
		display = getWindowManager().getDefaultDisplay();
		studentDetail = new StudentDetailView(context, rlLocation, display, this);
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
				mTransaction.add(R.id.access_fragment, mFrontFragment, mFrontTag);
			}
			// 保存当前显示fragment标志
			mCurrentfragmentTag = mFrontTag;
			mTransaction.commit();
			selectColor(moudleId);
		}
	}

	private Fragment makeFragment(int moudleId) {

		switch (moudleId) {
		case R.id.tv_my_access:
			mFragment = new MyAccessFragment();
			break;
		case R.id.tv_access_to_me:
			mFragment = new AccessToMeFragment();
			break;
		case R.id.tv_complain:
			mFragment = new ComplaintFragment();
			break;
		default:
			break;
		}
		return mFragment;
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private void selectColor(int moudleId) {

		/*
		 * set selected state
		 */
		switch (moudleId) {
		case R.id.tv_my_access:
			tvMyAccess.setBackground(getResources().getDrawable(R.drawable.left_half_circly_black));
			tvMyAccess.setTextColor(getResources().getColor(R.color.white));

			break;
		case R.id.tv_access_to_me:
			tvAccessToMe.setBackground(getResources().getDrawable(R.drawable.center_black_bar));
			tvAccessToMe.setTextColor(getResources().getColor(R.color.white));

			break;
		case R.id.tv_complain:
			tvComplain.setBackground(getResources().getDrawable(R.drawable.right_half_circly_black));
			tvComplain.setTextColor(getResources().getColor(R.color.white));
			break;
		default:
			break;
		}

		/*
		 * restore the unselected state of last selected item
		 */
		if (mSelectedModule != moudleId) {
			switch (mSelectedModule) {
			case R.id.tv_my_access:
				// tvMyAccess.setSelected(false);
				tvMyAccess.setBackground(getResources().getDrawable(R.drawable.left_half_circly_white));
				tvMyAccess.setTextColor(getResources().getColor(R.color.text_black));
				break;
			case R.id.tv_access_to_me:
				// tvAccessToMe.setSelected(false);
				tvAccessToMe.setBackground(getResources().getDrawable(R.drawable.center_white_bar));
				tvAccessToMe.setTextColor(getResources().getColor(R.color.text_black));
				break;
			case R.id.tv_complain:
				tvComplain.setBackground(getResources().getDrawable(R.drawable.right_half_circly_white));
				tvComplain.setTextColor(getResources().getColor(R.color.text_black));
				break;
			default:
				break;
			}
		}
		mSelectedModule = moudleId;
	}

}
