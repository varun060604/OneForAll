package com.varun.all_combined;

import java.util.List;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonHolder {

    @GET("comments")
    Call<List<Post>> getComments();

}
