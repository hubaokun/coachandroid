package xiaoba.coach.views;

import java.util.ArrayList;
import java.util.List;

import org.androidannotations.annotations.ViewById;

import xiaoba.coach.R;
import xiaoba.coach.activity.ClassTimeSetActivity;
import xiaoba.coach.module.Schedule;
import xiaoba.coach.net.result.GetScheduleResult;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShowSetClassDialog extends BaseDialog{
	private ImageView mIsRest;
	private TextView mCloseTip;
	private Button btnSetClass;
	private Boolean isRest;
	String mor, aft, nig;
	String day; 
	GetScheduleResult result;
	int single;
	List<Schedule> list, templist = new ArrayList<Schedule>();
	private LinearLayout llClose;
	private RelativeLayout rlOpen;

	public ShowSetClassDialog(Context context) {
		super(context,R.style.dialog);
		// TODO Auto-generated constructor stub
	}

	public ShowSetClassDialog(Context context, int theme) {
		super(context, theme);
	}

	public ShowSetClassDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.show_setclass_dialog;
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		mIsRest = (ImageView)findViewById(R.id.btn_isrest);
		mCloseTip = (TextView)findViewById(R.id.open_close_tip);
		rlOpen = (RelativeLayout)findViewById(R.id.rl_open_class);
		llClose = (LinearLayout)findViewById(R.id.ll_close_class);
		mIsRest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isRest = !isRest;
				if (isRest)
				{
					rlOpen.setVisibility(View.VISIBLE);
					llClose.setVisibility(View.GONE);
				}else{
					rlOpen.setVisibility(View.GONE);
					llClose.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	
	public void setDate(Boolean rest,int single,String day,GetScheduleResult result)
	{
		isRest = !rest;
		this.single = single;
		this.day = day;
		this.result = result;
		list = result.getDatelist();
		if (list != null)
			templist.addAll(list);
		if (isRest) {
			mIsRest.setImageResource(R.drawable.button_off);
			mCloseTip.setText(mContext.getString(R.string.classset_close_tip));
			//btnSetClass.setClickable(false);
		}else{
			mIsRest.setImageResource(R.drawable.button_on);
			mCloseTip.setText(mContext.getString(R.string.classset_open_tip));
		}
	}
	

	@Override
	protected void setWindowParam() {
		// TODO Auto-generated method stub
		setWindowParams(-1, -2, Gravity.CENTER);
		setCanceledOnTouchOutside(true);
	}

}
