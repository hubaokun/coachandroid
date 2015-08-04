package xiaoba.coach.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.grobas.view.PolygonImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.R;
import xiaoba.coach.common.IntentsParamNames;
import xiaoba.coach.common.Settings;
import xiaoba.coach.module.BaseParam;
import xiaoba.coach.module.Coachscore;
import xiaoba.coach.module.HistoryTaskInfo;
import xiaoba.coach.module.StudentInfo;
import xiaoba.coach.module.HistoryTaskInfo.Studentscore;
import xiaoba.coach.net.result.BaseResult;
import xiaoba.coach.net.result.DoneOrderListResult;
import xiaoba.coach.utils.CommonUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daoshun.lib.communication.http.JSONAccessor;
import com.daoshun.lib.listview.PullToRefreshBase;
import com.daoshun.lib.listview.PullToRefreshBase.Mode;
import com.daoshun.lib.listview.PullToRefreshListView;
import com.daoshun.lib.view.OnSingleClickListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/*
 * display the orders which are done
 */
@EActivity(R.layout.activity_history_order)
public class HistoryOrderActivity extends BaseActivity {
	@ViewById(R.id.pulltorefresh)
	PullToRefreshListView mListView;
	@ViewById(R.id.title)
	TextView mTitle;
	@ViewById(R.id.title_back_img)
	ImageView mBackImg;
	@ViewById(R.id.title_back)
	FrameLayout mBack;
	@ViewById(R.id.his_order_no_data)
	LinearLayout mNoData;

	int mPageNum;
	int mShowHidePosition = -1; // mark the item show the content hided
	int mCommentPosition = -1; // mark the item which call the comment dialog
	boolean blockComment; // lock the operation comment when the net result is transmitting
	List<HistoryTaskInfo> list = new ArrayList<HistoryTaskInfo>();
	HistoryOrderAdapter mAdapter = new HistoryOrderAdapter();

	final static int COMMENT_REQUEST_CODE = 1001;

	@AfterViews
	void init() {
		initView();
		addListener();
	}

	private void initView() {
		mBackImg.setImageResource(R.drawable.back_arrow);
		mTitle.setText(getString(R.string.history_order));
		mTitle.setTextColor(Color.parseColor("#2c2c2c"));

		mListView.setAdapter(mAdapter);
	}

