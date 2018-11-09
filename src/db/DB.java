package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static Connection conn;
    public static void connect() {
        conn = null;
        try {
            String uri = "jdbc:sqlite:C:/db/self_checkout.db";
            conn = DriverManager.getConnection(uri);

            System.out.println("Connection to SQLite has been established");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS "
    }

    public static void close() {
        try {
            if (conn!=null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
