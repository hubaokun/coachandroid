package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.listview.DensityUtils;
import com.daoshun.lib.view.OnSingleClickListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.GetIncomeDetailResult;
import xiaoba.coach.net.result.GetIncomeDetailResult.RecordInfo;
import xiaoba.coach.utils.CommonUtils;

@EActivity(R.layout.activity_income_detail)
public class IncomeDetailActivity extends BaseActivity {
//	@ViewById(R.id.title)
//	TextView mTitle;
//	@ViewById(R.id.title_back_img)
//	ImageView mTitleBack;
//	@ViewById(R.id.title_right_text)
//	TextView mTitleRightTv;
//	@ViewById(R.id.title_back)
//	FrameLayout mBack;
	@ViewById(R.id.img_back)
	ImageView imgBack;
	@ViewById(R.id.tv_right)
	TextView tvRight;
	@ViewById(R.id.income_detail_list)
	ListView mListView;
	@ViewById(R.id.tv_total_money)
	TextView tvTotal;
	@ViewById(R.id.tv_free_money)
	TextView tvFree;
	@ViewById(R.id.tv_frozen_money)
	TextView tvFrozen;
	@ViewById(R.id.recharge_instant)
	TextView mRecharge;
	@ViewById(R.id.get_cash)
	TextView mGetCash;

	IncomeDetailAdapter mAdapter = new IncomeDetailAdapter();
	List<RecordInfo> list = new ArrayList<GetIncomeDetailResult.RecordInfo>();
	float mBalance;  //可用余额

	@AfterViews
	void init() {

		mListView.setAdapter(mAdapter);
	}

	@Click(R.id.img_back)
	void goback() {
		finish();
	}

	@Click(R.id.tv_right)
	void addAccount() {
		startActivity(new Intent(IncomeDetailActivity.this, AccountArrangeActivity.class));
	}

	@Override
	protected void onResume() {
		super.onResume();
		new GetMyBalanceInfoTask().execute();
	}

	private class GetMyBalanceInfoTask extends AsyncTask<Void, Void, GetIncomeDetailResult> {
		JSONAccessor accessor = new JSONAccessor(IncomeDetailActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected GetIncomeDetailResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetMyBalanceInfo");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());
			return accessor.execute(Settings.USER_URL, param, GetIncomeDetailResult.class);

		}

		@Override
		protected void onPostExecute(GetIncomeDetailResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					if (result.getRecordlist() != null) {
						list = result.getRecordlist();
						mAdapter.notifyDataSetChanged();
						mBalance = result.getBalance() - result.getGmoney();
						if (mBalance<0)
						{
							mBalance = 0;
						}
						tvTotal.setText(result.getBalance()+"");
						float left = (result.getBalance() - result.getGmoney());
						if (left > 0)
						{
							tvFree.setText(left+"");
						}
						else
						{
							tvFree.setText("0");
						}
						tvFrozen.setText(result.getFmoney()+"");
					}
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(IncomeDetailActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(IncomeDetailActivity.this);
					}
				}
			} else {
				CommonUtils.showToast(IncomeDetailActivity.this.getApplicationContext(), IncomeDetailActivity.this.getString(R.string.net_error));
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

	}

	/*
	 * 
	 */
	private class IncomeDetailAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		ViewHolder holder;

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			if (convertView != null) {
				holder = (ViewHolder) convertView.getTag();
			} else {
				holder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.item_income_detail, null);

				holder.title = (TextView) convertView.findViewById(R.id.income_detail_title);
				holder.time = (TextView) convertView.findViewById(R.id.income_detail_time);
				holder.number = (TextView) convertView.findViewById(R.id.income_detail_number);
				holder.tip = (TextView) convertView.findViewById(R.id.income_detail_tip);

