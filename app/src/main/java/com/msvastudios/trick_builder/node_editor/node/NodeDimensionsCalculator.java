package com.msvastudios.trick_builder.node_editor.node;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class NodeDimensionsCalculator {

    static int screenHeight, screenWidth;
    private static boolean initialized = false;


    private NodeDimensionsCalculator(){}

    static void init(Context context){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        initialized = true;
    }


    public static int nodeWidth(){
        if (!isInitialized()){
            return -1;
        }
        return (int) (screenWidth / 2.16);
    }

    public static int nodeItemHeight(){
        if (!isInitialized()){
            return -1;
        }
        return nodeWidth() / 5;
    }

    public static int innerNodeMargin(){
        if (!isInitialized()){
            return -1;
        }
        return nodeWidth() / 10;
    }

    public static int getInnerNodeWidth(){
        if (!isInitialized()){
            return -1;
        }
        return nodeWidth() - innerNodeMargin();
    }



    private static int statusBarHeight = -1;
    public static int getStatusBarHeight(Context context) {
        if (statusBarHeight != -1) return statusBarHeight;

        int height = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }

        statusBarHeight = height;
        return statusBarHeight;
    }

    public static boolean isInitialized() {
        return initialized;
    }


}
