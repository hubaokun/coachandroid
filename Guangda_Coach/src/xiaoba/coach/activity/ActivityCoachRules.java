package xiaoba.coach.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import xiaoba.coach.R;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityCoachRules extends BaseActivity {
	private Context mcontext;
	private TextView tvTitle;
	private ImageView imgBack;
	private TextView tvRules;
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
		tvRules = (TextView)findViewById(R.id.tv_rules);
		getRules();
	}
	private void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("会员注册协议");
		tvTitle.setTextColor(Color.parseColor("#2c2c2c"));
		imgBack.setImageResource(R.drawable.back_arrow);
	}
	private void addListener() {
		// TODO Auto-generated method stub
		
	}
	 
	private void getRules()
	{
		InputStream inputStream = getResources().openRawResource(R.raw.coach_rules);
		tvRules.setText(getString(inputStream));
	}
	
	public static String getString(InputStream inputStream) {  
	    InputStreamReader inputStreamReader = null;  
	    try {  
	        inputStreamReader = new InputStreamReader(inputStream, "gbk");  
	    } catch (UnsupportedEncodingException e1) {  
	        e1.printStackTrace();  
	    }  
	    BufferedReader reader = new BufferedReader(inputStreamReader);  
	    StringBuffer sb = new StringBuffer("");  
	    String line;  
	    try {  
	        while ((line = reader.readLine()) != null) {  
	            sb.append(line);  
	            sb.append("\n");  
	        }  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    return sb.toString();  
	}  
}
