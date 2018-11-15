package db;

import entities.ItemEntity;
import entities.TransactionEntity;
import lib.RandomString;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDBService {
    private static Connection conn = DB.connect();
    private static RandomString uuid = new RandomString(10);

    public static void newTransaction(List<ItemEntity> items, double amount) {
        try (Statement stmt = conn.createStatement();) {
            String id = uuid.nextString();
            LocalDateTime dateTime = LocalDateTime.now();

            stmt.execute("INSERT INTO transactions(id,amount,created_date) VALUES ('" + id + "'," + amount + ",'" + dateTime.toString() + "');");
            for (ItemEntity item :
                    items) {
                double itemPrice = item.getPrice() * (100 - item.getDiscountPercent()) / 100;
                double discountAmount = item.getPrice() - itemPrice;
                stmt.executeUpdate("INSERT INTO transaction_items(" +
                        "transaction_id, " +
                        "item_id, " +
                        "item_price, " +
                        "discount_amount, " +
                        "quantity) VALUES(" +
                        "'" + id + "'," +
                        +item.getId() + "," +
                        +itemPrice + "," +
                        +discountAmount + "," +
                        "1);");
            }

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<TransactionEntity> getTransactions() {
        List<TransactionEntity> transactions = new ArrayList<>();
        try(Statement stmt = conn.createStatement();){
            ResultSet rs = stmt.executeQuery("SELECT * from transactions;");
            while(rs.next()) {
                String id = rs.getString("id");
                double amount = rs.getDouble("amount");
                String createdDate = rs.getString("created_date");
                List<ItemEntity> items = getTransactionItems(id);
                transactions.add(new TransactionEntity(id, amount, createdDate, items));
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return transactions;
    }

    public static List<ItemEntity> getTransactionItems(String transactionId){
        List<ItemEntity> items = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from transaction_items WHERE transaction_id='"+transactionId+"';");
            while(rs.next()) {
                int item_id = rs.getInt("item_id");
                ItemEntity item = ItemDBService.getItemById(item_id);
                items.add(item);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
