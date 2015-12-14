package ppm.uqac.com.geekproject.geeklopedie;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ppm.uqac.com.geekproject.R;

/**
 * Created by Simon on 17/11/2015.
 */

public class Fragment_Video extends Fragment
{
    View rootview;
    AdapterVideo _cadapter;
    ListView _VideoListView;

    public void Fragment_Video()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootview = inflater.inflate(R.layout.fragment_video,container,false);
        _VideoListView = (ListView) rootview.findViewById(R.id.drawerListVideo);
        _VideoListView.setAdapter(_cadapter);

        _VideoListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ItemContent c = _cadapter.getItem(position);
                Uri uri = Uri.parse("https://www.youtube.com/watch?v="+c.get_url()); // Si l'url ne contient pas http:// l'appli plante
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        return rootview;
    }

    public void setData(AdapterVideo cAdapter)
    {
        _cadapter = cAdapter;
        System.out.println(_cadapter);
    }
}