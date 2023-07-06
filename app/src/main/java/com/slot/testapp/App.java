package com.slot.testapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAttribution;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.BuildConfig;
import com.adjust.sdk.LogLevel;
import com.adjust.sdk.OnAttributionChangedListener;
import com.appsflyer.AFLogger;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.slot.testapp.xiaomantou.XiaomantouSlot;

import java.util.Map;
import java.util.Objects;

public class App extends Application {

    private static final String TAG = "Application";
    AppsFlyerConversionListener conversionListener;

    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().remove("link").apply();
        conversionListener = new AppsFlyerConversionListener() {

            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                if (map == null) {
                    return;
                }
                for (String attrName : map.keySet()) {
                    Log.e(TAG, "attribute: " + attrName + " = " + map.get(attrName));
                    if (attrName.equals("af_status")) {
                        String af_status = Objects.requireNonNull(map.get("af_status")).toString();
                        Log.e(TAG, "af_status: " + af_status);
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("af_status", af_status).apply();
                    }
                }
            }

            @Override
            public void onConversionDataFail(String s) {
                Log.e(TAG, "onConversionDataFail: " + s);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
                Log.e(TAG, "onAppOpenAttribution: " + attributionData);
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.e(TAG, "onAttributionFailure: " + errorMessage);
            }
        };

        initAD();
        initAF();
    }

    private void initAF() {


        AppsFlyerLib appsflyer = AppsFlyerLib.getInstance();
        appsflyer.setMinTimeBetweenSessions(0);
        appsflyer.setLogLevel(AFLogger.LogLevel.VERBOSE);
        appsflyer.setDebugLog(true);
        appsflyer.init(Config.APPSFLYER_KEY, conversionListener, this);
        appsflyer.start(this);
    }

    private void initAD() {
        //初始化Adjust
        String environment = BuildConfig.DEBUG ? AdjustConfig.ENVIRONMENT_SANDBOX : AdjustConfig.ENVIRONMENT_PRODUCTION;
        AdjustConfig config = new AdjustConfig(this, Config.ADJUST_KEY, environment);
        config.setOnAttributionChangedListener(new OnAttributionChangedListener() {
            @Override
            public void onAttributionChanged(AdjustAttribution adjustAttribution) {
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("ad_status", adjustAttribution.trackerName).apply();

            }
        });

        config.setLogLevel(LogLevel.VERBOSE);
        Adjust.onCreate(config);
        this.registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());
    }


    public static final class AdjustLifecycleCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(@androidx.annotation.NonNull Activity activity, @androidx.annotation.Nullable android.os.Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(@androidx.annotation.NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(@androidx.annotation.NonNull Activity activity) {
            Adjust.onResume();
        }

        @Override
        public void onActivityPaused(@androidx.annotation.NonNull Activity activity) {
            Adjust.onPause();
        }

        @Override
        public void onActivityStopped(@androidx.annotation.NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@androidx.annotation.NonNull Activity activity, @androidx.annotation.NonNull android.os.Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@androidx.annotation.NonNull Activity activity) {

        }
    }
}
