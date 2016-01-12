package xiaoba.coach.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import xiaoba.coach.R;
import xiaoba.coach.common.Settings;

public class ActivityStarCoach extends BaseActivity {
	private TextView tvTitle;
	private ImageView imgBack;
	private String url = Settings.BASE_SHARE+"popularcoaches.html";
	private WebView starWebCoach;
	private ImageView imgStarCoach;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_star_coach_relus);
	        initView();
	        initData();
	        addListener();
	    }

	private void initView() {
		// TODO Auto-generated method stub
		imgBack = (ImageView)findViewById(R.id.title_back_img);
		tvTitle = (TextView)findViewById(R.id.title);
		starWebCoach = (WebView)findViewById(R.id.web_star_relus);
		imgStarCoach = (ImageView)findViewById(R.id.img_star_relus);
		getRules();
	}

	private void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("小巴明星教练服务协议");
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
		
		imgStarCoach.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//starWebCoach.loadUrl(url);
				getRules();
//				starWebCoach.setVisibility(View.VISIBLE);
//				imgStarCoach.setVisibility(View.GONE);
			}
		});
	}
	
	private void getRules()
	{
		starWebCoach.loadUrl(url);
		starWebCoach.setWebViewClient(new WebViewClient()
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
					view.setVisibility(View.VISIBLE);
					imgStarCoach.setVisibility(View.GONE);
				return true;
			}
			
//			@Override  
//            public void onPageFinished(WebView view,String url)  
//            {
//				WebChromeClient wvcc = new WebChromeClient(){
//				      @Override  
//				      public void onReceivedTitle(WebView view, String title){ 
//				        super.onReceivedTitle(view, title);
//				        tvTitle.setText(title);
//				      }
//				    };
//				    // 设置setWebChromeClient对象  
//				    starWebCoach.setWebChromeClient(wvcc); 
//            }  
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				tvTitle.setText("小巴明星教练服务协议");
				super.onReceivedError(view, errorCode, description, failingUrl);
				view.setVisibility(View.GONE);
				imgStarCoach.setVisibility(View.VISIBLE);
				
				}
		});
		
//		 WebChromeClient wvcc = new WebChromeClient() {  
//	            @Override  
//	            public void onReceivedTitle(WebView view, String title) {  
//	                super.onReceivedTitle(view, title);  
//	                tvTitle.setText(title);  
//	            }  
//	  
//	        };  
//	        starWebCoach.setWebChromeClient(wvcc);
//		tvRules.setText(getString(inputStream));
	}
}
