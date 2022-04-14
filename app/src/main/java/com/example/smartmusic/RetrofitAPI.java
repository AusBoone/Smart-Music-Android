package com.example.smartmusic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public abstract class RetrofitAPI {

    @GET
    abstract Call<MsgModal> getMessage(@Url String url);

}
