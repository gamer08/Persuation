package ppm.uqac.com.geekproject.geekactivity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ppm.uqac.com.geekproject.Database.GADatabase;
import ppm.uqac.com.geekproject.R;
import ppm.uqac.com.geekproject.mainmenu.MainActivity;
import ppm.uqac.com.geekproject.profile.Profile;
import ppm.uqac.com.geekproject.profile.SaveProfileService;
import ppm.uqac.com.geekproject.questionary.QuestionaryActivity;


public class ViewListActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    GADatabase gadb;

    ArrayList<GA> gaList;

    GAAdapter gaAdapter;

    Fragment_GA fragment_ga;

    FragmentManager fm;

    Profile _profile;

    Boolean _firsttime=false;

    List<ApplicationInfo> _installedApps = null;
    PackageManager pm;

    // Variables pour le partage sur facebook
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get Application installed on devices

        pm = getPackageManager();
        _installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for(int i =0; i<_installedApps.size(); i++)
        {
            System.out.println(_installedApps.get(i).packageName);
        }


        Intent intent = getIntent();
        if (intent != null)
        {
            _profile = (Profile) intent.getSerializableExtra("profile");
            _firsttime = (Boolean) intent.getSerializableExtra("firsttime");
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                                                                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Ouverture de la BDD
        gadb = new GADatabase(this);
        // Récupération des activités dans la BDD
        gaList =  gadb.getActivitiesDoing(_profile.getLevel());
        // Constructeur de notre Adapter de GA
        gaAdapter = new GAAdapter(this, gaList);

        if(_firsttime)
            gaAdapter.setFirstTime(true);

        fm = getFragmentManager();
        fragment_ga = new Fragment_GA();
        fragment_ga.set_gadapter(gaAdapter);

        fm.beginTransaction().replace(R.id.activities_frame, fragment_ga).commit();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
        {
            if(_firsttime==true)
            {
                new AlertDialog.Builder(this)
                        .setMessage("Voulez-vous partager vos progrès sur facebook?")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                //test dialog
                                callbackManager = CallbackManager.Factory.create();
                                shareDialog = new ShareDialog(ViewListActivity2.this);
                                Uri uri = Uri.parse("R.drawable.startactivity");
                                if (ShareDialog.canShow(ShareLinkContent.class))
                                {
                                    System.out.println("if pour afficher partage facebook");
                                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                            .setContentTitle("GeekProject")
                                             /* .setImageUrl(uri) */// Pour le moment problème pour mettre une image
                                            .setContentDescription(
                                                    "A commencé à utiliser l'application GeekProject")
                                            .setContentUrl(Uri.parse("http://facebook.com"))
                                            .build();

                                    shareDialog.show(linkContent);
                                    System.out.println("fin if pour afficher partage facebook");
                                }
                                //Ouverture MainActivity
                                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                main.putExtra("profile",_profile);
                                main.putExtra("activite","ViewListActivity");
                                ViewListActivity2.this.finish();
                                startActivity(main);
                            }
                        })
                        .setNegativeButton("Non",new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                //Ouverture MainActivity
                                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                main.putExtra("profile",_profile);
                                main.putExtra("activite","ViewListActivity");
                                ViewListActivity2.this.finish();
                                startActivity(main);
                            }
                        }).show();
                System.out.println("Bouton retour");
            }
            else
            {
                //Ouverture MainActivity
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                main.putExtra("profile",_profile);
                main.putExtra("activite","ViewListActivity");
                ViewListActivity2.this.finish();
                startActivity(main);
            }
        }
    }

    /**
     * Méthode pour récupérer le resultat du partage sur facebook
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_list_activity2, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.activitiesDoing)
        {
            // Ouverture de la BDD
            gadb = new GADatabase(this);
            // Récupération des activités dans la BDD
            gaList =  gadb.getActivitiesDoing(_profile.getLevel());
            // Constructeur de notre Adapter de GA
            gaAdapter.updateListView(gaList);
            fragment_ga.set_gadapter(gaAdapter);
            fm.beginTransaction().detach(fragment_ga).attach(fragment_ga).commit();

        }
        else if (id == R.id.activitiesDone)
        {

            // Ouverture de la BDD
            gadb = new GADatabase(this);
            // Récupération des activités dans la BDD
            gaList =  gadb.getActivitiesDone();
            // Constructeur de notre Adapter de GA
            gaAdapter.updateListView(gaList);

            fragment_ga.set_gadapter(gaAdapter);
            fm.beginTransaction().detach(fragment_ga).attach(fragment_ga).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickActivitiesDone(View v)
    {
        boolean isApp = false;
        Fragment currentFragment = this.getFragmentManager().findFragmentById(R.id.activities_frame);
        GA activity = gaAdapter.getItem(fragment_ga.get_ContentListView().getPositionForView(v));
        System.out.println("ViewListActivity2 : Appuye bouton " + activity.get_name());
        if(activity.get_name().equals("Installer l application AppyGeek"))
        {
            isApp = searchApps("com.mobilesrepublic.appygeek");
            System.out.println(isApp);
            if(!isApp)
            {
                Toast.makeText(this, "Nous n'avons pas trouvé l'application Appy Geek sur votre apparaeil", Toast.LENGTH_SHORT).show();
                gaList.clear();
                gaList = gadb.getActivitiesDoing(_profile.getLevel());
                gaAdapter.updateListView(gaList);
                fragment_ga.set_gadapter(gaAdapter);
                fm.beginTransaction().detach(currentFragment).attach(currentFragment).commit();
                return;
            }
        }
        gadb.updateActivity(activity);
        gaList.clear();
        gaList = gadb.getActivitiesDoing(_profile.getLevel());
        gaAdapter.updateListView(gaList);
        fragment_ga.set_gadapter(gaAdapter);
        fm.beginTransaction().detach(currentFragment).attach(currentFragment).commit();


        Toast.makeText(this, "Bravo! Tu as réalisé l'activité " + activity.get_name(), Toast.LENGTH_SHORT).show();

        if (_profile.addExperience(activity.get_experience()) == true)
            addLevel(currentFragment);

        saveExperience();

        System.out.println("Vue du profil après avoir fait une activité : experience = " + _profile.getExperience() + " niveau = " + _profile.getLevel());

        if (_profile.testForQuestionaryProgress())
            askForANewQuestionary();
    }

	public void askForANewQuestionary()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Faire un questionnaire");
        builder.setMessage("Voulez-vous faire un nouveau questionnaire pour évaluer votre progression ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent newActivity = new Intent(getApplicationContext(), QuestionaryActivity.class);
                newActivity.putExtra("fromLevelUP", true);
                newActivity.putExtra("profile", _profile);
                ViewListActivity2.this.finish();
                startActivity(newActivity);
            }
        });

        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }
	
    public void saveExperience()
    {
        Intent intentSave = new Intent(this, SaveProfileService.class);
        intentSave.putExtra("profile", _profile);
        startService(intentSave);
    }

    public boolean getIsFirstTime()
    {
        return _firsttime;
    }

    public void onClickActivitiesUrl(View v)
    {
        GA activity = gaAdapter.getItem(fragment_ga.get_ContentListView().getPositionForView(v));
        Uri uri = Uri.parse(activity.get_url()); // Si l'url ne contient pas http:// l'appli plante
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void addLevel(Fragment currentFragment)
    {
        Toast.makeText(this, "Bravo! Tu es maintenant niveau " + _profile.getLevel(), Toast.LENGTH_SHORT).show();
        gaList = gadb.getActivitiesDoing(_profile.getLevel());
        gaAdapter.updateListView(gaList);
        fragment_ga.set_gadapter(gaAdapter);
        fm.beginTransaction().detach(currentFragment).attach(currentFragment).commit();
    }

    public boolean searchApps(String name)
    {
        int i =0;
        boolean isApp = false;
        if(!_installedApps.isEmpty())
            _installedApps.clear();
        _installedApps = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        System.out.println(_installedApps.size());
        while( i<(_installedApps.size()-1) && !isApp)
        {
            if (_installedApps.get(i).packageName.equals(name)) {
                isApp = true;
            } else {
                isApp = false;
            }
            i++;
        }
        return isApp;
    }
}

