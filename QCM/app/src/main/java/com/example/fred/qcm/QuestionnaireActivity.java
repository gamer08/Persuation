package com.example.fred.qcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/*
* L'activité de questionnaire qui permet à l'utilisateur de se créer un profil.
*/
public class QuestionnaireActivity extends AppCompatActivity
{
    /*Other data*/
    private int _curQuestion, _nbQuestions, _score, _totalWeightPossible;
    private Questionnaire _questionnaire;
    private View.OnClickListener _choiceListener;

    /*Service related data*/
    private Receiver _receiver;
    private IntentFilter _questionnaireIntentFilter, _profilIntentFilter;


    /*Interesting View*/
    private ArrayList<TextView> _choices;
    private TextView _question;
    private TextView _questionCpt;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
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
                        _score += _questionnaire._questions.get(_curQuestion)._possibleChoices.get(index)._weight;
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
        _questionnaireIntentFilter = new IntentFilter(GenerateQuestionnaire.GenerateQuestionnaireActions.Broadcast);
        _profilIntentFilter = new IntentFilter(GenerateProfil.GenerateProfilActions.Broadcast);

        _receiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(_receiver,_questionnaireIntentFilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(_receiver,_profilIntentFilter);

        _questionnaire = new Questionnaire(1);
        _nbQuestions = _questionnaire._nbQuestions;


        // Start du service de génération de questionnaire
        Intent questionnaireGenerator = new Intent(this,GenerateQuestionnaire.class);
        questionnaireGenerator.putExtra("questionnaire", _questionnaire);
        startService(questionnaireGenerator);
    }

    private class Receiver extends BroadcastReceiver
    {

        private Receiver()
        {}

        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent.getAction().equals(GenerateQuestionnaire.GenerateQuestionnaireActions.Broadcast))
            {
                _questionnaire = (Questionnaire) intent.getSerializableExtra("questionnaire");
                loadNextQuestion();
            }
            else
            {
                Profil profil = (Profil)intent.getSerializableExtra("profil");
                Intent profilActivity = new Intent(getApplicationContext(),ProfilActivity.class);
                profilActivity.putExtra("profil",profil);
                startActivity(profilActivity);
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
            for (TextView tv : _choices)
                tv.setClickable(false);

            Question quest = _questionnaire._questions.get(_curQuestion);
            _totalWeightPossible += quest._bestWeight;
            _question.setText(_questionnaire._questions.get(_curQuestion)._description);
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
            Intent profilGenerator = new Intent(this,GenerateProfil.class);
            float scoreQuestionnaire = _score / _totalWeightPossible;
            profilGenerator.putExtra("scoreQuestionnaire", scoreQuestionnaire);
            startService(profilGenerator);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_questionnaire, menu);
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
}