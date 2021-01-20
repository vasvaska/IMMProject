package com.IMM2020.imm_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;

public class FirstFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private final Handler mHandler = new Handler();
    private Runnable mHandlerTask;

    LineGraphSeries<DataPoint> series0, series1, series2, series3, series4, series5, series6, series7;
    /*LineGraphSeries<DataPoint>  series0  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series1  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series2  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series3  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series4  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series5  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series6  = new LineGraphSeries<>(new DataPoint[]{});
    LineGraphSeries<DataPoint>  series7  = new LineGraphSeries<>(new DataPoint[]{});*/

    LineGraphSeries[] emgSeries = new LineGraphSeries[]{series0, series1, series2, series3, series4, series5, series6, series7};

    private int cur = 0;
    private int[][] emgDataSet;
    int[] color = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.LTGRAY, Color.CYAN, Color.MAGENTA, Color.BLACK};

    private CheckBox[] checkBoxes;

    GraphView graph;
    boolean graphPaused = true;
    Spinner spinner;
    ArrayAdapter adapter;
    private final boolean ismoving = false;
    private String fileName;
    private Uri filePath;

    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("section number", 1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        for (int i = 0; i < 8; i++) {
            emgSeries[i] = new LineGraphSeries<>(new DataPoint[]{});
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @SuppressLint("ClickableViewAccessibility")
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
                        if (buttonView == checkBoxes[i]) {
                            if (!isChecked) {
                                emgSeries[i].setColor(Color.TRANSPARENT);
                            } else {
                                emgSeries[i].setColor(color[i]);
                            }
                            graph.onDataChanged(true, false); //to change lines visibility whn graph is still
                            Log.i("Checked", "" + emgSeries[i]);
                            return;
                        }
                    }
                }
            });
        }


        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphPaused = !graphPaused;
                if (graphPaused) {
                    mHandler.removeCallbacks(mHandlerTask);
                } else {
                    addDataPoints();
                }
            }
        });


        spinner = getView().findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(
                getContext(), R.array.gestures, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item));
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        GridLabelRenderer glr = graph.getGridLabelRenderer();
        glr.setPadding(60); // should allow for 3 digits to fit on screen

        graph.getViewport().setScalable(true);

        glr.setTextSize(40f);
        glr.setHorizontalAxisTitle("Time /ms");
        glr.setVerticalAxisTitle("Voltage / μV");
        glr.setNumHorizontalLabels(5);
        glr.reloadStyles();


    }

    private void drawGraph(int index) {
        emgDataSet = DataParser.parseData(getContext(), getActivity(), index);

        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(emgDataSet.length * 1.5);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(-100);
        graph.getViewport().setMaxY(100);
        graph.getViewport().setYAxisBoundsManual(true);

        mHandler.removeCallbacksAndMessages(null);

        addDataPoints();
    }

    private void addDataPoints() {

        if(graphPaused||cur >= emgDataSet.length - 8){return;}

        mHandler.postDelayed(mHandlerTask = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 8; i++) {
                    emgSeries[i].appendData(new DataPoint(cur * 5, emgDataSet[cur][i]), true, emgDataSet.length);
                }
                if (cur++ < emgDataSet.length - 8) {
                    addDataPoints();
                }
            }
        }, 5);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 14) {
            for (int i = 0; i < 8; i++) {
                emgSeries[i].resetData(new DataPointInterface[]{});
            }
            cur = 0;
            drawGraph(filePath);
            return;
        }
        if (position == 13) {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            //intent.setType("*/*");      //all files
            intent.setType("text/xml");   //XML file only
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            try {
                startActivityForResult(intent, 2, Bundle.EMPTY);
            } catch (android.content.ActivityNotFoundException ex) {
                // Potentially direct the user to the Market with a Dialog
                Toast.makeText(null, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
            }
            return;
        }


        for (int i = 0; i < 8; i++) {
            emgSeries[i].resetData(new DataPointInterface[]{});
        }
        cur = 0;
        drawGraph(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {/*empty*/}

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        try {
            data.equals(null);
        } catch (Exception e) {
            Log.e("Result error", "on result Exception");
            return;
        }

        Log.d(data.getData().getPath(), "debug");
        fileName = data.getData().getLastPathSegment().substring(data.getData().getLastPathSegment().lastIndexOf("/") + 1);
        filePath = data.getData();
        Log.d(data.getData().getLastPathSegment().substring(data.getData().getLastPathSegment().lastIndexOf("/") + 1), "debug");
        ArrayAdapter a = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item);
        a.addAll(getContext().getResources().getStringArray(R.array.gestures));
        a.add(fileName);

        spinner.setAdapter(a);
        spinner.setSelection(14);

        for (int i = 0; i < 8; i++) {
            emgSeries[i].resetData(new DataPointInterface[]{});
        }
        cur = 0;
        drawGraph(data.getData());

    }

    private void drawGraph(Uri path) {
        emgDataSet = DataParser.parseData(getContext(), getActivity(), path);

        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(emgDataSet.length * 1.5);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinY(-100);
        graph.getViewport().setMaxY(100);
        graph.getViewport().setYAxisBoundsManual(true);

        mHandler.removeCallbacksAndMessages(null);

        addDataPoints();
    }
}

