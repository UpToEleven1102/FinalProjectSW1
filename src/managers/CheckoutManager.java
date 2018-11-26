/*
* The A Team:
*
* Huyen Vu
* Zachary Simpson
* Isaac Simpson
* Wenhao Ge
* */


package managers;

import db.ItemDBService;
import db.TransactionDBService;
import entities.ItemEntity;
import entities.TransactionEntity;
import interfaces.ReceiptPrinterInterface;
import lib.Utilities;

import java.util.List;
import java.util.Scanner;

public class CheckoutManager {
    private static Scanner scanner = new Scanner(System.in);

    public static void addItemToCart(String barCode, List<ItemEntity> cart) {
        ItemEntity item = ItemDBService.getItemByBarCode(barCode);
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

    private static double getTotal(List<ItemEntity> cart){
        double total =0;
        for (ItemEntity item :
                cart) {
            System.out.println("\n" + item);
            total += item.getPrice();
        }
        return total;
    }

    public static void printSubtotal(List<ItemEntity> cart) {
        System.out.println("\n\nTotal: " + getTotal(cart));
        ReceiptPrinterInterface.printSubTotal(cart);
    }

    public static void checkout(List<ItemEntity> cart) {
        Utilities.clearScreen();
        double total = 0;
        String result = null;

        System.out.println("Cart: ");

        total = getTotal(cart);

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
                    result = CashierManager.payByCard(cart, total);
                    break payment;
                case "2":
                    result = CashierManager.payByCash(cart, total);
                    break payment;
                default:
                    System.out.println("Invalid input!");
            }
        }

        if (result != null) {
            TransactionEntity transaction = TransactionDBService.newTransaction(cart, total);
            System.out.println("Thank you for your order!");

            ItemDBService.updateInventory(cart);

            ReceiptPrinterInterface.printReceipt(transaction);
            scanner.nextLine();
        } else {
            System.out.println("Failed to verify payment!");
        }
    }
}
