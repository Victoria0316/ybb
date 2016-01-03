package com.bluemobi.ybb.app;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.bluemobi.ybb.activity.HomeActivity;
import com.bluemobi.ybb.base.BaseActivity;
import com.bluemobi.ybb.network.request.CheckVersionRequest;
import com.bluemobi.ybb.network.response.CheckVersionResponse;
import com.bluemobi.ybb.service.UpdateService;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.YbbAlertDialog;


public class UpdateChecker {
	private static BaseActivity mActivity;
	private static int localVersion = 1;

	private UpdateChecker(BaseActivity activity) {
		mActivity = activity;
		try {
			localVersion = mActivity.getPackageManager().getPackageInfo(
					mActivity.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

	}

	private static UpdateChecker instance;

	public static UpdateChecker getInstance(BaseActivity activity) {
		if (instance == null) {
			instance = new UpdateChecker(activity);
		}
		mActivity = activity;
		return instance;
	}

	/**
	 * 是否提示检测中
	 * 
	 * @param showLoading
	 */
	public void check(final boolean showLoading) {
		CheckVersionRequest request = new CheckVersionRequest(
				new Response.Listener<CheckVersionResponse>() {

					@Override
					public void onResponse(final CheckVersionResponse response) {
						if (showLoading) {
							Utils.closeDialog();
						}
						if (response != null && response.getStatus() == 0) {
							float serverVersion = Float.parseFloat(response.getData()
									.getVcode());
							if (serverVersion > localVersion) {
								final boolean force =  response.getData().getForceUpdate();
								String negative = !force? "取消":"退出";
								final YbbAlertDialog dialog = new YbbAlertDialog(
										mActivity);
								dialog.setTitle("更新提示")
										.setMessageFromHtml(
												response.getData()
														.getContent())
										.setNegativeButtonClickListener(negative,
												new View.OnClickListener() {

													@Override
													public void onClick(View v) {
														dialog.dismiss();
														if(force){
															if(mActivity instanceof HomeActivity){
																YbbApplication.getInstance().setMyUserInfo(null);
																mActivity.finish();
															}
														}
													}
												})
										.setPositiveButtonClickListener("更新",
												new View.OnClickListener() {

													@Override
													public void onClick(View v) {
														Intent updateIntent = new Intent(
																mActivity,
																UpdateService.class);
														updateIntent.putExtra("downLoadUrl",
//																Constants.DOWNLOAD_URL +
																		response.getData().getFilepath());
//														updateIntent.putExtra(
//																		"downLoadUrl",
//																		"http://gdown.baidu.com/data/wisegame/bc51a45152e35ceb/shoujitaobao_115.apk");
														updateIntent.putExtra("ifForce", force);
														mActivity
																.startService(updateIntent);
														dialog.dismiss();
													}
												})
										.show();
							} else {
								if(showLoading){
									Utils.makeToastAndShow(mActivity, "已是最新版本",
											Toast.LENGTH_SHORT);
								}
							}
						} else {// TODO:
							if(!showLoading){
								return;
							}
							Utils.makeToastAndShow(
									mActivity,
									response == null ? "服务器繁忙" : response
											.getContent(), Toast.LENGTH_SHORT);
						}
					}
				}, mActivity);
		if (showLoading) {
			Utils.showProgressDialog(mActivity);
		}else{
			request.setHandleCustomErr(false);
		}
		WebUtils.doPost(request);
	}

}
