package com.wiiee.core.platform.util;

import com.wiiee.core.platform.util.date.DateRange;
import io.searchbox.core.search.aggregation.DateRangeAggregation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wang.na on 2017/1/10.
 */
public abstract class DateUtil {
    private static final Logger _logger = LoggerFactory.getLogger(DateUtil.class);
    private static final int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;

    public static Calendar toCalendar(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            return cal;
        }
    }

    public static Calendar getCalendar(String date, String format){
        Calendar cal = Calendar.getInstance();

        try {
            Date time = new SimpleDateFormat(format).parse(date);
            cal.setTime(time);
        } catch (ParseException e) {
            _logger.error(e.getMessage());
        }

        return cal;
    }

    public static Date toDate(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.getTime();
    }

    public static Date getNextDay(Date date) {
        long millis = date.getTime() - MILLIS_IN_DAY;
        return new Date(millis);
    }

    public static Date getPreviousDay(Date date) {
        long millis = date.getTime() + MILLIS_IN_DAY;
        return new Date(millis);
    }

    public static Calendar getMinDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(0));
        return calendar;
    }

    public static boolean isIntersect(LocalDateTime startDate1, LocalDateTime endDate1,
                                      LocalDateTime startDate2, LocalDateTime endDate2){
        return !(endDate1.isBefore(startDate2) || endDate2.isBefore(startDate1));
    }
}
