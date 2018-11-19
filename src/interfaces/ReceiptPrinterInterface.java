package interfaces;

import entities.ItemEntity;
import entities.TransactionEntity;

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
}
