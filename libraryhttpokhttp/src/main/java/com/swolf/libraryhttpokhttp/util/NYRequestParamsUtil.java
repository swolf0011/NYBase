package com.swolf.libraryhttpokhttp.util;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 请求参数工具
 * Created by LiuYi-15973602714
 */
public class NYRequestParamsUtil {
    /**
     * Head设置
     *
     * @param builder
     * @param headMap
     * @return
     */
    public void setHead(Request.Builder builder, HashMap<String, String> headMap) {
        if (headMap != null) {
            for (Map.Entry<String, String> en : headMap.entrySet()) {
                builder.addHeader(en.getKey(), en.getValue());
            }
        }

    }

    /**
     * 参数设置
     *
     * @param builder
     * @param paramMap
     * @return
     */
    public void setParam(FormBody.Builder builder, HashMap<String, Object> paramMap) {
        if (paramMap != null) {
            for (Map.Entry<String, Object> en : paramMap.entrySet()) {
                builder.add(en.getKey(), en.getValue() + "");
            }
        }
    }


}
