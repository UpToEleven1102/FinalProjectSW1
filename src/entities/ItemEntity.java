package entities;

import java.util.ArrayList;
import java.util.List;

public class ItemEntity {
    private List<Item> items;

    public ItemEntity() {
        items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
