package com.wiiee.core.platform.history;

import com.wiiee.core.platform.constant.HistoryType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by wiiee on 9/1/2017.
 */
public class HistoryInfo implements Serializable {
    public String data;
    public String userId;
    public LocalDateTime date;

    public HistoryType type;

    public HistoryInfo(String data, String userId, LocalDateTime date, HistoryType type) {
        this.data = data;
        this.userId = userId;
        this.date = date;
        this.type = type;
    }
}
