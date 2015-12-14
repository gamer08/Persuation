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

import ppm.uqac.com.geekproject.R;

/**
 * Created by Valentin on 22/11/2015.
 */
public class Fragment_Chart extends Fragment {

    View rootview;
    LineChart mChart;
    private Profile _profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootview = inflater.inflate(R.layout.fragment_chart,container,false);
        mChart = (LineChart) rootview.findViewById(R.id.chart1);
        createChart();
        return rootview;
    }

    public void createChart()
    {
        Intent intent = new Intent(getActivity().getIntent());
        //Intent intent = getIntent();
        if (intent != null)
            _profile = (Profile) intent.getSerializableExtra("profile");

        System.out.println("In Fragment_Chart- Scores = " + _profile._scores);

        // Liste d'entrées
        ArrayList<Entry> points = new ArrayList<Entry>();

        // Abscisses
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 1; i<=10; i++)
            xVals.add(i + "");

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