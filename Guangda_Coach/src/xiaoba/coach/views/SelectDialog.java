package xiaoba.coach.views;

import xiaoba.coach.R;
import xiaoba.coach.interfaces.DialogConfirmListener;

import com.daoshun.lib.view.OnSingleClickListener;


import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SelectDialog extends BaseDialog {

	private WheelView mYearWheel;
	DialogConfirmListener mConfirmListener;
	String[] content;
	DateNumericAdapter mAdapter;

	public SelectDialog(Context context, String[] content) {
		super(context, R.style.dialog);
		this.content = content;
	}

	@Override
	protected int getLayoutId() {
		return R.layout.select_dialog_layout;
	}

	@Override
	protected void findViews() {

		mYearWheel = (WheelView) findViewById(R.id.wheel);
		TextView cancel = (TextView) findViewById(R.id.tv_cancel);
		TextView ok = (TextView) findViewById(R.id.tv_ok);

		mYearWheel.setShadowColor(0x00ffffff, 0x00ffffff, 0x00ffffff);
		mYearWheel.setWheelBackground(android.R.color.transparent);
		mYearWheel.setWheelForeground(R.drawable.wheel_val);
		mYearWheel.setVisibleItems(5);

		cancel.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (mConfirmListener != null) {
					mConfirmListener.doCancel();
				}
			}
		});

		ok.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				if (mConfirmListener != null && mAdapter != null) {
					mConfirmListener.doConfirm(mAdapter.getItemText(position).toString());
				}
			}
		});
	}
	
	

	public void setAdapter() {
		// 设置监听器
		mAdapter = new DateNumericAdapter(mContext, content, 0);
		WheelChangedListener wheelChangedListener = new WheelChangedListener();
		mYearWheel.setViewAdapter(mAdapter);
		// 设置中间位置的显示当前年
		mYearWheel.addChangingListener(wheelChangedListener);

	}

	@Override
	protected void setWindowParam() {
		setWindowParams(-1, -2, Gravity.BOTTOM);
	}

	TextView cacheView;

	int position;

	class WheelChangedListener implements OnWheelChangedListener {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			position = newValue;
			if (mAdapter != null) {
				// mAdapter.currentGet = newValue;

				if (cacheView == null)
					cacheView = new TextView(mContext);

				cacheView = (TextView) mAdapter.getItem(position, cacheView, null);

				cacheView.setTextColor(Color.parseColor("#3a95e6"));
			}

		}
	}

	private class DateNumericAdapter extends ArrayWheelAdapter<String> {
		int currentGet;
		int currentSet;
		String[] items;

		public DateNumericAdapter(Context context, String[] items, int current) {
			super(context, items);
			this.currentSet = current;
			setTextSize(16);
			this.items = items;
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			view.setTextColor(Color.WHITE);
			view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentGet = index;
			return super.getItem(index, cachedView, parent);
		}

		@Override
		public int getItemsCount() {
			return items.length;
		}
	}

	public void setmConfirmListener(DialogConfirmListener mConfirmListener) {
		this.mConfirmListener = mConfirmListener;
	}

}
