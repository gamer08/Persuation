package ppm.uqac.com.geekproject.profile;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ppm.uqac.com.geekproject.R;
import ppm.uqac.com.geekproject.geekactivity.GADatabase;
import ppm.uqac.com.geekproject.geekactivity.ViewListActivity2;
import ppm.uqac.com.geekproject.geeklopedie.Content;
import ppm.uqac.com.geekproject.geeklopedie.ContentAdapter;
import ppm.uqac.com.geekproject.geeklopedie.Fragment_6;


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
            /*GADatabase db = new GADatabase(this);

            InputStream is = getResources().openRawResource(
                    getResources().getIdentifier("raw/contenu",
                            "raw", getPackageName()));

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            int d,u;

            try
            {
                while ((line = reader.readLine()) != null)
                {
                    System.out.println("index url: "+line.indexOf("number="));
                    d=line.indexOf(";result=");
                    Point p = new Point(Integer.parseInt(line.substring(7,d)),Integer.parseInt(line.substring(d+8)));
                    db.addQuestionary(p);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //
            // Récupération de tout le contenu
            ArrayList<Content> List =  db.getContent();
            System.out.println(List.toString());

            // On crée un adapter*/
            /*final ContentAdapter cAdapter = new ContentAdapter(ViewListActivity2.this,List);

            Fragment_6 f6 = new Fragment_6();
            fm.beginTransaction().replace(R.id.content_frame,f6).commit();
            f6.setData(cAdapter);*/
            fm.beginTransaction().replace(R.id.content_frame, new Fragment_Chart()).commit();
        }

        mDL.closeDrawer(GravityCompat.START);

        return true;


    }



}
