package ppm.uqac.com.geekproject.profile;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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


        _saveListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(_userName.getText().length() !=0)
                    saveProfil();
                else
                    Toast.makeText(CreationProfileActivity.this, "Veuillez rentrer un pseudo", Toast.LENGTH_LONG).show();
            }
        };

        _userName = (EditText) findViewById(R.id.TV_UserName);
        _score = (TextView) findViewById(R.id.TV_Score);

        _saveButton = (Button) findViewById(R.id.BTN_Save);
        _saveButton.setOnClickListener(_saveListener);

        Typeface typeFace= Typeface.createFromAsset(getAssets(), "octapost.ttf");

        TextView tv = (TextView) findViewById(R.id.TVL_Score);
        TextView tv2 = (TextView) findViewById(R.id.TV_Score);
        _userName.setTypeface(typeFace);
        _score.setTypeface(typeFace);
        tv.setTypeface(typeFace);
        tv2.setTypeface(typeFace);

        Intent intent = getIntent();
        if (intent != null)
        {
            _profile = (Profile) intent.getSerializableExtra("profile");

            String formatedScore = String.format("%.1f", _profile.getScore());
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * Sauvegarde les information du profil dans le fichier
     */
    public void saveProfil()
    {
        _profile.setUserName(_userName.getText().toString());
        _profile.setLevel(1);
        _profile.defineType();
        _profile.addExperience(20);

        System.out.println("Fin de création du profil et avant sauvegarde: profil: username = " +
                _profile.getUserName() + " et score =  " + _profile.getScore() + " et type = " + _profile.getType() +
                " et level = " + _profile.getLevel() + " et experience = " + _profile.getExperience() +
                " et nb de questionnaires = " + _profile.getNbQuestionaries() );

        // Sauvegarde
        Intent intentSave = new Intent(this, SaveProfileService.class);
        intentSave.putExtra("profile", _profile);
        startService(intentSave);

        System.out.println("Fin de création du profil et après sauvegarde: profil: username = " +
                _profile.getUserName() + "et score =  " + _profile.getScore() + " et type = " + _profile.getType() +
                " et level = " + _profile.getLevel() + " et experience = " + _profile.getExperience() +
                " et nb de questionnaires = " + _profile.getNbQuestionaries() );

        //Nouvelle activity MainActivity
        Intent intent = new Intent(this,MainActivity.class);

        this.finish();
        intent.putExtra("profile", _profile);
        intent.putExtra("activite", "CreateProfil");
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        System.out.println("Bouton retour");
    }
}