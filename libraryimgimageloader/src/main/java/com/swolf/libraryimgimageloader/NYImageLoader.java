package com.swolf.libraryimgimageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;


/**
 * 图片缓存加载工具
 * 在application类中添加NYImageLoader.initImageLoader(this);
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYImageLoader {

    /**
     UI:请求数据,使用唯一的Key值索引Memory Cache中的Bitmap.
     内存缓存:缓存搜索,如果能找到Key值对应的Bitmap,则返回数据.否则执行第三步.
     硬盘存储:使用唯一Key值对应的文件名,检索SDCard上的文件.
     如果有对应文件,使用BitmapFactory.decode*方法,解码Bitmap并返回数据,同时将数据写入缓存.如果没有对应文件,执行第五步.
     下载图片:启动异步线程,从数据源下载数据(Web).
     若下载成功,将数据同时写入硬盘和缓存,并将Bitmap显示在UI中.
     */

    /*
    UIL中的内存缓存策略

    1只使用的是强引用缓存
    LruMemoryCache[这个类就是这个开源框架默认的内存缓存类,缓存的是bitmap的强引用,下面我会从源码上面分析这个类]

    2使用强引用和弱引用相结合的缓存有
    UsingFreqLimitedMemoryCache[如果缓存的图片总量超过限定值,先删除使用频率最小的bitmap]
    LRULimitedMemoryCache[这个也是使用的lru算法,和LruMemoryCache不同的是,他缓存的是bitmap的弱引用]
    FIFOLimitedMemoryCache[先进先出的缓存策略,当超过设定值,先删除最先加入缓存的bitmap]
    LargestLimitedMemoryCache[当超过缓存限定值,先删除最大的bitmap对象]
    LimitedAgeMemoryCache[当 bitmap加入缓存中的时间超过我们设定的值,将其删除]

    3只使用弱引用缓存
    WeakMemoryCache[这个类缓存bitmap的总大小没有限制,唯一不足的地方就是不稳定,缓存的图片容易被回收掉]
    */

    /**
     * UIL中的磁盘缓存策略
     * FileCountLimitedDiscCache[可以设定缓存图片的个数,当超过设定值,删除掉最先加入到硬盘的文件]
     * LimitedAgeDiscCache[设定文件存活的最长时间,当超过这个值,就删除该文件]
     * TotalSizeLimitedDiscCache[设定缓存bitmap的最大值,当超过这个值,删除最先加入到硬盘的文件]
     * UnlimitedDiscCache[这个缓存类没有任何的限制]
     */

    private static int imageRes;
    private static DisplayImageOptions displayOptions = null;

    public static void initImageLoader(Context context, int imageResVal) {
        NYImageLoader.imageRes = imageResVal;
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, "imageloader/Cache");

        //创建默认的ImageLoader  全局配置参数
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration
                .Builder(context)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长度
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                // .memoryCache(new LruMemoryCache(5 * 1024 * 1024)) //内存缓存方式  默认就是此方法
                .memoryCacheSize(20 * 1024 * 1024) //内存缓存大小 20Mib
                .diskCacheSize(50 * 1024 * 1024) //50 Mib
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//加密
                .diskCacheFileCount(200)//缓存的文件数量
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for release app
                .build();

        ImageLoader.getInstance().init(configuration);
    }


    /**
     * 加载显示图片
     */
    public static void showLoaderImage(String imageUrl, ImageView imageView_img) {
        if (NYImageLoader.displayOptions == null) {
            setDisplayOptions(imageRes);
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView_img, NYImageLoader.displayOptions);
    }

    private static void setDisplayOptions(int imageRes) {
        displayOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(imageRes)
                .showImageForEmptyUri(imageRes)
                .showImageOnFail(imageRes)
                .cacheInMemory(true)//设置下载的图片是否缓存在内存
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                //.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
                //设置图片加入缓存前，对bitmap进行设置
                //.preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
//			.displayer(new CircleBitmapDisplayer(Color.WHITE, 5))//是否设置圆形显示
                .build();
    }

}
