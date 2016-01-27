package com.example.paul.nfcsecondfactor0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class BTWebSiteActivity extends AppCompatActivity {

    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btweb_site);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("http://www.home.bt.com");
    }
}
