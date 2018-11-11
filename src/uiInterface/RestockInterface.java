package uiInterface;

import db.DB;
import entities.ItemEntity;
import lib.Utilities;
import managers.RestockManager;

import java.util.List;
import java.util.Scanner;

public class RestockInterface {
    public static void restockInventory() {
        Utilities.clearScreen();

        Scanner scanner = new Scanner(System.in);

        List<ItemEntity> items = DB.getAllItems();

        System.out.println("Inventory: ");
        for (ItemEntity item :
                items) {
            System.out.println(item);
        }
        System.out.print("\nEnter bar code: ");
        String barCode = scanner.nextLine();

        ItemEntity item = DB.getItemByBarCode(barCode);
        if (item != null) {
            System.out.println("Item selected: \n\t"+item.info());
            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            item = RestockManager.restock(item, quantity);
            System.out.println("Item updated: "+item.info());
            scanner.nextLine();
        } else {
            System.out.print("Enter item name: ");
            String name = scanner.nextLine();
            System.out.print("Description: ");
            String des = scanner.nextLine();
            System.out.print("Price: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Discount percent: ");
            int discount = Integer.parseInt(scanner.nextLine());
            System.out.print("Quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            System.out.print("Inventory level: ");
            double inventoryLevel = Double.parseDouble(scanner.nextLine());

            item = new ItemEntity(quantity, discount, name, des, false, price, barCode, inventoryLevel );

            item = RestockManager.restockNewItem(item);
            if (item!= null) {
                System.out.println("Item inserted: "+item.info());
            } else System.out.println("Failed to newItem item...");
            scanner.nextLine();
        }
    }
}
