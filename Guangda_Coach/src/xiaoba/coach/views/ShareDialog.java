package xiaoba.coach.views;

import com.daoshun.lib.view.OnSingleClickListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;


import xiaoba.coach.CoachApplication;
import xiaoba.coach.R;
import xiaoba.coach.activity.BaseActivity;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ShareDialog extends BaseDialog {
	private Context mContext;
	private ImageView imgCancle;
	private LinearLayout llShareWX;
	private LinearLayout llShareWXF;
	private LinearLayout llShareSin;
	private LinearLayout llShareQQ;
	private LinearLayout llShareZone;
	private BaseActivity mActivity;
	public final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	private String shareUrl;
	public ShareDialog(Context context,BaseActivity activity) {
		super(context,R.style.dialog);
		mContext = context;
		mActivity = activity;
		// TODO Auto-generated constructor stub
	}
	public ShareDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	public ShareDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mContext = context;
	}

	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.share_dialog;
	}
	
	public void setShareUrl(String url)
	{
		this.shareUrl = url;
	}

	@Override
	protected void findViews() {
		// TODO Auto-generated method stub
		imgCancle = (ImageView)findViewById(R.id.img_cancle);
		llShareWX = (LinearLayout)findViewById(R.id.ll_share_to_weixin);
		llShareWXF = (LinearLayout)findViewById(R.id.ll_share_penyouquan);
		llShareQQ = (LinearLayout)findViewById(R.id.ll_share_qq);
		llShareZone = (LinearLayout)findViewById(R.id.ll_share_zone);
		llShareSin = (LinearLayout)findViewById(R.id.ll_share_xinlang);
		
		imgCancle.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
		
		//分享到QQ
		llShareQQ.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(mActivity, "1104653815",
		                "oUgKlUs1Ya79zwca");
		        qqSsoHandler.addToSocialSDK();
		        QQShareContent qqShareContent = new QQShareContent();
		//设置分享文字
		        qqShareContent.setShareContent("小巴学车，只因改变\n加入小巴，月入过万");
		//设置分享title
		        if (!TextUtils.isEmpty(CoachApplication.mApplication.getUserInfo().getRealname()))
		        {
		        qqShareContent.setTitle(CoachApplication.mApplication.getUserInfo().getRealname()+"邀请您加入小巴学车");
		        }else{
		        	qqShareContent.setTitle("邀请您加入小巴学车");
		        }

		//设置分享图片
		        qqShareContent.setShareImage(new UMImage(mActivity, R.drawable.ic_launcher));
		//设置点击分享内容的跳转链接
		        qqShareContent.setTargetUrl(shareUrl);
		        mController.setShareMedia(qqShareContent);
		        mController.postShare(mContext, SHARE_MEDIA.QQ,
		                new SocializeListeners.SnsPostListener() {
		                    @Override
		                    public void onStart() {
//		                        Toast.makeText(mContext, "开始分享.", Toast.LENGTH_SHORT).show();
		                    }
		                    @Override
		                    public void onComplete(SHARE_MEDIA arg0, int arg1,SocializeEntity arg2) {
		                        if (arg1 == 200) {
//		                            Toast.makeText(mContext, "分享成功.", Toast.LENGTH_SHORT).show();
		                        } else {
		                            String eMsg = "";
		                            if (arg1 == -101){
		                                eMsg = "没有授权";
		                            }
//		                            Toast.makeText(mContext, "分享失败[" + arg1 + "] " +
//		                                    eMsg,Toast.LENGTH_SHORT).show();
		                        }
		                    }
		                });
			}
		});
		
		//分享到QQ朋友圈
		llShareZone.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
		        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(mActivity, "1104653815",
		                "oUgKlUs1Ya79zwca");
		        qZoneSsoHandler.addToSocialSDK();

		        QZoneShareContent qzone = new QZoneShareContent();
		//设置分享文字
		        qzone.setShareContent("小巴学车，只因改变\n加入小巴，月入过万");
		//设置点击消息的跳转URL
		        qzone.setTargetUrl(shareUrl);
		//设置分享内容的标题
		        if (!TextUtils.isEmpty(CoachApplication.mApplication.getUserInfo().getRealname()))
		        {
		        	qzone.setTitle(CoachApplication.mApplication.getUserInfo().getRealname()+"邀请您加入小巴学车");
		        }else{
		        	qzone.setTitle("邀请您加入小巴学车");
		        }
		//设置分享图片
		        qzone.setShareImage(new UMImage(mActivity, R.drawable.ic_launcher));
		        mController.setShareMedia(qzone);

		        mController.postShare(mContext, SHARE_MEDIA.QZONE,
		                new SocializeListeners.SnsPostListener() {
		                    @Override
		                    public void onStart() {
//		                        Toast.makeText(mContext, "开始分享.", Toast.LENGTH_SHORT).show();
		                    }
		                    @Override
		                    public void onComplete(SHARE_MEDIA platform, int eCode,SocializeEntity entity) {
		                        if (eCode == 200) {
//		                            Toast.makeText(mContext, "分享成功.", Toast.LENGTH_SHORT).show();
		                        } else {
		                            String eMsg = "";
		                            if (eCode == -101){
		                                eMsg = "没有授权";
		                            }
//		                            Toast.makeText(mContext, "分享失败[" + eCode + "] " +
//		                                    eMsg,Toast.LENGTH_SHORT).show();
		                        }
		                    }
		                });
			}
		});
		
		llShareSin.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				  mController.getConfig().setSsoHandler(new SinaSsoHandler());
			        SinaShareContent sinaShareContent=new SinaShareContent();
			        sinaShareContent.setShareContent("小巴学车，只因改变\n加入小巴，月入过万");
					//设置点击消息的跳转URL
			        sinaShareContent.setTargetUrl(shareUrl);
					//设置分享内容的标题
			        if (!TextUtils.isEmpty(CoachApplication.mApplication.getUserInfo().getRealname()))
			        {
			        	sinaShareContent.setTitle(CoachApplication.mApplication.getUserInfo().getRealname()+"邀请您加入小巴学车");
			        }else{
			        	sinaShareContent.setTitle("邀请您加入小巴学车");
			        }
					//设置分享图片
			        sinaShareContent.setShareImage(new UMImage(mActivity, R.drawable.ic_launcher));
			        mController.setShareMedia(sinaShareContent);
			        mController.directShare(mActivity,SHARE_MEDIA.SINA,new SocializeListeners.SnsPostListener() {

			            @Override
			            public void onStart() {
			            	
			            }

			            @Override
			            public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
	                        if (eCode == 200) {
//	                            Toast.makeText(mContext, "分享成功.", Toast.LENGTH_SHORT).show();
	                        } else {
	                            String eMsg = "";
	                            if (eCode == -101){
	                                eMsg = "没有授权";
	                            }
//	                            Toast.makeText(mContext, "分享失败[" + eCode + "] " +
//	                                    eMsg,Toast.LENGTH_SHORT).show();
	                        }
	                    }
			        });

			}
		});
		
		llShareWX.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				
				// TODO Auto-generated method stub
				UMWXHandler wxHandler = new UMWXHandler(mActivity,"wx6e408d648087cffb","648bdd4b00bcfa025944b56d6176d031");
		        wxHandler.addToSocialSDK();

		        WeiXinShareContent weixinContent = new WeiXinShareContent();
		//设置分享文字
		        weixinContent.setShareContent("小巴学车，只因改变\n加入小巴，月入过万");
		//设置title
		        if (!TextUtils.isEmpty(CoachApplication.mApplication.getUserInfo().getRealname()))
		        {
		        	weixinContent.setTitle(CoachApplication.mApplication.getUserInfo().getRealname()+"邀请您加入小巴学车");
		        }else{
		        	weixinContent.setTitle("邀请您加入小巴学车");
		        }
		//设置分享内容跳转URL
		        weixinContent.setTargetUrl(shareUrl);
		//设置分享图片
		        weixinContent.setShareImage(new UMImage(mActivity, R.drawable.ic_launcher));
		        mController.setShareMedia(weixinContent);

		        mController.postShare(mContext,SHARE_MEDIA.WEIXIN,
		                new SocializeListeners.SnsPostListener() {
		                    @Override
		                    public void onStart() {
//		                        Toast.makeText(mContext, "开始分享.", Toast.LENGTH_SHORT).show();
		                    }
		                    @Override
		                    public void onComplete(SHARE_MEDIA platform, int eCode,SocializeEntity entity) {
		                        if (eCode == 200) {
//		                            Toast.makeText(mContext, "分享成功.", Toast.LENGTH_SHORT).show();
		                        } else {
		                            String eMsg = "";
		                            if (eCode == -101){
		                                eMsg = "没有授权";
		                            }
//		                            Toast.makeText(mContext, "分享失败[" + eCode + "] " +
//		                                    eMsg,Toast.LENGTH_SHORT).show();
		                        }
		                    }
		                });
			}
		});
		
		llShareWXF.setOnClickListener(new OnSingleClickListener() {
			
			@Override
			public void doOnClick(View v) {
				// TODO Auto-generated method stub
				UMWXHandler wxCircleHandler = new UMWXHandler(mActivity,"wx6e408d648087cffb","648bdd4b00bcfa025944b56d6176d031");
		        wxCircleHandler.setToCircle(true);
		        wxCircleHandler.addToSocialSDK();

		        CircleShareContent circleMedia = new CircleShareContent();
		        circleMedia.setShareContent("小巴学车，只因改变\n加入小巴，月入过万");
		//设置朋友圈title
		        if (!TextUtils.isEmpty(CoachApplication.mApplication.getUserInfo().getRealname()))
		        {
		        	circleMedia.setTitle(CoachApplication.mApplication.getUserInfo().getRealname()+"邀请您加入小巴学车");
		        }else{
		        	circleMedia.setTitle("邀请您加入小巴学车");
		        }
		        circleMedia.setShareImage(new UMImage(mActivity, R.drawable.ic_launcher));
		        circleMedia.setTargetUrl(shareUrl);
		        mController.setShareMedia(circleMedia);

		        mController.postShare(mContext,SHARE_MEDIA.WEIXIN_CIRCLE,
		                new SocializeListeners.SnsPostListener() {
		                    @Override
		                    public void onStart() {
//		                        Toast.makeText(mContext, "开始分享.", Toast.LENGTH_SHORT).show();
		                    }
		                    @Override
		                    public void onComplete(SHARE_MEDIA platform, int eCode,SocializeEntity entity) {
		                        if (eCode == 200) {
//		                            Toast.makeText(mContext, "分享成功.", Toast.LENGTH_SHORT).show();
		                        } else {
		                            String eMsg = "";
		                            if (eCode == -101){
		                                eMsg = "没有授权";
		                            }
//		                            Toast.makeText(mContext, "分享失败[" + eCode + "] " +
//		                                    eMsg,Toast.LENGTH_SHORT).show();
		                        }
		                    }
		                });
			}
		});
	}

	@Override
	protected void setWindowParam() {
		// TODO Auto-generated method stub
		setWindowParams(-1, -2, Gravity.BOTTOM);
		setCanceledOnTouchOutside(true);
	}
}
