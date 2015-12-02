package ppm.uqac.com.geekproject.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.R;

/**
 * Created by Valentin on 22/11/2015.
 */
public class AdapterBadges extends BaseAdapter
{
    private ArrayList<Badge> _listBadges; // liste des badges (dont les informations sont contenues dans la classe Badge
    private Context _context; // contexte dans lequel est présenté notre adapteur
    private LayoutInflater _inflater; // mécanisme qui permet d'afficher les différentes éléments graphiques depuis un layout XML

    /**
     * Constructeur de la classe AdapterBadges
     *
     */
    public AdapterBadges(Context c, ArrayList<Badge> l)
    {
        _context = c;
        _listBadges = l;
        _inflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount()
    {
        return _listBadges.size();
    }

    @Override
    public Object getItem(int position)
    {
        return _listBadges.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    /**
     * Gère l'affichage de chaque badge
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        System.out.println("In Adapter.getView");
        RelativeLayout l;

        // On récupère le layout lié à notre Badge

        if (convertView == null)
            l = (RelativeLayout) _inflater.inflate(R.layout.badge_layout, parent, false);
        else
            l = (RelativeLayout) convertView;

        ImageView image = (ImageView) l.findViewById(R.id.badge_image);
        TextView name = (TextView) l.findViewById(R.id.badge_name);
        TextView description = (TextView) l.findViewById(R.id.badge_description);
        CheckBox got = (CheckBox) l.findViewById(R.id.checkBox_got);

        image.setImageResource(_listBadges.get(position).getImage());
        name.setText(_listBadges.get(position).getName());
        description.setText(_listBadges.get(position).getDescription());
        got.setChecked(_listBadges.get(position).isGot());

        // On récupère les informations grâce à la position passée
        // en paramètre

        return l;

    }
}
