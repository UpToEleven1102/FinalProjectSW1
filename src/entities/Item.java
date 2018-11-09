package entities;

public class Item {
    private static int inventoryQuantity;
    private static int discountPercent;
    private String itemName;
    private String description;
    private boolean isAlcohol;
    private double price;

    public Item(String itemName, String description, boolean isAlcohol, double price) {
        this.itemName = itemName;
        this.description = description;
        this.isAlcohol = isAlcohol;
        this.price = price;
    }

    public static int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public static void setInventoryQuantity(int inventoryQuantity) {
        Item.inventoryQuantity = inventoryQuantity;
    }

    public static int getDiscountPercent() {
        return discountPercent;
    }

    public static void setDiscountPercent(int discountPercent) {
        Item.discountPercent = discountPercent;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAlcohol() {
        return isAlcohol;
    }

    public void setAlcohol(boolean alcohol) {
        isAlcohol = alcohol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
