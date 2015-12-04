package ppm.uqac.com.geekproject.geeklopedie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.R;

/**
 * Created by Valentin on 02/12/2015.
 */
public class AdapterWiki extends BaseAdapter
{

    // Liste pour gérer tout le contenu
    private ArrayList<ItemWiki> _listWiki;

    private Context mContext;

    //Sert pour afficher le layout
    private LayoutInflater mInflater;

    public AdapterWiki(Context context, ArrayList<ItemWiki> list)
    {
        mContext=context;
        _listWiki =list;
        mInflater = LayoutInflater.from(mContext);
    }

    public int getCount()
    {
        return _listWiki.size();
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
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.wiki_layout, parent, false);
        }
        else
            layoutItem = (LinearLayout) convertView;

        // On récupère les différents champs

        TextView tv_name = (TextView) layoutItem.findViewById(R.id.name_wiki);
        TextView tv_definition = (TextView) layoutItem.findViewById(R.id.definition_wiki);


        return layoutItem;
    }


    public ItemWiki getItem(int position)
    {
        return  _listWiki.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }
}
