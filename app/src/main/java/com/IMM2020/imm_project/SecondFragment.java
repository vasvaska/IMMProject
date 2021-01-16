package com.IMM2020.imm_project;

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
    LineGraphSeries<DataPoint> series;
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


        double y;
        double x;

        GraphView graphView = (GraphView) getView().findViewById(R.id.graph);

        GridLabelRenderer glr = graphView.getGridLabelRenderer();
        glr.setPadding(60); // should allow for 3 digits to fit on screen

        series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(1,1),
                new DataPoint(2,2),
                new DataPoint(3,1)
        });
        graphView.addSeries(series);

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
                series.appendData(new DataPoint(cur, new Random().nextInt(10)), false, 200);
                if(cur<=1000){addDataPoints();}
            }
        }, 100);
    }
}