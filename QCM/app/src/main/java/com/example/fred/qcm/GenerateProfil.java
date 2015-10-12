package com.example.fred.qcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;

/**
 * Service qui permet de générer le profil suite à un questionnaire
 */
public class GenerateProfil extends IntentService
{
    private ArrayList<Profil.Type> _profilTypes;
    private float _profilSclice;

    public final class GenerateProfilActions
    {
        public static final String Broadcast ="Profil_Broadcast";
    }

    public GenerateProfil() {
        super("GenerateProfil");
    }


    /**
     * Génération d'un profil selon le score
     */
    @Override
    protected void onHandleIntent(Intent intent)
    {
        _profilTypes = new ArrayList<Profil.Type>();

        _profilTypes.add(Profil.Type.ANTIGEEK);
        _profilTypes.add(Profil.Type.GEEKPERSECUTOR);
        _profilTypes.add(Profil.Type.NEUTRAL);
        _profilTypes.add(Profil.Type.GEEKFRIENDLY);
        _profilTypes.add(Profil.Type.GEEK);

        _profilSclice =0.20f;

        float scoreQuestionnaire = intent.getFloatExtra("scoreQuestionnaire", 0);

        Profil profil = new Profil();

        profil._score = scoreQuestionnaire * 100;
        int slice = (int)(scoreQuestionnaire/_profilSclice);

        //Pour gérer le cas où le score est parfait et obtenir la bonne tranche de profil.
        if (slice == _profilTypes.size())
            slice-=1;

        profil._type = _profilTypes.get(slice);


        Intent callBackIntent = new Intent(GenerateProfilActions.Broadcast);
        callBackIntent.putExtra("profil",profil);
        LocalBroadcastManager.getInstance(this).sendBroadcast(callBackIntent);
    }

}
