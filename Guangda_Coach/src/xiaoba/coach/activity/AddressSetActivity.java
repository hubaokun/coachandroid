package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.UserInfo;
import xiaoba.coach.net.result.AddressResult;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.AddressResult.AddressInfo;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.views.SwipeListView;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.listview.DensityUtils;
import com.daoshun.lib.view.OnSingleClickListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@EActivity(R.layout.activity_address_set)
public class AddressSetActivity extends BaseActivity {
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.set_loc_title)
	RelativeLayout mTitleLayout;
	@ViewById(R.id.noaddress_rt)
	LinearLayout mNoAddressRt;
	@ViewById(R.id.noaddress_center)
	LinearLayout mNoAddressCen;
	SwipeListView mListView;
	SwipeListViewAdapter mAdapter = new SwipeListViewAdapter();
	List<AddressInfo> list = new ArrayList<AddressResult.AddressInfo>();
	int selPosition; // 选中设为默认的item位置
	@AfterViews
	void init() {
		mTitle.setText("地址设置");
		mTitleBack.setImageResource(R.drawable.back_arrow);
		mTitle.setTextColor(Color.parseColor("#2c2c2c"));
		mTitleRightTv.setText("添加");
		mTitleRightTv.setTextColor(Color.parseColor("#2c2c2c"));
		mTitleRightTv.setVisibility(View.VISIBLE);
		mTitleLayout.setBackgroundColor(Color.parseColor("#ffffff"));
		mListView = (SwipeListView) findViewById(R.id.loc_list);
		mListView.setAdapter(mAdapter);
		addListeners();
	}

	private void addListeners() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
				if (list.get(pos).getIscurrent() == 0) {
					selPosition = pos;
					showDialog();
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		new GetAllAddressTask().execute();
	}
	
	private class SwipeListViewAdapter extends BaseAdapter {

		private Context context; // 上下文
		private LayoutInflater inflater; // 打气筒
		private SwipeListView mSwipeListView;
		private int groupSize;
		private AddressInfo info;


		public SwipeListViewAdapter() {
		}

		public SwipeListViewAdapter(Context context, SwipeListView listView) {
			this.context = context;
			inflater = LayoutInflater.from(context);
			mSwipeListView = listView;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		ViewHolder holder;

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if (convertView != null) {
				holder = (ViewHolder) convertView.getTag();
			} else {
				holder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.item_loc, null);

				holder.loc = (ImageView) convertView.findViewById(R.id.item_loc_img);
				holder.province = (TextView) convertView.findViewById(R.id.item_loc_province);
				holder.block = (TextView) convertView.findViewById(R.id.item_loc_block);
				holder.isDefault = (TextView) convertView.findViewById(R.id.item_isdefault);
				holder.delete = (RelativeLayout) convertView.findViewById(R.id.item_right);
				convertView.setTag(holder);
			}

			info = list.get(position);
			if (info == null)
				return convertView;

			if (info.getIscurrent() == 1) { // 为默认地址
				holder.loc.setImageResource(R.drawable.default_loc);
				holder.isDefault.setVisibility(View.VISIBLE);
			} else {
				holder.loc.setImageResource(R.drawable.grey_loc);
				holder.isDefault.setVisibility(View.INVISIBLE);
			}
			if (info.getArea() != null)
				holder.province.setText(info.getArea());
			else {
				holder.province.setText("");
			}
			if (info.getDetail() != null)
				holder.block.setText(info.getDetail());
			else {
				holder.block.setText("");
			}

			holder.delete.setOnClickListener(new DeleteListener(position));

			return convertView;
		}

		class DeleteListener extends OnSingleClickListener {
			int pos;

			public DeleteListener(int pos) {
				this.pos = pos;
			}

			@Override
			public void doOnClick(View v) {
				if (list.get(pos).getIscurrent() == 1)
				{
					Toast.makeText(AddressSetActivity.this, "默认地址不能删除", 0).show();
				}else{
				new DelAddressTask(pos).execute();
				}
			}
		}

		class ViewHolder {
			ImageView loc;
			TextView province;
			TextView block;
			TextView isDefault;
			RelativeLayout delete;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}
	}

	/*
	 * 获取所有地址
	 */
	private class GetAllAddressTask extends AsyncTask<Void, Void, AddressResult> {
		JSONAccessor accessor = new JSONAccessor(AddressSetActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected AddressResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetAllAddress");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			return accessor.execute(Settings.CMY_URL, param, AddressResult.class);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected void onPostExecute(AddressResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();

			if (result != null) {
				if (result.getCode() == 1) {
					if (result.getAddresslist() != null) {
						list = result.getAddresslist();
						mAdapter.notifyDataSetChanged();
					}
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(AddressSetActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(AddressSetActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(AddressSetActivity.this.getApplicationContext(), getString(R.string.net_error));
			}

			if (list.size() == 0) {
				mNoAddressCen.setVisibility(View.VISIBLE);
				mNoAddressRt.setVisibility(View.VISIBLE);
			} else {
				mNoAddressCen.setVisibility(View.INVISIBLE);
				mNoAddressRt.setVisibility(View.INVISIBLE);
			}
		}
	}

	@Click(R.id.title_right_text)
	void addNewAddress() {
		startActivity(new Intent(AddressSetActivity.this, AddAddressActivity_.class));
	}

	@Click(R.id.title_back)
	void goback() {
		hasSetTeachAddress();
		finish();
		
	}
	
	private void hasSetTeachAddress()
	{
		if (list.size() == 0)
		{
			CoachApplication.getInstance().setSetTeachAdd(true);
		}else{
			CoachApplication.getInstance().setSetTeachAdd(false);
		}
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK){
            //这你写你的返回处理
        	hasSetTeachAddress();
        	finish();
            return true;
            }
        return super.onKeyDown(keyCode, event);
    }

	private class DelAddressTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(AddressSetActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);
		int position;

		public DelAddressTask(int position) {
			this.position = position;
		}

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "DelAddress");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("addressid", list.get(position).getAddressid());
			return accessor.execute(Settings.CMY_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					CommonUtils.showToast(AddressSetActivity.this.getApplicationContext(), "删除地址成功");
					/*
					 * 若是默认地址清除本地存储
					 */
					if (list.get(position).getIscurrent() == 1) {
						UserInfo userInfo = CoachApplication.getInstance().getUserInfo();
						userInfo.setDefaultAddress("");
						userInfo.saveUserInfo(userInfo, AddressSetActivity.this);
					}
					/*
					 * 清除list中数据，并刷新界面
					 */
					list.remove(position);
					mAdapter.notifyDataSetChanged();
					if (list.size() == 0) {
						mNoAddressCen.setVisibility(View.VISIBLE);
						mNoAddressRt.setVisibility(View.VISIBLE);
					}
					/*
					 * 删除完，隐藏删除按钮
					 */
					if (position < list.size())
						mListView.hiddenRight(mListView.getChildAt(position));
					
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(AddressSetActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(AddressSetActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(AddressSetActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

	}

	Dialog setDialog;

	private void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(AddressSetActivity.this);
		setDialog = builder.create();
		setDialog.show();
		setDialog.setContentView(R.layout.dialog_set_default);
		setDialog.setCanceledOnTouchOutside(true);
		setDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		WindowManager.LayoutParams params = setDialog.getWindow().getAttributes();
		// params.gravity = Gravity.BOTTOM;
		int width = Settings.DISPLAY_WIDTH - DensityUtils.dp2px(AddressSetActivity.this, 77);
		params.width = width;
		params.height = width * 316 / 480;
		setDialog.getWindow().setAttributes(params);

		ImageView cancel = (ImageView) setDialog.findViewById(R.id.dialog_set_cancel);
		Button confirm = (Button) setDialog.findViewById(R.id.dialog_set_confirm);

		cancel.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				setDialog.dismiss();
			}
		});

		confirm.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				new SetCurrentAddressTask().execute();
				setDialog.dismiss();
			}
		});
	}

	private class SetCurrentAddressTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(AddressSetActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);
		int tempPosition;

		public SetCurrentAddressTask() {
			tempPosition = selPosition;
		}

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "SetCurrentAddress");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			param.put("addressid", list.get(tempPosition).getAddressid());
			return accessor.execute(Settings.CMY_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					for (AddressInfo info : list) {
						info.setIscurrent(0);
					}
					UserInfo userInfo = CoachApplication.getInstance().getUserInfo();
					userInfo.setDefaultAddress(list.get(tempPosition).getDetail());
					userInfo.saveUserInfo(userInfo, AddressSetActivity.this);
					list.get(tempPosition).setIscurrent(1);
					mAdapter.notifyDataSetChanged();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(AddressSetActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(AddressSetActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(AddressSetActivity.this.getApplicationContext(), getString(R.string.net_error));
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
