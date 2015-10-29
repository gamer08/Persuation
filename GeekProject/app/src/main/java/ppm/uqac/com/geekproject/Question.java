package ppm.uqac.com.geekproject;

import java.util.ArrayList;

/**
 * Classe représentant une question du questionnaire
 */
public class Question
{
    public String _description;
    public ArrayList<Choice> _possibleChoices;
    public String _fact;
    public int _bestWeight;


    public Question()
    {
        _description = "";
        _possibleChoices = new ArrayList<Choice>();
        _fact = "";
        _bestWeight = 0;
    }
}
