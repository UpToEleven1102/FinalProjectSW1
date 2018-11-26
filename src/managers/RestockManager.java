/*
* The A Team:
*
* Huyen Vu
* Zachary Simpson
* Isaac Simpson
* Wenhao Ge
* */


package managers;

import db.DB;
import db.ItemDBService;
import entities.ItemEntity;

import java.util.Scanner;

public class RestockManager {
    public static Scanner scanner = new Scanner(System.in);

    public static ItemEntity restock(String barCode) {
        ItemEntity item = ItemDBService.getItemByBarCode(barCode);
        if (item != null) {
            System.out.println("Item selected: \n\t"+item.info());
            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine());
            item.setInventoryQuantity(item.getInventoryQuantity() + quantity);
            ItemDBService.updateItem(item);
            System.out.println("Item updated: "+item.info());
            scanner.nextLine();
        }
        return item;
    }

    public static ItemEntity restockNewItem(String barCode){
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

        ItemEntity item = new ItemEntity(quantity, discount, name, des, false, price, barCode, inventoryLevel );

        item = ItemDBService.updateItem(item);
        if (item!= null) {
            System.out.println("Item inserted: "+item.info());
        } else System.out.println("Failed to newItem item...");
        scanner.nextLine();
        ItemDBService.newItem(item);
        return item;
    }
}
