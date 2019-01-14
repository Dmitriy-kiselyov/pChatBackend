package ru.pussy_penetrator.util;

import ru.pussy_penetrator.DBListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class DBUtil {
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECT_URL = "jdbc:mysql://localhost:3306?characterEncoding=UTF-8&useLegacyDatetimeCode=false&amp&serverTimezone=UTC";

    private static Connection con;
    private static Statement  st;

    private static ScriptRunner scriptRunner;

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName(DB_DRIVER);

        con = DriverManager.getConnection(CONNECT_URL, "root", "");
        st = con.createStatement();
        scriptRunner = new ScriptRunner(con, false, true);
    }

    public static void close() throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    public static ResultSet execute(String query) throws SQLException {
        return st.executeQuery(query);
    }

    public static void insert(String table, Object... values) throws SQLException {
        String update = "INSERT INTO " + table + " VALUES " + toValues(values);
        st.executeUpdate(update);
    }

    private static String toValues(Object... values) {
        StringBuilder s = new StringBuilder("(");
        boolean first = true;

        for (Object val : values) {
            if (!first)
                s.append(", ");
            first = false;

            if (val == null) {
                s.append("NULL");
            } else if (val.getClass() == String.class) {
                String sVal = (String) val;
                sVal = sVal.trim();

                if (sVal.isEmpty())
                    s.append("NULL");
                else
                    s.append("'").append(sVal).append("'");
            } else if (val.getClass() == Date.class) {
                s.append("'").append(val).append("'");
            } else {
                s.append(val);
            }
        }

        s.append(")");
        return s.toString();
    }

    public static void executeFile(String path) throws IOException, SQLException {
        ClassLoader classLoader = DBUtil.class.getClassLoader();
        // path = URLDecoder.decode(classLoader.getResource(path).getPath(), "UTF-8");
        InputStreamReader reader = new InputStreamReader(classLoader.getResourceAsStream(path));

        scriptRunner.runScript(new BufferedReader(reader));
    }

}
