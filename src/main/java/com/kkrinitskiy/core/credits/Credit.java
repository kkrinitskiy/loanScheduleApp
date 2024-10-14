package com.kkrinitskiy.core.credits;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public interface Credit {
    static final BigDecimal MONTH_IN_YEAR = BigDecimal.valueOf(12);
    static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Метод производит расчеты платежей и заполнение таблицы
     */
    void createPaymentSchedule(BigDecimal interestRate, int loanPeriodInMonths, BigDecimal loanAmount, LocalDate loanIssueDate);

    /**
     * Метод возвращает таблицу в виде строки с графиком платежей
     */
    String getSchedule();

    /**
     * Метод для проверки даты внесения платежа - если дата выпадает на выходной или праздник,
     * то дата платежа сдвигается на следующий ближайший рабочий день
     */
    default LocalDate checkDate(LocalDate date) {
        while (date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                (date.getMonth() == Month.JANUARY && date.getDayOfMonth() <= 8) ||
                date.getMonth() == Month.FEBRUARY && date.getDayOfMonth() == 23 ||
                date.getMonth() == Month.MARCH && date.getDayOfMonth() == 8 ||
                date.getMonth() == Month.MAY && date.getDayOfMonth() == 1 ||
                date.getMonth() == Month.MARCH && date.getDayOfMonth() == 9 ||
                date.getMonth() == Month.JUNE && date.getDayOfMonth() == 12 ||
                date.getMonth() == Month.NOVEMBER && date.getDayOfMonth() == 4
        ) {
            date = date.plusDays(1);
        }
        return date;
    }
}
