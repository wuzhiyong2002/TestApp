package com.slot.testapp.ku;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.appsflyer.AppsFlyerLib;
import com.slot.testapp.Config;
import com.slot.testapp.Customer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class KuSlot implements Customer {
    AppCompatActivity activity;
    WebView webView;
    private static final String TAG = "KuSlot";

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
        return Config.DEBUG ? "https://www.afun.com/casino?tab=Slots&ch=1000000&gplat=4#/register" : null;
    }

    @Override
    public String[] getJsInterface() {
        return new String[]{"jsThirdBridge"};
    }


    @JavascriptInterface
    public void appsFlyerEvent(String data) {
        Map<String, Object> eventParameters0 = new HashMap<String, Object>();
        JSONObject jsonData = JSON.parseObject(data);
        Iterator<Map.Entry<String, Object>> it = jsonData.entrySet().iterator();
        String eventType = "";
        while (it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            if (key.equals("event_type")) {
                eventType = value.toString();
            }
            eventParameters0.put(key, value);
        }
        if (!eventType.equals("")) {
            Log.e(TAG, "eventType: " + eventType);
            switch (eventType) {
                case "af_firstOpen ":
                    AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_firstOpen", eventParameters0);
                    break;
                case "af_login":
                    AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_login", eventParameters0);
                    break;
                case "af_complete_registration":
                    AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_complete_registration", eventParameters0);
                    break;
                case "af_purchase":
                    AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_purchase", eventParameters0);
                    break;
                case "af_first_purchase":
                    AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_first_purchase", eventParameters0);
                    break;
                case "af_refund":
                    AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_refund", eventParameters0);
                    break;
                case "af_recharge":
                    AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_recharge", eventParameters0);
                    break;
                case "af_withdraw":
                    AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_withdraw", eventParameters0);
                    break;
                case "click_deposit":
                    AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "click_deposit", eventParameters0);
                    break;
                case "af_login_out":
                    AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_login_out", eventParameters0);
                    break;
                case "af_share":
                    AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "af_share", eventParameters0);
                    break;
                case "goToOptionGame":
                    AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "goToOptionGame", eventParameters0);
                    break;
                case "activity":
                    AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "activity", eventParameters0);
                    break;
                case "banner":
                    AppsFlyerLib.getInstance().logEvent(activity.getApplicationContext(), "banner", eventParameters0);
                    break;
            }

        }
    }

}
