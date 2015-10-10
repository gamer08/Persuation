package com.example.fred.qcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Activité de test pour tester le chargement du profil à partir du fichier
 * */
public class StartActivity extends AppCompatActivity
{

    private Receiver _receiver;
    private IntentFilter _profilIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        _profilIntentFilter = new IntentFilter(LoadProfil.LoadProfilActions.Broadcast);
        _receiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(_receiver, _profilIntentFilter);

        Intent profilLoader = new Intent(this,LoadProfil.class);
        startService(profilLoader);
    }


    private class Receiver extends BroadcastReceiver
    {

        private Receiver()
        {}

        @Override
        public void onReceive(Context context, Intent intent)
        {
            Profil profil = (Profil)intent.getSerializableExtra("profil");
            if (profil == null)
            {
                Intent questionnaireActivity = new Intent(getApplicationContext(),QuestionnaireActivity.class);
                startActivity(questionnaireActivity);
            }
            else
            {
                Intent profilActivity = new Intent(getApplicationContext(),ProfilActivity.class);
                profilActivity.putExtra("profil",profil);
                startActivity(profilActivity);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
