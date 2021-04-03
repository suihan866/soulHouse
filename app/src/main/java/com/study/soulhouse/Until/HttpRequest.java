package com.study.soulhouse.Until;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpRequest {
    public static void request(String address,okhttp3.Callback Callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        //结果的处理可能开了线程
        client.newCall(request).enqueue(Callback);
    }
}
