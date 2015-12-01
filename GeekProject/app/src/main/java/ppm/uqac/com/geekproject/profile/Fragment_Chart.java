package ppm.uqac.com.geekproject.profile;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

import ppm.uqac.com.geekproject.R;

/**
 * Created by Valentin on 22/11/2015.
 */
public class Fragment_Chart extends Fragment {

    View rootview;
    LineChart mChart;
    private Profile _profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        rootview = inflater.inflate(R.layout.fragment_chart,container,false);
        mChart = (LineChart) rootview.findViewById(R.id.chart1);
        createChart();
        return rootview;


    }

    public void createChart()
    {

        /*Calendar c = Calendar.getInstance();
        int days = c.get(Calendar.DAY_OF_YEAR);*/

        //Calendar now = Calendar.getInstance();

        /*ArrayList<String> xVals = new ArrayList<String>();
        int count = 5;
        int range = 5;
        for (int i = 0; i < 5; i++) {
            //xVals.add((i) + "lal");
            xVals.add(now.get(Calendar.DATE)+i + "." + (now.get(Calendar.MONTH)+1) +"" );
        }*/

        /*ArrayList<String> xVals = new ArrayList<String>();

        ArrayList<Entry> yVals = new ArrayList<Entry>();*/

        /*for (int i = 1; i < 6; i++) {

            float mult = (range + 1);
            float val = (float) (Math.random() * mult) + 3;// + (float)
            // ((mult *
            // 0.1) / 10);
            yVals.add(new Entry(val, i));
        }*/

        Intent intent = new Intent(getActivity().getIntent());
        //Intent intent = getIntent();
        if (intent != null)
        {
            _profile = (Profile) intent.getSerializableExtra("profile");
        }


        System.out.println("In Fragment_Chart- Scores = " + _profile._scores);

        // Parcours de la table des scores


        /*for (int i = 1; i<=_profile._scores.size(); i++)
        {
            System.out.println("Score: " + _profile._scores.get(i - 1) + " et Numero = " + i);
            xVals.add(i+"");
            yVals.add(new Entry(_profile._scores.get(i-1), i));
        }*/


        // create a dataset and give it a type
        /*LineDataSet set1 = new LineDataSet(yVals, "Résultat des questionnaires");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        //set1.setColor(Color.BLUE);
        //set1.setCircleColor(Color.RED);
        set1.setLineWidth(1f);
        set1.setCircleSize(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        //set1.setFillAlpha(65);
        set1.setFillColor(Color.GREEN);


        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data

        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.getAxisRight().setEnabled(false);

        mChart.setData(data);*/

        // Liste d'entrées

        ArrayList<Entry> points = new ArrayList<Entry>();

        // Abscisses

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 1; i<=10; i++)
        {
            xVals.add(i + "");
        }


        for (int i = 1; i<_profile._scores.size()+1; i++)
        {
            System.out.println("Nombre de questionnaires = " +_profile._scores.size());

            points.add(new Entry(_profile._scores.get(i-1), i-1));

        }

        // LineSet créé à partir de ces entrées

        LineDataSet set = new LineDataSet(points, "Résultats des questionnaires");


        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        set.setCircleColor(Color.RED);
        set.setColor(Color.BLUE);

        LineData data = new LineData(xVals, set);

        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setAxisMaxValue(100);
        mChart.getAxisRight().setEnabled(false);
        mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChart.setData(data);
        mChart.invalidate();




    }


}
