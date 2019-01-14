package ru.pussy_penetrator;

import ru.pussy_penetrator.util.DBUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class DBListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log("Соединяюсь с БД...");
        try {
            DBUtil.connect();
            log("Соединение с БД успешно");
        }
        catch (ClassNotFoundException e) {
            log("Драйвер БД не найден", e);
        }
        catch (SQLException e) {
            log("Не удалось установить соединение", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log("Отсоединяюсь от БД...");
        try {
            DBUtil.close();
            log("Отсоединение прошло успешно");
        }
        catch (SQLException e) {
            log("Отсоединение прошло с ошибкой", e);
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }

    private static void log(String message, Exception e) {
        log(message);
        e.printStackTrace();
    }
}
