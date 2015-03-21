package com.micromap.core.map.model;

import com.micromap.model.Building;
import com.micromap.model.Facility;
import com.micromap.model.Position;

import java.io.Serializable;

public class ItemMark implements Serializable {
    /**  **/
    private static final long serialVersionUID = 1L;
    private int id;
    private int fid;
    private String name;
    private int type;
    private String description;
    private Position position;
    private boolean isChosen = false; // 给Item是否被选择,如果被选择，则在地图上显示地图信息

    public ItemMark() {
    }

    public ItemMark(int id, String name, String description, int type, Position position) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void chooseThisItem() {
        this.isChosen = true;
    }

    public boolean isChosen() {
        return this.isChosen;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setChosen(boolean isChosen) {
        this.isChosen = isChosen;
    }

    public Building getBuilding(){
        Building building = new Building();
        building.setId(id);
        building.setName(name);
        building.setDescription(description);
        building.setAlias("");
        building.setNumber(0);
        return building;
    }

    public Facility getFacility(){
        Facility facility = new Facility();
        facility.setId(fid);
        facility.setName(name);
        facility.setDescription(description);
        facility.setType(type);
        facility.setBuildingId(id);

        return facility;
    }
}
