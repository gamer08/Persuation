package ppm.uqac.com.geekproject.start;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.R;
import ppm.uqac.com.geekproject.profile.Profile;
import ppm.uqac.com.geekproject.questionary.QuestionaryActivity;

public class WelcomeActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        // Fonts
        Typeface typeFace= Typeface.createFromAsset(getAssets(), "octapost.ttf");

        TextView tv1 = (TextView) findViewById(R.id.tv_keep);
        TextView tv2 = (TextView) findViewById(R.id.tv_calm);
        TextView tv3 = (TextView) findViewById(R.id.tv_and);
        TextView tv4 = (TextView) findViewById(R.id.tv_click);
        TextView tv5 = (TextView) findViewById(R.id.tv_here);

        ArrayList<TextView> a = new ArrayList<>();
        a.add(tv1);
        a.add(tv2);
        a.add(tv3);
        a.add(tv4);
        a.add(tv5);

        for (TextView t : a)
        {
            t.setTypeface(typeFace);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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

    public void StartQuestionary(View v)
    {
        this.finish();
        Intent intent = new Intent(this,QuestionaryActivity.class);
		intent.putExtra("fromLevelUP",false);

        Profile profile = null;
        intent.putExtra("profile",profile);
        startActivity(intent);
    }
}