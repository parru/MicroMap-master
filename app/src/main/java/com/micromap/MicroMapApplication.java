package com.micromap;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.micromap.core.map.model.ItemMark;
import com.micromap.core.map.model.RoadMark;
import com.micromap.model.Position;
import com.micromap.model.RoadMap;

import java.util.ArrayList;
import java.util.List;


/**
 * 全局类型，单例模式实现
 * 记录地图的状态、缩放等级、坐标等基本信息
 * @author Pingfu
 */
public class MicroMapApplication extends Application {
    /**
     * MapView的状态
     * 分为初始状态、工作状态和结束状态
     */
    public static final int INIT_STATE = 0;
    public static final int WORKING_STATE = 1;
    public static final int END_STATE = 2;

    public static class MapConfig {
        /**
         * 地图切片大小 *
         */
        public static final int TILE_SIZE = 100;

        /**
         * 第一缩放等级的MapView的长和宽
         */
        public static final int INIT_MAP_HEIGHT = 500;
        public static final int INIT_MAP_WIDTH = 1000;

        /**
         * MapView的缩放等级限制
         */
        public static final int MIN_DEEP_ZOOM = 1;           //做小缩放等级
        public static final int MAX_DEEP_ZOOM = 3;           //最大缩放等级

        /**
         * 地图的默认图层的z-index值
         */
        public static final int ITEM_OVERLAY_Z_INDEX = 10;
        public static final int ROUTE_OVERLAY_Z_INDEX = 1;

        /**
         * 得到当前缩放等级的地图的宽
         *
         * @param deepZoom
         * @return
         */
        public static int getMapWidthByDeepZoom(int deepZoom) {
            // TODO Auto-generated method stub
            int size = (int) (INIT_MAP_WIDTH * Math.pow(2, deepZoom - 1));
            return size;
        }

        /**
         * 得到当前缩放等级的地图的高
         *
         * @param deepZoom
         * @return
         */
        public static int getMapHeightByDeepZoom(int deepZoom){
            int size = (int) (INIT_MAP_HEIGHT * Math.pow(2, deepZoom - 1));
            return size;
        }

        /**
         * 得到当前的缩放比
         *
         * @param mapHeight
         * @param deepZoom
         * @return
         */
        public static float getMapScale(int mapHeight, int deepZoom) {
            float scale = 1f;
            int size = getMapHeightByDeepZoom(deepZoom);
            scale = (float) ((float) mapHeight / (float) size);

            return scale;
        }

        /**
         * 得到当前缩放等级的切片最大编号
         *
         * @param deepZoom
         * @return
         */
        public static int getMaxTileNum(int deepZoom){
            int m = getMapHeightByDeepZoom(deepZoom) / TILE_SIZE;
            int n = getMapWidthByDeepZoom(deepZoom) / TILE_SIZE;

            return m * n;
        }

        /**
         * 根据当前的高度获取缩放等级
         *
         * @param mapHeight
         * @return
         */
        public static int getDeepZoomByMapHeight(int mapHeight) {
            int deepZoom = 1;
            if (mapHeight >= 500 && mapHeight < 1000) {
                deepZoom = 1;
            }
            if (mapHeight >= 1000 && mapHeight < 2000) {
                deepZoom = 2;
            }
            if (mapHeight >= 2000) {
                deepZoom = 3;
            }

            return deepZoom;
        }

        /**
         * 根据地图的缩放等级，得到地图的高
         *
         * @param width     地图当前的缩放等级
         * @return height   当前地图的高
         */
        public static int getMapHeightByWidth(int width) {
            return width / 2;
        }

        /**
         * 根据地图的缩放等级，得到地图的高
         *
         * @param height     地图当前的缩放等级
         * @return width   当前地图的高
         */
        public static int getMapWidthByHeight(int height){
            return height * 2;
        }
    }

    private int mapState;         //记录当前应用的状态

    public List<RoadMark> searchedRoadMarks = new ArrayList<RoadMark>();
    public List<ItemMark> itemMarks = new ArrayList<ItemMark>();  //地图上的标记点
    public List<Position> positions = new ArrayList<Position>();  //地图上的道路
    public String path[];
    public RoadMap roadMap;

    public void setMapState(int state) {
        this.mapState = state;
    }

    public int getMapState() {
        return this.mapState;
    }

    public void initRoadMap(SQLiteDatabase database) {
        roadMap = new RoadMap(database);
        Thread thread = new Thread(roadMap);
        thread.start();
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mapState = INIT_STATE;
    }
}