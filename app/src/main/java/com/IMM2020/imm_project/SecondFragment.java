package com.IMM2020.imm_project;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;

public class SecondFragment extends Fragment implements AdapterView.OnItemSelectedListener {

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
    int[] color = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.LTGRAY, Color.CYAN, Color.MAGENTA, Color.BLACK};

    private CheckBox[] checkBoxes;

    GraphView graph;

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

        graph = getView().findViewById(R.id.graph);
        checkBoxes = new CheckBox[]{getView().findViewById(R.id.checkBox0), getView().findViewById(R.id.checkBox1), getView().findViewById(R.id.checkBox2), getView().findViewById(R.id.checkBox3),
                getView().findViewById(R.id.checkBox4), getView().findViewById(R.id.checkBox5), getView().findViewById(R.id.checkBox6), getView().findViewById(R.id.checkBox7)};



        // add all series again

        for (int i = 0; i < 8; i++) {
            graph.addSeries(emgSeries[i]);
            emgSeries[i].setColor(color[i]);
            checkBoxes[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    for (int i = 0; i < 8; i++) {
                        if(buttonView == checkBoxes[i]) {
                            if(!isChecked){emgSeries[i].setColor(Color.TRANSPARENT);}
                            else{ emgSeries[i].setColor(color[i]);}
                            Log.i("Checked", ""+emgSeries[i]);
                            return;
                        }
                    }
                }
            });
        }

        Spinner spinner = getView().findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(getContext(), R.array.gestures, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item));
        spinner.setAdapter(adapter);
        //spinner.setOn
        spinner.setOnItemSelectedListener(this);

        GridLabelRenderer glr = graph.getGridLabelRenderer();
        glr.setPadding(60); // should allow for 3 digits to fit on screen

        graph.getViewport().setScalable(true);
        graph.getGridLabelRenderer().setTextSize(45f);
        graph.getGridLabelRenderer().reloadStyles();


        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    private void drawGraph(int index){
        emgDataSet = DataParser.parseData(getContext(), getActivity(), index);


        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(emgDataSet.length*1.5);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(-100);
        graph.getViewport().setMaxY(100);
        graph.getViewport().setYAxisBoundsManual(true);

        mHandler.removeCallbacksAndMessages(null);

        addDataPoints();
    }


    private void addDataPoints(){

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 8; i++) { emgSeries[i].appendData(new DataPoint(cur*5, emgDataSet[cur][i]), true, emgDataSet.length); }
                if(cur++<emgDataSet.length-10){addDataPoints(); }
            }
        }, 5);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //for (int i = 0; i < 8; i++) { emgSeries[i].resetData(new DataPointInterface[])}
        for (int i = 0; i < 8; i++) {
            emgSeries[i].resetData(new DataPointInterface[]{});
            //emgSeries[i].setColor(Color.TRANSPARENT);
        }

        drawGraph(position);
        cur = 0;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        for (int i = 0; i < 8; i++) {
            if(view == checkBoxes[i]) {
                if(checked){emgSeries[i].setColor(Color.TRANSPARENT);}
                else{ emgSeries[i].setColor(color[i]);}
                return;
            }
        }
    }*/
}