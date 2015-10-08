package com.example.fred.qcm;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;


public class GenerateQuestionnaire extends IntentService
{
    private int _nbQuestionsTotal;
    private int _nbQuestionsQuestionnaire;
    private int _curQuestionID;
    private int _curQuestion;
    private static final int QUESTIONS_ALL_LOADED =-1;
    private int[] _questionsID;


    public final class GenerateQuestionnaireActions
    {
        public static final String Broadcast ="Questionnaire_Broadcast";

    }

    public GenerateQuestionnaire()
    {
        super("GenerateQuestionnaire");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Questionnaire questionnaire = (Questionnaire) intent.getSerializableExtra("questionnaire");

        try
        {
            Generate(questionnaire);
            Intent callBackIntent = new Intent(GenerateQuestionnaireActions.Broadcast);
            callBackIntent.putExtra("questionnaire",questionnaire);

            LocalBroadcastManager.getInstance(this).sendBroadcast(callBackIntent);
        }
        catch(XmlPullParserException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    void Generate(Questionnaire questionnaire) throws XmlPullParserException, IOException
    {
        _questionsID = new int[questionnaire._nbQuestions];
        _nbQuestionsQuestionnaire = questionnaire._nbQuestions;
        _curQuestion = 0;

        XmlResourceParser parser = getApplicationContext().getResources().getXml(R.xml.questions);
        int eventType = parser.getEventType();
        while (_curQuestionID != QUESTIONS_ALL_LOADED && eventType != XmlPullParser.END_DOCUMENT)
        {
            switch (eventType)
            {
                case XmlPullParser.START_TAG:

                    if (parser.getName().equals("Number"))
                    {
                        _nbQuestionsTotal = Integer.valueOf(parser.nextText());
                        GenerateQuestionsID(questionnaire._nbQuestions);
                    }
                    else if (parser.getName().equals("Questions"))
                        questionnaire._questions = ParseQuestions(parser);

                    break;

                default:
                    break;
            }
            eventType = parser.next();
        }
    }

    ArrayList<Question> ParseQuestions( XmlPullParser parser) throws XmlPullParserException, IOException
    {
        int eventType = parser.next();

        ArrayList<Question> questions = new ArrayList<Question>();
        Question question = null;

        while (_curQuestionID != QUESTIONS_ALL_LOADED && !parser.getName().equals("Questions"))
        {
            switch (eventType)
            {
                case XmlPullParser.START_TAG:

                    if (parser.getName().equals("Question"))
                        question = new Question();
                    else if (question != null)
                    {
                        if (parser.getName().equals("ID"))
                        {
                            if(_curQuestionID != Integer.valueOf(parser.nextText()))
                            question = null;
                        }
                        else if (parser.getName().equals("Description"))
                            question._description = parser.nextText();
                        else if(parser.getName().equals("Choices"))
                            question._possibleChoices = ParseChoices(parser);
                    }

                    break;

                case XmlPullParser.END_TAG:

                    if ((parser.getName().equals("Question")) && question!= null)
                    {
                        questions.add(question);
                        _curQuestionID = GetNextQuestionID();
                    }

                    break;

                default:
                    break;
            }
           eventType = parser.next();
        }
        return questions;
    }

    ArrayList<Choice> ParseChoices( XmlPullParser parser) throws XmlPullParserException, IOException
    {
        int eventType = parser.next();
        ArrayList<Choice> choices = new ArrayList<Choice>();
        Choice choice = null;

        while (!parser.getName().equals("Choices"))
        {
            switch (eventType)
            {
                case XmlPullParser.START_TAG:

                    if (parser.getName().equals("Choice"))
                        choice = new Choice();

                    else if (choice != null)
                    {
                        if (parser.getName().equals("Description"))
                            choice._description = parser.nextText();
                        else if (parser.getName().equals("Weight"))
                            choice._weight = Integer.valueOf(parser.nextText());
                    }
                    break;

                case XmlPullParser.END_TAG:

                    if (parser.getName().equals("Choice") && (choice !=null))
                        choices.add(choice);

                    break;

                default:
                    break;
            }
          eventType =  parser.next();
        }

        return choices;
    }

    void GenerateQuestionsID(int nbQuestionsInQuestionnaire)
    {
        int slice = _nbQuestionsTotal / nbQuestionsInQuestionnaire;
        int lastID =0;
        for (int i =0;i < nbQuestionsInQuestionnaire ; i++)
        {
            Random rand = new Random();
            int nextID = rand.nextInt(slice - lastID) + lastID;
            lastID = nextID;
            _questionsID[i] = nextID;

            if ((nbQuestionsInQuestionnaire-(i+1)) != 0)
            slice = (_nbQuestionsTotal - (lastID + 1)) / (nbQuestionsInQuestionnaire-(i+1));
            else
                slice =0;
        }

        _curQuestionID = _questionsID[0];
    }

    int GetNextQuestionID()
    {
        _curQuestion++;

        if (_curQuestion >= 0 && _curQuestion < _nbQuestionsQuestionnaire)
            return _questionsID[_curQuestion];
        else
            return QUESTIONS_ALL_LOADED;
    }
}
