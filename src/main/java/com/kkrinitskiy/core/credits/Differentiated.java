package com.kkrinitskiy.core.credits;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;

public class Differentiated implements Credit {

    private StringBuilder sb;

    public Differentiated() {
        sb = new StringBuilder();
    }

    @Override
    public void createPaymentSchedule(BigDecimal interestRate, int loanPeriodInMonths, BigDecimal loanAmount, LocalDate loanIssueDate) {
        BigDecimal remaining = loanAmount;
        BigDecimal principal = loanAmount.divide(BigDecimal.valueOf(loanPeriodInMonths), 10, RoundingMode.HALF_EVEN);


        sb.append(String.format("%-15s %-15s %-15s %-15s %-15s%n", "Дата", "Платеж", "Проценты", "Основной долг", "Остаток"));
        for (int month = 1; month <= loanPeriodInMonths; month++) {


            BigDecimal loanBodyPayment = calculateLoanBodyPayment(month, remaining, loanIssueDate, interestRate);
            BigDecimal fullPayment = loanBodyPayment.add(principal);
            LocalDate paymentDate = checkDate(loanIssueDate.plusMonths(month));

            remaining = remaining.subtract(principal);


            sb.append(String.format("%-15s %-15.2f %-15.2f %-15.2f %-15.2f%n",
                    paymentDate.format(formatter),
                    fullPayment,
                    loanBodyPayment,
                    principal,
                    remaining
            ));

        }

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
        return sb.toString();
    }
}
