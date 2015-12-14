package ppm.uqac.com.geekproject.profile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ppm.uqac.com.geekproject.R;

/**
 * Created by Valentin on 22/11/2015.
 */
public class Fragment_Badges extends Fragment
{
    View _rootview;
    ListView _lvBadges;
    AdapterBadges _adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _rootview = inflater.inflate(R.layout.fragment_badges,container,false);
        _lvBadges = (ListView) _rootview.findViewById(R.id.listBadges);
        _lvBadges.setAdapter(_adapter);
        return _rootview;
    }


    public void setAdapter (AdapterBadges a) { _adapter = a; }
}
