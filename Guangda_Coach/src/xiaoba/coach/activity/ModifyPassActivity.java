package xiaoba.coach.activity;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.R;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

@EActivity(R.layout.activity_modify_pass)
public class ModifyPassActivity extends BaseActivity {
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mTitleBack;
	@ViewById(R.id.title_right_text)
	TextView mTitleRightTv;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.enter_new_pass)
	EditText mNewPass;
	@ViewById(R.id.reenter_new_pass)
	EditText mReNewPass;
	@ViewById(R.id.pencil_up)
	ImageView mPencilUp;
	@ViewById(R.id.pencil_down)
	ImageView mPencilDown;

	@AfterViews
	void init() {
		mTitle.setText("密码修改");
		mTitleBack.setImageResource(R.drawable.back_arrow);
		mTitle.setTextColor(Color.parseColor("#2c2c2c"));
		mTitleRightTv.setText("确定");
		mTitleRightTv.setTextColor(Color.parseColor("#d2d2d2"));
		mTitleRightTv.setClickable(false);
		mTitleRightTv.setVisibility(View.VISIBLE);

		addListeners();
	}

	private void addListeners() {
		mNewPass.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				if (mNewPass.getText().length() > 0 && mReNewPass.getText().length() > 0) {
					mTitleRightTv.setTextColor(Color.parseColor("#2c2c2c"));
					mTitleRightTv.setClickable(true);
				}
			}
		});
	}

	@Click(R.id.title_back)
	void returnLastPage() {
		finish();
	}

	@Click(R.id.title_right_text)
	void submit() {

	}
}
