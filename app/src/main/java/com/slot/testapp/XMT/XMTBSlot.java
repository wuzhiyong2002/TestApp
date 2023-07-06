package com.slot.testapp.XMT;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AppsFlyerLib;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.slot.testapp.Config;
import com.slot.testapp.Customer;

import java.lang.reflect.Type;
import java.util.Map;

public class XMTBSlot implements Customer {
    private AppCompatActivity activity;

    @Override
    public void initWebView(AppCompatActivity activity, WebView webView) {
        this.activity = activity;

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
        return Config.DEBUG ? "https://www.333bet9.com" : null;
    }

    @Override
    public String[] getJsInterface() {
        return new String[]{"jsBridge"};
    }


    @JavascriptInterface
    public void postMessage(String name, String data) {

        Log.e("TAG", "postMessage  name==" + name);
        Log.e("TAG", "postMessage  data==" + data);
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = gson.fromJson(data, type);

        switch (name) {
            case "login":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_login", map);
                break;
            case "logout":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_logout", map);
                break;
            case "registerClick":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_registerClick", map);
                break;
            case "register":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_register", map);
                break;
            case "rechargeClick":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_rechargeClick", map);
                break;
            case "firstrecharge":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_firstrecharge", map);
                break;
            case "recharge":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_recharge", map);
                break;
            case "withdrawClick":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_withdrawClick", map);
                break;
            case "withdrawOrderSuccess":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_withdrawOrderSuccess", map);
                break;
            case "enterGame":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_enterGame", map);
                break;
            case "vipReward":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_vipReward", map);
                break;
            case "dailyReward":
                AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_dailyReward", map);
                break;
        }
    }

}
