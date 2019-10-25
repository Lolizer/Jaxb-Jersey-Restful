package com.mulaev.ardnya.App.services;

import com.mulaev.ardnya.AppElements.User;
import com.mulaev.ardnya.AuthenticationFilter.CheckedSource;
import com.mulaev.ardnya.ParamsFilter.UserExistenceEnsured;

import javax.servlet.ServletContext;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/*
 * removal endpoint allowing to delete a certain user using id
 */
@Path("remove/{id}")
@CheckedSource
@UserExistenceEnsured
public class DeletionEndpoint {
    @Context
    private ServletContext application;

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public User removeCertain(@NotNull @PathParam(value = "id") String id) {
        return delete(id);
    }

    @POST
    @Produces(MediaType.APPLICATION_XML)
    public User removeCertainUsingPost(@NotNull @PathParam(value = "id") String id) {
        return delete(id);
    }
    /*
     * performs the actual deletion operation
     */
    private User delete (String id) {
        Connection connection;
        Statement statement;
        String sql;
        User user = null;

        try {
            connection = (Connection) application.getAttribute("DB");
            statement = connection.createStatement();

            sql = String.format("SELECT * FROM users WHERE id = %s", id);
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                user = new User(rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5));
            }

            sql = String.format("DELETE FROM users WHERE id = %s", id);
            statement.executeUpdate(sql);

            sql = "REINDEX TABLE users";
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
