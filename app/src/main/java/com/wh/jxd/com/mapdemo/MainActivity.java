package com.wh.jxd.com.mapdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.MyLocationStyle;

/**
 * 一个地图的应用
 */

public class MainActivity extends AppCompatActivity implements AMap.OnMyLocationChangeListener, View.OnClickListener {
    private MapView mMapView;
    MyLocationStyle myLocationStyle;
    private AMap mAMap;
    private Button mBt_one;
    private Button mBt_two;
    private Button mBt_three;
    private Button mBt_four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initView(savedInstanceState);
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        myLocationStyle = new MyLocationStyle();
        mAMap.setMyLocationEnabled(true);
        mAMap.setMyLocationStyle(myLocationStyle);
//        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
////      aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
//        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        //设置样式
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE) ;//定位一次，且将视角移动到地图中心点。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW) ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（1秒1次定位）
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE);//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（1秒1次定位）
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        //以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        //显示定位蓝点
        myLocationStyle.showMyLocation(true);
        //自定义定位蓝点的图案
//        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
//        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//        BitmapDescriptor descriptor = new BitmapDescriptor(bitmap);
//        myLocationStyle.myLocationIcon(descriptor);
        //设置锚点
        myLocationStyle.anchor(0, 1);
        //设置圆圈精度
        myLocationStyle.strokeColor(Color.BLUE);
        myLocationStyle.strokeWidth(20);
        //调整定位的频次
//        MyLocationStyle.LOCATION_TYPE_FOLLOW ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（默认1秒1次定位）
//        MyLocationStyle.LOCATION_TYPE_MAP_ROTATE;//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（默认1秒1次定位）
//        MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE;//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移
        myLocationStyle.interval(MyLocationStyle.LOCATION_TYPE_FOLLOW);
//显示室内图
        mAMap.showIndoorMap(true);
    }

    private void initView(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mBt_one = (Button) findViewById(R.id.bt_one);
        mBt_two = (Button) findViewById(R.id.bt_two);
        mBt_three = (Button) findViewById(R.id.bt_three);
        mBt_four = (Button) findViewById(R.id.bt_four);
        mAMap = mMapView.getMap();
        mBt_one.setOnClickListener(this);
        mBt_two.setOnClickListener(this);
        mBt_three.setOnClickListener(this);
        mBt_four.setOnClickListener(this);
        initLocation();

        showControls();
    }

    /**
     * 显示控制的按钮
     */
    private void showControls() {
        //显示指南针


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMyLocationChange(Location location) {
        //当定位发生改变了
        double latitude = location.getLatitude();
        double altitude = location.getAltitude();
        Bundle extras = location.getExtras();
        Toast.makeText(this, "经纬度：" + latitude + " ...." + altitude, Toast.LENGTH_SHORT).show();
    }

    /**
     * 切换地图的多种模式图层切换
     * MAP_TYPE_NAVI(导航) MAP_TYPE_NIGHT(夜景)  MAP_TYPE_NORMAL(白昼)  MAP_TYPE_SATELLITE(卫星)
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_one:
                //标准
                mAMap.setMapType(AMap.MAP_TYPE_NORMAL);
                break;
            case R.id.bt_two:
                //卫星
                mAMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 设置卫星地图模式，aMap是地图控制器对象。
                break;
            case R.id.bt_three:
                //夜间
                mAMap.setMapType(AMap.MAP_TYPE_NIGHT);
                break;
            case R.id.bt_four:
                //交通
                mAMap.setMapType(AMap.MAP_TYPE_NAVI);
                break;
        }
    }
}
