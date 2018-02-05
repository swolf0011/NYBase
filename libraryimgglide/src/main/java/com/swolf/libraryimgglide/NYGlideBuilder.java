package com.swolf.libraryimgglide;


/**
 * Created by LiuYi-15973602714
 */
public class NYGlideBuilder {
//    public static int placeholderImgRes;
//    public static int errorImgRes ;
//    public static int fallbackImgRes ;
    public static int placeholderImgRes = R.mipmap.ic_launcher;
    public static int errorImgRes = R.mipmap.ic_launcher;
    public static int fallbackImgRes = R.mipmap.ic_launcher;

    public static void init2App(int placeholderImgResDrawable, int errorImgResDrawable, int fallbackImgResDrawable){
        placeholderImgRes = placeholderImgResDrawable;
        errorImgRes = errorImgResDrawable;
        fallbackImgRes = fallbackImgResDrawable;
    }
}
