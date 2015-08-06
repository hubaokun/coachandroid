package xiaoba.coach.views;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import xiaoba.coach.R;
import xiaoba.coach.utils.ImageLoadSaveTask;
import xiaoba.coach.utils.ImageLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.Gravity;
import android.widget.ImageView;

public class ShowAdvertisementDialog extends BaseDialog {
	private ImageView imgAdver;
	private ImageLoader imgloader;

	public ShowAdvertisementDialog(Context context) {
		super(context,R.style.dialog);
		imgloader = new ImageLoader(context, R.drawable.portrait_test);
		// TODO Auto-generated constructor stub
	}
	
	public ShowAdvertisementDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
		imgloader = new ImageLoader(context, R.drawable.portrait_test);
	}

	public ShowAdvertisementDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mContext = context;
		imgloader = new ImageLoader(context, R.drawable.portrait_test);
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.show_advertisement;
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		imgAdver = (ImageView)findViewById(R.id.img_advertisement);
	}

	@Override
	protected void setWindowParam() {
		// TODO Auto-generated method stub
		setWindowParams(-1, -2, Gravity.CENTER);
		setCanceledOnTouchOutside(false);
	}
	
	public void setImage(String url)
	{
		   try {
               new ImageLoadSaveTask(mContext, imgAdver).execute(url);
       		   show();
           } catch (Exception e) {
               e.printStackTrace();
           }

	}
	
//	 public  Bitmap getHttpBitmap(String url){  
//	        URL myFileURL;  
//	        Bitmap bitmap=null;  
//	        try{  
//	            myFileURL = new URL(url);  
//	            //获得连接  
//	            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();  
//	            //设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制  
//	            conn.setConnectTimeout(6000);  
//	            //连接设置获得数据流  
//	            conn.setDoInput(true);  
//	            //不使用缓存  
//	            conn.setUseCaches(false);  
//	            //这句可有可无，没有影响  
//	            //conn.connect();  
//	            //得到数据流  
//	            InputStream is = conn.getInputStream();  
//	            //解析得到图片  
//	            bitmap = BitmapFactory.decodeStream(is);  
//	            //关闭数据流  
//	            is.close();  
//	        }catch(Exception e){  
//	            e.printStackTrace();  
//	        }  
//	          
//	        return bitmap;  
//	          
//	    }  
}
