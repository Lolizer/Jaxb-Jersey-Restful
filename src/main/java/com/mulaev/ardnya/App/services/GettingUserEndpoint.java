package com.mulaev.ardnya.App.services;

import com.mulaev.ardnya.AppElements.User;
import com.mulaev.ardnya.AuthenticationFilter.CheckedSource;
import com.mulaev.ardnya.ParamsFilter.UserExistenceEnsured;

import javax.servlet.ServletContext;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * service allows to get all users in a DB or certain one
 */
@Path("users")
public class GettingUserEndpoint {
    @Context
    private ServletContext application;

    private Connection connection;
    private Statement statement;

    /*
     * returns List of all users if a resource method caller is authenticated
     */
    @Path("/all")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
    @CheckedSource
    public List<User> getAllUsers() {
        String sql;
        ArrayList<User> allUsers = new ArrayList<>();

        try {
            connection = (Connection) application.getAttribute("DB");
            statement = connection.createStatement();

            sql = String.format("SELECT * FROM users");
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                User user = new User(rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5));

                user.setId(rs.getInt(1));

                allUsers.add(user);
            }

            return allUsers;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * returns List of all users if a resource method caller is not authenticated
     */
    @Path("/all/unauthorized")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
    public List<User> getAllUsersUnauthorized() {
        String sql;
        ArrayList<User> allUsers = new ArrayList<>();

        try {
            connection = (Connection) application.getAttribute("DB");
            statement = connection.createStatement();

            sql = "SELECT * FROM users";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                allUsers.add(new User(rs.getString(2), rs.getString(3),
                        null, null));
            }

            return allUsers;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * returns certain user out of all users in DB if a resource method caller is authenticated
     */
    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
    @CheckedSource
    @UserExistenceEnsured
    public User getUser(@NotNull @PathParam(value = "id") String id) {
        String sql;
        User user = new User();

        try {
            connection = (Connection) application.getAttribute("DB");
            statement = connection.createStatement();

            sql = String.format("SELECT * FROM users WHERE id ='%s'", id);
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                user.setId(rs.getInt(1));
                user.setFirstName(rs.getString(2));
                user.setSurName(rs.getString(3));
                user.setBirthday(rs.getString(4));
                user.setAddress(rs.getString(5));
            }

            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
