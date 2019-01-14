package ru.pussy_penetrator.model;

import com.sun.istack.internal.Nullable;
import ru.pussy_penetrator.util.AuthUtils;
import ru.pussy_penetrator.util.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDB {
    private static final String TABLE = "pchat.users";

    public static void inflateUsers(User[] users) throws SQLException {
        for (User user : users) {
            EncryptedUser encryptedUser = new EncryptedUser(user);
            encryptedUser.generateToken();

            insertUser(encryptedUser);
        }
    }

    public static void insertUser(EncryptedUser user) throws SQLException {
        DBUtil.insert(TABLE, user.getLogin(), user.getPassword(), user.getToken());
    }

    public static String insertUser(User user) throws SQLException {
        EncryptedUser encryptedUser = new EncryptedUser(user);
        encryptedUser.generateToken();

        DBUtil.insert(TABLE, encryptedUser.getLogin(), encryptedUser.getPassword(), encryptedUser.getToken());

        return encryptedUser.getToken();
    }

    @Nullable
    static public String getToken(User user) throws SQLException {
        String query = "SELECT password, token FROM " + TABLE
                + " WHERE login = \"" + user.getLogin() + "\"";
        ResultSet result = DBUtil.execute(query);

        if (!result.next()) {
            return null;
        }

        String passwordHash = result.getString("password");
        String token = result.getString("token");
        if (passwordHash == null || token == null) {
            return null;
        }

        if (AuthUtils.verify(user.getPassword(), passwordHash)) {
            return token;
        }

        return null;
    }

    static public boolean isUserExists(User user) throws SQLException {
        String query = "SELECT * FROM " + TABLE
                + " WHERE login = \"" + user.getLogin() + "\"";
        ResultSet result = DBUtil.execute(query);

        return result.next();
    }
}
