package ppm.uqac.com.geekproject.geeklopedie;

/**
 * Created by Simon on 21/10/2015.
 */


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.R;


public class ContentAdapter extends BaseAdapter {



    // Liste pour gérer tout le contenu
    private ArrayList<Content> listContent;


    private Context mContext;

    //Sert pour afficher le layout
    private LayoutInflater mInflater;


    public ContentAdapter(Context context, ArrayList<Content> list)
    {
        mContext=context;
        listContent =list;
        mInflater = LayoutInflater.from(mContext);
    }
    public int getCount() {
        return listContent.size();
    }

    /**
     * gère l'affichage de chaque contenu
     * @param position : position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LinearLayout layoutItem;

        // Récupération du layout lié à notre GA
        if(convertView==null)
        {
            // Initialisation à partir du fichier Content_layout.xml
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.content_layout, parent, false);
        }
        else
            layoutItem = (LinearLayout) convertView;

        // On récupère les différents champs

        TextView tv_name = (TextView) layoutItem.findViewById(R.id.TV_NameActivity);
        TextView tv_description = (TextView) layoutItem.findViewById(R.id.TV_DescriptionActivity);

        tv_name.setText(listContent.get(position).get_name());
        tv_description.setText(listContent.get(position).get_description());

        return layoutItem;

    }


    public Content getItem(int position) {
        return listContent.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


}