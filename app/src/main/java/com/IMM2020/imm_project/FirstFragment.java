package com.IMM2020.imm_project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Random;

public class FirstFragment extends Fragment {
    Button btn_one, btn_two, btn_three, btn_four;
    TextView tv_question;

    private Question QuestionList = new Question();

    private String answer;
    private int questionLength = QuestionList.questions.length;

    Random random = new Random();


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_first, container, false);

    }


    private View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            switch (v.getId()) {
                case R.id.btn_one:
                    if (btn_one.getText() == answer) {
                        Toast.makeText(getContext(), R.string.correctAnswer, Toast.LENGTH_SHORT).show();
                        NextQuestion(random.nextInt(questionLength));
                    } else {
                        GameOver();
                    }

                    break;

                case R.id.btn_two:
                    if (btn_two.getText() == answer) {
                        Toast.makeText(getContext(), R.string.correctAnswer, Toast.LENGTH_SHORT).show();
                        NextQuestion(random.nextInt(questionLength));
                    } else {
                        GameOver();
                    }

                    break;

                case R.id.btn_three:
                    if (btn_three.getText() == answer) {
                        Toast.makeText(getContext(), R.string.correctAnswer, Toast.LENGTH_SHORT).show();
                        NextQuestion(random.nextInt(questionLength));
                    } else {
                        GameOver();
                    }

                    break;

                case R.id.btn_four:
                    if (btn_four.getText() == answer) {
                        Toast.makeText(getContext(), R.string.correctAnswer, Toast.LENGTH_SHORT).show();
                        NextQuestion(random.nextInt(questionLength));
                    } else {
                        GameOver();
                    }

                    break;
            }
        }
    };

    private void GameOver() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder
                .setMessage("Wrong answer. Correct was: " + answer)
                .setCancelable(false)
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NextQuestion(random.nextInt(questionLength));
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
        alertDialogBuilder.show();

    }


    private void NextQuestion(int num) {
        tv_question.setText(QuestionList.getQuestion(num));
        btn_one.setText(QuestionList.getchoice1(num));
        btn_two.setText(QuestionList.getchoice2(num));
        btn_three.setText(QuestionList.getchoice3(num));
        btn_four.setText(QuestionList.getchoice4(num));

        answer = QuestionList.getCorrectAnswer(num);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        random.nextInt();
        btn_one = view.findViewById(R.id.btn_one);
        btn_one.setOnClickListener(clicker);

        btn_two = view.findViewById(R.id.btn_two);
        btn_two.setOnClickListener(clicker);

        btn_three = view.findViewById(R.id.btn_three);
        btn_three.setOnClickListener(clicker);

        btn_four = view.findViewById(R.id.btn_four);
        btn_four.setOnClickListener(clicker);
        tv_question = view.findViewById(R.id.tv_question);
        NextQuestion(random.nextInt(questionLength));
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }

        });

    }
}