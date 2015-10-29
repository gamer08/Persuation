package ppm.uqac.com.geekproject;

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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/*
* L'activité de questionnaire qui permet à l'utilisateur de se créer un profil.
*/
public class QuestionaryActivity extends AppCompatActivity
{
    /*Other data*/
    private int _curQuestion, _nbQuestions, _score, _totalWeightPossible;
    private Questionary _questionary;
    private View.OnClickListener _choiceListener;

    /*Service related data*/
    private Receiver _receiver;
    private IntentFilter _questionnaireIntentFilter, _profilIntentFilter;


    /*Interesting View*/
    private ArrayList<TextView> _choices;
    private TextView _question;
    private TextView _questionCpt;

    /*Pour afficher des messages dans les logs*/
    private static final String TAG = "QuestionaryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //Log.d(TAG, "OnCreate");

        super.onCreate(savedInstanceState);
        Toast.makeText(this, "QuestionaryA.onCreate", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_questionary);
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
                        _score += _questionary._questions.get(_curQuestion)._possibleChoices.get(index)._weight;
                        Log.d(TAG,"LoadedNextQuestion");
                        loadNextQuestion();
                    }
                }
            }
        };

        _curQuestion = -1;
        _score = _totalWeightPossible = 0 ;

        // Références vers les objets de l'Activité
        _question = (TextView) findViewById(R.id.TV_Question);
        _questionCpt = (TextView) findViewById(R.id.TV_curQuestionCpt);

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
        _profilIntentFilter = new IntentFilter(GenerateProfileService.GenerateProfilActions.Broadcast);

        _receiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(_receiver,_questionnaireIntentFilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(_receiver,_profilIntentFilter);

        _questionary = new Questionary(1);
        _nbQuestions = _questionary._nbQuestions;

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
            else
            {
                Profile profile = (Profile)intent.getSerializableExtra("profile");
                Intent nextActivity;

                switch(profile._type)
                {
                    case ANTIGEEK:
                    case GEEKPERSECUTOR:
                    case NEUTRAL:
                    case GEEKFRIENDLY:
                    case GEEK:
                        nextActivity = new Intent(getApplicationContext(),CreationProfileActivity.class);
                        break;

                    default:
                        nextActivity = new Intent(getApplicationContext(),MainActivity.class);
                        nextActivity.putExtra("activite","questionnaire");
                        break;
                }

                nextActivity.putExtra("profile", profile);
                QuestionaryActivity.this.finish();
                System.out.println("valeur profil avant start profilactivity "+profile._score);
                startActivity(nextActivity);
            }
        }
    }

  /**
   * Affiche la prochaine question du questionnaire et charge et génére le profil à la fin grâce au service
   * */
    void loadNextQuestion()
    {
        _curQuestion++;
        if (_curQuestion != _nbQuestions)
        {
            for (TextView tv : _choices) {
                tv.setClickable(false);
                tv.setText(""); //Sans cette ligne, si la question précédente a plus de choix, on a toujours la ou les questions en plus qui apparaisent
            }
            Question quest = _questionary._questions.get(_curQuestion);
            _totalWeightPossible += quest._bestWeight;
            _question.setText(_questionary._questions.get(_curQuestion)._description);
            _questionCpt.setText(String.valueOf(_curQuestion + 1).concat(" of ").concat(String.valueOf(_nbQuestions)));

            int nbChoices = quest._possibleChoices.size();
            for (int i = 0; i < nbChoices; i++)
            {
                _choices.get(i).setClickable(true);
                _choices.get(i).setText(quest._possibleChoices.get(i)._description);
            }
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Création d'un profil");
            builder.setMessage("Voulez-vous créer un profil ?");
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id)
                {
                    createProfile(false);
                }
            });

            builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id)
                {
                    createProfile(true);
                }
            });

            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.show();



        }
    }

    public void createProfile(boolean isDummyProfile)
    {
        float scoreQuestionnaire;

        if (!isDummyProfile)
            scoreQuestionnaire = _score / _totalWeightPossible;
        else
            scoreQuestionnaire = Float.MIN_VALUE; // non va donner un profil guest

        Intent profilGenerator = new Intent(this, GenerateProfileService.class);
        profilGenerator.putExtra("scoreQuestionnaire", scoreQuestionnaire);
        startService(profilGenerator);
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
}
