package xiaoba.coach.utils;

import xiaoba.coach.common.Settings;
import android.content.Context;

import com.daoshun.lib.util.ImageLoader;

public class CacheImageLoader extends ImageLoader {

	// 取得缓存图片
	public CacheImageLoader(Context context) {
		super(context, Settings.PIC_PATH, Settings.TEMP_PATH);
	}
}
