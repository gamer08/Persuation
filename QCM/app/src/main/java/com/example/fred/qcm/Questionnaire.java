package com.example.fred.qcm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe représentant le questionnaire
 */
public class Questionnaire implements Serializable
{
    public ArrayList<Question> _questions;
    public int _nbQuestions;

    private Questionnaire()
    {
        _questions = new ArrayList<Question>();
        _nbQuestions = 0;
    }

    public Questionnaire(int nbQuestions)
    {
        _questions = new ArrayList<Question>();
        _nbQuestions = nbQuestions;
    }
}
