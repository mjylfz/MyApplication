package utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by LFZ on 2017/7/16.
 * 工具类
 */

public class IFHelper {

    public static int dp2px(Context context,float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(scale * dpValue +0.5f);
    }


    public static boolean isNavigationOpen(Activity context){
        return getNavigationHeight(context) > 0;
    }


    /**
     * 获取是否存在NavigationBar
     * @param context
     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }


    /**
     * 第一种方法
     * @param context
     * @return
     */
    public static int getNavigationHeight(Context context){
        int vh = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            Class c = Class.forName("android.view.Display");
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            if(isLandScape(context)){
                vh = dm.widthPixels - windowManager.getDefaultDisplay().getWidth();
                Toast.makeText(context,"dm.widthPixels " + dm.widthPixels + " "+ "width1 " + windowManager.getDefaultDisplay().getWidth() + " " +"width2 " + getScreenWidth(context),Toast.LENGTH_LONG).show();
            }else if(isPortrait(context)){
                vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }


    public static int getNavigationHeight(Activity context){
//        Display display = context.getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        Point realSize = new Point();
//        display.getSize(size);
//        display.getRealSize(realSize);
//        if(isPortrait(context)){
//            return realSize.y - size.y;
//        }else{
//            return realSize.x - size.x;
//        }
        View view  = context.findViewById(android.R.id.navigationBarBackground);
        if(view == null){
            return 0;
        }
        int navigationBarSize = isPortrait(context) ? view.getHeight() : view.getWidth();
        int visisble = view.getVisibility();
        if(visisble == View.GONE){
            return 0 ;
        }else if (visisble == View.INVISIBLE){
            return 0;
        }else{
        }
        return navigationBarSize;
//        return context.getResources().getDimensionPixelSize(com.android.internal.R.dimen.navigation_bar_height);
    }

    /**
     * 暂时可行
     * @param context
     * @return
     */
    public static boolean isNavigationBarShow(Activity context){
        int systemUiVisibility = context.getWindow().getDecorView().getSystemUiVisibility();
        return getNavigationHeight(context) > 0;
    }


    /**
     * 返回屏幕宽度
     *
     * @param context 上下文
     * @return 返回宽度
     */
    public static int getScreenWidth(Context context) {
        if (context == null) {
            return 0;
        }

        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 返回屏幕高度
     *
     * @param context 上下文
     * @return 返回高度
     */
    public static int getScreenHeight(Context context) {
        if (context == null) {
            return 0;
        }
        return context.getResources().getDisplayMetrics().heightPixels;
    }
    /**
     * 获取屏幕物理高度，无论是否有虚拟键
     * @param context 上下文
     * @return 返回高度
     */
    public static int getRealScreenHeight(Activity context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (context == null) {
                return 0;
            }

            WindowManager wm = (context).getWindowManager();
            Display d = wm.getDefaultDisplay();


            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);
            int realHeight = realDisplayMetrics.heightPixels;
            return realHeight;
        }else{
            return getScreenHeight(context);
        }
    }


    /**
     * 获取屏幕物理宽度，无论是否有虚拟键
     * @param context 上下文
     * @return 返回高度
     */
    public static int getRealScreenWidth(Activity context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (context == null) {
                return 0;
            }

            WindowManager wm = (context).getWindowManager();
            Display d = wm.getDefaultDisplay();


            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);
            int realWidth = realDisplayMetrics.widthPixels;
            return realWidth;
        }else{
            return getScreenWidth(context);
        }
    }

    /**
     * 获取除去虚拟键的可用屏幕高度
     * @param context 上下文
     * @return 返回高度
     */
    public static int getContentHeight(Activity context){
        int contentHeight;
        if(isPortrait(context)){
            contentHeight = getRealScreenHeight(context) - getNavigationHeight(context);
        }else{
            contentHeight = getRealScreenHeight(context);
        }
        return contentHeight;
    }

    /**
     * 获取除去虚拟键的可用屏幕宽度
     * @param context 上下文
     * @return 返回宽度
     */
    public static int getContentWidth(Activity context){
        int contentWidth;
        if(isLandScape(context)){
            contentWidth = getRealScreenWidth(context) - getNavigationHeight(context);
        }else{
            contentWidth = getRealScreenWidth(context);
        }
        return contentWidth;
    }

    public static int getOrientation(Context context){
        return context.getResources().getConfiguration().orientation;
    }

    public static boolean isLandScape(Context context){
        return getOrientation(context) == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static boolean isPortrait(Context context){
        return getOrientation(context) == Configuration.ORIENTATION_PORTRAIT;
    }



    public static boolean isHideNavigationBar(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            WindowManager wm = ((Activity) context).getWindowManager();
            Display d = wm.getDefaultDisplay();


            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);


            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;


            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);


            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;


            return (realWidth - displayWidth) > 0 || (realHeight - displayHeight) > 0;
        } else {
            return false;
        }
    }



    public static int getNavigationBarHeight(@NonNull Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height","dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
