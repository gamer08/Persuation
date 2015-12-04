package ppm.uqac.com.geekproject.geeklopedie;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import ppm.uqac.com.geekproject.R;

/**
 * Created by Simon on 23/10/2015.
 */
public class Fragment_1Mefiant extends Fragment
{
    View rootview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootview = inflater.inflate(R.layout.fragment_1mefiant,container,false);
        return rootview;
    }




}

