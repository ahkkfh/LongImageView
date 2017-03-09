package com.marks.longview;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/***
 * @author marks.luo
 * @Description: TODO(android中帮助类，用于宽和高)
 * @date:2017-03-08 16:36
 */
public class AndroidUtils {
    private static int width = 0;
    private static int height = 0;

    private static void getDisplay(Context context) {
        if (width <= 0 || height <= 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            width = dm.widthPixels;
            height = dm.heightPixels;
        }
    }

    public static int getScreenWidth(Context context) {
        getDisplay(context);
        return width;
    }

    public static int getScreenHeight(Context context) {
        getDisplay(context);
        return height;
    }
}
