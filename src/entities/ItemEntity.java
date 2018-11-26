/*
* The A Team:
*
* Huyen Vu
* Zachary Simpson
* Isaac Simpson
* Wenhao Ge
* */


package entities;

public class ItemEntity {
    private int id;
    private int inventoryQuantity;
    private int discountPercent;
    private String itemName;
    private String description;
    private boolean isAlcohol;
    private double price;
    private String barCode;
    private double inventoryLevel;

    public ItemEntity(int id, int inventoryQuantity, int discountPercent, String itemName, String description, boolean isAlcohol, double price, String barCode, double inventoryLevel) {
        this.id = id;
        this.inventoryQuantity = inventoryQuantity;
        this.discountPercent = discountPercent;
        this.itemName = itemName;
        this.description = description;
        this.isAlcohol = isAlcohol;
        this.price = price;
        this.barCode = barCode;
        this.inventoryLevel = inventoryLevel;
    }

    public ItemEntity(int inventoryQuantity, int discountPercent, String itemName, String description, boolean isAlcohol, double price, String barCode, double inventoryLevel) {
        this.inventoryQuantity = inventoryQuantity;
        this.discountPercent = discountPercent;
        this.itemName = itemName;
        this.description = description;
        this.isAlcohol = isAlcohol;
        this.price = price;
        this.barCode = barCode;
        this.inventoryLevel = inventoryLevel;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public double getInventoryLevel() {
        return inventoryLevel;
    }

    public void setInventoryLevel(double inventoryLevel) {
        this.inventoryLevel = inventoryLevel;
    }

    public String peek() {
        return String.format("\tId: %d - Item: %s - Description: %s",
                this.getId(),
                this.getItemName(),
                this.getDescription());
    }

    public String info() {
        return String.format("\tItem: %s - Description: %s - Quantity: %d - Original Price: %f - Discount percent: %d - Bar code: %s - Inventory level: %f" ,
                this.getItemName(),
                this.getDescription(),
                this.getInventoryQuantity(),
                this.getPrice(),
                this.getDiscountPercent(),
                this.getBarCode(),
                this.getInventoryLevel());
    }

    public String report() {
        return String.format("\tItem: %s - ID: %d - Quantity sold: %d - Revenue: %f - Inventory level: %f" ,
                this.getItemName(),
                this.getId(),
                this.getInventoryQuantity(),
                this.getPrice(),
                this.getInventoryLevel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemEntity)) return false;

        ItemEntity that = (ItemEntity) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("\tItem: %s - Description: %s - Original Price: %f - Discount percent: %d - Bar code: %s",
                this.getItemName(),
                this.getDescription(),
                this.getPrice(),
                this.getDiscountPercent(),
                this.getBarCode());
    }
}
