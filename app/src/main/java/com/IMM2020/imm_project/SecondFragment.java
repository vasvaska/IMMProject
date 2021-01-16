package com.IMM2020.imm_project;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class SecondFragment extends Fragment {

    private Handler mHandler = new Handler();
    //LineGraphSeries<DataPoint>  series0, series1, series2, series3, series4, series5, series6, series7  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series0  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series1  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series2  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series3  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series4  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series5  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series6  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series7  = new LineGraphSeries<>(new DataPoint[]{});

    LineGraphSeries[] emgSeries = new LineGraphSeries[]{series0, series1, series2, series3, series4, series5, series6, series7};
    private int cur = 0;
    private int[][] emgDataSet;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GraphView graph = getView().findViewById(R.id.graph);
        emgDataSet = DataParser.parseData(getContext(), getActivity());

        GridLabelRenderer glr = graph.getGridLabelRenderer();
        glr.setPadding(60); // should allow for 3 digits to fit on screen


        int[] color = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.LTGRAY, Color.CYAN, Color.MAGENTA, Color.BLACK};
        for (int i = 0; i < 8; i++) {
            graph.addSeries(emgSeries[i]);
            emgSeries[i].setColor(color[i]);
        }

        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(emgDataSet.length*1.5);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(-100);
        graph.getViewport().setMaxY(100);
        graph.getViewport().setYAxisBoundsManual(true);

        graph.getViewport().setScalable(true);
        graph.getGridLabelRenderer().setTextSize(45f);
        graph.getGridLabelRenderer().reloadStyles();

        addDataPoints();


        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }




    private void addDataPoints(){
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 8; i++) { emgSeries[i].appendData(new DataPoint(cur*5, emgDataSet[cur][i]), false, emgDataSet.length); }
                if(cur++<emgDataSet.length-1){addDataPoints(); }
            }
        }, 5);
    }
}