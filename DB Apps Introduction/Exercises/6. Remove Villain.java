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

        PreparedStatement villainStatement = connection
                .prepareStatement("SELECT name FROM villains WHERE id =?;");
        PreparedStatement minionStatement = connection
                .prepareStatement("SELECT COUNT(*) AS 'count' FROM minions_villains WHERE villain_id = ?;");

        PreparedStatement deleteVillainStatement = connection
                .prepareStatement("DELETE FROM villains WHERE id = ?;");
        PreparedStatement releaseMinionsStatement = connection
                .prepareStatement("DELETE FROM minions_villains WHERE villain_id = ?;");

        int villainId = Integer.parseInt(reader.readLine());

        villainStatement.setInt(1, villainId);
        ResultSet villainSet = villainStatement.executeQuery();
        if (!villainSet.next()) {
            System.out.println("No such villain was found.");
            return;
        }

        minionStatement.setInt(1, villainId);
        ResultSet minionSet = minionStatement.executeQuery();
        minionSet.next();

        int minionsReleased = minionSet.getInt("count");
        String villainName = villainSet.getString("name");

        releaseMinionsStatement.setInt(1, villainId);
        releaseMinionsStatement.executeUpdate();

        deleteVillainStatement.setInt(1, villainId);
        deleteVillainStatement.executeUpdate();

        System.out.printf("%s was deleted %n%d minions released%n", villainName, minionsReleased);
    }
}
