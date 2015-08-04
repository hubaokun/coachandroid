package xiaoba.coach.views;

import xiaoba.coach.R;
import xiaoba.coach.activity.BaseActivity;

import com.daoshun.lib.view.OnSingleClickListener;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NoticeDetailView {
	private Context mcontext;
	private RelativeLayout /* rlbackground, */rllocation;
	private Display display;
	private View detailView;
	private TextView tvNoticeDetail;
	private RelativeLayout rlNoticeDetail;
	private PopupWindow popNoticeDetail;
	private int width, height;

	public NoticeDetailView(Context context, RelativeLayout rlLocation, Display dis, BaseActivity activity) {
		this.mcontext = context;
		/* this.rlbackground = rlBackground; */
		this.rllocation = rlLocation;
		this.display = dis;
		detailView = activity.getLayoutInflater().inflate(R.layout.show_notice_detail, null);
		initView();
		init();
		setListener();
	}

	private void initView() {
		tvNoticeDetail = (TextView) detailView.findViewById(R.id.tv_notice_detail);
		rlNoticeDetail = (RelativeLayout) detailView.findViewById(R.id.rl_notice_detail);
	}

	private void init() {
		width = display.getWidth();
		height = display.getHeight();
		popNoticeDetail = new PopupWindow(detailView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
		popNoticeDetail.setWidth((int) (width * 1));
		popNoticeDetail.setHeight((int) (height * 1));
		popNoticeDetail.setFocusable(true);
		popNoticeDetail.setOutsideTouchable(false);
		popNoticeDetail.setBackgroundDrawable(new BitmapDrawable());
	}

	private void setListener() {
		rlNoticeDetail.setOnClickListener(new OnSingleClickListener() {

			@Override
			public void doOnClick(View v) {
				popNoticeDetail.dismiss();
			}
		});
	}

	public void setNoticeContent(String content) {
		tvNoticeDetail.setText(content);
		popNoticeDetail.setContentView(detailView);
		popNoticeDetail.showAtLocation(rllocation, Gravity.CENTER, 0, 0);
		popNoticeDetail.update();
	}
}
