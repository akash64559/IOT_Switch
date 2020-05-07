package com.akash.div.iotswitch;

import android.os.Build;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

public class Connection{
    WebView wView;
    String site;
    static short code=0;
    public boolean statusReceived=false;

    Connection(WebView wv, String site){
        wView = wv;
        this.site=site;
    }

    public void fillAutomatically(Integer buttonId){
            code ^=1 <<(buttonId);
        final String js = "javascript:" +
                "document.getElementById('s1').value="+code +";"+
                "document.getElementById('1').click();";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    wView.evaluateJavascript(js, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {

                        }
                    });
                }
    }



}
