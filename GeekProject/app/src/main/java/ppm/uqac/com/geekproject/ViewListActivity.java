package ppm.uqac.com.geekproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class ViewListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "ViewListA.onCreate", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_view_list);

        // Ouverture de la BDD
        GADatabase activityData = new GADatabase(this);


        // Exemple avec 2 GA
        GA activity1 = new GA("regarder une video youtube", "blablal", 1, 500, false);
        GA activity2 = new GA("regarder une video youtubbxcbce", "bcxvlablal", 1, 500, false);
        activityData.addActivity(activity1);
        activityData.addActivity(activity2);
        // Récupération des activités dans la BDD
        ArrayList<GA> listActivities =  activityData.getActivities();
        // Constructeur de notre Adapter de GA
        GAAdapter adapterGeekActivity = new GAAdapter(this, listActivities);
        // Récupération de la listView
        ListView listGA = (ListView) findViewById(R.id.ListView01);
        // Adaptation de la ListView avec notre Adapter
        listGA.setAdapter(adapterGeekActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_list, menu);
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
}
