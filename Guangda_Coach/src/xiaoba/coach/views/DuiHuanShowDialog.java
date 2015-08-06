package xiaoba.coach.views;

import com.daoshun.lib.view.OnSingleClickListener;

import xiaoba.coach.R;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DuiHuanShowDialog extends BaseDialog {
	private TextView tvName;
	private ImageView imgClose;
	private TextView tvId;
	private TextView tvFrom;
	private TextView tvCount;
	private TextView tvMoney;
	private TextView tvTime;

	public DuiHuanShowDialog(Context context) {
		super(context,R.style.dialog);
		// TODO Auto-generated constructor stub
	}
	
	public DuiHuanShowDialog(Context context, int theme) {
		super(context, theme);
	}

	public DuiHuanShowDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}
	
	public void setMessage(String Id,String Name,String From,String Count,String Money,String Time)
	{
		tvId.setText(Id);
		tvName.setText(Name);
		tvFrom.setText(From);
		tvCount.setText(Count);
		tvMoney.setText(Money);
		tvTime.setText(Time);
		show();
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.show_coin_detail_dialog;
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		tvId = (TextView)findViewById(R.id.tv_duihuan_id);
		tvName = (TextView)findViewById(R.id.tv_duihuan_name);
		tvFrom = (TextView)findViewById(R.id.tv_duihuan_from);
		tvCount = (TextView)findViewById(R.id.tv_duihuan_count);
		tvMoney = (TextView)findViewById(R.id.tv_zhesuan_money);
		tvTime = (TextView)findViewById(R.id.tv_shenqing_time);
		imgClose = (ImageView)findViewById(R.id.img_close);
		
		imgClose.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
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
