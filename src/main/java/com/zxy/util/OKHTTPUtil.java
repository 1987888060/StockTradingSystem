package com.zxy.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;

public class OKHTTPUtil {
    public static OkHttpClient client = new OkHttpClient();
    //get获取
    public static JSONObject GET(String url) throws IOException {

        Request req = new Request.Builder().url(url).build();
        Response rep = client.newCall(req).execute();
        System.out.println("返回码："+rep.code());
        String string = rep.body().string();
        JSONObject json = JSONObject.parseObject(string);
        return json;
    }

    //post获取
    public static JSONObject POST(String url, JSONObject json) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,json.toJSONString());
        Request req = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        //同步请求
        Call call = client.newCall(req);
        Response response = call.execute();
        System.out.println("返回码："+response.code());
        String string = response.body().string();
        JSONObject jsonObject = JSONObject.parseObject(string);
        return jsonObject;
    }
}
