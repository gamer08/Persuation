package ppm.uqac.com.geekproject.profile;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.R;
import ppm.uqac.com.geekproject.mainmenu.MainActivity;


public class ViewProfileActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout mDL;
    private ListView mLV;
    private String[] mItems;
    AdapterBadges _adapter;
    private Profile _profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile2);
        Toast t = Toast.makeText(getApplicationContext(), "ND.create", Toast.LENGTH_SHORT);
        t.show();


        mDL = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDL, R.string.drawer_open, R.string.drawer_close);
        mDL.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new Fragment_Pseudo()).commit();


        // TO DO: Faire une table dans la BD pour mettre les badges dedans
        // Mise en place de l'adapteur

        Badge b0 = new Badge("Newbie", "Badge pour avoir créé un profil", R.drawable.badge_newbie, true);
        Badge b1 = new Badge("Bronze", "Badge pour avoir atteint le niveau 4", R.drawable.badge_bronze, false);
        Badge b2 = new Badge("Argent", "Badge pour avoir atteient le niveau 7", R.drawable.badge_silver, false);
        Badge b3 = new Badge("Or", "Badge pour avoir atteint le niveau 10", R.drawable.badge_gold, false);


        ArrayList<Badge> l = new ArrayList<>(4);
        l.add(b0);
        l.add(b1);
        l.add(b2);
        l.add(b3);
        _adapter = new AdapterBadges(this, l);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);*/

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        FragmentManager fm = getFragmentManager();
        int id = menuItem.getItemId();

        if (id == R.id.pseudo)
        {
            fm.beginTransaction().replace(R.id.content_frame, new Fragment_Pseudo()).commit();
        }

        if (id == R.id.badges)
        {
            System.out.println("In ViewProfileActivity2.clicsurBadges");
            Fragment_Badges f = new Fragment_Badges();
            f.setAdapter(_adapter);
            fm.beginTransaction().replace(R.id.content_frame, f).commit();
        }

        if (id == R.id.questionaries)
        {
            fm.beginTransaction().replace(R.id.content_frame, new Fragment_Chart()).commit();
        }

        if (id == R.id.photo)
        {
            fm.beginTransaction().replace(R.id.content_frame, new Fragment_Photo()).commit();
        }

        mDL.closeDrawer(GravityCompat.START);

        return true;


    }

    @Override
    public void onBackPressed()
    {
        /*new AlertDialog.Builder(this)
                .setMessage("Voulez-vous vraiment quitter cette application?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ViewProfileActivity2.this.moveTaskToBack(true);
                    }
                })
                .setNegativeButton("Non", null)
                .show();*/
        System.out.println("Profile: Bouton retour");

        Intent intent = new Intent(this, MainActivity.class);
        //Intent intent = new Intent(this,MainActivity.class);
        finish();
        intent.putExtra("profile",(Profile)  getIntent().getSerializableExtra("profile"));
        intent.putExtra("activite", "ViewProfileActivity");
        startActivity(intent);
    }



}
