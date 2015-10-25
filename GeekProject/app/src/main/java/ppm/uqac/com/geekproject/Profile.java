package ppm.uqac.com.geekproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

/**
 * Classe de test pour le profil.
 */
public class Profile implements Serializable
{
    public enum Type
    {
        ANTIGEEK,
        GEEKPERSECUTOR,
        NEUTRAL,
        GEEKFRIENDLY,
        GEEK
    }

    public String _userName;
    public float _score;
    public Type _type;
    int _avatar;

    public static final String PROFIL_FILE_NAME ="Profile.txt";


    public Profile()
    {
        _userName  ="";
        _score = 0.0f;
        _type = Type.ANTIGEEK;
        _avatar = R.drawable.antigeek;

    }

    String getUserName()
    {
        return _userName;
    }



    Type getType()
    {
        return _type;
    }

    float getScore()
    {
        return _score;
    }

    int getAvatar() { return _avatar; }

    void setType(Type t)
    {
        _type = t;
    }
    public void setUserName(String f)
    {
        _userName = f;
    }


    public void defineType()
    {
        if (_score <=0.2)
        {
            _type = Type.ANTIGEEK;
            _avatar = R.drawable.antigeek;
        }

        else if (_score<=0.4 && _score>0.2)
        {
            _type = Type.GEEKPERSECUTOR;
            _avatar = R.drawable.geekpersecutor;
        }

        else if (_score<=0.6 && _score >0.4)
        {
            _type = Type.NEUTRAL;

            _avatar = R.drawable.neutral;
        }

        else if (_score<=0.8 && _score >0.6)
        {
            _type = Type.GEEKFRIENDLY;
            _avatar = R.drawable.geekfriendly;
        }

        else
        {
            _type = Type.GEEK;
            _avatar = R.drawable.geek;
        }
    }


}
