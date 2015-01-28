package com.micromap.core.map.model.dao;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.micromap.core.map.model.BuildingMark;
import com.micromap.core.map.model.FacilityMark;
import com.micromap.model.Facility;
import com.micromap.model.dao.FacilityDao;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取FacilityMark信息
 *
 * @author Pingfu
 */
public class FacilityMarkDao {
    private SQLiteDatabase database;

    public FacilityMarkDao(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * 根据Facility的id查找Facility
     *
     * @param id 待查的Facility的id
     * @return facilityMark 一个Facility对象
     */
    public FacilityMark getFacilityMarkById(int id) {
        FacilityMark facilityMark = null;
        Facility facility = new FacilityDao(database).getFacilityById(id);
        int b_id = 0;
        BuildingMark buildingMark = new BuildingMarkDao(database).getBuildingMarkById(b_id);

        facilityMark = new FacilityMark(facility, buildingMark);
        return facilityMark;
    }

    /**
     * 根据Facility的name查找Facility
     *
     * @param name 待查的Facility的name
     * @return facilityMarks 与name相似的Facility对象列表
     */
    public List<FacilityMark> getFacilityMarkByName(String name) {
        List<FacilityMark> facilityMarks = new ArrayList<FacilityMark>();
        List<Facility> facilities = new FacilityDao(database).getFacilityByName(name);
        for (int i = 0; i < facilities.size(); i++) {
            int b_id = facilities.get(i).getBuildingId();
            BuildingMark buildingMark = new BuildingMarkDao(database).getBuildingMarkById(b_id);
            FacilityMark facilityMark = new FacilityMark(facilities.get(i), buildingMark);
            facilityMarks.add(facilityMark);
        }
        return facilityMarks;
    }

    /**
     * 根据Facility的type查找Facility
     *
     * @param type 待查的Facility的type
     * @return facilityMarks 与name相似的Facility对象列表
     */
    public List<FacilityMark> getFacilityMarkByType(int type) {
        List<FacilityMark> facilityMarks = new ArrayList<FacilityMark>();
        List<Facility> facilities = new FacilityDao(database).getFacilityByType(type);

        for (int i = 0; i < facilities.size(); i++) {
            int b_id = facilities.get(i).getBuildingId();
            BuildingMark buildingMark = new BuildingMarkDao(database).getBuildingMarkById(b_id);
            FacilityMark facilityMark = new FacilityMark(facilities.get(i), buildingMark);
            facilityMarks.add(facilityMark);
        }
        return facilityMarks;
    }
}