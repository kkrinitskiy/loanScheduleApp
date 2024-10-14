package com.kkrinitskiy.core.credits;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Annuity implements Credit {
    private StringBuilder sb;

    public Annuity() {
        sb = new StringBuilder();
    }

    @Override
    public void createPaymentSchedule(BigDecimal interestRate, int loanPeriodInMonths, BigDecimal loanAmount, LocalDate loanIssueDate) {
        BigDecimal monthlyRate = interestRate.divide(MONTH_IN_YEAR, 10, RoundingMode.HALF_EVEN);
//        вынесено общее выражение в отдельную переменную
        BigDecimal j = (BigDecimal.ONE.add(monthlyRate)).pow(loanPeriodInMonths);
        BigDecimal annuityCoefficient = monthlyRate.multiply(j).divide((j.subtract(BigDecimal.ONE)), 10, RoundingMode.HALF_EVEN);
        BigDecimal payment = loanAmount.multiply(annuityCoefficient);
        BigDecimal remaining = loanAmount;

        sb.append(String.format("%-15s %-15s %-15s %-15s %-15s%n", "Дата", "Платеж", "Проценты", "Основной долг", "Остаток"));

        for (int month = 1; month <= loanPeriodInMonths; month++) {
            LocalDate paymentDate = checkDate(loanIssueDate.plusMonths(month));

            BigDecimal interestPayment = remaining.multiply(monthlyRate).setScale(2, RoundingMode.HALF_EVEN);
            BigDecimal principalPayment;

            if (month == loanPeriodInMonths) {
                principalPayment = remaining;
                payment = interestPayment.add(principalPayment);
            } else {
                principalPayment = payment.subtract(interestPayment).setScale(2, RoundingMode.HALF_EVEN);
            }

            remaining = remaining.subtract(principalPayment).setScale(2, RoundingMode.HALF_EVEN);

            sb.append(String.format("%-15s %-15.2f %-15.2f %-15.2f %-15.2f%n",
                    paymentDate.format(formatter),
                    payment,
                    interestPayment,
                    principalPayment,
                    remaining));
        }

        BigDecimal totalPayments = payment.multiply(BigDecimal.valueOf(loanPeriodInMonths)).setScale(2, RoundingMode.HALF_EVEN);
        System.out.println("Общая сумма выплат: " + totalPayments + " рублей");
    }

    @Override
    public String getSchedule() {
        return sb.toString();
    }
}
