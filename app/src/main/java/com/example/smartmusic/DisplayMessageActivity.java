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
import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.natural_language_understanding.v1.model.EmotionOptions;
import com.ibm.watson.natural_language_understanding.v1.model.Features;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayMessageActivity extends AppCompatActivity {

    private RecyclerView chats;
    private EditText userMsgEdt;
    private FloatingActionButton sendMsgFab;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatsModal>chatsModalArrayList;
    private ChatRVAdapter chatRVAdapter;

    @Override
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

    private void getResponse(String message) throws IOException {
        System.out.println("message = " + message);
        chatsModalArrayList.add(new ChatsModal(message,USER_KEY));
        chatRVAdapter.notifyDataSetChanged();
        for(int i=0; i< chatsModalArrayList.size(); i++){
            System.out.println(chatsModalArrayList);
        }

        String ibmURL = "https://api.us-east.natural-language-understanding.watson.cloud.ibm.com/instances/f78d5078-01f6-44a5-b9c8-9fb1fa9ecf98";

        IamAuthenticator authenticator = new IamAuthenticator.Builder().apikey("0zV1aRs0i26eHhVPqEkGkP-nnxabDAxladH5GHjBjRy4").build();
        NaturalLanguageUnderstanding naturalLanguageUnderstanding = new NaturalLanguageUnderstanding("2022-04-07", authenticator);
        naturalLanguageUnderstanding.setServiceUrl(ibmURL);

        String url = "www.ibm.com";

        List<String> targets = new ArrayList<>();
        targets.add(message);

        EmotionOptions emotion= new EmotionOptions.Builder()
                .targets(targets)
                .build();

        Features features = new Features.Builder()
                .emotion(emotion)
                .build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                .url(url)
                .features(features)
                .build();

        AnalysisResults aResponse = naturalLanguageUnderstanding
                .analyze(parameters)
                .execute()
                .getResult();
        System.out.println(aResponse);

        String URL = "http://api.brainshop.ai/get?bid=165361&key=qcRNGI9WWxgUcabt&uid=[uid]&msg=" + message;
        System.out.println(URL);

        String BASE_URL = "http://api.brainshop.ai/";

/*
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://api.brainshop.ai/get?bid=164972&key=iZ5YQQe77XxPiNjj&uid=[uid]&msg=hello")
                .method("GET", null)
                .build();
        okhttp3.Response response = client.newCall(request).execute();
*/

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<MsgModal> call = retrofitAPI.getMessage(URL);
        call.enqueue(new Callback<MsgModal>() {
            @Override
            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
                int statusCode = response.code();
                if(response.isSuccessful() && response.code() == 200) {
                    System.out.println("[API response code = "+statusCode + ".]" );
                    MsgModal modal = response.body();
                    System.out.println(modal.getCnt().toString());
                    System.out.println(response.body().toString());
                    String res = modal.getCnt();
                    System.out.println(res);
                    chatsModalArrayList.add(new ChatsModal(res, BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MsgModal> call, Throwable t) {
                chatsModalArrayList.add(new ChatsModal("Please revert your question",BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();
            }
        });
    }

}