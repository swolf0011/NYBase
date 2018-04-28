package com.swolf.librarypay.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

/**
 * @author LiuYi-15973602714
 * @version V1.0
 */
@SuppressLint("HandlerLeak") 
public class NYAlipayUtil {

//	// 商户PID
//	public static final String PARTNER = "2088711189454077";
//	// 商户收款账号
//	public static final String SELLER = "2088711189454077";
//	// 商户私钥，pkcs8格式
//	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANHqi58UMnhUQUmzvi2lNnPCXc80KQzsnFgmruLXBMV/x8vDEh7uJddWlLonIEruCA4tUcKHPng8nugp65vRvWfU1k6zqVplvyp2XfAsLkFYMykXmjXJ0ti70emH1KHnJ2MZ7nxrfQHjOCsuK9fHq9QIFGU0DRCTEqhmjBHpa1qjAgMBAAECgYBal0kYJwQ878eZQgvR8RnVzUzkzhLlM/upA1C4Lqktfp6/7fiVBpuoGgJnb9k83Qr261H8jJdGFotRkD3Q6iw9qdGhS9a/afyx2SwJqizte3H5Ev7eB6SUGxGnoT6WPVO2nl0+IBnsbA/Th4cvCWrl5mDMkamy4TudlWa9KaYQCQJBAPJExDB6luVR9RohvlZva6/DIaOgPrlL+16e72FUwQHppIHTY/8d3rVkreQLtEQN2e5tKRKQZkJFExCIDnzzOMUCQQDd0FtHMuXaobCWPxVicZWP0qOd3eDiUraEzRIJsjMjFKI3p9lQZLVU6F/n1Ila0GiIlKAwhyajChNJJEbDLexHAkEArI6MSpdWWPnaIQW9w2TTB7ptgFUHuAVFgmyjxeiPHGSk9o9xXumQkhSmwpIPkJVpDyiTI5TUMQlv/ctavmainQJBAKnvc87TVreuQlyJTffSr1O1e7Z5g03BMqYBej1FcdoBd9oN1Pa7gRTgxoEVGnohysRAoY0sLdSg5m+VxETKDQcCQBILEiZ+i89hPyQY8mOLy9acVKhz5xW0qfuW5V/hIco0OUmWafF+yFiVNzkJ1BM5Dibgmc6a4zgRSW16Anipxhg=";
//	// 支付宝公钥
//	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
//	// 服务器异步通知页面路径
//	public static final String NOTIFY_URL = "http://notify.msp.hk/notify.htm";

	
	

	private static final int SDK_PAY_FLAG = 1;

	private static final int SDK_CHECK_FLAG = 2;

	Activity activity;
	
	IPaySuccess paySuccess;

	public NYAlipayUtil(Activity activity) {
		this.activity = activity;
	}

	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String result = payResult.getResult();
				String memo = payResult.getMemo();
				String resultStatus = payResult.getResultStatus();

				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(activity, "支付成功-获取服务器订单信息。", Toast.LENGTH_SHORT).show();
					if(paySuccess!=null){
						paySuccess.successHandler();
					}
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(activity, "支付结果确认中", Toast.LENGTH_SHORT).show();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//						Toast.makeText(activity, "支付失败，请检查是否已安装支付宝。", Toast.LENGTH_SHORT).show();
						Toast.makeText(activity, "支付失败。", Toast.LENGTH_SHORT).show();
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(activity, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
		};
	};
	
	public interface IPaySuccess{
		void successHandler();
	}

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 * @param subject
	 *            "商品名"
	 * @param body
	 *            "品的详细描述"
	 * @param price
	 *            "0.01"
	 * @param outTradeNo
	 *            "商户订单号"：后台生成，后台可以使用此类的getOutTradeNo()生成
	 * @param notify_url
	 *            服务器异步通知页面路径"http://notify.msp.hk/notify.htm"
	 * @param return_url
	 *            服务器同步通知页面路径"http://notify.msp.hk/notify.htm"           
	 * 
	 */
	public void pay(String subject, String body, String price, String outTradeNo, String notify_url,String return_url,IPaySuccess paySuccess) {
		
		this.paySuccess = paySuccess;
		// 订单
		String orderInfo = getOrderInfo(subject, body, price, outTradeNo, notify_url,return_url);

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {

				// 构造PayTask 对象
				PayTask alipay = new PayTask(activity);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {
			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(activity);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();
	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(activity);
		String version = payTask.getVersion();
		Toast.makeText(activity, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price, String outTradeNo, String notify_url,String return_url) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + NYAlipayCommon.PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + NYAlipayCommon.SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + outTradeNo + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" + notify_url + "\"";

		// 服务接口名称， 固定值
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

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\""+return_url+"\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, NYAlipayCommon.RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

}
