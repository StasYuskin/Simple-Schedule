package ua.pp.danielyuzek.simple_schedule.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ua.pp.danielyuzek.simple_schedule.SimpleScheduleApplication;
import ua.pp.danielyuzek.simple_schedule.models.Data;

public class UserSettings {

    private static final String TAG = UserSettings.class.getSimpleName();

    private static final String PREF_NAME = "simple-schedule-data";

    public static final String DATA_JSON = "data_json";
    public static final String FIRST_APP_RUN = "first_app_run";
    public static final String USER_GROUP = "user_group";

    public static final String BASE_URL = "base_url";

    private static SharedPreferences sPref;
    private static SharedPreferences.Editor edit;

    private static Data mCachedData;

    private UserSettings() {
    }

    public static void init() {
        sPref = SimpleScheduleApplication.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        edit = sPref.edit();
        edit.apply();
    }

    public static SharedPreferences getPrefs() {
        if (edit == null) {
            init();
        }
        return sPref;
    }

    public static void setStringSet(String key, Set<String> value) {
        if (edit == null) {
            init();
        }

        Logger.i(TAG, "setString " + key + "=" + value);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            edit.putStringSet(key, value);
            edit.commit();
        }
    }

    public static Set<String> getStringSet(String key) {
        if (edit == null) {
            init();
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            return sPref.getStringSet(key, new HashSet<String>());
        }
        return Collections.emptySet();
    }

    public static void setString(String key, String value) {
        if (edit == null) {
            init();
        }

        Logger.i(TAG, "setString " + key + "=" + value);
        edit.putString(key, value);
        edit.commit();
    }

//    public static void setTireStation(String key, TireStation station) {
//        if (edit == null) {
//            init(DriverApplication.getInstance());
//        }
//
//        Logger.i(TAG, "setString " + key + "=" + new Gson().toJson(station));
//        edit.putString(key, new Gson().toJson(station));
//        edit.commit();
//    }
//
//    public static TireStation getTireStation(String key) {
//        if (edit == null) {
//            init(DriverApplication.getInstance());
//        }
//
//        String user = getString(key);
//        Logger.i(TAG, "setString " + key + "=" + user);
//        return new Gson().fromJson(getString(key), TireStation.class);
//    }


    public static void setLong(String key, long value) {
        if (edit == null) {
            init();
        }

        Logger.i(TAG, "setLong " + key + "=" + value);
        edit.putLong(key, value);
        edit.commit();
    }

    public static void setBoolean(String key, boolean value) {
        if (edit == null) {
            init();
        }

        Logger.i(TAG, "setBoolean " + key + "=" + value);
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static void setInt(String key, int value) {
        if (edit == null) {
            init();
        }

        Logger.i(TAG, "setInt " + key + "=" + value);
        edit.putInt(key, value);
        edit.commit();
    }

    public static void setPrefFloat(String key, float value) {
        if (edit == null) {
            init();
        }

        Logger.i(TAG, "setPrefFloat " + key + "=" + value);
        edit.putFloat(key, value);
        edit.commit();
    }

    public static String getString(String key) {
        if (sPref == null) {
            init();
        }

        return sPref.getString(key, "");
    }

    public static String getString(String key, String def) {
        if (sPref == null) {
            init();
        }

        return sPref.getString(key, def);
    }

    public static boolean getBoolean(String key) {
        if (sPref == null) {
            init();
        }

        return sPref.getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        if (sPref == null) {
            init();
        }

        return sPref.getBoolean(key, defValue);
    }

    public static long getLong(String key) {
        if (sPref == null) {
            init();
        }

        return sPref.getLong(key, 0);
    }

    public static int getInt(String key) {
        if (sPref == null) {
            init();
        }

        return sPref.getInt(key, -1);
    }

    public static int getInt(String key, int def) {
        if (sPref == null) {
            init();
        }

        return sPref.getInt(key, def);
    }

    public static float getFloat(String key) {
        if (sPref == null) {
            init();
        }

        return sPref.getFloat(key, -1);
    }


    public static void setData (Data data) {
        if (edit == null) {
            init();
        }

        if (data != null) {
            mCachedData = data;
            String key = DATA_JSON;
            Logger.i(TAG, "setString " + key + "=" + new Gson().toJson(data));
            edit.putString(key, new Gson().toJson(data));
            edit.commit();
        }
    }

    public static Data getData() {
        if (edit == null) {
            init();
        }

        if (mCachedData == null) {
            String key = DATA_JSON;
            String user = getString(key);
            Logger.i(TAG, "getString " + key + "=" + user);
            mCachedData = new Gson().fromJson(getString(key), Data.class);
        }

        return mCachedData;
    }

}