package ru.pussy_penetrator.model;

import com.mysql.cj.xdevapi.SqlDataResult;
import ru.pussy_penetrator.response.MessageResponse;
import ru.pussy_penetrator.util.DBUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MessageDB {
    private static String TABLE_START = "pchat.";
    private static String LOGIN_DELIM = "__";

    public static void insertMessage(String sender, Message message) throws IOException, SQLException {
        try {
            tryInsertMessage(sender, message);
            return;
        }
        catch (SQLException e) {}

        createTable(sender, message.getTarget());
        tryInsertMessage(sender, message);
    }

    public static List<MessageResponse> getMessagesUntil(String login1, String login2, int untilId) throws SQLException {
        String table = getTableName(login1, login2);
        String query = "SELECT * FROM " + table + " WHERE id >= " + untilId;

        ResultSet result;
        try {
            result = DBUtil.execute(query);
        }
        catch (SQLException e) {
            if (e.getErrorCode() == 1146) { // table does not exists
                return new LinkedList<>();
            }
            throw e;
        }

        LinkedList<MessageResponse> messages = new LinkedList<>();
        while(result.next()) {
            int id = result.getInt("id");
            long time = result.getLong("time");
            String text = result.getString("text");
            String sender = result.getString("sender");

            MessageResponse message = new MessageResponse();
            message.setId(id);
            message.setTime(time);
            message.setText(text);
            message.setSender(sender);

            messages.add(message);
        }

        return messages;
    }

    private static String getTableName(String login1, String login2) {
        String[] logins = sortedLogins(login1, login2);
        return TABLE_START + logins[0] + LOGIN_DELIM + logins[1];
    }

    private static String[] sortedLogins(String login1, String login2) {
        if (login1.compareToIgnoreCase(login2) < 0) {
            return new String[] {login1, login2};
        } else {
            return new String[] {login2, login1};
        }
    }

    private static void tryInsertMessage(String sender, Message message) throws SQLException {
        String table = getTableName(sender, message.getTarget());
        DBUtil.insert(table, null, message.getTime(), message.getText(), sender);
    }

    private static void createTable(String login1, String login2) throws IOException, SQLException {
        String[] logins = sortedLogins(login1, login2);

        Map<String, String> replaces = new HashMap<>();
        replaces.put("login1", logins[0]);
        replaces.put("login2", logins[1]);

        DBUtil.executeFileAndReplace("create_messages.sql", replaces);
    }
}
