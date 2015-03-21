package com.micromap.core.map.model;

import com.micromap.model.Building;
import com.micromap.model.Position;

import java.io.Serializable;


/**
 * 建筑的抽象数据类型，包括建筑的基本信息、建筑的位置
 * 用来将建筑在地图上显示
 *
 * @author Pingfu
 */
public class BuildingMark implements Serializable{
    private Building building;
    private Position position;
    
    public BuildingMark(){};
    public BuildingMark(Building building,Position position){
    	this.building = building;
    	this.position = position;
    }
    
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public Building getBuilding() {
		return building;
	}
	
	public void setBuilding(Building building) {
		this.building = building;
	}
}
