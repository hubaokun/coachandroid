package xiaoba.coach.views;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import xiaoba.coach.R;

public class CoinRulesDialog extends BaseDialog {
	private ImageView imgClose;
	public CoinRulesDialog(Context context) {
		super(context,R.style.dialog);
		// TODO Auto-generated constructor stub
	}
	
	public CoinRulesDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	public CoinRulesDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mContext = context;
	}
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.coin_rules_dialog;
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		imgClose = (ImageView)findViewById(R.id.img_close);
		imgClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}

	@Override
	protected void setWindowParam() {
		// TODO Auto-generated method stub
		setWindowParams(-1, -2, Gravity.CENTER);
		setCanceledOnTouchOutside(true);
	}

}
