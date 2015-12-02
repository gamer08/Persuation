package ppm.uqac.com.geekproject.geeklopedie;

import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.R;

/**
 * Cette classe regroupe les différents éléments que doit contenir
 * une page de la geeklopédie sur un type de profil
 * Created by Valentin on 01/12/2015.
 */
public class Content_Profile
{
    private String _name;
    private String _description;
    private ArrayList<String> _people;

    public Content_Profile()
    {
        _name="";
        _description="";
        _people=new ArrayList<String>();
    }



}
