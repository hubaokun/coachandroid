package xiaoba.coach.views;

import java.util.ArrayList;
import java.util.List;

import xiaoba.coach.R;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

public class DatePickerDialog extends BaseDialog {

	private TextView mGirlTv, mBoyTv;
	private OnComfirmClickListener mOnComfirmClickListener;

	public DatePickerDialog(Context context) {
		super(context, R.style.dialog);
	}

	public DatePickerDialog(Context context, int theme) {
		super(context, theme);
	}

	public DatePickerDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.picker_dialog_layout;
	}

	@Override
	protected void findViews() {
		PickerView picker = (PickerView) findViewById(R.id.day_pv);
		List<String> days = new ArrayList<String>();
		for (int i = 0; i < 31; i++) {
			days.add(i < 10 ? "0" + i : "" + i);
		}

		picker.setData(days);
		picker.setOnSelectListener(new PickerView.onSelectListener() {

			@Override
			public void onSelect(String text) {
				xiaoba.coach.utils.CommonUtils.showToast(mContext, text);
			}
		});
	}

	@Override
	protected void setWindowParam() {
		setWindowParams(-1, -2, Gravity.BOTTOM);
	}

	public void setOnComfirmClickListener(OnComfirmClickListener l) {
		mOnComfirmClickListener = l;
	}

	public interface OnComfirmClickListener {
		void onComfirmBtnClick(String gender);
	}

}
