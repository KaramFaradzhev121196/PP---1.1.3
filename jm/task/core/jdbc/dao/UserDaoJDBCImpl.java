package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String tableName = "USERSDB";

    private static final String URL = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "rootRfhfv121196";

    private final static Connection connection = Util.connection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String table = "CREATE TABLE " + tableName + " ("
                + "id BIGINT(64) NOT NULL AUTO_INCREMENT,"
                + "name VARCHAR(45),"
                + "lastName VARCHAR(45),"
                + "age TINYINT(4), "
                + "PRIMARY KEY(id))";
        try (Statement statement = Util.connection().createStatement()) {
            DatabaseMetaData dbmd = Util.connection().getMetaData();
            ResultSet resultSet = dbmd.getTables(null, null, tableName, null);
            if (!resultSet.next()) {
                connection.setAutoCommit(false);
                statement.executeUpdate(table);
                System.out.println("Таблица создана");
            } else {
                System.out.println("Таблица уже существует");
            }
            connection.commit();
        } catch (SQLException throwables) {
            System.out.println("Соединение с базой данных не установлено");
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.connection().createStatement()) {
            DatabaseMetaData md1 = Util.connection().getMetaData();
            ResultSet rs1 = md1.getTables(null, null, tableName, null);
            if (rs1.next()) {
                connection.setAutoCommit(false);
                statement.executeUpdate("DROP TABLE " + tableName);
                System.out.println("Таблица удалена");
            } else {
                System.out.println("Такой таблицы не существует");
            }
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Соединение с базой данных не установлено");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String str = "INSERT INTO " + tableName + " SET" + " name = " + "'" + name + "'" +
                ", lastName = " + "'" + lastName + "'" + ", age = " + "'" + age + "'";

        try (Statement statement1 = Util.connection().createStatement()) {
            statement1.executeUpdate(str);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Соединение с базой данных не установлено");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = Util.connection().createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("DELETE FROM " + tableName + " WHERE id=" + id);
            System.out.println("удален пользователь с id " + id);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Соединение с базой данных не установлено");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement statement = Util.connection().createStatement()) {
            connection.setAutoCommit(false);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);

            while (resultSet.next()) {
                User templUser = new User();
                templUser.setId(resultSet.getLong("id"));
                templUser.setName(resultSet.getString("name"));
                templUser.setLastName(resultSet.getString("lastName"));
                templUser.setAge(resultSet.getByte("age"));
                list.add(templUser);

                for (User u : list){
                    System.out.println(u.toString());
                }
            }
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Соединение с базой данных не установлено");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.connection().createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate("TRUNCATE TABLE " + tableName);
            System.out.println("Таблица очищена");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Соединение с базой данных не установлено");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
