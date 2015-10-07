package com.example.fred.qcm;

import java.util.ArrayList;

/**
 * Created by Fred on 2015-10-03.
 */
public class Question
{
    public int _id;
    public String _description;
    public ArrayList<Choice> _possibleChoices;

    public Question()
    {
        _id = 0;
        _description = "";
        _possibleChoices = new ArrayList<Choice>();
    }
}
