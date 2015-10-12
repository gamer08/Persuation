package ppm.uqac.com.geekproject;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

/**
 * Service qui permet de générer le profil suite à un questionnaire
 */
public class GenerateProfile extends IntentService
{
    private ArrayList<Profile.Type> _profilTypes;
    private float _profilSclice;

    public final class GenerateProfilActions
    {
        public static final String Broadcast ="Profil_Broadcast";
    }

    public GenerateProfile() {
        super("GenerateProfile");
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

        _profilSclice =0.20f;

        float scoreQuestionnaire = intent.getFloatExtra("scoreQuestionnaire", 0);

        Profile profile = new Profile();

        profile._score = scoreQuestionnaire * 100;
        int slice = (int)(scoreQuestionnaire/_profilSclice);

        //Pour gérer le cas où le score est parfait et obtenir la bonne tranche de profile.
        if (slice == _profilTypes.size())
            slice-=1;

        profile._type = _profilTypes.get(slice);


        Intent callBackIntent = new Intent(GenerateProfilActions.Broadcast);
        callBackIntent.putExtra("profile", profile);
        LocalBroadcastManager.getInstance(this).sendBroadcast(callBackIntent);
    }

}
