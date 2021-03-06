package com.micromap.model;

import android.database.sqlite.SQLiteDatabase;

import com.micromap.model.dao.BuildingDao;
import com.micromap.model.dao.BuildingPositionDao;
import com.micromap.model.dao.CategoryDao;
import com.micromap.model.dao.FacilityDao;
import com.micromap.model.dao.PositionDao;
import com.micromap.model.dao.RoadDao;

public class TBFactory {
	
	/**
	 * 
	 * @param tableName
	 * @param database
	 * @return
	 */
    public static TBManager createTBManager(String tableName, SQLiteDatabase database){
    	TBManager tbManager = null;
    	if(tableName.equalsIgnoreCase("building")){
    		//建筑表(building)
    		tbManager = new BuildingDao(database);
    		
    	}else if(tableName.equalsIgnoreCase("buildingfacilities")){
    		//建筑和部门的对应关系表(buildingFacilities)
    		//tbManager = new BuildingFacilityDao(database);
    		
    	}else if(tableName.equalsIgnoreCase("buildingpositions")){
    		//建筑和坐标点的对应关系表
    		tbManager = new BuildingPositionDao(database);
    		
    	}else if(tableName.equalsIgnoreCase("facility")){
    		//部门表
    		tbManager = new FacilityDao(database);
    		
    	}else if(tableName.equalsIgnoreCase("category")){
    		//分类表
    		tbManager = new CategoryDao(database);
    		
    	}else if(tableName.equalsIgnoreCase("position")){
    		//坐标点表
    		tbManager = new PositionDao(database);
    	}else if(tableName.equalsIgnoreCase("road")){
    		//道路表
    		tbManager = new RoadDao(database);
    	}else if(tableName.equalsIgnoreCase("roadPosition")){
    		//道路和坐标点的对应关系表
    		tbManager = new RoadDao(database);
    	}else{
    		tbManager = null;
    	}
    	return tbManager;
    }
}
