package com.wh.jxd.com.mapdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Gradient;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.wh.jxd.com.mapdemo.inter.InfoWindowAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 一个地图的应用
 */

public class MainActivity extends AppCompatActivity implements AMap.OnMyLocationChangeListener, View.OnClickListener, LocationSource, AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.OnMapLoadedListener, AMap.OnCameraChangeListener, View.OnLongClickListener {
    private MapView mMapView;
    MyLocationStyle myLocationStyle;
    private AMap mAMap;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private Button mBt_one;
    private Button mBt_two;
    private Button mBt_three;
    private Button mBt_four;
    private LinearLayout mLl_top;
    private List<LatLng> mMarkerOptions = new ArrayList<>();
    private Marker mScreenMarker;
    private LatLonPoint mLatLonPoint;
    private GeocodeSearch mGeocodeSearch;
    private Button mBt_five;
    private boolean zoomToMax;//是否放大到最大级别


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();
        initView(savedInstanceState);
    }
    /**
     * 初始化定位
     */
    private void initLocation() {

        if (mAMap == null) {
            mAMap = mMapView.getMap();

        }
        setUpMap();
        //设置SDK 自带定位消息监听
        mAMap.setOnMyLocationChangeListener(this);
//        myLocationStyle = new MyLocationStyle();
//        mAMap.setMyLocationEnabled(true);
//        mAMap.setMyLocationStyle(myLocationStyle);
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

//        mAMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER));
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）默认执行此种模式。
        //以下三种模式从5.1.0版本开始提供
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，并且蓝点会跟随设备移动。
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_MAP_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，地图依照设备方向旋转，并且蓝点会跟随设备移动。
        //显示定位蓝点
//        myLocationStyle.showMyLocation(true);
        //自定义定位蓝点的图案
//        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
//        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//        BitmapDescriptor descriptor = new BitmapDescriptor(bitmap);
//        myLocationStyle.myLocationIcon(descriptor);
        //设置锚点
//        myLocationStyle.anchor(0, 1);
//        //设置圆圈精度
//        myLocationStyle.strokeColor(Color.BLUE);
//        myLocationStyle.strokeWidth(20);
        //调整定位的频次
//        MyLocationStyle.LOCATION_TYPE_FOLLOW ;//连续定位、且将视角移动到地图中心点，定位蓝点跟随设备移动。（默认1秒1次定位）
//        MyLocationStyle.LOCATION_TYPE_MAP_ROTATE;//连续定位、且将视角移动到地图中心点，地图依照设备方向旋转，定位点会跟随设备移动。（默认1秒1次定位）
//        MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE;//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移
//        myLocationStyle.interval(MyLocationStyle.LOCATION_TYPE_FOLLOW);
////显示室内图
//        mAMap.showIndoorMap(true);
    }

    private void setUpMap() {
        // 如果要设置定位的默认状态，可以在此处进行设置
        myLocationStyle = new MyLocationStyle();
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示
    }

    private void initView(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mLl_top = (LinearLayout) findViewById(R.id.ll_top);
        mBt_one = (Button) findViewById(R.id.bt_one);
        mBt_two = (Button) findViewById(R.id.bt_two);
        mBt_three = (Button) findViewById(R.id.bt_three);
        mBt_four = (Button) findViewById(R.id.bt_four);
        mBt_five = (Button) findViewById(R.id.bt_five);
        //实例化对象
        mAMap = mMapView.getMap();
        mUiSettings = mAMap.getUiSettings();

        mAMap.setOnMarkerClickListener(this);
        mAMap.setOnInfoWindowClickListener(this);
        mAMap.setOnMapLoadedListener(this);
        mAMap.setOnCameraChangeListener(this);
        mBt_one.setOnClickListener(this);
        mBt_two.setOnClickListener(this);
        mBt_three.setOnClickListener(this);
        mBt_four.setOnClickListener(this);
        mBt_five.setOnClickListener(this);
        mBt_five.setOnLongClickListener(this);
        initLocation();
        //地图控件的交互
        showControls();
        setGestures();
//        //调用方法交互
        callMethod(savedInstanceState);
//        //地图上绘制点
        markOptions();
//        //自定义InfoWindow
        CustomInfoWindow();
//        //绘制热力图
        drawHeatDrawable();
        //经纬度的逆转换
//        queryAddress(mLatLonPoint);
    }

    /**
     * 绘制热力图
     */
    private void drawHeatDrawable() {
        //生成热力点坐标列表
        LatLng[] latlngs = new LatLng[500];
        double x = 28.120000;
        double y = 112.590000;

        for (int i = 0; i < 500; i++) {
            double x_ = 0;
            double y_ = 0;
            x_ = Math.random() * 0.5 - 0.25;
            y_ = Math.random() * 0.5 - 0.25;
            latlngs[i] = new LatLng(x + x_, y + y_);
        }
        //构建热力图
        // 构建热力图 HeatmapTileProvider
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.data(Arrays.asList(latlngs)); // 设置热力图绘制的数据
//                .gradient(new Gradient(new int[]{1,2,3,4,5})); // 设置热力图渐变，有默认值 DEFAULT_GRADIENT，可不设置该接口
// Gradient 的设置可见参考手册
// 构造热力图对象
        HeatmapTileProvider heatmapTileProvider = builder.build();
        //第三步  绘制热力图层
        // 初始化 TileOverlayOptions
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者
        // 向地图上添加 TileOverlayOptions 类对象
        mAMap.addTileOverlay(tileOverlayOptions);


    }

    /**
     * 自定义标记点的弹出window
     */
    private void CustomInfoWindow() {
        AMap.InfoWindowAdapter infoWindowAdapter = new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            View infoWindow = null;

            @Override
            public View getInfoContents(Marker marker) {
                if (infoWindow == null) {
                    infoWindow = LayoutInflater.from(MainActivity.this).inflate(
                            R.layout.custom_info_window, null);
                }
                render(marker, infoWindow);
                return infoWindow;
            }
        };
        mAMap.setInfoWindowAdapter(infoWindowAdapter);
    }

    private void markOptions() {
        mMarkerOptions.add(new LatLng(28.227780, 112.938860));
        mMarkerOptions.add(new LatLng(28.227481, 112.937862));
        mMarkerOptions.add(new LatLng(28.227782, 112.938863));
        mMarkerOptions.add(new LatLng(28.223783, 112.932864));
        mMarkerOptions.add(new LatLng(28.217784, 112.968865));

        for (LatLng latLng : mMarkerOptions) {
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(latLng);
            markerOption.title("长沙市").snippet("长沙，湖南省省会，古称潭州，别名星城，地处湖南省东部偏北，湘江下游和湘浏盆地西缘，是长江中游地区重要的中心城市，全国“两型社会”综合配套改革试验区、中南地区重要的工商业城市，中国重要粮食生产基地、能源原材料基地、装备制造业... ");
            markerOption.draggable(true);//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.drawable.ic_party)));
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(true);
            //设置marker平贴地图效果
            mAMap.addMarker(markerOption);
        }
//        LatLng latLng = new LatLng(28.227780, 112.938860);
//        LatLng latLng1 = new LatLng(28.227481, 112.937862);
//        LatLng latLng2 = new LatLng(28.227782, 112.938863);
//        LatLng latLng3 = new LatLng(28.223783, 112.932864);
//        LatLng latLng4 = new LatLng(28.217784, 112.968865);
//
//        final Marker marker = mAMap.addMarker(new MarkerOptions().position(latLng).title("湖南华顺").snippet("View的大小不仅由自身所决定"));
//        final Marker marker1 = mAMap.addMarker(new MarkerOptions().position(latLng1).title("政府").snippet("这种情况下需要开启新的线程"));
//        final Marker marker2 = mAMap.addMarker(new MarkerOptions().position(latLng2).title("银行").snippet("高德开放平台目前开放了Android 地图"));
//        final Marker marker3 = mAMap.addMarker(new MarkerOptions().position(latLng3).title("体育馆").snippet("View和Activity一样的，"));
//        final Marker marker4 = mAMap.addMarker(new MarkerOptions().position(latLng4).title("饭店").snippet("金麓国际大酒店........"));
        //自定义绘制mark
//        MarkerOptions markerOption = new MarkerOptions();
//        LatLng latLng = new LatLng(28.221780, 112.938860);
//        markerOption.position(latLng);
////        markerOptions.position(SyncStateContract.Constants.XIAN);
//        markerOption.title("长沙市").snippet("长沙市：28.227481, 112.937862");
//        markerOption.draggable(true);//设置Marker可拖动
//        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                .decodeResource(getResources(), R.drawable.ic_party)));
//        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
//        markerOption.setFlat(true);//设置marker平贴地图效果
//        mAMap.addMarker(markerOption);

    }


    /**
     * 调用方法交互
     *
     * @param savedInstanceState
     */
    private void callMethod(Bundle savedInstanceState) {
        //地图视图移动动画
//        CameraUpdate cameraUpdate = new CameraUpdate();
//        mAMap.animateCamera(cameraUpdate);
        //改变地图的中心点
//        28.2277800000,112.9388600000
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(28.227780, 112.938860), 18, 30, 0));

        mAMap.animateCamera(mCameraUpdate);
        //设置缩放的级别
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        //限制地图的显示范围
        //南、西
