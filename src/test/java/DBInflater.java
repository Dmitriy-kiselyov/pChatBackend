import ru.pussy_penetrator.model.User;
import ru.pussy_penetrator.model.UserDB;
import ru.pussy_penetrator.util.DBUtil;

import java.io.IOException;
import java.sql.SQLException;

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
        // ENTER COMMANDS
    }

    private static void create() throws SQLException, IOException {
        DBUtil.executeFile("create.sql");
    }

    private static void createUserTable() throws SQLException, IOException {
        DBUtil.executeFile("create_users.sql");
    }

    private static void inflateUsers() throws SQLException {
        User[] users = new User[] {
            new User("Dima", "123456"),
            new User("Tanya", "Orlova"),
            new User("Popka", "Durak")
        };

        UserDB.inflateUsers(users);
    }
}
