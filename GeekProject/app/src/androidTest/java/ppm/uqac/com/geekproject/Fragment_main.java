package ppm.uqac.com.geekproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Simon on 23/10/2015.
 */
public class Fragment_main extends Fragment {

    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.geeklopedie_fragment_main,container,false);
        return rootview;
    }
}
