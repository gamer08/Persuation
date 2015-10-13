package ppm.uqac.com.geekproject;

import android.app.IntentService;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Service qui permet de générer un questionnaire à partir d'un fichier XML
 */

public class GenerateQuestionary extends IntentService
{

    private static final String TAG = "Generate questionary";


    private int _nbQuestionsTotal;
    private int _nbQuestionsQuestionnaire;
    private int _nextQuestionID;
    private int _curQuestion;
    private static final int QUESTIONS_ALL_LOADED =-1;
    private int[] _questionsID;


    public final class GenerateQuestionnaireActions
    {
        public static final String Broadcast ="Questionnaire_Broadcast";
    }

    public GenerateQuestionary()
    {
        super("GenerateQuestionary");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Questionary questionary = (Questionary) intent.getSerializableExtra("questionary");
        Log.d(TAG, "questionary generated !");

        try
        {
            generate(questionary);
            Intent callBackIntent = new Intent(GenerateQuestionnaireActions.Broadcast);
            callBackIntent.putExtra("questionary", questionary);

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

    /**
     * Fonction principale pour la génération du questionary
     * @param questionary le questionary a construire
     */

    void generate(Questionary questionary) throws XmlPullParserException, IOException
    {
        _questionsID = new int[questionary._nbQuestions];
        _nbQuestionsQuestionnaire = questionary._nbQuestions;
        _curQuestion = 0;

        XmlResourceParser parser = getApplicationContext().getResources().getXml(R.xml.questions);
        int eventType = parser.getEventType();
        while (_nextQuestionID != QUESTIONS_ALL_LOADED)
        {
            switch (eventType)
            {
                case XmlPullParser.START_TAG:

                    if (parser.getName().equals("Number"))
                    {
                        _nbQuestionsTotal = Integer.valueOf(parser.nextText());
                        generateQuestionsID(questionary._nbQuestions);
                    }
                    else if (parser.getName().equals("Questions"))
                        questionary._questions = parseQuestions(parser);

                    break;

                default:
                    break;
            }
            eventType = parser.next();
        }
    }

    /**
     * Fonction qui permet de récupérer toutes les questions pour le questionnaire
     * @param parser le parser pour extraire les données
     * @return la liste de questions pour le questionnaire
     */
    ArrayList<Question> parseQuestions( XmlPullParser parser) throws XmlPullParserException, IOException
    {
        int eventType = parser.next();

        ArrayList<Question> questions = new ArrayList<Question>();
        Question question = null;

        while (_nextQuestionID != QUESTIONS_ALL_LOADED)
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
                            if(_nextQuestionID != Integer.valueOf(parser.nextText()))
                            question = null;
                        }
                        else if (parser.getName().equals("BestWeight"))
                            question._bestWeight = Integer.valueOf(parser.nextText());
                        else if (parser.getName().equals("Description"))
                            question._description = parser.nextText();
                        else if(parser.getName().equals("Choices"))
                            question._possibleChoices = parseChoices(parser);
                    }

                    break;

                case XmlPullParser.END_TAG:

                    if ((parser.getName().equals("Question")) && question!= null)
                    {
                        questions.add(question);
                        _nextQuestionID = getNextQuestionID();
                    }

                    break;

                default:
                    break;
            }
           eventType = parser.next();
        }
        return questions;
    }

    /**
     * Fonction qui permet de récupérer toutes les questions pour le questionnaire
     * @param parser le parser pour extraire les données
     * @return la liste de choix pour une question
     */
    ArrayList<Choice> parseChoices( XmlPullParser parser) throws XmlPullParserException, IOException
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

    /**
     * Fonction qui permet de générer les ID des questions qui seront sélectionnées pour le questionnaire
     * @param nbQuestionsInQuestionnaire le nombre de questions désirées
     */
    void generateQuestionsID(int nbQuestionsInQuestionnaire)
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

        _nextQuestionID = _questionsID[0];
    }

    /**
     * Fonction qui retourne le prochain ID de question à sélectionner
     * @return l'iD de la prochaine question
     */
    int getNextQuestionID()
    {
        _curQuestion++;

        if (_curQuestion >= 0 && _curQuestion < _nbQuestionsQuestionnaire)
            return _questionsID[_curQuestion];
        else
            return QUESTIONS_ALL_LOADED;
    }
}
