package com.wiiee.core.platform.history;

import com.wiiee.core.platform.data.BaseData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wiiee on 9/1/2017.
 */
public class History extends BaseData<String> {
    private List<HistoryInfo> historyInfos;

    public History() {

    }

    public History(String id) {
        super(id);
        this.historyInfos = new ArrayList<>();
    }

    public History(String id, HistoryInfo info) {
        super(id);
        this.historyInfos = Arrays.asList(info);
    }

    public List<HistoryInfo> getHistoryInfos() {
        return historyInfos;
    }

    public void setHistoryInfos(List<HistoryInfo> historyInfos) {
        this.historyInfos = historyInfos;
    }

    public void addHistoryInfo(HistoryInfo info) {
        historyInfos.add(info);
    }
}
