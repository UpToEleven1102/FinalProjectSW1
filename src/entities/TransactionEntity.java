package entities;

import java.util.ArrayList;
import java.util.List;

public class TransactionEntity {
    private String id;
    private double total;
    private String createdDate;
    private List<ItemEntity> items;

    public TransactionEntity(String id, double total, String createdDate, List<ItemEntity> items) {
        this.id = id;
        this.total = total;
        this.createdDate = createdDate;
        this.items = items;
    }

    public void addNewItem(Object item) {
        if (item instanceof ItemEntity) {
            this.items.add((ItemEntity)item);
        }
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "TransactionEntity{" +
                "items=" + items +
                ", id='" + id + '\'' +
                ", total=" + total +
                ", createdDate='" + createdDate + '\'' +
                '}';
    }
}
