package ppm.uqac.com.geekproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Toast.makeText(this, "ViewProfileA.onCreate", Toast.LENGTH_SHORT).show();

        EditText firstname = (EditText) findViewById(R.id.TV_FirstName);
        EditText lastname = (EditText) findViewById(R.id.TV_LastName);
        TextView type = (TextView) findViewById(R.id.TV_Type);



        Intent intent = getIntent();
        if (intent != null) {

            firstname.setText(intent.getStringExtra("firstname"));
            lastname.setText(intent.getStringExtra("lastname"));
            type.setText(intent.getStringExtra("type"));

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_profile, menu);
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
