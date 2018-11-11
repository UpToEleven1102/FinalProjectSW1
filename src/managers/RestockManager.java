package managers;

import db.DB;
import entities.ItemEntity;

public class RestockManager {
    public static ItemEntity restock(ItemEntity item, int quantity) {
        item.setInventoryQuantity(item.getInventoryQuantity() + quantity);
        DB.updateItem(item);
        return item;
    }

    public static ItemEntity restockNewItem(ItemEntity item){
        DB.newItem(item);
        return item;
    }
}
