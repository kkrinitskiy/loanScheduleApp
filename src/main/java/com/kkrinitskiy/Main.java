package com.kkrinitskiy;

import com.kkrinitskiy.core.DateAdjuster;
import com.kkrinitskiy.core.Loan;
import com.kkrinitskiy.core.Util;

import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            try {

                Loan loan = new Loan(new DateAdjuster(Util.getHolidays()));

                System.out.println("Сумма кредита:");
                loan.setLoanAmount(prompt());
                System.out.println("Срок кредита в месяцах:");
                loan.setLoanPeriodInMonths(prompt());
                System.out.println("Процентная ставка:");
                loan.setInterestRate(prompt());
                System.out.println("Год в формате YYYY-MM-DD (пример: 2007-12-03):");
                loan.setLoanIssueDate(prompt());
                System.out.println("Введите латинскую букву \n\"a\" если график платежей аннуитетный \"d\" если график платежей дифференцированный");
                loan.setAnnuityOrDifferentiated(prompt());

            } catch (DateTimeParseException e) {
                System.out.println("Неверно введена дата: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Неверно введено значение: " + e.getMessage());
            }catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }


        }
    }

    private static String prompt() {
        return scanner.nextLine();
    }
}