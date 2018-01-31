package com.wiiee.core.domain.valuetype;

import java.time.LocalDateTime;

public class DateRange {
    public LocalDateTime StartDate;
    public LocalDateTime EndDate;

    public DateRange(LocalDateTime startDate, LocalDateTime endDate) {
        StartDate = startDate;
        EndDate = endDate;
    }
}