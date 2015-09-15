package xiaoba.coach.activity;

import com.daoshun.lib.util.DensityUtils;
import com.daoshun.lib.view.OnSingleClickListener;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.umeng.analytics.game.UMGameAgent;

import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.activity.HomeActivity.NewMsgReceiver;
import xiaoba.coach.common.Settings;
import xiaoba.coach.views.LoadingDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class BaseActivity extends FragmentActivity {
	public CoachApplication mApplication;
	public LoadingDialog mLoadingDialog;// 普通加载对话框
	NewMsgReceiver receiver;
	IntentFilter filter;
//	InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获得CzbApplication实例
		mApplication = CoachApplication.getInstance();
		// 将activity添加到集合中
		mApplication.addCurrentActivity(this);
		// loading对话框
		mLoadingDialog = new LoadingDialog(this, true);
	    UMGameAgent.setDebugMode(true);//设置输出运行时日志
	    UMGameAgent.init( this );

		receiver = new NewMsgReceiver();
		filter = new IntentFilter();
		filter.addAction("xiaoba.newmsg");
		filter.addCategory(Intent.CATEGORY_DEFAULT);
	}
	
	public class NewMsgReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context arg0, Intent data) {
			showNewMsgDialog(data.getStringExtra("content"));
			abortBroadcast(); // Receiver接收到广播后中断广播
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (receiver != null) {
			receiver = null;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		//PgyFeedbackShakeManager.unregister();
		unregisterReceiver(receiver);
		UMGameAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		//PgyFeedbackShakeManager.register(BaseActivity.this, Settings.PGY_APP_ID);
		registerReceiver(receiver, filter);
		UMGameAgent.onResume(this);
	}

	private Dialog mServiceDialog;

	public void showNewMsgDialog(String content) {
		AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
		mServiceDialog = builder.create();
		mServiceDialog.show();
		mServiceDialog.setContentView(R.layout.dialog_new_msg);
		mServiceDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		WindowManager.LayoutParams params = mServiceDialog.getWindow().getAttributes();
		int maxWidth = Settings.DISPLAY_WIDTH - DensityUtils.dp2px(BaseActivity.this, 40);
		int textWidth = DensityUtils.dp2px(BaseActivity.this, 17 * 15);
		if (maxWidth > textWidth)
			params.width = textWidth;
		else
			params.width = maxWidth;
		params.gravity = Gravity.CENTER;
		mServiceDialog.getWindow().setAttributes(params);
		mServiceDialog.setCanceledOnTouchOutside(true);

		TextView contentTv = (TextView) mServiceDialog.findViewById(R.id.new_msg_content);
		TextView mButton = (TextView) mServiceDialog.findViewById(R.id.new_msg_confirm);
		contentTv.setText(content);
		mButton.setOnClickListener(new OnSingleClickListener(){
			@Override
			public void doOnClick(View v) {
				mServiceDialog.dismiss();
			}
		});
	}
	
//	@Override  
//	public boolean dispatchTouchEvent(MotionEvent ev) {  
//	    if (ev.getAction() == MotionEvent.ACTION_DOWN) {  
//	        View v = getCurrentFocus();  
//	        if (isShouldHideInput(v, ev)) {  
//	  
//	            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
//	            if (imm != null) {  
//	                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
//	            }  
//	        }  
//	        return super.dispatchTouchEvent(ev);  
//	    }  
//	    // 必不可少，否则所有的组件都不会有TouchEvent了  
//	    if (getWindow().superDispatchTouchEvent(ev)) {  
//	        return true;  
//	    }  
//	    return onTouchEvent(ev);  
//	}
//	
//	public  boolean isShouldHideInput(View v, MotionEvent event) {  
//	    if (v != null && (v instanceof EditText)) {  
//	        int[] leftTop = { 0, 0 };  
//	        //获取输入框当前的location位置  
//	        v.getLocationInWindow(leftTop);  
//	        int left = leftTop[0];  
//	        int top = leftTop[1];  
//	        int bottom = top + v.getHeight();  
//	        int right = left + v.getWidth();  
//	        if (event.getX() > left && event.getX() < right  
//	                && event.getY() > top && event.getY() < bottom) {  
//	            // 点击的是输入框区域，保留点击EditText的事件  
//	            return false;  
//	        } else {  
//	            return true;  
//	        }  
//	    }  
//	    return false;  
//	}  
	
    @Override  
    public boolean dispatchTouchEvent(MotionEvent ev) {  
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {  
  
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）  
            View v = getCurrentFocus();  
  
            if (isShouldHideInput(v, ev)) {  
                hideSoftInput(v.getWindowToken());  
            }  
        }  
        return super.dispatchTouchEvent(ev);  
    }  
  
    /** 
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏 
     *  
     * @param v 
     * @param event 
     * @return 
     */  
    private boolean isShouldHideInput(View v, MotionEvent event) {  
        if (v != null && (v instanceof EditText)) {  
            int[] l = { 0, 0 };  
            v.getLocationInWindow(l);  
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left  
                    + v.getWidth();  
            if (event.getX() > left && event.getX() < right  
                    && event.getY() > top && event.getY() < bottom) {  
                // 点击EditText的事件，忽略它。  
                return false;  
            } else {  
                return true;  
            }  
        }  
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点  
        return false;  
    }  
  
    /** 
     * 多种隐藏软件盘方法的其中一种 
     *  
     * @param token 
     */  
    private void hideSoftInput(IBinder token) {  
        if (token != null) {  
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
            im.hideSoftInputFromWindow(token,  
                    InputMethodManager.HIDE_NOT_ALWAYS);  
        }  
    }  
}
