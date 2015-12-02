package ppm.uqac.com.geekproject.geekactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.Database.GADatabase;
import ppm.uqac.com.geekproject.R;

public class ViewListActivity extends AppCompatActivity
{
    GADatabase gadb;

    ArrayList<GA> gaList;

    GAAdapter gaAdapter;

    ListView gaLV;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);

        // Ouverture de la BDD
        gadb = new GADatabase(this);
        // Récupération des activités dans la BDD
        gaList =  gadb.getActivitiesDoing(0);
        // Constructeur de notre Adapter de GA
        gaAdapter = new GAAdapter(this, gaList);
        // Récupération de la listView
        gaLV = (ListView) findViewById(R.id.ListView01);
        // Adaptation de la ListView avec notre Adapter
        gaLV.setAdapter(gaAdapter);
    }


    public void onClickActivitiesDone(View v)
    {
        gaList.clear();
        gaList = gadb.getActivitiesDoing(0);
        gaAdapter.updateListView(gaList);
        gaLV.setAdapter(gaAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_list, menu);
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
}