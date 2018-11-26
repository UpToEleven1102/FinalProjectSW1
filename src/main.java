/*
* The A Team:
*
* Huyen Vu
* Zachary Simpson
* Isaac Simpson
* Wenhao Ge
* */


import db.DB;
import lib.Utilities;
import managers.TimerManager;
import interfaces.CheckoutInterface;
import interfaces.RestockInterface;
import interfaces.ViewUpdateInterface;

import java.util.Scanner;

public class main {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        String choice = "-1";

        //DB config
        DB.connect();

        TimerManager.printDailyReport();
        TimerManager.printInventoryReport(1);

        while (true) {
            Utilities.clearScreen();
            System.out.println("Choose function: \n " +
                    "1. Checkout Order. \n " +
                    "2. Restock Product Inventory. \n " +
                    "3. View/Update Product \n 0. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextLine();

            switch (choice) {
                case "1":
                    CheckoutInterface.checkoutOrder(); break;
                case "2":
                    RestockInterface.restockInventory(); break;
                case "3":
                    ViewUpdateInterface.viewUpdateProduct(); break;
                case "0": DB.close(); return;
                default: System.out.println("Wrong input.");
            }
        }

    }
}
