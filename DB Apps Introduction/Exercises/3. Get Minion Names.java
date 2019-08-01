package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class Main {
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
                .getConnection("jdbc:mysql://localhost:3306/minions_db?autoReconnect=true&useSSL=false", properties);

        PreparedStatement getMinions = connection
                .prepareStatement("SELECT m.name, m.age FROM minions m JOIN minions_villains mv ON m.id = mv.minion_id WHERE mv.villain_id = ?;");
        PreparedStatement getVillain = connection
                .prepareStatement("SELECT name FROM villains WHERE id = ?");
        int id = Integer.parseInt(reader.readLine());

        getMinions.setInt(1, id);
        getVillain.setInt(1, id);

        ResultSet resultSet = getMinions.executeQuery();
        ResultSet villainName = getVillain.executeQuery();

        if (!villainName.next()) {
            System.out.printf("No villain with ID %d exists in the database.", id);
            return;
        }

        System.out.printf("Villain: %s%n", villainName.getString("name"));

        int counter = 1;
        while (resultSet.next()) {
            System.out.printf("%d. %s %d%n", counter, resultSet.getString("name"), resultSet.getInt("age"));
            counter++;
        }
    }
}
