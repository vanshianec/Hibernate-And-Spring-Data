package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(Enter username);
        String username = reader.readLine();
        System.out.println(Enter password);
        String password = reader.readLine();

        Properties properties = new Properties();
        properties.setProperty(user, username);
        properties.setProperty(password, password);
        Connection connection;
        try {
            connection = DriverManager
                    .getConnection(jdbcmysqllocalhost3306minions_dbautoReconnect=true&useSSL=false, properties);
            System.out.println(Connection successful!);
        } catch (SQLException e) {
            System.out.println(Cannot connect to database.);
            e.printStackTrace();
        }
    }
}
