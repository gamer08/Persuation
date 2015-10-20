package ppm.uqac.com.geekproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GADialog.dialogDoneListener{
    private TextView _firstNameTV;
    private TextView _lastNameTV;
    private TextView _typeTV;
    private ImageView _avatar;
    private Profile _profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "MainA.onCreate", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);


        //test pour afficher nom de la personne connecté
        _firstNameTV = (TextView) findViewById(R.id.TV_FirstName);
        _lastNameTV = (TextView) findViewById(R.id.TV_LastName);
        _typeTV = (TextView) findViewById(R.id.TV_Type);
        _avatar = (ImageView) findViewById(R.id.image);

        Intent intent = getIntent();
        if (intent != null)
        {

            // recuperation des donnees presentes dans l'intent
            /*_profile.setFirstName((String) intent.getSerializableExtra("firstName"));
            _profile.setLastName((String) intent.getSerializableExtra("lastName"));
            _profile.setType((Profile.Type) intent.getSerializableExtra("type"));*/

           /* _profile.setFirstName((String) intent.getStringExtra("profile"));
            _profile.setLastName((String) intent.getStringExtra("lastName"));
            _profile.setType((Profile.Type) intent.getSerializableExtra("type"));*/

            _profile = (Profile) intent.getSerializableExtra("profile");

            System.out.println("AAAAAAAA   " +
                    "" + _profile.getFirstName());


            // on set les text viewer qui ne sont que des informations pour l'utilisateurs
            _firstNameTV.setText(_profile.getFirstName());
            _lastNameTV.setText(_profile.getLastName());
            _typeTV.setText((_profile.getType()).toString());

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), _profile.getAvatar());
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 640, 640, false);
            _avatar.setImageBitmap(bMapScaled);

            String previousActivity = (String) intent.getSerializableExtra("activite");

            if(previousActivity.toString().equals("CreateProfil"))
            {
                //test dialog
                GADialog myDiag=new GADialog();
                myDiag.show(getFragmentManager(),"Diag");
            }
            else
            {
                System.out.println(previousActivity.toString());
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    public void onClickProfile(View v)
    {
        Intent intent = new Intent(this,ViewProfileActivity.class);

        //System.out.println("in MA.onClickProfil()" + _firstNameTV.)

        /*intent.putExtra("firstname", _firstNameTV.getText().toString());
        intent.putExtra("lastname", _lastNameTV.getText().toString());
        intent.putExtra("type", _typeTV.getText().toString());*/

        intent.putExtra("profile", _profile);

        startActivity(intent);
    }

    public void onClickActivities(View v)
    {
        Intent intent = new Intent(this,ViewListActivity.class);
        startActivity(intent);
    }

    public void onClickContent(View v)
    {
        Intent intent = new Intent(this,GeeklopedieActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        System.out.println("Bouton retour");
    }

    /*
    Méthode pour gérer l'action de la fenetre de dialogue
     */
    @Override
    public void onDone(boolean state) {
        Intent intent = new Intent(this,ViewListActivity.class);
        startActivity(intent);
    }
}
