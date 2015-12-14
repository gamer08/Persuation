package ppm.uqac.com.geekproject.mainmenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ppm.uqac.com.geekproject.Database.ProfileDatabase;
import ppm.uqac.com.geekproject.Database.WikiDatabase;
import ppm.uqac.com.geekproject.R;
import ppm.uqac.com.geekproject.geekactivity.GADialog;
import ppm.uqac.com.geekproject.geekactivity.ViewListActivity2;
import ppm.uqac.com.geekproject.geeklopedie.GeeklopedieActivity;
import ppm.uqac.com.geekproject.profile.Profile;
import ppm.uqac.com.geekproject.profile.ViewProfileActivity;

public class MainActivity extends AppCompatActivity implements GADialog.dialogDoneListener{
    private TextView _userNameTV;
    private TextView _typeTV;
    private ImageView _avatar;
    private Profile _profile;

    CallbackManager callbackManager;
    ShareDialog shareDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("IN MAIN");
        WikiDatabase db = new WikiDatabase(MainActivity.this);

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
                Toast t2 = Toast.makeText(MainActivity.this, "Vous avez gagné le badge Newbie", Toast.LENGTH_SHORT);
                //t2.setGravity(Gravity.BOTTOM, 4,0);
                t2.show();

                Toast t = Toast.makeText(MainActivity.this, "Vous avez gagné le badge Newbie", Toast.LENGTH_SHORT);
                ImageView view = new ImageView(this);
                view.setImageResource(R.drawable.badge_newbie);

