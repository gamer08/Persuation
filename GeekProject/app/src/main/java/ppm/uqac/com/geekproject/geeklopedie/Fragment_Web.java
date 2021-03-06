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
 * Created by Simon on 23/10/2015.
 */
public class Fragment_Web extends Fragment
{
    View rootview;
    AdapterContent _cadapter;
    ListView _ContentListView;

    public void Fragment_6()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootview = inflater.inflate(R.layout.fragment_6,container,false);
        _ContentListView = (ListView) rootview.findViewById(R.id.drawerListgeek);
        _ContentListView.setAdapter(_cadapter);

        _ContentListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ItemContent c = _cadapter.getItem(position);
                Uri uri = Uri.parse(c.get_url()); // Si l'url ne contient pas http:// l'appli plante
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        return rootview;
    }

    public void setData(AdapterContent cAdapter)
    {
        _cadapter = cAdapter;
        System.out.println(_cadapter);
    }
}
