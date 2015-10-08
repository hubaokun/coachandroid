package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.view.OnSingleClickListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.activity.ActivityMyCoin.HolderView;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.GetChoseStudentResult;
import xiaoba.coach.net.result.GetChoseStudentResult.StudentList;
import xiaoba.coach.net.result.GetSendInviteCodeResult;
import xiaoba.coach.net.result.GetStudentRestCouponResult;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.utils.ImageLoader;
import xiaoba.coach.views.CoinRulesDialog;
import xiaoba.coach.views.DuiHuanShowDialog;

public class ActivitySendCoupon extends BaseActivity {
	private Context context;
	private ImageView imgBack;
	private TextView tvTitle;
	private TextView tvRight;
	private ListView lvStudent;
	private TextView tvRelease;
	private ArrayList<StudentList> studentArray = new ArrayList<StudentList>();
	private ArrayList<StudentList> chosedArray = new ArrayList<StudentList>();
	private ChoseStudentAdapter choseStudentAda;
	private EditText etStudent;
	private EditText etTicketNum;
	private String studentName = "";
	private String studentPhone;
	private Button btnGrantCoupon;
	private RelativeLayout rlStudentName;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_coupon);
	        context = this;
	        getData();
	        initView();
	        initData();
	        addListener();
	}

	private void getData() {
		// TODO Auto-generated method stub
		new getChoseStudent().execute();
	}

	private void initView() {
		// TODO Auto-generated method stub
		tvTitle = (TextView)findViewById(R.id.title);
		imgBack = (ImageView)findViewById(R.id.title_back_img);
		tvRight = (TextView)findViewById(R.id.title_right_text);
		tvRelease = (TextView)findViewById(R.id.tv_release_tickets);
		lvStudent = (ListView)findViewById(R.id.lv_student);
		etStudent = (EditText)findViewById(R.id.et_student_name);
		etTicketNum = (EditText)findViewById(R.id.et_send_count);
		btnGrantCoupon = (Button)findViewById(R.id.btn_grantcoupon);
		rlStudentName = (RelativeLayout)findViewById(R.id.rl_student_name);
	}

	private void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("发放小巴券");
		tvTitle.setTextColor(Color.parseColor("#2c2c2c"));
		imgBack.setImageResource(R.drawable.back_arrow);
		tvRight.setText("发放记录");
		tvRight.setTextColor(getResources().getColor(R.color.text_green));
		choseStudentAda = new ChoseStudentAdapter(context);
		lvStudent.setAdapter(choseStudentAda);
	}

	private void addListener() {
		// TODO Auto-generated method stub
		tvRight.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(context,ActivitySendHistory.class));
			}
		});
		
		imgBack.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		etStudent.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				int length = s.length();
				if (studentArray.size()==0)
				{
					lvStudent.setVisibility(View.GONE);
					return;
				}
				if (length==0)
				{
					lvStudent.setVisibility(View.GONE);
				}else{
					chosedArray.clear();
					lvStudent.setVisibility(View.VISIBLE);
					for (StudentList student : studentArray) {
						if (student.getPhone().contains(s.toString())||student.getRealname().contains(s.toString()))
						{
							chosedArray.add(student);
						}
					}
					choseStudentAda.notifyDataSetChanged();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		etStudent.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus)
				{
					if (studentName.length()!=0)
					{
						int nameLength = studentName.length();
						String studentInfo = etStudent.getText().toString();
						studentInfo = studentInfo.substring(0,studentInfo.length()-nameLength-3);
						etStudent.setText(studentInfo);
					}
				}
			}
		});
		
		lvStudent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String studentInfo = chosedArray.get(position).getPhone()+"   "+chosedArray.get(position).getRealname();
				studentName = chosedArray.get(position).getRealname();
				studentPhone = chosedArray.get(position).getPhone();
				int studentid = chosedArray.get(position).getStudentid();
				etStudent.setText(studentInfo);
				lvStudent.setVisibility(View.GONE);
				new getStudentRest(studentid).execute();
			}
		});
		
//		rlStudentName.setOnClickListener(new OnSingleClickListener() {
//			
//			@Override
//			public void doOnClick(View v) {
//				// TODO Auto-generated method stub
//				etStudent.setFocusable(true);
//			}
//		});
		
		btnGrantCoupon.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				String number = etTicketNum.getText().toString();
				String studentphone = etStudent.getText().toString();
				if (TextUtils.isEmpty(studentphone))
				{
					Toast.makeText(context, "请填写学员的手机号", 0).show();
					return;
				}
				
				if (TextUtils.isEmpty(number))
				{
					Toast.makeText(context, "请填写小巴券发放数量", 0).show();
					return;
				}
				int count = Integer.parseInt(number);
				if (count>32)
				{
					Toast.makeText(context, "小巴券单次发放数量不超过32张", 0).show();
					etTicketNum.setText("");
					return;
				}
				
				if (TextUtils.isEmpty(number))
				{
					Toast.makeText(context, "请输入小巴券张数", 0).show();
					return;
				}
				
				if (studentPhone==null)
				{
					String phone = etStudent.getText().toString();
					if (isNumeric(phone))
					{
						studentPhone = phone;
						new grantCoupon(number).execute();
					}else{
						Toast.makeText(context, "请输入真确格式的手机号", 0);
						return;
					}
				}else{
					new grantCoupon(number).execute();
				}
			}
		});
		
	}
	
