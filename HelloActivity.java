package com.example.fred.hello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HelloActivity extends AppCompatActivity
{
    protected List<String> a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        a = new ArrayList<String>() ;
        a.add("Java cest de la marde");
        a.add("Vive le C++, FUCK LE JAVA");
        a.add("Gloire au C++");
        a.add("Im a java Hater and proud of it !!!!");

        Button test = (Button) findViewById(R.id.testButton);
        test.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TextView label = (TextView) findViewById(R.id.textView);
                label.setText(AfficheMessage());
            }
        });
    }

    public String AfficheMessage()
    {
        int nb = a.size();
        Random random = new Random();
        return a.get(random.nextInt(nb));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hello, menu);
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
