package xiaoba.coach.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆型imageview
 * 
 * @author xingf
 * 
 */
public class RoundImageView extends ImageView {

	public RoundImageView(Context context) {
		super(context);
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		BitmapDrawable drawable = (BitmapDrawable) getDrawable();

		if (drawable == null) {
			return;
		}

		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}

		Bitmap fullSizeBitmap = drawable.getBitmap();

		int scaledWidth = getMeasuredWidth();
		int scaledHeight = getMeasuredHeight();

		Bitmap mScaledBitmap;
		if (scaledWidth == fullSizeBitmap.getWidth() && scaledHeight == fullSizeBitmap.getHeight()) {
			mScaledBitmap = fullSizeBitmap;
		} else {
			mScaledBitmap = Bitmap.createScaledBitmap(fullSizeBitmap, scaledWidth, scaledHeight, true /* filter */);
		}

		Bitmap roundBitmap = getRoundedBitmap(getContext(), mScaledBitmap, scaledWidth, scaledHeight);
		canvas.drawBitmap(roundBitmap, 0, 0, null);

	}

	/*
	 * 取圆型bitmap
	 */
	public static Bitmap getRoundedBitmap(Context context, Bitmap input, int w, int h) {

		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();

		int r = 0;
		// 取最短边做边长
		if (w > h) {
			r = h;
		} else {
			r = w;
		}

		RectF rectF = new RectF(0, 0, r, r);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, r / 2, r / 2, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(input, null, rectF, paint);

		return output;
	}
}
