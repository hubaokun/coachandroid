package xiaoba.coach.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.androidannotations.annotations.UiThread;

import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityCoachRules extends BaseActivity {
	private Context mcontext;
	private TextView tvTitle;
	private ImageView imgBack;
	private String url = Settings.BASE_SHARE+"servicestandard.html";
	private WebView webRelus;
	private ImageView imgNoWeb;
//	private TextView tvRules;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_coach_rules);
	        mcontext = this;
	        initView();
	        initData();
	        addListener();
	    }
	private void initView() {
		// TODO Auto-generated method stub
		imgBack = (ImageView)findViewById(R.id.title_back_img);
		tvTitle = (TextView)findViewById(R.id.title);
		webRelus = (WebView)findViewById(R.id.web_relus);
		imgNoWeb = (ImageView)findViewById(R.id.img_no_web);
//		tvRules = (TextView)findViewById(R.id.tv_rules);
		getRules();
	}
	private void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("服务标准及约定");
		tvTitle.setTextColor(Color.parseColor("#2c2c2c"));
		imgBack.setImageResource(R.drawable.back_arrow);
	}
	private void addListener() {
		// TODO Auto-generated method stub
		imgBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 
				finish();
			}
		});
		
		imgNoWeb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				webRelus.loadUrl(url);
//				webRelus.setVisibility(View.VISIBLE);
//				imgNoWeb.setVisibility(View.GONE);
			}
		});
	}
	 
	private void getRules()
	{
		webRelus.loadUrl(url);
		webRelus.setWebViewClient(new WebViewClient()
				{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// return super.shouldOverrideUrlLoading(view, url);
				WebChromeClient wvcc = new WebChromeClient(){ 
				      @Override  
				      public void onReceivedTitle(WebView view, String title){ 
				        super.onReceivedTitle(view, title);
				        tvTitle.setText(title); 
				      }
				    }; 
				    // 设置setWebChromeClient对象  
				    view.setWebChromeClient(wvcc); 
				return true;
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				// super.onPageFinished(view, url);
			}
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				tvTitle.setText("服务标准及约定");
				view.setVisibility(View.GONE);
				imgNoWeb.setVisibility(View.VISIBLE);
				
				}
		});
		

//		tvRules.setText(getString(inputStream));
	}
	
//	public static String getString(InputStream inputStream) {  
//	    InputStreamReader inputStreamReader = null;  
//	    try {  
//	        inputStreamReader = new InputStreamReader(inputStream, "gbk");  
//	    } catch (UnsupportedEncodingException e1) {  
//	        e1.printStackTrace();  
//	    }  
//	    BufferedReader reader = new BufferedReader(inputStreamReader);  
//	    StringBuffer sb = new StringBuffer("");  
//	    String line;  
//	    try {  
//	        while ((line = reader.readLine()) != null) {  
//	            sb.append(line);  
//	            sb.append("\n");  
//	        }  
//	    } catch (IOException e) {  
//	        e.printStackTrace();  
//	    }  
//	    return sb.toString();  
//	}  
}
