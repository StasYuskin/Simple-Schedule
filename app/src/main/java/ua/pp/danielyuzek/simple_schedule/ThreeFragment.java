package ua.pp.danielyuzek.simple_schedule;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by daniel on 08/06/16.
 */
public class ThreeFragment extends Fragment {

    public ThreeFragment(){

    }
    @Override
    public void onCreate (Bundle savedInstanceState) {
        super onCreate(savedInstanceState);
    }

    @Override
    public void View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_three, container,false);
    }
}
