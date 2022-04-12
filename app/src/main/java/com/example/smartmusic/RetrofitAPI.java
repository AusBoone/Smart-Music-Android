package com.example.smartmusic;

import retrofit2.Call;
import retrofit2.call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class RetrofitAPI {

    @GET
    Call<MsgModal> getMessage(@Url String url);

}
