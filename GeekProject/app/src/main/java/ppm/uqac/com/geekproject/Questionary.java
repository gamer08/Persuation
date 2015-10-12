package ppm.uqac.com.geekproject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe représentant le questionnaire
 */
public class Questionary implements Serializable
{
    public ArrayList<Question> _questions;
    public int _nbQuestions;

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
}
