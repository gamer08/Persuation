package ppm.uqac.com.geekproject.geeklopedie;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import ppm.uqac.com.geekproject.R;

/**
 * Created by Valentin on 02/12/2015.
 */
public class Fragment_Wiki extends Fragment
{
    View rootview;
    ListView _lvWiki;
    AdapterWiki _adapter;

  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootview = inflater.inflate(R.layout.fragment_wiki,container,false);
        _lvWiki = (ListView) rootview.findViewById(R.id.wikilist);
        _lvWiki.setAdapter(_adapter);

        return rootview;
    }

    public void setData(AdapterWiki adapter)
    {
        _adapter = adapter;

    }
}