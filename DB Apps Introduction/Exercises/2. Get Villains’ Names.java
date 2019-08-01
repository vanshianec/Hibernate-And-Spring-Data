package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class Main{
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

        PreparedStatement statement = connection
                .prepareStatement("SELECT v.name, COUNT(*) AS 'count'" +
                        " FROM villains v JOIN minions_villains mv ON v.id = mv.villain_id" +
                        " GROUP BY v.id HAVING count > 15 ORDER BY count DESC;");

        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            System.out.printf("%s %s%n",resultSet.getString("name"), resultSet.getString("count"));
        }

    }
}
