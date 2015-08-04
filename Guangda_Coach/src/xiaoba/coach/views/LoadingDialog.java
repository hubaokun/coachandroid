/**
 * 
 */
package xiaoba.coach.views;

import xiaoba.coach.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class LoadingDialog extends Dialog {
	private Context mContext;
	private boolean mCancelable;
	private ImageView mImageView;
	private RotateAnimation mRotateThreeAnim;// 第一步：转3圈动画
	private AnimationSet mReduceAnimSet;// 第二步：边转一圈，边缩小动画
	private AnimationSet mAmplyAnimSet;

	public LoadingDialog(Context context) {
		super(context, R.style.dialog);
		mContext = context;
		// 初始化所有动画对象
		initAnims();
	}

	public LoadingDialog(Context context, boolean cancelable) {
		super(context, R.style.dialog);
		mContext = context;
		mCancelable = cancelable;
		// 初始化所有动画对象
		initAnims();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading_dialog_layout);
		this.setCanceledOnTouchOutside(false);
		this.setCancelable(mCancelable);
		mImageView = (ImageView) findViewById(R.id.loading_image);
	}

	@Override
	public void show() {
		super.show();
		// 需要调用系统的super.show()来调用onCreate来实例化view
		if (mImageView != null) {
			initRotateThreeAnim();
		}
	}

	/**
	 * 初始化所有动画对象
	 */
	private void initAnims() {
		// 第一步：转3圈动画
		mRotateThreeAnim = new RotateAnimation(0.0f, 1080.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateThreeAnim.setDuration(1500);
		mRotateThreeAnim.setFillAfter(true);

		// 第二步：边转一圈，边缩小动画
		mReduceAnimSet = new AnimationSet(true);
		RotateAnimation rotateOneAnim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateOneAnim.setDuration(500);
		rotateOneAnim.setFillAfter(true);
		mReduceAnimSet.addAnimation(rotateOneAnim);

		ScaleAnimation reduceAnim = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		reduceAnim.setDuration(500);
		reduceAnim.setFillAfter(true);
		mReduceAnimSet.addAnimation(reduceAnim);

		mAmplyAnimSet = new AnimationSet(true);
		mAmplyAnimSet.addAnimation(rotateOneAnim);

		ScaleAnimation amplyAnim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		amplyAnim.setDuration(500);
		amplyAnim.setFillAfter(true);
		mAmplyAnimSet.addAnimation(amplyAnim);

	}

	private void initRotateThreeAnim() {
		// 第一步：转3圈
		mImageView.startAnimation(mRotateThreeAnim);
		mRotateThreeAnim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				// 第二步：边转一圈，边缩小
				initRotateReduce();

			}
		});
		// 然后从第一步开始重复

	}

	private void initRotateReduce() {
		// 第二步：边转一圈，边缩小
		mImageView.startAnimation(mReduceAnimSet);
		mReduceAnimSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				// 第三步：然后边转一圈，边放大
				initRotateAmply();

			}
		});

	}

	private void initRotateAmply() {
		// 第三步：然后边转一圈，边放大
		mImageView.startAnimation(mAmplyAnimSet);
		mAmplyAnimSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				// 重头开始第一步
				initRotateThreeAnim();
			}
		});
	}

	@Override
	public void dismiss() {
		super.dismiss();
		if (mImageView != null)
			mImageView.clearAnimation();
	}

}
