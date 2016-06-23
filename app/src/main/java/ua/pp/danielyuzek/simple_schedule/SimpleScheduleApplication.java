package ua.pp.danielyuzek.simple_schedule;

import android.app.Application;
import android.content.res.Configuration;

import java.util.Locale;
import ua.pp.danielyuzek.simple_schedule.utilities.Const;

public class SimpleScheduleApplication extends Application {

    private static SimpleScheduleApplication mInstance;

    public static SimpleScheduleApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        setLocale(Const.DEFAULT_LOCALE);
    }

    private void setLocale(String localeCode) {
        Locale locale = new Locale(localeCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }
}
