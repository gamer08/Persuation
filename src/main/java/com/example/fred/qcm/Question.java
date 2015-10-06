package com.example.fred.qcm;

import java.util.ArrayList;

/**
 * Created by Fred on 2015-10-03.
 */
public class Question
{
    public String _description;
    public ArrayList<Choice> _possibleChoices;

    public Question()
    {
        _description = "";
        _possibleChoices = new ArrayList<Choice>();
    }
}
