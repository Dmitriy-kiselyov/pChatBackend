package ru.pussy_penetrator.model;

import com.sun.istack.internal.Nullable;
import ru.pussy_penetrator.exception.DatabaseException;
import ru.pussy_penetrator.exception.UnauthorizedException;
import ru.pussy_penetrator.util.AuthUtils;
import ru.pussy_penetrator.util.DBUtil;

import javax.ws.rs.core.HttpHeaders;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDB {
    private static final String TABLE = "pchat.users";

    public static void inflateUsers(AuthUser[] users) throws SQLException {
        for (AuthUser user : users) {
            EncryptedUser encryptedUser = new EncryptedUser(user);
            encryptedUser.generateToken();

            insertUser(encryptedUser);
        }
    }

    public static void insertUser(EncryptedUser user) throws SQLException {
        DBUtil.insert(TABLE, user.getLogin(), user.getPassword(), user.getToken());
    }

    public static String insertUser(AuthUser user) throws SQLException {
        EncryptedUser encryptedUser = new EncryptedUser(user);
        encryptedUser.generateToken();

        DBUtil.insert(TABLE, encryptedUser.getLogin(), encryptedUser.getPassword(), encryptedUser.getToken());

        return encryptedUser.getToken();
    }

    @Nullable
    static public String getToken(AuthUser user) throws SQLException {
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

    static public boolean isUserExists(String login) throws SQLException {
        String query = "SELECT * FROM " + TABLE
                + " WHERE login = \"" + login + "\"";
        ResultSet result = DBUtil.execute(query);

        return result.next();
    }

    @Nullable
    static public String getUserLogin(String token) throws SQLException {
        if (token == null) {
            return null;
        }

        String query = "SELECT login FROM " + TABLE + " WHERE token = \"" + token + "\"";
        ResultSet result = DBUtil.execute(query);

        boolean userExists = result.next();
        if (!userExists) {
            return null;
        }

        return result.getString("login");
    }

    static public String getLoginByAuth(HttpHeaders headers) {
        String token = headers.getHeaderString("Authorization");

        String login;
        try {
            login = getUserLogin(token);
        }
        catch (SQLException e) {
            throw new DatabaseException();
        }
        if (login == null) {
            throw new UnauthorizedException();
        }

        return login;
    }
}
