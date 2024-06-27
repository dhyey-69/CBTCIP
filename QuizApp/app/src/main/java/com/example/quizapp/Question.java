package com.example.quizapp;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class Question {
    @SerializedName("question")
    private String question;

    @SerializedName("answers")
    private Map<String, String> answers;

    @SerializedName("correct_answer")
    private String correctAnswer;

    public String getQuestion() {
        return question;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
