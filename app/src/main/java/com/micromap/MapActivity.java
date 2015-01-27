package com.micromap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.micromap.core.animation.MyPlaceIconAnim;
import com.micromap.core.location.LocationProvider;
import com.micromap.core.map.GeoPoint;
import com.micromap.core.map.MapController;
import com.micromap.core.map.MapView;
import com.micromap.core.map.model.ItemMark;
import com.micromap.core.map.overlay.ItemizedOverlay;
import com.micromap.core.map.overlay.OverlayItem;
import com.micromap.core.map.overlay.OverlayItemUtls;
import com.micromap.core.map.overlay.RouteOverlay;
import com.micromap.model.Position;
import com.micromap.model.SearchRoadUtil;
import com.micromap.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends BaseActivity {
    /** Called when the activity is first created. */
    /**
     * 地图上的控件
     */
    private ImageButton searchContentBtn;
    private ImageButton pathFindBtn;
    private ImageButton myplaceBtn;
    private ZoomControls zoomControls;

    //地图控制信息
    private MicroMapApplication mapManager;
    private MapController mapControl;
    private MapView mapView;
    private Location location;

    private Context context;

    //窗口的大小
    private int screenHeight;
    private int screenWidth;

    //GPS提供模块
    private LocationProvider locationProvider;
    //导航模块
    private MyPlaceIconAnim myPlaceIconAnim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏
        setContentView(R.layout.map_activity);

        context = getApplicationContext();
        mapManager = (MicroMapApplication) getApplication();

		/* 得到XML上的所有控件  */
        getAllWidgets();
		
		/* 添加监听事件  */
        searchContentBtn.setOnClickListener(this);
        pathFindBtn.setOnClickListener(this);
        myplaceBtn.setOnClickListener(this);

        zoomControls.setIsZoomInEnabled(true);
        zoomControls.setIsZoomOutEnabled(true);

        OverlayItemUtls itemUtls = new OverlayItemUtls(mapView);

        mapControl = mapView.getMapControl();

        /** 画出MapManager全局变量中的功能图层 */
        if (mapManager.getMapState() == MicroMapApplication.WORKING_STATE) {
            /** 恢复之前的MapView状态 */

            /** 如果有建筑搜索结果，显示建筑图层 */
            Bundle bundle = this.getIntent().getExtras();
            if (mapManager.itemMarks.size() > 0) {
                List<ItemMark> items = new ArrayList<ItemMark>();
                for (int i = 0; i < mapManager.itemMarks.size(); i++) {
                    items.add(mapManager.itemMarks.get(i));
                }

                //将查询的建筑居中
                String building_name = "";
                if (bundle != null) {
                    building_name = (String) bundle.get("building_name");
                }
                if (building_name.length() > 0) {
                    for (int i = 0; i < items.size(); i++) {
                        ItemMark item = items.get(i);
                        if (item.getName().equals(building_name)) {
                            //
                            if (i != 0) {
                                ItemMark temp = items.get(0);
                                items.set(0, item);
                                items.set(i, temp);
                            }
                            break;
                        }
                    }
                }
                ItemMark temp = items.get(0);
                Log.i("Items-->", "" + items.size() + " ###");
                GeoPoint point = GeoPoint.getGeoPoint(
                        temp.getBuildingMark().getPosition());
                mapControl.setCenter(point);
                Log.i("ItemlizedOverlay", "显示建筑信息");
                ItemizedOverlay overlay = new ItemizedOverlay(mapView);
                overlay.setItemMarks(items);
                overlay.setzIndex(MicroMapApplication.MapConfig.ITEM_OVERLAY_Z_INDEX);
                overlay.displayOverlay();
                mapControl.addOverlayer(overlay);
            }

            /** 如果有道路查询结果，显示道路查询图层  */
            if (mapManager.positions.size() > 0) {
                RouteOverlay overlay = new RouteOverlay(mapView);
                List<OverlayItem> items = itemUtls.getItemsByPath(mapManager.positions);
                //overlay.setRoadMarks(mapManager.searchedRoadMarks);
                overlay.setItems(items);
                overlay.displayOverlay();
                overlay.setzIndex(MicroMapApplication.MapConfig.ROUTE_OVERLAY_Z_INDEX);
                mapControl.addOverlayer(overlay);
            }
        }

        /** 打开导航模块  */
        myPlaceIconAnim = mapView.getMyPlaceIconAnim();
		
		
		/* 放大地图按钮  */
        zoomControls.setOnZoomInClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mapControl.zoomIn();
            }
        });

		/* 缩小地图按钮  */
        zoomControls.setOnZoomOutClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mapControl.zoomOut();
            }
        });
    }

    protected void getAllWidgets() {
		/*
		 * 得到xml上的View
		 */
        LinearLayout.LayoutParams layoutParams;
        int height = 0;
        int width = 0;
        searchContentBtn = (ImageButton) findViewById(R.id.search_button);
        pathFindBtn = (ImageButton) findViewById(R.id.path_finding_button);
        myplaceBtn = (ImageButton) findViewById(R.id.myplace_button);
        zoomControls = (ZoomControls) findViewById(R.id.zoom);
        mapView = (MapView) findViewById(R.id.mapview);

        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
		
	    /* 改变SearchButton控件大小 */
        width = screenWidth / 2;
        height = screenHeight / 12;
        layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(0, 0, 0, 0);
        searchContentBtn.setLayoutParams(layoutParams);
		
		/* 改变PathFindButton控件大小 */
        width = screenHeight / 12;
        if (width < 48) {
            width = 48;
        }
        height = width;
        layoutParams = new LinearLayout.LayoutParams(width, height);
        //layoutParams.setMargins(0, 0, 0, 0);
        pathFindBtn.setLayoutParams(layoutParams);
		
		/* 改变MyPlaceButton控件的大小 */
        myplaceBtn.setLayoutParams(layoutParams);
		
		/* 改变ZoomControl控件的大小 */
        zoomControls.setGravity(Gravity.CENTER);
    }

    private void restoreMapState(){

        mapView.invalidate();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
		/*
		 * 添加监听
		 */
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        // 保存当前地图的基本信息
        mapManager.setMapState(MicroMapApplication.WORKING_STATE);
        //mapManager.setMapView(mapView);
        Intent intent;
        switch (v.getId()) {
            case R.id.search_button: // 点击搜索框
				/*
				 * 转到建筑搜索界面
				 */
                intent = new Intent(MapActivity.this, BuildingSearchActivity.class);
                startActivityForResult(intent, 0);
                Log.i("查找教室", "#####################");
                break;

            case R.id.pathfinding_button: // 点击寻路按钮
                Log.i("寻路", "##############");
				/*
				 * 转到道路查询界面
				 */
                intent = new Intent(MapActivity.this, PathFindingActivity.class);
                startActivityForResult(intent, 0);
                break;

            case R.id.navigation_button:  //点击导航按钮
                Log.i("导航", "#############################");

                //判断是否有道路
                if (mapManager.positions.size() < 1) {
                    Toast.makeText(context, "请选择道路", Toast.LENGTH_SHORT).show();
                    break;
                }

                /** 打开导航动画  */
                LocationManager lManager = (LocationManager)
                        getSystemService(Context.LOCATION_SERVICE);
                // 判断GPS是否正常启动
                if (!lManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(context, "请开启GPS导航...",
                            Toast.LENGTH_SHORT).show();
                    // 返回开启GPS导航设置界面
                    intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 0);
                    break;
                }
                locationProvider = new LocationProvider(context, myPlaceIconAnim);
                location = locationProvider.getMyLocation();
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                Position p1 = new Position(0, longitude, latitude, "");
                if (!myPlaceIconAnim.isShowMyPlaceAnim()) {
                    myPlaceIconAnim.showMyPlaceAnim();
                }
                int dis = SearchRoadUtil.getDistance(p1, mapManager.positions.get(0));
                if (dis > 10) {
                    String info = "dis = " + dis + ", 距离起始点太远，请选择新的路径";
                    Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.myplace_button:     //点击移动到当前位置按钮

                if (locationProvider == null) {
                    LocationManager manager = (LocationManager) getSystemService(
                            Context.LOCATION_SERVICE);
                    // 判断GPS是否正常启动
                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        Toast.makeText(context, "请开启GPS导航",
                                Toast.LENGTH_SHORT).show();
                        // 返回开启GPS导航设置界面
                        intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 0);
                        break;
                    }
                    locationProvider = new LocationProvider(context, myPlaceIconAnim);
                }
                location = locationProvider.getMyLocation();
                if (location != null) {
                    Log.i("Location-->", Double.toString(location.getLongitude()));
                    myPlaceIconAnim.updateMyLocation(location);
                    double longitude1 = location.getLongitude();
                    double latitude1 = location.getLatitude();
                    Position position = new Position(0, longitude1, latitude1, "");
                    GeoPoint geoPoint = GeoPoint.getGeoPoint(position);
                    int x = geoPoint.getMapX(mapView.getMapWidth());
                    int y = geoPoint.getMapY(mapView.getMapHeight());
                    int dx = (screenWidth / 2 - mapView.getMapOffsetX()) - x;
                    int dy = (screenHeight / 2 - mapView.getMapOffsetY()) - y;
                    mapControl.moveMap(dx, dy);
                } else {
                    Toast toast = Toast.makeText(context, "不能获取GPS信息",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
//				myPlaceIconAnim.updateMyLocation(location);
                break;

            default:
                break;
        }
    }



}