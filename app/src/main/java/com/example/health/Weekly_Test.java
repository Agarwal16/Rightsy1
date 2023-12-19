package com.example.health;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.QuestionsBank;
import com.QuestionsList;

import java.util.List;

public class Weekly_Test extends AppCompatActivity {
    private TextView question;
    private String selectedOptionByUser = "";
    private int currentQuestionPosition = 0;
    private AppCompatButton option1, option2, option3, option4;
    private AppCompatButton next;
    private List<QuestionsList> questionsLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_test);

        final Button backBtn = findViewById(R.id.backBtn);
        final TextView selectedTopicName = findViewById(R.id.TopicName);
        question = findViewById(R.id.question);
        option1 = findViewById(R.id.Option1);
        option2 = findViewById(R.id.Option2);
        option3 = findViewById(R.id.Option3);
        option4 = findViewById(R.id.Option4);
        next = findViewById(R.id.next);

        final String getSelectedTopicName = getIntent().getStringExtra("selectedTopic");
        selectedTopicName.setText(getSelectedTopicName);

        questionsLists = QuestionsBank.getQuestions(getSelectedTopicName);

        question.setText(questionsLists.get(0).getQuestion());
        option1.setText(questionsLists.get(0).getOption1());
        option2.setText(questionsLists.get(0).getOption2());
        option3.setText(questionsLists.get(0).getOption3());
        option4.setText(questionsLists.get(0).getOption4());

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(option1);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(option2);
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(option3);
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOptionClick(option4);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOptionByUser.isEmpty()) {
                    Toast.makeText(Weekly_Test.this, "Please select an Option", Toast.LENGTH_SHORT).show();
                } else {
                    changeNextQuestion();
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle back button click
            }
        });
    }

    private void handleOptionClick(AppCompatButton selectedOption) {
        if (selectedOptionByUser.isEmpty()) {
            selectedOptionByUser = selectedOption.getText().toString();
            selectedOption.setBackgroundResource(R.drawable.round_back_red10);
            selectedOption.setTextColor(Color.WHITE);

            revealAnswer();
            questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
        }
    }

    private void changeNextQuestion() {
        currentQuestionPosition++;

        if ((currentQuestionPosition + 1) == questionsLists.size()) {
            next.setText("Submit Quiz");
        }

        if (currentQuestionPosition < questionsLists.size()) {
            resetOptions();
            updateQuestion();
        } else {
            Intent intent = new Intent(Weekly_Test.this, QuizResultActivity.class);
            // Add extras if needed
            startActivity(intent);
            finish();
        }
    }

    private void resetOptions() {
        selectedOptionByUser = "";
        option1.setBackgroundResource(R.drawable.round_back_white_stroke10);
        option1.setTextColor(Color.parseColor("#1F6BB8"));
        option2.setBackgroundResource(R.drawable.round_back_white_stroke10);
        option2.setTextColor(Color.parseColor("#1F6BB8"));
        option3.setBackgroundResource(R.drawable.round_back_white_stroke10);
        option3.setTextColor(Color.parseColor("#1F6BB8"));
        option4.setBackgroundResource(R.drawable.round_back_white_stroke10);
        option4.setTextColor(Color.parseColor("#1F6BB8"));
    }

    private void updateQuestion() {
        question.setText(questionsLists.get(currentQuestionPosition).getQuestion());
        option1.setText(questionsLists.get(currentQuestionPosition).getOption1());
        option2.setText(questionsLists.get(currentQuestionPosition).getOption2());
        option3.setText(questionsLists.get(currentQuestionPosition).getOption3());
        option4.setText(questionsLists.get(currentQuestionPosition).getOption4());
    }

    private void revealAnswer() {
        final String correctAnswer = questionsLists.get(currentQuestionPosition).getAnswer();
        AppCompatButton correctOptionButton = getCorrectOptionButton(correctAnswer);

        if (correctOptionButton != null) {
            correctOptionButton.setBackgroundResource(R.drawable.round_back_green10);
            correctOptionButton.setTextColor(Color.WHITE);
        }
    }

    private AppCompatButton getCorrectOptionButton(String correctAnswer) {
        if (option1.getText().toString().equals(correctAnswer)) {
            return option1;
        } else if (option2.getText().toString().equals(correctAnswer)) {
            return option2;
        } else if (option3.getText().toString().equals(correctAnswer)) {
            return option3;
        } else if (option4.getText().toString().equals(correctAnswer)) {
            return option4;
        }
        return null;
    }
}
