package com.micromap.model.bean;

import java.io.Serializable;

public class StudentCourses implements Serializable{
private String student_id;//学生id
private String  course_id;//课程id
public String getStudent_id() {
	return student_id;
}
public void setStudent_id(String student_id) {
	this.student_id = student_id;
}
public String getCourse_id() {
	return course_id;
}
public void setCourse_id(String course_id) {
	this.course_id = course_id;
}
@Override
public String toString() {
	return "StudentCourses [student_id=" + student_id + ", course_id="
			+ course_id + "]";
}

}
