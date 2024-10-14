package com.kkrinitskiy.core;

import com.kkrinitskiy.core.credits.Annuity;
import com.kkrinitskiy.core.credits.Credit;
import com.kkrinitskiy.core.credits.Differentiated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Loan {
    private static final BigDecimal MAX_AMOUNT = BigDecimal.valueOf(5_000_000);
    private static final BigDecimal MIN_AMOUNT = BigDecimal.valueOf(30_000);

    private BigDecimal loanAmount;
    private int loanPeriodInMonths;
    private BigDecimal interestRate;
    private LocalDate loanIssueDate;
    private String annuityOrDifferentiated; // = "a"/"d"

    private Credit credit;
    private DateAdjuster dateAdjuster;

    public Loan(DateAdjuster dateAdjuster) {
        this.dateAdjuster = dateAdjuster;
    }

    public void setLoanAmount(String amount) throws NumberFormatException {
        BigDecimal loanAmount = new BigDecimal(amount);
        if (loanAmount.compareTo(MIN_AMOUNT) < 0 || loanAmount.compareTo(MAX_AMOUNT) > 0) {
            throw new IllegalArgumentException("Кредит может быть от 30000 до 5000000 руб.");
        }
        this.loanAmount = loanAmount;
    }

    public void setLoanPeriodInMonths(String period) throws NumberFormatException{
        int loanPeriodInMonths = Integer.parseInt(period);
        if (loanPeriodInMonths < 1 || loanPeriodInMonths > 360) {
            throw new IllegalArgumentException("Срок кредита может быть в диапазоне от 1 до 360 месяцев");
        }
        this.loanPeriodInMonths = loanPeriodInMonths;
    }

    public void setInterestRate(String rate) throws NumberFormatException {
        BigDecimal interestRate = new BigDecimal(rate);
        if (interestRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Процентная ставка должна быть положительной");
        }
        this.interestRate = interestRate.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
    }

    public void setLoanIssueDate(String date) throws DateTimeParseException {
        this.loanIssueDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }

    public void setAnnuityOrDifferentiated(String annuityOrDifferentiated) {
        switch (annuityOrDifferentiated.trim().toLowerCase()) {
            case "a", "d" -> {
                this.annuityOrDifferentiated = annuityOrDifferentiated;
                setCredit();
                System.out.println(credit.getSchedule());
            }
            default -> throw new IllegalArgumentException("Недопустимое значение для графика платежей. " +
                    "\nМожет быть a - annuity (аннуитетный) или d - differentiated (дифференцированный).");

        }
    }

    private void setCredit() {
        switch (annuityOrDifferentiated) {
            case "a" -> credit = new Annuity(dateAdjuster);
            case "d" -> credit = new Differentiated(dateAdjuster);
        }
        System.out.println("считаем");
        credit.createPaymentSchedule(interestRate, loanPeriodInMonths, loanAmount, loanIssueDate);
    }


}

