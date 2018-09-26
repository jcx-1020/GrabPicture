package westos;

import java.sql.*;

public class Utils {
    static final String URL = "jdbc:mysql://localhost:3306/eshop?serverTimezone=GMT%2B8&useSSL=false" +
            "&useServerPrepStmts=true&cachePrepStmts=true&rewriteBatchedStatements=true" +
            "&useCursorFetch=true&defaultFetchSize=100";
    static final String USERNAME = "root";
    static final String PASSWORD = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return conn;
    }

    public static void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(PreparedStatement stmt, Connection conn) {
        close(null, stmt, conn);
    }
}
