package com.bluemobi.ybb.ps.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.bluemobi.base.crop.Crop;
import com.bluemobi.base.utils.Utils;
import com.bluemobi.base.utils.WebUtils;
import com.bluemobi.ybb.ps.activity.LoginActivity;
import com.bluemobi.ybb.ps.app.ActivityManager;
import com.bluemobi.ybb.ps.app.YbbPsApplication;
import com.bluemobi.ybb.ps.callback.TitleBarCallBack;
import com.bluemobi.ybb.ps.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.ps.network.exception.CustomResponseError;
import com.bluemobi.ybb.ps.network.exception.TokenInvalid;
import com.bluemobi.ybb.ps.util.Constants;
import com.bluemobi.ybb.ps.view.LoadingPage;
import com.bluemobi.ybb.ps.view.YbbAlertDialog;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements
		TitleBarCallBack,PullToRefreshListView.OnRefreshListener2,Response.ErrorListener{
	/**
	* 上下文
	*/
	public Context mContext = null;
	/**
	 * 加载Loading页面
	 */
	private com.bluemobi.ybb.ps.view.LoadingPage loadingPage;

	private ImageView resultView;

	private YbbAlertDialog mDialog;

	private YbbHttpJsonRequest request;

	public PullToRefreshListView plv_refresh = null;
	public static final int LOAD_MORE = 1;
	public static final int LOAD_REFRESH = 2;
	public static final int NUMBER_PER_PAGE = Constants.CURRENTNUMBASE;// 每页条数
	protected int curPage = -1;// 当前页
	protected long pageTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mContext = this;
		initBaseData();
		LayoutInflater inflater = LayoutInflater.from(mContext);
		request = initRequest();

		initView(inflater);
		invokeRequest();
		ActivityManager.getInstance().pushOneActivity(this);

	}

	private void invokeRequest() {
		if(request == null){
			return;
		}else{
			request.setNetWorkResponseListener(loadingPage);
			pageTime = System.currentTimeMillis();
			YbbPsApplication.getInstance().setPageTime(pageTime);
			request.setNetWorkResponseListener(loadingPage);
			WebUtils.doPost(request);
		}
	}

	protected YbbHttpJsonRequest initRequest() {
		return null;
	}


	/**
	 * 初始化基础数据
	 */
	protected abstract void initBaseData();

	/**
	 * 初始化loadingpage
	 */
	protected void initView( final LayoutInflater inflater)
	{

		if (loadingPage == null)
		{
			loadingPage = new com.bluemobi.ybb.ps.view.LoadingPage(mContext)
			{


				@Override
				public View createSuccessView()
				{

					return BaseActivity.this.createSuccessView(inflater);
				}

				@Override
				protected LoadResult load()
				{
					return BaseActivity.this.load();
				}
			};
		}
		else
		{
			com.bluemobi.base.utils.ViewUtils.removeParent(loadingPage);
		}
		setContentView(loadingPage);

	}

	/**
	 *  显示加载界面
	 */
	public void showLoadingPage(boolean isShowLoad)
	{
		if(loadingPage!=null)
		{
			loadingPage.show(isShowLoad);
		}
	}


	/**
	 * 服务器返回数据校验
	 * @param datas
	 * @return
	 */
	public com.bluemobi.ybb.ps.view.LoadingPage.LoadResult checkData(List datas)
	{
		if (datas == null)
		{
			return com.bluemobi.ybb.ps.view.LoadingPage.LoadResult.error;//  请求服务器失败
		}
		else
		{
			if (datas.size() == 0)
			{
				return com.bluemobi.ybb.ps.view.LoadingPage.LoadResult.empty;
			}
			else
			{
				return com.bluemobi.ybb.ps.view.LoadingPage.LoadResult.success;
			}
		}
	}


	/***
	 *  创建成功的界面 通过inflate加载界面
	 * @return
	 */
	public abstract View createSuccessView(LayoutInflater inflater);
	/**
	 * 请求服务器
	 * @return
	 */
	protected abstract LoadingPage.LoadResult load();


	protected void myFinish() {
		ActivityManager.getInstance().popOneActivity(this);
	}

	protected void finishAll() {
		ActivityManager.getInstance().finishAllActivity();
	}

	@Override
	protected void onDestroy() {
		ActivityManager.getInstance().popOneActivity(this);
		super.onDestroy();
	}


	/**
	 * 点击bar 右边按钮
	 */
	@Override
	public void clickBarRight() {

	}

	/**
	 * 点击bar左边按钮
	 */
	@Override
	public void clickBarleft()
	{
		this.finish();
	}

	/**
	 * 点击bar搜索按钮
	 */
	@Override
	public void clickBarSearch() {

	}


	/**
	 * 点击bar搜索按钮
	 */
	@Override
	public void clickBarHint() {

	}

	@Override
	public void clickleftTab() {

	}

	@Override
	public void clickMiddleTab() {

	}

	@Override
	public void clickRightTab() {

	}


	@Override
	public void clickTwoTableft() {

	}

	@Override
	public void clickTwoTabright() {

	}

	@Override
	public void clickImageRight() {

	}

	/**
	 * 点击下拉spinner
	 */
	@Override
	public void clickBarSpinner(int position) {

	}

	public void initPullToRefresh(PullToRefreshListView pullToRefresh)
	{
		this.plv_refresh = pullToRefresh;
		pullToRefresh.setOnRefreshListener(this);
		pullToRefresh.setMode(PullToRefreshBase.Mode.BOTH);
		ILoadingLayout startLabels = pullToRefresh
				.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("下拉刷新...");
		startLabels.setRefreshingLabel("正在载入...");
		startLabels.setReleaseLabel("放开刷新...");
		ILoadingLayout endLabels = pullToRefresh.getLoadingLayoutProxy(
				false, true);
		endLabels.setPullLabel("上拉刷新...");
		endLabels.setRefreshingLabel("正在载入...");
		endLabels.setReleaseLabel("放开刷新...");
	}

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent result) {
//		if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
//			beginCrop(result.getData());
//		} else if (requestCode == Crop.REQUEST_CROP) {
//			handleCrop(resultCode, result);
//		} else if (requestCode == Crop.REQUEST_CAMERA && resultCode == RESULT_OK) {
//			beginCrop(Crop.outputFileUri);
//		}
//	}

	private void beginCrop(Uri source) {
		Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
		Crop.of(source, destination).asSquare().start(this);
	}

	private void handleCrop(int resultCode, Intent result) {
		if (resultCode == RESULT_OK) {
			resultView.setImageURI(Crop.getOutput(result));
		} else if (resultCode == Crop.RESULT_ERROR) {
			Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	protected void getImage(ImageView iv){
		this.resultView = iv;
		CharSequence[] items = { "相册", "相机" };
		new AlertDialog.Builder(this).setTitle("选择图片来源")
				.setItems(items, new MyDialogClickListener(1)).create()
				.show();
	}

	private class MyDialogClickListener implements
			DialogInterface.OnClickListener {
		private int n;

		MyDialogClickListener(int number) {
			this.n = number;
		}

		public void onClick(DialogInterface dialog, int which) {
			if (which == 0) {
				resultView.setImageDrawable(null);
				Crop.pickImage(BaseActivity.this);
			} else {
				resultView.setImageDrawable(null);
				Crop.pickImageFromCamera(BaseActivity.this);
			}
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		requestError(error);
	}
	public void requestError(VolleyError error) {
		Utils.closeDialog();
		if (error instanceof TimeoutError) {
			Utils.makeToastAndShow(this, "网络异常,请检查您的网络", Toast.LENGTH_SHORT);
		} else if (error instanceof ParseError) {
			Utils.makeToastAndShow(this, "解析出错", Toast.LENGTH_SHORT);
		} else if (error instanceof TokenInvalid) {
			if (mDialog == null || !mDialog.isShowing()) {
				mDialog = new YbbAlertDialog(this);
				mDialog.setTitle("提示")
						.setMessage("用户登录已失效,请重新登录")
						.setNegativeButtonClickListener("取消",
								new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										mDialog.dismiss();
									}
								})
						.setPositiveButtonClickListener("确定",
								new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										Intent intent = new Intent();
										intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
										intent.setClass(getBaseContext(),
												LoginActivity.class);
										intent.putExtra("tokeninvalid", true);
										getBaseContext().startActivity(intent);
										mDialog.dismiss();
									}
								});
				mDialog.show();
			}

		} else if (error instanceof CustomResponseError) {
			CustomResponseError crs = (CustomResponseError) error;
			requestCustomErr(crs);
		} else if(error instanceof NoConnectionError){//无网络连接
			Utils.makeToastAndShow(this, "网络异常,请检查您的网络");
		}
		error.printStackTrace();
	};
	public void requestCustomErr(CustomResponseError err) {
		if (err.isToast()) {
			Utils.makeToastAndShow(this, err.getErrMsg(), Toast.LENGTH_SHORT);
		}
	}
	private PicView curPicView = new PicView();
	private int curPicKey = 0;
	protected static final int PIC_WIDTH = 480;
	protected static final int PIC_HEIGHT = 480;
	protected static final int PIC_REQUEST_BEGIN = 0;
	protected static final int PIC_REQUEST_FINISH = 1;
	protected static final int REQUEST_CODE_CAMERA = 130;
	protected static final int REQUEST_CODE_PHOTO = 131;
	protected static final int REQUEST_CODE_CROP = 132;
	protected static final String PIC_NAME = "cmrp";
	protected static final String PIC_NAME_EXT = ".jpg";
	private HashMap<Integer, PicView> picImageViewMap;

	private class PicView {
		public ImageView iv;
		public int status;
		public int width = 0;
		public int height = 0;
	}
	// 照相
	protected void takePhoto(int n, ImageView imageView) {
		curPicView.iv = imageView;
		curPicKey = n;
		curPicView.width = PIC_WIDTH;
		curPicView.height = PIC_HEIGHT;
		curPicView.status = PIC_REQUEST_BEGIN;
		File file = new File(Environment.getExternalStorageDirectory(), "cmrp" + n + ".jpg");
		Uri outputFileUri = Uri.fromFile(file);
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
		startActivityForResult(intent, REQUEST_CODE_CAMERA);
	}

	// 从相册中选择一个照片
	protected void selectPic(int n, ImageView imageView) {
		curPicView.iv = imageView;
		curPicKey = n;
		curPicView.width = PIC_WIDTH;
		curPicView.height = PIC_HEIGHT;
		curPicView.status = PIC_REQUEST_BEGIN;

		Intent intent1 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(Intent.createChooser(intent1, "选择图片"), REQUEST_CODE_PHOTO);
	}

	/**
	 * Activity回调函数
	 * */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
			beginCrop(data.getData());
		} else if (requestCode == Crop.REQUEST_CROP) {
			handleCrop(resultCode, data);
		} else if (requestCode == Crop.REQUEST_CAMERA && resultCode == RESULT_OK) {
			beginCrop(Crop.outputFileUri);
		}
		if (requestCode == REQUEST_CODE_PHOTO && resultCode == RESULT_OK) {
			Uri inputUri = data.getData();
			Uri outputUri = getPicUri(curPicKey);
			cropImageUri(inputUri, outputUri, 480, 480);
		}
		if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
			Uri uri = getPicUri(curPicKey);
			cropImageUri(uri, uri, 480, 480);
		}

		if (requestCode == REQUEST_CODE_CROP && resultCode == RESULT_OK) {
			String path = getPicFilename(curPicKey);
			// resetPicSizeByOptions(path, PIC_WIDTH / 2, PIC_HEIGHT / 2);
			if (picImageViewMap == null) {
				picImageViewMap = new HashMap<Integer, PicView>();
			}
			PicView pv = picImageViewMap.get(curPicKey);
			if (pv == null) {
				pv = new PicView();
				picImageViewMap.put(curPicKey, pv);
			}
			pv.iv = curPicView.iv;
			pv.status = PIC_REQUEST_FINISH;
			pv.width = curPicView.width;
			pv.height = curPicView.height;

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = calculateInSampleSize(path, pv.iv.getWidth(), pv.iv.getHeight());
			Bitmap bmp = BitmapFactory.decodeFile(path, options);

			pv.iv.setImageBitmap(bmp);
			doPicOK();
		}
	}
	protected void doPicOK() {
	}
	/**
	 *
	 * 获得照片uri
	 *
	 * */
	protected Uri getPicUri(int key) {
		File file = new File(Environment.getExternalStorageDirectory(), PIC_NAME + key + PIC_NAME_EXT);
		Uri uri = Uri.fromFile(file);
		return uri;
	}
	/**
	 *
	 * 图片裁剪函数
	 *
	 * **/
	private void cropImageUri(Uri inputUri, Uri outputUri, int outputX, int outputY) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(inputUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", outputX);
		intent.putExtra("aspectY", outputY);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, REQUEST_CODE_CROP);
	}
	/**
	 *
	 * 获得照片文件名
	 *
	 * **/
	protected String getPicFilename(int key) {
		String path = Environment.getExternalStorageDirectory() + "/" + PIC_NAME + key + PIC_NAME_EXT;
		return path;
	}
	/**
	 *
	 * 计算图片BitmapFactory.Options.inSampleSize值
	 *
	 * */
	private static int calculateInSampleSize(String picPath, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(picPath, options);

		final int height = options.outHeight;
		final int width = options.outWidth;
		// String imageType = options.outMimeType;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
	@Override
	public void onPullDownToRefresh(PullToRefreshBase pullToRefreshBase)
	{
		Log.e("tag", "onPullDownToRefresh");
		getPage(LOAD_REFRESH);
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase pullToRefreshBase)
	{
		Log.e("tag","onPullUpToRefresh");
		getPage(LOAD_MORE);

	}


	public void plvonRefreshCompleted() {
		if(plv_refresh==null)
			return;

		plv_refresh.onRefreshComplete();

	}

	protected boolean getPage(int type) {
		boolean ret = true;
		if (plv_refresh == null) {
			switch (type) {
				case LOAD_MORE:
					curPage++;
					break;
				case LOAD_REFRESH:
					pageTime = System.currentTimeMillis();
					YbbPsApplication.getInstance().setPageTime(pageTime);
					curPage = 0;
					break;
			}
		} else {
			switch (type) {
				case LOAD_MORE: {

					ListAdapter adapter = plv_refresh.getRefreshableView().getAdapter();
					int i = adapter.getCount()-2;
					if (i % NUMBER_PER_PAGE == 0) {
						curPage = i/ NUMBER_PER_PAGE;
					} else {
						ret = false;
						plvonRefreshCompleted();
						Utils.makeToastAndShow(this, "已经没有更多记录", Toast.LENGTH_SHORT);
					}
				}
				break;
				case LOAD_REFRESH:
					pageTime = System.currentTimeMillis();
					YbbPsApplication.getInstance().setPageTime(pageTime);
					curPage = 0;
					break;
			}
		}

		return ret;
	}



	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public long getPageTime() {
		return pageTime;
	}


	public interface RefreshCollectListener {

		public void refreshView(String flag,int  indexP,int indexC);
	}
	/**
	 * 收藏
	 *//*

	public void setCollectionRequest(String id ,String CollectionType,final RefreshCollectListener listener,final int p,final int c){
		CollectionRequest request = new CollectionRequest(new Response.Listener<CollectionResponse>() {
			@Override
			public void onResponse(CollectionResponse response) {
				Utils.closeDialog();
				if (response != null && response.getStatus() == 0) {// success
					Utils.makeToastAndShow(mContext, "收藏成功");
//                    isColl.setBackgroundResource(R.drawable.coll);
					listener.refreshView("1", p, c);
				} else {// failed
					Log.e("error", "error");
				}
			}
		},this);
		request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
		request.setCollectionId(id);
		request.setCollectionType(CollectionType);
		Utils.showProgressDialog(mContext);
		WebUtils.doPost(request);
	}

	*//**
	 * 取消收藏
	 *//*
	public void setDelCollectionRequest(String id ,String CollectionType,final RefreshCollectListener listener,final int p,final int c){

		DelCollectionRequest request = new DelCollectionRequest(new Response.Listener<DelCollectionResponse>() {
			@Override
			public void onResponse(DelCollectionResponse response) {
				Utils.closeDialog();
				if (response != null && response.getStatus() == 0) {// success
					Utils.makeToastAndShow(mContext, "取消收藏");
					listener.refreshView("0", p, c);
				} else {// failed
					Log.e("error", "error");
				}
			}
		},this);
		request.setUserid(YbbApplication.getInstance().getMyUserInfo().getUserId());
		request.setCollectionId(id);
		request.setCollectionType(CollectionType);
		Utils.showProgressDialog(mContext);
		WebUtils.doPost(request);
	}
*/

	protected static final int PIC_REQUEST_NOT_BEGIN = -1;
	/**
	 *
	 * 得到图片状态
	 *
	 * */
	protected int getPictureStatus(int key) {
		if (picImageViewMap == null) {
			return PIC_REQUEST_NOT_BEGIN;
		}

		PicView v = picImageViewMap.get(key);
		if (v == null) {
			return PIC_REQUEST_NOT_BEGIN;
		}

		return v.status;
	}

}
