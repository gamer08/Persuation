package ppm.uqac.com.geekproject.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

import ppm.uqac.com.geekproject.R;

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
        GEEK,
        GUEST
    }

    public String _userName;
    public float _score;
    public Type _type;
    int _avatar;
    public int _level;
    double _experience;

    public static final String PROFIL_FILE_NAME ="Profile.txt";


    public Profile()
    {
        _userName  ="";
        _score = 0.0f;
        _type = Type.ANTIGEEK;
        _avatar = R.drawable.antigeek;
        _level = 1;
        _experience = 0;

    }

    public String getUserName()
    {
        return _userName;
    }



    public Type getType()
    {
        return _type;
    }

    public float getScore()
    {
        return _score;
    }

    public int getAvatar() { return _avatar; }

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

    public boolean isLevelUp()
    {
        // Algorithme de

        if (_experience>getLevelLimit())
        {
            return true;
        }

        else
        {
            return false;
        }
    }

    public void setLevel()
    {
        if (isLevelUp())
        {
            _level +=1;
        }
    }

    public double getLevelLimit()
    {
        return _level * 50 + Math.exp(_level - 1) * 5;
    }

    public void addExperience(double i)
    {
        _experience+=i;
    }


    public double getExperience() { return _experience; }




}
