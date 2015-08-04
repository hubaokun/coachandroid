package xiaoba.coach.views;

import com.daoshun.lib.view.OnSingleClickListener;
import xiaoba.coach.R;
import xiaoba.coach.views.WheelCityDialog.OnComfirmClickListener;
import xiaoba.coach.views.WheelDateDialog.WheelChangedListener;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;

public class PriceDialog extends BaseDialog {
	private WheelView WheelHundred,WheelTends,WheelSingle;
	private OnComfirmClickListener mOnComfirmClickListener;
	private String[] hundredArray;
	private String[] tendsArray1 ;
	private String[] tendsArray2 ;
	private String[] singleArray ;

	public PriceDialog(Context context) {
		super(context,R.style.dialog);
		// TODO Auto-generated constructor stub
	}
	
	public PriceDialog(Context context, int theme) {
		super(context, theme);
	}

	public PriceDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return xiaoba.coach.R.layout.price_dialog;
	}
	
	@Override
	protected void setWindowParam() {
		// TODO Auto-generated method stub
	
		setWindowParams(-1, -2, Gravity.BOTTOM);
	}
	

	@Override
	protected void findViews() {
		hundredArray = mContext.getResources().getStringArray(R.array.hundred);
		tendsArray1 = mContext.getResources().getStringArray(R.array.tends1);
		tendsArray2 = mContext.getResources().getStringArray(R.array.tends2);
		singleArray = mContext.getResources().getStringArray(R.array.single);
		// TODO Auto-generated method stub
		WheelHundred = (WheelView)findViewById(xiaoba.coach.R.id.price_hundred);
		WheelTends = (WheelView)findViewById(xiaoba.coach.R.id.price_tends);
		WheelSingle = (WheelView)findViewById(xiaoba.coach.R.id.price_single);
		
		WheelHundred.setShadowColor(0x00ffffff, 0x00ffffff, 0x00ffffff);
		WheelHundred.setWheelBackground(android.R.color.transparent);
		WheelHundred.setWheelForeground(R.drawable.wheel_val);
		WheelHundred.setVisibleItems(2);
		
		WheelTends.setShadowColor(0x00ffffff, 0x00ffffff, 0x00ffffff);
		WheelTends.setWheelBackground(android.R.color.transparent);
		WheelTends.setWheelForeground(R.drawable.wheel_val);
		WheelTends.setVisibleItems(2);
		
		WheelSingle.setShadowColor(0x00ffffff, 0x00ffffff, 0x00ffffff);
		WheelSingle.setWheelBackground(android.R.color.transparent);
		WheelSingle.setWheelForeground(R.drawable.wheel_val);
		WheelSingle.setVisibleItems(2);
		
		WheelHundred.setViewAdapter(new PriceAdapter(mContext, hundredArray, 1));
		WheelHundred.setCurrentItem(0);
		
		WheelTends.setViewAdapter(new PriceAdapter(mContext, tendsArray1, 1));
		WheelTends.setCurrentItem(0);
		
		WheelSingle.setViewAdapter(new PriceAdapter(mContext, singleArray, 1));
		WheelSingle.setCurrentItem(0);
		
		WheelChangedListener wheelChangedListener = new WheelChangedListener();
		WheelHundred.addChangingListener(wheelChangedListener);
		findViewById(xiaoba.coach.R.id.tv_ok).setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				String hundred = hundredArray[WheelHundred.getCurrentItem()];
				String tends = "0";
				switch (WheelHundred.getCurrentItem()) {
				case 0:
					tends = tendsArray1[WheelTends.getCurrentItem()];
					break;
				default:
					tends = tendsArray2[WheelTends.getCurrentItem()];
					break;
				}
				String single = singleArray[WheelSingle.getCurrentItem()];
				if (mOnComfirmClickListener != null) {
					mOnComfirmClickListener.onComfirmBtnClick(hundred, tends, single);
				}
			}
		});
		
		findViewById(xiaoba.coach.R.id.tv_cancel).setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}
	
	class WheelChangedListener implements OnWheelChangedListener {
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			updatePrice(WheelHundred,WheelTends);
		}
	}
	
	private void updatePrice(WheelView hundred, WheelView tends)
	{
		int hundredIndex = hundred.getCurrentItem();
		switch (hundredIndex) {
		case 0:
			tends.setViewAdapter(new PriceAdapter(mContext, tendsArray1, 0));
			tends.setCurrentItem(0);
			break;
		default:
			tends.setViewAdapter(new PriceAdapter(mContext, tendsArray2, 0));
			tends.setCurrentItem(0);
			break;
		}
	}

	
	private class PriceAdapter extends ArrayWheelAdapter<String>
	{
		int currentGet;
		int currentSet;
		public PriceAdapter(Context context, String[] items,int current) {
			super(context, items);
			this.currentSet = current;
			setTextSize(16);
			// TODO Auto-generated constructor stub
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
		void onComfirmBtnClick(String hundred,String tends,String single);
	}
}
