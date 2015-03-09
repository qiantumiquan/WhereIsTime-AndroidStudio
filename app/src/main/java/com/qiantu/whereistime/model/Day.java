package com.qiantu.whereistime.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Finder;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.List;

@Table(name="day")
public class Day implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	/**
	 * 日期,格式为2014-03-27
	 */
	@Column(column="date")
	private String date;
	@Finder(targetColumn="day_id", valueColumn="id")
	private List<AppInfo> appInfos;
	
	public Day() {
		super();
	}
	public Day(int id, String date, List<AppInfo> appInfos) {
		super();
		this.id = id;
		this.date = date;
		this.appInfos = appInfos;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<AppInfo> getAppInfos() {
		return appInfos;
	}
	public void setAppInfos(List<AppInfo> appInfos) {
		this.appInfos = appInfos;
	}
}
