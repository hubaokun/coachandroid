package xiaoba.coach.activity;

import xiaoba.coach.R;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

public class ShowHandleActivity extends BaseActivity {
	protected void onCreate(Bundle savedInstanceState) {  
	    super.onCreate(savedInstanceState);  
	    setContentView(R.layout.show_handle_activity);
	    final Window win = getWindow();  
	    win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED  
	            | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD  
	            | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON  
	            | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);  
	  
	    // 自己的代码  
	    
	    
	    PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);  
	    if (!pm.isScreenOn()) {  
	        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |  
	                PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");  
	        wl.acquire();  
	        wl.release();  
	    }  
	} 
}
