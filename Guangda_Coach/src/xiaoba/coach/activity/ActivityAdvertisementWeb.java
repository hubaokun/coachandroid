package xiaoba.coach.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import xiaoba.coach.R;

public class ActivityAdvertisementWeb extends BaseActivity {
	private ImageView imgGoBack;
	private ImageView imgFinish;
	private TextView tvTitle;
	private WebView webAdver;
	private String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advertise_url);
		initView();
		addListener();
		initData();
		init();
	}

	private void initView() {
		// TODO Auto-generated method stub
		imgGoBack = (ImageView)findViewById(R.id.img_go_back);
		imgFinish = (ImageView)findViewById(R.id.img_finish);
		tvTitle = (TextView)findViewById(R.id.tv_title);
		webAdver = (WebView)findViewById(R.id.web_advertise);
		Bundle bundle = getIntent().getExtras();
		url = bundle.getString("url");
		
	}

	private void addListener() {
		// TODO Auto-generated method stub
		imgGoBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(webAdver.canGoBack()){
					webAdver.goBack();
				}else{
					finish();
				}
			}
		});
		
		imgFinish.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		 WebChromeClient wvcc = new WebChromeClient() {  
	            @Override  
	            public void onReceivedTitle(WebView view, String title) {  
	                super.onReceivedTitle(view, title);  
	                tvTitle.setText(title);  
	            }  
	        };  
	        
	        webAdver.setWebChromeClient(wvcc);
	        
	        webAdver.setWebViewClient(new WebViewClient(){
	            @Override
	         public boolean shouldOverrideUrlLoading(WebView view, String url) {
	             // TODO Auto-generated method stub
	                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
	              view.loadUrl(url);
	             return true;
	         }
	        });  
	        
	}

	private void initData() {
		// TODO Auto-generated method stub
		WebSettings s = webAdver.getSettings();    
		s.setBuiltInZoomControls(true);    
		s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);     
		s.setUseWideViewPort(true);     
		s.setLoadWithOverviewMode(true);    
		s.setSavePassword(true);     
		s.setSaveFormData(true);     
		s.setJavaScriptEnabled(true);     // enable navigator.geolocation     
		s.setGeolocationEnabled(true);     
		//s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");     // enable Web Storage: localStorage, sessionStorage     
		s.setDomStorageEnabled(true); 
		webAdver.requestFocus();
		webAdver.loadUrl(url);
	}

	private void init() {
		// TODO Auto-generated method stub
		
	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
        	if(webAdver.canGoBack()){
				webAdver.goBack();
			}else{
				finish();
			}
            return true;  
        } else  
            return super.onKeyDown(keyCode, event);  
    }
}
