package ppm.uqac.com.geekproject.geeklopedie;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ppm.uqac.com.geekproject.Database.WikiDatabase;
import ppm.uqac.com.geekproject.R;
import ppm.uqac.com.geekproject.Database.ContentDatabase;

public class GeeklopedieActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geeklopedie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                                                                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame,new Fragment_main()).commit();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.geeklopedie2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.

        FragmentManager fm = getFragmentManager();

        int id = item.getItemId();
        System.out.println(id);

        if (id == R.id.mistrustful)
            fm.beginTransaction().replace(R.id.content_frame,new Fragment_1Mistrustful()).commit();
         else if (id == R.id.skeptical)   // Handle the camera action
            fm.beginTransaction().replace(R.id.content_frame,new Fragment_2Sckeptical()).commit();
         else if (id == R.id.Neutral)
            fm.beginTransaction().replace(R.id.content_frame,new Fragment_3Neutral()).commit();
        else if (id == R.id.Geekfriendly)
            fm.beginTransaction().replace(R.id.content_frame,new Fragment_4GeekFriendly()).commit();
        else if (id == R.id.Geek)
            fm.beginTransaction().replace(R.id.content_frame,new Fragment_5Geek()).commit();

        else if (id == R.id.nav_wiki)
        {

            // Ouverture de la BDD

            WikiDatabase db = new WikiDatabase(this);
            ArrayList<ItemWiki> list = new ArrayList<>();

            list = db.getWords();
            // On crée un adapter

            AdapterWiki adapter = new AdapterWiki(GeeklopedieActivity.this,list);

            Fragment_Wiki f = new Fragment_Wiki();
            f.setData(adapter);
            fm.beginTransaction().replace(R.id.content_frame,f).commit();


            db.close();
        }
        else if (id == R.id.nav_web)
        {
            ContentDatabase db = new ContentDatabase(this);

            InputStream is = getResources().openRawResource(getResources().getIdentifier("raw/contenu", "raw", getPackageName()));

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            int d,u;

            try
            {
                while ((line = reader.readLine()) != null)
                {
                    /*sb.append(line).append('\n');
                    System.out.println("while "+sb.toString());*/
                    System.out.println("index url: "+line.indexOf("name="));
                    d=line.indexOf(";desc=");
                    u=line.indexOf(";url=");
                    ItemContent contenu = new ItemContent(line.substring(5,d),line.substring(d+6,u),line.substring(u+5));
                    db.addContent(contenu);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            //
            // Récupération de tout le contenu
            ArrayList<ItemContent> List =  db.getContent();
            System.out.println(List.toString());

            // On crée un adapter
            final AdapterContent cAdapter = new AdapterContent(GeeklopedieActivity.this,List);

            Fragment_Web f6 = new Fragment_Web();
            fm.beginTransaction().replace(R.id.content_frame,f6).commit();
            f6.setData(cAdapter);

            db.close();
        }
        else if (id == R.id.nav_videos)
        {
            System.out.println("fragment video");

            ContentDatabase db = new ContentDatabase(this);
            System.out.println("ouverture bd");

            InputStream is = getResources().openRawResource(getResources().getIdentifier("raw/video", "raw", getPackageName()));

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();

            String line = null;
            int d,u;

            try
            {
                while ((line = reader.readLine()) != null)
                {
                    System.out.println("index url: "+line.indexOf("name="));
                    d=line.indexOf(";desc=");
                    u=line.indexOf(";url=");
                    ItemContent contenu = new ItemContent(line.substring(5,d),line.substring(d+6,u),line.substring(u+5));
                    db.addVideo(contenu);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }


            // Récupération de tout le contenu
            ArrayList<ItemContent> List =  db.getVideo();

            // On crée un adapter
            final AdapterVideo cAdapter = new AdapterVideo(GeeklopedieActivity.this,List);

            Fragment_Video f = new Fragment_Video();
            fm.beginTransaction().replace(R.id.content_frame,f).commit();
            f.setData(cAdapter);

            db.close();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}