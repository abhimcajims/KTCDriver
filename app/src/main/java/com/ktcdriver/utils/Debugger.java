package com.ktcdriver.utils;

import android.util.Log;

public class Debugger {

    public static boolean isActive = true;

    public static void e(String tag, String msg) {
        if (isActive)
            Log.e(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isActive)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isActive)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isActive)
            Log.v(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isActive)
            Log.w(tag, msg);
    }
}
