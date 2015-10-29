package ppm.uqac.com.geekproject;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Service qui permet de générer le profil suite à un questionnaire
 */
public class GenerateProfileService extends IntentService
{
    private ArrayList<Profile.Type> _profilTypes;
    private float _profilSclice;

    private static final String TAG = "Generate questionary";

    public final class GenerateProfilActions
    {
        public static final String Broadcast ="Profil_Broadcast";
    }

    public GenerateProfileService() {
        super("GenerateProfileService");
    }


    /**
     * Génération d'un profil selon le score
     */
    @Override
    protected void onHandleIntent(Intent intent)
    {
        Log.d(TAG,"OnHandleIntent");
        _profilTypes = new ArrayList<Profile.Type>();

        _profilTypes.add(Profile.Type.ANTIGEEK);
        _profilTypes.add(Profile.Type.GEEKPERSECUTOR);
        _profilTypes.add(Profile.Type.NEUTRAL);
        _profilTypes.add(Profile.Type.GEEKFRIENDLY);
        _profilTypes.add(Profile.Type.GEEK);
        _profilTypes.add(Profile.Type.GUEST);

        _profilSclice =0.20f;

        float scoreQuestionnaire = intent.getFloatExtra("scoreQuestionnaire", 0);

        Profile profile = new Profile();

        if (scoreQuestionnaire != Float.MIN_VALUE)
        {
            profile._score = scoreQuestionnaire * 100;
            int slice = (int) (scoreQuestionnaire / _profilSclice);

            //Pour gérer le cas où le score est parfait et obtenir la bonne tranche de profile.
            if (slice == 5)
                slice -= 1;

            profile._type = _profilTypes.get(slice);
        }
        else
        {
            profile._type = Profile.Type.GUEST;
            profile._userName = "Guest";
            profile._score = Float.MIN_VALUE;
        }

        Intent callBackIntent = new Intent(GenerateProfilActions.Broadcast);
        callBackIntent.putExtra("profile", profile);
        LocalBroadcastManager.getInstance(this).sendBroadcast(callBackIntent);
    }
}
