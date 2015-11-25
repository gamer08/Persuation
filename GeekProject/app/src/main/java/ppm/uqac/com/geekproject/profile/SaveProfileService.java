package ppm.uqac.com.geekproject.profile;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import java.io.FileOutputStream;

/**
 * Service qui est utilisé pour sauvegarder les informations relatives à un profil
 * Created by Valentin on 25/11/2015.
 */
public class SaveProfileService extends IntentService
{

    public SaveProfileService()
    {
        super("SaveProfileService");
    }


    @Override
    protected void onHandleIntent(Intent intent)
    {
        System.out.println("in SaveProfileService.onHandleIntent()");

        Profile profile = (Profile)intent.getSerializableExtra("profile");

        String userName = "userName=";
        String lastScore = "lastScore=";
        String level = "level=";
        String experience = "experience=";
        userName = userName.concat(profile.getUserName()).concat(System.getProperty("line.separator"));
        lastScore = lastScore.concat((String.valueOf(profile.getScore())).concat(System.getProperty("line.separator")));
        level = level.concat((String.valueOf(profile.getLevel())).concat(System.getProperty("line.separator")));
        experience = experience.concat((String.valueOf(profile.getExperience())).concat(System.getProperty("line.separator")));


        try
        {
            FileOutputStream out = openFileOutput(Profile.PROFIL_FILE_NAME, Context.MODE_PRIVATE);
            out.write(userName.getBytes());
            out.write(lastScore.getBytes());
            out.write(level.getBytes());
            out.write(experience.getBytes());
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}