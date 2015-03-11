package com.micromap.model.bean;

import java.io.Serializable;

public class Student implements Serializable{
private String student_id;//学生Id
private String student_name;//学生名字
private String password;//密码
public String getStudent_id() {
	return student_id;
}
public void setStudent_id(String student_id) {
	this.student_id = student_id;
}
public String getStudent_name() {
	return student_name;
}
public void setStudent_name(String student_name) {
	this.student_name = student_name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
@Override
public String toString() {
	return "Student [student_id=" + student_id + ", student_name="
			+ student_name + ", password=" + password + "]";
}

}
