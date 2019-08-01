package orm;

import orm.annotations.Column;
import orm.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class EntityManager<E> implements DbContext<E> {

    private Connection connection;
    private final String SELECT_ALL = "SELECT * FROM %s WHERE 1 %s";

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    private Field getId(Class entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity does not have primary key"));
    }

    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field primary = this.getId(entity.getClass());
        primary.setAccessible(true);
        Object value = primary.get(entity);

        if (value == null || (int) value <= 0) {
            return this.doInsert(entity);
        }
        return this.doUpdate(entity, primary);
    }

    private boolean doInsert(E entity) throws SQLException {
        String tableName = this.getTableName(entity.getClass());
        String[] fields = getFields(entity);

        String[] values = getValues(entity);

        String query = String.format("INSERT INTO `%s` (%s) VALUES(%s)",
                tableName, String.join(", ", fields), String.join(", ", values));

        return connection.prepareStatement(query).execute();
    }

    private boolean doUpdate(E entity, Field primary) throws SQLException, IllegalAccessException {
        String tableName = this.getTableName(entity.getClass());

        String[] fields = getFields(entity);
        String[] values = getValues(entity);

        String[] fieldValuePairs = IntStream.range(0, fields.length)
                .mapToObj(i -> fields[i] + " = " + values[i])
                .toArray(String[]::new);

        String whereClause = String.format("%s = %d", primary.getName(), (int) primary.get(entity));

        String query = String.format("UPDATE `%s` SET %s WHERE %s",
                tableName, String.join(", ", fieldValuePairs), whereClause);

        return connection.prepareStatement(query).execute();
    }

    private String[] getFields(E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> {
                    field.setAccessible(true);
                    return this.getDatabaseFieldName(field.getName());
                })
                .toArray(String[]::new);
    }

    //registrationDate  -> registration_date
    private String getDatabaseFieldName(String name) {
        return name.replaceAll("([A-Z])", "_$1").toLowerCase();
    }

    private String[] getValues(E entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        return "'" + field.get(entity) + "'";
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return "";
                    }
                })
                .toArray(String[]::new);
    }

    private String getTableName(Class<?> aClass) {
        return aClass.getSimpleName().toLowerCase() + "s";
    }

    public Iterable<E> find(Class<E> table) {
        return null;
    }

    public Iterable<E> find(Class<E> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Statement statement = connection.createStatement();
        String query = String.format(SELECT_ALL, this.getTableName(table), where != null ? " AND " + where : "");
        ResultSet resultSet = statement.executeQuery(query);

        List<E> result = new ArrayList<>();
        while (resultSet.next()) {
            E current = this.mapResultToEntity(resultSet, table);
            result.add(current);
        }
        return result;
    }

    public E findFirst(Class<E> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return findFirst(table, null);
    }

    public E findFirst(Class<E> table, String where) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return find(table, where == null ? " 1 LIMIT 1" : where + " LIMIT 1").iterator().next();
    }

    private E mapResultToEntity(ResultSet rs, Class<E> table) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        E entity = table.getDeclaredConstructor().newInstance();

        Arrays.stream(table.getDeclaredFields())
                .forEach(f -> {
                    f.setAccessible(true);
                    String name = getDatabaseFieldName(f.getName());
                    Object value = null;
                    try {
                        value = this.getFieldValueFromResultSet(rs, name, f.getType());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    try {
                        f.set(entity, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        return entity;
    }

    private Object getFieldValueFromResultSet(ResultSet rs, String name, Class type) throws SQLException {
        Object result = null;
        if (type == int.class) {
            result = rs.getInt(name);
        } else if (type == String.class) {
            result = rs.getString(name);
        } else if (type == Date.class) {
            result = rs.getDate(name);
        }
        return result;
    }

}
