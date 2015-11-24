package ppm.uqac.com.geekproject.profile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

import ppm.uqac.com.geekproject.mainmenu.MainActivity;
import ppm.uqac.com.geekproject.R;

/**
 * Classe de test pour l'activité de profil
 *
 */
public class CreationProfileActivity extends AppCompatActivity
{

    private View.OnClickListener _saveListener;
    private Button _saveButton;
    private EditText _userName;
    private TextView _score;
    private Profile _profile;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toast.makeText(this, "CreationProfileA.onCreate", Toast.LENGTH_SHORT).show();

        _saveListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(_userName.getText().length() !=0)
                {
                    saveProfil();
                }
                else
                {
                    Toast.makeText(CreationProfileActivity.this, "Veuillez rentrer un pseudo", Toast.LENGTH_LONG).show();
                }

            }
        };

        _userName = (EditText) findViewById(R.id.TV_UserName);
        _score = (TextView) findViewById(R.id.TV_Score);

        _saveButton = (Button) findViewById(R.id.BTN_Save);
        _saveButton.setOnClickListener(_saveListener);

        Intent intent = getIntent();
        if (intent != null)
        {
            _profile = (Profile) intent.getSerializableExtra("profile");

            String formatedScore = String.format("%.1f", _profile._score);
            formatedScore += " %";

            _score.setText(formatedScore);
            //_score.setText((TextView) ((String) _profile.getScore()));
            //Oubli du "e" a la fin de profil. Dans le fichier GenerateProfileService.java ligne 62 on a profile

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profileactivity, menu);
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
        String userName = "userName=";
        userName = userName.concat(_userName.getText().toString()).concat(System.getProperty("line.separator"));
        String score = "score=";
        score = score.concat(_score.getText().toString()).concat(System.getProperty("line.separator"));
        String type = "type=";
        type = type.concat(_profile._type.toString()).concat(System.getProperty("line.separator"));
        String experience = "experience=";
        experience = experience.concat("20".concat(System.getProperty("line.separator")));
        String level = "level=";
        level = level.concat("1".concat(System.getProperty("line.separator")));


        try
        {
            FileOutputStream out = openFileOutput(Profile.PROFIL_FILE_NAME, Context.MODE_PRIVATE);
            System.out.println(Profile.PROFIL_FILE_NAME);
            out.write(userName.getBytes());

            out.write(score.getBytes());
            out.write(type.getBytes());
            out.write(experience.getBytes());
            out.write(level.getBytes());
            out.close();

            //Nouvelle activity MainActivity
            Intent intent = new Intent(this,MainActivity.class);

            _profile.setUserName(_userName.getText().toString());

            _profile.defineType();


            _profile.addExperience(20);

            this.finish();
            intent.putExtra("profile", _profile);
            intent.putExtra("activite", "CreateProfil");
            startActivity(intent);

            System.out.println("In CreationProfileActivity: experience = " + _profile._experience + " level = " + _profile._level);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



    }

    @Override
    public void onBackPressed()
    {
        System.out.println("Bouton retour");
    }
}
