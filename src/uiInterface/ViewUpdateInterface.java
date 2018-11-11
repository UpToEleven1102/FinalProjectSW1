package uiInterface;

import db.DB;
import entities.ItemEntity;
import lib.Utilities;

import java.util.List;
import java.util.Scanner;

public class ViewUpdateInterface {
    private static Scanner scanner = new Scanner(System.in);

    public static void viewItem(int id) {
        Utilities.clearScreen();
        ItemEntity item = DB.getItemById(id);
        System.out.println(item.info());
        System.out.println("\n(Enter 'update' to edit the item info)");
        System.out.print("\n(Press enter to exit...)");
        String input = scanner.nextLine();
        if (input.equals("update")){
            updateItem(item);
        }
    }

    public static ItemEntity updateItem(ItemEntity item) {
        Utilities.clearScreen();
        System.out.println("Enter new info: ");
        System.out.print("Name (enter empty string to skip):");
        String name = scanner.nextLine();
        name = name.length() == 0? item.getItemName(): name;
        item.setItemName(name);

        System.out.print("Description (enter empty string to skip):");
        String des = scanner.nextLine();
        des = des.length() ==0? item.getDescription(): des;
        item.setDescription(des);

        System.out.print("Quantity (enter empty string to skip):");
        String q = scanner.nextLine();
        int quantity = q.length() == 0? item.getInventoryQuantity(): Integer.parseInt(q);
        item.setInventoryQuantity(quantity);

        System.out.print("Discount (enter empty string to skip):");
        String d = scanner.nextLine();
        int discount = d.length() == 0? item.getDiscountPercent():Integer.parseInt(d);
        item.setDiscountPercent(discount);

        System.out.print("Price (enter empty string to skip):");
        String p = scanner.nextLine();
        double price = p.length()==0 ? item.getPrice(): Double.parseDouble(p);
        item.setPrice(price);

        System.out.print("Bar code (enter empty string to skip):");
        String barCode = scanner.nextLine();
        barCode = barCode.length() ==0? item.getBarCode(): barCode;
        item.setBarCode(barCode);

        DB.updateItem(item);

        System.out.println("\n"+item.info());
        System.out.println("Item updated...");
        scanner.nextLine();
        return item;
    }

    public static void viewUpdateProduct() {
        Utilities.clearScreen();

        List<ItemEntity> items = DB.getAllItems();
        System.out.println("Select an item to view: ");
        for (ItemEntity item :
                items) {
            System.out.println("\t"+item.peek());
        }
        System.out.print("Enter Item ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        viewItem(id);
    }
}
