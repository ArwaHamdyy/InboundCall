package com.twilio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MessageDAO {
    private static final String DATABASE_URL = "jdbc:postgresql://127.0.0.1:5432/Message";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    static {
        try {
            // Explicitly load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void saveMessage(String message) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
            String query = "INSERT INTO messages (message) VALUES (?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, message);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getLatestMessage() {
        String message = null;
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD)) {
            String query = "SELECT message FROM messages ORDER BY id DESC LIMIT 1";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    message = resultSet.getString("message");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message != null ? message : "This a default message";
    }
}
