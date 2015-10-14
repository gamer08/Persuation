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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


/**
 * Activité de test pour tester le chargement du profil à partir du fichier
 * */
public class StartActivity extends AppCompatActivity
{

    private Receiver _receiver;
    private IntentFilter _profileIntentFilter;
    Button _creationProfileButton;
    Button _viewProfileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        _creationProfileButton = (Button) findViewById(R.id.buttonCreation);
        _viewProfileButton =  (Button) findViewById(R.id.buttonView);;


        Toast.makeText(this, "StartA.onCreate", Toast.LENGTH_SHORT).show();

        // Pour mettre des listeners sur les boutons

        setButton();

        _profileIntentFilter = new IntentFilter(LoadProfileService.LoadProfilActions.Broadcast);
        _receiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(_receiver, _profileIntentFilter);

        Intent profileLoader = new Intent(this,LoadProfileService.class);
        startService(profileLoader);

    }


    /**
     * Classe interne
     */
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
                Intent intentWelcome = new Intent(StartActivity.this, WelcomeActivity.class);
                startActivity(intentWelcome);
            }
            else
            {
                Intent profilActivity = new Intent(getApplicationContext(),MainActivity.class);
                profilActivity.putExtra("lastName",profile._lastName);
                profilActivity.putExtra("firstName",profile._firstName);
                profilActivity.putExtra("type",profile._type.toString());
                profilActivity.putExtra("activite", "Start");
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

    /**
     * Clic sur le bouton pour aller voir le profil
     * @param v
     */
    public void onButtonProfileClick (View v)
    {
        // do something

        Button buttonProfile = (Button) v;
        //((Button)v).setText("clicked");


    }

    public void setButton()
    {
        _creationProfileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        _viewProfileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(this, LoadProfileService.class);
                Intent intent = new Intent(StartActivity.this, LoadProfileService.class);
                startService(intent);
            }

        });

    }
}

