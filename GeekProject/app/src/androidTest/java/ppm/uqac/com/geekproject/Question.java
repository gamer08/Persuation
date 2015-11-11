package ppm.uqac.com.geekproject;

import java.util.ArrayList;

/**
 * Classe représentant une question du questionnaire
 */
public class Question
{
    public int _id;
    public String _description;
    public ArrayList<ppm.uqac.com.geekproject.questionary.Choice> _possibleChoices;
    public int _bestWeight;

    public Question()
    {
        _id = 0;
        _description = "";
        _possibleChoices = new ArrayList<ppm.uqac.com.geekproject.questionary.Choice>();
        _bestWeight = 0;
    }
}