				convertView.setTag(holder);
			}

			RecordInfo info = list.get(position);
			if (info == null)
				return convertView;
			int money = (int)info.getAmount();

			switch (info.getType()) {
			case 1:
				holder.title.setText("收入");
				float out1 = info.getAmount_out1(); // 平台
				float out2 = info.getAmount_out1(); // 驾校
				float total = money + out1 + out2; // 总额
				
				if (info.getAmount_out1() != 0) {
					
					if ( info.getAmount_out2() == 0) {
						holder.tip.setText("(课程总额" + total + "元，其中" + out1 + "元广大平台抽成)");
						holder.tip.setVisibility(View.VISIBLE);
					} else {
						holder.tip.setText("(课程总额" + total + "元，其中" + out1 + "元广大平台抽成," + out2 + "元广大驾校抽成)");
						holder.tip.setVisibility(View.VISIBLE);
					}
				} else {
					holder.tip.setVisibility(View.GONE);
					if (out2 == 0) {
					} else {
						holder.tip.setText("(课程总额" + total + "元，其中" + out2 + "元广大驾校抽成)");
						holder.tip.setVisibility(View.VISIBLE);
					}
				}

				float amoumt = info.getAmount() - info.getAmount_out1() - info.getAmount_out2();
				if (amoumt >= 0) {
					holder.number.setText("+ " + money+"元");
					holder.number.setTextColor(Color.parseColor("#20b478"));
				} else {
					holder.number.setText("- " + money+"元");
					holder.number.setTextColor(Color.parseColor("#e0483d"));
				}
				break;
				
				
			case 2:
			case 7:
				holder.title.setText("提现");
				if (holder.tip.getVisibility() == View.VISIBLE)
					holder.tip.setVisibility(View.GONE);

/*				if (info.getAmount() >= 0) {
					holder.number.setText("+ " + info.getAmount());
					holder.number.setTextColor(Color.parseColor("#20b478"));
				} else {*/
					holder.number.setText("- " + money +"元");
					holder.number.setTextColor(Color.parseColor("#e0483d"));
				/*}*/
				break;

			case 3:
				holder.title.setText("充值");
				if (holder.tip.getVisibility() == View.VISIBLE)
					holder.tip.setVisibility(View.GONE);

				if (info.getAmount() >= 0) {
					holder.number.setText("+ " + money +"元");
					holder.number.setTextColor(Color.parseColor("#20b478"));
				} else {
					holder.number.setText("- " + money +"元");
					holder.number.setTextColor(Color.parseColor("#e0483d"));
				}
				break;
			case 4:
				holder.title.setText("推荐奖");
				if (holder.tip.getVisibility() == View.VISIBLE)
					holder.tip.setVisibility(View.GONE);

				if (info.getAmount() >= 0) {
					holder.number.setText("+ " + money +"元");
					holder.number.setTextColor(Color.parseColor("#20b478"));
				} else {
					holder.number.setText("- " + money +"元");
					holder.number.setTextColor(Color.parseColor("#e0483d"));
				}
				break;
			case 5:
				holder.title.setText("被推荐教练开单奖");
				if (holder.tip.getVisibility() == View.VISIBLE)
					holder.tip.setVisibility(View.GONE);

				if (info.getAmount() >= 0) {
					holder.number.setText("+ " + money +"元");
					holder.number.setTextColor(Color.parseColor("#20b478"));
				} else {
					holder.number.setText("- " + money +"元");
					holder.number.setTextColor(Color.parseColor("#e0483d"));
				}
				break;
			case 6:
				holder.title.setText("提现失败");
				if (holder.tip.getVisibility() == View.VISIBLE)
				{
					holder.tip.setVisibility(View.GONE);
				}
				if (info.getAmount() >= 0) {
					holder.number.setText("+ " + money +"元");
					holder.number.setTextColor(Color.parseColor("#20b478"));
				} else {
					holder.number.setText("- " + money +"元");
					holder.number.setTextColor(Color.parseColor("#e0483d"));
				}
			default:
				break;
			}

			if (info.getAddtime() != null)
				holder.time.setText(info.getAddtime());
			else {
				holder.time.setText("");
			}

			return convertView;
		}

		class ViewHolder {
			TextView title;
			TextView time;
			TextView number;
			TextView tip;
		}
	}

	@Click(R.id.recharge_instant)
	void recharge() {
		String account = CoachApplication.getInstance().getUserInfo().getAlipay_account();
		if (account != null && !account.equals(""))
			startActivity(new Intent(IncomeDetailActivity.this, RechargeActivity_.class));
		else {
			Toast.makeText(IncomeDetailActivity.this, "请点击右上角添加支付宝账号", Toast.LENGTH_SHORT).show();
		}
	}

	@Click(R.id.get_cash)
	void getCash() {
		String account = CoachApplication.getInstance().getUserInfo().getAlipay_account();
		if (account != null && !account.equals(""))
			startActivityForResult(new Intent(IncomeDetailActivity.this, GetCashActivity_.class).putExtra("balance", mBalance), 11);
		else {
			Toast.makeText(IncomeDetailActivity.this, "请点击右上角添加支付宝账号", Toast.LENGTH_SHORT).show();
		}

	}

	Dialog setDialog;

	private void showmyDialog(int money) {
		AlertDialog.Builder builder = new AlertDialog.Builder(IncomeDetailActivity.this);
		setDialog = builder.create();
		setDialog.show();
		setDialog.setContentView(R.layout.dialog_tixian);
		setDialog.setCanceledOnTouchOutside(true);
		setDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		// WindowManager.LayoutParams params = setDialog.getWindow().getAttributes();

		ImageView cancel = (ImageView) setDialog.findViewById(R.id.recharge_cancel);
		TextView tip = (TextView) setDialog.findViewById(R.id.money_tip);

		cancel.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				setDialog.dismiss();
			}
		});
		tip.setText("您申请的" + money + "元金额已提交成功，请等待审核，我们会在3个工作日内联系您！");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 11 && data != null) {
			showmyDialog(data.getIntExtra("money", 0));
		}
	}
}
