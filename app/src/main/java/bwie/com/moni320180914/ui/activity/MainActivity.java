package bwie.com.moni320180914.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import bwie.com.moni320180914.R;
import bwie.com.moni320180914.data.bean.InfoBean;
import bwie.com.moni320180914.di.IContract;
import bwie.com.moni320180914.di.presenter.PresenterImpl;
import bwie.com.moni320180914.ui.adapter.MainAdapters;

public class MainActivity extends AppCompatActivity implements IContract.IView, AMapLocationListener {

    private static final int REQUST_CODE_LOGIN = 1000;
    @BindView(R.id.m_search)
    ImageView mSearch;
    @BindView(R.id.main_mao)
    ImageView mainMao;
    @BindView(R.id.reacycle_view)
    RecyclerView reacycleView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    private IContract.IPersenter iPersenter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE}, 200);
        }
        initLocation();
        ButterKnife.bind(this);
        iPersenter = new PresenterImpl();
        iPersenter.AttData(this);
        // iPersenter.infoData();
        iPersenter.infoPost("美食");
        recyclerView = findViewById(R.id.reacycle_view);
    }



    @Override
    public void showData(final String msg) {
        Log.i("aa", "showData: " + msg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                InfoBean infoBean = gson.fromJson(msg, InfoBean.class);
                List<InfoBean.Pois> data = infoBean.getPois();

                LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);

                MainAdapters mainAdapter = new MainAdapters(data, MainActivity.this);
                recyclerView.setAdapter(mainAdapter);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPersenter.DeleteData(this);
    }

    @OnClick({R.id.m_search, R.id.main_mao,R.id.tv_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.m_search:
                String keywords = etSearch.getText().toString();
                if (!TextUtils.isEmpty(keywords)) {
                    iPersenter.infoPost(keywords);
                }
                //  iPersenter.infoPost();
                break;
            case R.id.main_mao:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                // startActivity(intent);
                startActivityForResult(intent, REQUST_CODE_LOGIN);
                break;
            case R.id.tv_location:
                mlocationClient.startLocation();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUST_CODE_LOGIN && resultCode == RESULT_OK) {
            String iconurl = data.getStringExtra("iconurl");
            // Picasso.with(this).load(iconurl).into(mainMao);
            Glide.with(this).load(iconurl).apply(new RequestOptions().circleCrop()).into(mainMao);
        }
    }

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    private void initLocation() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true
        mLocationOption.setNeedAddress(true);
        mlocationClient = new AMapLocationClient(this);
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(10000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
    }




        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
             if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                     //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                    String address = amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    String country = amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                   // amapLocation.getAOIName();//获取当前定位点的AOI信息
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapSuccess","address:"
                            + address + ", country:"
                            + country);
                    Toast.makeText(this, "address:"
                            + address + ", country:"
                            + country, Toast.LENGTH_SHORT).show();
                    tvLocation.setText(address);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

}
