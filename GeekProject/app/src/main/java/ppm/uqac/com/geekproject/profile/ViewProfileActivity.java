package ppm.uqac.com.geekproject.profile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;

import ppm.uqac.com.geekproject.mainmenu.MainActivity;
import ppm.uqac.com.geekproject.R;

public class ViewProfileActivity extends AppCompatActivity {
    private EditText _userNameET;
    private TextView _typeTV;
    private TextView _score;
    private Profile _profile;
    private ImageView _avatar;
    private TextView _levelTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        _userNameET = (EditText) findViewById(R.id.TV_UserName);
        _typeTV = (TextView) findViewById(R.id.TV_Type);
        _score = (TextView) findViewById(R.id.TV_Score);
        _avatar = (ImageView) findViewById(R.id.image);
        _levelTV = (TextView) findViewById(R.id.TV_Level);

        // Listener pour le bouton de sauvegarde des modifications

        Button buttonModification = (Button)findViewById(R.id.BTN_Modificate);

        buttonModification.setOnClickListener(new Button.OnClickListener()
        {
            public void onClick(View v)
            {
                if(_userNameET.getText().length() !=0)
                {
                    saveProfil();

                }
                else
                {
                    Toast.makeText(ViewProfileActivity.this, "Veuillez entrer un pseudo conforme", Toast.LENGTH_LONG).show();
                }
            }
        });



        Intent intent = getIntent();
        if (intent != null)
        {
            _profile = (Profile) intent.getSerializableExtra("profile");
            _userNameET.setText(_profile.getUserName());

            _typeTV.setText(_profile.getType().toString());
            _score.setText(String.valueOf(_profile._score));
            _levelTV.setText(String.valueOf(_profile._level));
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), _profile.getAvatar());
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 640, 640, false);
            _avatar.setImageBitmap(bMapScaled);

            // On recupère l'expérience de l'utilisateur

            double xp = _profile.getExperience();
            int percent = (int) (xp/ _profile.getLevelLimit() *100);
            System.out.println("In VPA.onCreate total = " + _profile.getLevelLimit());
            System.out.println("In VPA.onCreate experience = " + xp);
            System.out.println("In VPA.onCreate purcentage = " + percent);
            ProgressBar myprogressbar = (ProgressBar)findViewById(R.id.progress_bar);
            myprogressbar.setProgress(percent);


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


    /**
     * Sauvegarde les information du profil dans le fichier
     */
    public void saveProfil()
    {
        System.out.println("in CPA.saveProfil()");
        String userName = "userName=";
        userName = userName.concat(_userNameET.getText().toString()).concat(System.getProperty("line.separator"));
        _profile.setUserName(_userNameET.getText().toString());

        String lastName = "lastName=";


        String score = "score=";
        score = score.concat(_score.getText().toString()).concat(System.getProperty("line.separator"));

        String type = "type=";
        type = type.concat(_profile._type.toString()).concat(System.getProperty("line.separator"));

        String experience = "experience=";
        experience = experience.concat((String.valueOf(_profile._experience)).concat(System.getProperty("line.separator")));


        try
        {
            FileOutputStream out = openFileOutput(Profile.PROFIL_FILE_NAME, Context.MODE_PRIVATE);
            System.out.println(Profile.PROFIL_FILE_NAME);
            out.write(userName.getBytes());

            out.write(score.getBytes());
            out.write(type.getBytes());
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        Intent intent = new Intent(this,MainActivity.class);
        this.finish();
        intent.putExtra("profile", _profile);
        intent.putExtra("activite", "ViewProfileActivity");
        startActivity(intent);
        System.out.println("fin save file et type est "+_profile._type.toString());
    }
}
