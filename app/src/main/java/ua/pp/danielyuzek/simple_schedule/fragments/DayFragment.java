package ua.pp.danielyuzek.simple_schedule.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ua.pp.danielyuzek.simple_schedule.R;
import ua.pp.danielyuzek.simple_schedule.adapters.LessonsAdapter;
import ua.pp.danielyuzek.simple_schedule.models.Day;

/**
 * Created by daniel on 08/06/16.
 */
public class DayFragment extends Fragment {

    public static String DAY = "day";

    View mView;
    private Day mDay;

    /**
     * Returns a new instance of this fragment
     */
    public static DayFragment newInstance(Day day) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putSerializable(DAY, day);
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

        TextView title = (TextView) mView.findViewById(R.id.schedule_title);

        //todo get true date of that day
        Date date = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("dd/mm/yyyy");
        String dateString = formater.format(date);

        title.setText(dateString);
        title.setVisibility(View.VISIBLE);

        ListView list = (ListView) mView.findViewById(R.id.schedule_list);
        LessonsAdapter lessonsAdapter = new LessonsAdapter(getActivity(), mDay.getLessons());
        list.setAdapter(lessonsAdapter);

        return mView;
    }

    private void extractBundle() {
        Bundle data = getArguments();
        if (data.containsKey(DAY)) {
            mDay = (Day) data.getSerializable(DAY);
        }
    }
}
