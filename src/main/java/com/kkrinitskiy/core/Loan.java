package com.kkrinitskiy.core;

import com.kkrinitskiy.core.credits.Annuity;
import com.kkrinitskiy.core.credits.Credit;
import com.kkrinitskiy.core.credits.Differentiated;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Loan {
    private static final BigDecimal MAX_AMOUNT = BigDecimal.valueOf(5_000_000);
    private static final BigDecimal MIN_AMOUNT = BigDecimal.valueOf(30_000);
    private static final BigDecimal MONTH_IN_YEAR = BigDecimal.valueOf(12);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private BigDecimal loanAmount;
    private int loanPeriodInMonths;
    private BigDecimal interestRate;
    private LocalDate loanIssueDate;
    private String annuityOrDifferentiated; // = "a"/"d"

    private Credit credit;
    private String schedule;
    public Loan() {
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        if (loanAmount.compareTo(MIN_AMOUNT) < 0 || loanAmount.compareTo(MAX_AMOUNT) > 0) {
            throw new IllegalArgumentException("Кредит может быть от 30000 до 5000000 руб.");
        }
        this.loanAmount = loanAmount;
    }

    public void setLoanPeriodInMonths(int loanPeriodInMonths) {
        if (loanPeriodInMonths < 1 || loanPeriodInMonths > 360) {
            throw new IllegalArgumentException("Срок кредита может быть в диапазоне от 1 до 360 месяцев");
        }
        this.loanPeriodInMonths = loanPeriodInMonths;
    }

    public void setInterestRate(BigDecimal interestRate) {
        if (interestRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Процентная ставка должна быть положительной");
        }
        this.interestRate = interestRate.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
    }

    public void setLoanIssueDate(LocalDate loanIssueDate) {
        this.loanIssueDate = loanIssueDate;
    }

    public void setAnnuityOrDifferentiated(String annuityOrDifferentiated) {
        switch (annuityOrDifferentiated.trim().toLowerCase()) {
            case "a", "d" -> {
                this.annuityOrDifferentiated = annuityOrDifferentiated;
                setCredit();
            }
            default -> throw new IllegalArgumentException("Недопустимое значение для графика платежей. " +
                    "\nМожет быть a - annuity (аннуитетный) или d - differentiated (дифференцированный).");

        }
    }

    private void setCredit() {
        switch (annuityOrDifferentiated) {
            case "a" -> credit = new Annuity();
            case "d" -> credit = new Differentiated();
        }
        credit.createPaymentSchedule(interestRate, loanPeriodInMonths, loanAmount, loanIssueDate);
    }

    public void printSchedule(){
        System.out.println(credit.getSchedule());
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public int getLoanPeriodInMonths() {
        return loanPeriodInMonths;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public LocalDate getLoanIssueDate() {
        return loanIssueDate;
    }

    public String getAnnuityOrDifferentiated() {
        return annuityOrDifferentiated;
    }

}

