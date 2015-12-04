package ppm.uqac.com.geekproject.geekactivity;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.R;

/**
 * Created by Arnaud on 18/10/2015.
 * Cette classe est l'Adapter de GA. Elle permet d'afficher les diférentes GA dans la classe ViewListActivity
 */
public class GAAdapter extends BaseAdapter
{
    // Notre liste de GA
    private ArrayList<GA> listActivity;

    //Le contexte dans lequel est présent notre adapter
    private Context mContext;

    //Un mécanisme pour gérer l'affichage graphique depuis un layout XML
    private LayoutInflater mInflater;

    private TextView tv_name;

    private TextView tv_description;

    private TextView tv_level;

    private TextView tv_exp;

    private CheckBox cb_activity;

    private boolean firstTime = false;

    private TextView tv_url;

    public GAAdapter(Context context, ArrayList<GA> lista)
    {
        mContext=context;
        listActivity=lista;
        mInflater = LayoutInflater.from(mContext);
    }
    public int getCount()
    {
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

        tv_name = (TextView) layoutItem.findViewById(R.id.TV_NameActivity);
        tv_description = (TextView) layoutItem.findViewById(R.id.TV_DescriptionActivity);
        tv_level = (TextView) layoutItem.findViewById(R.id.TV_LevelActivity);
        tv_exp = (TextView) layoutItem.findViewById(R.id.TV_experienceActivity);
        cb_activity = (CheckBox) layoutItem.findViewById(R.id.checkBox);
        tv_url = (TextView) layoutItem.findViewById(R.id.TV_UrlActivity);
        // renseignement des nouveaux champs

        tv_name.setText(listActivity.get(position).get_name());
        tv_description.setText(listActivity.get(position).get_description());
        String lvl ="Level : " + Integer.toString(listActivity.get(position).get_level());
        tv_level.setText(lvl);
        String xp = "Experience : " + Integer.toString(listActivity.get(position).get_experience());
        tv_exp.setText(xp);
        System.out.println("GA adapter url : " + listActivity.get(position).get_url());
        tv_url.setText("");
        if (listActivity.get(position).get_url().length() !=0)
        {
            if(listActivity.get(position).get_name().equals("Installer l application AppyGeek"))
            {
                tv_url.setText("Cliquer ici pour télécharger l'application");
            }
            else if (listActivity.get(position).get_name().equals("Regarder une vidéo Culture GEEK"))
                tv_url.setText("Cliquer ici pour ouvrir la vidéo");
            tv_url.setPaintFlags(tv_url.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }


        if(!listActivity.get(position).get_isDone())
        {
            cb_activity.setChecked(false);
            if(firstTime)
                cb_activity.setEnabled(false);
            else if (!firstTime)
                cb_activity.setEnabled(true);
        }
        else if (listActivity.get(position).get_isDone())
        {
            cb_activity.setChecked(true);
            cb_activity.setEnabled(false);
        }
        return layoutItem;
    }

    public GA getItem(int position)
    {
        return listActivity.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public void updateListView(ArrayList<GA> lista)
    {
        listActivity = lista;
    }

    public void setFirstTime(boolean first)
    {
        firstTime = first;
    }
}
