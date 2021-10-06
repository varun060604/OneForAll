package com.varun.all_combined;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit extends AppCompatActivity {

    TextView textViewResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        textViewResults = findViewById(R.id.TextViewResult);

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonHolder jsonHolder = retrofit.create(JsonHolder.class);

        retrofit2.Call<List<Post>> call = jsonHolder.getComments();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResults.setText("Code"+response.code());
                    return;
                }

                List<Post> comments = response.body();
                for (Post comment: comments){
                    String abc = "";
                    abc+= "Post Id: " + comment.getPostId() + "\n";
                    abc+= "ID: " + comment.getId() + "\n";
                    abc+= "Name: " + comment.getName() + "\n";
                    abc+= "Email: " + comment.getEmail() + "\n";
                    abc+= "Text: " + comment.getBody() + "\n\n\n\n";

                    textViewResults.append(abc);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResults.setText(t.getMessage());
            }
        });

    }
}