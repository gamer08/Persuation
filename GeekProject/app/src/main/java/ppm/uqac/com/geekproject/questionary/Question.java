package ppm.uqac.com.geekproject.questionary;

import java.util.ArrayList;

/**
 * Classe représentant une question du questionnaire
 */
public class Question
{
    private String _description;
    private ArrayList<Choice> _possibleChoices;
    private String _fact;
    private int _bestWeight;

    public Question()
    {
        _description = "";
        _possibleChoices = new ArrayList<Choice>();
        _fact = "";
        _bestWeight = 0;
    }

    public String description()
    {
        return _description;
    }

    public void setDescription(String value)
    {
        _description = value;
    }

    public ArrayList<Choice> possibleChoices()
    {
        return _possibleChoices;
    }

    public void setPossibleChoices(ArrayList<Choice> choices)
    {
        _possibleChoices = choices;
    }

    public String fact()
    {
        return _fact;
    }

    public void setFact(String value)
    {
        _fact = value;
    }

    public int bestWeight()
    {
        return _bestWeight;
    }

    public void setBestWeight(int value)
    {
        _bestWeight = value;
    }
}
