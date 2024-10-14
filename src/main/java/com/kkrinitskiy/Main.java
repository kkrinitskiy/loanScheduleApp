package com.kkrinitskiy;

import com.kkrinitskiy.core.Loan;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {

                Loan loan = new Loan();

                System.out.println("Сумма кредита:");
                loan.setLoanAmount(scanner.nextBigDecimal());
                System.out.println("Срок кредита в месяцах:");
                loan.setLoanPeriodInMonths(scanner.nextInt());
                System.out.println("Процентная ставка:");
                loan.setInterestRate(scanner.nextBigDecimal());
                System.out.println("Год в формате YYYY-MM-DD (пример: 2007-12-03):");
                String date = scanner.next();
                loan.setLoanIssueDate(LocalDate.parse(date));
                scanner.nextLine();
                System.out.println("Введите латинскую букву \n\"a\" если график платежей аннуитетный \"d\" если график платежей дифференцированный");
                loan.setAnnuityOrDifferentiated(scanner.nextLine());

            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                scanner.nextLine();
            }


        }
    }
}