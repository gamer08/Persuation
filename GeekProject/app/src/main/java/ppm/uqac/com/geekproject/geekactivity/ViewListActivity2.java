package ppm.uqac.com.geekproject.geekactivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import java.util.ArrayList;

import ppm.uqac.com.geekproject.R;


public class ViewListActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    GADatabase gadb;

    ArrayList<GA> gaList;

    GAAdapter gaAdapter;

    ActivitiesDoingFragment acDoingFrag;

    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        gaList =  gadb.getActivitiesDoing();
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
            gaList =  gadb.getActivitiesDoing();
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
        gaList = gadb.getActivitiesDoing();
        gaAdapter.updateListView(gaList);
        acDoingFrag.set_gadapter(gaAdapter);
        fm.beginTransaction().detach(currentFragment).attach(currentFragment).commit();
    }
}
