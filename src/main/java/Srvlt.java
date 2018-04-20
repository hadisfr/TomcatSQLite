package main.java;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet("/")
public class Srvlt extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            resp.getWriter().write(e.getMessage());
            return;
        }

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s",
                    getServletContext().getRealPath("WEB-INF/sample.db")));
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");

            connection.setAutoCommit(false);

            try {
                PreparedStatement insertStatement = connection.prepareStatement("insert into person values(?, ?)");
                insertStatement.setInt(1, 1);
                insertStatement.setString(2, "leo");
                insertStatement.executeUpdate();
                insertStatement.setInt(1, 2);
                insertStatement.setString(2, "yui");
                insertStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                resp.getWriter().write(e.getMessage());
                connection.rollback();
            }

            connection.setAutoCommit(true);

            ResultSet rs = statement.executeQuery("select * from person");
            while (rs.next()) {
                // read the result set
                resp.getWriter().write("name = " + rs.getString("name"));
                resp.getWriter().write(", ");
                resp.getWriter().write("id = " + rs.getInt("id"));
                resp.getWriter().write("\n");
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            resp.getWriter().write(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                resp.getWriter().write(e.getMessage());
            }
        }
    }
}
