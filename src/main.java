import java.util.Scanner;

public class main {
    private static void startCheckoutOrder() {

    }

    private static void startRestockInventory() {

    }

    private static void viewUpdateProduct() {

    }

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        String choice = "-1";

        while (true) {
            System.out.println("Choose function: \n 1. Checkout Order. \n 2. Restock Product Inventory. \n 3. View/Update Product \n 0. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextLine();

            switch (Integer.parseInt(choice)) {
                case 1: startCheckoutOrder(); break;
                case 2: startRestockInventory(); break;
                case 3: viewUpdateProduct(); break;
                case 0: return;
                default: System.out.println("Wrong input.");
            }
        }
    }
}
