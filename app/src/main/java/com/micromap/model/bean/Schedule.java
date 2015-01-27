package com.micromap.model.bean;

import java.io.Serializable;

public class Schedule implements Serializable{
private int schedule_id;
private String week;
private String startclass;
private String finishclass;
private String course_week;
public int getSchedule_id() {
	return schedule_id;
}
public void setSchedule_id(int schedule_id) {
	this.schedule_id = schedule_id;
}
public String getWeek() {
	return week;
}
public void setWeek(String week) {
	this.week = week;
}
public String getStartclass() {
	return startclass;
}
public void setStartclass(String startclass) {
	this.startclass = startclass;
}
public String getFinishclass() {
	return finishclass;
}
public void setFinishclass(String finishclass) {
	this.finishclass = finishclass;
}

public String getCourse_week() {
	return course_week;
}
public void setCourse_week(String course_week) {
	this.course_week = course_week;
}
@Override
public String toString() {
	return "Schedule [schedule_id=" + schedule_id + ", week=" + week
			+ ", startclass=" + startclass + ", finishclass=" + finishclass
			+ ", course_week=" + course_week + "]";
}

}
