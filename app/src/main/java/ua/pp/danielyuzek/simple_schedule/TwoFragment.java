package ua.pp.danielyuzek.simple_schedule;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import ua.pp.danielyuzek.simple_schedule.R;

/**
 * Created by daniel on 08/06/16.
 */
public class TwoFragment extends Fragment{

    public TwoFragment(){

    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two, container, false);
    }
 }