//        LatLng southwestLatLng = new LatLng(30.080000, 108.470000);
        //北、东
//        LatLng northeastLatLng = new LatLng(24.380000, 114.150000);
//        LatLngBounds latLngBounds = new LatLngBounds(southwestLatLng, northeastLatLng);
//        mAMap.setMapStatusLimits(latLngBounds);
        //改变地图的默认显示区域
        // 定义长沙市经纬度坐标（此处以长沙坐标为例）
//        28.2277800000,112.9388600000
//        LatLng centerBJPoint= new LatLng(28.227780,112.938860);
//// 定义了一个配置 AMap 对象的参数类
//        AMapOptions mapOptions = new AMapOptions();
// 设置了一个可视范围的初始化位置
// CameraPosition 第一个参数： 目标位置的屏幕中心点经纬度坐标。
// CameraPosition 第二个参数： 目标可视区域的缩放级别
// CameraPosition 第三个参数： 目标可视区域的倾斜度，以角度为单位。
// CameraPosition 第四个参数： 可视区域指向的方向，以角度为单位，从正北向顺时针方向计算，从0度到360度
//        mapOptions.camera(new CameraPosition(centerBJPoint, 10f, 0, 0));
//// 定义一个 MapView 对象，构造方法中传入 mapOptions 参数类
//        MapView mapView = new MapView(this, mapOptions);
//
//// 调用 onCreate方法 对 MapView LayoutParams 设置
//        mapView.onCreate(savedInstanceState);
    }

    /**
     * 设置手势
     */
    private void setGestures() {
        //缩放
        mUiSettings.setZoomGesturesEnabled(true);
        //滑动
        mUiSettings.setScrollGesturesEnabled(true);
        //旋转
        mUiSettings.setRotateGesturesEnabled(true);
        //倾斜
        mUiSettings.setTiltGesturesEnabled(true);
        //指定屏幕中心点
        //x、y均为屏幕坐标，屏幕左上角为坐标原点，即(0,0)点。
//        mAMap.setPointToCenter(0, 0);
        mAMap.getUiSettings().setGestureScaleByMapCenter(true);
    }

    /**
     * 显示控制的按钮
     */
    private void showControls() {
//        mLl_top.setVisibility(View.GONE);
        //显示指南针
        mUiSettings.setCompassEnabled(false);
        //缩放按钮
        mUiSettings.setZoomControlsEnabled(true);
        //点击定位
//        mAMap.setLocationSource(this);//通过aMap对象设置定位数据源的监听
        mUiSettings.setMyLocationButtonEnabled(false); //显示默认的定位按钮
//        mAMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置
        //比例尺控件
        mUiSettings.setScaleControlsEnabled(true);
        //控制LOGO的位置
        mUiSettings.setLogoPosition(AMapOptions.LOGO_MARGIN_RIGHT);

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
//        double latitude = location.getLatitude();
//        double altitude = location.getAltitude();
//        Bundle extras = location.getExtras();
//        Toast.makeText(this, "经纬度：" + latitude + " ...." + altitude, Toast.LENGTH_SHORT).show();
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
            case R.id.bt_five:
                Toast.makeText(this, "点击了改变视图", Toast.LENGTH_SHORT).show();
                //改变地图的缩放
                if (zoomToMax == false) {
                    mAMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                    zoomToMax = true;
                } else {
                    mAMap.moveCamera(CameraUpdateFactory.zoomTo(14));
                    zoomToMax = false;
                }
                // 定位、且将视角移动到地图中心点，定位点依照设备方向旋转，  并且会跟随设备移动。
//                mAMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE));
                break;
        }
    }

    /**
     * 定位数据源的监听
     *
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }


    /**
     * 标记点的点击事件
     *
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        //返回true自己处理点击事件
//        showMarkAnimation(marker);
//        MarkerOptions options = marker.getOptions();
//        Toast.makeText(this, options.getSnippet(), Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 显示标记点的动画
     *
     * @param marker
     */
    private void showMarkAnimation(Marker marker) {
        Animation animation = new RotateAnimation(marker.getRotateAngle(), marker.getRotateAngle() + 180, 0, 0, 0);
        long duration = 1000L;
        animation.setDuration(duration);
        animation.setInterpolator(new LinearInterpolator());
        marker.setAnimation(animation);
        marker.startAnimation();
    }

    /**
     * 自定义infowindow样式
     */
    public void render(Marker marker, View view) {
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_title.setText(marker.getTitle());
        tv_content.setText(marker.getSnippet());
    }

    /**
     * infoWindow的点击事件
     *
     * @param marker
     */
    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    /**
     * 地图加载的监听
     */
    @Override
    public void onMapLoaded() {
//        LatLng centerPoint = getMapCenterPoint();
//        Toast.makeText(this, "centerPoint.longitude+centerPoint.latitude:" + (centerPoint.longitude + centerPoint.latitude), Toast.LENGTH_SHORT).show();
        addMarkersToMap();
    }

    /**
     * 将标记点移动到地图中心
     */
    private void addMarkersToMap() {
        LatLng latLng = mAMap.getCameraPosition().target;
        Point screenPosition = mAMap.getProjection().toScreenLocation(latLng);
        mScreenMarker = mAMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin)));
        //设置Marker在屏幕上,不跟随地图移动
        mScreenMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;

        Log.i("test", "地图经纬度：" + latLng.latitude + latLng.longitude);

