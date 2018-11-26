/*
* The A Team:
*
* Huyen Vu
* Zachary Simpson
* Isaac Simpson
* Wenhao Ge
* */


package interfaces;

import db.ItemDBService;
import entities.ItemEntity;
import lib.Utilities;
import managers.CheckoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CheckoutInterface {
    public static void checkoutOrder() {
        Utilities.clearScreen();
        System.out.println("List of items: ");
        List<ItemEntity> items = ItemDBService.getAllItems();
        List<ItemEntity> cart = new ArrayList<>();
        for (ItemEntity item :
                items) {
            System.out.println(item);
        }

        System.out.print("\n");

        Scanner scanner = new Scanner(System.in);
        String barCode = null;
        while (true) {
            System.out.print("\nEnter bar code for next item : " +
                            "\n(Enter 'cancel' to cancel the transaction)" +
                            "\n(Enter 'total' to check out)" +
                            "\n(Enter 'sub total' to print current receipt");
            barCode = scanner.nextLine();

            if(barCode.equals("total")) break;
            else if(barCode.equals("sub total")) {
                CheckoutManager.printSubtotal(cart); break;
            }
            else if(barCode.equals("cancel")) return;
            CheckoutManager.addItemToCart(barCode, cart);
        }

        CheckoutManager.checkout(cart);
    }
}
