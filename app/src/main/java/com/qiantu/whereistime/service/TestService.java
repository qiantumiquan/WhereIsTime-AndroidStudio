package com.qiantu.whereistime.service;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.qiantu.whereistime.model.AppInfo;
import com.qiantu.whereistime.model.Day;

/**
 * 提供测试数据
 */
public class TestService {
    private static TestService instance;

    private TestService() {}

    public static TestService getInstance() {
        if(instance != null) return instance;
        else {
            instance = new TestService();
            return instance;
        }
    }

    public void addDataTest(DbUtils db) {
        try {
            Day day = new Day(0, "2015-03-07", null);
            db.saveBindingId(day);
            AppInfo a = new AppInfo(0, "微信", 20, "com.miquan.mqnote", day);
            db.save(a);
            AppInfo b = new AppInfo(0, "qq", 20, "com.miquan.qq", day);
            db.save(b);

            day = new Day(0, "2015-03-08", null);
            db.saveBindingId(day);
            a = new AppInfo(0, "斗地主", 20, "com.miquan.mqnote", day);
            db.save(a);
            a = new AppInfo(0, "qq", 20, "com.miquan.qq", day);
            db.save(a);

            day = new Day(0, "2015-03-09", null);
            db.saveBindingId(day);
            a = new AppInfo(0, "斗地主", 20, "com.miquan.mqnote", day);
            db.save(a);
            a = new AppInfo(0, "qq", 20, "com.miquan.qq", day);
            db.save(a);

            day = new Day(0, "2015-03-10", null);
            db.saveBindingId(day);
            a = new AppInfo(0, "陌陌", 20, "com.miquan.mqnote", day);
            db.save(a);
            a = new AppInfo(0, "qq", 20, "com.miquan.qq", day);
            db.save(a);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
