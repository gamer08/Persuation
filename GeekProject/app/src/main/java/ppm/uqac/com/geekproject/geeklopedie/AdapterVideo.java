package ppm.uqac.com.geekproject.geeklopedie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.R;

/**
 * Created by Simon on 17/11/2015.
 */
public class AdapterVideo extends BaseAdapter {

    // Liste pour gérer tout le contenu
    private ArrayList<ItemContent> listVideo;


    private Context mContext;

    //Sert pour afficher le layout
    private LayoutInflater mInflater;


    public AdapterVideo(Context context, ArrayList<ItemContent> list)
    {
        mContext=context;
        listVideo =list;
        mInflater = LayoutInflater.from(mContext);
    }


    /**
     * gère l'affichage de chaque contenu
     * @param position : position
     * @param convertView
     * @param parent
     * @return
    **/

    public View getView(int position, View convertView, ViewGroup parent)
    {
        RelativeLayout layoutItem;

        // Récupération du layout lié à notre GA
        if(convertView==null)
        {
            // Initialisation à partir du fichier Content_layout.xml
            layoutItem = (RelativeLayout) mInflater.inflate(R.layout.video_layout, parent, false);
        }
        else
            layoutItem = (RelativeLayout) convertView;

        // On récupère les différents champs

        TextView tv_name = (TextView) layoutItem.findViewById(R.id.TV_NameActivity);
        TextView tv_description = (TextView) layoutItem.findViewById(R.id.TV_DescriptionActivity);
        ImageView iv_img = (ImageView) layoutItem.findViewById(R.id.IM_image);

        tv_name.setText(listVideo.get(position).get_name());
        tv_description.setText(listVideo.get(position).get_description());

        String name_img = listVideo.get(position).get_url();
        System.out.println(name_img);
        name_img = name_img.toLowerCase();
        System.out.println(name_img);
        switch (name_img)
        {
            case "vu0hjo52xpu":
                iv_img.setImageResource(R.drawable.linkthesun);
                break;
            case "wzjvkygubsi":
                iv_img.setImageResource(R.drawable.cyprienlesgeeks);
                break;
            case "2tvy_pbe5na":
                iv_img.setImageResource(R.drawable.nerdvsgeek);
                break;
            default:
                iv_img.setImageResource(R.drawable.linkthesun);
                break;
        }

        return layoutItem;
    }

    /*public Bitmap getBitmapFromURL(String src)
    {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            InputStream input = connection.getInputStream();
            Bitmap result = BitmapFactory.decodeStream(input);
            return result;
        }catch (Exception e)
        {
            System.out.println("Error: "+e.getMessage());
            return null;
        }
    }*/


    @Override
    public int getCount() {
        return this.listVideo.size();
    }


    public ItemContent getItem(int position) {
        return listVideo.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
}