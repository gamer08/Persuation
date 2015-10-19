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

    public String _firstName, _lastName;
    public float _score;
    public Type _type;
    Bitmap _avatar;

    public static final String PROFIL_FILE_NAME ="Profile.txt";


    public Profile()
    {
        _firstName = _lastName ="";
        _score = 0.0f;
        _type = Type.ANTIGEEK;
        //_avatar = BitmapFactory.decodeResource(getResources(),R.drawable.antigeek);
        //_avatar = Bitmap.createBitmap(_avatar, 480, 320, false);

    }

    String getFirstName()
    {
        return _firstName;
    }

    String getLastName()
    {
        return _lastName;
    }

    Type getType()
    {
        return _type;
    }

    float getScore()
    {
        return _score;
    }

    void setType(Type t)
    {
        _type = t;
    }
    public void setFirstName(String f)
    {
        _firstName = f;
    }

    public void setLastName(String l)
    {
        _lastName = l;
    }

    public Type defineType()
    {
        if (_score <=0.2)
        {
            return Type.ANTIGEEK;
        }

        else if (_score<=0.4 && _score>0.2)
        {
            return Type.GEEKPERSECUTOR;
        }

        else if (_score<=0.6 && _score >0.4)
        {
            return Type.NEUTRAL;
        }

        else if (_score<=0.8 && _score >0.6)
        {
            return Type.GEEKFRIENDLY;
        }

        else
        {
            return Type.GEEK;
        }
    }


}
