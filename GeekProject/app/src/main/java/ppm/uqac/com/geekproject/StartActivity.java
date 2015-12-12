package ppm.uqac.com.geekproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import ppm.uqac.com.geekproject.Database.WikiDatabase;
import ppm.uqac.com.geekproject.geekactivity.GA;
import ppm.uqac.com.geekproject.Database.GADatabase;
import ppm.uqac.com.geekproject.geeklopedie.ItemWiki;
import ppm.uqac.com.geekproject.mainmenu.MainActivity;
import ppm.uqac.com.geekproject.profile.LoadProfileService;
import ppm.uqac.com.geekproject.profile.Profile;
import ppm.uqac.com.geekproject.questionary.Question;


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
        {
        }

        @Override
        public void onReceive(Context context, Intent intent)
        {
            Profile profile = (Profile)intent.getSerializableExtra("profile");
            if (profile == null) {
                Intent intentWelcome = new Intent(StartActivity.this, WelcomeActivity.class);
                startActivity(intentWelcome);
                GADatabase gadb = new GADatabase(StartActivity.this);
                // Exemple avec 2 GA
                GA activity1 = new GA("Lire la définition d un Geek", "Vous pouvez trouver un article présentant un geek dans la geeklopédie --> contenu", 1, 20, false, "");
                GA activity2 = new GA("Regarder la vidéo Youtube Cyprien : Les GEEK", "Vous pouvez trouver cette vidéo dans la geeklopédie --> vidéo", 1, 20, false, "");
                GA activity3 = new GA("Aller consulter la section de la geeklopédie qui correspond à votre profil", "Vous pouvez trouver cette vidéo dans la geeklopédie", 1, 20, false, "");
                GA activity4 = new GA("Regarder une vidéo Culture GEEK", "Culture GEEK est une émission diffusé sur BFMTV qui présente les nouvelles technologies", 2, 50, false, "http://www.bfmtv.com/mediaplayer/replay/culture-geek/");
                GA activity5 = new GA("Installer l application AppyGeek", "AppyGeek est une application qui permet de suivre toute l actualité Tech", 2, 50, false, "https://play.google.com/store/apps/details?id=com.mobilesrepublic.appygeek&hl=fr_CA");
                GA activity6 = new GA("Augmenter votre niveau de jeux sur le play store", "Passer au niveau 10", 4, 500, false, "");
                gadb.addActivity(activity1);
                gadb.addActivity(activity2);
                gadb.addActivity(activity3);
                gadb.addActivity(activity4);
                gadb.addActivity(activity5);
                gadb.addActivity(activity6);

                try
                {
                    getWiki();
                }


                catch (XmlPullParserException e)
                {

                }

                catch (IOException e)
                {

                }


            }
            else
            {
                StartActivity.this.finish();
                Intent profilActivity = new Intent(getApplicationContext(),MainActivity.class);
                profilActivity.putExtra("profile", profile);
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
        if (id == R.id.action_settings)
            return true;

        return super.onOptionsItemSelected(item);
    }



    public void getWiki() throws XmlPullParserException, IOException
    {
        System.out.println("In getwiki()");
        XmlResourceParser parser = getApplicationContext().getResources().getXml(R.xml.wiki);
        int eventType = parser.next();

        WikiDatabase wdb = new WikiDatabase(StartActivity.this);
        String name="";
        String definition="";

        boolean b = false;

        while (b == false)
        {

            switch (eventType) {
                case XmlPullParser.START_TAG:


                    if (parser.getName().equals("Nom"))
                    {
                        name = parser.nextText();
                    }

                    else if (parser.getName().equals("Definition"))
                    {
                        definition = parser.nextText();
                        wdb.addWord(new ItemWiki(name, definition));

                    }

                    else if (parser.getName().equals("Fin"))
                    {
                        System.out.println( parser.nextText());
                        b = true;
                    }

                    break;

                default:
                    break;
            }

            eventType = parser.next();

        }



    }


}

