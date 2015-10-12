package ppm.uqac.com.geekproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


/**
 * Activité de test pour tester le chargement du profil à partir du fichier
 * */
public class StartActivity extends AppCompatActivity
{

    private Receiver _receiver;
    private IntentFilter _profileIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();

        _profileIntentFilter = new IntentFilter(LoadProfileActivity.LoadProfilActions.Broadcast);
        _receiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(_receiver, _profileIntentFilter);

        Intent profileLoader = new Intent(this,LoadProfileActivity.class);
        startService(profileLoader);
    }


    private class Receiver extends BroadcastReceiver
    {

        private Receiver()
        {}

        @Override
        public void onReceive(Context context, Intent intent)
        {
            Profile profile = (Profile)intent.getSerializableExtra("profile");
            if (profile == null)
            {
                Intent questionnaireActivity = new Intent(getApplicationContext(),QuestionaryActivity.class);
                startActivity(questionnaireActivity);
            }
            else
            {
                Intent profilActivity = new Intent(getApplicationContext(),ProfileActivity.class);
                profilActivity.putExtra("profile",profile);
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

