package com.example.fred.qcm;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Fred on 2015-10-04.
 */
public class Questionnaire implements Serializable
{
    public ArrayList<Question> _questions;

    public Questionnaire()
    {
        _questions = new ArrayList<Question>();
    }
}
