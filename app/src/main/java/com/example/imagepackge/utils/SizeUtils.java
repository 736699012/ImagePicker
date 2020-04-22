package com.example.imagepackge.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

public class SizeUtils {


    public static Point getScreenSize(Context context){
        Point point  = new Point();
        //获取屏幕尺寸的
        ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
        return point;
    }
}
