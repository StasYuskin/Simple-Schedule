package ua.pp.danielyuzek.simple_schedule.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import ua.pp.danielyuzek.simple_schedule.R;
import ua.pp.danielyuzek.simple_schedule.SimpleScheduleApplication;
import ua.pp.danielyuzek.simple_schedule.adapters.LessonsAdapter;
import ua.pp.danielyuzek.simple_schedule.models.Day;
import ua.pp.danielyuzek.simple_schedule.models.Week;
import ua.pp.danielyuzek.simple_schedule.utilities.Const;
import ua.pp.danielyuzek.simple_schedule.utilities.Logger;

/**
 * Created by daniel on 08/06/16.
 */
public class DayFragment extends Fragment implements UpdatableFragment {

    public static String WEEK_DATA = "week_data";
    public static String WEEK = "week";
    public static String DAY = "day";

    View mView;
    private Week mWeek;
    private Integer mWeekNumber;
    private Integer mDay;

    private TextView mTitle;
    private ListView mList;
    private LessonsAdapter mLessonsAdapter;

    /**
     * Returns a new instance of this fragment
     */
    public static DayFragment newInstance(Week weekData, int week, int day) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putSerializable(WEEK_DATA, weekData);
        args.putInt(WEEK, week);
        args.putInt(DAY, day);
        fragment.setArguments(args);
        return fragment;
    }

    public DayFragment() {
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_day, container, false);

        extractBundle();

        mTitle = (TextView) mView.findViewById(R.id.schedule_title);

        setTitle();

        mList = (ListView) mView.findViewById(R.id.schedule_list);
        mLessonsAdapter = new LessonsAdapter(getActivity(), mWeek.getDays().get(mDay).getLessons());
        mList.setAdapter(mLessonsAdapter);

        return mView;
    }

    private void setTitle() {
        Calendar calendar = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
//        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.WEEK_OF_YEAR, mWeekNumber);
        calendar.set(Calendar.DAY_OF_WEEK, mDay+2);

        Date date =  calendar.getTime();
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formater.format(date);

        if (mTitle != null) {
            mTitle.setText(dateString);
            mTitle.setVisibility(View.VISIBLE);
        }
    }

    private void extractBundle() {
        Bundle data = getArguments();
        if (data.containsKey(DAY)) {
            mWeek = (Week) data.getSerializable(WEEK_DATA);
            mWeekNumber = data.getInt(WEEK);
            mDay = data.getInt(DAY);
        }
    }

    @Override
    public void update(Week week) {
        if (week != null) {
            if (mLessonsAdapter != null) {
                mLessonsAdapter.setData(mWeek.getDays().get(mDay).getLessons());
            }
            setTitle();
        }
    }
}
