package managers;

import db.DB;
import entities.Item;

import java.util.Scanner;

public class RestockManager {
    public static Item restock(Item item, int quantity) {
        item.setInventoryQuantity(item.getInventoryQuantity() + quantity);
        DB.updateItem(item);
        return item;
    }

    public static Item restockNewItem(String barcode){
        Scanner scanner = new Scanner(System.in);
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

        Item item = new Item(quantity, discount, name, des, false, price, barcode );
        DB.insert(item);
        return item;
    }
}
