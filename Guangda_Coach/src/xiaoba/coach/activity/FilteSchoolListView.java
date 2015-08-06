package xiaoba.coach.activity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.view.OnSingleClickListener;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import xiaoba.coach.R;
import xiaoba.coach.activity.ActivityMyCoinsList.HolderView;
import xiaoba.coach.common.Settings;
import xiaoba.coach.interfaces.DialogConfirmListener;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.School;
import xiaoba.coach.net.result.GetSchoolResult;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.views.SelectDialog;

public class FilteSchoolListView extends BaseActivity {
	private TextView tvTitle;
	private ImageView imgBack;
	private ListView lvSchool;
	private List<String> getSchool = new ArrayList<String>();
	private List<String> newSchool = new ArrayList<String>();
	private EditText etSchool;
	private SchoolAdapter schoolAda;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.filte_school_list);
	    findView();
	    tvTitle.setText("所属驾校");
	    tvTitle.setTextColor(getResources().getColor(R.color.text_black));
	    imgBack.setImageResource(R.drawable.back_arrow);
	    schoolAda = new SchoolAdapter(this);
	    lvSchool.setAdapter(schoolAda);
	    new GetAllSchool().execute();
	    addListener();
	}
	
	private void addListener()
	{
		etSchool.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				newSchool.clear();
				String input = etSchool.getText().toString().trim();
				for (String school:getSchool)
				{
					if (school.contains(input))
					{
						newSchool.add(school);
					}
				}
				schoolAda.notifyDataSetChanged();
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
		
		imgBack.setOnClickListener(new OnSingleClickListener() {
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		lvSchool.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				String choseSchool = newSchool.get(position);
				Intent intent = new Intent();
				intent.putExtra("school", choseSchool);
				setResult(1000, intent);
				finish();
			}
		});
	}
	private void findView()
	{
		tvTitle = (TextView)findViewById(R.id.title);
		imgBack = (ImageView)findViewById(R.id.title_back_img);
		etSchool = (EditText)findViewById(R.id.et_school);
		lvSchool = (ListView)findViewById(R.id.lv_school);
	}
	
	
	private class GetAllSchool extends AsyncTask<Void, Void, GetSchoolResult> {
		JSONAccessor accessor = new JSONAccessor(FilteSchoolListView.this.getApplicationContext(), JSONAccessor.METHOD_POST_MULTIPART);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected GetSchoolResult doInBackground(Void... params) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetAllSchool");
			return accessor.execute(Settings.CMY_URL, param, GetSchoolResult.class);
		}

		@Override
		protected void onPostExecute(GetSchoolResult result) {
			super.onPostExecute(result);

			if (result != null) {
				if (result.getCode() == 1) {
					for (int i = 0; i < result.getSchoollist().size(); i++) {
						getSchool.add(result.getSchoollist().get(i).getName().toString());
					}
					getSchool.add("其他");
					newSchool.addAll(getSchool);
					schoolAda.notifyDataSetChanged();
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(FilteSchoolListView.this, result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(FilteSchoolListView.this);
					}
				}
			} else {
				CommonUtils.showToast(FilteSchoolListView.this, getString(R.string.net_error));
			}
		}
	}
	
	private class SchoolAdapter extends BaseAdapter
	{
		private LayoutInflater inflater;
		

		 public SchoolAdapter(Context context)
		 {
			 this.inflater = LayoutInflater.from(context);
		 }

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return newSchool.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return newSchool.get(position);
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
                convertView = inflater.inflate(R.layout.school_list_item,null);
                holder.tvSchool = (TextView)convertView.findViewById(R.id.tv_school);
                convertView.setTag(holder);
            }else {
                holder = (HolderView)convertView.getTag();
            }
            holder.tvSchool.setText(newSchool.get(position));
			return convertView;
		}
	}
	
	private class HolderView
	{
		private TextView tvSchool;
	}
	
}
