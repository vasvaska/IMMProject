package com.IMM2020.imm_project;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class SecondFragment extends Fragment {
    Button btn_one, btn_two, btn_three, btn_four;
    TextView tv_question;

    private final Question QuestionList = new Question();

    private String answer;
    private final int questionLength = QuestionList.questions.length;

    Random random = new Random();
    int currQuestion = 0;
    private static int rightAnswers = 0;
    private View reverseView;

    public static void resetCounter() {

        rightAnswers = 0;
    }

    public static SecondFragment newInstance() {
        SecondFragment fragment = new SecondFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("section number", 1);
        fragment.setArguments(bundle);
        resetCounter();

        return fragment;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        rightAnswers = 0;

        return inflater.inflate(R.layout.fragment_second, container, false);
    }


    private final View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            switch (v.getId()) {
                case R.id.btn_one:
                    if (btn_one.getText() == answer) {
                        ShowCorrectSnack();
                        NextQuestion();
                    } else {
                        GameOver();
                    }

                    break;

                case R.id.btn_two:
                    if (btn_two.getText() == answer) {
                        ShowCorrectSnack();
                        NextQuestion();
                    } else {
                        GameOver();
                    }

                    break;

                case R.id.btn_three:
                    if (btn_three.getText() == answer) {
                        ShowCorrectSnack();
                        NextQuestion();
                    } else {
                        GameOver();
                    }

                    break;

                case R.id.btn_four:
                    if (btn_four.getText() == answer) {
                        ShowCorrectSnack();
                        NextQuestion();
                    } else {
                        GameOver();
                    }

                    break;
            }
        }
    };

    private void ShowCorrectSnack() {
        rightAnswers++;
        Snackbar.make(tv_question, this.getResources().getText(R.string.correctAnswer) + "\t Total: " + rightAnswers, Snackbar.LENGTH_SHORT).setTextColor(Color.YELLOW).show();

    }

    private void GameOver() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder
                .setMessage("Wrong answer.")
                .setCancelable(true)
                .setPositiveButton("Next question", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NextQuestion();
                    }
                })
                .setNeutralButton("Show Answer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Snackbar.make(tv_question, "Correct answer was: " + answer, Snackbar.LENGTH_LONG).setTextColor(Color.YELLOW).show();
                        NextQuestion();
                    }
                });
        alertDialogBuilder.show();

    }


    private void NextQuestion() {
        int num = random.nextInt(questionLength);
        if (num == currQuestion) {

            NextQuestion();
            return;
        }
        currQuestion = num;
        tv_question.setText(QuestionList.getQuestion(num));
        btn_one.setText(QuestionList.getChoice1(num));
        btn_two.setText(QuestionList.getChoice2(num));
        btn_three.setText(QuestionList.getChoice3(num));
        btn_four.setText(QuestionList.getChoice4(num));

        answer = QuestionList.getCorrectAnswer(num);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        random.nextInt();
        rightAnswers = 0;
        btn_one = view.findViewById(R.id.btn_one);
        btn_one.setOnClickListener(clicker);

        btn_two = view.findViewById(R.id.btn_two);
        btn_two.setOnClickListener(clicker);

        btn_three = view.findViewById(R.id.btn_three);
        btn_three.setOnClickListener(clicker);

        btn_four = view.findViewById(R.id.btn_four);
        btn_four.setOnClickListener(clicker);
        tv_question = view.findViewById(R.id.tv_question);
        NextQuestion();
    }
}
