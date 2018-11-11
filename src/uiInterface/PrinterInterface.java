package uiInterface;

import entities.ItemEntity;
import lib.Utilities;

import java.util.List;
import java.util.Set;

public class PrinterInterface {
    public static void printDailyReport(double revenue, Set<ItemEntity> items) {
        Utilities.clearScreen();
        System.out.println("Daily Report: ");
        System.out.printf("\tTotal revenue: %f\n", revenue);
        for (ItemEntity item :
                items) {
            System.out.println("\t" + item.report());
        }


    }

    public static void printInventoryReport(List<ItemEntity> inventory) {
        System.out.println("\n\nInventory Report: ");
        System.out.println("Items under inventory level:");
        for (ItemEntity item :
                inventory) {
            System.out.println("\t" + item.report());
        }
    }
}
