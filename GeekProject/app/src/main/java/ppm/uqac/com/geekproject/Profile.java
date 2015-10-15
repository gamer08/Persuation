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

}
