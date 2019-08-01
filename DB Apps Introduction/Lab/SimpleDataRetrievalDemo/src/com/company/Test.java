package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
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

        PreparedStatement increaseAgeStatement = connection
                .prepareStatement("CALL usp_get_older(?);");

        int minionId = Integer.parseInt(reader.readLine());

        increaseAgeStatement.setInt(1, minionId);
        increaseAgeStatement.executeUpdate();

        PreparedStatement getResultStatement = connection
                .prepareStatement("SELECT name, age FROM minions WHERE id  = ?");
        getResultStatement.setInt(1, minionId);

        ResultSet resultSet = getResultStatement.executeQuery();
        resultSet.next();
        System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("age"));
    }
}
