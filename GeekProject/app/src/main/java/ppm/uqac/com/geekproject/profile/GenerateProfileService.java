package ppm.uqac.com.geekproject.profile;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

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
            profile.setScore(scoreQuestionnaire * 100);
            int slice = (int) (scoreQuestionnaire / _profilSclice);

            //Pour gérer le cas où le score est parfait et obtenir la bonne tranche de profile.
            if (slice == 5)
                slice -= 1;

            profile.setType(_profilTypes.get(slice));

            // on met à jour la table des scores

            profile.updateScores();

            System.out.println("In GenerateProfileService - Scores = " + profile._scores);
        }
        else
        {
            profile.setUserName("Guest");
            profile.setType(Profile.Type.GUEST);
            profile.setScore(Float.MIN_VALUE);
            profile.setLevel(0);
            profile.setExperience(0);
        }


        Intent callBackIntent = new Intent(GenerateProfilActions.Broadcast);
        callBackIntent.putExtra("profile", profile);
        LocalBroadcastManager.getInstance(this).sendBroadcast(callBackIntent);
    }
}
