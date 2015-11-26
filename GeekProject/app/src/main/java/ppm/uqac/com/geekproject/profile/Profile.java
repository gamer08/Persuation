package ppm.uqac.com.geekproject.profile;

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

    private String _userName;
    private float _score;
    private  int _level;
    private double _experience;
    private Type _type;
    private int _avatar;
    private double _limitExperience; // expéreince à atteindre pour le prochain niveau
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

    }

    // Setters

    public void setUserName(String f) { _userName = f; }
    public void setScore(float s) { _score = s; }
    public void setLevel(int l) { _level = l; }
    public void setExperience (double e) { _experience = e; }
    public void setType (Type t) { _type = t; } // utilisée uniquement quand le score est parafait (fans GenerateProfileService

    // Utilisée pour déterminer le type en fonction du dernier score obtenu au questionnaire
    public void defineType()
    {
        if (_score <=20)
        {
            _type = Type.ANTIGEEK;
            _avatar = R.drawable.antigeek;
        }

        else if (_score<=40 && _score>20)
        {
            _type = Type.GEEKPERSECUTOR;
            _avatar = R.drawable.geekpersecutor;
        }

        else if (_score<=60 && _score >40)
        {
            _type = Type.NEUTRAL;

            _avatar = R.drawable.neutral;
        }

        else if (_score<=80 && _score >60)
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

    // Getters

    public String getUserName() { return _userName; }

    public float getScore() { return _score; }

    public int getLevel() { return _level;}

    public double getExperience() { return _experience; }

    public Type getType() { return _type; }

    public int getAvatar() { return _avatar; }

    public double getLevelLimit()
    {
        return _level * 50 + Math.exp(_level - 1) * 5;
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

    public void updateLevel()
    {

        if (isLevelUp()==true)
        {
            System.out.println("IN SET LEVEL");
            _level = _level + 1;
        }
    }



    public void addExperience(double i)
    {
        _experience+=i;
        updateLevel();
    }
	
	  //Tester pour voir si l'application propose de faire un questionnaire.
    public boolean testForQuestionaryProgress()
    {
        if (_level != 1 && _level % 3 == 1)
            return true;
        else
            return false;
    }

}
