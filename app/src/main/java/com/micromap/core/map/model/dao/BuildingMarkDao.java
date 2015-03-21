/*
 * 并没有
 */
package com.micromap.core.map.model.dao;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.micromap.core.map.model.BuildingMark;
import com.micromap.model.Building;
import com.micromap.model.Position;
import com.micromap.model.dao.BuildingDao;
import com.micromap.model.dao.BuildingPositionDao;

import java.util.ArrayList;
import java.util.List;

public class BuildingMarkDao {
    private SQLiteDatabase database;

    public BuildingMarkDao(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * 根据名称搜索建筑
     *
     * @param name 带查询的建筑名称
     * @return 一组BuildingMark对象
     */
    public List<BuildingMark> getBuildingMarkByName(String name) {
        List<BuildingMark> buildingMarks = new ArrayList<BuildingMark>();
        BuildingDao builidingDao = new BuildingDao(database);

        List<Building> buildings = new ArrayList<Building>();

        buildings = builidingDao.getBuildingByName(name);
        for (int i = 0; i < buildings.size(); i++) {
            BuildingPositionDao buildingPointDao = new BuildingPositionDao(database);
            int building_id = buildings.get(i).getId();

            // 查找建筑的位置，type = 1
            Position position = buildingPointDao.getBuidingPosition(building_id, 1);
            BuildingMark buildingMark = new BuildingMark();
            buildingMark.setBuilding(buildings.get(i));
            if (position != null) {
                buildingMark.setPosition(position);
            }
            buildingMarks.add(buildingMark);
        }
        return buildingMarks;
    }

    /**
     * 根据建筑编号搜索
     *
     * @param number 要查找的建筑编号
     * @return 一组BuildingMark对象
     */
    public BuildingMark getBuildingMarkByNumber(int number) {
        BuildingMark buildingMark = null;

        BuildingDao builidingDao = new BuildingDao(database);
        Building building = builidingDao.getBuildingByNumber(number);
        Position position = new BuildingPositionDao(database).getBuidingPosition(building.getId(), 1);
        buildingMark = new BuildingMark(building, position);

        return buildingMark;
    }

    /**
     * 根据建筑id搜索
     *
     * @param id 要查找的建筑id
     * @return 一个BuildingMark对象
     */
    public BuildingMark getBuildingMarkById(int id) {
        BuildingMark buildingMark = null;

        BuildingDao builidingDao = new BuildingDao(database);
        Building building = builidingDao.getBuildingById(id);
        Position position = new BuildingPositionDao(database).getBuidingPosition(building.getId(), 1);
        buildingMark = new BuildingMark(building, position);

        return buildingMark;
    }
}
