package xiaoba.coach.views;

import xiaoba.coach.R;
import xiaoba.coach.activity.BaseActivity;
import xiaoba.coach.utils.ImageLoader;
import net.grobas.view.PolygonImageView;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StudentDetailView {
	private Context mcontext;
	private RelativeLayout /* rlbackground, */rllocation;
	private Display display;
	private View detailView;
	private ImageView imgCancel;
	private PolygonImageView imgStudentAvater;
	private TextView tvStudentName, tvStudentContact, tvStudentNumber;
	private PopupWindow popStudentDetail;
	private int width, height;
	private String phoneNum;
	private RelativeLayout rlMobil;
	private ImageLoader imgLoader;
	private RatingBar mRatingbar;
	private TextView tvRank;

	public StudentDetailView(Context context, RelativeLayout rlLocation, Display dis, BaseActivity activity) {
		this.mcontext = context;
		/* this.rlbackground = rlBackground; */
		this.rllocation = rlLocation;
		this.display = dis;
		detailView = activity.getLayoutInflater().inflate(R.layout.compaint_student_detail, null);
		imgLoader = new ImageLoader(mcontext, R.drawable.portrait_test);
		initView();
		init();
		setListener();
	}

	public StudentDetailView(Context context, RelativeLayout rlLocation, Display dis, FragmentActivity activity) {
		this.mcontext = context;
		/* this.rlbackground = rlBackground; */
		this.rllocation = rlLocation;
		this.display = dis;
		detailView = activity.getLayoutInflater().inflate(R.layout.compaint_student_detail, null);
		imgLoader = new ImageLoader(mcontext, R.drawable.portrait_test);
		initView();
		init();
		setListener();
	}

	private void initView() {
		imgCancel = (ImageView) detailView.findViewById(R.id.img_cancel);
		tvStudentNumber = (TextView) detailView.findViewById(R.id.tv_student_number);
		tvStudentContact = (TextView) detailView.findViewById(R.id.tv_student_contact);
		tvStudentName = (TextView) detailView.findViewById(R.id.tv_student_name);
		rlMobil = (RelativeLayout) detailView.findViewById(R.id.rl_mobil);
		imgStudentAvater = (PolygonImageView) detailView.findViewById(R.id.student_detail_avater);
		mRatingbar = (RatingBar) detailView.findViewById(R.id.ratingBar);
		tvRank = (TextView) detailView.findViewById(R.id.tv_rank);
	}

	private void init() {
		width = display.getWidth();
		height = display.getHeight();
		popStudentDetail = new PopupWindow(detailView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
		popStudentDetail.setWidth((int) (width * 1));
		popStudentDetail.setHeight((int) (height * 1));
		popStudentDetail.setFocusable(true);
		popStudentDetail.setOutsideTouchable(false);
		popStudentDetail.setBackgroundDrawable(new BitmapDrawable());
		popStudentDetail.setOnDismissListener(onDismissListener);
	}

	private PopupWindow.OnDismissListener onDismissListener = new PopupWindow.OnDismissListener() {

		@Override
		public void onDismiss() {
			/* rlbackground.setVisibility(View.GONE); */
		}
	};

	public void setShowDialog(String phonenumber, String studentName, String studentNumber, String studentAvater, float rank) {
		phoneNum = phonenumber;
		tvStudentContact.setText(phoneNum);
		tvStudentName.setText(studentName);
		tvStudentNumber.setText(studentNumber);
		imgLoader.DisplayImage(studentAvater, imgStudentAvater);
		mRatingbar.setRating(rank);
		tvRank.setText(String.valueOf(rank));
		/* rlbackground.setVisibility(View.VISIBLE); */
		popStudentDetail.setContentView(detailView);
		popStudentDetail.showAtLocation(rllocation, Gravity.CENTER, 0, 0);
		popStudentDetail.update();
	}

	private void setListener() {
		imgCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				popStudentDetail.dismiss();
			}
		});

		rlMobil.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
				mcontext.startActivity(intent);
			}
		});
	}
}
