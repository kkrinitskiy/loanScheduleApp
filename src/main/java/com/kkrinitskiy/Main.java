package com.kkrinitskiy;

import com.kkrinitskiy.core.Loan;

import java.math.BigDecimal;
import java.time.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean test = true;
        while (test){
            try {

                Loan loan = new Loan();

//                System.out.println("Сумма кредита:");
//                loan.setAmount(scanner.nextBigDecimal());
//                System.out.println("Срок кредита в месяцах:");
//                loan.setTerm(scanner.nextInt());
//                System.out.println("Процентная ставка:");
//                loan.setInterestRate(scanner.nextBigDecimal());
//                System.out.println("Год в формате YYYY-MM-DD (пример: 2007-12-03):");
//                String date = scanner.next();
//                loan.setIssueDate(LocalDate.parse(date));
//                scanner.nextLine();
//                System.out.println("Введите латинскую букву \n\"a\" если график платежей аннуитетный \"d\" если график платежей дифференцированный");
//                loan.setAnnuityOrDifferentiated(scanner.nextLine());

                loan.setLoanAmount(BigDecimal.valueOf(2000000));
                loan.setLoanPeriodInMonths(12);
                loan.setInterestRate(BigDecimal.valueOf(15));
                loan.setLoanIssueDate(LocalDate.parse("2000-12-12"));
                loan.setAnnuityOrDifferentiated("d");
                loan.printSchedule();

                test = false;
            }catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                scanner.nextLine();
            }


        }
    }
}