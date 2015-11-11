package ppm.uqac.com.geekproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ppm.uqac.com.geekproject.geekactivity.ViewListActivity;
import ppm.uqac.com.geekproject.profile.ViewProfileActivity;

public class MainActivity extends AppCompatActivity implements ppm.uqac.com.geekproject.geekactivity.GADialog.dialogDoneListener{
    private TextView _userNameTV;
    private TextView _typeTV;
    private ImageView _avatar;
    private ppm.uqac.com.geekproject.profile.Profile _profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "MainA.onCreate", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_main);


        //test pour afficher nom de la personne connecté
        _userNameTV = (TextView) findViewById(R.id.TV_UserName);
        _typeTV = (TextView) findViewById(R.id.TV_Type);
        _avatar = (ImageView) findViewById(R.id.image);

        Intent intent = getIntent();
        if (intent != null)
        {

            // recuperation des donnees presentes dans l'intent
            /*_profile.setUserName((String) intent.getSerializableExtra("firstName"));
            _profile.setLastName((String) intent.getSerializableExtra("lastName"));
            _profile.setType((Profile.Type) intent.getSerializableExtra("type"));*/

           /* _profile.setUserName((String) intent.getStringExtra("profile"));
            _profile.setLastName((String) intent.getStringExtra("lastName"));
            _profile.setType((Profile.Type) intent.getSerializableExtra("type"));*/

            _profile = (ppm.uqac.com.geekproject.profile.Profile) intent.getSerializableExtra("profile");

            System.out.println("firstname   " + _profile.getUserName());
            System.out.println("score   " + _profile.getScore());


            // on set les text viewer qui ne sont que des informations pour l'utilisateurs
            _userNameTV.setText(_profile.getUserName());

            _typeTV.setText((_profile.getType()).toString());

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), _profile.getAvatar());
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 640, 640, false);
            _avatar.setImageBitmap(bMapScaled);

            String previousActivity = (String) intent.getSerializableExtra("activite");

            if(previousActivity.toString().equals("CreateProfil"))
            {
                //test dialog
                ppm.uqac.com.geekproject.geekactivity.GADialog myDiag=new ppm.uqac.com.geekproject.geekactivity.GADialog();
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

        //System.out.println("in MA.onClickProfil()" + _userNameTV.)

        /*intent.putExtra("username", _userNameTV.getText().toString());
        intent.putExtra("lastname", _lastNameTV.getText().toString());
        intent.putExtra("type", _typeTV.getText().toString());*/
        this.finish();
        intent.putExtra("profile", _profile);

        startActivity(intent);
    }

    public void onClickActivities(View v)
    {
        Intent intent = new Intent(this, ppm.uqac.com.geekproject.geekactivity.ViewListActivity.class);
        startActivity(intent);
    }

    public void onClickContent(View v)
    {
        Intent intent = new Intent(this, ppm.uqac.com.geekproject.geeklopedie.GeeklopedieActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setMessage("Voulez-vous vraiment quitter cette application?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ppm.uqac.com.geekproject.mainmenu.MainActivity.this.moveTaskToBack(true);
                    }
                })
                .setNegativeButton("Non", null)
                .show();
        System.out.println("Bouton retour");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MaintActivity", "onPause");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MaintActivity", "onDestroy");
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
