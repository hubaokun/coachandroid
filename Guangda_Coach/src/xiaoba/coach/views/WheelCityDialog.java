package xiaoba.coach.views;

import java.util.ArrayList;

import db.DBManager;

import xiaoba.coach.R;
import xiaoba.coach.net.result.Area;
import xiaoba.coach.net.result.City;
import xiaoba.coach.net.result.Province;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 城市选择对话框
 * 
 * @author liulj
 * 
 */
public class WheelCityDialog extends BaseDialog {
	private WheelView mCityWheel, mZoneWheel,mProvinceWheel;
	private OnComfirmClickListener mOnComfirmClickListener;
	private boolean scrolling;
    private ArrayList<City> citylist;
    private ArrayList<Province> provincelist;
    private ArrayList<Area> zoneList;
    private DBManager mgr;
    private String[] ArrayProvince,ArrayCity,ArrayZone;

	public WheelCityDialog(Context context) {
		super(context, R.style.dialog);
	}

	public WheelCityDialog(Context context, int theme) {
		super(context, theme);
		
	}

	public WheelCityDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected void setWindowParam() {
		setWindowParams(-1, -2, Gravity.BOTTOM);
		setOnDismissListener(onDismissListener);
		setOnShowListener(onShowListener);

	}

	@Override
	protected int getLayoutId() {
		return R.layout.wheel_city_dialog;
	}

	@Override
	protected void findViews() {

		/*final String cities[][] = new String[][] { mContext.getResources().getStringArray(R.array.zhejiang), mContext.getResources().getStringArray(R.array.shanghai),
				mContext.getResources().getStringArray(R.array.chongqing), mContext.getResources().getStringArray(R.array.jiangsu), mContext.getResources().getStringArray(R.array.hubei),
				mContext.getResources().getStringArray(R.array.fujian), mContext.getResources().getStringArray(R.array.beijing), mContext.getResources().getStringArray(R.array.tianjing),
				mContext.getResources().getStringArray(R.array.heilongjiang), mContext.getResources().getStringArray(R.array.jilin), mContext.getResources().getStringArray(R.array.liaonin),
				mContext.getResources().getStringArray(R.array.shandong), mContext.getResources().getStringArray(R.array.shanxi), mContext.getResources().getStringArray(R.array.shanxii),
				mContext.getResources().getStringArray(R.array.hebei), mContext.getResources().getStringArray(R.array.henan), mContext.getResources().getStringArray(R.array.hunan),
				mContext.getResources().getStringArray(R.array.hainan), mContext.getResources().getStringArray(R.array.jiangxi), mContext.getResources().getStringArray(R.array.guangdong),
				mContext.getResources().getStringArray(R.array.guangxi), mContext.getResources().getStringArray(R.array.yunnan), mContext.getResources().getStringArray(R.array.guizhou),
				mContext.getResources().getStringArray(R.array.sichuan), mContext.getResources().getStringArray(R.array.neimenggu), mContext.getResources().getStringArray(R.array.ninxia),
				mContext.getResources().getStringArray(R.array.gansu), mContext.getResources().getStringArray(R.array.qinghai), mContext.getResources().getStringArray(R.array.xizang),
				mContext.getResources().getStringArray(R.array.xinjiang), mContext.getResources().getStringArray(R.array.anhui), mContext.getResources().getStringArray(R.array.taiwan),
				mContext.getResources().getStringArray(R.array.xianggang), mContext.getResources().getStringArray(R.array.aomen) };*/
		mProvinceWheel = (WheelView)findViewById(R.id.wv_province);
		mCityWheel = (WheelView) findViewById(R.id.wv_country);
		mZoneWheel = (WheelView) findViewById(R.id.wv_city);

		mCityWheel.setShadowColor(0x00ffffff, 0x00ffffff, 0x00ffffff);
		mCityWheel.setWheelBackground(android.R.color.transparent);
		mCityWheel.setWheelForeground(R.drawable.wheel_val);
		mCityWheel.setVisibleItems(5);
		//mCityWheel.setCyclic(true);

		mZoneWheel.setShadowColor(0x00ffffff, 0x00ffffff, 0x00ffffff);
		mZoneWheel.setWheelBackground(android.R.color.transparent);
		mZoneWheel.setWheelForeground(R.drawable.wheel_val);
		mZoneWheel.setVisibleItems(5);
		//mZoneWheel.setCyclic(true);
		mProvinceWheel.setShadowColor(0x00ffffff, 0x00ffffff, 0x00ffffff);
		mProvinceWheel.setWheelBackground(android.R.color.transparent);
		mProvinceWheel.setWheelForeground(R.drawable.wheel_val);
		mProvinceWheel.setVisibleItems(5);
		
		//获得省列表

		// 设置监听器
		
		mProvinceWheel.addChangingListener(new OnWheelChangedListener() {
			
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				getCity();
			}
		});
		
