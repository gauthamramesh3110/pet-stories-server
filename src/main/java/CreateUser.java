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
            String db_user = "mirshmfqdtbodb";
            String db_password = "f588978a84806a5b8bf7c7a2d11d3504d21d4c4e9a359810864190c1c42fff63";

            String db_host = "ec2-52-7-15-198.compute-1.amazonaws.com";
            String db_port = "5432";
            String db_database = "dkhd5gp9gean2";

            String db_url = String.format("jdbc:postgresql://%s:%s/%s", db_host, db_port, db_database);

            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(db_url, db_user, db_password);

            if(connection != null){
                String usersCreateSql = "create table if not exists user_details "
                        + "(id serial, "
                        + "email varchar(50) not null, "
                        + "fullname varchar(50) not null, "
                        + "password varchar(50) not null, "
                        + "constraint user_details_pk primary key (id) );";

                Statement stm = connection.createStatement();
                stm.execute(usersCreateSql);

                String fullname = req.getParameter("fullname");
                String email = req.getParameter("email");
                String password = req.getParameter("password");

                String usersInsertSql = String.format("insert into user_details (email, password, fullname) values ('%s', '%s', '%s') ;", email, password, fullname);


                if(stm.executeUpdate(usersInsertSql) == 1){
                    respOp.println("User created successfully.");
                }else{
                    respOp.println("Error Creating user.");
                }
                stm.close();
                connection.close();

            }else{
                respOp.println("Cannot establish connection with database.");
            }

        } catch (ClassNotFoundException | SQLException e) {
            respOp.println("Exception Thrown.");
            e.printStackTrace();
        }
    }
}