//	 public static boolean isContainChinese(String str) {// 检测是否包含中文
//		  String regEx = "[\\u4E00-\\u9FA5]+";
//		  Pattern p = Pattern.compile(regEx);
//		  Matcher m = p.matcher(str);
//		  if (m.find()) {
//		   return true;
//		  } else {
//		   return false;
//		  }
//		 }
	
	private class getChoseStudent extends AsyncTask<Void, Void, GetChoseStudentResult> {
		JSONAccessor accessor = new JSONAccessor(getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			 if (mLoadingDialog != null)
			 mLoadingDialog.show();
		}
		
		@Override
		protected GetChoseStudentResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GETCOACHSTUDENT");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());// CoachApplication.getInstance().getUserInfo().getCoachid()
			return accessor.execute(Settings.USER_URL, param, GetChoseStudentResult.class);
		}
		
		@Override
		protected void onPostExecute(GetChoseStudentResult result) {
			super.onPostExecute(result);
			 if (mLoadingDialog != null && mLoadingDialog.isShowing())
			 mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					if (result.getStudentlist()!=null&&result.getStudentlist().size()!=0)
					{
						studentArray.addAll(result.getStudentlist());
					}
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ActivitySendCoupon.this);
					}
				}
			} else {
				CommonUtils.showToast(getApplicationContext(), getString(R.string.net_error));
			}
		}
	}
	
	private class getStudentRest extends AsyncTask<Void, Void, GetStudentRestCouponResult> {
		JSONAccessor accessor = new JSONAccessor(getApplicationContext(), JSONAccessor.METHOD_POST);
		int studentId;
		public getStudentRest(int studentid)
		{
			studentId = studentid;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// if (mLoadingDialog != null)
			// mLoadingDialog.show();
		}
		
		@Override
		protected GetStudentRestCouponResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GETSTUDENTCOUPON");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());// CoachApplication.getInstance().getUserInfo().getCoachid()
			param.put("studentid", studentId+"");
			return accessor.execute(Settings.USER_URL, param, GetStudentRestCouponResult.class);
		}
		
		@Override
		protected void onPostExecute(GetStudentRestCouponResult result) {
			super.onPostExecute(result);
			// if (mLoadingDialog != null && mLoadingDialog.isShowing())
			// mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					tvRelease.setText("该学员已用小巴券"+result.getTotal()+"张，剩余"+result.getRest()+"张");
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ActivitySendCoupon.this);
					}
				}
			} else {
				CommonUtils.showToast(getApplicationContext(), getString(R.string.net_error));
			}
		}
	}
	
	private class grantCoupon extends AsyncTask<Void, Void, BaseResult> {
		JSONAccessor accessor = new JSONAccessor(getApplicationContext(), JSONAccessor.METHOD_POST);
		String TicketNumber;
		public grantCoupon(String number)
		{
			TicketNumber = number;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			 if (mLoadingDialog != null)
			 mLoadingDialog.show();
		}
		
		@Override
		protected BaseResult doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GRANTCOUPON");
			param.put("coachid", CoachApplication.getInstance().getUserInfo().getCoachid());// CoachApplication.getInstance().getUserInfo().getCoachid()
			param.put("phone", studentPhone);
			param.put("pubnum", TicketNumber);
			return accessor.execute(Settings.USER_URL, param, BaseResult.class);
		}
		
		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			 if (mLoadingDialog != null && mLoadingDialog.isShowing())
			 mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					Toast.makeText(context, "您的小巴券发放成功", 0).show();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(ActivitySendCoupon.this);
					}
				}
			} else {
				CommonUtils.showToast(getApplicationContext(), getString(R.string.net_error));
			}
		}
	}
	
	private class ChoseStudentAdapter extends BaseAdapter
	{
		  private LayoutInflater inflater;
		  public ChoseStudentAdapter(Context mcontext) {
	            this.inflater = LayoutInflater.from(context);
	     }

	        
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return chosedArray.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return chosedArray.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			HolderView holder = null;
            if (convertView == null){
                holder = new HolderView();
                convertView = inflater.inflate(R.layout.chose_student_name_item,null);
                holder.tvName = (TextView)convertView.findViewById(R.id.tv_name);
                holder.tvPhone = (TextView)convertView.findViewById(R.id.tv_phone);
                convertView.setTag(holder);
            }else {
                holder = (HolderView)convertView.getTag();
            }
            
            holder.tvName.setText(chosedArray.get(position).getRealname());
            holder.tvPhone.setText(chosedArray.get(position).getPhone());
			return convertView;
		}
	}
	
	private class HolderView
	{
		private TextView tvName;
		private TextView tvPhone;
	}
	
	public static boolean isNumeric(String str){  
	    Pattern pattern = Pattern.compile("[0-9]*");  
	    return pattern.matcher(str).matches();     
	}  
}
