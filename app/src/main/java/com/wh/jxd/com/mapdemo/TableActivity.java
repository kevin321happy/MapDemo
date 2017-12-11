package com.wh.jxd.com.mapdemo;

import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;


/**
 * Created by kevin321vip on 2017/12/11.
 */

public class TableActivity extends AppCompatActivity implements AMap.OnMyLocationChangeListener {
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private MapView mMapView;
    private AMap mAMap;
    private MyLocationStyle myLocationStyle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_layout);
        ActionBar ab = getSupportActionBar();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer);
        NavigationView navigationView =
                (NavigationView) findViewById(R.id.nv_main_navigation);
        //初始化地图控件
        initView(savedInstanceState);


        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    item.setChecked(true);
                    mDrawerLayout.closeDrawers();
                    return true;
                }
            });
        }
    }

    /**
     * 初始化视图
     *
     * @param savedInstanceState
     */
    private void initView(Bundle savedInstanceState) {
        mMapView = (MapView) findViewById(R.id.map);
        mAMap = mMapView.getMap();
        mMapView.onCreate(savedInstanceState);

        initLocation();

    }

    private void initLocation() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
        setUpMap();
        //设置SDK 自带定位消息监听
        mAMap.setOnMyLocationChangeListener(this);
    }

    private void setUpMap() {
        //如果要设置定位的默认状态，可以在此处进行设置
        myLocationStyle = new MyLocationStyle();
        mAMap.setMyLocationStyle(myLocationStyle);
        mAMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        mAMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示
    }
    @Override
    public void onMyLocationChange(Location location) {

    }
}
