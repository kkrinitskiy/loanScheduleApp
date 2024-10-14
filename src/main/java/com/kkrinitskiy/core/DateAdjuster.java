package com.kkrinitskiy.core;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Set;

public class DateAdjuster {
    private Set<MonthDay> holidays;

    public DateAdjuster(Set<MonthDay> holidays) {
        if (holidays == null) {
            throw new IllegalArgumentException("Holidays не может быть равно null");
        }
        this.holidays = holidays;
    }

    public LocalDate adjustDate(LocalDate date) {
        while (isWeekend(date) || isHoliday(date)) {
            date = date.plusDays(1);
        }
        return date;
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private boolean isHoliday(LocalDate date) {
        return holidays.contains(MonthDay.from(date));
    }
}
