package com.wh.jxd.com.mapdemo.inter;

import android.view.View;

import com.amap.api.maps.model.Marker;

/**
 * Created by kevin321vip on 2017/12/8.
 * 自定义InfoWindow
 */

public interface InfoWindowAdapter {

    View getInfoWindow(Marker marker);

    View getInfoContents(Marker marker);
}
