/*
* The A Team:
*
* Huyen Vu
* Zachary Simpson
* Isaac Simpson
* Wenhao Ge
* */


package db;

import entities.ItemEntity;
import lib.RandomString;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDBService {
    private static RandomString uuid = new RandomString(6);

    private static Connection conn = DB.connect();

    public static ArrayList<ItemEntity> getAllItems() {
        ArrayList<ItemEntity> items = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM items");
            while (rs.next()) {
                int id = rs.getInt("id");
                String des = rs.getString("description");
                String name = rs.getString("name");
                int isAlcohol = rs.getInt("is_alcohol");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                int discount = rs.getInt("discount");
                String barCode = rs.getString("bar_code");
                double inventoryLevel = rs.getDouble("inventory_level");
                items.add(new ItemEntity(id, quantity, discount, name, des, isAlcohol == 1, price, barCode, inventoryLevel));
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public static void newItem(ItemEntity item) {
        int alcohol = item.isAlcohol() ? 1 : 0;
        String barCode = item.getBarCode().equals("") ? uuid.nextString() : item.getBarCode();

        String sql = String.format("INSERT INTO items (name, description, quantity, discount, is_alcohol, price, bar_code, inventory_level) values('%s', '%s', %d, %d, %d, %f, '%s', %f);",
                item.getItemName(),
                item.getDescription(),
                item.getInventoryQuantity(),
                item.getDiscountPercent(),
                alcohol,
                item.getPrice(),
                barCode,
                item.getInventoryLevel());
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ItemEntity getItemByBarCode(String _barCode) {
        try {
            String query = "SELECT * FROM items WHERE bar_code = ?;";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, _barCode);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String des = rs.getString("description");
                String name = rs.getString("name");
                int isAlcohol = rs.getInt("is_alcohol");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                int discount = rs.getInt("discount");
                String barCode = rs.getString("bar_code");
                double inventoryLevel = Double.parseDouble(rs.getString("inventory_level"));
                return (new ItemEntity(id, quantity, discount, name, des, isAlcohol == 1, price, barCode, inventoryLevel));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ItemEntity getItemById(int id) {
        try (Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM items WHERE id=" + id);
            if (rs.next()) {
                String name = rs.getString("name");
                String des = rs.getString("description");
                int quantity = rs.getInt("quantity");
                int discount = rs.getInt("discount");
                int alcohol = rs.getInt("is_alcohol");
                double price = rs.getDouble("price");
                String barCode = rs.getString("bar_code");
                double inventoryLevel = Double.parseDouble(rs.getString("inventory_level"));
                ItemEntity item = new ItemEntity(id, discount, name, des, alcohol == 1, price, barCode, inventoryLevel);
                item.setId(id);
                return item;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ItemEntity updateItem(ItemEntity item) {
        try {
            String query = String.format("UPDATE items SET\n" +
                            "name='%s',\n" +
                            "description='%s',\n" +
                            "quantity=%d,\n" +
                            "discount=%d,\n" +
                            "price=%f,\n" +
                            "bar_code='%s',\n" +
                            "inventory_level=%f\n" +
                            "WHERE id=%d;",
                    item.getItemName(),
                    item.getDescription(),
                    item.getInventoryQuantity(),
                    item.getDiscountPercent(),
                    item.getPrice(),
                    item.getBarCode(),
                    item.getInventoryLevel(),
                    item.getId());
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateInventory(List<ItemEntity> cart) {
        for (ItemEntity item :
                cart) {
            item.setInventoryQuantity(item.getInventoryQuantity() -1);
            ItemDBService.updateItem(item);
        }
    }
}
