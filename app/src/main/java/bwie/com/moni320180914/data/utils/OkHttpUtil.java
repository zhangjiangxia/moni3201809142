package bwie.com.moni320180914.data.utils;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtil {
    public static OkHttpUtil okHttpUtil;
    public OkHttpClient okHttpClient;

    private OkHttpUtil() {
        if (null == okHttpClient) {
            synchronized (OkHttpClient.class) {
                if (null == okHttpClient) {
                    okHttpClient = new OkHttpClient
                            .Builder()
                            .build();
                }
            }
        }
    }

    public static OkHttpUtil getInstance() {
        if (null == okHttpUtil) {
            synchronized (OkHttpUtil.class) {
                if (null == okHttpUtil) {
                    okHttpUtil = new OkHttpUtil();
                }
            }
        }
        return okHttpUtil;
    }


    public void get(String urlString, Callback callback) {
        Request request = new Request.Builder().url(urlString).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public void post(String urlString, FormBody formBody, Callback callback) {
        Request request = new Request.Builder().method("POST", formBody).url(urlString).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