		mCityWheel.addChangingListener(new OnWheelChangedListener() {
			
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				getZone();
			}
		});

//		final String countries[] = mContext.getResources().getStringArray(R.array.province);
//		mCityWheel.setViewAdapter(new CountryAdapter(mContext, countries, 1));
//		mCityWheel.setCurrentItem(1);

		findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String province = provincelist.get(mProvinceWheel.getCurrentItem()).provinceName;
				String provinceid = provincelist.get(mProvinceWheel.getCurrentItem()).provinceId;
				String city = citylist.get(mCityWheel.getCurrentItem()).cityname;
				String cityid = citylist.get(mCityWheel.getCurrentItem()).cityid;
				String zone = zoneList.get(mZoneWheel.getCurrentItem()).zonename;
				String zoneid = zoneList.get(mZoneWheel.getCurrentItem()).zoneid;
				if (mOnComfirmClickListener != null) {
					mOnComfirmClickListener.onComfirmBtnClick(province, city,zone,provinceid,cityid,zoneid);
				}
				dismiss();
			}
		});
		findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
	}

//	private void updateCities(WheelView city, String cities[][], int index) {
//		CountryAdapter adapter = new CountryAdapter(mContext, cities[index], 2);
//		adapter.setTextSize(18);
//		city.setViewAdapter(adapter);
//		city.setCurrentItem(cities[index].length / 2);
//	}

	private class CountryAdapter extends ArrayWheelAdapter<String> {
		int currentGet;
		int currentSet;

		public CountryAdapter(Context context, String[] items) {
			super(context, items);
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			// if (currentGet == currentSet) {
			// view.setTextColor(Color.parseColor("#17b3ec"));
			// }
			view.setTextColor(Color.WHITE);
			view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 21);
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentGet = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	public void setOnComfirmClickListener(OnComfirmClickListener l) {
		mOnComfirmClickListener = l;
	}

	public interface OnComfirmClickListener {
		void onComfirmBtnClick(String province, String city,String zone,String provinceid,String cityid,String zoneid);
	}
	
	private void getProvince()
    {
        ArrayProvince = new String[provincelist.size()];
        for (int i =0;i<provincelist.size();i++)
        {
            ArrayProvince[i] = provincelist.get(i).provinceName;
        }
    }
	
	private void setUpData()
    {
		CountryAdapter adapter = new CountryAdapter(mContext, ArrayProvince);
		mProvinceWheel.setViewAdapter(adapter);
		mProvinceWheel.setCurrentItem(0);
        getCity();
        //getZone();
    }
	
	private void getCity()
    {
        int index = mProvinceWheel.getCurrentItem();
        String provinceid = provincelist.get(index).provinceId;
        citylist = (ArrayList<City>) mgr.queryCity(provinceid);

        ArrayCity = new String[citylist.size()];
        if (citylist.size()>0)
        {
        for (int i =0;i<citylist.size();i++)
        {
            ArrayCity[i]= citylist.get(i).cityname;
        }
        CountryAdapter adapter = new CountryAdapter(mContext, ArrayCity);
        mCityWheel.setViewAdapter(adapter);
        mCityWheel.setCurrentItem(0);
        }else{
            CountryAdapter adapter = new CountryAdapter(mContext, ArrayCity);
            mCityWheel.setViewAdapter(adapter);
        }
        getZone();
    }
	
	private void getZone()
    {
		if (citylist.size()>0)
		{
        int index = mCityWheel.getCurrentItem();
        String cityid = citylist.get(index).cityid;
        zoneList = (ArrayList<Area>) mgr.queryArea(cityid);
        ArrayZone = new String[zoneList.size()];
        if (zoneList.size()>0)
        {
        for (int i = 0;i<zoneList.size();i++)
        {
            ArrayZone[i] = zoneList.get(i).zonename;
        }
        CountryAdapter adapter = new CountryAdapter(mContext, ArrayZone);
        mZoneWheel.setViewAdapter(adapter);
        mZoneWheel.setCurrentItem(0);
        }else{
            CountryAdapter adapter = new CountryAdapter(mContext, ArrayZone);
            mZoneWheel.setViewAdapter(adapter);
        }
		}else{
			 ArrayZone = new String[0];
            CountryAdapter adapter = new CountryAdapter(mContext, ArrayZone);
            mZoneWheel.setViewAdapter(adapter);
		}
    }
	
	private OnDismissListener  onDismissListener = new OnDismissListener() {
		
		@Override
		public void onDismiss(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mgr.closeDB();
		}
	};
	
	private OnShowListener onShowListener = new OnShowListener() {
		
		@Override
		public void onShow(DialogInterface dialog) {
			// TODO Auto-generated method stub
			mgr = new DBManager(mContext);
			provincelist = (ArrayList<Province>) mgr.queryProvince();
			getProvince();
			setUpData();
		}
	};

}