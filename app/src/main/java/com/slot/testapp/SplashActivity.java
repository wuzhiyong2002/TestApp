package com.slot.testapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.slot.testapp.util.HttpUtil;
import com.xuexiang.xui.widget.progress.HorizontalProgressView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private HorizontalProgressView progress;
    private WebView webView;
    private final SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//            if (!Config.APPSFLYER_KEY.equals("")) {
//                if (sharedPreferences.getString("af_status", "").equals("Organic") && !sharedPreferences.getString("link", "").equals("")) {
//                    Log.e(TAG, "af_status=" + sharedPreferences.getString("af_status", ""));
//
//                    initWebView();
//                    return;
//                }
//            } else if (!Config.ADJUST_KEY.equals("")) {
//                if (sharedPreferences.getString("ad_status", "").equals("Organic") && !sharedPreferences.getString("link", "").equals("")) {
//                    Log.e(TAG, "af_status=" + sharedPreferences.getString("ad_status", ""));
//                    initWebView();
//                    return;
//                }
//            }


//            if (!Config.APPSFLYER_KEY.equals("")) {
//                if (sharedPreferences.getString("af_status", "").equals("Organic")) {
//                    Log.e(TAG, "af_status=" + sharedPreferences.getString("af_status", ""));

            initWebView();
            return;
//                }
//            }

        }
    };
    private String url = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        progress();
    }

    private void initData() {
        HttpUtil httpUtil = new HttpUtil();
        httpUtil.POST(Config.BASEURL, this, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: " + e);
                goGame();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.e(TAG, "onResponse: " + response.code());
                if (response.isSuccessful()) {
                    JSONObject data = (JSONObject) JSON.parse(response.body().string());
                    if (data == null || data.getJSONObject("data").isEmpty()) {
                        goGame();
                        return;
                    }
                    url = data.getJSONObject("data").getString("link");
                    Log.e(TAG, "onResponse: " + url);
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().
                            putString("link", url).apply();
//                    if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("link", "").equals(url)) {
//
//                    } else {
//
//                    }
                }
            }
        });
    }

    private void progress() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                initData();
                for (int i = 0; i < 100; i++) {
                    progress.setProgress(i);
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.start();
    }


    protected void onResume() {

        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).registerOnSharedPreferenceChangeListener(mListener);
        super.onResume();

    }

    @Override

    protected void onPause() {

        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).unregisterOnSharedPreferenceChangeListener(mListener);
        super.onPause();
    }


    private void goGame() {
        startActivity(new Intent(this, GameActivity.class));
        finish();
    }

    private void initView() {
        progress = (HorizontalProgressView) findViewById(R.id.progress);
        webView = (WebView) findViewById(R.id.webView);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("link", "").apply();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        Customer xiaomantou = SimpleFactory.createInstance("TB1");
        xiaomantou.initWebView(this, webView);
        WebSettings settings = webView.getSettings();
        if (Config.DEBUG)
            webView.loadUrl(xiaomantou.getTestUrl());
        else
            webView.loadUrl(url);
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                Log.e(TAG, "shouldOverrideUrlLoading: " + request.getUrl().toString());
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.setVisibility(View.VISIBLE);
            }
        });
    }
}