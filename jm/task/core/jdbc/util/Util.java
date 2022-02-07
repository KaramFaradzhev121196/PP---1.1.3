package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/mydb?useUnicode=true&useSSL=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "rootRfhfv121196";

    static Connection connection = null;

    public static Connection connection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()) {
                System.out.println("Соединениие с БД установлено");
            }
        } catch (SQLException e) {
            System.out.println("Соединениие с БД не установлено");
        }
        return connection;
    }// реализуйте настройку соеденения с БД
}
