package com.slot.testapp;

import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public interface Customer {

    void initWebView(AppCompatActivity activity, WebView webView);

    String getTestUrl();

    String[] getJsInterface();
}
