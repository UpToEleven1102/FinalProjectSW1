package managers;

import db.DB;
import db.ItemDBService;
import entities.ItemEntity;
import lib.Utilities;

public class ViewUpdateManager {
    public static ItemEntity viewItem(int id) {
        return ItemDBService.getItemById(id);
    }

    public static ItemEntity updateItem(ItemEntity item) {
        return ItemDBService.updateItem(item);
    }
}
