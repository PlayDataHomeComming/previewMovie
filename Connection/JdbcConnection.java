package Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class JdbcConnection {
    private final String url = "jdbc:mysql://localhost:3306/movie"+
            "?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
    private final String username = "root";
    private final String password = "1q2w3e4r!!";

    public java.sql.Connection getJdbc()  {
        java.sql.Connection conn;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager
                    .getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
//        System.out.println("연동 성공");
        return conn;
    }
}
