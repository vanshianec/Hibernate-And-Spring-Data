package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Arrays;
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

        int[] inputValues = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < inputValues.length; i++) {
            builder.append("?,");
        }

        String sql = "UPDATE minions SET name = LOWER(name), age = age + 1 WHERE id IN(" + builder.deleteCharAt(builder.length() - 1).toString() + ");";

        PreparedStatement minionsStatement = connection
                .prepareStatement(sql);

        for (int i = 1; i <= inputValues.length; i++) {
            minionsStatement.setInt(i, inputValues[i - 1]);
        }

        minionsStatement.executeUpdate();

        PreparedStatement allMinionsStatement = connection
                .prepareStatement("SELECT name, age FROM minions;");

        ResultSet resultSet = allMinionsStatement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("age"));
        }
    }
}
