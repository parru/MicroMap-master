package com.micromap;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.micromap.core.utils.OverlayItemUtls;
import com.micromap.core.map.overlay.RouteOverlay;
import com.micromap.model.Position;
import com.micromap.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends BaseActivity {
    /** Called when the activity is first created. */
    /**
     * 地图上的控件
     */
    private LinearLayout topLayout;
    private TextView searchContentTxt;
    private ImageButton pathFindBtn;
    private ImageButton myplaceBtn;
    private ZoomControls zoomControls;

    //地图控制信息
    private MicroMapApplication mapManager;
    private MapController mapControl;
    private MapView mapView;
    private Location location;

    private Context context;

    //GPS提供模块
    private LocationProvider locationProvider;
    //导航模块
    private MyPlaceIconAnim myPlaceIconAnim;

    public static final int ACTIVITY_RESULT_CODE = 20;

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
        searchContentTxt.setOnClickListener(this);
        pathFindBtn.setOnClickListener(this);
        myplaceBtn.setOnClickListener(this);

        zoomControls.setIsZoomInEnabled(true);
        zoomControls.setIsZoomOutEnabled(true);

        mapControl = mapView.getMapControl();

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
        searchContentTxt = (TextView) findViewById(R.id.search_button);
        pathFindBtn = (ImageButton) findViewById(R.id.path_finding_button);
        myplaceBtn = (ImageButton) findViewById(R.id.myplace_button);
        zoomControls = (ZoomControls) findViewById(R.id.zoom);
        mapView = (MapView) findViewById(R.id.mapview);
        topLayout = (LinearLayout) findViewById(R.id.mapview_top);
		
		/* 改变ZoomControl控件的大小 */
        zoomControls.setGravity(Gravity.CENTER);
    }

    private void restoreMapState() {

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
                    mapControl.setCenter(geoPoint);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        addItemOverlay();
        addRootOverlay();
        if (resultCode == ACTIVITY_RESULT_CODE) {
            mapView.invalidate();
        }
    }

    private void addItemOverlay() {
        if (mapManager.itemMarks == null || mapManager.itemMarks.size() == 0) {
            return;
        }
        List<OverlayItem> items = OverlayItemUtls.getItemsByItemMarks(mapManager.itemMarks, mapView);
        ItemizedOverlay overlay = new ItemizedOverlay(mapView, items);
        overlay.displayOverlay();
        overlay.showPopUpWindow();
        mapControl.addOverlayer(overlay);
    }

    private void addRootOverlay(){
        if(mapManager.positions == null || mapManager.positions.size() == 0){
            return;
        }
    }
}