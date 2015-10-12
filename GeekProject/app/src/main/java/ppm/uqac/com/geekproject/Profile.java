package ppm.uqac.com.geekproject;

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

    public static final String PROFIL_FILE_NAME ="Profile.txt";


    public Profile()
    {
        _firstName = _lastName ="";
        _score = 0.0f;
        _type = Type.ANTIGEEK;

    }

}
