package xiaoba.coach.views;


import java.io.File;

import xiaoba.coach.R;
import xiaoba.coach.activity.CheckStudentActivity;
import xiaoba.coach.common.Settings;
import xiaoba.coach.utils.CommonUtils;

import com.daoshun.lib.util.DensityUtils;
import com.daoshun.lib.view.OnSingleClickListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoDetailDialog {
	private Dialog mServiceDialog;

	public PhotoDetailDialog(Context context, File imgFile) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		mServiceDialog = builder.create();
		mServiceDialog.show();
		mServiceDialog.setContentView(R.layout.show_photo_detail_view);
		mServiceDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		WindowManager.LayoutParams params = mServiceDialog.getWindow().getAttributes();
		int maxWidth = Settings.DISPLAY_WIDTH - DensityUtils.dp2px(context, 40);
		int height = Settings.DISPLAY_HEIGHT - DensityUtils.dp2px(context, 40);
		// int textWidth = DensityUtils.dp2px(context, 17 * 15);
		// if (maxWidth > textWidth)
		// params.width = textWidth;
		// else
		// params.width = maxWidth;
		params.gravity = Gravity.CENTER;
		mServiceDialog.getWindow().setAttributes(params);
		mServiceDialog.setCanceledOnTouchOutside(true);

		ImageView imgDetail = (ImageView) mServiceDialog.findViewById(R.id.img_photo_detail);
		Bitmap bitmap = CommonUtils.getBitmapFromFile(imgFile, maxWidth, height);
		imgDetail.setImageBitmap(bitmap);
	}

}
