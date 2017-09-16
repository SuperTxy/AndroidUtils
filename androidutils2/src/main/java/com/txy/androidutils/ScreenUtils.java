package com.txy.androidutils;

import android.content.Context;
import android.view.View;

/**
 * Created by Apple on 17/9/11.
 */

public class ScreenUtils {

    public static int dp2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (density * dp + 0.5);
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public int getStatusBarHeight(Context context) {
        int statusBarHeight = -1;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) statusBarHeight = context.getResources().getDimensionPixelOffset(resId);
        return statusBarHeight;
    }

    public View getView(int layoutId, Context context) {
        return View.inflate(context, layoutId, null);
    }
}
