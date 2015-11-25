package ppm.uqac.com.geekproject.mainmenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import ppm.uqac.com.geekproject.ImageSettings;
import ppm.uqac.com.geekproject.R;
import ppm.uqac.com.geekproject.geekactivity.GADialog;
import ppm.uqac.com.geekproject.geekactivity.ViewListActivity2;
import ppm.uqac.com.geekproject.geeklopedie.GeeklopedieActivity;
import ppm.uqac.com.geekproject.profile.Profile;
import ppm.uqac.com.geekproject.profile.ViewProfileActivity2;

public class MainActivity extends AppCompatActivity implements GADialog.dialogDoneListener{
    private TextView _userNameTV;
    private TextView _typeTV;
    private ImageView _avatar;
    private Profile _profile;

    // test pour partager sur facebook
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext());


        //test pour afficher nom de la personne connecté
        _userNameTV = (TextView) findViewById(R.id.TV_UserName);
        _typeTV = (TextView) findViewById(R.id.TV_Type);
        _avatar = (ImageView) findViewById(R.id.image);

        Intent intent = getIntent();
        if (intent != null)
        {

            _profile = (Profile) intent.getSerializableExtra("profile");

            System.out.println("Menu principal: profil: username = " +
            _profile.getUserName() + "score =  " + _profile.getScore() + " et type = " + _profile.getType() +
            " et level = " + _profile.getLevel() + " et experience = " + _profile.getExperience());


            // on set les text viewer qui ne sont que des informations pour l'utilisateurs
            _userNameTV.setText(_profile.getUserName());

            _typeTV.setText((_profile.getType()).toString());

            /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), _profile.getAvatar());
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 640, 640, false);*/

            Bitmap bm = BitmapFactory.decodeResource(getResources(), _profile.getAvatar());

            bm = ImageSettings.getCircleBitmap(bm);
            _avatar.setImageBitmap(bm);

            String previousActivity = (String) intent.getSerializableExtra("activite");

            if(previousActivity.toString().equals("CreateProfil"))
            {
                //test dialog
                callbackManager = CallbackManager.Factory.create();
                shareDialog = new ShareDialog(this);
                Uri uri=Uri.parse("R.drawable.startactivity");
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    System.out.println("if pour afficher partage facebook");
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("GeekProject")
                           /* .setImageUrl(uri) */// Pour le moment problème pour mettre une image
                            .setContentDescription(
                                    "A commencé à utiliser l'application GeekProject")
                            .setContentUrl(Uri.parse("http://facebook.com"))
                            .build();

                    shareDialog.show(linkContent);
                    System.out.println("fin if pour afficher partage facebook");
                }



                Toast t2 = Toast.makeText(MainActivity.this, "Vous avez gagné le badge Newbie", Toast.LENGTH_SHORT);
                //t2.setGravity(Gravity.BOTTOM, 4,0);
                t2.show();

                Toast t = Toast.makeText(MainActivity.this, "Vous avez gagné le badge Newbie", Toast.LENGTH_SHORT);
                ImageView view = new ImageView(this);
                view.setImageResource(R.drawable.badge_newbie);
                t.setView(view);

                t.show();


                GADialog myDiag=new GADialog();
                myDiag.show(getFragmentManager(),"Diag");
            }
            else
            {
                System.out.println(previousActivity.toString());
            }

            if(_profile.getType()== Profile.Type.GUEST)
            {
                System.out.println("le type est guest");
                Button b = (Button) findViewById(R.id.buttonProfil);
                b.setVisibility(View.INVISIBLE);
            }

        }

        System.out.println("Vue du profil dans le main menu : experience = " + _profile.getExperience() + " niveau = " + _profile.getLevel());


    }


    /**
     * Méthode pour récupérer le resultat du partage sur facebook
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
        //Intent intent = new Intent(this,ViewProfileActivity.class);

        Intent intent = new Intent(this, ViewProfileActivity2.class);
        this.finish();
        intent.putExtra("profile", _profile);

        startActivity(intent);
    }

    public void onClickActivities(View v)
    {
        Intent intent = new Intent(this,ViewListActivity2.class);
        this.finish();
        intent.putExtra("profile", _profile);
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
        new AlertDialog.Builder(this)
                .setMessage("Voulez-vous vraiment quitter cette application?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.moveTaskToBack(true);
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
        Intent intent = new Intent(this,ViewListActivity2.class);
        this.finish();
        intent.putExtra("profile", _profile);
        startActivity(intent);
    }
}
