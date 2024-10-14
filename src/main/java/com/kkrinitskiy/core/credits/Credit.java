package com.kkrinitskiy.core.credits;

import com.kkrinitskiy.core.DateAdjuster;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface Credit {
    BigDecimal MONTH_IN_YEAR = BigDecimal.valueOf(12);
    String TABLE_HEADER_FORMAT = "%-15s %-15s %-15s %-15s %-15s%n";
    String TABLE_ROW_FORMAT = "%-15s %-15.2f %-15.2f %-15.2f %-15.2f%n";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Метод производит расчеты платежей и заполнение таблицы
     */
    void createPaymentSchedule(BigDecimal interestRate, int loanPeriodInMonths, BigDecimal loanAmount, LocalDate loanIssueDate);


    String getSchedule();

    /**
     * Метод для проверки даты внесения платежа - если дата выпадает на выходной или праздник,
     * то дата платежа сдвигается на следующий ближайший рабочий день
     */
    LocalDate checkDate(DateAdjuster dateAdjuster, LocalDate date);
}
