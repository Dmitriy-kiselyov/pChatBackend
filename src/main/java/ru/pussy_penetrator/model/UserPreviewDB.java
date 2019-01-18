package ru.pussy_penetrator.model;

import ru.pussy_penetrator.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserPreviewDB {
    private static String TABLE = "pchat.users";

    public static List<UserPreview> getAllUsersExcept(String login) throws SQLException {
        String query = "SELECT login FROM " + TABLE + " WHERE login <> \"" + login + "\"";
        return getUsersFromQuery(query);
    }

    private static List<UserPreview> getUsersFromQuery(String query) throws SQLException {
        ResultSet result = DBUtil.execute(query);

        LinkedList<UserPreview> users = new LinkedList<>();
        while(result.next()) {
            String login = result.getString("login");
            users.add(new UserPreview(login));
        }

        return users;
    }
}
