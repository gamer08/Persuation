package ppm.uqac.com.geekproject.geeklopedie;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ppm.uqac.com.geekproject.R;

/**
 * Created by Simon on 17/11/2015.
 */
public class VideoAdapter extends BaseAdapter {

    // Liste pour gérer tout le contenu
    private ArrayList<Content> listVideo;


    private Context mContext;

    //Sert pour afficher le layout
    private LayoutInflater mInflater;


    public VideoAdapter(Context context, ArrayList<Content> list)
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
        iv_img.setImageResource(R.drawable.vu0hjo52xpu);

        return layoutItem;

    }

    public Bitmap getBitmapFromURL(String src)
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
    }


    @Override
    public int getCount() {
        return this.listVideo.size();
    }


    public Content getItem(int position) {
        return listVideo.get(position);
    }

    public long getItemId(int position) {
        return position;
    }
}