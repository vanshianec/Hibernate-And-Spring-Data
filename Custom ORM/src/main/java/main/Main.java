package main;

import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String username = reader.readLine().trim();
        String password = reader.readLine().trim();
        String db = reader.readLine();

        Connector.createConnection(username, password, db);
        EntityManager<User> em = new EntityManager<>(Connector.getConnection());

        em.find(User.class, "username = 'Pesho'")
                .forEach(u -> System.out.println(u.getRegistrationDate()));
    }
}
