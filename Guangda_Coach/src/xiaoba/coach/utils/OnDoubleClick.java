package xiaoba.coach.utils;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public abstract class OnDoubleClick implements OnTouchListener {
	private int count = 0;
	// 第一次点击的时间 long型
	private long firstClick = 0;
	// 最后一次点击的时间
	private long lastClick = 0;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (MotionEvent.ACTION_DOWN == event.getAction()) {
			count++;
			if (count == 1) {
				firstClick = System.currentTimeMillis();

			} else if (count == 2) {
				lastClick = System.currentTimeMillis();
				if (lastClick - firstClick < 1000) {
					doOnClick(v);
				}
				count = 0;
				firstClick = 0;
				lastClick = 0;
			} else {
				count = 0;
				firstClick = 0;
				lastClick = 0;
			}
		}
		return true;
	}

	public abstract void doOnClick(View v);

}
