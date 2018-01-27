package com.swolf.librarybase.photo.popupWindow;

/**
 * Created by LiuYi-15973602714
 */
public interface NYIActivityResultHandler {
    /**
     * 相册..结果
     */
    void pickPhotoFromGallerySuccess();

    /**
     * 拍照..结果
     */
    void pickPhotoFromTakePhotoSuccess();

    /**
     * 裁剪图片
     */
    void photoZoomClipSuccess();

    /**
     * 取消
     */
    void cancel();

    /**
     * 失败
     */
    void fail();
}
