package ppm.uqac.com.geekproject.geeklopedie;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.R;

/**
 * Created by Simon on 23/10/2015.
 */
public class Fragment_1Mistrustful extends Fragment
{
    View rootview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootview = inflater.inflate(R.layout.fragment_1mistrustful,container,false);

        // Font
        Typeface typeFace= Typeface.createFromAsset(getActivity().getAssets(), "octapost.ttf");

        TextView tv1 = (TextView) rootview.findViewById(R.id.title_mistrustful);
        TextView tv2 = (TextView) rootview.findViewById(R.id.mistrustful_description);
        TextView tv3 = (TextView) rootview.findViewById(R.id.TV_advice);
        TextView tv4 = (TextView) rootview.findViewById(R.id.mistrustful_advice);

        ArrayList<TextView> a = new ArrayList<>();
        a.add(tv1);
        a.add(tv2);
        a.add(tv3);
        a.add(tv4);

        for (TextView tv : a)
        {
            tv.setTypeface(typeFace);
        }

        return rootview;
    }




}

