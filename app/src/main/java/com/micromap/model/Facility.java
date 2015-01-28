package com.micromap.model;

import java.io.Serializable;

public class Facility implements Serializable {
    private int id;
    private int buildingId;
    private String name;
    private int type;
    private String description;
    
    public Facility(){}
    
    public Facility(int id, int buildingId, String name, int type){
    	this.id = id;
        this.buildingId = buildingId;
    	this.name = name;
    	this.type = type;
    }

    public Facility(int id, int buildingId, String name, int type, String description){
        this.id = id;
        this.buildingId = buildingId;
        this.name = name;
        this.type = type;
        this.description = description;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
