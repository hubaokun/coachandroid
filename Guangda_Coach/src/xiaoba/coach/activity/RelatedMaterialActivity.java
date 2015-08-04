package xiaoba.coach.activity;

import java.util.HashMap;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.R;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.utils.CommonUtils;

import com.daoshun.lib.communication.http.JSONAccessor;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


/*
 * upload coach material page
 */
@EActivity(R.layout.activity_related_material)
public class RelatedMaterialActivity extends BaseActivity {
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_back_text)
	TextView mTitleBackTv;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.id_num_img)
	ImageView mIdModify;
	@ViewById(R.id.coach_num_img)
	ImageView mCoachSerialModify;
	@ViewById(R.id.car_type_img)
	ImageView mCarTypeModify;
	@ViewById(R.id.card_make_time_img)
	ImageView mTimeModify;
	@ViewById(R.id.rm_input_id_num)
	EditText mInputIdNum;
	@ViewById(R.id.rm_input_coach_num)
	EditText mInputCoachNum;
	@ViewById(R.id.rm_input_car_type)
	EditText mInputCarType;
	@ViewById(R.id.rm_input_make_time)
	EditText mInputMakeTime;

	@AfterViews
	public void init() {
		initViews();
		addListeners();
	}

	private void initViews() {
		mTitle.setText(getString(R.string.related_material));
		mTitle.setTextColor(getResources().getColor(R.color.text_black));
		mTitleRightTv.setText(getString(R.string.rm_submit));
		mTitleRightTv.setTextColor(getResources().getColor(R.color.text_grey));
		mTitleRightTv.setClickable(false);
		mTitleBack.setVisibility(View.INVISIBLE);
		mTitleBackTv.setText(getString(R.string.skip));
		mTitleBackTv.setVisibility(View.VISIBLE);
	}

	private void addListeners() {
	}

	@Click(R.id.title_back)
	void returnLastPage() {
		finishPage();
	}

	@Click(R.id.title_right_text)
	void gotoNextPage() {
		finishPage();
	}

	@Click(R.id.id_num_img)
	void modifyIdNum() {
		mIdModify.setVisibility(View.INVISIBLE);
		mInputIdNum.setEnabled(true);
		canSubmit();
	}

	@Click(R.id.coach_num_img)
	void modifyCoachNum() {
		mCoachSerialModify.setVisibility(View.INVISIBLE);
		mInputCoachNum.setEnabled(true);
		canSubmit();
	}

	@Click(R.id.car_type_img)
	void modifyCarType() {
		mCarTypeModify.setVisibility(View.INVISIBLE);
		mInputCarType.setEnabled(true);
		canSubmit();
	}

	@Click(R.id.card_make_time_img)
	void modifyMakeTime() {
		mTimeModify.setVisibility(View.INVISIBLE);
		mInputMakeTime.setEnabled(true);
		canSubmit();
	}

	/*
	 * activate the btn submit
	 */
	private void canSubmit() {
		mTitleRightTv.setClickable(true);
		mTitleRightTv.setTextColor(getResources().getColor(R.color.text_black));
	}

	@Override
	public void onBackPressed() {
		finishPage();
	}

	private void finishPage() {
		startActivity(new Intent(RelatedMaterialActivity.this, HomeActivity_.class));
		RelatedMaterialActivity.this.finish();
	}

	private class PerfectAccountInfoTask extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(RelatedMaterialActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();
		}

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("coachid", "");
			param.put("realname", "");
			param.put("phone", "");
			return null;
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					// registe ok
					startActivity(new Intent(RelatedMaterialActivity.this, RelatedMaterialActivity_.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
				} else {
					if (result.getMessage() != null && result.getCode() == 2)
						CommonUtils.showToast(RelatedMaterialActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(RelatedMaterialActivity.this);
						if (result.getMessage() != null)
							CommonUtils.showToast(RelatedMaterialActivity.this.getApplicationContext(), result.getMessage());
					}
				}

			} else {
				CommonUtils.showToast(RelatedMaterialActivity.this.getApplicationContext(), getString(R.string.net_error));
			}
		}

	}

}
