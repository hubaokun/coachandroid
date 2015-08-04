package xiaoba.coach.activity;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.R;
import xiaoba.coach.common.IntentsParamNames;

import android.widget.RatingBar;
import android.widget.TextView;

@Fullscreen
@EActivity(R.layout.activity_comment_detail)
public class CommentDetailActivity extends BaseActivity {

	private float average;
	private float attitude;
	private float quality;
	private float look;
	private String content;

	@ViewById(R.id.ratingBar_attitude)
	RatingBar mAttitude;
	@ViewById(R.id.ratingBar_quality)
	RatingBar mQuality;
	@ViewById(R.id.ratingBar_look)
	RatingBar mLook;

	@ViewById(R.id.attitude)
	TextView mAttitudeTv;
	@ViewById(R.id.quality)
	TextView mQualityTv;
	@ViewById(R.id.look)
	TextView mLookTv;
	@ViewById(R.id.average)
	TextView mAverageTv;
	@ViewById(R.id.rating_title)
	TextView mTitle;

	@ViewById(R.id.detail_content)
	TextView mContent;

	@AfterViews
	void init() {
		getIntentData();

		initView();
	}

	private void initView() {
		mAverageTv.setText(CommentDetailActivity.this.getString(R.string.averate_rate) + average + CommentDetailActivity.this.getString(R.string.rate_unit));
		mAttitudeTv.setText(CommentDetailActivity.this.getString(R.string.comment_atti) + attitude + CommentDetailActivity.this.getString(R.string.rate_unit));
		mQualityTv.setText(CommentDetailActivity.this.getString(R.string.comment_quality) + quality + CommentDetailActivity.this.getString(R.string.rate_unit));
		mLookTv.setText(CommentDetailActivity.this.getString(R.string.comment_look) + look + CommentDetailActivity.this.getString(R.string.rate_unit));
		mContent.setText(content + "");

		mAttitude.setRating(attitude);
		mQuality.setRating(quality);
		mLook.setRating(look);
	}

	private void getIntentData() {
		average = getIntent().getFloatExtra(IntentsParamNames.RATINAVER, 0f);
		attitude = getIntent().getFloatExtra(IntentsParamNames.RATINGAT, 0f);
		quality = getIntent().getFloatExtra(IntentsParamNames.RATINGQU, 0f);
		look = getIntent().getFloatExtra(IntentsParamNames.RATINGLO, 0f);
		content = getIntent().getStringExtra(IntentsParamNames.COMMENT_CONTENT);
		
		if (getIntent().getStringExtra("studentrate") != null)
			mTitle.setText("学员评价详情");
	}

	@Click(R.id.comment_root_view)
	void exitDetail() {
		CommentDetailActivity.this.finish();
	}
}
