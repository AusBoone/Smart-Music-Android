package com.example.smartmusic;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {

    @GET()
    Call<MsgModal> getMessage(@Url String url);

}