//     Toast.makeText(this, "经纬度" + latitude + "   " + longitude, Toast.LENGTH_SHORT).show();
//     Toast.makeText(this,"经纬度："+latitude+"   "+longitude,1);
    }

    /**
     * by moos on 2017/09/05
     * func:获取屏幕中心的经纬度坐标
     *
     * @return
     */
    public LatLng getMapCenterPoint() {
        int left = mMapView.getLeft();
        int top = mMapView.getTop();
        int right = mMapView.getRight();
        int bottom = mMapView.getBottom();
        // 获得屏幕点击的位置
        int x = (int) (mMapView.getX() + (right - left) / 2);
        int y = (int) (mMapView.getY() + (bottom - top) / 2);
        Projection projection = mAMap.getProjection();
        LatLng pt = projection.fromScreenLocation(new Point(x, y));
        return pt;
    }

    /**
     * 监听地图改变
     *
     * @param cameraPosition
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {


    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        LatLng centerPoint = getMapCenterPoint();
        double latitude = centerPoint.latitude;
        double longitude = centerPoint.longitude;
        mLatLonPoint = new LatLonPoint(latitude, longitude);
        try {
            queryAddress(mLatLonPoint);
        } catch (AMapException e) {
            e.printStackTrace();
        }
//        Toast.makeText(this, "centerPoint.longitude+centerPoint.latitude:" + (centerPoint.longitude + "," + centerPoint.latitude), Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据中心点的坐标查地址
     *
     * @param centerPoint
     */
    private void queryAddress(final LatLonPoint centerPoint) throws AMapException {
        if (mGeocodeSearch == null) {
            mGeocodeSearch = new GeocodeSearch(this);
        }
        final Geocoder geocoder = new Geocoder(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Address> fromLocation = geocoder.getFromLocation(centerPoint.getLatitude(), centerPoint.getLongitude(), 20);
                    for (final Address address : fromLocation) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, address.getAddressLine(1) + address.getFeatureName(), Toast.LENGTH_SHORT).show();
                            }
                        });
