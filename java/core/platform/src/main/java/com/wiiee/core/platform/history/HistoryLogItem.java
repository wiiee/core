package com.wiiee.core.platform.history;

import java.io.Serializable;

/**
 * Created by wiiee on 9/10/2017.
 */
public class HistoryLogItem implements Serializable {
    private String id;
    private HistoryInfo historyInfo;

    public HistoryLogItem(String id, HistoryInfo historyInfo) {
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
