package com.example.fred.qcm;

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

import java.io.File;
import java.io.FileOutputStream;

/**
 * Classe de test pour l'activité de profil
 *
 */
public class ProfilActivity extends AppCompatActivity
{

    private View.OnClickListener _saveListener;
    private Button _saveButton;
    private EditText _firstName, _lastName;
    private TextView _score;
    private Profil _profil;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        _saveListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
              saveProfil();
            }
        };

        _firstName = (EditText) findViewById(R.id.TV_FirstName);
        _lastName = (EditText) findViewById(R.id.TV_LastName);
        _score = (TextView) findViewById(R.id.TV_Score);

        _saveButton = (Button) findViewById(R.id.BTN_Save);
        _saveButton.setOnClickListener(_saveListener);

        Intent intent = getIntent();
        if (intent != null)
        {
            _profil = (Profil) intent.getSerializableExtra("profil");

            if (_profil != null)
            {
                _firstName.setText(_profil._firstName);
                _lastName.setText(_profil._lastName);
                _score.setText(String.valueOf(_profil._score));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profil, menu);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Sauvegarde les information du profil dans le fichier
     */
    public void saveProfil()
    {
        String firstName = "firstName=";
        firstName = firstName.concat(_firstName.getText().toString()).concat(System.getProperty("line.separator"));

        String lastName = "lastName=";
        lastName = lastName.concat(_lastName.getText().toString()).concat(System.getProperty("line.separator"));

        String score = "score=";
        score = score.concat(_score.getText().toString()).concat(System.getProperty("line.separator"));

        //File file = new File(getBaseContext().getFilesDir(),PROFIL_FILE_NAME);
       // File file = getApplicationContext().getFileStreamPath(PROFIL_FILE_NAME);
        try
        {
            FileOutputStream out = openFileOutput(Profil.PROFIL_FILE_NAME, Context.MODE_PRIVATE);
            out.write(firstName.getBytes());
            out.write(lastName.getBytes());
            out.write(score.getBytes());
            out.close();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }



    }
}