//                        Message message = new Message();
//                        message.what=1;
//                        message.obj = address.getAddressLine(1) + address.getFeatureName();
//
//                        mHandler.handleMessage(message);


//                Toast.makeText(this, address.getExtras().toString(), Toast.LENGTH_SHORT).show();
//                        Log.d("MainActivity", address.toString());
//                        Toast.makeText(MainActivity.this, address.getAddressLine(1) +address.getFeatureName(), Toast.LENGTH_SHORT).show();
                    }
//            Toast.makeText(this, "fromLocation:" +fromLocation.get(0).toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

//        RegeocodeQuery query = new RegeocodeQuery(centerPoint, 200, GeocodeSearch.AMAP);
//        RegeocodeAddress fromLocation = mGeocodeSearch.getFromLocation(query);


//        mGeocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
//            @Override
//            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
//                RegeocodeAddress address =
//                        regeocodeResult.getRegeocodeAddress();
//                if (centerPoint!=null){
//                    RegeocodeQuery query = new RegeocodeQuery(centerPoint, 200, GeocodeSearch.AMAP);
//                    try {
//                        RegeocodeAddress fromLocation = mGeocodeSearch.getFromLocation(query);
//                        Log.d("address",fromLocation.getFormatAddress());
//                    } catch (AMapException e) {
//                        e.printStackTrace();
//                    }
//                }


//            }
//
//            @Override
//            public void onGeocodeSearched(GeocodeResult geocodeResult, int i){
//
//
//            }
//        });

    }

    @Override
    public boolean onLongClick(View v) {
        //监听长按事件跳转
        Intent intent = new Intent(this, TableActivity.class);
        startActivity(intent);
        return true;
    }
}
