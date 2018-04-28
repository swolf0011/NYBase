package com.swolf.librarymedia.camera.image;

import android.graphics.SurfaceTexture;
import android.view.TextureView;


/**
 * SurfaceTexture监听
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYTextureViewSurfaceTextureListener implements TextureView.SurfaceTextureListener {
    NYCamera2Image camera2Utils;

    public NYTextureViewSurfaceTextureListener(NYCamera2Image camera2Utils) {
        this.camera2Utils = camera2Utils;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
        camera2Utils.openCamera(width, height);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture texture) {
    }
}
