package ppm.uqac.com.geekproject.questionary;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ppm.uqac.com.geekproject.profile.CreationProfileActivity;
import ppm.uqac.com.geekproject.profile.GenerateProfileService;
import ppm.uqac.com.geekproject.mainmenu.MainActivity;
import ppm.uqac.com.geekproject.profile.Profile;
import ppm.uqac.com.geekproject.R;


/*
* L'activité de questionnaire qui permet à l'utilisateur de se créer un profil.
*/
public class QuestionaryActivity extends AppCompatActivity
{
    /*Other data*/
    private int _curQuestion, _nbQuestions, _score, _totalWeightPossible;
    private Questionary _questionary;
    private View.OnClickListener _choiceListener;
    private boolean _fromLevelUP;
    private Profile _profile;

    /*Service related data*/
    private Receiver _receiver;
    private IntentFilter _questionnaireIntentFilter;


    /*Interesting View*/
    private ArrayList<TextView> _choices;
    private TextView _question;
    private TextView _questionCpt;
    private TextView _fact;

    /*Pour afficher des messages dans les logs*/
    private static final String TAG = "QuestionaryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Log.d(TAG, "OnCreate");
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "QuestionaryA.onCreate", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_questionary);

        //On regarde si on vient d'un level UP.
        Intent intent = getIntent();
        if (intent != null)
        {
            _fromLevelUP = intent.getBooleanExtra("fromLevelUP", false);
            _profile = (Profile) intent.getSerializableExtra("profile");
        }

        _choiceListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (v.isClickable())
                {
                    int index = _choices.indexOf((TextView)v);

                    if (index >=0)
                    {
                        System.out.println("Valeur de index "+index);
                        _score += _questionary.questions().get(_curQuestion).possibleChoices().get(index).weight();
                        Log.d(TAG,"LoadedNextQuestion");
                        loadNextQuestion();
                    }
                }
            }
        };

        this._curQuestion = -1;
        _score = _totalWeightPossible = 0 ;

        // Références vers les objets de l'Activité
        _question = (TextView) findViewById(R.id.TV_Question);
        _questionCpt = (TextView) findViewById(R.id.TV_curQuestionCpt);
        _fact = (TextView) findViewById(R.id.TV_Fact);

        _choices = new ArrayList<TextView>();

        _choices.add((TextView) findViewById(R.id.TV_Choice1));
        _choices.add((TextView) findViewById(R.id.TV_Choice2));
        _choices.add((TextView) findViewById(R.id.TV_Choice3));
        _choices.add((TextView) findViewById(R.id.TV_Choice4));
        _choices.add((TextView) findViewById(R.id.TV_Choice5));


       //Init de l'observeur de clic pour les choix
        for (TextView tv : _choices)
            tv.setOnClickListener(_choiceListener);

        // Déclaration des filtres et diffuseurs pour les services
        _questionnaireIntentFilter = new IntentFilter(GenerateQuestionaryService.GenerateQuestionnaireActions.Broadcast);

        _receiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(_receiver,_questionnaireIntentFilter);

        _questionary = new Questionary(5);
        _nbQuestions = _questionary.nbQuestions();

        // Start du service de génération de questionnaire
        Intent questionnaireGenerator = new Intent(QuestionaryActivity.this,GenerateQuestionaryService.class);
        questionnaireGenerator.putExtra("questionary", _questionary);
        startService(questionnaireGenerator);
        Log.d(TAG,"End OnCreate");
    }

    private class Receiver extends BroadcastReceiver
    {

        private Receiver()
        {}

        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent.getAction().equals(GenerateQuestionaryService.GenerateQuestionnaireActions.Broadcast))
            {
                _questionary = (Questionary) intent.getSerializableExtra("questionary");
                loadNextQuestion();
            }
        }
    }

  /**
   * Affiche la prochaine question du questionnaire et charge et génére le profil à la fin grâce au service
   * */
    void loadNextQuestion()
    {
        this._curQuestion++;
        if (this._curQuestion != _nbQuestions)
        {
            for (TextView tv : _choices)
            {
                tv.setVisibility(View.INVISIBLE);
                tv.setClickable(false);
                tv.setText(""); //Sans cette ligne, si la question précédente a plus de choix, on a toujours la ou les questions en plus qui apparaisent
            }

            Question quest = _questionary.questions().get(this._curQuestion);
            _totalWeightPossible += quest.bestWeight();
            _question.setText(quest.description());
            _questionCpt.setText(String.valueOf(this._curQuestion + 1).concat(" of ").concat(String.valueOf(_nbQuestions)));
            _fact.setText(quest.fact());

            int nbChoices = quest.possibleChoices().size();
            for (int i = 0; i < nbChoices; i++)
            {
                _choices.get(i).setVisibility(View.VISIBLE);
                _choices.get(i).setClickable(true);
                _choices.get(i).setText(quest.possibleChoices().get(i).description());
            }
        }
        else
        {
            Intent newActivity = new Intent(getApplicationContext(),QuestionarySummaryActivity.class);

            float scoreQuestionnaire = (float)_score / (float)_totalWeightPossible;

            newActivity.putExtra("fromLevelUP",_fromLevelUP);
            newActivity.putExtra("scoreQuestionnaire",scoreQuestionnaire);
            newActivity.putExtra("profile",_profile);

            this.finish();
            startActivity(newActivity);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_questionary, menu);
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
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        System.out.println("Bouton retour");

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(_receiver);
       // _receiver.abortBroadcast();
        _receiver = null;
        _questionnaireIntentFilter = null;
        for (Question q :_questionary.questions())
        {
            q.possibleChoices().clear();
        }
        _questionary.questions().clear();
        _questionary = null;

    }
}
