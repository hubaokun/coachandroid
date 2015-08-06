package xiaoba.coach.activity;

import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.UserInfo;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.utils.CommonUtils;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult.AddressComponent;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.daoshun.lib.communication.http.JSONAccessor;

@EActivity(R.layout.activity_add_address)
public class AddAddressActivity extends BaseActivity {
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.search_et)
	EditText mSearch;
	@ViewById(R.id.search_tv)
	TextView mSearchTv;
	@ViewById(R.id.loc_detail)
	EditText mAddress;

	MapView mMapView;
	OverlayOptions option;
	GeoCoder mGeoSearch;

	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	Marker marker;

	BaiduMap mBaiduMap;

	// UI相关
	OnCheckedChangeListener radioButtonListener;
	Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位

	PoiSearch mPoiSearch;

	private LatLng latLng;
	private String district, detail;

	@AfterViews
	void init() {
		mTitleBack.setImageResource(R.drawable.back_arrow);
		mTitleRightTv.setText("保存");
		mTitleRightTv.setTextColor(Color.parseColor("#2c2c2c"));
		mTitleRightTv.setVisibility(View.VISIBLE);

		mCurrentMode = LocationMode.NORMAL;

		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 修改为自定义marker
		mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);

		// mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, false, mCurrentMarker));

		mPoiSearch = PoiSearch.newInstance();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setIsNeedAddress(true);
		mLocClient.setLocOption(option);
		mLocClient.start();

		mGeoSearch = GeoCoder.newInstance();
		addListeners();
	}

	private void addListeners() {
		mSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (arg0.length() > 0)
					mSearchTv.setVisibility(View.INVISIBLE);
				else {
					mSearchTv.setVisibility(View.VISIBLE);
				}
			}
		});

		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
				option = new MarkerOptions().position(arg0).icon(mCurrentMarker);
				if (marker != null)
					marker.remove();
				marker = (Marker) (mBaiduMap.addOverlay(option));

				latLng = arg0;
				if (mLoadingDialog != null)
					mLoadingDialog.show();
				mGeoSearch.reverseGeoCode(new ReverseGeoCodeOption().location(arg0));
			}
		});

		mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {

			@Override
			public void onGetPoiResult(PoiResult arg0) {

			}

			@Override
			public void onGetPoiDetailResult(PoiDetailResult arg0) {
				if (arg0.getAddress() != null)
					mAddress.setText(arg0.getAddress());
			}
		});

		mGeoSearch.setOnGetGeoCodeResultListener(geoListener);

		mSearch.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {

					// 先隐藏键盘
					((InputMethodManager) mSearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

					// Geo搜索
					mGeoSearch.geocode(new GeoCodeOption().city("杭州").address(mSearch.getText().toString()));

					if (mLoadingDialog != null)
						mLoadingDialog.show();
				}
				return false;
			}
		});
	}

	OnGetGeoCoderResultListener geoListener = new OnGetGeoCoderResultListener() {

		@Override
		public void onGetGeoCodeResult(GeoCodeResult result) {
			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				Toast.makeText(AddAddressActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();

				if (mLoadingDialog != null && mLoadingDialog.isShowing())
					mLoadingDialog.dismiss();
				return;
			}
			// mBaiduMap.clear();
			// mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
			// .icon(BitmapDescriptorFactory
			// .fromResource(R.drawable.icon_geo)));
			// mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
			// .getLocation()));
			/*
			 * set latlng
			 */
			latLng = new LatLng(result.getLocation().latitude, result.getLocation().longitude);
			/*
			 * set marker
			 */
			mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
			option = new MarkerOptions().position(latLng).icon(mCurrentMarker);
			if (marker != null)
				marker.remove();
			marker = (Marker) (mBaiduMap.addOverlay(option));
			/*
			 * show address
			 */
			if (result.getAddress() != null)
				mAddress.setText(result.getAddress());

			mGeoSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
		}

		@Override
		public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();

			if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
				return;
			} else {
				if (result.getAddress() != null) {
					mAddress.setText(result.getAddress());
					// detail = result.getAddress();
				}
				AddressComponent component = result.getAddressDetail();
				district = component.province + component.city;
			}
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		// activity 暂停时同时暂停地图控件
		mMapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// activity 恢复时同时恢复地图控件
		mMapView.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// activity 销毁时同时销毁地图控件
		mMapView.onDestroy();
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
			// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);

				// 添加第一个overlay
				latLng = ll;
				mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.icon_geo);
				option = new MarkerOptions().position(ll).icon(mCurrentMarker);
				if (marker != null)
					marker.remove();
				marker = (Marker) (mBaiduMap.addOverlay(option));

				district = location.getProvince() + location.getCity();
				mGeoSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
			}

		}

		public void onReceivePoi(BDLocation poiLocation) {

		}
	}

	@Click(R.id.title_back)
	void goBack() {
		finish();
	}

	@Click(R.id.title_right_text)
	void save() {
		new AddAddressTask().execute();
	}

	private class AddAddressTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(AddAddressActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			detail = mAddress.getText().toString().trim();
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "AddAddress");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			if (latLng != null) {
				param.put("longitude", latLng.longitude);
				param.put("latitude", latLng.latitude);
			}
			param.put("area", district);
			param.put("detail", detail);
			return accessor.execute(Settings.CMY_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					CommonUtils.showToast(AddAddressActivity.this.getApplicationContext(), "保存地址成功");
					UserInfo userInfo = CoachApplication.getInstance().getUserInfo();
					/*
					 * 如果为第一个地址就保存
					 */
					if (TextUtils.isEmpty(userInfo.getDefaultAddress())) {
						userInfo.setDefaultAddress(detail);
						userInfo.saveUserInfo(userInfo, AddAddressActivity.this);
					}

					AddAddressActivity.this.finish();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(AddAddressActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(AddAddressActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(AddAddressActivity.this.getApplicationContext(), getString(R.string.net_error));
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

	}

}
