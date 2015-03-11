package com.micromap.core.map.model.dao;

import com.micromap.core.map.model.BuildingMark;
import com.micromap.core.map.model.FacilityMark;
import com.micromap.core.map.model.ItemMark;

import java.util.ArrayList;
import java.util.List;

public class ItemMarkDao {
    
	public static final int BUILDING_DATA_TYPE = 0;
	public static final int FACILITY_DATA_TYPE = 1;

	/**
	 * 根据建筑列表得到建筑在地图上的标志
	 * @param buildingMarks  buildingMark的列表
	 * @return marks         该列表对应的地图图标
	 */
	public static List<ItemMark> getMarksByBuildingMarks(List<BuildingMark> buildingMarks){
		List<ItemMark> marks = new ArrayList<ItemMark>();
		for(int i = 0; i < buildingMarks.size(); i++){
			BuildingMark buildingMark = buildingMarks.get(i);
            int id = buildingMark.getBuilding().getId();
			String name = buildingMark.getBuilding().getName();
			String description = buildingMark.getBuilding().getDescription();
			ItemMark mark = new ItemMark(id, name, description, BUILDING_DATA_TYPE, buildingMark.getPosition());
			marks.add(mark);
		}
		return marks;
	}
	
	/**
	 * 根据FacilityMark生成在地图上的标志
     *
     * @param facilityMarks 部门列表
     * @return marks
	 */
	public static List<ItemMark> getMarksByFacilityMarks(List<FacilityMark> facilityMarks){
		List<ItemMark> marks = new ArrayList<ItemMark>();
		for(int i = 0; i < facilityMarks.size(); i++){
			FacilityMark facilityMark = facilityMarks.get(i);
			BuildingMark buildingMark = facilityMark.getBuildingMark();
            int id = buildingMark.getBuilding().getId();
			String name = facilityMark.getFacility().getName();
			String description = facilityMark.getFacility().getDescription();
			int type = FACILITY_DATA_TYPE;
			ItemMark mark = new ItemMark(id, name, description, type, buildingMark.getPosition());
			marks.add(mark);
		}
		return marks;
	}
}
