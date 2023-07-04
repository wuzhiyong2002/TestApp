package com.slot.testapp.TB1;


import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustEvent;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.appsflyer.AppsFlyerLib;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.slot.testapp.Config;
import com.slot.testapp.Customer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TB1Slot implements Customer {
    AppCompatActivity activity;
    WebView webView;
    private static final String TAG = "TB1Slot";

    @Override
    public void initWebView(AppCompatActivity activity, WebView webView) {
        this.activity = activity;
        this.webView = webView;

        try {
            if (webView == null) return;
            String[] list = getJsInterface();
            if (list == null || list.length == 0) return;
            for (String item : list) webView.addJavascriptInterface(this, item);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public String getTestUrl() {
        return Config.DEBUG ? "https://bt035.com/?id=44068374" : null;
    }

    @Override
    public String[] getJsInterface() {
        return new String[]{"jsBridge"};
    }


    @JavascriptInterface
    public void postMessage(String name, String data) {
        String token = null;
        switch (name) {
            case "login":
                token = "oxo8es";
                break;
            case "logout":
                token = "o0o7s7";
                break;
            case "registerClick":
                token = "y3n81g";
                break;
            case "register":
                token = "c4me60";
                break;
            case "rechargeClick":
                token = "ynp2x1";
                break;
            case "firstrecharge":
                token = "9vohtq";
                break;
            case "recharge":
                token = "kewyy1";
                break;
            case "withdrawClick":
                token = "3hs3g2";
                break;
            case "withdrawOrderSuccess":
                token = "te1079";
                break;
            case "enterGame":
                token = "gx1my7";
                break;
            case "vipReward":
                token = "vli9z5";
                break;
            case "dailyReward":
                token = "2y79jg";
                break;
            case "enterEventCenter":
                token = "tkxxcn";
                break;
            case "enterTask":
                token = "u9oca7";
                break;
            case "enterCashback":
                token = "n8bcbz";
                break;
            case "enterPromote":
                token = "xc0rv1";
                break;
            case "bannerClick":
                token = "3rp21w";
                break;

        }
        if (name.equals("openWindow")) {
            JSONObject jsonObject = (JSONObject) JSON.parse(data);
            String url = jsonObject.getString("url");
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.setWebViewClient(null);
                    webView.loadUrl(url);
                }
            });
        }
        AdjustEvent adjustEvent = new AdjustEvent(token);
        Log.e(TAG, "name: " + name);
        Log.e(TAG, "data: " + data);
        Log.e(TAG, "token: " + token);

        JSONObject jsonObject = (JSONObject) JSON.parse(data);
        if (!Objects.isNull(jsonObject.getString("amount"))) {
            adjustEvent.setRevenue(Double.parseDouble(jsonObject.getString("amount")), jsonObject.getString("currency"));
        }

        Adjust.trackEvent(adjustEvent);


    }

}

