package ru.pussy_penetrator.model;

import ru.pussy_penetrator.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class UserMessagePreviewDB {
    private static String TABLE = "pchat.users";

    public static List<UserMessagePreview> getAllUsers() throws SQLException {
        String query = "SELECT login FROM " + TABLE;
        ResultSet result = DBUtil.execute(query);

        LinkedList<UserMessagePreview> users = new LinkedList<>();
        while(result.next()) {
            String login = result.getString("login");
            users.add(new UserMessagePreview(login));
        }

        return users;
    }
}
