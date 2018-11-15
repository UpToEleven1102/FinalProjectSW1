package uiInterface;

import db.DB;
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
            System.out.print("\n(Enter 'cancel' to cancel the transaction)" +
                    "\n(Enter empty input to check out)" +
                    "\nEnter bar code for next item : ");
            barCode = scanner.nextLine();

            if(barCode.equals("")) break;
            else if(barCode.equals("cancel")) return;
            CheckoutManager.addItemToCart(barCode, cart);
        }

        CheckoutManager.checkout(cart);
    }
}
