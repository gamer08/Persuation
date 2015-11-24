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
    public int _result; // résultat du questionnaire
    int _avatar;
    public int _level;
    double _experience;
    double _limitExperience; // expéreince à atteindre pour le prochain niveau

    public static final String PROFIL_FILE_NAME ="Profile.txt";


    public Profile()
    {
        _userName  ="";
        _score = 0.0f;
        _type = Type.ANTIGEEK;
        _avatar = R.drawable.antigeek;
        _level = 1;
        _experience = 0;
        _limitExperience = getLevelLimit();
        _result = 0;

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
        if (_score <=20)
        {
            _type = Type.ANTIGEEK;
            _avatar = R.drawable.antigeek;
            _result = 1;
        }

        else if (_score<=40 && _score>20)
        {
            _type = Type.GEEKPERSECUTOR;
            _avatar = R.drawable.geekpersecutor;
            _result = 2;
        }

        else if (_score<=60 && _score >40)
        {
            _type = Type.NEUTRAL;

            _avatar = R.drawable.neutral;
            _result = 3;
        }

        else if (_score<=80 && _score >60)
        {
            _type = Type.GEEKFRIENDLY;
            _avatar = R.drawable.geekfriendly;
            _result = 4;
        }

        else
        {
            _type = Type.GEEK;
            _avatar = R.drawable.geek;
            _result = 5;
        }
    }

    public boolean isLevelUp()
    {

        System.out.println("Experience = " + _experience + " limit = " + getLevelLimit());
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

        if (isLevelUp()==true)
        {


            System.out.println("IN SET LEVEL");
            _level = _level + 1;
        }
    }

    public double getLevelLimit()
    {
        return _level * 50 + Math.exp(_level - 1) * 5;
    }

    public void addExperience(double i)
    {

        _experience+=i;
        setLevel();
    }
	
	  //Tester pour voir si l'application propose de faire un questionnaire.
    public boolean testForQuestionaryProgress()
    {
        if (_level % 3 == 1)
            return true;
        else
            return false;
    }


    public double getExperience() { return _experience; }

    public int get_level() {
        return _level;
    }
}
