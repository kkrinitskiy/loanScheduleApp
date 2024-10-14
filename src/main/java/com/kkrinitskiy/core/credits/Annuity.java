package com.kkrinitskiy.core.credits;

import com.kkrinitskiy.core.DateAdjuster;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;


public class Annuity implements Credit {
    private StringBuilder shedule;
    private DateAdjuster dateAdjuster;

    /**
     * Реализация интерфейса Credit. Позволяет составить график платежей при аннуитетных платежах по кредиту
     */
    public Annuity(DateAdjuster dateAdjuster) {
        this.dateAdjuster = dateAdjuster;
        shedule = new StringBuilder();
    }

    /**
     * Метод производит расчет аннуитетного платежа и заполняет график, хранящийся в StringBuilder sb
     */
    @Override
    public void createPaymentSchedule(BigDecimal interestRate, int loanPeriodInMonths, BigDecimal loanAmount, LocalDate loanIssueDate) {
        BigDecimal monthlyRate = interestRate.divide(MONTH_IN_YEAR, 10, RoundingMode.HALF_EVEN);
//        вынесено общее выражение в отдельную переменную
        BigDecimal j = (BigDecimal.ONE.add(monthlyRate)).pow(loanPeriodInMonths);
        BigDecimal annuityCoefficient = monthlyRate.multiply(j).divide((j.subtract(BigDecimal.ONE)), 10, RoundingMode.HALF_EVEN);
        BigDecimal payment = loanAmount.multiply(annuityCoefficient);
        BigDecimal remaining = loanAmount;

        shedule.append(String.format(TABLE_HEADER_FORMAT, "Дата", "Платеж", "Проценты", "Основной долг", "Остаток"));

        for (int month = 1; month <= loanPeriodInMonths; month++) {
            LocalDate paymentDate = checkDate(dateAdjuster, loanIssueDate.plusMonths(month));

            BigDecimal interestPayment = remaining.multiply(monthlyRate).setScale(2, RoundingMode.HALF_EVEN);
            BigDecimal principalPayment;

            if (month == loanPeriodInMonths) {
                principalPayment = remaining;
                payment = interestPayment.add(principalPayment);
            } else {
                principalPayment = payment.subtract(interestPayment).setScale(2, RoundingMode.HALF_EVEN);
            }

            remaining = remaining.subtract(principalPayment).setScale(2, RoundingMode.HALF_EVEN);

            fillRow(paymentDate,
                    payment,
                    interestPayment,
                    principalPayment,
                    remaining);
        }

        BigDecimal totalPayments = payment.multiply(BigDecimal.valueOf(loanPeriodInMonths)).setScale(2, RoundingMode.HALF_EVEN);
        System.out.println("Общая сумма выплат: " + totalPayments + " рублей");
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


    @Override
    public String getSchedule() {
        return shedule.toString();
    }

    @Override
    public LocalDate checkDate(DateAdjuster dateAdjuster, LocalDate date) {
        return dateAdjuster.adjustDate(date);
    }
}
