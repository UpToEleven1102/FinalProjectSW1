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

    public static Connection connect() {
        if (conn == null)
            try {
                String uri = "jdbc:sqlite:src/db/self_checkout.db";
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
            if (ItemDBService.getAllItems().size() == 0) {
                seed();
            }
        }
    }

    public static void seed() {
        List<ItemEntity> items = new ArrayList<>();
        File file = new File("src/db/sample_products.csv");
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
                ItemDBService.newItem(item);
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
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
