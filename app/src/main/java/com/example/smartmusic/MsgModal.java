package com.example.smartmusic;


import com.google.gson.annotations.SerializedName;

/**
 * MsgModal.java
 * @author Suleman, Austin, Patrick
 * This java class file stores the message response from the API
 * date: 04-28-22
 */
public class MsgModal{

    @SerializedName("cnt")
    private String cnt;



    /**
     * getter and setter methods.
     */
    public String getCnt() {
        return cnt;
    }

}