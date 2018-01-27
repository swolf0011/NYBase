package com.swolf.librarybase.baseView;

/**
 * 基View方法类
 * Created by LiuYi-15973602714
 */
public interface NYIBaseViewFun {

    /**
     * 初始化Intent数据
     */
    public abstract void getIntentInData();

    /**
     * 初始化View
     */
    public abstract void initView();

    /**
     * 初始化Value
     */
    public abstract void initValue();

    /**
     * 获取网络数据
     */
    public abstract void getNetDate();
}
