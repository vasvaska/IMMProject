package com.IMM2020.imm_project;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataParser {

    private static final Intent FILE_SELECT_CODE = null;

    static int[][] parseData(Context ctx, Activity act, int index) {

        InputStream ins = ctx.getResources().openRawResource
                (
                        ctx.getResources().getIdentifier("emg_" + index, "raw", act.getPackageName())
                );
        InputStreamReader insR = new InputStreamReader(ins);
        BufferedReader bufR = new BufferedReader(insR);
        StringBuffer strB = new StringBuffer();
        String str = "";


        try {
            while ((str = bufR.readLine()) != null) {
                str = str + "\n";
                strB.append(str); }
        }catch (IOException e){}

        String[] newLines = strB.toString().split("\n");

        int[][] emgDataSet = new int[newLines.length][8];

        int row = 0;
        for (String line : newLines) {

            if (line == "")
                continue;

            String[] newValues = line.split(" ");

            for (int column = 0; column < 8; column++) {
                emgDataSet[row][column] = Integer.parseInt(newValues[column]);
            }

            row++;
        }
        return emgDataSet;
    }

    static int[][] parseData(Context ctx, Activity act, Uri path) {
        ContentResolver cR = ctx.getContentResolver();
        InputStream ins = null;
        try {
            ins = cR.openInputStream(path);
        } catch (FileNotFoundException e) {
            parseData(ctx, act, 1);
            Toast.makeText(ctx, "FnF", Toast.LENGTH_LONG).show();
        }

        InputStreamReader insR = new InputStreamReader(ins);
        BufferedReader bufR = new BufferedReader(insR);
        StringBuffer strB = new StringBuffer();
        String str = "";


        try {
            while ((str = bufR.readLine()) != null) {
                str = str + "\n";
                strB.append(str);
            }
        } catch (IOException e) {
        }

        String[] newLines = strB.toString().split("\n");

        int[][] emgDataSet = new int[newLines.length][8];

        int row = 0;
        for (String line : newLines) {

            if (line == "")
                continue;

            String[] newValues = line.split(" ");

            for (int column = 0; column < 8; column++) {
                emgDataSet[row][column] = Integer.parseInt(newValues[column]);
            }

            row++;
        }
        return emgDataSet;
    }
}
