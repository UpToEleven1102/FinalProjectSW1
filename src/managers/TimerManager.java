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
import interfaces.PrinterInterface;

import java.util.*;

public class TimerManager {
    private static Timer timer = new Timer();
    private static List<ItemEntity> inventory = ItemDBService.getAllItems();

    public static void printDailyReport() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                List<TransactionEntity> transactions = TransactionDBService.getTransactions();
                Set<ItemEntity> items = new HashSet<>();
                double revenue = 0;
                for (TransactionEntity transaction :
                        transactions) {
                    revenue += transaction.getTotal();
                    for (ItemEntity item :
                            transaction.getItems()) {
                        if (items.contains(item)) {
                            for (ItemEntity it :
                                    items) {
                                if (it.getId() == item.getId()) {
                                    it.setInventoryQuantity(it.getInventoryQuantity() + 1);
                                    it.setPrice(it.getPrice() + item.getPrice());
                                }
                            }
                        } else {
                            item.setInventoryQuantity(1);
                            items.add(item);
                        }
                    }
                }
                PrinterInterface.printDailyReport(revenue, items);
            }
        }, 30 * 1000, 60 * 1000);
    }

    public static void printInventoryReport(double inventoryLevel) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                List<ItemEntity> reportItems = new ArrayList<>();
                for (ItemEntity item :
                        inventory) {
                    if (item.getInventoryLevel() < inventoryLevel) {
                        reportItems.add(item);
                    }
                }
                PrinterInterface.printInventoryReport(reportItems);
            }
        }, 31 * 1000, 60 * 1000);

    }
}