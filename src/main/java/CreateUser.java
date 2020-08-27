import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/register")
public class CreateUser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter respOp = resp.getWriter();

        try {
            String db_user = "postgres";
            String db_password = "alert123";
            String db_url = "jdbc:postgresql://localhost:5432/users";

            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(db_url, db_user, db_password);

            if(connection != null){
                String fullname = req.getParameter("fullname");
                String email = req.getParameter("email");
                String password = req.getParameter("password");

                String sqlQuery = String.format("insert into users (email, password, fullname) values ('%s', '%s', '%s') ;", email, password, fullname);

                Statement stm = connection.createStatement();
                if(stm.executeUpdate(sqlQuery) == 1){
                    respOp.println("User created successfully.");
                }else{
                    respOp.println("Error Creating user.");
                }

            }else{
                respOp.println("Cannot establish connection with database.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            respOp.println("Exception Thrown.");
            e.printStackTrace();
        }
    }
}