                ProfileDatabase pdb = new ProfileDatabase(this);
                pdb.gainBadge(pdb.getBadges().get(0));
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
                ImageView b = (ImageView) findViewById(R.id.buttonProfil);
                b.setVisibility(View.INVISIBLE);
            }


        }

        //System.out.println("Vue du profil dans le main menu : experience = " + _profile.getExperience() + " niveau = " + _profile.getLevel());

        // Font
        Typeface typeFace= Typeface.createFromAsset(getAssets(), "octapost.ttf");
        _userNameTV.setTypeface(typeFace);
        _typeTV.setTypeface(typeFace);

        System.out.println("In Main - Scores = " + _profile._scores);

        // Test par rapport au dernier questionnaire pour le gain de badge

        if (_profile.getScore() >80)
        {
            ProfileDatabase pdb = new ProfileDatabase(this);
            pdb.gainBadge(pdb.getBadges().get(6));
            Toast.makeText(this, "Tu as gagné un nouveau badge! ", Toast.LENGTH_SHORT).show();
        }

        else if (_profile.getScore() == 100)
        {
            ProfileDatabase pdb = new ProfileDatabase(this);
            pdb.gainBadge(pdb.getBadges().get(7));
            Toast.makeText(this, "Tu as gagné un nouveau badge! ", Toast.LENGTH_SHORT).show();
        }

        this.socialNetwork();


    }

    public void socialNetwork(){
        ImageView iB1 = (ImageView) findViewById(R.id.buttonGoogle);
        ImageView iB2 = (ImageView) findViewById(R.id.buttonFacebook);
        ImageView iB3 = (ImageView) findViewById(R.id.buttonTwitter);
        iB1.setVisibility(View.INVISIBLE);
        iB2.setVisibility(View.INVISIBLE);
        iB3.setVisibility(View.INVISIBLE);
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");//("text/plain");
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(shareIntent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            if (TextUtils.equals(resolveInfo.activityInfo.name.toLowerCase(),"com.google.android.libraries.social.gateway.gatewayactivity")) {
                iB1.setVisibility(View.VISIBLE);
            }
            else  if (TextUtils.equals(resolveInfo.activityInfo.packageName.toLowerCase(),"com.facebook.katana")) {
                iB2.setVisibility(View.VISIBLE);
            } else  if (TextUtils.equals(resolveInfo.activityInfo.name.toLowerCase(),"com.twitter.android.composer.composeractivity")) {
                iB3.setVisibility(View.VISIBLE);
            }
        }


        iB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ArrayList<String> wantedPackage = new ArrayList<>();
                List<Intent> targetedShareIntents = new ArrayList<Intent>();
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("image/jpeg");//("text/plain");
                List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(shareIntent, 0);
                for (ResolveInfo resolveInfo : resInfo) {
                    String packageName = resolveInfo.activityInfo.packageName.toLowerCase();
                    Intent targetedShareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    targetedShareIntent.setType("image/jpeg");
                    if (TextUtils.equals(resolveInfo.activityInfo.name.toLowerCase(), "com.google.android.libraries.social.gateway.gatewayactivity")) {
                        System.out.println("google plus");
                        targetedShareIntent.setPackage(packageName);
                        targetedShareIntent.setClassName(
                                resolveInfo.activityInfo.packageName,
                                resolveInfo.activityInfo.name);
                        String message = "Par rapport aux geeks, je suis " + _profile.getType() + " et je suis de niveau " + _profile.getLevel();
                        targetedShareIntent.putExtra(Intent.EXTRA_TEXT, message);
                        startActivity(targetedShareIntent);
                    }
                }
            }
        });

        iB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                FacebookSdk.sdkInitialize(getApplicationContext());
                callbackManager = CallbackManager.Factory.create();
                shareDialog = new ShareDialog(MainActivity.this);
                String message = "Par rapport aux geeks, je suis "+_profile.getType()+" et je suis de niveau "+_profile.getLevel();
                if (ShareDialog.canShow(ShareLinkContent.class))
                {
                    LoginManager.getInstance().logInWithReadPermissions(
                            MainActivity.this,
                            Arrays.asList("user_friends"));
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("GeekProject")
                            .setContentDescription(message)
                            .setContentUrl(Uri.parse("http://facebook.com"))
                            .build();
                    shareDialog.show(linkContent);
                }
            }
        });


        iB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                ArrayList<String> wantedPackage = new ArrayList<>();
                wantedPackage.add("com.google.android.gm");
                List<Intent> targetedShareIntents = new ArrayList<Intent>();
                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("image/jpeg");//("text/plain");
                List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(shareIntent, 0);
                for (ResolveInfo resolveInfo : resInfo) {
                    System.out.println(resolveInfo.resolvePackageName);
                    String packageName = resolveInfo.activityInfo.packageName.toLowerCase();
                    Intent targetedShareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    targetedShareIntent.setType("image/jpeg");
                    if (TextUtils.equals(resolveInfo.activityInfo.name, "com.twitter.android.composer.ComposerActivity")) {
                        System.out.println("sdsd " + resolveInfo.activityInfo.packageName);
                        System.out.println("sdsd 2   " + resolveInfo.activityInfo.name);
                        targetedShareIntent.setPackage(packageName);
                        targetedShareIntent.setClassName(
                                resolveInfo.activityInfo.packageName,
                                resolveInfo.activityInfo.name);
                        String message = "Par rapport aux geeks, je suis " + _profile.getType() + " et je suis de niveau " + _profile.getLevel();
                        targetedShareIntent.putExtra(Intent.EXTRA_TEXT, message);
                        targetedShareIntents.add(targetedShareIntent);
                        wantedPackage.remove(packageName);
                    }
                }
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                startActivity(chooserIntent);
            }
        });


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

        Intent intent = new Intent(this, ViewProfileActivity.class);
        this.finish();
        intent.putExtra("profile", _profile);

        startActivity(intent);
    }

    public void onClickActivities(View v)
    {
        Intent intent = new Intent(this,ViewListActivity2.class);
        this.finish();
        intent.putExtra("profile", _profile);
        intent.putExtra("firsttime",false);
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

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MaintActivity", "onResume");
        this.socialNetwork();
    }

    /*
    Méthode pour gérer l'action de la fenetre de dialogue
     */
    @Override
    public void onDone(boolean state) {
        Intent intent = new Intent(this,ViewListActivity2.class);
        this.finish();
        intent.putExtra("profile", _profile);
        intent.putExtra("firsttime", true);
        startActivity(intent);
    }
}
