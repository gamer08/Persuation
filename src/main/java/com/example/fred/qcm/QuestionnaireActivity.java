package com.example.fred.qcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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

public class QuestionnaireActivity extends AppCompatActivity
{

    private View.OnClickListener choiceListener;
    private Questionnaire _questionnaire;
    private Receiver _receiver;
    private IntentFilter _intentFilter;
    private ArrayList<TextView> _choices;
    private TextView _question;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        choiceListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Test(String.valueOf(v.getId()));
            }
        };

        _question = (TextView) findViewById(R.id.TV_Question);

        _choices = new ArrayList<TextView>();

        _choices.add((TextView) findViewById(R.id.TV_Choice1));
        _choices.add((TextView) findViewById(R.id.TV_Choice2));
        _choices.add((TextView) findViewById(R.id.TV_Choice3));
        _choices.add((TextView) findViewById(R.id.TV_Choice4));
        //_choices.add((TextView) findViewById(R.id.TV_Choice5));

        for (TextView tv : _choices)
            tv.setOnClickListener(choiceListener);

        _intentFilter = new IntentFilter(GenerateQuestionnaire.GenerateQuestionnaireActions.Broadcast);
        _receiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(_receiver,_intentFilter);

        _questionnaire = new Questionnaire();
        Intent generator = new Intent(this,GenerateQuestionnaire.class);
        generator.putExtra("questionnaire", _questionnaire);
        startService(generator);
    }

    private class Receiver extends BroadcastReceiver
    {

        private Receiver()
        {}

        @Override
        public void onReceive(Context context, Intent intent)
        {

            _questionnaire = (Questionnaire)intent.getSerializableExtra("questionnaire");
            UpdateActivityInterface();
        }
    }

    void Test(String btn)
    {
        Toast.makeText(getApplicationContext(), btn, Toast.LENGTH_LONG).show();
    }

    void UpdateActivityInterface()
    {
        _question.setText(_questionnaire._questions.get(0)._description);
        _choices.get(0).setText(_questionnaire._questions.get(0)._possibleChoices.get(0)._description);
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
