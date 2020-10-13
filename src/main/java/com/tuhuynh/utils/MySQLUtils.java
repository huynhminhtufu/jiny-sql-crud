package com.tuhuynh.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
public class MySQLUtils {
    private static Connection connectionInstance = null;

    public static Connection getConnection() {
        if (connectionInstance == null) {
            connectionInstance = initConnection();
        }
        return connectionInstance;
    }

    @SneakyThrows
    public static Connection initConnection() {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        return DriverManager
                .getConnection("jdbc:mysql://localhost/tutorial?user=root&password=example");
    }
}
