package ppm.uqac.com.geekproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

public class ViewProfileActivity extends AppCompatActivity {
    private EditText _firstNameET;
    private EditText _lastNameET;
    private TextView _typeTV;
    private TextView _score;
    private Profile _profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Toast.makeText(this, "ViewProfileA.onCreate", Toast.LENGTH_SHORT).show();


       // _profile = new Profile();
        _firstNameET = (EditText) findViewById(R.id.TV_FirstName);
        _lastNameET = (EditText) findViewById(R.id.TV_LastName);
        _typeTV = (TextView) findViewById(R.id.TV_Type);

        // Listener pour le bouton de sauvegarde des modifications

        Button buttonModification = (Button)findViewById(R.id.BTN_Modificate);

        buttonModification.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                System.out.println("in CPA.saveProfil()");
                String firstName = "firstName=";
                firstName = firstName.concat(_firstNameET.getText().toString()).concat(System.getProperty("line.separator"));

                String lastName = "lastName=";
                lastName = lastName.concat(_lastNameET.getText().toString()).concat(System.getProperty("line.separator"));

                String score = "score=";
                score = score.concat(_score.getText().toString()).concat(System.getProperty("line.separator"));

                String type = "type=";
                type = type.concat(_profile._type.toString()).concat(System.getProperty("line.separator"));

        /*String avatar = "avatar=";
        type = type.concat(_profile._avatar.toString()).concat(System.getProperty("line.separator"));*/


                //File file = new File(getBaseContext().getFilesDir(),PROFIL_FILE_NAME);
                // File file = getApplicationContext().getFileStreamPath(PROFIL_FILE_NAME);
                try
                {
                    FileOutputStream out = openFileOutput(Profile.PROFIL_FILE_NAME, Context.MODE_PRIVATE);
                    System.out.println(Profile.PROFIL_FILE_NAME);
                    out.write(firstName.getBytes());
                    out.write(lastName.getBytes());
                    out.write(score.getBytes());
                    out.write(type.getBytes());
                    out.close();



                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });



        Intent intent = getIntent();
        if (intent != null)
        {
            _profile = (Profile) intent.getSerializableExtra("profile");
            _firstNameET.setText(_profile.getFirstName());
            _lastNameET.setText(_profile.getLastName());
            _typeTV.setText(_profile.getType().toString());

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

    public void changeProfile()
    {

    }
}
