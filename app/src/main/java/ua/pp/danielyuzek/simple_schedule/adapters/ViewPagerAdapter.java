package ua.pp.danielyuzek.simple_schedule.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import ua.pp.danielyuzek.simple_schedule.fragments.DayFragment;
import ua.pp.danielyuzek.simple_schedule.models.Week;
import ua.pp.danielyuzek.simple_schedule.utilities.Const;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Integer mWeekNumber;
    private Week mWeek;

    public ViewPagerAdapter(FragmentManager manager, Week week, Integer weekNumber) {
        super(manager);
        this.mWeekNumber = weekNumber;
        this.mWeek = week;
    }

    //call this method to update fragments in ViewPager dynamically
    public void update(Week week, Integer weekNumber) {
        if (week != null) {
            this.mWeek = week;
            this.mWeekNumber = weekNumber;
            notifyDataSetChanged();
        }
    }

    @Override
    public DayFragment getItem(int position) {
        return DayFragment.newInstance(mWeek, mWeekNumber, position);
    }

    public int getCount() {
        return (mWeek != null && mWeek.getDays() != null) ? mWeek.getDays().size() : 0;
    }

    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Calendar calendar = new GregorianCalendar(new Locale(Const.DEFAULT_LOCALE));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.WEEK_OF_YEAR, mWeekNumber);
        calendar.set(Calendar.DAY_OF_WEEK, position+2);

        Date date =  calendar.getTime();
        SimpleDateFormat formater = new SimpleDateFormat("E");
        String dateString = formater.format(date);

        return dateString;

//        return mWeek.getDays().get(position).getName();
    }

}