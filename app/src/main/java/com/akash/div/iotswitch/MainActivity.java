package com.akash.div.iotswitch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Connection con;
    WebView wView;
    String myServer = "http://192.168.43.90:80";
    ArrayList<Button> buttonList;
    static short code = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        wView = (WebView) findViewById(R.id.view1);
//
//        wView.getSettings().setJavaScriptEnabled(true);
//        con = new Connection(wView,myServer);
//       loadSite();
//        commecnting the bottom line to not execute httpreqtask class
//        new HTTPReqTask().execute();


    }

    public void setColor() {
        for (int i = 0; i < 8; i++) {
            int x = code & 1 << i;
            if (x > 0) {
                buttonList.get(i).setBackgroundColor(Color.GREEN);
            } else {
                buttonList.get(i).setBackgroundColor(Color.RED);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void loadSite() {
        wView.loadUrl(myServer);
        wView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                getStatus();

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (error != null) {
                    wView.loadUrl(myServer);
                }
            }

        });

        wView.setVisibility(View.GONE);
    }

    public void initialize() {
        buttonList = new ArrayList<>();
        buttonList.add((Button) findViewById(R.id.button1));
        buttonList.add((Button) findViewById(R.id.button2));
        buttonList.add((Button) findViewById(R.id.button3));
        buttonList.add((Button) findViewById(R.id.button4));
        buttonList.add((Button) findViewById(R.id.button5));
        buttonList.add((Button) findViewById(R.id.button6));
        buttonList.add((Button) findViewById(R.id.button7));
        buttonList.add((Button) findViewById(R.id.button8));
        setColor();
        for (int i = 0; i < 8; i++) {
            final int x = i;
            buttonList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    con.fillAutomatically(x);
                    getStatus();
                    setColor();
                }
            });
        }
    }


    public void getStatus() {
        final String j1 = "(function(){" +
                "var a;" +
                "a=document.getElementById('s1').value;" +
                "return a;})();";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            wView.evaluateJavascript(j1, new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    if (value != null) {
                        code = Short.valueOf(value.replace("\"", ""));
                        initialize();
                    }

                }
            });
        }

    }

    public void goToWifiActivity(View view){
        Intent intent = new Intent(this, WifiActivity.class);
        startActivity(intent);
    }
}
