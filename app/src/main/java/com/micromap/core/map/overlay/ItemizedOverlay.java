package com.micromap.core.map.overlay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;

import com.micromap.MicroMapApplication;
import com.micromap.core.map.GeoPoint;
import com.micromap.core.map.MapView;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Pingfu
 */
@SuppressLint("HandlerLeak")
public class ItemizedOverlay extends Overlay {
    private List<OverlayItem> items;
    private Handler handler;
    private int index = 0;
    private boolean finishShowMark = false;

    public ItemizedOverlay(MapView mapView) {
        super(mapView);
        // TODO Auto-generated constructor stub
        items = new ArrayList<OverlayItem>();
        showPopUpWindow();
    }

    public ItemizedOverlay(MapView mapView, List<OverlayItem> overlayItems){
        super(mapView);
        items = overlayItems;
    }

    /**
     * draw the items which are in the screen in this overlayer
     *
     */
    @Override
    public void draw(Canvas canvas, int deepZoom) {
        // 判断是否显示当前图层
        if (!this.isShowingOverlay()) {
            return;
        }
        int mapOffsetX = getMapView().getMapOffsetX();
        int mapOffsetY = getMapView().getMapOffsetY();

        int mapHeight = getMapView().getMapHeight();
        int mapWidth = getMapView().getMapWidth();

        int screenHeight = getMapView().getScreenHeight();
        int screenWidth = getMapView().getScreenWidth();
        Paint paint = new Paint();
        for (int i = 0; i < items.size()&& i < 5; i++) {
            OverlayItem item = items.get(i);
            GeoPoint point = item.getPoint();
            int mapX = point.getMapX(mapWidth);
            int mapY = point.getMapY(mapHeight);

            int x = mapX + mapOffsetX;
            int y = mapY + mapOffsetY;
            if (x < screenWidth && y < screenHeight && x > 0 && y > 0) {
                // 画出图层上的标注，标注的大小是30 * 30
//                item.getMarkerByNumber(i);
                item.isClickable = true;
                canvas.drawBitmap(item.getMarker(), x - 15, y - 30, paint);
            }
        }
        if(!finishShowMark && handler != null){
            handler.obtainMessage(0).sendToTarget();
        }
    }

    @Override
    public void onClick(int x, int y) {
        // TODO Auto-generated method stub
        for (int i = 0; i < items.size();i ++){
            OverlayItem mark = items.get(i);
            GeoPoint point = GeoPoint.getGeoPoint(mark.getPoint().getPosition());
            String description = mark.getTitle();
            String title = mark.getTitle();
            int item_type = 0;
            OverlayItem item = items.get(i);
            Context context = getMapView().getContext();
            item = new OverlayItem(point, description, title, item_type, context);
            if (item.isClickable()) {
//                item.onClick(x, y, getMapView(), mark);
            }
        }
    }
    
    public void showPopUpWindow(){
        handler = new Handler(){
            @Override
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                case 0:
                    index ++;
                    if(index > 1){
                        finishShowMark = true;
                        index = 0;
                    }else{
                        OverlayItem item = items.get(0);
                        GeoPoint point = item.getPoint();
                        int deepZoom = getMapView().getDeepZoom();
                        int mapOffsetX = getMapView().getMapOffsetX();
                        int mapOffsetY = getMapView().getMapOffsetY();

                        int mapHeight = getMapView().getMapHeight();
                        int mapWidth = getMapView().getMapWidth();
                        
                        int mapX = point.getMapX(mapWidth);
                        int mapY = point.getMapY(mapHeight);

                        int x = (mapX + mapOffsetX);
                        int y = (mapY + mapOffsetY);
                        Log.i("handle-->",Integer.toString(x));
                        String text = item.getTitle();
                        Context context = getMapView().getContext();
                        ItemPopupWindow popupWindow = new ItemPopupWindow(context,item,text);
                        popupWindow.showItemPopupWindow(getMapView(), x, y);
                    }
                    break;
                default:
                    break;
                }
            }  
        };
    }    
}
