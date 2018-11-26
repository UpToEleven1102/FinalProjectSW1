/*
* The A Team:
*
* Huyen Vu
* Zachary Simpson
* Isaac Simpson
* Wenhao Ge
* */


package managers;

import entities.ItemEntity;
import interfaces.BankInterface;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CashierManager {
    private static Scanner scanner = new Scanner(System.in);

    private static int checkDebitCard(String cardNumber) {
        Random random = new Random();
        return random.nextInt(2);
    }


    public static String payByCash(List<ItemEntity> cart, double amount) {
        System.out.println("Enter cash....");
        scanner.nextLine();
        return "8899";
    }

    public static String payByCard(List<ItemEntity> cart, double amount) {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.nextLine();
        int pin = 0;

        if (checkDebitCard(cardNumber) == 1) {
            System.out.print("Enter pin number: ");
            pin = Integer.parseInt(scanner.nextLine());
        }

        String authorizeNum = BankInterface.authorizeCardInfo(cardNumber, pin);
        if(authorizeNum!=null){
            return authorizeNum;
        }
        return null;
    }
}
