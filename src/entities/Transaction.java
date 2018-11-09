package entities;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private List<Item> items;
    private double total;

    public Transaction() {
        this.items = new ArrayList<>();
    }

    public Transaction(List<Item> items, double total) {
        this.items = items;
        this.total = total;
    }
}
