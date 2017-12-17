package com.platform.log;

import com.platform.data.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wiiee on 9/10/2017.
 */
@Component
public class LogReceiver {
    @Autowired
    private IHistoryService historyService;

    //处理日志
    public void receive(LogItem item) {
        historyService.process(item);
    }
}
