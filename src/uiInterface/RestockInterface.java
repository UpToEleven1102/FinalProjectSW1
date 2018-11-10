package uiInterface;

import db.DB;
import entities.Item;
import lib.Utilities;
import managers.RestockManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RestockInterface {
    public static void restockInventory() {
        Utilities.clearScreen();

        Scanner scanner = new Scanner(System.in);

        List<Item> items = DB.getAllItems();

        System.out.println("Inventory: ");
        for (Item item :
                items) {
            System.out.println(item);
        }
        System.out.print("\nEnter bar code: ");
        String barCode = scanner.nextLine();

        Item item = DB.getItemByBarCode(barCode);
        if (item != null) {
            System.out.println("Item selected: \n\t"+item.info());
            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            item = RestockManager.restock(item, quantity);
            System.out.println("Item updated: "+item.info());
            scanner.nextLine();
        } else {
            item = RestockManager.restockNewItem(barCode);
            if (item!= null) {
                System.out.println("Item inserted: "+item.info());
            } else System.out.println("Failed to insert item...");
            scanner.nextLine();
        }
    }
}
