package ua.pp.danielyuzek.simple_schedule.utilities;

import android.util.Log;

public class Logger {

    public static final boolean DEBUG = true;

    public static void i(String text, String message) {
        if (DEBUG) {
            Log.i(text, message);
        }
    }

    public static void w(String text, String message) {
        if (DEBUG) {
            Log.w(text, message);
        }
    }

    public static void e(String text, String message) {
        if (DEBUG) {
            Log.e(text, message);
        }
    }

    public static void d(String text, String message) {
        if (DEBUG) {
            Log.d(text, message);
        }
    }
}
