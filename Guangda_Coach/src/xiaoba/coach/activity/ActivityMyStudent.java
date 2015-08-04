package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.grobas.view.PolygonImageView;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.listview.PullToRefreshBase;
import com.daoshun.lib.listview.PullToRefreshListView;
import com.daoshun.lib.listview.PullToRefreshBase.Mode;
import com.daoshun.lib.view.OnSingleClickListener;

import xiaoba.coach.R;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.net.result.DoneOrderListResult;
import xiaoba.coach.net.result.GetMyStudentResult;
import xiaoba.coach.net.result.GetMyStudentResult.myStudent;
import xiaoba.coach.utils.CommonUtils;
import xiaoba.coach.utils.ImageLoader;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMyStudent extends BaseActivity {

    private Context context;
    private ImageView imgBack;
    private PullToRefreshListView mListView;
    int mPageNum;
    private List<myStudent> list = new ArrayList<GetMyStudentResult.myStudent>();
    int mShowHidePosition = -1; // mark the item show the content hided
    private MyAllStudentAdapter myStudentAda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_student);
        context = this;
        initView();
        addListener();
        initData();
    }

    private void initView() {
        // TODO Auto-generated method stub
        imgBack = (ImageView) findViewById(R.id.img_title_left);
        mListView = (PullToRefreshListView) findViewById(R.id.pulltorefresh);
    }

    private void addListener() {
        // TODO Auto-generated method stub
        imgBack.setOnClickListener(new OnSingleClickListener() {

            @Override
            public void doOnClick(View v) {
                // TODO Auto-generated method stub
                ActivityMyStudent.this.finish();
            }
        });

        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPageNum = 0;
                // doRefresh();
                new getAllMyStudent().execute();

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mPageNum++;
                new getAllMyStudent().execute();
            }
        });
    }

    private void initData() {
        // TODO Auto-generated method stub
        mListView.setRefreshing();
        myStudentAda = new MyAllStudentAdapter(context);
        mListView.setAdapter(myStudentAda);
    }

    private class getAllMyStudent extends AsyncTask<Void, Void, GetMyStudentResult> {

        JSONAccessor accessor = new JSONAccessor(ActivityMyStudent.this.getApplicationContext(),
                JSONAccessor.METHOD_POST);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected GetMyStudentResult doInBackground(Void... params) {
            // TODO Auto-generated method stub
            HashMap<String, Object> param = new BaseParam();
            param.put("action", "getMyAllStudent");
            param.put("coachid", mApplication.getUserInfo().getCoachid());
            param.put("pagenum", mPageNum);
            return accessor.execute(Settings.CMY_URL, param, GetMyStudentResult.class);
        }

        @Override
        protected void onPostExecute(GetMyStudentResult result) {
            super.onPostExecute(result);
            if (mListView != null)
                mListView.onRefreshComplete();
            if (result != null) {
                if (result.getCode() == 1) {

                    if (result.getStudentList() != null && result.getStudentList().size() > 0) {
                        if (mPageNum == 0)
                            // if put this operation before request the list ui will change very
                            // visible
                            list.clear();
                        list.addAll(result.getStudentList());
                        myStudentAda.notifyDataSetChanged();
                    } else {
                        // show no data layout
                    }
                    if (result.getHasmore() == 0) {
                        mListView.setMode(Mode.PULL_FROM_START);
                    } else {
                        mListView.setMode(Mode.BOTH);
                    }
                } else {
                    if (result.getMessage() != null)
                        CommonUtils.showToast(ActivityMyStudent.this.getApplicationContext(),
                                result.getMessage());
                    if (result.getCode() == 95) {
                        CommonUtils.gotoLogin(ActivityMyStudent.this);
                    }
                }

            } else {
                CommonUtils.showToast(ActivityMyStudent.this.getApplicationContext(),
                        getString(R.string.net_error));
            }
        }
    }

    private class MyAllStudentAdapter extends BaseAdapter {

        private LayoutInflater inflater;
        private ImageLoader imgLoader;

        public MyAllStudentAdapter(Context mcontext) {
            this.inflater = LayoutInflater.from(context);
            imgLoader = new ImageLoader(mcontext, R.drawable.portrait_test);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            convertView = inflater.inflate(R.layout.item_my_student, null);
            LinearLayout mainPart = (LinearLayout) convertView.findViewById(R.id.jo_main_part);
            RelativeLayout hidePart = (RelativeLayout) convertView.findViewById(R.id.hide_content);
            ImageView arrow = (ImageView) convertView.findViewById(R.id.jo_item_arrow);
            mainPart.setOnClickListener(new ShowHideListener(hidePart, arrow, position));
            TextView tvName = (TextView) convertView.findViewById(R.id.tv_my_student_name);
            TextView tvTimeCount = (TextView) convertView.findViewById(R.id.tv_time_count);
            TextView tvMoneyCount = (TextView) convertView.findViewById(R.id.tv_money_count);
            TextView tvTimeCountInMine =
                    (TextView) convertView.findViewById(R.id.tv_time_count_in_mine);
            PolygonImageView imgStudentAvater =
                    (PolygonImageView) convertView.findViewById(R.id.potrait);
            TextView tvStudentName = (TextView) convertView.findViewById(R.id.jo_stuname);
            TextView tvTel = (TextView) convertView.findViewById(R.id.jo_stutel);
            TextView tvCard = (TextView) convertView.findViewById(R.id.jo_stucard);
            ImageView imgPortrait = (ImageView) convertView.findViewById(R.id.big_portrait);
            LinearLayout tousu = (LinearLayout) convertView.findViewById(R.id.jo_tousu);
            LinearLayout contact = (LinearLayout) convertView.findViewById(R.id.jo_contact);
            myStudent getMyStudent = list.get(position);
            if (position == mShowHidePosition) {
                hidePart.setVisibility(View.VISIBLE);
                arrow.setImageResource(R.drawable.arrow_blue);
            } else {
                if (hidePart.getVisibility() == View.VISIBLE)
                    hidePart.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.arrow_grey);
            }
            if (getMyStudent == null) {
                return convertView;
            }
            if (getMyStudent.getRealname() != null)
                tvName.setText(getMyStudent.getRealname().toString().trim());

            tvMoneyCount.setText(String.valueOf(getMyStudent.getMoney()));
            tvTimeCount.setText(String.valueOf(getMyStudent.getLearntime()));
//            if (getMyStudent.getLearnmytime() == 0) {
//                tvTimeCountInMine.setVisibility(View.GONE);
//            } else {
//                tvTimeCountInMine.setVisibility(View.VISIBLE);
                tvTimeCountInMine.setText(String.valueOf(getMyStudent.getLearnmytime()) + "/");
            //}
            imgLoader.DisplayImage(getMyStudent.getAvatar(), imgStudentAvater);
            imgLoader.DisplayImage(getMyStudent.getAvatar(), imgPortrait);
            if (getMyStudent.getRealname() != null)
                tvStudentName.setText(getMyStudent.getRealname().toString().trim());
            if (getMyStudent.getPhone() == null) {
                tvTel.setText("");
                contact.setOnClickListener(new ContactListener(null));
                tousu.setOnClickListener(new MessageListener(null));
            } else {
                tvTel.setText(getMyStudent.getPhone().trim());
                contact.setOnClickListener(new ContactListener(getMyStudent.getPhone()));
                tousu.setOnClickListener(new MessageListener(getMyStudent.getPhone()));
            }
            if (getMyStudent.getStudent_cardnum() == null) {
                tvCard.setText("");
            } else {
                tvCard.setText(getMyStudent.getStudent_cardnum().trim());
            }
            return convertView;
        }

        class ShowHideListener extends OnSingleClickListener {

            View view;
            ImageView arrow;
            int position;

            public ShowHideListener(View view, ImageView arrow, int position) {
                this.view = view;
                this.arrow = arrow;
                this.position = position;
            }

            @Override
            public void doOnClick(View v) {
                if (view.getVisibility() == View.VISIBLE) {
                    view.setVisibility(View.GONE);
                    arrow.setImageResource(R.drawable.arrow_grey);
                    mShowHidePosition = -1;
                } else {
                    view.setVisibility(View.VISIBLE);
                    arrow.setImageResource(R.drawable.arrow_blue);
                    mShowHidePosition = position;
                }

                myStudentAda.notifyDataSetChanged();
            }
        }

        class ContactListener extends OnSingleClickListener {

            String tel;

            public ContactListener(String tel) {
                this.tel = tel;
            }

            @Override
            public void doOnClick(View v) {
                if (tel == null) {
                    Toast.makeText(ActivityMyStudent.this.getApplicationContext(),
                            ActivityMyStudent.this.getString(R.string.lacktel), Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                try {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel)));
                } catch (Exception e) {
                    CommonUtils.showToast(ActivityMyStudent.this.getApplicationContext(),
                            "This telephone number is not well formated.");
                }

            }
        }

        class MessageListener extends OnSingleClickListener {

            String phone;

            public MessageListener(String phone) {
                this.phone = phone;
            }

            @Override
            public void doOnClick(View v) {
                if (phone != null)
                    sendSMS(phone);
                else {
                    Toast.makeText(ActivityMyStudent.this.getApplicationContext(),
                            ActivityMyStudent.this.getString(R.string.lacktel), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }

        private void sendSMS(String phonenum) {
            Uri smsToUri = Uri.parse("smsto:" + phonenum);
            Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
            // intent.putExtra("sms_body", smsBody);
            startActivity(intent);
        }
    }
}
