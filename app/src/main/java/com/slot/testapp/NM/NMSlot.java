package com.slot.testapp.NM;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustEvent;

import com.slot.testapp.Config;
import com.slot.testapp.Customer;

public class NMSlot implements Customer {
    AppCompatActivity activity;
    WebView webView;
    private static final String TAG = "NMSlot";

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
        return Config.DEBUG ? "https://wintbr.style/" : null;
    }

    @Override
    public String[] getJsInterface() {
        return new String[]{"androidJs"};
    }


    @JavascriptInterface
    public void adjustEvent(String event) {
        Log.e(TAG, "adjustEvent: " + event);
        String token = "";
        switch (event) {
            case "login":
                token = "non8ot";
                break;
            case "logout":
                token = "cigyi9";
                break;
            case "register":
                token = "2jscby";
                break;
            case "charge":
                token = "rsw26o";
                break;
            case "register-success":
                token = "tiyps1";
                break;

        }
        AdjustEvent byfnjuanjnjbgaag = new AdjustEvent(token);
        Adjust.trackEvent(byfnjuanjnjbgaag);
    }

    @JavascriptInterface
    public void adjustEvent(String bfynnuniaubnguag, double bgynujfnaungaga) {
        Log.e(TAG, "adjustEvent: " + bfynnuniaubnguag);
        String bynunafmagag = "";
        switch (bfynnuniaubnguag) {
            case "first-deposit-success":
                bynunafmagag = "w5a3uv";
                break;
            case "second-deposit-success":
                bynunafmagag = "2xrlol";
                break;
        }
        AdjustEvent nhumifanmgiamgag = new AdjustEvent(bynunafmagag);
        if (bgynujfnaungaga > 0) {
            nhumifanmgiamgag.setRevenue(bgynujfnaungaga, "VND");
        }
        Adjust.trackEvent(nhumifanmgiamgag);
    }
}
