package ppm.uqac.com.geekproject.questionary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ppm.uqac.com.geekproject.R;
import ppm.uqac.com.geekproject.mainmenu.MainActivity;
import ppm.uqac.com.geekproject.profile.CreationProfileActivity;
import ppm.uqac.com.geekproject.profile.GenerateProfileService;
import ppm.uqac.com.geekproject.profile.Profile;

public class QuestionarySummaryActivity extends AppCompatActivity {

    /*Service related data*/
    private Receiver _receiver;
    private IntentFilter  _profilIntentFilter;
    private float _scoreQuestionnaire;

    private TextView _scoreView;
    private Profile _profile;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionary_summary);

        _profilIntentFilter = new IntentFilter(GenerateProfileService.GenerateProfilActions.Broadcast);

        _receiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(_receiver,_profilIntentFilter);

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LL_CustomFill);
        _scoreView = (TextView) findViewById(R.id.TV_Score);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        boolean fromLevelUP = false;
        Intent intent = getIntent();

        if (intent!= null)
        {
            fromLevelUP = intent.getBooleanExtra("fromLevelUP", false);
            _scoreQuestionnaire = intent.getFloatExtra("scoreQuestionnaire",0);
            _profile = (Profile)intent.getSerializableExtra("profile");
        }

        String formatedScore = String.format("%.1f", (_scoreQuestionnaire * 100));
        formatedScore += (" %");

        if (_profile !=null)
        {
            _profile.setScore(_scoreQuestionnaire *100);
            _profile.updateScores(); // on ajoute un résultat dans la table de hachage du profil
            System.out.println("In QuestionarySummaryService - Scores = " + _profile._scores);
        }


        _scoreView.setText(formatedScore);

        if (!fromLevelUP)
            createProfileCreationLayout(linearLayout);
        else
            createSummaryLayout(linearLayout);

        // Fonts
        Typeface typeFace= Typeface.createFromAsset(getAssets(), "octapost.ttf");

        TextView tv = (TextView) findViewById(R.id.TV_yourScore);
        TextView tv2 = (TextView) findViewById(R.id.TV_Score);
        _scoreView.setTypeface(typeFace);
        tv.setTypeface(typeFace);
        tv2.setTypeface(typeFace);

    }

    private class Receiver extends BroadcastReceiver
    {

        private Receiver()
        {}

        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent.getAction().equals(GenerateProfileService.GenerateProfilActions.Broadcast))
            {
                _profile = (Profile)intent.getSerializableExtra("profile");
                Intent nextActivity;

                switch(_profile.getType())
                {
                    case MEFIANT:
                    case SCEPTIQUE:
                    case NEUTRE:
                    case GEEKFRIENDLY:
                    case GEEK:
                        nextActivity = new Intent(getApplicationContext(),CreationProfileActivity.class);
                        break;

                    default:
                        nextActivity = new Intent(getApplicationContext(),MainActivity.class);
                        nextActivity.putExtra("activite","questionnaire");
                        break;
                }

                nextActivity.putExtra("profile", _profile);
                QuestionarySummaryActivity.this.finish();

                System.out.println("Fin du questionnaire: profil: score =  " + _scoreQuestionnaire + " et type = " + _profile.getType());
                startActivity(nextActivity);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_questionary_summary, menu);
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

    private void createProfileCreationLayout(LinearLayout parentLayout)
    {
        LinearLayout subLayout = new LinearLayout(this);

        TextView question = new TextView(this);
        question.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        question.setText("Voulez-vous creer un profil?");

        question.setTextColor(getResources().getColor(R.color.value));
        parentLayout.addView(question);

        subLayout.setOrientation(LinearLayout.HORIZONTAL);
        subLayout.setWeightSum(2);

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);

        Button yes = new Button(this);
        yes.setText("Oui");
        yes.setTextColor(getResources().getColor(R.color.value));
        yes.setLayoutParams(param);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProfile(false);
            }
        });

        Button no = new Button(this);
        no.setText("Non");
        no.setTextColor(getResources().getColor(R.color.value));
        no.setLayoutParams(param);

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProfile(true);
            }
        });

        subLayout.addView(yes);
        subLayout.addView(no);

        // Fonts
        Typeface typeFace= Typeface.createFromAsset(getAssets(), "octapost.ttf");

        no.setTypeface(typeFace);
        yes.setTypeface(typeFace);
        question.setTypeface(typeFace);

        parentLayout.addView(subLayout);
    }

    private void createSummaryLayout(LinearLayout parentLayout)
    {
        Button ok = new Button(this);
        ok.setText("ok");
        parentLayout.addView(ok);

        ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("profile", _profile);
                intent.putExtra("activite","questionnaire");
                QuestionarySummaryActivity.this.finish();
                startActivity(intent);
            }
        });

    }

    public void createProfile(boolean isDummyProfile)
    {
        if (isDummyProfile)
            _scoreQuestionnaire = Float.MIN_VALUE;

        Intent profilGenerator = new Intent(this, GenerateProfileService.class);
        profilGenerator.putExtra("scoreQuestionnaire", _scoreQuestionnaire);
        startService(profilGenerator);
    }
}