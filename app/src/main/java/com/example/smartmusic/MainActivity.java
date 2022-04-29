

package com.example.smartmusic;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

/** MainActivity.java
* @author: Austin, Suleman, Patrick
* This java class file contains a function for the begin button and a text view message for the user
* date: 03-20-2022
*/
public class MainActivity extends AppCompatActivity {
    //public static final String EXTRA_MESSAGE = "com.example.SmartMusic.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
    
    /** 
    * Called when the user taps the Send button on the main activity and navigates the user to the Chat Message Activity
    */
    public void beginButton(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivity(intent);
    }

}
