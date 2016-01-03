package com.bluemobi.ybb.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.PayResult;
import com.alipay.sdk.pay.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * wangzhijun
 */
public class AlipayUtil {

	public static final int SDK_PAY_FLAG = 1;

	public static final int SDK_CHECK_FLAG = 2;
	/**
	 * 签约的支付宝账号对应的支 付宝唯一用户号。 以 2088 开头的 16 位纯数字 组成。
	 */
	public static final String PARTNER = "2088021122249014"; // 商户PID
	/**
	 * 卖家支付宝账号（邮箱或手机 号码格式）或其对应的支付宝 唯一用户号（以 2088 开头的 纯 16 位数字）。
	 */
	public static final String SELLER = "2088021122249014";

	// 商户私钥，pkcs8格式

	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMxQc37SuVJbjeeXf+C2u35sLJe0LyBhfxaeWsPhzCJcZ03ljF73LpkKTnrbYrgrA4QtTDvV15WfG+Ng7n0J+TIuc4+zaUmc652KIg7bVtLD4bH/oTxwxELnfNyoWcPbX45A5QLnT8FcZGsDpe/oyOShV2c50ZgcF6xmRT9D0dIZAgMBAAECgYEAtZpczkFnT/jo7Q0QMSC7Duf/yufxa4q9d9FRRAMTewc/G1IJru9uj8FFeJY/7QrumTHaLOUqNmeyViz2TlXrFEMV5SLcZ6spbFBxr3XFJM+F98pP5WY5AxkPZCVP5RGAEtFKcMt+XCw6kVWlWZRNlJ4XkxIghBtPz8a6VxVGKHECQQD77A7j+Cr48vJA6Ck40AxgxHpk9qFQd1nCMiacBXcoNbXWvpJdPdkWFryPArpk47qgEJR8TZRfxVDG3iF/3mn/AkEAz58cSpwWtuGgJoRO+CrulkGXO0kG/3hglaCCB4e1Bhze7i3QQ9QsDA2HkgR51jPpvQRwyKUWi7L5l5aosT3T5wJBALpi5IF2UxTtap7rJdSK2R2fQI8CikdmhlpPkJFO2eI+GtNPHCswXzUgjVGhyxZqBuH2YmW1YFmbOzBnmJFqlTECQQCjjFmz6Br4euk2DhlK4j7JO+fOeqs5iPDETQjAt8UAiLOPeaVKGRONdwbpHf9/nastCxdw5qLI7vYv1C0UWXjXAkALFUETFoEe/blQYB+vs2tTmOl2MOdFNrpWs/6NxU8/lggQfxndMKKOeUNHW2Jf5QGgo4BShJxtIae0UxRYmCS7";


	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCgzfpSAukciJguvVwEzbQqAvxsJkyydsmF/VeVe6eHcf6NnE1ZdQLJiWn6AakFxk3wMHTxKdTEKgJ+bGEq4X6Gbic4F3DtrMgyKfELOkw6WDi0s5yNlkqdjCJHPKxilx2jPoel4wmDgvHAY2eSxl+AxAmyf89Sc7wWUIGtZKflwQIDAQAB";

	private static String notify_url = "http://lcg711624.xicp.net:34087/ebbmgr/ali/alipayNotify";

	private AlipayStausListener mListener;

	private Activity mActivity;

	public AlipayUtil(AlipayStausListener listener, Activity activity) {
		mListener = listener;
		mActivity = activity;
	}

	public AlipayUtil(Activity activity) {
		mListener = null;
		mActivity = activity;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SDK_PAY_FLAG: {
					PayResult payResult = new PayResult((String) msg.obj);

					// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
					String resultInfo = payResult.getResult();

					String resultStatus = payResult.getResultStatus();

					// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
					if (TextUtils.equals(resultStatus, "9000")) {
						Toast.makeText(mActivity, "支付成功",
								Toast.LENGTH_SHORT).show();
						if(mListener != null){
							mListener.payOk();
						}
					} else {
						// 判断resultStatus 为非“9000”则代表可能支付失败
						// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
						if (TextUtils.equals(resultStatus, "8000")) {
							Toast.makeText(mActivity, "支付结果确认中",
									Toast.LENGTH_SHORT).show();
							if(mListener != null){
								mListener.paying();
							}

						} else {
							// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
							Toast.makeText(mActivity, "支付失败",
									Toast.LENGTH_SHORT).show();
							if(mListener != null){
								mListener.payFailed();
							}

						}
					}
					break;
				}
				case SDK_CHECK_FLAG: {
					Toast.makeText(mActivity, "检查结果为：" + msg.obj,
							Toast.LENGTH_SHORT).show();
					if (mListener != null) {
						mListener.checkResult((Boolean) msg.obj);
					}

					break;
				}
				default:
					break;
			}
		}

		;
	};

	public void check() {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask payTask = new PayTask(mActivity);
				boolean isExist = payTask.checkAccountIfExist();
				Log.d("AlipayUtil--", "" + isExist);
				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();
	}

	public void pay(String subject, String body, String price, String orderNo) {
		pay(subject, body, price, orderNo, null);
	}
	/**
	 *
	 * @param subject
	 * @param body
	 * @param price
	 * @param orderNo
	 */
	public void pay(String subject, String body, String price, String orderNo, String notify_url) {
		String orderInfo = getOrderInfo(subject, body, price, orderNo, notify_url);
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(mActivity);
				// 调用支付接口
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	public interface AlipayStausListener {
		public void checkResult(boolean isExist);

		public void payOk();

		public void payFailed();

		public void paying();
	}

	public String getOrderInfo(String subject, String body, String price,
			String orderNo, String notify) {
		String tempNotify = StringUtils.isEmpty(notify)?this.notify_url:notify;
		// 合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + orderNo + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + tempNotify + "\"";

		// 接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}
}
