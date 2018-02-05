package com.swolf.libraryimgglide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by LiuYi-15973602714
 */
@GlideModule
public class NYAppGlideModule extends AppGlideModule {
    public void registerComponents(Context context, Glide glide, Registry registry) {
    }
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.format(DecodeFormat.PREFER_RGB_565);

        builder.setDefaultRequestOptions(requestOptions);

        //设置memory和Bitmap池的大小
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));


        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));

        int cacheSize100MegaBytes = 1024*1024*100; // 100 MB
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, cacheSize100MegaBytes));

    }

}