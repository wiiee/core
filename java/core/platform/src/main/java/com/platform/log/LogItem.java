package com.platform.log;

import com.platform.data.HistoryInfo;

import java.io.Serializable;

/**
 * Created by wiiee on 9/10/2017.
 */
public class LogItem implements Serializable {
    private String id;
    private HistoryInfo historyInfo;

    public LogItem(String id, HistoryInfo historyInfo) {
        this.id = id;
        this.historyInfo = historyInfo;
    }

    public String getId() {
        return id;
    }

    public HistoryInfo getHistoryInfo() {
        return historyInfo;
    }
}
