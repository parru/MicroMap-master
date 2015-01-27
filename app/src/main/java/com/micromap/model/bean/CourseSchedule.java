package com.micromap.model.bean;

import java.io.Serializable;

public class CourseSchedule implements Serializable{
private Course mCourse;
private Schedule mSchedule;
public CourseSchedule(Course mCourse, Schedule mSchedule) {
	super();
	this.mCourse = mCourse;
	this.mSchedule = mSchedule;
}
public Course getmCourse() {
	return mCourse;
}
public void setmCourse(Course mCourse) {
	this.mCourse = mCourse;
}
public Schedule getmSchedule() {
	return mSchedule;
}
public void setmSchedule(Schedule mSchedule) {
	this.mSchedule = mSchedule;
}
public String  getScheduleWeek(){
	return mSchedule.getWeek();
}
public String getScheduleStart(){
	return mSchedule.getStartclass();
}
public String getScheduleSfinish(){
	return mSchedule.getFinishclass();
}
}
