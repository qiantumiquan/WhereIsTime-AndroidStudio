package com.qiantu.whereistime.service;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.qiantu.whereistime.model.Day;
import com.qiantu.whereistime.util.DBUtilx;

/**
 * Created by miquan on 2015/3/9.
 */
public class DataService {

    /**
     * 获取某一天的数据。
     * @param index 以今天为0，昨天为1，前天为2，以此类推。
     * @return
     */
    public static Day getDay(int index) {
        //如果要查询的那一天不存在
        if (index + 1 > getDaysCount()) return null;

        Day day = null;
        try {
            day = DBUtilx.getInstance().findFirst(
                    Selector.from(Day.class).orderBy("id", true)
                            .offset(index).limit(index + 1));
        } catch (DbException e) {
            e.printStackTrace();
        }
        return day;
    }

    public static long getDaysCount() {
        try {
            return DBUtilx.getInstance().count(Day.class);
        } catch (DbException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
