package com.platform.data;

import com.platform.constant.HistoryType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wiiee on 9/1/2017.
 */
public class HistoryInfo implements Serializable {
    private String data;
    private String userId;
    private Date date;

    private HistoryType type;

    public HistoryInfo(String data, String userId, Date date, HistoryType type) {
        this.data = data;
        this.userId = userId;
        this.date = date;
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HistoryType getType() {
        return type;
    }

    public void setType(HistoryType type) {
        this.type = type;
    }
}
