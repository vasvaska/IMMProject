package com.IMM2020.imm_project;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Random;

public class DataParser {

    static int[][] parseData(Context ctx, Activity act){

        int index = 4;

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

        String newLines[]= strB.toString().split("\n");

        int emgDataSet[][] = new int[newLines.length][8];

        int row = 0;
        for (String line : newLines) {

            if (line == "")
                continue;

            String newValues[] = line.split(" ");
            //EmgData emg = new EmgData();

            for (int column = 0; column < 8; column++) {
                emgDataSet[row][column] = Integer.parseInt(newValues[column]);
            }

            row++;
        }
        return emgDataSet;







    }
}
