package com.kkrinitskiy.core;

import java.time.Month;
import java.time.MonthDay;
import java.util.HashSet;
import java.util.Set;


public class Util {

    public static Set<MonthDay> getHolidays() {
        Set<MonthDay> holidays = new HashSet<>();

        for (int day = 1; day <= 8; day++) {
            holidays.add(MonthDay.of(Month.JANUARY, day));
        }
        holidays.add(MonthDay.of(Month.FEBRUARY, 23));
        holidays.add(MonthDay.of(Month.MARCH, 8));
        holidays.add(MonthDay.of(Month.MARCH, 9));
        holidays.add(MonthDay.of(Month.MAY, 1));
        holidays.add(MonthDay.of(Month.JUNE, 12));
        holidays.add(MonthDay.of(Month.NOVEMBER, 4));

        return holidays;
    }

}
