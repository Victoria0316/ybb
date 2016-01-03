package com.bluemobi.ybb.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.bluemobi.base.crop.Crop;
import com.bluemobi.base.utils.Logger;
import com.bluemobi.ybb.R;
import com.bluemobi.ybb.activity.LoginActivity;
import com.bluemobi.ybb.activity.RestaurantBookingActivity;
import com.bluemobi.ybb.app.YbbActivityManager;
import com.bluemobi.ybb.app.YbbApplication;
import com.bluemobi.ybb.callback.TitleBarCallBack;
import com.bluemobi.ybb.network.YbbHttpJsonRequest;
import com.bluemobi.ybb.network.exception.CustomResponseError;
import com.bluemobi.ybb.network.exception.TokenInvalid;
import com.bluemobi.ybb.network.request.AddToShopCartRequest;
import com.bluemobi.ybb.network.request.CollectionRequest;
import com.bluemobi.ybb.network.request.DelCollectionRequest;
import com.bluemobi.ybb.network.request.EditShopCartRequest;
import com.bluemobi.ybb.network.response.CollectionResponse;
import com.bluemobi.ybb.network.response.CommonResponse;
import com.bluemobi.ybb.network.response.DelCollectionResponse;
import com.bluemobi.ybb.util.Constants;
import com.bluemobi.ybb.util.PreferencesService;
import com.bluemobi.ybb.util.StringUtils;
import com.bluemobi.ybb.util.UiUtils;
import com.bluemobi.ybb.util.Utils;
import com.bluemobi.ybb.util.ViewUtils;
import com.bluemobi.ybb.util.WebUtils;
import com.bluemobi.ybb.view.LoadingPage;
import com.bluemobi.ybb.view.YbbAlertDialog;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jauker.widget.BadgeView;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class BaseActivity extends AppCompatActivity
		implements Response.ErrorListener,TitleBarCallBack ,
		PullToRefreshListView.OnRefreshListener2{

	/**
	 * 上下文
	 */
	public Context mContext = null;
	/**
	 * 加载Loading页面
	 */
	private LoadingPage loadingPage;

	private ImageView resultView;

	private YbbAlertDialog mDialog;

	private YbbHttpJsonRequest request;

	public PullToRefreshListView plv_refresh = null;
	public static final int LOAD_MORE = 1;
	public static final int LOAD_REFRESH = 2;
	public static final int NUMBER_PER_PAGE = Constants.CURRENTNUMBASE;// 每页条数
	protected int curPage = -1;// 当前页
	protected long pageTime = 0;


	/**
	 * 购物车ImageView
	 */
	public ImageView shopCart;


	public RelativeLayout phone;

	/**
	 * 购买数量
	 */
	public int buyNum = 0;
	/**
	 * 动画层
	 */
	private ViewGroup anim_mask_layout;
	private Queue<ViewGroup> animQueue;
	/**
	 *  显示购物车hint 数量
	 */
	public BadgeView buyNumView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mContext = this;
		buyNumView = new BadgeView(mContext);
		initBaseData();
		LayoutInflater inflater = LayoutInflater.from(mContext);
		request = initRequest();
		initView(inflater);
		invokeRequest();
		YbbActivityManager.getInstance().pushOneActivity(this);

	}

	private void invokeRequest() {
		if(request == null){
			return;
		}else{
			request.setNetWorkResponseListener(loadingPage);
			pageTime = System.currentTimeMillis();
			YbbApplication.getInstance().setPageTime(pageTime);
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
			loadingPage = new LoadingPage(mContext)
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
			ViewUtils.removeParent(loadingPage);
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
	public LoadingPage.LoadResult checkData(List datas)
	{
		if (datas == null)
		{
			return LoadingPage.LoadResult.error;//  请求服务器失败
		}
		else
		{
			if (datas.size() == 0)
			{
				return LoadingPage.LoadResult.empty;
			}
			else
			{
				return LoadingPage.LoadResult.success;
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
		YbbActivityManager.getInstance().popOneActivity(this);
	}

	protected void finishAll() {
		YbbActivityManager.getInstance().finishAllActivity();
	}

	@Override
	protected void onDestroy() {
		YbbActivityManager.getInstance().popOneActivity(this);
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
		if(plv_refresh != null){
			plv_refresh.onRefreshComplete();
		}
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


	//add getPage
	protected boolean getPage(int type) {
		boolean ret = true;

			switch (type) {
				case LOAD_MORE: {
					int count = plv_refresh.getRefreshableView().getAdapter().getCount();
					int i = count-2;
					if (i % NUMBER_PER_PAGE == 0) {
						curPage = i/ NUMBER_PER_PAGE;
					} else {
						ret = false;
						plv_refresh.postDelayed(new Runnable() {
							@Override
							public void run() {
								plv_refresh.onRefreshComplete();
								Utils.makeToastAndShow(mContext, "已经没有更多记录", Toast.LENGTH_SHORT);
							}
						}, 2000);
					}
				}
				break;
				case LOAD_REFRESH:
					pageTime = System.currentTimeMillis();
					YbbApplication.getInstance().setPageTime(pageTime);
					curPage = 0;
					break;
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
	 */

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


	public void setDelCollectionRequest(String id ,String CollectionType,final RefreshCollectListener listener,
										final int p,final int c){

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

	/**
	 * 添加购物车接口
	 *
	 * @param buyNumPar 数量
	 * @param productID 商品ID
	 */
	public void addShopCarRequest(int buyNumPar, String productID,String reserveTime,int type,final boolean isMius) {
         //TODO
		AddToShopCartRequest request = new AddToShopCartRequest
				(
						new Response.Listener<CommonResponse>() {
							@Override
							public void onResponse(CommonResponse response) {
								//addShopCarAction(et_hint);
								if (isMius)
								{
									Utils.makeToastAndShow(mContext, "成功从购物车移除");
								}
								else
								{
									Utils.makeToastAndShow(mContext, "加入购物车成功");
								}

							}
						}, this);
		request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
		request.setProductId(productID);
		request.setProductNum(buyNumPar + "");
		if (type==Constants.FOODS_TYPE)
		{
			request.setReserveTime(reserveTime);
		}
		else
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			request.setReserveTime(format.format(new Date()));
		}

		WebUtils.doPost(request);

	}

	/**
	 * 添加购物车接口
	 *  @param buyNumPar 数量
	 * @param productID 商品ID
	 * @param tv_shopcar_total
	 * @param customer_price
	 */
	public void addShopCarRequest(final int buyNumPar, String productID, String reserveTime,
								  int type, final boolean isMius, final TextView tv_shopcar_total,
								  final String customer_price, String categoryId, String attributeId) {

		Log.e("buyNumPar",buyNumPar+"");
		AddToShopCartRequest request = new AddToShopCartRequest
				(
						new Response.Listener<CommonResponse>() {
							@Override
							public void onResponse(CommonResponse response) {
								String totalString = YbbApplication.getInstance().getCartAmount();
								BigDecimal current = new BigDecimal(StringUtils.isEmpty(totalString) ? "0" :
										totalString);
								BigDecimal target = new BigDecimal(StringUtils.isEmpty(customer_price) ? "0" :
										customer_price);
								int sumAddShopCar = PreferencesService.getInstance(mContext).getPreferencesInt("cartCounts");
								if (isMius)
								{
									BigDecimal total = current.subtract(target);
									total = total.setScale(2, BigDecimal.ROUND_UP);
									YbbApplication.getInstance().setCartAmount(String.valueOf(total.floatValue()));
									Utils.makeToastAndShow(mContext, "成功从购物车移除");

								}
								else
								{
									BigDecimal total = current.add(target);
									total = total.setScale(2, BigDecimal.ROUND_UP);
									YbbApplication.getInstance().setCartAmount(String.valueOf(total.floatValue()));
									Utils.makeToastAndShow(mContext, "加入购物车成功");
									Logger.e("wangzhijun", String.valueOf(total.floatValue()));
								}
								sumAddShopCar = sumAddShopCar + buyNumPar;

								setShopCarNum(sumAddShopCar, true);
								YbbApplication.getInstance().setCartCounts(sumAddShopCar);
								tv_shopcar_total.setText("￥"+YbbApplication.getInstance().getCartAmount());

							}
						}, this);
		request.setUserId(YbbApplication.getInstance().getMyUserInfo().getUserId());
		request.setProductId(productID);
		request.setProductNum(buyNumPar + "");
		request.setCategoryId(categoryId);
		request.setAttributeId(attributeId);
		if (type==Constants.FOODS_TYPE)
		{
			request.setReserveTime(reserveTime);
		}
		else
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			request.setReserveTime(format.format(new Date()));
		}
		WebUtils.doPost(request);

	}
	private ViewGroup animLayout;
	/**
	 * @Description: 创建动画层
	 * @param
	 * @return void
	 * @throws
	 */
	private ViewGroup createAnimLayout() {
		ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
		LinearLayout animLayout = new LinearLayout(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		animLayout.setLayoutParams(lp);
		animLayout.setId(Integer.MAX_VALUE);
		animLayout.setBackgroundResource(android.R.color.transparent);
		rootView.addView(animLayout);
		return animLayout;
	}

	private View addViewToAnimLayout(final ViewGroup parent, final View view,
									 int[] location) {
		int x = location[0];
		int y = location[1];
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		lp.leftMargin = x;
		lp.topMargin = y;
		view.setLayoutParams(lp);
		return view;
	}

	public void setAnim() {
//		if(anim_mask_layout == null){
			anim_mask_layout = createAnimLayout();
			anim_mask_layout.addView(ball);
//		}
		animQueue.offer(anim_mask_layout);
		final View view = addViewToAnimLayout(anim_mask_layout, ball,
				startLocation);
		int[] endLocation = new int[2];
		shopCart.getLocationInWindow(endLocation);
		// 计算位移
		int endX = 0 - startLocation[0] + 80;// 动画位移的X坐标
		int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标
		TranslateAnimation translateAnimationX = new TranslateAnimation(0,
				endX, 0, 0);
		translateAnimationX.setInterpolator(new LinearInterpolator());
		translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
		translateAnimationX.setFillAfter(true);

		TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
				0, endY);
		translateAnimationY.setInterpolator(new AccelerateInterpolator());
		translateAnimationY.setRepeatCount(0);
		translateAnimationX.setFillAfter(true);

		AnimationSet set = new AnimationSet(false);
		set.setFillAfter(false);
		set.addAnimation(translateAnimationY);
		set.addAnimation(translateAnimationX);
		set.setDuration(800);// 动画的执行时间
		view.startAnimation(set);
		set.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				ball.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			@Override
			public void onAnimationEnd(Animation animation) {
				Logger.e("wangzhijun", "onAnimationEnd" );
				ball.setVisibility(View.GONE);
				buyNumView.setText(buyNum + "");
				ViewGroup temp = animQueue.poll();
				ViewGroup rootView = (ViewGroup) BaseActivity.this.getWindow().getDecorView();
//				rootView.removeView(temp);
				temp.setVisibility(View.GONE);
			}
		});

	}

	/**
	 * 购物车显示的数量
	 * @param sum 购物车总数
	 * @param isInit 是否第一次调用
	 */
	public void setShopCarNum(int sum,boolean isInit)
	{
		buyNumView.setTextColor(Color.WHITE);
		buyNumView.setTextSize(12);
		buyNumView.setBackgroundResource(R.drawable.bageview_shop_icon);
		buyNumView.setTargetView(shopCart);
		if (isInit)
			buyNumView.setText(sum + "");
		this.buyNum = sum;

	}

	private int[] startLocation;
	private  TextView ball;

	/**
	 * 设置起始点左边
	 * @param view 起始点View
	 */
  public void setStartPoint(View view)
  {
		if(animQueue == null){
			animQueue = new LinkedList<>();
		}
	  startLocation = new int[2];
	  view.getLocationInWindow(startLocation);
//	  if(ball == null){
		  ball = new TextView(mContext);
		  ball.setText("1");
		  ball.setTextColor(Color.RED);
//	  }else{
//		  ball.setVisibility(View.VISIBLE);
//	  }

  }
	public void setStartPoint(View view,int num)
	{
		if(animQueue == null){
			animQueue = new LinkedList<>();
		}
		startLocation = new int[2];
		view.getLocationInWindow(startLocation);

		ball = new TextView(mContext);
		ball.setText(num+"");
		ball.setTextColor(Color.RED);
	}

	/**
	 * 添加购物车按钮处理
	 * @param editText
	 */
  public void addShopCarAction(EditText editText)
  {

	  int sumAddShopCar = PreferencesService.getInstance(mContext).getPreferencesInt("cartCounts");
	  int num = Integer.parseInt(editText.getText().toString());
	  sumAddShopCar = num + sumAddShopCar;
	  setShopCarNum(sumAddShopCar, false);
	  try {
		  PreferencesService.getInstance(mContext).saveInt("cartCounts", sumAddShopCar);
	  } catch (Exception e) {
		  e.printStackTrace();
	  }

  }



	/**
	 * 删除购物车按钮处理
	 * @param editText
	 */
	public void minusShopCarAction(EditText editText)
	{
		int sumAddShopCar = PreferencesService.getInstance(mContext).getPreferencesInt("cartCounts");
		int num = Integer.parseInt(editText.getText().toString());
		if (sumAddShopCar <= 0) {
			sumAddShopCar = 0;
			try {
				PreferencesService.getInstance(mContext).saveInt("cartCounts", sumAddShopCar);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			sumAddShopCar = sumAddShopCar - num;
			if (sumAddShopCar<=0)
				sumAddShopCar =0;
			try {
				PreferencesService.getInstance(mContext).saveInt("cartCounts", sumAddShopCar);
			} catch (Exception e) {
				e.printStackTrace();
			}


		}
		setShopCarNum(sumAddShopCar, true);

	}

	public void call(){
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + YbbApplication.getInstance().getParamModel().getCateen_phone()));
		startActivity(intent);
	}


}
