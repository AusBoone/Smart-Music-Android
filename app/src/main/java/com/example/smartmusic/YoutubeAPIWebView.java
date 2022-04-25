package com.example.smartmusic;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class YoutubeAPIWebView extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_api_webview);

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        String tempURL = DisplayMessageActivity.tempStr;
        String URL = tempURL.substring(tempURL.indexOf('h'),tempURL.length()-1);
        if(tempURL.contains("http:")){
            int num = tempURL.indexOf('h');
            System.out.println("num = "+ num);
            for(int i = num; i<tempURL.length();i++) {
                URL = URL + tempURL.charAt(i);
            }
        }
        System.out.println("Temp URL = " + tempURL);
        System.out.println("URL = " + URL);
        webView.loadUrl(URL);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }



    @Override
    public void onBackPressed(){
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}