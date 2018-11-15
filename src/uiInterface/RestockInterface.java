package uiInterface;

import db.DB;
import db.ItemDBService;
import entities.ItemEntity;
import lib.Utilities;
import managers.RestockManager;

import java.util.List;
import java.util.Scanner;

public class RestockInterface {
    public static void restockInventory() {
        Utilities.clearScreen();

        Scanner scanner = new Scanner(System.in);

        List<ItemEntity> items = ItemDBService.getAllItems();

        System.out.println("Inventory: ");
        for (ItemEntity item :
                items) {
            System.out.println(item);
        }
        System.out.print("\nEnter bar code: ");
        String barCode = scanner.nextLine();

        ItemEntity item = RestockManager.restock(barCode);

        if (item == null) {
            RestockManager.restockNewItem(barCode);
        }
    }
}
