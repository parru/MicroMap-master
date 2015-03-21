package com.micromap.model.bean;

import java.io.Serializable;

public class Course implements Serializable{
	private int course_id;//课程Id
	private String course_name;//课程名字
	private String schedule_id;//课程安排名字
	private String place;//上课地点
	private String teacher;//上课老师
	private String course_num;//课程号
	private int evaluate_good;//好評數
	private int evaluate_bad;//差評數
	private int evaluate_own;//自己的评价
	private String evaluate;//评价
	public int getCourse_id() {
		return course_id;
	}
	public void setCourse_id(int course_id) {
		this.course_id = course_id;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getSchedule_id() {
		return schedule_id;
	}
	public void setSchedule_id(String schedule_id) {
		this.schedule_id = schedule_id;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getCourse_num() {
		return course_num;
	}
	public void setCourse_num(String course_num) {
		this.course_num = course_num;
	}
	
	public int getEvaluate_good() {
		return evaluate_good;
	}
	public void setEvaluate_good(int evaluate_good) {
		this.evaluate_good = evaluate_good;
	}
	public int getEvaluate_bad() {
		return evaluate_bad;
	}
	public void setEvaluate_bad(int evaluate_bad) {
		this.evaluate_bad = evaluate_bad;
	}
	public int getEvaluate_own() {
		return evaluate_own;
	}
	public void setEvaluate_own(int evaluate_own) {
		this.evaluate_own = evaluate_own;
	}
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	@Override
	public String toString() {
		return "Course [course_id=" + course_id + ", course_name="
				+ course_name + ", schedule_id=" + schedule_id + ", place="
				+ place + ", teacher=" + teacher + ", course_num=" + course_num
				+ ", evaluate_good=" + evaluate_good + ", evaluate_bad="
				+ evaluate_bad + ", evaluate_own=" + evaluate_own
				+ ", evaluate=" + evaluate + "]";
	}
	
	
}
