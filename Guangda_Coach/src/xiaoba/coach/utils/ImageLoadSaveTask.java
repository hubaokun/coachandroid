package xiaoba.coach.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import xiaoba.coach.R;

/**
 * Created by Administrator on 2015/3/13.
 */
public class ImageLoadSaveTask extends AsyncTask<String,Integer,Bitmap> {
    private ImageView avatar;
    private Context mcontext;
   // private String avatarPath;
    private static OnImageLoad mOnImageLoad;
    public ImageLoadSaveTask(Context context, ImageView img) {
        this.avatar = img;
        this.mcontext = context;
     //   avatarPath = path;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            bitmap = ImgUtil.getHttpBitmap(params[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {//后台任务执行完之后被调用，在ui线程执行
        if (result == null)
        {
            avatar.setImageResource(R.drawable.im_advertisement);
        }else {
            avatar.setImageBitmap(result);
            //saveMyBitmap(avatarPath,result);
            if (mOnImageLoad != null) {
            	mOnImageLoad.showCancle(true);
            	mOnImageLoad = null;
    		}
        }
    }
    
    public static void setImageShowListener(OnImageLoad l){
    	mOnImageLoad=l;
    }
    
    public interface OnImageLoad{
    	void showCancle(Boolean image);
    }
    
    
    public void saveMyBitmap(String path, Bitmap mBitmap) {
        File f = new File(path);
        try {
            f.createNewFile();
           /* photoWindow.dismiss();*/
        } catch (IOException e) {
            Toast.makeText(mcontext, "保存图片出错", Toast.LENGTH_SHORT).show();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 20, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
