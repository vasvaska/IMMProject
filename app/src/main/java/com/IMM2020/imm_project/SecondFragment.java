package com.IMM2020.imm_project;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;

public class SecondFragment extends Fragment {

    private Handler mHandler = new Handler();
    LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint> series5 = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint> series6 = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint> series7 = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint> series8 = new LineGraphSeries<>(new DataPoint[]{});
    private double cur = 0;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.i("Myo","MYOHERE");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GraphView graphView = getView().findViewById(R.id.graph);

        GridLabelRenderer glr = graphView.getGridLabelRenderer();
        glr.setPadding(60); // should allow for 3 digits to fit on screen

        /*series1 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(1,1),
                new DataPoint(2,2),
                new DataPoint(3,1)
        });
        series2 = new LineGraphSeries<>(new DataPoint[]{});*/

        graphView.addSeries(series1);
        series1.setColor(Color.RED);
        graphView.addSeries(series2);
        series1.setColor(Color.GREEN);

        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(1000);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(10);
        graphView.getViewport().setYAxisBoundsManual(true);

        graphView.getViewport().setScalable(true);

        addDataPoints();

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        //Toast.makeText(view.getContext(), "HELLO WORLD", Toast.LENGTH_LONG).show();
    }

    private void addDataPoints(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cur+=10;
                series1.appendData(new DataPoint(cur, new Random().nextInt(10)), false, 200);
                series2.appendData(new DataPoint(cur, new Random().nextInt(10)), false, 200);
                if(cur<=1000){addDataPoints();}
            }
        }, 25);
    }
}