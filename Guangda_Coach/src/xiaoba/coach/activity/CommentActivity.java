package xiaoba.coach.activity;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NoTitle;
import org.androidannotations.annotations.ViewById;

import xiaoba.coach.R;
import xiaoba.coach.common.IntentsParamNames;

import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

@EActivity(R.layout.activity_comment)
@NoTitle
public class CommentActivity extends BaseActivity {
	@ViewById(R.id.ratingBar_attitude)
	RatingBar mAttitudeRate;
	@ViewById(R.id.ratingBar_quality)
	RatingBar mQualityRate;
	@ViewById(R.id.ratingBar_look)
	RatingBar mLookRate;

	@ViewById(R.id.tv_attitude)
	TextView mAttitudeTv;
	@ViewById(R.id.tv_quality)
	TextView mQualityTv;
	@ViewById(R.id.tv_look)
	TextView mLookTv;

	@ViewById(R.id.comment_content)
	EditText mContent;

	@ViewById(R.id.cancel)
	ImageView mCancel;

	@ViewById(R.id.confirm)
	ImageView mConfirm;

	boolean mHasRated;
	int mOrderid;

	@AfterViews
	void init() {
		mOrderid = getIntent().getIntExtra(IntentsParamNames.ORDERID, -1);
		addListener();
	}

	private void addListener() {
		mAttitudeRate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar rb, float arg1, boolean arg2) {
				if (rb.getRating() == 0)
					mAttitudeTv.setText(CommentActivity.this.getString(R.string.comment_atti));
				else {
					mAttitudeTv.setText(CommentActivity.this.getString(R.string.comment_atti) + " " + (int) rb.getRating() + CommentActivity.this.getString(R.string.rate_unit));
				}

				checkRate();
			}
		});

		mQualityRate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar rb, float arg1, boolean arg2) {
				if (rb.getRating() == 0)
					mQualityTv.setText(CommentActivity.this.getString(R.string.comment_quality));
				else {
					mQualityTv.setText(CommentActivity.this.getString(R.string.comment_quality) + " " + (int) rb.getRating() + CommentActivity.this.getString(R.string.rate_unit));
				}

				checkRate();
			}
		});

		mLookRate.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar rb, float arg1, boolean arg2) {
				if (rb.getRating() == 0)
					mLookTv.setText(CommentActivity.this.getString(R.string.comment_look));
				else {
					mLookTv.setText(CommentActivity.this.getString(R.string.comment_look) + " " + (int) rb.getRating() + CommentActivity.this.getString(R.string.rate_unit));
				}

				checkRate();
			}
		});

	}

	void checkRate() {
		if (!mHasRated) {
			mHasRated = true;
			mConfirm.setImageResource(R.drawable.comment_confirm_white);
			mConfirm.setClickable(true);
		}
	}

	@Click(R.id.cancel)
	void exitComment() {
		CommentActivity.this.finish();
	}

	@Click(R.id.confirm)
	void commitScore() {
		Intent intent = new Intent();
		intent.putExtra(IntentsParamNames.RATINGAT, (int) mAttitudeRate.getRating());
		intent.putExtra(IntentsParamNames.RATINGQU, (int) mQualityRate.getRating());
		intent.putExtra(IntentsParamNames.RATINGLO, (int) mLookRate.getRating());
		intent.putExtra(IntentsParamNames.COMMENT_CONTENT, mContent.getText().toString());
		if (mOrderid != -1)
			intent.putExtra(IntentsParamNames.ORDERID, mOrderid);
		setResult(0, intent);
		CommentActivity.this.finish();
	}
}
