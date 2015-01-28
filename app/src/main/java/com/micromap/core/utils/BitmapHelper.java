package com.micromap.core.utils;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by pingfu on 15/1/14.
 */
public class BitmapHelper {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context  系统的context
     * @param dpValue  转化前的dp值
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context  系统的context
     * @param dpValue  转化前的dp值
     */
    public static float dip2pxF(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context  系统的context
     * @param pxValue  转化前的px值
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context  系统的context
     * @param pxValue  转化前的px值
     */
    public static float px2dipF(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return pxValue / scale + 0.5f;
    }

    public static Bitmap getDpSizeBitmap(Context context, Bitmap bitmap){
        if(bitmap == null){
            return null;
        }
        int width = dip2px(context, bitmap.getWidth());
        int height = dip2px(context, bitmap.getHeight());
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);


        return newBitmap;
    }
}
