package ppm.uqac.com.geekproject.geeklopedie;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ppm.uqac.com.geekproject.R;

/**
 * Created by Simon on 23/10/2015.
 */
public class Fragment_main extends Fragment
{
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootview = inflater.inflate(R.layout.fragment_main,container,false);

        TextView textView = (TextView) rootview.findViewById(R.id.textViewGeeklopedie);
        TextView textView2 = (TextView) rootview.findViewById(R.id.TV_finger);

        // Font
        Typeface typeFace= Typeface.createFromAsset(rootview.getContext().getAssets(), "octapost.ttf");
        textView.setTypeface(typeFace);
        textView2.setTypeface(typeFace);


        return rootview;
    }
}
