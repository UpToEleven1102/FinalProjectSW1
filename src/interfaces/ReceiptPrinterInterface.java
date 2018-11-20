package interfaces;

import entities.ItemEntity;
import entities.TransactionEntity;

import java.util.List;

public class ReceiptPrinterInterface {
    public static void printReceipt(TransactionEntity transaction) {
        System.out.println("\n\n\n Receipt info: ");
        System.out.println("\tDate: "+ transaction.getCreatedDate());
        System.out.println("\tItems: ");
        for (ItemEntity item :
                transaction.getItems()) {
            System.out.println("\t\t"+item);
        }
    }
    
    public static void printSubTotal(List<ItemEntity> cart) {
        double total =0;

        System.out.println("\nItems in cart: ");
        for (ItemEntity item :
                cart) {
            System.out.println("\t"+item);
            total += item.getPrice();
        }
        System.out.println("\nTotal: " + total);
    }
}
