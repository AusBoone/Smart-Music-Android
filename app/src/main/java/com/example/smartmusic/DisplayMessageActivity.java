package com.example.smartmusic;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *DisplayMessageActivity.java
 * @author Suleman, Austin, Patrick
 * This java class file contains the second activity: displaymessage activity.
 * Date: 04-28-22
 */
public class DisplayMessageActivity extends AppCompatActivity {

    private RecyclerView chats;
    private EditText userMsgEdt;
    private FloatingActionButton sendMsgFab;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatsModal>chatsModalArrayList;
    private ChatRVAdapter chatRVAdapter;

    static String tempStr;

    @Override
    /**
     * this method displays the chat messages and contains the edit text box and send button
     * contains the implementation for the adapters
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        chats = findViewById(R.id.idRVChats);
        userMsgEdt = findViewById(R.id.idEdtMessage);
        sendMsgFab = findViewById(R.id.idFabSend);
        chatsModalArrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chatsModalArrayList, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        chats.setLayoutManager(manager);
        chats.setAdapter(chatRVAdapter);

        sendMsgFab.setOnClickListener(new View.OnClickListener() {

            @Override
            /**
             * this method sends a Toast message to the user
             * if it is empty, then it sends a Toast message that says "Please enter your message"
             * else if the user types a message it returns the response from the API for that
             * particular message
             */
            public void onClick(View view) {
                String msg = userMsgEdt.getText().toString();
                System.out.println("[user input = "+ msg + "]");
                if(userMsgEdt.getText().toString().isEmpty()) {
                    Toast.makeText(DisplayMessageActivity.this, "Please enter your message", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    getResponse(userMsgEdt.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                userMsgEdt.setText("");
            }
        });

    }

    /**
     * @return the response from the Brainshop API for a particular message
     * @param message
     * @throws IOException
     */
    private void getResponse(String message) throws IOException {
        System.out.println("message = " + message);
        chatsModalArrayList.add(new ChatsModal(message,USER_KEY));
        chatRVAdapter.notifyDataSetChanged();
        for(int i=0; i< chatsModalArrayList.size(); i++){
            System.out.println(chatsModalArrayList);
        }

        String URL = "http://api.brainshop.ai/get?bid=165361&key=qcRNGI9WWxgUcabt&uid=[uid]&msg=" + message;
        System.out.println(URL);

        String BASE_URL = "http://api.brainshop.ai/";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<MsgModal> call = retrofitAPI.getMessage(URL);
        call.enqueue(new Callback<MsgModal>() {
            @Override
            /**
             * this method will check for the successful response from the API and the response code "200"
             */
            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
                int statusCode = response.code();
                if(response.isSuccessful() && response.code() == 200) {
                    System.out.println("[API response code = "+statusCode + ".]" );
                    MsgModal modal = response.body();
                    System.out.println("Line 151 = " + modal.getCnt());
                    tempStr = modal.getCnt().toString();
                    System.out.println("Line 152 = " + response.body().toString());
                    String res = modal.getCnt();
                    System.out.println("Line 154 = " + res);
                    chatsModalArrayList.add(new ChatsModal(res, BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            /**
             * This method onFailure sends the response as "please revert your question" if there's
             * no response from the API
             */
            public void onFailure(Call<MsgModal> call, Throwable t) {
                chatsModalArrayList.add(new ChatsModal("Please revert your question",BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();
            }
        });
    }

}