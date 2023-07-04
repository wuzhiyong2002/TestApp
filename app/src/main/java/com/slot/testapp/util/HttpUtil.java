package com.slot.testapp.util;

import android.content.Context;

import com.google.android.gms.common.api.Response;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    public void POST(String url, Context context,Callback callback) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "    {\r\n        \"api\":\"app\"\r\n    }");
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("X-Requested-With", context.getPackageName())
                .addHeader("Content-Type", "application/json")
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

}
