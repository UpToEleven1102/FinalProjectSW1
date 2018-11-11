package db;

import entities.ItemEntity;
import lib.RandomString;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DB {
    private static Connection conn = null;
    private static RandomString uuid = new RandomString(6);

    public static Connection connect() {
        if (conn == null)
            try {
                String uri = "jdbc:sqlite:self_checkout.db";
                conn = DriverManager.getConnection(uri);
                System.out.println("Connection to SQLite has been established");
                createTables();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        return conn;
    }

    public static void createTables() {
        String itemsSql = "CREATE TABLE items(\n" +
                " id integer PRIMARY KEY AUTOINCREMENT, \n" +
                " name text NOT NULL, \n" +
                " description text, \n" +
                " quantity text NOT NULL , \n" +
                " discount text , \n" +
                " is_alcohol integer NOT NULL, \n" +
                " price real NOT NULL, \n" +
                " bar_code text NOT NULL, \n" +
                "inventory_level REAL" +
                " );";

        String transactionsSql = "CREATE TABLE transactions( " +
                "id text PRIMARY KEY," +
                "amount REAL NOT NULL," +
                "created_date TEXT);";

        String transaction_items = "CREATE TABLE transaction_items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                "transaction_id REFERENCES transactions(id), \n" +
                "item_id REFERENCES items(id), \n" +
                "item_price REAL NOT NULL," +
                "discount_amount REAL NOT NULL," +
                "quantity INTEGER NOT NULL" +
                ");";


        try (Statement stmt = conn.createStatement()) {
            stmt.execute(itemsSql);
            stmt.execute(transactionsSql);
            stmt.execute(transaction_items);
            System.out.printf("Tables are created \n");
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (getAllItems().size() == 0) {
                seed();
            }
        }
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

    public static void seed() {
        List<ItemEntity> items = new ArrayList<>();
        File file = new File("sample_products.csv");
        try {
            Scanner scanner = new Scanner(file);
            String line;
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                String[] fields = line.split(",");

                items.add(new ItemEntity(Integer.parseInt(fields[3]),
                        Integer.parseInt(fields[4]),
                        fields[2],
                        fields[1],
                        fields[6].equals("1"),
                        Double.parseDouble(fields[5]),
                        "",
                        Double.parseDouble(fields[7])));
            }

            for (ItemEntity item :
                    items) {
                newItem(item);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

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

    public static void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
