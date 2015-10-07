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


public class GenerateQuestionnaire extends IntentService
{
    private int _nbQuestionsTotal;
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

        /*Question test = new Question("this is a test");
        test._possibleChoices.add(new Choice("this is a choice test",2));
        questionnaire._questions.add(test);

        Intent callBackIntent = new Intent(GenerateQuestionnaireActions.Broadcast);
        callBackIntent.putExtra("questionnaire",questionnaire);

        LocalBroadcastManager.getInstance(this).sendBroadcast(callBackIntent);*/

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
       // XmlPullParser parser = Xml.newPullParser();

        //XmlPullParser parser = getApplicationContext().getResources().getXml(R.xml.questions);
        //parser.setInput(in, null);
       // parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        //parser.nextTag();

       /* parser.require(XmlPullParser.START_TAG, ns, "Questions");
        while (parser.next() != XmlPullParser.END_TAG)
        {
            if (parser.getEventType() != XmlPullParser.START_TAG)
                continue;
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("Question"))
                questionnaire._questions.add(ParseQuestion(parser));
        }*/

        _questionsID = new int[questionnaire._nbQuestions];

        XmlResourceParser parser = getApplicationContext().getResources().getXml(R.xml.questions);
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            switch (eventType)
            {
                case XmlPullParser.START_TAG:

                    if (parser.getName().equals("Number"))
                    {
                        _nbQuestionsTotal = Integer.valueOf(parser.nextText());
                    }

                    if (parser.getName().equals("Questions"))
                        questionnaire._questions = ParseQuestions(parser);

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

        while (!parser.getName().equals("Questions"))
        {
            switch (eventType)
            {
                case XmlPullParser.START_TAG:

                    if (parser.getName().equals("Question"))
                        question = new Question();

                    else if (question != null)
                    {
                        if (parser.getName().equals("Description"))
                            question._description = parser.nextText();


                        if (parser.getName().equals("Choices"))
                            question._possibleChoices = ParseChoices(parser);
                    }

                    break;

                case XmlPullParser.END_TAG:

                    if ((parser.getName().equals("Question")) && question!= null)
                        questions.add(question);

                    break;

                default:
                    break;
            }
           eventType = parser.next();
        }
        return questions;

        /*parser.require(XmlPullParser.START_TAG, ns, "Question");
        while (parser.next() != XmlPullParser.END_TAG)
        {
            if (parser.getEventType() != XmlPullParser.START_TAG)
                continue;

            String name = parser.getName();
            if (name.equals("Description"))
            {
                question._description =  parser.nextText();
               // parser.next();
            }
            else if (name.equals("Choices"))
            {
                question._possibleChoices = ParseChoices(parser);
                //parser.next();
            }

            parser.nextTag();

        }*/
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

                        if (parser.getName().equals("Weight"))
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
        /*parser.require(XmlPullParser.START_TAG, ns, "Choices");

        while (parser.next() != XmlPullParser.END_TAG)
        {
            if (parser.getEventType() != XmlPullParser.START_TAG)
                continue;

            String name = parser.getName();
            if (name.equals("Choice"))
            {
                choices.add(ParseChoice(parser));
            }

            parser.nextTag();
        }*/
    }

    void GenerateQuestionsID(int nbQuestionsInQuestionnaire)
    {
        int slice = _nbQuestionsTotal / nbQuestionsInQuestionnaire;
        int lastID =0;
        for (int i =0;i < nbQuestionsInQuestionnaire ; i++)
        {


        }

    }

   /* Choice ParseChoice( XmlPullParser parser) throws XmlPullParserException, IOException
    {
        Choice choice = new Choice();

        parser.require(XmlPullParser.START_TAG, ns, "Choice");

        while (parser.next() != XmlPullParser.END_TAG)
        {
            if (parser.getEventType() != XmlPullParser.START_TAG)
                continue;

            String name = parser.getName();
            if (name.equals("Description"))
            {
                choice._description = parser.nextText();
            }
            else if (name.equals("Weight"))
            {
                choice._weight = Integer.valueOf(parser.nextText());
            }

            parser.nextTag();
        }

        return choice;
    }*/
}
