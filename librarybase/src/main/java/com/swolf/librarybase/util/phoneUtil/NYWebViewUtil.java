package com.swolf.librarybase.util.phoneUtil;

import android.annotation.SuppressLint;
import android.webkit.WebView;

/**
 * WebView工具
 * Created by LiuYi-15973602714
 */
public class NYWebViewUtil {

    /**
     *
     * @param jsObject
     * @param webView
     * @param html
     */
    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    public static void loadHtml(Object jsObject, WebView webView, String html) {
        webView.getSettings().setJavaScriptEnabled(true);
        // 加载需要显示的网页
        webView.addJavascriptInterface(jsObject, "jsObject");
        webView.loadUrl(html);
    }
}
