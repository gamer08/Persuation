package ppm.uqac.com.geekproject.geeklopedie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ppm.uqac.com.geekproject.R;

/**
 * Created by Simon on 23/10/2015.
 */
public class Fragment_5 extends Fragment
{

    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootview = inflater.inflate(R.layout.fragment_5,container,false);
        return rootview;
    }
}
