package com.example.fred.qcm;

import java.util.ArrayList;

/**
 * Classe représentant une question du questionnaire
 */
public class Question
{
    public int _id;
    public String _description;
    public ArrayList<Choice> _possibleChoices;
    public int _bestWeight;

    public Question()
    {
        _id = 0;
        _description = "";
        _possibleChoices = new ArrayList<Choice>();
        _bestWeight = 0;
    }
}
