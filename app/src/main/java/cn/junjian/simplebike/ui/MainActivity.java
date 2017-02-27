package cn.junjian.simplebike.ui;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.junjian.simplebike.R;
import cn.junjian.simplebike.base.BaseActivity;

/**
 * Created by junjianliu
 * on 17/2/14
 * email:spyhanfeng@qq.com
 */
public class MainActivity extends BaseActivity implements IMain, AMapLocationListener, View.OnClickListener {

    private Toolbar toolbar;

    private MapView mapView;
    private AMap aMap;

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        aMap = mapView.getMap();

        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        // 隐藏地图缩放按钮
        aMap.getUiSettings().setZoomControlsEnabled(false);

        initMap();

        findViewById(R.id.layout_scan).setOnClickListener(this);
    }

    @Override
    public void init() {

    }

    @Override
    public void sreach() {

    }

    @Override
    public void userinfo() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_home,menu);
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_sreach){
            //搜索
        }

        return super.onOptionsItemSelected(item);
    }

    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient;
    @Override
    public void initMap() {

        //声明mLocationOption对象

        mlocationClient = new AMapLocationClient(getApplicationContext());
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();//设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(5000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();

    }

    @Override
    public void refresh() {

    }

    @Override
    public void help() {

    }

    @Override
    public void richScan() {

    }

    private void toLoc(LatLng loc){
        changeCamera(
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        loc, 18, 30, 30)));
        aMap.clear();
        aMap.addMarker(new MarkerOptions().position(loc)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
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

                Log.e("TAG >>>", "onLocationChanged: amapLocation 2 = " + amapLocation.toString());

                LatLng temp = new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
                toLoc(temp);

                mlocationClient.stopLocation();

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update) {

        aMap.moveCamera(update);

    }

    private void initPop(){

        View view = View.inflate(this,R.layout.view_pop_user_help,null);
        if(popupWindow == null) {
            popupWindow = new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            //popupWindow.setContentView(view);

            //popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            //popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            popupWindow.setFocusable(true);
            popupWindow.setTouchable(true);
            popupWindow.setTouchable(true);
            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    //点击PopupWindow以外区域时PopupWindow消失
                    if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        popupWindow.dismiss();
                    }
                    return false;
                }

            });

            view.findViewById(R.id.layout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });

            view.findViewById(R.id.layout_chat).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            view.findViewById(R.id.layout_lock).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            view.findViewById(R.id.layout_repair).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            view.findViewById(R.id.layout_warming).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            popupWindow.showAtLocation(findViewById(R.id.layout_scan), Gravity.BOTTOM,0,0);
        }else{
            popupWindow.showAtLocation(findViewById(R.id.layout_scan), Gravity.BOTTOM,0,0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_scan:
                initPop();
                break;
        }
    }
}
