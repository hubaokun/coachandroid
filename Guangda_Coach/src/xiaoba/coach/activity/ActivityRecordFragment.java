package xiaoba.coach.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import xiaoba.coach.R;
import xiaoba.coach.activity.HomeActivity.NewMsgReceiver;
import xiaoba.coach.fragment.ShareRecordFragmentCoach;
import xiaoba.coach.fragment.ShareRecordFragmentStudent;

public class ActivityRecordFragment extends FragmentActivity {
	private TextView tvCoach,tvStudent;
	private ImageView mBackIv;
	private ViewPager mViewPager;
	private TabAdapter mTabAdapter;
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		 setContentView(R.layout.share_record_fragment);
		mContext = ActivityRecordFragment.this;
		findView();
		addListener();
		initView();
//		showAdver = new ShowAdvertisementDialog(mContext);
	}

	private void findView() {
		// TODO Auto-generated method stub
		mBackIv = (ImageView)findViewById(R.id.iv_back);
		tvCoach = (TextView)findViewById(R.id.tv_title_left);
		tvStudent = (TextView)findViewById(R.id.tv_title_right);
		mViewPager = (ViewPager)findViewById(R.id.viewPager_tab);
	}

	private void addListener() {
		// TODO Auto-generated method stub
		mBackIv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		tvCoach.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tvCoach.setSelected(true);
				tvStudent.setSelected(false);
				mViewPager.setCurrentItem(0);
			}
		});
		
		tvStudent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				tvCoach.setSelected(false);
				tvStudent.setSelected(true);
				mViewPager.setCurrentItem(1);
			}
		});
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					tvCoach.performClick();
					break;
				case 1:
					tvStudent.performClick();
					break;
				}
			}

		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		tvCoach.setSelected(true);

		mTabAdapter = new TabAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mTabAdapter);
	}
	
	private class TabAdapter extends FragmentPagerAdapter {

		public TabAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			switch (arg0) {
			case 0:
				return new ShareRecordFragmentCoach();
			case 1:
				return new ShareRecordFragmentStudent();
//			case 2:
//				return new FinishedFragment();

			default:
				break;
			}
			return null;
		}

		@Override
		public int getCount() {
			return 2;
		}

	}
}
