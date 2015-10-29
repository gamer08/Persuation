package ppm.uqac.com.geekproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
        GADatabase gadb = new GADatabase(this);
        // Récupération des activités dans la BDD
        ArrayList<GA> gaList =  gadb.getActivities();
        // Constructeur de notre Adapter de GA
        GAAdapter gaAdapter = new GAAdapter(this, gaList);
        // Récupération de la listView
        ListView gaLV = (ListView) findViewById(R.id.ListView01);
        // Adaptation de la ListView avec notre Adapter
        gaLV.setAdapter(gaAdapter);
    }

    public void onClickActivitiesDone(View v)
    {
        Toast.makeText(this, "activite " + v.getId() + "  faite", Toast.LENGTH_SHORT).show();
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
