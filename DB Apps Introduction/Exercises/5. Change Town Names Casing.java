package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Test {

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/minions_db?autoReconnect=true&useSSL=false";

    public static void main(String[] args) throws IOException, SQLException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter username:");
        String username = reader.readLine();
        System.out.println("Enter password:");
        String password = reader.readLine();

        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        Connection connection;

        connection = DriverManager
                .getConnection(CONNECTION_URL, properties);

        PreparedStatement updateStatement = connection
                .prepareStatement("UPDATE towns SET name = UPPER(name) WHERE country = ?;");
        PreparedStatement getStatement = connection
                .prepareStatement("SELECT name FROM towns WHERE country = ?;");

        String country = reader.readLine();

        updateStatement.setString(1, country);
        updateStatement.executeUpdate();

        getStatement.setString(1, country);
        ResultSet townsSet = getStatement.executeQuery();

        List<String> towns = new ArrayList<>();

        while (townsSet.next()) {
            towns.add(townsSet.getString("name"));
        }

        if (towns.isEmpty()) {
            System.out.println("No town names were affected.");
            return;
        }

        System.out.printf("%d town names were affected.%n[%s]%n", towns.size(), String.join(", ", towns));
    }
}
