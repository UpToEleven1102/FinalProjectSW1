package db;

import entities.Item;
import lib.RandomString;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DB {
    private static Connection conn;
    private static RandomString randomString = new RandomString(6);

    public static void connect() {
        conn = null;
        try {
            String uri = "jdbc:sqlite:self_checkout.db";
            conn = DriverManager.getConnection(uri);

            System.out.println("Connection to SQLite has been established");

            createNewTable("items");
            if (getAllItems().size() == 0) {
                seed();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable(String tableName) {
        String sql = "CREATE TABLE " + tableName + "(\n" +
                " id integer PRIMARY KEY AUTOINCREMENT, \n" +
                " name text NOT NULL, \n" +
                " description text, \n" +
                " quantity text NOT NULL , \n" +
                " discount text , \n" +
                " is_alcohol integer NOT NULL, \n" +
                " price real NOT NULL, \n" +
                " bar_code text NOT NULL \n" +
                " );";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.printf("Table %s is created \n", tableName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insert(Item item) {
        int alcohol = item.isAlcohol() ? 1 : 0;
        String barCode = item.getBarCode().equals("") ? randomString.nextString() : item.getBarCode();

        String sql = String.format("INSERT INTO items (name, description, quantity, discount, is_alcohol, price, bar_code) values('%s', '%s', %d, %d, %d, %f, '%s');",
                item.getItemName(),
                item.getDescription(),
                item.getInventoryQuantity(),
                item.getDiscountPercent(),
                alcohol,
                item.getPrice(),
                barCode);
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void seed() {
        List<Item> items = new ArrayList<>();
        File file = new File("sample_products.csv");
        try {
            Scanner scanner = new Scanner(file);
            String line;
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                String[] fields = line.split(",");

                items.add(new Item(Integer.parseInt(fields[3]),
                        Integer.parseInt(fields[4]),
                        fields[2],
                        fields[1],
                        fields[6].equals("1"),
                        Double.parseDouble(fields[5]), ""));
            }

            for (Item item :
                    items) {
                insert(item);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<>();

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
                items.add(new Item(id, quantity, discount, name, des, isAlcohol == 1, price, barCode));
            }
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public static Item getItemByBarCode(String _barCode) {
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
                return (new Item(id, quantity, discount, name, des, isAlcohol == 1, price, barCode));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Item getItemById(int id) {
        try (Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM items WHERE id=" + id);
            if(rs.next()) {
                String name = rs.getString("name");
                String des = rs.getString("description");
                int quantity = rs.getInt("quantity");
                int discount = rs.getInt("discount");
                int alcohol = rs.getInt("is_alcohol");
                double price = rs.getDouble("price");
                String barCode = rs.getString("bar_code");
                Item item = new Item(id, discount, name, des, alcohol==1, price, barCode);
                item.setId(id);
                return item;
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Item updateItem(Item item) {
        try {
            String query = String.format("UPDATE items SET\n" +
                            "name='%s',\n" +
                            "description='%s',\n" +
                            "quantity=%d,\n" +
                            "discount=%d,\n" +
                            "price=%f,\n" +
                            "bar_code='%s'\n" +
                            "WHERE id=%d;",
                    item.getItemName(),
                    item.getDescription(),
                    item.getInventoryQuantity(),
                    item.getDiscountPercent(),
                    item.getPrice(),
                    item.getBarCode(),
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
