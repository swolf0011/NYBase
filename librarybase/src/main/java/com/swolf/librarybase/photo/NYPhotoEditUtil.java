package com.swolf.librarybase.photo;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片工具
 * Created by LiuYi-15973602714
 */
@SuppressLint("InflateParams")
public class NYPhotoEditUtil {
    /**
     * 圆形 Bitmap
     */
    public static Bitmap roundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top = 0, right, bottom, dst_left = 0, dst_top = 0, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            bottom = height;
            width = height;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }


    /**
     * 旋转照片
     */
    public static Bitmap rotate(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        int width = img.getWidth();
        int height = img.getHeight();
        img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
        return img;
    }

    /**
     * 获得带倒影的图片方法
     *
     * @param bitmap
     * @return
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        // 图片与倒影间隔距离
        final int reflectionGap = 4;
        // 图片的宽度
        int width = bitmap.getWidth();
        // 图片的高度
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        // 图片缩放，x轴变为原来的1倍，y轴为-1倍,实现图片的反转
        matrix.preScale(1, -1);
        // 创建反转后的图片Bitmap对象，图片高是原图的一半。
        Bitmap reflectionImage = Bitmap.createBitmap(bitmap,
                0, height / 2, width, height / 2, matrix, false);
        // 创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍。 可以理解为这张图将会在屏幕上显示 是原图和倒影的合体
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);
        // 构造函数传入Bitmap对象，为了在图片上画图
        Canvas canvas = new Canvas(bitmapWithReflection);
        // 画原始图片
        canvas.drawBitmap(bitmap, 0, 0, null);
        // 画间隔矩形
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap,
                deafalutPaint);
        // 画倒影图片
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        // 实现倒影渐变效果
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
                bitmap.getHeight(), 0, bitmapWithReflection.getHeight()
                + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        // Set the Transfer mode to be porter duff and destination in
        // 覆盖效果
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }

    /**
     * 放大缩小图片
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbmp;
    }


    /**
     * 合成图片
     *
     * @param src              原图
     * @param watermark        水印图
     * @param xWatermarkOffset x轴水印图偏移
     * @param yWatermarkOffset y轴水印图偏移
     * @return
     */
    public static Bitmap synthesisBitmap(Bitmap src, Bitmap watermark, int xWatermarkOffset, int yWatermarkOffset) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();

        if (xWatermarkOffset < 0) {
            xWatermarkOffset = 0;
        }
        if (yWatermarkOffset < 0) {
            yWatermarkOffset = 0;
        }

        //create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_4444);//创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        //draw src into
        cv.drawBitmap(src, 0, 0, null);//在 0，0坐标开始画入src
        //draw watermark into
        cv.drawBitmap(watermark, xWatermarkOffset, yWatermarkOffset, null);//在src的右下角画入水印
        //save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);//保存
        //store
        cv.restore();//存储
        return newb;
    }

    /**
     * 图片透明度处理
     *
     * @param sourceImg 原始图片
     * @param number    透明度
     * @return
     */
    public static Bitmap setAlpha(Bitmap sourceImg, int number) {
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];
        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg.getWidth(), sourceImg.getHeight());// 获得图片的ARGB值
        number = number * 255 / 100;
        for (int i = 0; i < argb.length; i++) {
            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);// 修改最高2位的值
        }
        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg.getHeight(), Config.ARGB_8888);
        return sourceImg;
    }

    /**
     * 图标加灰色过滤；
     *
     * @param mDrawable
     * @return
     */
    public static Drawable setGray(Drawable mDrawable) {
        mDrawable.mutate();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(cm);
        mDrawable.setColorFilter(cf);
        return mDrawable;
    }

    /***
     * 图片平均分割方法，将大图平均分割为N行N列，方便用户使用
     *
     * @param g
     * ：画布
     * @param paint
     * ：画笔
     * @param imgBit
     * ：图片
     * @param x
     * ：X轴起点坐标
     * @param y
     * ：Y轴起点坐标
     * @param w
     * ：单一图片的宽度
     * @param h
     * ：单一图片的高度
     * @param line
     * ：第几列
     * @param row
     * ：第几行
     */
    public final void cuteImage(Canvas g, Paint paint, Bitmap imgBit, int x,
                                int y, int w, int h, int line, int row) {
        g.clipRect(x, y, x + w, h + y);
        g.drawBitmap(imgBit, x - line * w, y - row * h, paint);
        g.restore();
    }

    /***
     * 绘制带有边框的文字
     *
     * @param strMsg
     * ：绘制内容
     * @param g
     * ：画布
     * @param paint
     * ：画笔
     * @param setx
     * ：：X轴起始坐标
     * @param sety
     * ：Y轴的起始坐标
     * @param fg
     * ：前景色
     * @param bg
     * ：背景色
     */
    public void drawText(String strMsg, Canvas g, Paint paint, int setx,
                         int sety, int fg, int bg) {
        paint.setColor(bg);
        g.drawText(strMsg, setx + 1, sety, paint);
        g.drawText(strMsg, setx, sety - 1, paint);
        g.drawText(strMsg, setx, sety + 1, paint);
        g.drawText(strMsg, setx - 1, sety, paint);
        paint.setColor(fg);
        g.drawText(strMsg, setx, sety, paint);
        g.restore();
    }

    /**
     * 将彩色图转换为灰度图
     *
     * @param img 位图
     * @return 返回转换好的位图
     */
    public Bitmap convertGreyImg(Bitmap img) {
        int width = img.getWidth();         //获取位图的宽
        int height = img.getHeight();       //获取位图的高

        int[] pixels = new int[width * height]; //通过位图的大小创建像素点数组

        img.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);
        result.setPixels(pixels, 0, width, 0, 0, width, height);
        return result;
    }

    /**
     * 将一个图片切割成多个图片
     *
     * @param bitmap
     * @param xPiece
     * @param yPiece
     * @return
     */
    public static List<NYImagePiece> split(Bitmap bitmap, int xPiece, int yPiece) {
        List<NYImagePiece> pieces = new ArrayList<NYImagePiece>(xPiece * yPiece);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int pieceWidth = width / 3;
        int pieceHeight = height / 3;
        for (int i = 0; i < yPiece; i++) {
            for (int j = 0; j < xPiece; j++) {
                NYImagePiece piece = new NYImagePiece();
                piece.index = j + i * xPiece;
                int xValue = j * pieceWidth;
                int yValue = i * pieceHeight;
                piece.bitmap = Bitmap.createBitmap(bitmap, xValue, yValue,
                        pieceWidth, pieceHeight);
                pieces.add(piece);
            }
        }
        return pieces;
    }

    /**
     * 重新编码Bitmap
     *
     * @param src     需要重新编码的Bitmap
     * @param format  编码后的格式（目前只支持png和jpeg这两种格式）
     * @param quality 重新生成后的bitmap的质量
     * @return 返回重新生成后的bitmap
     */
    private static Bitmap codec(Bitmap src, Bitmap.CompressFormat format,
                                int quality) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        src.compress(format, quality, os);
        byte[] array = os.toByteArray();
        return BitmapFactory.decodeByteArray(array, 0, array.length);
    }


    public static class NYImagePiece {
        public int index = 0;
        public Bitmap bitmap = null;
    }
}
