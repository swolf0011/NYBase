package com.swolf.librarybase.constract;

/**
 * Created by LiuYi-15973602714
 */
public abstract class NYAPresenter<V extends NYIView, M extends NYIModel> {
    public V view;
    public M model;

    public NYAPresenter(V view) {
        this.view = view;
        this.view.setPresenter(this);
        setModel();
    }

    /**
     * 设置model
     */
    public abstract void setModel();
}
