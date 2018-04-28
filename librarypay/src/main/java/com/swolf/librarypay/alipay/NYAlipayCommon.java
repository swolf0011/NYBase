package com.swolf.librarypay.alipay;

/**
 * @author LiuYi-15973602714
 * @version V1.0
 */
public class NYAlipayCommon {
	// 商户PID
	public static String PARTNER = "2088711189454077";
	// 商户收款账号
	public static String SELLER = "2088711189454077";
	// 商户私钥，pkcs8格式
	public static String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANHqi58UMnhUQUmzvi2lNnPCXc80KQzsnFgmruLXBMV/x8vDEh7uJddWlLonIEruCA4tUcKHPng8nugp65vRvWfU1k6zqVplvyp2XfAsLkFYMykXmjXJ0ti70emH1KHnJ2MZ7nxrfQHjOCsuK9fHq9QIFGU0DRCTEqhmjBHpa1qjAgMBAAECgYBal0kYJwQ878eZQgvR8RnVzUzkzhLlM/upA1C4Lqktfp6/7fiVBpuoGgJnb9k83Qr261H8jJdGFotRkD3Q6iw9qdGhS9a/afyx2SwJqizte3H5Ev7eB6SUGxGnoT6WPVO2nl0+IBnsbA/Th4cvCWrl5mDMkamy4TudlWa9KaYQCQJBAPJExDB6luVR9RohvlZva6/DIaOgPrlL+16e72FUwQHppIHTY/8d3rVkreQLtEQN2e5tKRKQZkJFExCIDnzzOMUCQQDd0FtHMuXaobCWPxVicZWP0qOd3eDiUraEzRIJsjMjFKI3p9lQZLVU6F/n1Ila0GiIlKAwhyajChNJJEbDLexHAkEArI6MSpdWWPnaIQW9w2TTB7ptgFUHuAVFgmyjxeiPHGSk9o9xXumQkhSmwpIPkJVpDyiTI5TUMQlv/ctavmainQJBAKnvc87TVreuQlyJTffSr1O1e7Z5g03BMqYBej1FcdoBd9oN1Pa7gRTgxoEVGnohysRAoY0sLdSg5m+VxETKDQcCQBILEiZ+i89hPyQY8mOLy9acVKhz5xW0qfuW5V/hIco0OUmWafF+yFiVNzkJ1BM5Dibgmc6a4zgRSW16Anipxhg=";
	// 支付宝公钥
	public static String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	// 服务器异步通知页面路径
	public static String NOTIFY_URL = "http://notify.msp.hk/notify.htm";
	// 订单号
	public static String ORDER_NO = "";
	// 服务器同步通知页面路径
	public static String RETURN_URL = "http://notify.msp.hk/notify.htm";
}
