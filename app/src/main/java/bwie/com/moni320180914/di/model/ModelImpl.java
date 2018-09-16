package bwie.com.moni320180914.di.model;


import java.io.IOException;

import bwie.com.moni320180914.data.utils.OkHttpUtil;
import bwie.com.moni320180914.di.IContract;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

public class ModelImpl implements IContract.IModel{
    private static final String URL="https://restapi.amap.com/v3/place/around?key=d78f39012867929dc6ad174dd498f51f&location=116.473168,39.993015&keywords=%E7%BE%8E%E9%A3%9F&types=&radius=1000&offset=20&page=1&extensions=all";
    private static final String BASE_URL = "https://restapi.amap.com/v3/place/around";
    @Override
    public void requestData(final onCallBacklisenter onCallBacklisenter) {
        OkHttpUtil.getInstance().get(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String estring = e.getMessage().toString();
                onCallBacklisenter.stringMsg(estring);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();
                onCallBacklisenter.stringMsg(responseString);
            }
        });
    }

    @Override
    public void requestDataPost(String keyworkd,final onCallBacklisenter onCallBacklisenter) {
        FormBody formBody = new FormBody.Builder()
                .add("key","d78f39012867929dc6ad174dd498f51f")
                .add("location","116.473168,39.993015")
                .add("keywords",keyworkd)
                .add("types","")
                .add("radius","1000")
                .add("offset","20")
                .add("page","1")
                .add("extensions","all")
                .build();
        OkHttpUtil.getInstance().post(BASE_URL, formBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String estring = e.getMessage().toString();
                onCallBacklisenter.stringMsg(estring);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseString = response.body().string();
                onCallBacklisenter.stringMsg(responseString);
            }
        });
    }
}