	private void addListener() {
		mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				mPageNum = 0;
				doRefresh();

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				mPageNum++;
				doRefresh();
			}
		});
	}

	/*
	 * get data by new request
	 */
	protected void doRefresh() {
		new GetHistoryOrderTask().execute();
	}

	@Click(R.id.title_back)
	void returnLastPage() {
		HistoryOrderActivity.this.finish();
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		mListView.setRefreshing();
	}

	/*
	 * get list data by net request
	 */
	private class GetHistoryOrderTask extends AsyncTask<Void, Void, DoneOrderListResult> {
		JSONAccessor accessor = new JSONAccessor(HistoryOrderActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected DoneOrderListResult doInBackground(Void... arg0) {
			accessor.enableJsonLog(true);
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "GetHisTask");
			param.put("coachid", mApplication.getUserInfo().getCoachid());
			param.put("pagenum", mPageNum);
			return accessor.execute(Settings.TASK_URL, param, DoneOrderListResult.class);
		}

		@Override
		protected void onPostExecute(DoneOrderListResult result) {
			super.onPostExecute(result);
			if (mListView != null)
				mListView.onRefreshComplete();
			if (result != null) {
				if (result.getCode() == 1) {

					if (result.getTasklist() != null && result.getTasklist().size() > 0) {
						if (mPageNum == 0)
							// if put this operation before request the list ui will change very visible
							list.clear();
						list.addAll(result.getTasklist());
						mAdapter.notifyDataSetChanged();
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
						CommonUtils.showToast(HistoryOrderActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(HistoryOrderActivity.this);
					}
				}

			} else {
				CommonUtils.showToast(HistoryOrderActivity.this.getApplicationContext(), getString(R.string.net_error));
			}

			if (list.size() == 0) {
				mNoData.setVisibility(View.VISIBLE);
			} else {
				mNoData.setVisibility(View.INVISIBLE);
			}
		}

	}

	private class HistoryOrderAdapter extends BaseAdapter {
		DisplayImageOptions options;
		ImageSize mImageSize;

		public HistoryOrderAdapter() {
			// 显示图片的配置
			options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

			mImageSize = new ImageSize(150, 150);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			convertView = HistoryOrderActivity.this.getLayoutInflater().inflate(R.layout.item_history_job_order, null);
			LinearLayout mainPart = (LinearLayout) convertView.findViewById(R.id.jo_main_part);
			RelativeLayout hidePart = (RelativeLayout) convertView.findViewById(R.id.hide_content);
			ImageView arrow = (ImageView) convertView.findViewById(R.id.jo_item_arrow);
			mainPart.setOnClickListener(new ShowHideListener(hidePart, arrow, position));

			TextView location = (TextView) convertView.findViewById(R.id.loc_detail);
			TextView orderDate = (TextView) convertView.findViewById(R.id.order_date);
			TextView startTime = (TextView) convertView.findViewById(R.id.start_time);
			TextView endTime = (TextView) convertView.findViewById(R.id.end_time);
			TextView stuName = (TextView) convertView.findViewById(R.id.jo_stuname);
			TextView stuTel = (TextView) convertView.findViewById(R.id.jo_stutel);
			TextView stuCard = (TextView) convertView.findViewById(R.id.jo_stucard);
			TextView tvMoney = (TextView)convertView.findViewById(R.id.tv_money);
			Button btn = (Button) convertView.findViewById(R.id.jo_stubtn);
			LinearLayout tousu = (LinearLayout) convertView.findViewById(R.id.jo_tousu);
			LinearLayout contact = (LinearLayout) convertView.findViewById(R.id.jo_contact);
			TextView orderSuc = (TextView) convertView.findViewById(R.id.del_order);
			LinearLayout belowOC = (LinearLayout) convertView.findViewById(R.id.below_del_order);
			PolygonImageView potrait = (PolygonImageView) convertView.findViewById(R.id.potrait);
			ImageView bigPortrait = (ImageView) convertView.findViewById(R.id.big_portrait);
			TextView stuComTitle = (TextView) convertView.findViewById(R.id.stu_comment_title);
			TextView stuComContent = (TextView) convertView.findViewById(R.id.stu_comment_content);
			RatingBar stuRatingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
			LinearLayout coachComment = (LinearLayout) convertView.findViewById(R.id.coach_comment);
			RatingBar coachRatingBar = (RatingBar) convertView.findViewById(R.id.coach_ratingBar);
			ImageView imgPayType = (ImageView)convertView.findViewById(R.id.img_paytype);
			HistoryTaskInfo info = list.get(position); // item data
			LinearLayout stuHidePart = (LinearLayout) convertView.findViewById(R.id.stu_hide_part);
			

			/*
			 * judge whether show the hide part
			 */
			if (position == mShowHidePosition) {
				hidePart.setVisibility(View.VISIBLE);
				arrow.setImageResource(R.drawable.arrow_blue);
			} else {
				if (hidePart.getVisibility() == View.VISIBLE)
					hidePart.setVisibility(View.GONE);
				arrow.setImageResource(R.drawable.arrow_grey);
			}

			if (info == null)
				return convertView;

			/*
			 * student's score
			 */
			if (info.getStudentscore() != null) {
				stuComTitle.setText(HistoryOrderActivity.this.getString(R.string.student_comment));
				stuComContent.setVisibility(View.VISIBLE);
				stuRatingBar.setVisibility(View.VISIBLE);
				Studentscore sscore = info.getStudentscore();
				if (sscore.getContent() != null) {
					stuComContent.setText(sscore.getContent());
				} else {
					stuComContent.setText("");
				}
				stuRatingBar.setRating(sscore.getScore());
				stuHidePart.setOnClickListener(new ScListener(info.getStudentscore()));
			} else {
				stuComTitle.setText(HistoryOrderActivity.this.getString(R.string.student_uncomment));
				stuComContent.setVisibility(View.GONE);
				stuRatingBar.setVisibility(View.GONE);
			}
			/*
			 * coach's score
			 */
			if (info.getCoachscore() != null) {
				coachComment.setVisibility(View.VISIBLE);
				btn.setVisibility(View.INVISIBLE);

				coachRatingBar.setRating(info.getCoachscore().getScore());
				coachComment.setOnClickListener(new CcListener(info.getCoachscore()));
			} else {
				coachComment.setVisibility(View.INVISIBLE);
				btn.setVisibility(View.VISIBLE);

				btn.setOnClickListener(new CommentListener(position));
			}

			/*
			 * contact student
			 */
			
			if (info.getTotal() != null) {
				if (info.getTotal().contains(".")) {
					tvMoney.setText(info.getTotal().substring(0, info.getTotal().indexOf(".")) + "元");
				}
			} else {
				tvMoney.setText("");
			}
			
			
			switch (info.getPaytype()) {
			case Settings.MONEY:
				imgPayType.setImageResource(R.drawable.money);
				break;
			case Settings.COIN:
				imgPayType.setImageResource(R.drawable.coin);
				break;
			case Settings.COUPON:
				imgPayType.setImageResource(R.drawable.ticket);
				break;
			default:
				break;
			}
			
			if (info.getStudentinfo() != null && info.getStudentinfo().getPhone() != null) {
				contact.setOnClickListener(new ContactListener(info.getStudentinfo().getPhone()));
				tousu.setOnClickListener(new MessageListener(info.getStudentinfo().getPhone()));

			} else {
				contact.setOnClickListener(new ContactListener(null));
				tousu.setOnClickListener(new MessageListener(null));
			}

			/*
			 * set address
			 */
			if (info.getDetail() != null)
				location.setText(info.getDetail());
			else
				location.setText("");
			/*
			 * set start time
			 */
			if (info.getStart_time() != null) {
				startTime.setText(getTime(info.getStart_time()));
				orderDate.setText(getDate(info.getStart_time())); // set date
			} else {
				startTime.setText("");
				orderDate.setText("");
			}
			/*
			 * set end time
			 */
			if (info.getEnd_time() != null) {
				endTime.setText(getTime(info.getEnd_time()));
			} else {
				endTime.setText("");
			}
			/*
			 * check coach state (change time's font)
			 */
			switch (info.getState()) {
			case 0:
				startTime.setTextColor(Color.parseColor("#1c1c1c"));
				endTime.setTextColor(Color.parseColor("#1c1c1c"));
				break;

			case 1:
				startTime.setTextColor(Color.parseColor("#e0483e"));
				endTime.setTextColor(Color.parseColor("#e0483e"));
				break;

			case 2:
				startTime.setTextColor(Color.parseColor("#e0483e"));
				endTime.setTextColor(Color.parseColor("#e0483e"));
				break;

			case 3:
				startTime.setTextColor(Color.parseColor("#1c1c1c"));
				endTime.setTextColor(Color.parseColor("#1c1c1c"));
				break;

			default:
				break;
			}

			/*
			 * set student infomation
			 */
			potrait.setImageResource(R.drawable.portrait_test);
			bigPortrait.setBackgroundResource(0);
			bigPortrait.setImageResource(R.drawable.portrait_test);
			if (info.getStudentinfo() != null) {
				StudentInfo stuInfo = info.getStudentinfo();

				if (stuInfo.getCoachstate() == 1) {
					ImageLoader.getInstance().loadImage(stuInfo.getAvatarurl(), mImageSize, options, new MImageLoaderListener(potrait, bigPortrait));
				} else {
					potrait.setImageResource(R.drawable.portrait_untest);
					bigPortrait.setImageResource(R.drawable.portrait_untest);
				}
				/*
				 * set student name
				 */
				if (stuInfo.getRealname() != null)
					stuName.setText("学员姓名  " + stuInfo.getRealname());
				else
					stuName.setText("学员姓名  未设置");
				/*
				 * set student telephone
				 */
				if (stuInfo.getPhone() != null)
					stuTel.setText("联系电话  " + stuInfo.getPhone());
				else
					stuTel.setText("联系电话  未设置");
				/*
				 * set student card number
				 */
				if (stuInfo.getStudent_cardnum() != null)
					stuCard.setText("学员证号  " + stuInfo.getStudent_cardnum());
				else
					stuCard.setText("学员证号  未设置");
			} else {
				stuName.setText("");
				stuTel.setText("");
				stuCard.setText("");
			}

			return convertView;
		}

		/*
		 * load portrait
		 */
		class MImageLoaderListener extends SimpleImageLoadingListener {
			PolygonImageView portrait;
			ImageView bigPortrait;

			public MImageLoaderListener(PolygonImageView portrait, ImageView bigPortrait) {
				this.portrait = portrait;
				this.bigPortrait = bigPortrait;
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				super.onLoadingComplete(imageUri, view, loadedImage);
				if (loadedImage != null) {
					portrait.setImageBitmap(loadedImage);
					bigPortrait.setBackgroundDrawable(new BitmapDrawable(loadedImage));
					bigPortrait.setImageResource(0);
				}
			}

		}

		/*
		 * go to send msg
		 */
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
					Toast.makeText(HistoryOrderActivity.this.getApplicationContext(), HistoryOrderActivity.this.getString(R.string.lacktel), Toast.LENGTH_SHORT).show();
				}
			}
		}

		/*
		 * show the hide view which display the student info (name ,tel , cardnum)
		 */
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

				mAdapter.notifyDataSetChanged();
			}
		}

		/*
		 * add comment btn listener
		 */
		class CommentListener extends OnSingleClickListener {

			int position;

			public CommentListener(int position) {
				this.position = position;
			}

			@Override
			public void doOnClick(View v) {
				if (blockComment) {

				} else {
					mCommentPosition = position;
					startActivityForResult(new Intent(HistoryOrderActivity.this, CommentActivity_.class), COMMENT_REQUEST_CODE);
				}

			}
		}

		/*
		 * coach comment listener
		 */
		class CcListener extends OnSingleClickListener {

			Coachscore score;

			public CcListener(Coachscore score) {
				this.score = score;
			}

			@Override
			public void doOnClick(View v) {
				Intent intent = new Intent(HistoryOrderActivity.this, CommentDetailActivity_.class);
				intent.putExtra(IntentsParamNames.RATINAVER, score.getScore());
				intent.putExtra(IntentsParamNames.RATINGAT, score.getScore1());
				intent.putExtra(IntentsParamNames.RATINGQU, score.getScore2());
				intent.putExtra(IntentsParamNames.RATINGLO, score.getScore3());
				intent.putExtra(IntentsParamNames.COMMENT_CONTENT, score.getContent());
				startActivity(intent);
			}

		}
		
		
		/*
		 * stu
		 */
		class ScListener extends OnSingleClickListener {
			
			Studentscore score;
			
			public ScListener(Studentscore score) {
				this.score = score;
			}
			
			@Override
			public void doOnClick(View v) {
				Intent intent = new Intent(HistoryOrderActivity.this, CommentDetailActivity_.class);
				intent.putExtra(IntentsParamNames.RATINAVER, score.getScore());
				intent.putExtra(IntentsParamNames.RATINGAT, score.getScore1());
				intent.putExtra(IntentsParamNames.RATINGQU, score.getScore2());
				intent.putExtra(IntentsParamNames.RATINGLO, score.getScore3());
				intent.putExtra(IntentsParamNames.COMMENT_CONTENT, score.getContent());
				intent.putExtra("studentrate", "studentrate");
				startActivity(intent);
			}
			
		}

		/*
		 * contact click listener
		 */
		class ContactListener extends OnSingleClickListener {

			String tel;

			public ContactListener(String tel) {
				this.tel = tel;
			}

			@Override
			public void doOnClick(View v) {
				if (tel == null) {
					Toast.makeText(HistoryOrderActivity.this.getApplicationContext(), HistoryOrderActivity.this.getString(R.string.lacktel), Toast.LENGTH_SHORT).show();
					return;
				}
				try {
					startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel)));
				} catch (Exception e) {
					CommonUtils.showToast(HistoryOrderActivity.this.getApplicationContext(), "This telephone number is not well formated.");
				}

			}
		}

	}

	private void sendSMS(String phonenum) {
		Uri smsToUri = Uri.parse("smsto:" + phonenum);
		Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
		// intent.putExtra("sms_body", smsBody);
		startActivity(intent);
	}

	/*
	 * get time (HH:mm:ss)
	 */
	String getTime(String time) {
		return time.substring(time.lastIndexOf(" ") + 1, time.lastIndexOf(":"));
	}

	/*
	 * get date (yyyy:MM:dd)
	 */
	String getDate(String time) {
		return time.substring(0, time.indexOf(" "));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == COMMENT_REQUEST_CODE) {
			if (data != null) {
				Coachscore score = new Coachscore();
				score.setScore1(data.getIntExtra(IntentsParamNames.RATINGAT, 0));
				score.setScore2(data.getIntExtra(IntentsParamNames.RATINGQU, 0));
				score.setScore3(data.getIntExtra(IntentsParamNames.RATINGLO, 0));
				score.setScore((data.getIntExtra(IntentsParamNames.RATINGAT, 0) + data.getIntExtra(IntentsParamNames.RATINGQU, 0) + data.getIntExtra(IntentsParamNames.RATINGLO, 0)) / 3);
				score.setContent(data.getStringExtra(IntentsParamNames.COMMENT_CONTENT));

				new EvaluateTask(score).execute();
			} else {

			}

		}
	}

	/*
	 * submit the coach's evaluation to his/her student
	 */
	private class EvaluateTask extends AsyncTask<Void, Void, BaseResult> {

		JSONAccessor accessor = new JSONAccessor(HistoryOrderActivity.this.getApplicationContext(), JSONAccessor.METHOD_POST);

		Coachscore score;

		public EvaluateTask(Coachscore score) {
			this.score = score;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mLoadingDialog != null)
				mLoadingDialog.show();

			blockComment = true;
		}

		@Override
		protected BaseResult doInBackground(Void... arg0) {
			HashMap<String, Object> param = new BaseParam();
			param.put("action", "EvaluationTask");
			param.put("userid", mApplication.getUserInfo().getCoachid());
			param.put("type", "1"); // 1: coach to student
			param.put("orderid", list.get(mCommentPosition).getOrderid());
			param.put("score1", score.getScore1()); //
			param.put("score2", score.getScore2()); //
			param.put("score3", score.getScore3()); //
			param.put("content", score.getContent()); //
			return accessor.execute(Settings.TASK_URL, param, BaseResult.class);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			super.onPostExecute(result);
			if (mLoadingDialog != null && mLoadingDialog.isShowing())
				mLoadingDialog.dismiss();
			if (result != null) {
				if (result.getCode() == 1) {
					list.get(mCommentPosition).setCoachscore(score);
					mAdapter.notifyDataSetChanged();
					CommonUtils.showToast(HistoryOrderActivity.this.getApplicationContext(), "评价成功");
				} else {
					if (result.getMessage() != null)
						CommonUtils.showToast(HistoryOrderActivity.this.getApplicationContext(), result.getMessage());
					if (result.getCode() == 95) {
						CommonUtils.gotoLogin(HistoryOrderActivity.this);
					}
				}

			} else {
				CommonUtils.showToast(HistoryOrderActivity.this.getApplicationContext(), getString(R.string.net_error));
			}

			blockComment = false;
		}

	}

}
