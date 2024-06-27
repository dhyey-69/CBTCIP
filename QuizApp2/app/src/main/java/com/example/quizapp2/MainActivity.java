package com.example.quizapp2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup answersRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private Button nextButton;

    private final String[] questions = {
            "What is the capital of France?",
            "What is 2 + 2?",
            "What is the largest planet in our solar system?",
            "Who painted the Mona Lisa?",
            "Which planet is known as the Red Planet?",
            "What is the chemical symbol for water?",
            "Which country won the FIFA World Cup in 2018?",
            "Who wrote 'To Kill a Mockingbird'?",
            "What is the tallest mountain in the world?"
    };

    private final String[][] options = {
            {"Berlin", "Madrid", "Paris", "Rome"},
            {"3", "4", "5", "6"},
            {"Earth", "Jupiter", "Mars", "Saturn"},
            {"Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Claude Monet"},
            {"Mars", "Jupiter", "Venus", "Mercury"},
            {"H2O", "CO2", "O2", "NaCl"},
            {"France", "Brazil", "Germany", "Argentina"},
            {"Harper Lee", "Mark Twain", "Ernest Hemingway", "F. Scott Fitzgerald"},
            {"Mount Everest", "K2", "Makalu", "Kangchenjunga"}
    };

    private final int[] correctAnswers = {2, 1, 1, 0, 0, 0, 1, 0, 0};

    private int currentQuestionIndex;
    private int score;
    private final int NUM_QUESTIONS_TO_ASK = 5;
    private final int[] selectedQuestionIndices = {0, 1, 3, 5, 7};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        answersRadioGroup = findViewById(R.id.answersRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);
        nextButton = findViewById(R.id.nextButton);

        currentQuestionIndex = -1;
        score = 0;

        loadNextQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedAnswerIndex = answersRadioGroup.indexOfChild(findViewById(answersRadioGroup.getCheckedRadioButtonId()));
                if (selectedAnswerIndex == -1) {
                    Toast.makeText(MainActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedAnswerIndex == correctAnswers[selectedQuestionIndices[currentQuestionIndex]]) {
                    score++;
                    resultTextView.setText("Correct!");
                } else {
                    resultTextView.setText("Wrong! Correct answer: " + options[selectedQuestionIndices[currentQuestionIndex]][correctAnswers[selectedQuestionIndices[currentQuestionIndex]]]);
                }
                resultTextView.setVisibility(View.VISIBLE);
                nextButton.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.GONE);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextQuestion();
                resultTextView.setVisibility(View.GONE);
                nextButton.setVisibility(View.GONE);
                submitButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < NUM_QUESTIONS_TO_ASK) {
            questionTextView.setText(questions[selectedQuestionIndices[currentQuestionIndex]]);
            answersRadioGroup.clearCheck();
            for (int i = 0; i < answersRadioGroup.getChildCount(); i++) {
                ((RadioButton) answersRadioGroup.getChildAt(i)).setText(options[selectedQuestionIndices[currentQuestionIndex]][i]);
            }
        } else {
            Toast.makeText(MainActivity.this, "Quiz finished! Your score: " + score + " out of " + NUM_QUESTIONS_TO_ASK, Toast.LENGTH_LONG).show();
            finish();
        }
    }
}
