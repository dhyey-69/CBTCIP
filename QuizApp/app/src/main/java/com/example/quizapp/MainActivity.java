package com.example.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup answersRadioGroup;
    private Button submitButton;
    private TextView resultTextView;
    private Button nextButton;
    private TextView scoreTextView;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private ApiService apiService;


    private static final int QUESTION_LIMIT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        answersRadioGroup = findViewById(R.id.answersRadioGroup);
        submitButton = findViewById(R.id.submitButton);
        resultTextView = findViewById(R.id.resultTextView);
        nextButton = findViewById(R.id.nextButton);
        scoreTextView = findViewById(R.id.scoreTextView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://quizapi.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        fetchRandomQuestions(apiService);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedAnswerIndex = answersRadioGroup.indexOfChild(findViewById(answersRadioGroup.getCheckedRadioButtonId()));
                if (selectedAnswerIndex == -1 || questions == null || currentQuestionIndex >= questions.size()) {
                    Toast.makeText(MainActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    return;
                }

                String selectedAnswer = ((RadioButton) answersRadioGroup.getChildAt(selectedAnswerIndex)).getText().toString().trim();
                if (currentQuestionIndex < questions.size()) {
                    String correctAnswer = questions.get(currentQuestionIndex).getCorrectAnswer().trim();

                    // Compare selected answer with correct answer text
                    if (selectedAnswer.equalsIgnoreCase(correctAnswer)) {
                        score++;
                        resultTextView.setText("Correct!");
                    } else {
                        resultTextView.setText("Wrong! Correct answer: " + correctAnswer);
                    }
                    resultTextView.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.VISIBLE);
                    submitButton.setVisibility(View.GONE);


                    scoreTextView.setText("Score: " + score);
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex < questions.size() - 1) {
                    currentQuestionIndex++;
                    loadQuestion();
                    resultTextView.setVisibility(View.GONE);
                    nextButton.setVisibility(View.GONE);
                    submitButton.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(MainActivity.this, "Quiz finished! Your score: " + score, Toast.LENGTH_LONG).show();
                    resetQuiz();
                }
            }
        });

    }

    private void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        questions = null;
        resultTextView.setVisibility(View.GONE);
        nextButton.setVisibility(View.GONE);
        submitButton.setVisibility(View.VISIBLE);
        scoreTextView.setText("Score: " + score);
        fetchRandomQuestions(apiService);
    }

    private void fetchRandomQuestions(ApiService apiService) {
        Call<List<Question>> call = apiService.getRandomQuestions(QUESTION_LIMIT);
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful()) {
                    questions = response.body();
                    if (questions != null && !questions.isEmpty()) {
                        loadQuestion();
                    } else {
                        Toast.makeText(MainActivity.this, "No questions available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load questions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadQuestion() {
        if (questions == null || questions.isEmpty() || currentQuestionIndex >= questions.size()) {
            return;
        }

        Question currentQuestion = questions.get(currentQuestionIndex);
        questionTextView.setText(currentQuestion.getQuestion());
        answersRadioGroup.clearCheck();

        List<String> answerOptions = new ArrayList<>(currentQuestion.getAnswers().values());

        for (int i = 0; i < answersRadioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) answersRadioGroup.getChildAt(i);
            if (i < answerOptions.size()) {
                radioButton.setText(answerOptions.get(i));
                radioButton.setVisibility(View.VISIBLE);
            } else {
                radioButton.setVisibility(View.GONE);
            }
        }
    }
}
