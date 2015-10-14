package ppm.uqac.com.geekproject;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**Service qui permet de charger le profil s'il existe*/
public class LoadProfileService extends IntentService
{
    private String _firstName;
    private String _lastName;
    private float _score;
    private String _type;

    private static final String TAG = "LoadProfile Service";

    public final class LoadProfilActions
    {
        public static final String Broadcast ="Load_Broadcast";
    }

    public LoadProfileService() {
        super("LoadProfileService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Profile profile = null;
        String score ="";


        Log.d(TAG, "Service Started!");


        try
        {
            File file = getApplicationContext().getFileStreamPath(Profile.PROFIL_FILE_NAME);
            System.out.println(file.exists());
            /*
            Avant il y avait cette ligne
            if (file != null || file.exists())
            A quoi sert file != null?
             */
            if (file.exists())
            {
                FileInputStream in = openFileInput(Profile.PROFIL_FILE_NAME);
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader buff = new BufferedReader(reader);

                _firstName = buff.readLine();
                _lastName = buff.readLine();
                score = buff.readLine();
                _type = buff.readLine();


                buff.close();
                reader.close();
                in.close();

                String firstName = _firstName.substring(_firstName.indexOf('=')+1);
                String lastName = _lastName.substring(_lastName.indexOf('=')+1);
                score = score.substring(score.indexOf('=')+1);
                _score = Float.parseFloat(score);
                _type = _type.substring(_type.indexOf('=') + 1);

                profile = new Profile();

                profile._firstName = firstName;
                profile._lastName = lastName;
                profile._score = _score;

                switch (_type)
                {
                    case "GEEK":
                        profile._type=Profile.Type.GEEK;
                        break;
                    case "GEEKPERSECUTOR":
                        profile._type=Profile.Type.GEEKPERSECUTOR;
                        break;
                    case "NEUTRAL":
                        profile._type=Profile.Type.NEUTRAL;
                        break;
                    case "GEEKFRIENDLY":
                        profile._type=Profile.Type.GEEKFRIENDLY;
                        break;
                    default:
                        profile._type = Profile.Type.ANTIGEEK;
                }


                System.out.println("résumé profil");
                System.out.println(firstName);
                System.out.println(lastName);
                System.out.println(_score);
                System.out.println(profile._type.toString());
                System.out.println("fin résumé profil");

            }
                Intent callBackIntent = new Intent(LoadProfilActions.Broadcast);
                callBackIntent.putExtra("profile", profile);
                LocalBroadcastManager.getInstance(this).sendBroadcast(callBackIntent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
