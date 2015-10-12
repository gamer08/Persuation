package com.example.fred.qcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**Service qui permet de charger le profil s'il existe*/
public class LoadProfil extends IntentService
{
    private String _firstName;
    private String _lastName;
    private float _score;

    public final class LoadProfilActions
    {
        public static final String Broadcast ="Load_Broadcast";
    }

    public LoadProfil() {
        super("LoadProfil");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Profil profil = null;
        String score ="";

        try
        {
            File file = getApplicationContext().getFileStreamPath(Profil.PROFIL_FILE_NAME);

            if (file != null || file.exists())
            {
                FileInputStream in = openFileInput(Profil.PROFIL_FILE_NAME);
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader buff = new BufferedReader(reader);

                _firstName = buff.readLine();
                _lastName = buff.readLine();
                score = buff.readLine();

                buff.close();
                reader.close();
                in.close();

                String firstName = _firstName.substring(_firstName.indexOf('=')+1);
                String lastName = _lastName.substring(_lastName.indexOf('=')+1);
                score = score.substring(score.indexOf('=')+1);
                _score = Float.parseFloat(score);

                profil = new Profil();

                profil._firstName = firstName;
                profil._lastName = lastName;
                profil._score = _score;

            }
                Intent callBackIntent = new Intent(LoadProfilActions.Broadcast);
                callBackIntent.putExtra("profil",profil);
                LocalBroadcastManager.getInstance(this).sendBroadcast(callBackIntent);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
