package ppm.uqac.com.geekproject.profile;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**Service qui permet de charger le profil s'il existe*/
public class LoadProfileService extends IntentService
{
    private String _firstName;
    private Float _score;
    private String _type;
    private double _experience;

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
        String experience="";


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
                score = buff.readLine();
                _type = buff.readLine();
                experience = buff.readLine();

                System.out.println("In LoadProfileService: experience = " + experience);


                buff.close();
                reader.close();
                in.close();

                String firstName = _firstName.substring(_firstName.indexOf('=')+1);

                score = score.substring(score.indexOf('=') + 1);


                _score = Float.parseFloat(score);

                _type = _type.substring(_type.indexOf('=') + 1);
                experience = experience.substring(experience.indexOf('=') + 1);
                _experience = Double.parseDouble(experience);

                profile = new Profile();

                profile._userName = firstName;
                profile._score = _score;
                profile._experience = _experience;
                profile.defineType();

                System.out.println("résumé profil");
                System.out.println(firstName);

                System.out.println(_score);
                System.out.println(profile._type.toString());
                System.out.println(profile._experience);
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
