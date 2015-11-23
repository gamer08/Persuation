package ppm.uqac.com.geekproject.questionary;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe représentant le questionnaire
 */
public class Questionary implements Serializable
{
    private ArrayList<Question> _questions;
    private int _nbQuestions;

    private Questionary()
    {
        _questions = new ArrayList<Question>();
        _nbQuestions = 0;
    }

    public Questionary(int nbQuestions)
    {
        _questions = new ArrayList<Question>();
        _nbQuestions = nbQuestions;
    }


    public ArrayList<Question> questions()
    {
        return _questions;
    }

    public void setQuestions(ArrayList<Question> questions)
    {
        _questions = questions;
    }

    public int nbQuestions()
    {
        return _nbQuestions;
    }

    public void setNBQuestion(int value)
    {
        _nbQuestions = value;
    }
}
