package ppm.uqac.com.geekproject.geekactivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
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
import com.facebook.FacebookSdk;

import java.io.FileOutputStream;
import java.util.ArrayList;

import ppm.uqac.com.geekproject.R;
import ppm.uqac.com.geekproject.profile.Profile;


public class ViewListActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    GADatabase gadb;

    ArrayList<GA> gaList;

    GAAdapter gaAdapter;

    ActivitiesDoingFragment acDoingFrag;

    FragmentManager fm;

    Profile _profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        if (intent != null)
        {
            _profile = (Profile) intent.getSerializableExtra("profile");
            System.out.println("ViewListActivity : firstname   " + _profile.getUserName());
        }

        /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Ouverture de la BDD
        gadb = new GADatabase(this);
        // Récupération des activités dans la BDD
        gaList =  gadb.getActivitiesDoing(_profile.get_level());
        // Constructeur de notre Adapter de GA
        gaAdapter = new GAAdapter(this, gaList);

        fm = getFragmentManager();
        acDoingFrag = new ActivitiesDoingFragment();
        acDoingFrag.set_gadapter(gaAdapter);
        fm.beginTransaction().replace(R.id.activites_frame,acDoingFrag).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_list_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        if (id == R.id.activitiesDoing) {

            // Ouverture de la BDD
            gadb = new GADatabase(this);
            // Récupération des activités dans la BDD
            gaList =  gadb.getActivitiesDoing(_profile.get_level());
            // Constructeur de notre Adapter de GA
            gaAdapter.updateListView(gaList);
            acDoingFrag.set_gadapter(gaAdapter);
            fm.beginTransaction().replace(R.id.activites_frame,acDoingFrag).commit();

        } else if (id == R.id.activitiesDone) {

            // Ouverture de la BDD
            gadb = new GADatabase(this);
            // Récupération des activités dans la BDD
            gaList =  gadb.getActivitiesDone();
            // Constructeur de notre Adapter de GA
            gaAdapter.updateListView(gaList);

            acDoingFrag = new ActivitiesDoingFragment();
            acDoingFrag.set_gadapter(gaAdapter);
            fm.beginTransaction().replace(R.id.activites_frame,acDoingFrag).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClickActivitiesDone(View v)
    {
        Fragment currentFragment = this.getFragmentManager().findFragmentById(R.id.activites_frame);
        GA activity = gaAdapter.getItem(acDoingFrag.get_ContentListView().getPositionForView(v));
        System.out.println("ViewListActivity2 : Appuye bouton " + activity.get_name());
        gadb.updateActivity(activity);
        gaList.clear();
        gaList = gadb.getActivitiesDoing(_profile.get_level());
        gaAdapter.updateListView(gaList);
        acDoingFrag.set_gadapter(gaAdapter);
        fm.beginTransaction().detach(currentFragment).attach(currentFragment).commit();

        _profile.addExperience(activity.get_experience());
        saveExperience();

        System.out.println("Vue du profil après avoir fait une activité : experience = " + _profile.getExperience()  + " niveau = " + _profile.get_level());

        // Récupération du toast_ga_done
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_ga_done,
                (ViewGroup) findViewById(R.id.toast_ga_done_id));

        // set a dummy image
        ImageView image = (ImageView) layout.findViewById(R.id.image);
        image.setImageResource(R.drawable.gabutton);

        // Insertion du texte dans le textView
        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("Bravo !!!! Tu as réalisé l'activité " + activity.get_name());

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public void saveExperience()
    {
        String userName = "userName=";
        userName = userName.concat(_profile.getUserName()).concat(System.getProperty("line.separator"));

        String score = "score=";
        score = score.concat((String.valueOf(_profile.getScore())).concat(System.getProperty("line.separator")));

        String type = "type=";
        type = type.concat(_profile.getType().toString()).concat(System.getProperty("line.separator"));

        String experience = "experience=";
        experience = experience.concat((String.valueOf(_profile.getExperience())).concat(System.getProperty("line.separator")));

        String level = "level=";
        level = level.concat((String.valueOf(_profile.get_level())).concat(System.getProperty("line.separator")));

        try
        {
            FileOutputStream out = openFileOutput(Profile.PROFIL_FILE_NAME, Context.MODE_PRIVATE);
            System.out.println(Profile.PROFIL_FILE_NAME);
            out.write(userName.getBytes());
            out.write(score.getBytes());
            out.write(type.getBytes());
            out.write(experience.getBytes());
            out.write(level.getBytes());
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
