package com.mulaev.ardnya.AppContextListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/*
 *  used in order to initialize the DB connection
 */
@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Connection connection = null;
        Statement statement = null;
        String db = "restful"; //DB name
        String user = "postgres"; //DB user
        String password = "ardnya333"; //DB password

        try {
            Class.forName("org.postgresql.Driver");

            connection =
                    DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + db,
                    user, password);

            servletContextEvent.getServletContext().setAttribute("DB",connection);

            statement = connection.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS users(" +
                    "Id SERIAL NOT NULL PRIMARY KEY," +
                    "FirstName varchar(30) NOT NULL," +
                    "SurName varchar(30) NOT NULL," +
                    "Birthday varchar(30) NOT NULL," +
                    "Address varchar(30) NOT NULL)";

            statement.execute(sql);
        } catch (Exception e) {
            servletContextEvent.getServletContext().removeAttribute("DB");
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //TODO
    }
}
