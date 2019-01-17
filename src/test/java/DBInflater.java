import ru.pussy_penetrator.model.AuthUser;
import ru.pussy_penetrator.model.AuthDB;
import ru.pussy_penetrator.util.DBUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBInflater {
    public static void main(String[] args) throws Exception {
        try {
            DBUtil.connect();
            inflate();
        }
        finally {
            DBUtil.close();
        }
    }

    private static void inflate() throws SQLException, IOException {
        createMessagesTable();
    }

    private static void create() throws SQLException, IOException {
        DBUtil.executeFile("create.sql");
    }

    private static void createUserTable() throws SQLException, IOException {
        DBUtil.executeFile("create_users.sql");
    }

    private static void createMessagesTable() throws IOException, SQLException {
        Map<String, String> replaces = new HashMap<>();
        replaces.put("<login1>", "Dima");
        replaces.put("<login2>", "Tanya");

        DBUtil.executeFileAndReplace("create_messages.sql", replaces);
    }

    private static void inflateUsers() throws SQLException {
        AuthUser[] users = new AuthUser[] {
            new AuthUser("Dima", "123456"),
            new AuthUser("Tanya", "Orlova"),
            new AuthUser("Popka", "Durak")
        };

        AuthDB.inflateUsers(users);
    }
}
