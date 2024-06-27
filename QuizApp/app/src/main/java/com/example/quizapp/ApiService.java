package com.example.quizapp;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {
    @Headers("X-Api-Key: ok9NogyTZitQtVggyYxTfsOUFArYVvqpDtrRG14E")
    @GET("v1/questions")
    Call<List<Question>> getRandomQuestions(@Query("limit") int limit);
}




