package ppm.uqac.com.geekproject.profile;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**Service qui permet de charger le profil s'il existe*/

public class LoadProfileService extends IntentService
{
    private String _userName;
    private float _score;
    private int _level;
    private double _experience;
    private int _nbQuestionaries;
    private ArrayList<Float> _scores = new ArrayList<Float>();

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
        String experience="";
        String level="";
        String score="";
        String nbQuestionaries="";

        try
        {
            File file = getApplicationContext().getFileStreamPath(Profile.PROFIL_FILE_NAME);

            if (file.exists())
            {
                FileInputStream in = openFileInput(Profile.PROFIL_FILE_NAME);
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader buff = new BufferedReader(reader);

                _userName = buff.readLine();
                score = buff.readLine();
                level = buff.readLine();
                experience = buff.readLine();
                nbQuestionaries = buff.readLine();

                /**
                 * On s'occupe du nb de questionnaires d'abord
                 */
                nbQuestionaries = nbQuestionaries.substring(nbQuestionaries.indexOf('=') + 1);

                _nbQuestionaries = Integer.parseInt(nbQuestionaries);

                System.out.println("Load du nb de questionnaires: " + _nbQuestionaries);

                profile = new Profile();
                profile.setNbQuestionaries(_nbQuestionaries);

                for (int i=1; i<=_nbQuestionaries; i++)
                {
                    //profile._scores.add(Float.parseFloat(buff.readLine()));

                    System.out.println("Ligne particuliere dans fichier: " + buff.readLine());
                }

                buff.close();
                reader.close();
                in.close();

                String userName = _userName.substring(_userName.indexOf('=') + 1);

                score = score.substring(score.indexOf('=') + 1);
                _score = Float.parseFloat(score);
                level = level.substring(level.indexOf('=') + 1);
                _level = Integer.parseInt(level);
                experience = experience.substring(experience.indexOf('=') + 1);
                _experience = Double.parseDouble(experience);

                profile.setUserName(userName);
                profile.setScore(_score);
                profile.defineType();
                profile.setLevel(_level);
                profile.setExperience(_experience);
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