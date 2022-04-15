package com.example.smartmusic;


import com.google.gson.annotations.SerializedName;

public class MsgModal{

    @SerializedName("cnt")
    private String cnt;

    // getter and setter methods.
    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }
}