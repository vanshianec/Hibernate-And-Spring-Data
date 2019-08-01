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

        PreparedStatement minionsStatement = connection
                .prepareStatement("SELECT name FROM minions;");

        ResultSet minions = minionsStatement.executeQuery();

        List<String> minionNames = new ArrayList<>();

        while (minions.next()) {
            minionNames.add(minions.getString("name"));
        }

        int middle = minionNames.size() / 2;

        int count = 0;

        for (int i = 0; i < middle; i++) {
            System.out.println(minionNames.get(count));
            System.out.println(minionNames.get(minionNames.size() - 1 - count));
            count++;
        }

        if (minionNames.size() % 2 != 0) {
            System.out.println(minionNames.get(middle));
        }

    }
}
