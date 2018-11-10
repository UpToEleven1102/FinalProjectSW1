package uiInterface;

import db.DB;
import entities.Item;
import lib.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class CheckoutInterface {
    private static int checkDebitCard(String cardNumber) {
        Random random = new Random();
        return random.nextInt(2);
    }

    private static int payByDebit(double amount, Scanner scanner) {
        System.out.print("Enter card number: ");
        String cardNumber = scanner.nextLine();
        int pin = 0;

        if (checkDebitCard(cardNumber) == 1) {
            System.out.print("Enter pin number: ");
            pin = scanner.nextInt();
        }

        String authorizeNum = BankInterface.authorizeCardInfo(cardNumber, pin);
        if(authorizeNum!=null){
            System.out.println("Thank you for your order!");
            scanner.nextLine();
            return 0;
        }
        System.out.println("Failed to verify payment!");
        return -1;
    }

    private static void payByCash(double amount, Scanner scanner) {
        System.out.println("Enter cash....");
        scanner.nextLine();
        System.out.println("Thank you for your order!");
        scanner.nextLine();
    }

    public static Object checkoutOrder() {
        Utilities.clearScreen();
        System.out.println("List of items: ");
        List<Item> items = DB.getAllItems();
        List<Item> cart = new ArrayList<>();
        for (Item item :
                items) {
            System.out.println(item);
        }

        System.out.print("\n");

        Scanner scanner = new Scanner(System.in);
        String barCode = null;
        while (true) {
            System.out.print("\n(Enter 'cancel' to cancel the transaction)" +
                    "\n(Enter empty input to check out)" +
                    "\nEnter bar code for next item : ");
            barCode = scanner.nextLine();

            if(barCode.equals("")) break;
            else if(barCode.equals("cancel")) return null;

            Item item = DB.getItemByBarCode(barCode);
            if (item==null) {
                System.out.println("Wrong bar code");
            } else if (!item.isAlcohol()){
                System.out.printf("%s - %s - %f added\n", item.getItemName(), item.getDescription(), item.getPrice());
                cart.add(item);
            } else {
                System.out.print("Verify age: ");
                int age = Integer.parseInt(scanner.nextLine());
                if(age >=21) {
                    System.out.printf("%s - %s - %f added\n", item.getItemName(), item.getDescription(), item.getPrice());
                    cart.add(item);
                } else {
                    System.out.println("Unable to verify age. Please enter other item.");
                }
            }
        }

        Utilities.clearScreen();
        double total = 0;

        System.out.println("Cart: ");
        for (Item item :
                cart) {
            System.out.println("\n"+item);
            total += item.getPrice();
        }
        System.out.println("Total: " + total);

        payment:
        while(true) {
            System.out.println("\nSelect payment methods: ");
            System.out.println("1. Credit/Debit Card");
            System.out.println("2. Cash");
            System.out.print("Enter: ");

            String select = scanner.nextLine();

            switch (select) {
                case "1":
                    payByDebit(total, scanner);
                    break payment;
                case "2":
                    payByCash(total, scanner);
                    break payment;
                default:
                    System.out.println("Invalid input!");
            }
        }
        return null;
    }
}
