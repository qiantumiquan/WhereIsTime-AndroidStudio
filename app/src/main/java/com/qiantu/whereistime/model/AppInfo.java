package com.qiantu.whereistime.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Foreign;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

@Table(name="appinfo")
public class AppInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	@Column(column="name")
	private String name;
	/*使用时长，单位为秒*/
	@Column(column="useTime")
	private double useTime;
	/*程序包名*/
	@Column(column="pkgName")
	private String pkgName;
	@Foreign(column="day_id", foreign="id")
	private Day day;
	
	
	public AppInfo() {
		super();
	}
	public AppInfo(int id, String name, double useTime, String pkgName, Day day) {
		super();
		this.id = id;
		this.name = name;
		this.useTime = useTime;
		this.pkgName = pkgName;
		this.day = day;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getUseTime() {
		return useTime;
	}
	public void setUseTime(double useTime) {
		this.useTime = useTime;
	}
	public String getPkgName() {
		return pkgName;
	}
	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}
	public Day getDay() {
		return day;
	}
	public void setDay(Day day) {
		this.day = day;
	}
}
