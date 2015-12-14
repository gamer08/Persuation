package ppm.uqac.com.geekproject.geekactivity;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ppm.uqac.com.geekproject.R;

public class Fragment_GA extends Fragment
{
    View rootview;
    GAAdapter _gadapter;
    ListView _ContentListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_activities_doing, container, false);
        _ContentListView = (ListView) rootview.findViewById(R.id.ListView01);

        _ContentListView.setAdapter(_gadapter);

        return rootview;
    }
    void set_gadapter(GAAdapter adapter)
    {
        _gadapter = adapter;
    }

    public ListView get_ContentListView()
    {
        return _ContentListView;
    }
}