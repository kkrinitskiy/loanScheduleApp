package com.kkrinitskiy.core.credits;

import com.kkrinitskiy.core.DateAdjuster;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;


public class Differentiated implements Credit {
    private StringBuilder shedule;
    private DateAdjuster dateAdjuster;

    /**
     * Реализация интерфейса Credit. Позволяет составить график платежей при дифференцированных платежах по кредиту
     */
    public Differentiated(DateAdjuster dateAdjuster) {
        this.dateAdjuster = dateAdjuster;
        shedule = new StringBuilder();
    }

    @Override
    public void createPaymentSchedule(BigDecimal interestRate, int loanPeriodInMonths, BigDecimal loanAmount, LocalDate loanIssueDate) {
        BigDecimal remaining = loanAmount;
        BigDecimal principal = loanAmount.divide(BigDecimal.valueOf(loanPeriodInMonths), 10, RoundingMode.HALF_EVEN);


        shedule.append(String.format(TABLE_HEADER_FORMAT, "Дата", "Платеж", "Проценты", "Основной долг", "Остаток"));
        for (int month = 1; month <= loanPeriodInMonths; month++) {


            BigDecimal loanBodyPayment = calculateLoanBodyPayment(month, remaining, loanIssueDate, interestRate);
            BigDecimal fullPayment = loanBodyPayment.add(principal);
            LocalDate paymentDate = checkDate(dateAdjuster, loanIssueDate.plusMonths(month));

            remaining = remaining.subtract(principal);


            fillRow(paymentDate, fullPayment, loanBodyPayment, principal, remaining);

        }

    }

    private void fillRow(LocalDate paymentDate, BigDecimal fullPayment, BigDecimal loanBodyPayment, BigDecimal principal, BigDecimal remaining) {
        shedule.append(String.format(TABLE_ROW_FORMAT,
                paymentDate.format(formatter),
                fullPayment,
                loanBodyPayment,
                principal,
                remaining
        ));
    }


    private BigDecimal calculateLoanBodyPayment(int month, BigDecimal remaining, LocalDate loanIssueDate, BigDecimal interestRate) {
        int daysInMonth = YearMonth.of(loanIssueDate.plusMonths(month).getYear(), loanIssueDate.plusMonths(month).getMonth()).lengthOfMonth();
        int daysInYear = YearMonth.of(loanIssueDate.plusMonths(month).getYear(), loanIssueDate.plusMonths(month).getMonth()).lengthOfYear();

        return (remaining
                .multiply(interestRate)
                .multiply(BigDecimal.valueOf(daysInMonth)))
                .divide(BigDecimal.valueOf(daysInYear), 10, RoundingMode.HALF_EVEN);
    }

    @Override
    public String getSchedule() {
        return shedule.toString();
    }

    @Override
    public LocalDate checkDate(DateAdjuster dateAdjuster, LocalDate date) {
        return dateAdjuster.adjustDate(date);
    }
}
