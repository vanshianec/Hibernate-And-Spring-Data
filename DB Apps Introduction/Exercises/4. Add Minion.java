package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class Test {

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/minions_db?autoReconnect=true&useSSL=false";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter username:");
        String username = reader.readLine();
        System.out.println("Enter password:");
        String password = reader.readLine();

        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);
        Connection connection;

        try {
            connection = DriverManager
                    .getConnection(CONNECTION_URL, properties);

            PreparedStatement townStatement = connection
                    .prepareStatement("SELECT id FROM towns WHERE name = ?;");
            PreparedStatement villainStatement = connection
                    .prepareStatement("SELECT id FROM villains WHERE name = ?;");
            PreparedStatement minionStatement = connection
                    .prepareStatement("SELECT id FROM minions WHERE name = ?;");

            String inputValues[] = reader.readLine().split(" ");
            String minionName = inputValues[1];
            int minionAge = Integer.parseInt(inputValues[2]);
            String town = inputValues[3];
            String villain = reader.readLine().split(" ")[1];

            townStatement.setString(1, town);
            villainStatement.setString(1, villain);
            minionStatement.setString(1, minionName);

            ResultSet townSet = townStatement.executeQuery();
            int townId;

            if (!townSet.next()) {
                insertTownIntoDatabase(connection, town);
                System.out.printf("Town %s was added to the database %n", town);
                townId = getTownId(townStatement);
            } else {
                townId = townSet.getInt("id");
            }

            insertMinionIntoDatabase(connection, townId, minionName, minionAge);

            ResultSet villainSet = villainStatement.executeQuery();
            int villainId;

            if (!villainSet.next()) {
                insertVillainIntoDatabase(connection, villain);
                System.out.printf("Villain %s was added to the database %n", villain);
                villainId = getVillainId(villainStatement);
            } else {
                villainId = villainSet.getInt("id");
            }

            ResultSet minionSet = minionStatement.executeQuery();
            minionSet.next();
            int minionId = minionSet.getInt("id");

            insertMinionIdAndVillainIdIntoDatabase(connection, minionId, villainId);
            System.out.printf("Successfully added %s to be minion of %s %n", minionName, villain);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertMinionIntoDatabase(Connection connection, int townId, String minionName, int minionAge) throws SQLException {
        PreparedStatement insertMinion = connection
                .prepareStatement("INSERT INTO `minions` (`name`, `age`, `town_id`) VALUES (?, ?, ?);");
        insertMinion.setString(1, minionName);
        insertMinion.setInt(2, minionAge);
        insertMinion.setInt(3, townId);
        insertMinion.executeUpdate();
    }

    private static void insertTownIntoDatabase(Connection connection, String town) throws SQLException {
        PreparedStatement insertTown = connection
                .prepareStatement("INSERT INTO `towns` (`name`) VALUES (?);");
        insertTown.setString(1, town);
        insertTown.executeUpdate();
    }

    private static void insertVillainIntoDatabase(Connection connection, String villain) throws SQLException {
        PreparedStatement insertTown = connection
                .prepareStatement("INSERT INTO `villains` (`name`, `evilness_factor`) VALUES (?, 'evil');");
        insertTown.setString(1, villain);
        insertTown.executeUpdate();
    }

    private static void insertMinionIdAndVillainIdIntoDatabase(Connection connection, int minionId, int villainId) throws SQLException {
        PreparedStatement statement = connection
                .prepareStatement("INSERT INTO `minions_villains` (`minion_id`, `villain_id`) VALUES (?, ?);");
        statement.setInt(1, minionId);
        statement.setInt(2, villainId);
        statement.executeUpdate();
    }

    private static int getTownId(PreparedStatement townStatement) throws SQLException {
        ResultSet townSet = townStatement.executeQuery();
        townSet.next();
        return townSet.getInt("id");
    }

    private static int getVillainId(PreparedStatement villainStatement) throws SQLException {
        ResultSet villainSet = villainStatement.executeQuery();
        villainSet.next();
        return villainSet.getInt("id");
    }

}
