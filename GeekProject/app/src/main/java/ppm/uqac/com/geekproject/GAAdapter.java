package ppm.uqac.com.geekproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Arnaud on 18/10/2015.
 * Cette classe est l'Adapter de GA. Elle permet d'afficher les diférentes GA dans la classe ViewListActivity
 */
public class GAAdapter extends BaseAdapter {



    // Notre liste de GA
    private ArrayList<GA> listActivity;

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;


    public GAAdapter(Context context, ArrayList<GA> lista)
    {
        mContext=context;
        listActivity=lista;
        mInflater = LayoutInflater.from(mContext);
    }
    public int getCount() {
        return listActivity.size();
    }

    /**
     * Permet de faire le layout pour une GA
     * @param position : position de la GA dans l'arrayList
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
            // Initialisation du layoutItem à partir du layout XML GA_layout.xml
            layoutItem = (LinearLayout) mInflater.inflate(R.layout.ga_layout, parent, false);
        }
        else
            layoutItem = (LinearLayout) convertView;

        // Récupération des différents champs du layout

        TextView tv_name = (TextView) layoutItem.findViewById(R.id.TV_NameActivity);
        TextView tv_description = (TextView) layoutItem.findViewById(R.id.TV_DescriptionActivity);
        TextView tv_level = (TextView) layoutItem.findViewById(R.id.TV_LevelActivity);
        TextView tv_exp = (TextView) layoutItem.findViewById(R.id.TV_experienceActivity);

        // renseignement des nouveaux champs

        tv_name.setText(listActivity.get(position).get_name());
        tv_description.setText(listActivity.get(position).get_description());
        String lvl ="level : " + Integer.toString(listActivity.get(position).get_level());
        tv_level.setText(lvl);
        String xp = "Experience : " + Integer.toString(listActivity.get(position).get_experience());
        tv_exp.setText(xp);

        return layoutItem;

    }


    public GA getItem(int position) {
        return listActivity.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


}
