package managers;

import db.DB;
import db.TransactionDBService;
import entities.ItemEntity;
import lib.Utilities;

import java.util.List;
import java.util.Scanner;

public class CheckoutManager {
    private static Scanner scanner = new Scanner(System.in);

    public static void addItemToCart(String barCode, List<ItemEntity> cart) {
        ItemEntity item = DB.getItemByBarCode(barCode);
        if (item == null) {
            System.out.println("Wrong bar code");
        } else if (!item.isAlcohol()) {
            System.out.printf("%s - %s - %f added\n", item.getItemName(), item.getDescription(), item.getPrice());
            cart.add(item);
        } else {
            System.out.print("Verify age: ");
            int age = Integer.parseInt(scanner.nextLine());
            if (age >= 21) {
                System.out.printf("%s - %s - %f added\n", item.getItemName(), item.getDescription(), item.getPrice());
                cart.add(item);
            } else {
                System.out.println("Unable to verify age. Please enter other item.");
            }
        }
    }

    public static void checkout(List<ItemEntity> cart) {
        Utilities.clearScreen();
        double total = 0;
        String result = null;

        System.out.println("Cart: ");
        for (ItemEntity item :
                cart) {
            System.out.println("\n" + item);
            total += item.getPrice();
        }
        System.out.println("Total: " + total);

        payment:
        while (true) {
            System.out.println("\nSelect payment methods: ");
            System.out.println("1. Credit/Debit Card");
            System.out.println("2. Cash");
            System.out.print("Enter: ");

            String select = scanner.nextLine();

            switch (select) {
                case "1":
                    result = CashierManager.payByDebit(cart, total);
                    break payment;
                case "2":
                    result = CashierManager.payByCash(cart, total);
                    break payment;
                default:
                    System.out.println("Invalid input!");
            }
        }

        if (result != null) {
            TransactionDBService.newTransaction(cart, total);
            System.out.println("Thank you for your order!");
            scanner.nextLine();
        } else {
            System.out.println("Failed to verify payment!");
        }
    }
}
