package com.slot.testapp.XCB;

import android.annotation.SuppressLint;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustEvent;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.slot.testapp.Config;
import com.slot.testapp.Customer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class XCBSlot implements Customer {
    AppCompatActivity activity;
    WebView webView;
    private static final String TAG = "XCBSlot";

    @SuppressLint("JavascriptInterface")
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
        return Config.DEBUG ? "https://www.a6.com/?id=67767560" : null;
    }

    @Override
    public String[] getJsInterface() {
        return new String[]{"jsBridge"};
    }

    @JavascriptInterface
    public void postMessage(String eventName, String params) {
        Map<String, Object> eventValues = new HashMap<>();
        eventValues.put(AFInAppEventParameterName.PARAM_1, params);
        switch (eventName) {
            case "login":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_login", eventValues);
                break;
            case "logout":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_logout", eventValues);
                break;
            case "registerClick":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "registerClick", eventValues);
                break;
            case "register":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_register", eventValues);
                break;
            case "firstrecharge":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_firstrecharge", eventValues);
                break;
            case "rechargeClick":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_rechargeClick", eventValues);
                break;
            case "recharge":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_recharge", eventValues);
                break;
            case "withdrawClick":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_withdrawClick", eventValues);
                break;
            case "withdrawOrderSuccess":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_withdrawOrderSuccess", eventValues);
                break;
            case "enterGame":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_enterGame", eventValues);
                break;
            case "vipReward":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_vipReward", eventValues);
                break;
            case "dailyReward":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_dailyReward", eventValues);
                break;
            case "enterEventCenter":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_enterEventCenter", eventValues);
                break;
            case "enterCashback":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_enterCashback", eventValues);
                break;
            case "enterPromote":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_enterPromote", eventValues);
                break;
            case "bannerClick":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_bannerClick", eventValues);
                break;

        }
    }


}
